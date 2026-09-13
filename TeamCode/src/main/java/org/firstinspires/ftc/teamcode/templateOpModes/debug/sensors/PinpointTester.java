package org.firstinspires.ftc.teamcode.templateOpModes.debug.sensors;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;

import java.util.Locale;
import java.util.Objects;

/** Displays live pose, velocity, encoder, configuration, and status data from a Pinpoint. */
@Configurable
@TeleOp(name = "Pinpoint Tester", group = "Debug/Sensors")
public class PinpointTester extends OpMode {
    public static String PINPOINT_NAME = HardwareNames.PINPOINT;
    public static boolean RESET_POSITION = false;
    public static boolean RECALIBRATE_IMU = false;
    public static boolean RESET_POSITION_AND_IMU = false;

    private TelemetryManager telemetryM;
    private GoBildaPinpointDriver pinpoint;
    private String loadedName = "";
    private String lastAction = "None";

    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        clearActions();
        telemetryM.addLine("Enter the Pinpoint configuration name in Panels");
        telemetryM.addLine("Keep the robot still while recalibrating the IMU");
        telemetryM.update(telemetry);
    }

    @Override
    public void loop() {
        loadPinpointIfNeeded();

        if (pinpoint == null) {
            telemetryM.addData("Pinpoint Error", "Could not find: " + PINPOINT_NAME);
            telemetryM.update(telemetry);
            return;
        }

        try {
            runRequestedAction();
            pinpoint.update();
            displayReadings();
        } catch (RuntimeException exception) {
            telemetryM.addData("Pinpoint Error", Objects.requireNonNull(exception.getMessage()));
        }

        telemetryM.update(telemetry);
    }

    private void loadPinpointIfNeeded() {
        if (pinpoint != null && loadedName.equals(PINPOINT_NAME)) {
            return;
        }

        pinpoint = hardwareMap.tryGet(GoBildaPinpointDriver.class, PINPOINT_NAME);
        loadedName = pinpoint != null ? PINPOINT_NAME : "";
        lastAction = pinpoint != null ? "Loaded " + loadedName : "None";
        clearActions();
    }

    private void runRequestedAction() {
        if (RESET_POSITION_AND_IMU) {
            pinpoint.resetPosAndIMU();
            lastAction = "Position reset and IMU recalibration started";
            clearActions();
        } else if (RECALIBRATE_IMU) {
            pinpoint.recalibrateIMU();
            lastAction = "IMU recalibration started";
            clearActions();
        } else if (RESET_POSITION) {
            pinpoint.setPosition(new Pose2D(
                    DistanceUnit.INCH,
                    0,
                    0,
                    AngleUnit.DEGREES,
                    0
            ));
            lastAction = "Position reset to (0, 0, 0)";
            clearActions();
        }
    }

    private void displayReadings() {
        Pose2D pose = pinpoint.getPosition();
        double xVelocity = pinpoint.getVelX(DistanceUnit.INCH);
        double yVelocity = pinpoint.getVelY(DistanceUnit.INCH);

        telemetryM.addLine("Device Information");
        telemetryM.addData("Config Name", loadedName);
        telemetryM.addData("Device", pinpoint.getDeviceName());
        telemetryM.addData("Connection", pinpoint.getConnectionInfo());
        telemetryM.addData("Driver Version", pinpoint.getVersion());
        telemetryM.addData("Device ID", pinpoint.getDeviceID());
        telemetryM.addData("Device Version", pinpoint.getDeviceVersion());
        telemetryM.addData("Status", pinpoint.getDeviceStatus());
        telemetryM.addData("Last Action", lastAction);

        telemetryM.addLine("Pose");
        telemetryM.addData("X", String.format(Locale.US, "%.3f in", pose.getX(DistanceUnit.INCH)));
        telemetryM.addData("Y", String.format(Locale.US, "%.3f in", pose.getY(DistanceUnit.INCH)));
        telemetryM.addData("Heading", String.format(Locale.US,
                "%.3f deg",
                pose.getHeading(AngleUnit.DEGREES)
        ));

        telemetryM.addLine("Velocity");
        telemetryM.addData("X Velocity", String.format(Locale.US, "%.3f in/s", xVelocity));
        telemetryM.addData("Y Velocity", String.format(Locale.US, "%.3f in/s", yVelocity));
        telemetryM.addData("Speed", String.format(Locale.US, 
                "%.3f in/s",
                Math.hypot(xVelocity, yVelocity)
        ));
        telemetryM.addData("Heading Velocity", String.format(Locale.US, 
                "%.3f deg/s",
                pinpoint.getHeadingVelocity(UnnormalizedAngleUnit.DEGREES)
        ));
        telemetryM.addLine("Odometry Pods");
        telemetryM.addData("Forward Encoder", pinpoint.getEncoderX());
        telemetryM.addData("Strafe Encoder", pinpoint.getEncoderY());
        telemetryM.addData("Forward Pod Offset", String.format(Locale.US, 
                "%.3f in",
                pinpoint.getXOffset(DistanceUnit.INCH)
        ));
        telemetryM.addData("Strafe Pod Offset", String.format(Locale.US, 
                "%.3f in",
                pinpoint.getYOffset(DistanceUnit.INCH)
        ));

        telemetryM.addLine("IMU");
        telemetryM.addData("Yaw Scalar", pinpoint.getYawScalar());
        telemetryM.addData("Pitch", String.format(Locale.US, 
                "%.3f deg",
                pinpoint.getPitch(AngleUnit.DEGREES)
        ));
        telemetryM.addData("Roll", String.format(Locale.US, 
                "%.3f deg",
                pinpoint.getRoll(AngleUnit.DEGREES)
        ));

        telemetryM.addLine("Performance");
        telemetryM.addData("Loop Time", String.format(Locale.US, "%d us", pinpoint.getLoopTime()));
        telemetryM.addData("Frequency", String.format(Locale.US, "%.1f Hz", pinpoint.getFrequency()));

        if (pinpoint.getDeviceStatus() != GoBildaPinpointDriver.DeviceStatus.READY) {
            telemetryM.addLine("WARNING: Pinpoint is not ready; inspect its status above");
        }
    }

    private void clearActions() {
        RESET_POSITION = false;
        RECALIBRATE_IMU = false;
        RESET_POSITION_AND_IMU = false;
    }
}
