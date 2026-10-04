package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;

import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.SHOOTER1;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.SHOOTER2;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.PIDController;
import org.firstinspires.ftc.teamcode.util.DashboardTelemetry;

@Config
public class Shooter {
    private final DashboardTelemetry tm;
    @SuppressWarnings("CanBeFinal")
    public static double kP = 0.005;
    @SuppressWarnings("CanBeFinal")
    public static double kI = 0.0;
    @SuppressWarnings("CanBeFinal")
    public static double kD = 0.0001;
    @SuppressWarnings("CanBeFinal")
    public static double kS = 0.0;
    @SuppressWarnings("CanBeFinal")
    public static double kV = 0.00051;

    public DcMotorEx getShooter1() {
        return shooter1;
    }
    public DcMotorEx getShooter2() {
        return shooter2;
    }

    private final DcMotorEx shooter1;
    private final DcMotorEx shooter2;
    private final PIDController pid1;
    private final PIDController pid2;

    public Shooter(HardwareMap hardwareMap) {
        tm = DashboardTelemetry.getInstance();
        shooter1 = hardwareMap.get(DcMotorEx.class, SHOOTER1);
        shooter2 = hardwareMap.get(DcMotorEx.class, SHOOTER2);
        shooter2.setDirection(DcMotorSimple.Direction.REVERSE);
        shooter1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        pid1 = new PIDController(kP, kI, kD, kS, kV);
        pid2 = new PIDController(kP, kI, kD, kS, kV);
        pid1.setOutputLimits(0,1);
        pid2.setOutputLimits(0,1);
        ShooterLUT.init();
    }

    public void data(Double target) {
        tm.addData("targetTicks", target);
        tm.addData("s1 velocity", shooter1.getVelocity());
        tm.addData("s2 velocity", shooter2.getVelocity());
        tm.addData("s1 current", shooter1.getCurrent(CurrentUnit.AMPS));
        tm.addData("s2 current", shooter2.getCurrent(CurrentUnit.AMPS));
    }

    public void test(Double tps) {
        applyLiveGains(tps);
        double pidOutput1 = pid1.update(shooter1.getVelocity());
        double pidOutput2 = pid2.update(shooter2.getVelocity());
        shooter1.setPower(Range.clip(pidOutput1, 0.0, 1.0));
        shooter2.setPower(Range.clip(pidOutput2, 0.0, 1.0));

        data(tps);
    }

    public void update() {
        double tps = 0;//ShooterLUT.getLut().get(Ballistics.getResult().getDistance());

        applyLiveGains(tps);
        double pidOutput1 = pid1.update(shooter1.getVelocity());
        double pidOutput2 = pid2.update(shooter2.getVelocity());
        shooter1.setPower(Range.clip(pidOutput1, 0.0, 1.0));
        shooter2.setPower(Range.clip(pidOutput2, 0.0, 1.0));

        data(tps);
    }

    private void applyLiveGains(double targetTicksPerSecond) {
        pid1.setCoefficients(kP, kI, kD, kS, kV);
        pid2.setCoefficients(kP, kI, kD, kS, kV);
        pid1.setTarget(targetTicksPerSecond);
        pid2.setTarget(targetTicksPerSecond);
    }
}
