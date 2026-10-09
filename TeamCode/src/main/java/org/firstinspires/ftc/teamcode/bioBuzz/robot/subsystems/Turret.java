package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.ballistics.Ballistics;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;

// This is made for two positional servos
@Config
public class Turret {
    private final Servo servo1;
    private final Servo servo2;
    public static final Servo.Direction SERVO_1_DIRECTION = Servo.Direction.REVERSE;
    public static final Servo.Direction SERVO_2_DIRECTION = Servo.Direction.REVERSE;


    // Servo to turret
    public static final double servoRatio = 360;
    public static final double maxAngleDegrees = 360;
    public static final double minAngleDegrees = 0;
    public Turret(HardwareMap hardwareMap) {
        servo1 = hardwareMap.get(Servo.class, HardwareNames.TURRET_SERVO_1);
        servo2 = hardwareMap.get(Servo.class, HardwareNames.TURRET_SERVO_2);

        servo1.setDirection(SERVO_1_DIRECTION);
        servo2.setDirection(SERVO_2_DIRECTION);

    }

    public void turnTo(double angle) {
        angle = Range.clip(
                angle,
                minAngleDegrees,
                maxAngleDegrees
        );

        double servoPos = (angle * servoRatio);

        servo1.setPosition(servoPos);
        servo2.setPosition(servoPos);
    }

    public void update() {
        turnTo(Ballistics.getResult().getTurretAngleRS());
    }

}
