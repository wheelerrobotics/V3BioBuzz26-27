package org.firstinspires.ftc.teamcode.bioBuzz.helpers.ballistics;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.util.InterpLUT;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotVars;

import java.util.HashSet;
import java.util.List;

public class BallisticsHelper {

    @Configurable
    public static class Constants {
        public static double flywheelDiameter = 5; // FIXME: 9/19/26 SET
        public static double kDeparture = -0.01; // FIXME: 9/19/26 TUNE
        public static double encoderTPR = 24;
        public static double targetY = 86;
        public static double gravity = 9.81;
        public static double p = 1.2; // air density
        public static double ballDiameter = 0.071;
        public static double ballMass = 0.071;
        public static double kBallSpin = 1; // FIXME: 9/19/26 TUNE
        public static double kTargetErrorTolerance = 0.13; // meters
        public static double kTargetErrorIntegralMax = 9999999; // meters // FIXME: 9/19/26 TUNE

        //Spin values in rad/s
        public static double spinAt1000TPS = 0;
        public static double spinAt1500TPS = 0;
        public static double spinAt2000TPS = 0;
        public static double spinAt2500TPS = 0;
    }

    private final Follower f;
    private final Limelight3A ll;
    private final Telemetry t;
    private final DcMotorEx[] motors;
    private static final double ballRadius = Constants.ballDiameter / 2;
    private static final double A = Math.pow(ballRadius, 2) * Math.PI;
    private static final InterpLUT lut = new InterpLUT();
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
        lut.add(1000, Constants.spinAt1000TPS);
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
        return (departureMath * tps) / 60 * Constants.encoderTPR;
    }

    private static double getV0(double tps, Telemetry t) {
        double v0 = getFlywheelSurfaceSpeed(tps) * Constants.kDeparture;
        t.addData("getV0()", v0);
        return v0;

    }

    private static double getHoodAngle(double tps, double x, Telemetry t) {
        double g = getG_eff(tps, t);

        double angle = Math.atan(
                (Math.pow(v0, 2) + Math.sqrt(Math.pow(v0, 4) - g * (g * Math.pow(x, 2) + 2 * Constants.targetY * Math.pow(v0, 2))))
                        / (g * x));

        t.addData("getHoodAngle()", angle);
        return angle;
    }

    private static double getFlightTime(double x, double hoodAngle, Telemetry t) {
        double time = x / (v0 * Math.cos(hoodAngle));
        t.addData("getFlightTime()", time);
        return time;
    }

    private static double getV(Telemetry t) {
        double v = Math.sqrt(Math.pow(v0, 2) - (2 * Constants.gravity * Constants.targetY));
        t.addData("getV()", v);
        return v;
    }

    private static double getSpinRate(double tps, Telemetry t) {
        double S = lut.get(tps) / getV(t);
        t.addData("getSpinRate()", S);
        return S;
    }

    private static double getG_eff(double tps, Telemetry t) {
        double S = getSpinRate(tps, t);
        double V = getV(t);
        double g_eff = Constants.gravity - ((Constants.p *
                Constants.kBallSpin *
                S *
                A *
                Math.pow(V, 2))
                / (2 * Constants.ballMass));
        t.addData("getG_eff()", g_eff);
        return g_eff;
    }

    private static double updateTargetX(Follower f, LLResult r, Telemetry t) {
        double LLX = TargetHelper.getLLX(r, t);
        double pinpointX = TargetHelper.getPinpointX(f, t);
        double error = LLX - pinpointX;
        double nowTime = System.nanoTime();
        double dt = nowTime - lastTargetTime;
        lastTargetTime = nowTime;

        if (LLX > 0) { // limelight worked
            if (error < Constants.kTargetErrorTolerance) {
                targetErrorIntegral = 0;
            } else {
                targetErrorIntegral += error * dt;
            }
            if (targetErrorIntegral > Constants.kTargetErrorIntegralMax) {
                RobotVars.setFollowerDisabled(true);
            }

            // FIXME: 9/20/26 ADD CALL TO TARGET SPACE CHECK
            return LLX;
        }

        if (RobotVars.getFollowerDisabled()) {
            readyToShoot = false;
            return 0;
        }

        // FIXME: 9/20/26 ADD CALL TO TARGET SPACE CHECK
        return pinpointX;
    }


    BallisticResult getResult() {
        LLResult r = ll.getLatestResult();
        double tps = getMotorTPS();
        v0 = getV0(tps, t);
        double distance = updateTargetX(f, r, t);

        double hoodAngle = getHoodAngle(tps, distance, t);

        double flightTime = getFlightTime(distance, hoodAngle, t);
        double sotmOffset = SOTM.getLead(f, flightTime, t);

        return new BallisticResult(hoodAngle, sotmOffset, distance, readyToShoot);
    }


    private static class TargetHelper {

        private static HashSet<Integer> getCurrentIDs() {
            HashSet<Integer> list = new HashSet<>();
            if (Alliance.get() == Alliance.Color.RED) {
                if (PoseLib.getActiveCell() == PoseLib.Cell.NECTAR) {
                    list.add(34);
                    list.add(35);
                    list.add(36);
                    list.add(37);
                } else {
                    list.add(30);
                    list.add(31);
                    list.add(32);
                    list.add(33);
                }
            } else {
                if (PoseLib.getActiveCell() == PoseLib.Cell.NECTAR) {
                    list.add(42);
                    list.add(43);
                    list.add(44);
                    list.add(45);
                } else {
                    list.add(38);
                    list.add(39);
                    list.add(40);
                    list.add(41);
                }
            }

            return list;

        }

        private static LLResultTypes.FiducialResult getIdealResult(LLResult r) {
            List<LLResultTypes.FiducialResult> results = r.getFiducialResults();
            LLResultTypes.FiducialResult idealResult = null;
            HashSet<Integer> IDs = getCurrentIDs();
            double lastArea = 0;
            for (LLResultTypes.FiducialResult result : results) {
                if (IDs.contains(result.getFiducialId())) {
                    if (result.getTargetArea() > lastArea) { //pick the result of the most visible tag
                        lastArea = result.getTargetArea();
                        idealResult = result;
                    }
                }

            }
            return idealResult;
        }

        static double getLLX(LLResult r, Telemetry t) {
            if (!r.isValid()) {
                t.addData("distance", "not valid 65");
                return 0; // returns 0 if things don't work out
            }

            LLResultTypes.FiducialResult idealResult = getIdealResult(r);

            if (idealResult == null) {
                t.addData("distance", "null 72");
                return 0; // returns 0 if things don't work out
            }

            Pose3D tPose = idealResult.getTargetPoseRobotSpace(); //Point-of-Interest Tracking for tag offset
            double distance = tPose.getPosition().x * -0.1; // convert to meters
            t.addData("distance", distance);
            return distance;

        }

        static double getPinpointX(Follower f, Telemetry t) {
            double x = f.pose().distance(PoseLib.targetPose()) * 0.0254; // convert to meters
            t.addData("getPinpointX()", x);
            return x;
        }


    }

    private static class SOTM {
        private static double lastTime = 0;
        private static double lastVx = 0;
        private static double lastVy = 0;

        private SOTM() {
        }

        private static double getAngular(double x, double y) {
            return 90 - Math.tan(y / x);
        }


        private static double getXAcceleration(Follower f) {
            double vx = f.velocity().vx;

            long now = System.nanoTime();

            double dt = (now - lastTime) / 1e9; // seconds
            double ax = (vx - lastVx) / dt;

            lastVx = vx;
            lastTime = now;

            return ax;
        }

        private static double getYAcceleration(Follower f) {
            double vy = f.velocity().vy;

            long now = System.nanoTime();

            double dt = (now - lastTime) / 1e9; // seconds
            double ay = (vy - lastVy) / dt;

            lastVy = vy;
            lastTime = now;

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

    }
}
