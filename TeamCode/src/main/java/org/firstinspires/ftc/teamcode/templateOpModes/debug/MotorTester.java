package org.firstinspires.ftc.teamcode.templateOpModes.debug;

import java.util.Locale;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.templateOpModes.debug.util.HardwareTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/** Safely tests any configured DC motor and displays its live diagnostic data. */
@Configurable
@TeleOp(name = "Motor Tester", group = "Debug")
public class MotorTester extends OpMode {
    private TelemetryManager telemetryM;

    public static String MOTOR_NAME = "motor";
    public static double MOTOR_POWER = 0.20;
    public static boolean ENABLE_MOTOR = false;
    public static boolean REVERSE_DIRECTION = false;
    public static boolean USE_ENCODER = true;
    public static boolean BRAKE_WHEN_STOPPED = true;

    private DcMotorEx motor;
    private String loadedMotorName = "";

    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.addLine("Enter a motor configuration name in Panels");
        telemetryM.addLine("Set ENABLE_MOTOR = true when ready");
        telemetryM.update(telemetry);
    }

    @Override
    public void loop() {
        loadMotorIfNeeded();

        if (motor == null) {
            telemetryM.addData("Motor Error", "Could not find: " + MOTOR_NAME);
            telemetryM.update(telemetry);
            return;
        }

        configureMotor();

        double requestedPower = Range.clip(MOTOR_POWER, -1.0, 1.0);
        boolean shouldRun = ENABLE_MOTOR;
        motor.setPower(shouldRun ? requestedPower : 0.0);

        HardwareTelemetry.addDeviceInfo(telemetry, loadedMotorName, motor);

        telemetryM.addLine("Motor Readings");
        telemetryM.addData("Status", shouldRun ? "RUNNING" : "STOPPED");
        telemetryM.addData("Enabled", ENABLE_MOTOR);
        telemetryM.addData("Requested Power", MOTOR_POWER);
        telemetryM.addData("Applied Power", motor.getPower());
        telemetryM.addData("Direction", motor.getDirection());
        telemetryM.addData("Run Mode", motor.getMode());
        telemetryM.addData("Zero Power", motor.getZeroPowerBehavior());
        telemetryM.addData("Encoder Position", motor.getCurrentPosition());
        telemetryM.addData("Velocity", String.format(Locale.US,  "%.2f ticks/sec", motor.getVelocity()));
        telemetryM.addData("Current", String.format(Locale.US,  "%.3f A", motor.getCurrent(CurrentUnit.AMPS)));
        telemetryM.addData("Over Current", motor.isOverCurrent());
        telemetryM.update(telemetry);
    }

    private void loadMotorIfNeeded() {
        if (motor != null && loadedMotorName.equals(MOTOR_NAME)) {
            return;
        }
        stopLoadedMotor();
        motor = null;
        loadedMotorName = "";

        try {
            motor = hardwareMap.get(DcMotorEx.class, MOTOR_NAME);
            loadedMotorName = MOTOR_NAME;
        } catch (RuntimeException ignored) {
            // The error is shown in telemetry so the OpMode remains usable.
        }
    }

    private void configureMotor() {
        motor.setDirection(REVERSE_DIRECTION
                ? DcMotorSimple.Direction.REVERSE
                : DcMotorSimple.Direction.FORWARD);
        motor.setMode(USE_ENCODER
                ? DcMotor.RunMode.RUN_USING_ENCODER
                : DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setZeroPowerBehavior(BRAKE_WHEN_STOPPED
                ? DcMotor.ZeroPowerBehavior.BRAKE
                : DcMotor.ZeroPowerBehavior.FLOAT);
    }

    private void stopLoadedMotor() {
        if (motor != null) {
            motor.setPower(0.0);
        }
    }

    @Override
    public void stop() {
        stopLoadedMotor();
    }
}
