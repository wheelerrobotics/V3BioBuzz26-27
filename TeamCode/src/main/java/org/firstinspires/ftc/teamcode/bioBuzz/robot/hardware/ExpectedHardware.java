package org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware;

import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.BACK_LEFT_DRIVE;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.BACK_RIGHT_DRIVE;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.BREAKBEAMS;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.FRONT_LEFT_DRIVE;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.FRONT_RIGHT_DRIVE;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.HOOD;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.INTAKE;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.INTAKE_SENSOR;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.IN_SWITCH;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.LEFT_SLIDE_SERVO;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.LIGHT1;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.LIGHT2;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.LIGHT3;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.LIMELIGHT;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.OUT_SWITCH;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.PINPOINT;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.RIGHT_SLIDE_SERVO;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.SHOOTER1;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.SHOOTER2;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.STOPPER;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.TRANSFER;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.TURRET_SERVO_1;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.TURRET_SERVO_2;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

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
            new HardwareDeviceSpec(HOOD,
                    Servo.class,
                    "the hood"),
            new HardwareDeviceSpec(
                    SHOOTER1,
                    DcMotorEx.class,
                    "Left shooter flywheel"
            ),
            new HardwareDeviceSpec(
                    SHOOTER2,
                    DcMotorEx.class,
                    "Right shooter flywheel"
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
            ),
            new HardwareDeviceSpec(
                    TURRET_SERVO_1,
                    Servo.class,
                    "First Turret servo"
            ),
            new HardwareDeviceSpec(
                    TURRET_SERVO_2,
                    Servo.class,
                    "Second Turret servo"
            ),
            new HardwareDeviceSpec(
                    INTAKE,
                    DcMotorEx.class,
                    "Intake motor"
            ),
            new HardwareDeviceSpec(
                    TRANSFER,
                    DcMotorEx.class,
                    "Transfer motor"
            ),
            new HardwareDeviceSpec(
                    STOPPER,
                    Servo.class,
                    "Game-piece stopper servo"
            ),
            new HardwareDeviceSpec(
                    RIGHT_SLIDE_SERVO,
                    Servo.class,
                    "Right slide servo"
            ),
            new HardwareDeviceSpec(
                    LEFT_SLIDE_SERVO,
                    Servo.class,
                    "Left slide servo"
            ),
            new HardwareDeviceSpec(
                    BREAKBEAMS,
                    AnalogInput.class,
                    "Transfer breakbeams"
            ),
            new HardwareDeviceSpec(
                    IN_SWITCH,
                    AnalogInput.class,
                    "Transfer limit switch (in)"
            ),
            new HardwareDeviceSpec(
                    OUT_SWITCH,
                    AnalogInput.class,
                    "Transfer limit switch (out)"
            ),
            new HardwareDeviceSpec(
                    INTAKE_SENSOR,
                    AnalogInput.class,
                    "Intake breakbeams"
            ),
            new HardwareDeviceSpec(
                    LIGHT1,
                    Servo.class,
                    "Light 1"
            ),
            new HardwareDeviceSpec(
                    LIGHT2,
                    Servo.class,
                    "Light 2"
            ),
            new HardwareDeviceSpec(
                    LIGHT3,
                    Servo.class,
                    "Light 3"
            )
    };
}
