package org.firstinspires.ftc.teamcode.bioBuzz.helpers.ballistics;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.util.InterpLUT;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib;

class BallisticsHelper {

    @SuppressWarnings("CanBeFinal")
    @Config
    public static class Constants {
        public static double flywheelDiameter = 0.072;
        public static double kDepartureLoss = 0.3141;
        public static double encoderTPR = 24;
        public static double targetY = 1.52; //fixme robot height?
        public static double gravity = 9.81;
        public static double p = 1.2; // air density
        public static double ballDiameter = 0.071;
        public static double ballMass = 0.026;
        public static double kBallSpin = 1; // F/IXME: 9/19/26 TUNE
        public static double kTargetErrorTolerance = 0.13; // meters
        public static double kTargetErrorIntegralMax = 1; // meters // FIXME: 9/19/26 TUNE

        //Spin values in rad/s
        public static double spinAt1000TPS = 0; // F/IXME: 9/19/26 TUNE
        public static double spinAt1500TPS = 1; // F/IXME: 9/19/26 TUNE
        public static double spinAt2000TPS = 2; // F/IXME: 9/19/26 TUNE
        public static double spinAt2500TPS = 3; // F/IXME: 9/19/26 TUNE
    }

    private final Follower f;
    private final Limelight3A ll;
    private final Telemetry t;
    private final DcMotorEx[] motors;
    private static final double ballRadius = Constants.ballDiameter / 2;
    private static final double A = Math.pow(ballRadius, 2) * Math.PI;
    private final InterpLUT lut = new InterpLUT();
    private static final double departureMath = 2 * Math.PI * (Constants.flywheelDiameter / 2);
    private static boolean readyToShoot = true;
    private static double lastTargetTime = 0;
    private static double targetErrorIntegral = 0;
    private static double v0 = 0;


    BallisticsHelper(Follower f, Limelight3A ll, Telemetry t, DcMotorEx... motors) {
        this.f = f;
        this.ll = ll;
        this.t = t;
        this.motors = motors;
        lut.add(-1000, Constants.spinAt1000TPS);
        lut.add(1500, Constants.spinAt1500TPS);
        lut.add(2000, Constants.spinAt2000TPS);
        lut.add(2500, Constants.spinAt2500TPS);
        lut.createLUT();
    }

    private double getMotorTPS() {
        double speeds = 0;
        for (DcMotorEx motor : motors) {
            speeds += motor.getVelocity();
        }
        return speeds / motors.length;
    }

    private static double getFlywheelSurfaceSpeed(double tps) {
        return ((departureMath * tps) / Constants.encoderTPR);
    }

    private static double getV0(double tps, @NonNull Telemetry t) {
        double v0 = getFlywheelSurfaceSpeed(tps) * Constants.kDepartureLoss;
        t.addData("getV0()", v0);
        return v0;

    }

    private double getHoodAngle(double tps, double x, Telemetry t) {
        double g = getG_eff(tps, t);

        double sqrt = Math.pow(v0, 4) - g * (g * Math.pow(x, 2) + 2 * Constants.targetY * Math.pow(v0, 2));
        if (sqrt >= 0) {
            sqrt = Math.sqrt(sqrt);
        } else {
            t.addData("getHoodAngle()", "brokie");
            return Math.toRadians(72);
        }

        double denominator = g * x == 0 ? 1 : g * x;

        double angle = Math.atan(
                (Math.pow(v0, 2) + sqrt)
                        / (denominator));

        t.addData("getHoodAngle()", angle);
        return angle;
    }

    private static double getFlightTime(double x, double hoodAngle, @NonNull Telemetry t) {
        double time = x / (v0 * Math.cos(hoodAngle));
        t.addData("getFlightTime()", time);
        return time;
    }

    private static double getV(@NonNull Telemetry t) {
        double v = Math.sqrt(Math.pow(v0, 2) - (2 * Constants.gravity * Constants.targetY));
        t.addData("getV() SPIN OFF NOT AFFECTING", v);
        return v;
    }

    private double getSpinRate(double tps, Telemetry t) {
        double S = lut.get(tps) / getV(t);
        t.addData("getSpinRate() SPIN OFF NO EFFECTS", S);
        return S;
    }

