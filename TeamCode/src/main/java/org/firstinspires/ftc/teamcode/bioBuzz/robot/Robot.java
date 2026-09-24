package org.firstinspires.ftc.teamcode.bioBuzz.robot;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.commands.Commands.lazy;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.pedropathing.ivy.pedro.PedroCommands.hold;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotConstants.Intake.intakePower;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotConstants.Stopper.stopperIn;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotConstants.Stopper.stopperOut;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotConstants.Transfer.transferPower;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.BACK_LEFT_DRIVE;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.BACK_RIGHT_DRIVE;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.FRONT_LEFT_DRIVE;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.FRONT_RIGHT_DRIVE;

import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Hood;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.LL;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Stopper;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Turret;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public class Robot {
    public final Follower follower;
    public final Commands commands;
    public final Hood hood;
    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;
    public DcMotorEx backLeft;
    public DcMotorEx backRight;

    public LL limelight;
    public Turret turret;
    public final Shooter shooter;
    public Transfer transfer;
    public Intake intake;
    public Stopper stopper;


    public Robot(HardwareMap hardwareMap) {
        transfer = new Transfer(hardwareMap);
        intake = new Intake(hardwareMap);
        stopper = new Stopper(hardwareMap);


        follower = Constants.createFollower(hardwareMap);

        frontLeft = hardwareMap.get(DcMotorEx.class, FRONT_LEFT_DRIVE);
        frontRight = hardwareMap.get(DcMotorEx.class, FRONT_RIGHT_DRIVE);
        backLeft = hardwareMap.get(DcMotorEx.class, BACK_LEFT_DRIVE);
        backRight = hardwareMap.get(DcMotorEx.class, BACK_RIGHT_DRIVE);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        limelight = new LL(hardwareMap);
        turret = new Turret(hardwareMap, limelight);

        commands = new Commands();

        //Subsystems
        hood = new Hood(hardwareMap);


        shooter = new Shooter(hardwareMap);
    }

    public void tick(Gamepad gamepad1, Gamepad gamepad2) {
        boolean driverWantsControl =
                Math.abs(gamepad1.left_stick_x) > 0.1 ||
                Math.abs(gamepad1.left_stick_y) > 0.1 ||
                Math.abs(gamepad1.right_stick_x) > 0.1;


        if (driverWantsControl && (follower.following() || follower.holding())) {
            follower.stop();
        }

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
        public Command transferOn() {
            return Command.build()
                    .setStart(() -> transfer.setTransferPower(transferPower));
        }
        public Command transferOff() {
            return Command.build()
                    .setStart(() -> transfer.setTransferPower(0));
        }

        //INTAKE
        public Command intakeIn() {
            return Command.build()
                    .setStart(() -> intake.setIntakePower(intakePower));
        }
        public Command intakeOff() {
            return Command.build()
                    .setStart(() -> intake.setIntakePower(0));
        }
        public Command outtake() {
            return Command.build()
                    .setStart(() -> intake.setIntakePower(-intakePower));
        }

        //STOPPER
        public Command stopperIn() {
            return Command.build()
                    .setStart(() -> stopper.setStopperPos(stopperIn)).setDone(() -> true);
        }
        public Command stopperOut() {
            return Command.build()
                    .setStart(() -> stopper.setStopperPos(stopperOut)).setDone(() -> true);
        }

        //Shooter + Hood
        public Command shoot() {
            return Command.build()
                    .setExecute(shooter::update);
        }

        public Command hood() {
            return Command.build()
                    .setExecute(hood::updatePosition);
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
