package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;

import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.HOOD;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.ballistics.Ballistics;


@Config
public final class Hood {
    private final Servo hoodServo;
    public static final double radPosSlope = -0.346053;
    public static final double radPosIntercept = 2.97328;
    @SuppressWarnings("CanBeFinal")
    public static double hoodMax = 0.24;
    @SuppressWarnings("CanBeFinal")
    public static double hoodMin = 0;
    public Hood(HardwareMap hardwareMap) {
        hoodServo = hardwareMap.get(Servo.class, HOOD);
    }

    public void test(double pos) {
        hoodServo.setPosition(pos);
    }

    public double testInDegreesGetPosition(double deg) {
        double pos = getPos(Math.toRadians(deg));
        hoodServo.setPosition(pos);
        return pos;
    }

    public static double getPos(double rads) {
        rads *= radPosSlope;
        return  rads + radPosIntercept;
    }

    public void updatePosition() {
        double pos = getPos(Ballistics.getResult().getHoodAngle());
        pos = Range.clip(pos, hoodMin, hoodMax);
        hoodServo.setPosition(pos);
    }

}
