package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.robot.hardware.HardwareNames;

/** Owns the Limelight camera and exposes vision data to the rest of the robot. */
public class LimelightController {
    private static final int DEFAULT_PIPELINE = 0;
    private static final int POLL_RATE_HZ = 100;
    private static final double METERS_TO_INCHES = 39.3701;

    private final Limelight3A limelight;
    private LLResult result;

    public LimelightController(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, HardwareNames.LIMELIGHT);
        limelight.setPollRateHz(POLL_RATE_HZ);
        limelight.pipelineSwitch(DEFAULT_PIPELINE);
        limelight.start();
    }

    public void update() {
        result = limelight.getLatestResult();
    }

    public boolean hasTarget() {
        return result != null && result.isValid();
    }

    public double getTx() {
        return hasTarget() ? result.getTx() : 0;
    }

    public double getTy() {
        return hasTarget() ? result.getTy() : 0;
    }

    public double getDistanceMeters() {
        return hasTarget() ? result.getBotposeAvgDist() : 0;
    }

    public double getDistanceInches() {
        return getDistanceMeters() * METERS_TO_INCHES;
    }

    public void setPipeline(int pipeline) {
        limelight.pipelineSwitch(pipeline);
    }

    public void start() {
        limelight.start();
    }

    public void stop() {
        limelight.stop();
    }
    public Limelight3A getLimelight() {
        return limelight;
    }

    public LLResult getResult() {
        return result;
    }
}
