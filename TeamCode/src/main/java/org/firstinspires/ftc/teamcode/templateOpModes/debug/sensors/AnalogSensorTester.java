package org.firstinspires.ftc.teamcode.templateOpModes.debug.sensors;

import java.util.Locale;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;

import org.firstinspires.ftc.teamcode.templateOpModes.debug.util.HardwareTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/** Displays voltage and range information for any configured analog input. */
@Configurable
@TeleOp(name = "Analog Sensor Tester", group = "Debug/Sensors")
public class AnalogSensorTester extends OpMode {
    private TelemetryManager telemetryM;

    public static String SENSOR_NAME = "analogSensor";

    private AnalogInput sensor;
    private String loadedName = "";

    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.addLine("Enter an analog sensor name in Panels");
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

        double voltage = sensor.getVoltage();
        double maxVoltage = sensor.getMaxVoltage();

        HardwareTelemetry.addDeviceInfo(telemetry, loadedName, sensor);
        telemetryM.addLine("Analog Readings");
        telemetryM.addData("Voltage", String.format(Locale.US,  "%.4f V", voltage));
        telemetryM.addData("Maximum Voltage", String.format(Locale.US,  "%.4f V", maxVoltage));
        telemetryM.addData("Full Scale", maxVoltage > 0
                ? String.format(Locale.US, "%.1f%%", voltage / maxVoltage * 100.0)
                : "Unavailable");
        telemetryM.update(telemetry);
    }

    private void loadSensorIfNeeded() {
        if (sensor != null && loadedName.equals(SENSOR_NAME)) {
            return;
        }

        sensor = hardwareMap.tryGet(AnalogInput.class, SENSOR_NAME);
        loadedName = sensor != null ? SENSOR_NAME : "";
    }
}
