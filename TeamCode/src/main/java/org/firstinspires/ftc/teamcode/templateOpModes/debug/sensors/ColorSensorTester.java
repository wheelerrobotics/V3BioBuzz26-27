package org.firstinspires.ftc.teamcode.templateOpModes.debug.sensors;

import java.util.Locale;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.templateOpModes.debug.util.HardwareTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/** Displays raw and optional advanced readings from any configured color sensor. */
@Configurable
@TeleOp(name = "Color Sensor Tester", group = "Debug/Sensors")
public class ColorSensorTester extends OpMode {
    private TelemetryManager telemetryM;

    public static String SENSOR_NAME = "colorSensor";

    private ColorSensor sensor;
    private String loadedName = "";

    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.addLine("Enter a color sensor name in Panels");
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
        telemetryM.addLine("Raw Color Readings");
        telemetryM.addData("Red", sensor.red());
        telemetryM.addData("Green", sensor.green());
        telemetryM.addData("Blue", sensor.blue());
        telemetryM.addData("Alpha", sensor.alpha());
        telemetryM.addData("ARGB", String.format(Locale.US,  "0x%08X", sensor.argb()));
        telemetryM.addData("I2C Address", String.format(Locale.US,  "0x%02X", sensor.getI2cAddress().get8Bit()));

        if (sensor instanceof NormalizedColorSensor) {
            NormalizedColorSensor normalizedSensor = (NormalizedColorSensor) sensor;
            NormalizedRGBA colors = normalizedSensor.getNormalizedColors();
            telemetryM.addLine("Normalized Color Readings");
            telemetryM.addData("Red", String.format(Locale.US,  "%.4f", colors.red));
            telemetryM.addData("Green", String.format(Locale.US,  "%.4f", colors.green));
            telemetryM.addData("Blue", String.format(Locale.US,  "%.4f", colors.blue));
            telemetryM.addData("Alpha", String.format(Locale.US,  "%.4f", colors.alpha));
            telemetryM.addData("Gain", String.format(Locale.US,  "%.3f", normalizedSensor.getGain()));
        }

        if (sensor instanceof DistanceSensor) {
            DistanceSensor distanceSensor = (DistanceSensor) sensor;
            telemetryM.addLine("Integrated Distance Reading");
            telemetryM.addData("Distance", String.format(Locale.US,  "%.2f cm",
                    distanceSensor.getDistance(DistanceUnit.CM)));
        }

        telemetryM.update(telemetry);
    }

    private void loadSensorIfNeeded() {
        if (sensor != null && loadedName.equals(SENSOR_NAME)) {
            return;
        }

        sensor = hardwareMap.tryGet(ColorSensor.class, SENSOR_NAME);
        loadedName = sensor != null ? SENSOR_NAME : "";
    }
}
