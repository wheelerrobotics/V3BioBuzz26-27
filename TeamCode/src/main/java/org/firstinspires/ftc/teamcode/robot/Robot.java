package org.firstinspires.ftc.teamcode.robot;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.pedropathing.api.Paths.line;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.robot.hardware.HardwareNames;
import org.firstinspires.ftc.teamcode.robot.subsystems.LL;

public class Robot {
    public final Follower follower;
    public final RobotMacros macros;
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
        macros = new RobotMacros();
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
}
