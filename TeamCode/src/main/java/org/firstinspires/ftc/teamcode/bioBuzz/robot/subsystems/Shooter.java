package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;

import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.SHOOTER1;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.SHOOTER2;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Balistics.Ballistics;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.PIDController;


public class Shooter {
    private final TelemetryManager tm;
    public static double kP = 0.1;
    public static double kI = 0;
    public static double kD = 0;
    private final DcMotorEx shooter1;
    private final DcMotorEx shooter2;
    private final PIDController pid1;
    private final PIDController pid2;

    public Shooter(HardwareMap hardwareMap) {
        tm = PanelsTelemetry.INSTANCE.getTelemetry();
        shooter1 = hardwareMap.get(DcMotorEx.class, SHOOTER1);
        shooter2 = hardwareMap.get(DcMotorEx.class, SHOOTER2);
        pid1 = new PIDController(kP, kI, kD);
        pid2 = new PIDController(kP, kI, kD);
        ShooterLUT.init();
    }

    public void data(Double target) {
        tm.addData("setpoint", target);
        tm.addData("s1 velocity", shooter1.getVelocity());
        tm.addData("s2 velocity", shooter2.getVelocity());
        tm.addData("s1 current", shooter1.getCurrent(CurrentUnit.AMPS));
        tm.addData("s2 current", shooter2.getCurrent(CurrentUnit.AMPS));
    }

    public void test(Double tps) {
        pid1.setCoefficients(kP, kI, kD);
        pid1.setTarget(tps);
        shooter1.setPower(pid1.update(shooter1.getVelocity()));

        pid2.setCoefficients(kP, kI, kD);
        pid2.setTarget(tps);
        shooter2.setPower(pid2.update(shooter2.getVelocity()));

        data(tps);
    }

    public void update() {
        double v = ShooterLUT.getLut().get(Ballistics.getResult().getDistance());
        pid1.setTarget(v);
        pid2.setTarget(v);
        shooter1.setPower(pid1.update(shooter1.getVelocity()));
        shooter2.setPower(pid2.update(shooter2.getVelocity()));

        data(v);
    }

}
