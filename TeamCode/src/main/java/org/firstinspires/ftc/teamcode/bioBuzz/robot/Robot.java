package org.firstinspires.ftc.teamcode.bioBuzz.robot;

import static com.pedropathing.api.Paths.line;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotConstants.Intake.intakePower;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotConstants.Stopper.stopperIn;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotConstants.Stopper.stopperOut;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotConstants.Transfer.transferPower;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Hood;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bioBuzz.Commands;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.LL;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Stopper;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.pedro.Constants;

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
        limelight = new LL(hardwareMap);
        turret = new Turret(hardwareMap, limelight);

        commands = new Commands();
        limelight = new LL(hardwareMap);

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


        if (driverWantsControl && (follower.following() || follower.holding())) {
            follower.stop();
        }

        follower.update();
        limelight.update();
    }

    public void stop() {
        follower.stop();
        limelight.stop();
        turret.stop();
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
