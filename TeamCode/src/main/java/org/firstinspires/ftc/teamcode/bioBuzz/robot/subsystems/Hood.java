package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;

import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.HOOD;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.ballistics.Ballistics;


@Configurable
public final class Hood {
    private final Servo hoodServo;
    public static double radPosRelation = 0.5; // FIXME: 9/20/26 TUNE
    public static double hoodMax = 0.1; // FIXME: 9/20/26 TUNE
    public static double hoodMin = 0; // FIXME: 9/20/26 TUNE
    public Hood(HardwareMap hardwareMap) {
        hoodServo = hardwareMap.get(Servo.class, HOOD);
    }

    public void test(double pos) {
        hoodServo.setPosition(pos);
    }

    public double testInDegreesGetPosition(double deg) {
        double pos = deg * radPosRelation;
        hoodServo.setPosition(pos);
        return pos;
    }

    public void updatePosition() {
        double pos = Ballistics.getResult().getHoodAngle() * radPosRelation;
        hoodServo.setPosition(pos);
    }

}