    private double getG_eff(double tps, Telemetry t) {
        double S = getSpinRate(tps, t);
        double V = getV(t);
        double g_eff = Constants.gravity - ((Constants.p *
                Constants.kBallSpin *
                S *
                A *
                Math.pow(V, 2))
                / (2 * Constants.ballMass));
        t.addData("getG_eff() SPIN OFF NOT AFFECTING ANYTHING", g_eff);
//        return g_eff; //SPIN REMOVED HERE SPIN REMOVED HERE SPIN REMOVED HERE SPIN REMOVED HERE SPIN REMOVED HERE SPIN REMOVED HERE
        return Constants.gravity;
    }

//    private static double updateTargetX(Follower f, LLResult r, Telemetry t) { // FIXME TODAY TODAY TODAY REPLACE WITH SIMPLE
//        double LLX = TargetHelper.getLLX(r, t);
//        double pinpointX = TargetHelper.getPinpointX(f, t);
//        double error = LLX - pinpointX;
//        double nowTime = System.nanoTime() / 1e9;
//        double dt = nowTime - lastTargetTime;
//        lastTargetTime = nowTime;
//
//        if (LLX > 0) { // limelight worked
//            if (error < Constants.kTargetErrorTolerance) {
//                targetErrorIntegral = 0;
//            } else {
//                targetErrorIntegral += error * dt;
//            }
//            if (targetErrorIntegral > Constants.kTargetErrorIntegralMax) {
//                RobotVars.setFollowerDisabled(true);
//            }
//
//            t.addData("targetingIntegral", targetErrorIntegral);
//
//            // FIXME: 9/20/26 ADD CALL TO TARGET SPACE CHECK
//            return LLX;
//        }
//
//        if (RobotVars.getFollowerDisabled()) {
//            readyToShoot = false;
//            return 0;
//        }
//
//        // FIXME: 9/20/26 ADD CALL TO TARGET SPACE CHECK
//        return pinpointX;
//    }

    private static double updateTargetX(Follower f, LLResult r, Telemetry t) {
//        double LLX = TargetHelper.getLLX(r, t);
//        double pinpointX = TargetHelper.getPinpointX(f, t);
//        return LLX == 0 ? pinpointX : LLX; //ts syntax is fire
        return TargetHelper.getLLX(r, t);
    }


    BallisticResult getResult() {
        LLResult r = ll.getLatestResult();
        double tps = getMotorTPS();
        v0 = getV0(tps, t);
        double distance = updateTargetX(f, r, t);

        double hoodAngle = getHoodAngle(tps, distance, t);

        double flightTime = getFlightTime(distance, hoodAngle, t);
        double turretAngle_rs = SOTM.getTurretAngle_rs(f, flightTime, t);

        return new BallisticResult(hoodAngle, turretAngle_rs, distance
//                , readyToShoot Fixme: implement
                );
    }


    private static class SOTM {
        private static double lastTimex = 0;
        @SuppressWarnings("SpellCheckingInspection")
        private static double lastTimey = 0;

        private static double lastVx = 0;
        private static double lastVy = 0;

        private SOTM() {}

        private static double getAngular(double x, double y) {
            return Math.PI - Math.atan(y / x);
        }


        private static double getXAcceleration(Follower f) {
            double vx = f.velocity().vx;

            long now = System.nanoTime();

            double dt = (now - lastTimex) / 1e9; // seconds
            double ax = (vx - lastVx) / dt;

            lastVx = vx;
            lastTimex = now;

            return ax;
        }

        private static double getYAcceleration(Follower f) {
            double vy = f.velocity().vy;

            long now = System.nanoTime();

            double dt = (now - lastTimey) / 1e9; // seconds
            double ay = (vy - lastVy) / dt;

            lastVy = vy;
            lastTimey = now;

            return ay;
        }

        private static double modelLead(double vr, double ar, double tFlight) {
            return vr * tFlight + 0.5 * (ar * Math.pow(tFlight, 2));
        }

        public static double getLead(Follower f, double flightTime, Telemetry t) {
            double vr = getAngular(f.velocity().vx, f.velocity().vy);
            double ar = getAngular(getXAcceleration(f), getYAcceleration(f));
            double lead = modelLead(vr, ar, flightTime);
            t.addData("lead angle for SOTM", lead);
            return lead;
        }

        private static double lastTurretAngle = 0; //won't need reset
        private final static double deadband = Math.toRadians(0.3);
        public static double filterTurretAngle(double targetAngle, Telemetry t) {
            double difference = targetAngle - lastTurretAngle;
            t.addData("filterTurretAngleDifference", difference);

            if (Math.abs(difference) > deadband) {
                lastTurretAngle = targetAngle; //weird pattern where last angle gets returned
                t.addData("turretFilter", "Changed");
            } else t.addData("turretFilter", "Filtered");

            t.addData("setTurretAngle", lastTurretAngle);
            return lastTurretAngle;
        }

        public static double getTurretAngle_rs(Follower f, double flightTime, Telemetry t) {
            double y = PoseLib.targetPose().y() - f.pose().y() ;
            double x = PoseLib.targetPose().x() - f.pose().x();
            double targetAngle_fs = Math.atan2(y,x);
            t.addData("TargetAngle_fs", targetAngle_fs);
            t.addData("heading", f.pose().heading());
            double targetAngle_rs = targetAngle_fs - f.pose().heading();
            // Normalize to [-pi, pi] before the turret applies its angle limits.
            targetAngle_rs = Math.atan2(
                    Math.sin(targetAngle_rs),
                    Math.cos(targetAngle_rs)
            );
            t.addData("TargetAngle_rs", targetAngle_rs);
            return filterTurretAngle(targetAngle_rs, t); // + getLead(f, flightTime, t);
        }

    }
}
