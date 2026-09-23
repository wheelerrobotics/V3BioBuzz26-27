package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;

@Configurable
public class Turret {
    private Servo servo1;
    private Servo servo2;
    private LL limelight;
    public static boolean servo1_isReversed = false;
    public static boolean servo2_isReversed = false;

    // Servo to turret
    public static double GEAR_RATIO = 1.44;
    public static double TX_TOLERANCE_DEGREES = 1.0;
    public static double MAX_STEP_DEGREES = 3.0;
    public static double AIM_KP = 0.15;

    private Turret(HardwareMap hardwareMap, LL LL) {
        servo1 = hardwareMap.get(Servo.class, HardwareNames.TURRET_SERVO_1);
        servo2 = hardwareMap.get(Servo.class, HardwareNames.TURRET_SERVO_2);

        limelight = LL;
    }

    public void turnTo(double angle) {
        if (angle > 400 || angle < 0) return;

        double servoPos = (angle/GEAR_RATIO)/360;

        if (servo1_isReversed) {
            servo1.setPosition(1-servoPos);
        }
        if (servo2_isReversed) {
            servo2.setPosition(1-servoPos);
        }
    }

    public void followTarget() {
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

        turnTo(turretCorrectionDegrees);
    }
}
