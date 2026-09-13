package org.firstinspires.ftc.teamcode.templateOpModes.debug.sensors;

import java.util.Locale;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.templateOpModes.debug.util.HardwareTelemetry;

import java.util.List;

/** Automatically finds and displays safe diagnostic readings from the robot's IMU. */
@TeleOp(name = "IMU Tester", group = "Debug/Sensors")
public class ImuTester extends OpMode {
    private TelemetryManager telemetryM;

    private IMU imu;
    private String error;

    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        List<IMU> imus = hardwareMap.getAll(IMU.class);

        if (imus.isEmpty()) {
            error = "No IMU found";
        } else if (imus.size() > 1) {
            error = "Found " + imus.size() + " IMUs; automatic selection is ambiguous";
        } else {
            imu = imus.get(0);
        }
    }

    @Override
    public void loop() {
        if (imu == null) {
            telemetryM.addData("IMU Error", error);
            telemetryM.update(telemetry);
            return;
        }

        YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();
        AngularVelocity velocity = imu.getRobotAngularVelocity(AngleUnit.DEGREES);
        Quaternion quaternion = imu.getRobotOrientationAsQuaternion();

        HardwareTelemetry.addDeviceInfo(telemetry, "Automatically detected", imu);
        telemetryM.addLine("Orientation");
        telemetryM.addData("Yaw", String.format(Locale.US,  "%.3f°", angles.getYaw(AngleUnit.DEGREES)));
        telemetryM.addData("Pitch", String.format(Locale.US,  "%.3f°", angles.getPitch(AngleUnit.DEGREES)));
        telemetryM.addData("Roll", String.format(Locale.US,  "%.3f°", angles.getRoll(AngleUnit.DEGREES)));
        telemetryM.addData("Acquisition Time", angles.getAcquisitionTime());

        telemetryM.addLine("Angular Velocity");
        telemetryM.addData("X Rotation", String.format(Locale.US,  "%.3f°/s", velocity.xRotationRate));
        telemetryM.addData("Y Rotation", String.format(Locale.US,  "%.3f°/s", velocity.yRotationRate));
        telemetryM.addData("Z Rotation", String.format(Locale.US,  "%.3f°/s", velocity.zRotationRate));

        telemetryM.addLine("Quaternion");
        telemetryM.addData("W", String.format(Locale.US,  "%.6f", quaternion.w));
        telemetryM.addData("X", String.format(Locale.US,  "%.6f", quaternion.x));
        telemetryM.addData("Y", String.format(Locale.US,  "%.6f", quaternion.y));
        telemetryM.addData("Z", String.format(Locale.US,  "%.6f", quaternion.z));
        telemetryM.update(telemetry);
    }
}
