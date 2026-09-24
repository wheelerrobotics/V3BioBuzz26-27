package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;

// This is made for two positional servos
@Configurable
public class Turret {
    private final Servo servo1;
    private final Servo servo2;
    private final LL limelight;
    public static boolean SERVO_1_REVERSED = false;
    public static boolean SERVO_2_REVERSED = false;

    // Servo to turret
    public static double GEAR_RATIO = 1.44;
    public static double TX_TOLERANCE_DEGREES = 1.0;
    public static double MAX_STEP_DEGREES = 3.0;
    public static double AIM_KP = 0.15;
    public static double AIM_DIRECTION = 1.0;
    public static double MAX_ANGLE_DEGREES = 360;
    public static double MIN_ANGLE_DEGREES = 0;

    public boolean trackingEnabled = false;

    public Turret(HardwareMap hardwareMap, LL limelight) {
        servo1 = hardwareMap.get(Servo.class, HardwareNames.TURRET_SERVO_1);
        servo2 = hardwareMap.get(Servo.class, HardwareNames.TURRET_SERVO_2);

        if (SERVO_1_REVERSED) servo1.setDirection(Servo.Direction.REVERSE);
        if (SERVO_2_REVERSED) servo2.setDirection(Servo.Direction.REVERSE);

        this.limelight = limelight;
    }

    public void turnTo(double angle) {
        angle = Range.clip(
                angle,
                MIN_ANGLE_DEGREES,
                MAX_ANGLE_DEGREES
        );

        double servoPos = (angle/GEAR_RATIO)/360;

        servo1.setPosition(servoPos);
        servo2.setPosition(servoPos);
    }

    public double getAngle() {
        double servoPosAvg = (servo1.getPosition()+servo2.getPosition())/2;

        return servoPosAvg*360*GEAR_RATIO;
    }

    public void startFollowingTarget() {
        trackingEnabled = true;
    }

    public void stop() {
        trackingEnabled = false;
    }

    private void followTargetTick() {
        if (!limelight.hasTarget()) return;

        double tx = limelight.getTx();

        if (Math.abs(tx) <= TX_TOLERANCE_DEGREES) {
            return;
        }

        // Calculate a small turret correction from the camera error.
        double turretCorrectionDegrees = Range.clip(
                tx * AIM_KP,
                -MAX_STEP_DEGREES,
                MAX_STEP_DEGREES
        );

        turnTo(getAngle()+turretCorrectionDegrees*AIM_DIRECTION);
    }

    public void update() {
        if (trackingEnabled) {
            followTargetTick();
        }
    }
}
