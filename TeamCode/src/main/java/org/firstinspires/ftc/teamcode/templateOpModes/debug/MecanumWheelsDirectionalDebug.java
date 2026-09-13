package org.firstinspires.ftc.teamcode.templateOpModes.debug;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp(name = "Wheel Debug", group = "Debug")
public class MecanumWheelsDirectionalDebug extends OpMode {
    private TelemetryManager telemetryM;

    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;
    public DcMotorEx backLeft;
    public DcMotorEx backRight;

    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
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

        telemetryM.addLine("Wheel Debug Ready");
        telemetryM.update(telemetry);

    }

    @Override
    public void loop() {

        if (gamepad1.x) {
            frontLeft.setPower(1);
        } else {
            frontLeft.setPower(0);
        }
        if (gamepad1.y) {
            frontRight.setPower(1);
        } else {
            frontRight.setPower(0);
        }
        if (gamepad1.a) {
            backLeft.setPower(1);
        } else {
            backLeft.setPower(0);
        }
        if (gamepad1.b) {
            backRight.setPower(1);
        } else {
            backRight.setPower(0);
        }

        telemetryM.addData("X", "Front Left");
        telemetryM.addData("Y", "Front Right");
        telemetryM.addData("A", "Back Left");
        telemetryM.addData("B", "Back Right");

        telemetryM.update(telemetry);
    }
}
