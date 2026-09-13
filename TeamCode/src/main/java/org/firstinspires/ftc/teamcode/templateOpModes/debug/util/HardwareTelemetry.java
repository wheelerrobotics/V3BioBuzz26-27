package org.firstinspires.ftc.teamcode.templateOpModes.debug.util;

import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/** Adds identification and connection details shared by all FTC hardware devices. */
public final class HardwareTelemetry {
    public static void addDeviceInfo(
            Telemetry telemetry,
            String configName,
            HardwareDevice device
    ) {
        telemetry.addLine("Device Information");
        telemetry.addData("Config Name", configName);
        telemetry.addData("Device", device.getDeviceName());
        telemetry.addData("Manufacturer", device.getManufacturer());
        telemetry.addData("Connection", device.getConnectionInfo());
        telemetry.addData("Version", device.getVersion());
        telemetry.addLine("--------------------------------");
    }

    private HardwareTelemetry() {}
}
