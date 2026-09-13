package org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.util;

import com.qualcomm.robotcore.hardware.HardwareDevice;

/** Describes one device that is expected in the Robot Controller configuration. */
public final class HardwareDeviceSpec {
    private final String configName;
    private final Class<? extends HardwareDevice> deviceType;
    private final String purpose;

    public HardwareDeviceSpec(
            String configName,
            Class<? extends HardwareDevice> deviceType,
            String purpose
    ) {
        this.configName = configName;
        this.deviceType = deviceType;
        this.purpose = purpose;
    }

    public String getConfigName() {
        return configName;
    }

    public Class<? extends HardwareDevice> getDeviceType() {
        return deviceType;
    }

    public String getPurpose() {
        return purpose;
    }
}
