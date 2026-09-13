package org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware;

import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.*;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.util.HardwareDeviceSpec;

/** The complete list of devices expected on the current robot. */
public final class ExpectedHardware {
    private ExpectedHardware() {}
    public static final HardwareDeviceSpec[] DEVICES = {
            new HardwareDeviceSpec(
                    FRONT_LEFT_DRIVE,
                    DcMotorEx.class,
                    "Front-left drivetrain motor"
            ),
            new HardwareDeviceSpec(
                    FRONT_RIGHT_DRIVE,
                    DcMotorEx.class,
                    "Front-right drivetrain motor"
            ),
            new HardwareDeviceSpec(
                    BACK_LEFT_DRIVE,
                    DcMotorEx.class,
                    "Back-left drivetrain motor"
            ),
            new HardwareDeviceSpec(
                    BACK_RIGHT_DRIVE,
                    DcMotorEx.class,
                    "Back-right drivetrain motor"
            ),
            new HardwareDeviceSpec(
                    LIMELIGHT,
                    Limelight3A.class,
                    "Vision camera"
            ),
            new HardwareDeviceSpec(
                    PINPOINT,
                    GoBildaPinpointDriver.class,
                    "The Pinpoint"
            )
    };
}
