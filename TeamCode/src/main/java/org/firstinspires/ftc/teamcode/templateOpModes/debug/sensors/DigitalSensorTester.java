package org.firstinspires.ftc.teamcode.templateOpModes.debug.sensors;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.templateOpModes.debug.util.HardwareTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/** Displays live values from a digital channel or touch sensor. */
@Configurable
@TeleOp(name = "Digital Sensor Tester", group = "Debug/Sensors")
public class DigitalSensorTester extends OpMode {
    private TelemetryManager telemetryM;

    public static String SENSOR_NAME = "digitalSensor";
    public static boolean ACTIVE_LOW = true;

    private DigitalChannel digitalChannel;
    private TouchSensor touchSensor;
    private String loadedName = "";

    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.addLine("Enter a digital or touch sensor name in Panels");
        telemetryM.update(telemetry);
    }

    @Override
    public void loop() {
        loadSensorIfNeeded();

        HardwareDevice device = digitalChannel != null ? digitalChannel : touchSensor;
        if (device == null) {
            telemetryM.addData("Sensor Error", "Could not find: " + SENSOR_NAME);
            telemetryM.update(telemetry);
            return;
        }

        HardwareTelemetry.addDeviceInfo(telemetry, loadedName, device);
        telemetryM.addLine("Digital Readings");

        if (digitalChannel != null) {
            boolean rawState = digitalChannel.getState();
            telemetryM.addData("Mode", digitalChannel.getMode());
            telemetryM.addData("Raw State", rawState);
            telemetryM.addData("Active Low", ACTIVE_LOW);
            telemetryM.addData("Active", ACTIVE_LOW ? !rawState : rawState);
        } else {
            telemetryM.addData("Pressed", touchSensor.isPressed());
            telemetryM.addData("Value", touchSensor.getValue());
        }

        telemetryM.update(telemetry);
    }

    private void loadSensorIfNeeded() {
        if (loadedName.equals(SENSOR_NAME) && (digitalChannel != null || touchSensor != null)) {
            return;
        }

        digitalChannel = hardwareMap.tryGet(DigitalChannel.class, SENSOR_NAME);
        touchSensor = digitalChannel == null
                ? hardwareMap.tryGet(TouchSensor.class, SENSOR_NAME)
                : null;
        loadedName = digitalChannel != null || touchSensor != null ? SENSOR_NAME : "";
    }
}
