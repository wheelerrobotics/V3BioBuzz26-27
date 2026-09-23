package org.firstinspires.ftc.teamcode.bioBuzz.robot;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.bioBuzz.Commands;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.LL;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public class Robot {
    public final Follower follower;
    public final Commands commands;
    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;
    public DcMotorEx backLeft;
    public DcMotorEx backRight;

    public LL LL;

    public Robot(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotorEx.class, HardwareNames.FRONT_LEFT_DRIVE);
        frontRight = hardwareMap.get(DcMotorEx.class, HardwareNames.FRONT_RIGHT_DRIVE);
        backLeft = hardwareMap.get(DcMotorEx.class, HardwareNames.BACK_LEFT_DRIVE);
        backRight = hardwareMap.get(DcMotorEx.class, HardwareNames.BACK_RIGHT_DRIVE);

        frontLeft.setZeroPowerBehavior(BRAKE);
        frontRight.setZeroPowerBehavior(BRAKE);
        backLeft.setZeroPowerBehavior(BRAKE);
        backRight.setZeroPowerBehavior(BRAKE);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        follower = Constants.createFollower(hardwareMap);
        LL = new LL(hardwareMap);

        commands = new Commands(this);
    }

    public void motorDriveXYVectors(double x, double y, double rotation) {
        double frontLeftPower = y + x + rotation;
        double backLeftPower = y - x + rotation;
        double frontRightPower = y - x - rotation;
        double backRightPower = y + x - rotation;

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
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
        LL.update();
    }

    public void stop() {
        follower.stop();
        LL.stop();
    }
}
