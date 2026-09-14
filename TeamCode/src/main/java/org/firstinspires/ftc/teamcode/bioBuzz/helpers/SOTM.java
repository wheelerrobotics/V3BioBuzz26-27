package org.firstinspires.ftc.teamcode.bioBuzz.helpers;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.util.ElapsedTime;

public class SOTM {
    private static ElapsedTime elapsedTime = new ElapsedTime();
    private static double lastTime = 0;
    private static double lastVx = 0;
    private static double lastVy = 0;
    private SOTM() {}

    private static double getAngular(double x, double y) {
        return 90 - Math.tan(y/x);
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

    private static double getTFlight() {
        return 1; // FIXME: 9/13/26
    }

    private static double modelLead(double vr, double ar, double tFlight) {
        return vr * tFlight + 0.5 * (ar * tFlight * tFlight);
    }

    public static double getLead(Follower f) {
        double vr = getAngular(f.velocity().vx, f.velocity().vy);
        double ar = getAngular(getXAcceleration(f), getYAcceleration(f));
        double tFlight = getTFlight();
        return modelLead(vr, ar, tFlight);
    }

}
