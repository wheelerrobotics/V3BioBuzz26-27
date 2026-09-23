package org.firstinspires.ftc.teamcode.bioBuzz.robot;

import static com.pedropathing.api.Paths.line;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import static org.firstinspires.ftc.teamcode.robot.config.RobotConstants.Intake.intakePower;
import static org.firstinspires.ftc.teamcode.robot.config.RobotConstants.Stopper.stopperIn;
import static org.firstinspires.ftc.teamcode.robot.config.RobotConstants.Stopper.stopperOut;
import static org.firstinspires.ftc.teamcode.robot.config.RobotConstants.Transfer.transferPower;

import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Hood;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.LL;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.robot.hardware.HardwareNames;
import org.firstinspires.ftc.teamcode.robot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.robot.subsystems.LimelightController;
import org.firstinspires.ftc.teamcode.robot.subsystems.Stopper;
import org.firstinspires.ftc.teamcode.robot.subsystems.Transfer;

public class Robot {
    public final Follower follower;
    public final RobotMacros macros;
    public final Hood hood;
    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;
    public DcMotorEx backLeft;
    public DcMotorEx backRight;
    public LL LL;
    public final Shooter shooter;

    public LimelightController limelightController;

    public Transfer transfer;
    public Intake intake;
    public Stopper stopper;


    public Robot(HardwareMap hardwareMap) {
        transfer = new Transfer(hardwareMap);
        intake = new Intake(hardwareMap);
        stopper = new Stopper(hardwareMap);


        follower = Constants.createFollower(hardwareMap);
        LL = new LL(hardwareMap);
        macros = new RobotMacros();

        //Subsystems
        hood = new Hood(hardwareMap);


        shooter = new Shooter(hardwareMap);
    }

    public void motorDriveXYVectors(double x, double y, double rotation) {
        double frontLeftPower = y + x + rotation;
        double backLeftPower = y - x + rotation;
        double frontRightPower = y - x - rotation;
        double backRightPower = y + x - rotation;
    }

    public void tick(Gamepad gamepad1, Gamepad gamepad2) {
        boolean driverWantsControl =
                Math.abs(gamepad1.left_stick_x) > 0.1 ||
                Math.abs(gamepad1.left_stick_y) > 0.1 ||
                Math.abs(gamepad1.right_stick_x) > 0.1;

        // Do not stop normal manual driving. Only interrupt an active automatic
        // path/hold when the driver moves a joystick.
        if (driverWantsControl && (follower.following() || follower.holding())) {
            macros.stop();
        }

        follower.update();
        LL.update();
    }

    public void stop() {
        follower.stop();
        macros.stop();
        LL.stop();
    }

    public class RobotMacros {
        public void driveToPos(Pose destination) {driveToPos(destination, 1.0, 1.0);}
        public void driveToPos(Pose destination, double drive_power, double position_tolerance) {
            Pose start = follower.pose();

            if (Math.hypot(destination.x() - start.x(), destination.y() - start.y())
                    < position_tolerance) {
                follower.hold(destination);
            } else {
                Path path = line(start, destination)
                        .linear(start, destination)
                        .with(Constants.foresightConfig.maxPathSpeed.at(drive_power));
                follower.holdEnd.set(true);
                follower.follow(path);
            }
        }

        public void stop() {
            follower.stop();
        }
    }


    //COMMANDS
    public class Commands {

        //TRANSFER
        public Command transferOn() {
            return Command.build()
                    .setExecute(() -> transfer.setTransferPower(transferPower));
        }
        public Command transferOff() {
            return Command.build()
                    .setExecute(() -> transfer.setTransferPower(0));
        }


        //INTAKE
        public Command intakeIn() {
            return Command.build()
                    .setExecute(() -> intake.setIntakePower(intakePower));
        }
        public Command intakeOff() {
            return Command.build()
                    .setExecute(() -> intake.setIntakePower(0));
        }
        public Command outtake() {
            return Command.build()
                    .setExecute(() -> intake.setIntakePower(-intakePower));
        }


        //STOPPER
        public Command stopperIn() {
            return Command.build()
                    .setExecute(() -> stopper.setStopperPos(stopperIn));
        }
        public Command stopperOut() {
            return Command.build()
                    .setExecute(() -> stopper.setStopperPos(stopperOut));
        }

    }



}
