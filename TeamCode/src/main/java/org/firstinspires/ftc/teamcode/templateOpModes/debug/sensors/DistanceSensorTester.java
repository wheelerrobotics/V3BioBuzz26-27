package org.firstinspires.ftc.teamcode.templateOpModes.debug.sensors;

import java.util.Locale;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.templateOpModes.debug.util.HardwareTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/** Displays live measurements from any configured distance sensor. */
@Configurable
@TeleOp(name = "Distance Sensor Tester", group = "Debug/Sensors")
public class DistanceSensorTester extends OpMode {
    private TelemetryManager telemetryM;

    public static String SENSOR_NAME = "distanceSensor";

    private DistanceSensor sensor;
    private String loadedName = "";

    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.addLine("Enter a distance sensor name in Panels");
        telemetryM.update(telemetry);
    }

    @Override
    public void loop() {
        loadSensorIfNeeded();

        if (sensor == null) {
            telemetryM.addData("Sensor Error", "Could not find: " + SENSOR_NAME);
            telemetryM.update(telemetry);
            return;
        }

        HardwareTelemetry.addDeviceInfo(telemetry, loadedName, sensor);
        telemetryM.addLine("Distance Readings");
        telemetryM.addData("Millimeters", String.format(Locale.US,  "%.2f mm", sensor.getDistance(DistanceUnit.MM)));
        telemetryM.addData("Centimeters", String.format(Locale.US,  "%.2f cm", sensor.getDistance(DistanceUnit.CM)));
        telemetryM.addData("Meters", String.format(Locale.US,  "%.4f m", sensor.getDistance(DistanceUnit.METER)));
        telemetryM.addData("Inches", String.format(Locale.US,  "%.3f in", sensor.getDistance(DistanceUnit.INCH)));
        telemetryM.update(telemetry);
    }

    private void loadSensorIfNeeded() {
        if (sensor != null && loadedName.equals(SENSOR_NAME)) {
            return;
        }

        sensor = hardwareMap.tryGet(DistanceSensor.class, SENSOR_NAME);
        loadedName = sensor != null ? SENSOR_NAME : "";
    }
}
