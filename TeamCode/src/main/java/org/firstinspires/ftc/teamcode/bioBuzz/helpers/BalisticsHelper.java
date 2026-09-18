package org.firstinspires.ftc.teamcode.bioBuzz.helpers;

import com.bylazar.configurables.annotations.Configurable;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BalisticsHelper {

    private static final double departureMath = 2 * Math.PI * (Constants.flywheelDiameter/2);

    private BalisticsHelper() {}

    @Configurable
    public static class Constants {
        public static double flywheelDiameter = 5;
        public static double kDeparture = - 0.01;
        public static double encoderTPR = 24;
        public static double targetY = 86;
        public static double gravity = 9.81;

    }

    private static double getFlywheelSurfaceSpeed(double tps) {
        return (departureMath * tps) / 60 * Constants.encoderTPR;
    }

    public static double getDepartureSpeed(double tps, Telemetry t) {
        double v0 = getFlywheelSurfaceSpeed(tps) * Constants.kDeparture;
        t.addData("departure speed", v0);
        return v0;

    }

    public static double getHoodAngle(double v0, double x, Telemetry t) {
        double g = Constants.gravity;

        double angle = Math.atan(
                (Math.pow(v0, 2) + Math.sqrt(Math.pow(v0, 4) - g * (g * Math.pow(x, 2) + 2 * Constants.targetY * Math.pow(v0, 2))))
                        / (g * x));

        t.addData("angle", angle);
        return angle;
    }

    public static double getFlightTime(double v0, double x, double hoodAngle, Telemetry t) {
        double time = x / (v0 * Math.cos(hoodAngle));
        t.addData("flight time", time);
        return time;
    }


}
