package org.firstinspires.ftc.teamcode.bioBuzz.robot;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Command.build;
import static com.pedropathing.ivy.Scheduler.cancel;
import static com.pedropathing.ivy.commands.Commands.infinite;
import static com.pedropathing.ivy.commands.Commands.lazy;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.pedropathing.ivy.pedro.PedroCommands.hold;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Intake.intakePower;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Stopper.stopperIn;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Stopper.stopperOut;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Transfer.transferPower;

import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.GlobalT;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.ballistics.Ballistics;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Hood;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.LL;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Slides;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Stopper;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Turret;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public class Robot {
    public final Follower follower;
    public final Commands commands;
    public final Hood hood;
    public final LL limelight;
    public final Turret turret;
    public final Shooter shooter;
    public final Transfer transfer;
    public final Intake intake;
    public final Stopper stopper;
    public final Slides slides;


    public Robot(HardwareMap hardwareMap) {
        follower = Constants.createFollower(hardwareMap);
        shooter = new Shooter(hardwareMap);
        Ballistics.init(follower,
                hardwareMap.get(Limelight3A.class, HardwareNames.LIMELIGHT),
                GlobalT.get_telemetry(),
                shooter.getShooter1(),
                shooter.getShooter2());


        transfer = new Transfer(hardwareMap);
        intake = new Intake(hardwareMap);
        stopper = new Stopper(hardwareMap);
        slides = new Slides(hardwareMap);

        limelight = new LL(hardwareMap);
        turret = new Turret(hardwareMap, limelight);

        commands = new Commands();

        //Subsystems
        hood = new Hood(hardwareMap);



    }

    public void update(Gamepad gamepad1, Gamepad gamepad2) {
//        boolean driverWantsControl =
//                Math.abs(gamepad1.left_stick_x) > 0.1 ||
//                Math.abs(gamepad1.left_stick_y) > 0.1 ||
//                Math.abs(gamepad1.right_stick_x) > 0.1;
//
//
//        if (driverWantsControl && (follower.following() || follower.holding())) {
//            follower.stop();
//        }

        follower.update();
        limelight.update();
        turret.update();
    }

    public void stop() {
        follower.stop();
        turret.stop();
        limelight.stop();
    }


    //COMMANDS
    public class Commands {

        //TRANSFER
        public Command transfer() {
            return build()
                    .setStart(() -> transfer.setTransferPower(transferPower))
                    .setEnd(endCondition -> transfer.setTransferPower(0))
                    .requiring(Transfer.class);
        }

        //INTAKE
        public Command intake() {
            return infinite(() -> {
                if (intake.pollenPresent() && stopper.getNumBalls() < 4) {
                    intake.setIntakePower(intakePower);
                } else {
                    intake.setIntakePower(0);
                }
            })
                    .setPriority(1)
                    .requiring(Intake.class);
        }

        public Command intakeOn() {
            return build()
                    .setStart(() -> intake.setIntakePower(intakePower))
                    .setDone(() -> stopper.getNumBalls() >= 4)
                    .setEnd(endCondition -> intake.setIntakePower(0))
                    .requiring(Intake.class)
                    .setPriority(10);
        }

        public Command intakeOff() {
            return build()
                    .setStart(() -> intake.setIntakePower(0))
                    .setEnd(endCondition -> intake().schedule())
                    .requiring(Intake.class)
                    .setPriority(10);
        }
        public Command outtake() {
            return build()
                    .setStart(() -> {
                        intake.setIntakePower(-intakePower);
                        transfer.setTransferPower(-transferPower);
                    })
                    .setEnd(endCondition -> intake().schedule())
                    .requiring(Transfer.class, Intake.class)
                    .setPriority(100);
        }

        //INTAKE SLIDES
        public Command extendOut() {
            return build()
                    .setStart(slides::extendMax);
        }

        public Command extendIn() {
            return build()
                    .setStart(slides::extendMin);
        }

        public Command extendCommand() {
            return build()
                    .setStart(() -> slides.extendMax())
                    .setEnd(endCondition -> slides.extendMin());
            
        }

        //STOPPER
        public Command stopper() {
            return build()
                    .setStart(() -> stopper.setStopperPos(stopperOut))
                    .setEnd(endCondition -> stopper.setStopperPos(stopperIn));
        }


        //Shooter + Hood
        public Command shooterUpdate() {
            return infinite(shooter::update);
        }

        public Command shootCommand() {
            return build()
                    .setStart(() -> {
                        transfer().schedule();
                        stopper().schedule();
                    })
                    .setDone(stopper::isEmpty)
                    .setEnd(endCondition -> {
                        cancel(transfer());
                        cancel(stopper());
                    });

        }

        public Command hood() {
            return infinite(hood::updatePosition);
        }

        public Command updateBallistics() {
            return infinite(Ballistics::update);
        }

        public Command driveToPos(Pose destination, double drivePower, double positionTolerance) {
            return lazy(() -> {
                Pose start = follower.pose();

                if (start.distance(destination) < positionTolerance) {
                    return hold(follower, destination)
                            .requiring(follower);
                }

                Path path = line(start, destination)
                        .linear(start, destination)
                        .with(Constants.foresightConfig.maxPathSpeed.at(drivePower));

                follower.holdEnd.set(true);
                return follow(follower, path)
                        .requiring(follower);
            }).requiring(follower);
        }


    }
}
