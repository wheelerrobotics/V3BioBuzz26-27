package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;

@Config
public class Slides {
    private final Servo LEFT_SERVO;
    private final Servo RIGHT_SERVO;
    public static final Servo.Direction SERVO_LEFT_DIRECTION = Servo.Direction.REVERSE;
    public static final Servo.Direction SERVO_RIGHT_DIRECTION = Servo.Direction.REVERSE;
    public static final double MIN_POSITION = 0;
    @SuppressWarnings("SpellCheckingInspection")
    public static final double MAX_POSTION = 1;
    public Slides(HardwareMap hardwareMap) {
        LEFT_SERVO = hardwareMap.get(Servo.class, HardwareNames.LEFT_SLIDE_SERVO);
        RIGHT_SERVO = hardwareMap.get(Servo.class, HardwareNames.RIGHT_SLIDE_SERVO);

        LEFT_SERVO.setDirection(SERVO_LEFT_DIRECTION);
        RIGHT_SERVO.setDirection(SERVO_RIGHT_DIRECTION);
    }

    public void extendTo(double pos) {
        pos = Range.clip(
                pos,
                MIN_POSITION,
                MAX_POSTION
        );

        LEFT_SERVO.setPosition(pos);
        RIGHT_SERVO.setPosition(pos);
    }

    public void extendMax() {
        LEFT_SERVO.setPosition(MAX_POSTION);
        RIGHT_SERVO.setPosition(MAX_POSTION);
    }

    public void extendMin() {
        LEFT_SERVO.setPosition(MIN_POSITION);
        RIGHT_SERVO.setPosition(MIN_POSITION);
    }
}
