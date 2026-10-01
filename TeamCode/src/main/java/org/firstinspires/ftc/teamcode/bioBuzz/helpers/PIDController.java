package org.firstinspires.ftc.teamcode.bioBuzz.helpers;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * A reusable PID calculator for position, velocity, heading, distance, or any
 * other closed-loop control problem.
 *
 * This class does not control hardware directly. Call {@link #update(double)}
 * once per OpMode loop and apply the returned output in the owning subsystem.
 */
@SuppressWarnings("ALL")
public class PIDController {
    private double kP;
    private double kI;
    private double kD;
    private double kS;
    private double kV;

    private double target;
    private double integral;
    private double lastError;
    private double error;
    private double derivative;
    private double output;

    private double minOutput = -1.0;
    private double maxOutput = 1.0;
    private double maxIntegral = Double.POSITIVE_INFINITY;
    private double tolerance;

    private boolean firstUpdate = true;
    private boolean hasMeasurement;

    private final ElapsedTime timer = new ElapsedTime();

    public PIDController(double kP, double kI, double kD) {
        this(kP, kI, kD, 0.0, 0.0);
    }

    /** Creates a PID controller with static-friction and velocity feedforward. */
    public PIDController(double kP, double kI, double kD, double kS, double kV) {
        setCoefficients(kP, kI, kD, kS, kV);
        reset();
    }

    /** Backward-compatible overload: the fourth value is static-friction feedforward. */
    public PIDController(double kP, double kI, double kD, double kS) {
        this(kP, kI, kD, kS, 0.0);
    }

    /** Calculates and returns the next limited controller output. */
    public double update(double measurement) {
        double deltaTime = timer.seconds();
        timer.reset();

        error = target - measurement;
        hasMeasurement = true;

        double nextIntegral = integral;
        if (firstUpdate || deltaTime <= 0.0 || deltaTime > 0.5) {
            derivative = 0.0;
            firstUpdate = false;
        } else {
            nextIntegral = Range.clip(
                    integral + error * deltaTime,
                    -maxIntegral,
                    maxIntegral);
            derivative = (error - lastError) / deltaTime;
        }

        double feedforward = target == 0.0
                ? 0.0
                : Math.copySign(kS, target) + kV * target;

        // Do not keep integrating while the output is saturated in the same
        // direction as the error. This prevents the integral term from
        // winding up and causing a long overshoot after the target changes.
        double candidateOutput = kP * error
                + kI * nextIntegral
                + kD * derivative
                + feedforward;
        boolean saturatedHigh = candidateOutput > maxOutput && error > 0.0;
        boolean saturatedLow = candidateOutput < minOutput && error < 0.0;
        if (!saturatedHigh && !saturatedLow) {
            integral = nextIntegral;
        }

        lastError = error;
        output = kP * error + kI * integral + kD * derivative + feedforward;
        output = Range.clip(output, minOutput, maxOutput);
        return output;
    }

    /** Changes the target and clears accumulated controller state. */
    public void setTarget(double target) {
        if (Double.compare(this.target, target) != 0) {
            this.target = target;
            resetState();
        }
    }

    /** Changes PID gains without discarding the current target or state. */
    public void setCoefficients(double kP, double kI, double kD, double kS) {
        setCoefficients(kP, kI, kD, kS, this.kV);
    }

    /** Changes PID and feedforward gains without discarding controller state. */
    public void setCoefficients(double kP, double kI, double kD, double kS, double kV) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kS = kS;
        this.kV = kV;
    }

    public void setOutputLimits(double minOutput, double maxOutput) {
        if (minOutput > maxOutput) {
            throw new IllegalArgumentException(
                    "Minimum output cannot exceed maximum output");
        }

        this.minOutput = minOutput;
        this.maxOutput = maxOutput;
        output = Range.clip(output, minOutput, maxOutput);
    }

    /** Limits integral windup to plus or minus the supplied magnitude. */
    public void setMaxIntegral(double maxIntegral) {
        this.maxIntegral = Math.abs(maxIntegral);
        integral = Range.clip(integral, -this.maxIntegral, this.maxIntegral);
    }

    public void setTolerance(double tolerance) {
        this.tolerance = Math.abs(tolerance);
    }

    /** Returns true only after an update and while error is within tolerance. */
    public boolean isAtTarget() {
        return hasMeasurement && Math.abs(error) <= tolerance;
    }

    /** Clears PID state while preserving the target, gains, and limits. */
    public void reset() {
        resetState();
    }

    private void resetState() {
        integral = 0.0;
        lastError = 0.0;
        error = 0.0;
        derivative = 0.0;
        output = 0.0;
        firstUpdate = true;
        hasMeasurement = false;
        timer.reset();
    }

    public double getKP() {
        return kP;
    }

    public double getKI() {
        return kI;
    }

    public double getKD() {
        return kD;
    }

    public double getKS() {
        return kS;
    }

    public double getKV() {
        return kV;
    }

    public double getTarget() {
        return target;
    }

    public double getError() {
        return error;
    }

    public double getIntegral() {
        return integral;
    }

    public double getDerivative() {
        return derivative;
    }

    public double getOutput() {
        return output;
    }
}
