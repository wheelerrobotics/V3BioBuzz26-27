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
    public static final double degPosSlope = -0.0113458;
    public static final double degPosIntercept = 1.9668;
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

    public static double getPos(double degs) {
        degs = degs + 90;
        degs *= degPosSlope;
        degs += degPosIntercept;
        return  degs;
    }

    public void updatePosition() {
        double pos = getPos(Math.toDegrees(Ballistics.getResult().getHoodAngle()));
        pos = Range.clip(pos, hoodMin, hoodMax);
        hoodServo.setPosition(pos);
    }

}
