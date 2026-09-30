package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;

import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.SHOOTER1;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.SHOOTER2;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.controllers.Controller;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.util.DashboardTelemetry;

@Config
public class Shooter {
    private final DashboardTelemetry tm;
    public static final double kP = 0.1;
    public static final double kI = 0;
    public static final double kD = 0;

    public DcMotorEx getShooter1() {
        return shooter1;
    }
    public DcMotorEx getShooter2() {
        return shooter2;
    }

    private final DcMotorEx shooter1;
    private final DcMotorEx shooter2;
    private final Controller pid1;
    private final Controller pid2;

    public Shooter(HardwareMap hardwareMap) {
        tm = DashboardTelemetry.getInstance();
        shooter1 = hardwareMap.get(DcMotorEx.class, SHOOTER1);
        shooter2 = hardwareMap.get(DcMotorEx.class, SHOOTER2);
        shooter2.setDirection(DcMotorSimple.Direction.REVERSE);
        pid1 = Controller.pid(kP, kI, kD);
        pid2 = Controller.pid(kP, kI, kD);
        ShooterLUT.init();
    }

    public void data(Double target) {
        tm.addData("targetTicks", target);
        tm.addData("s1 velocity", shooter1.getVelocity());
        tm.addData("s2 velocity", shooter2.getVelocity());
        tm.addData("s1 current", shooter1.getCurrent(CurrentUnit.AMPS));
        tm.addData("s2 current", shooter2.getCurrent(CurrentUnit.AMPS));
    }

    private double lastTime = 0;
    public void test(Double tps) {
        double dt = 0 - lastTime;
        lastTime = System.nanoTime();
        shooter1.setPower(pid1.calculate(tps, (tps - shooter1.getVelocity()), dt));
        shooter2.setPower(pid2.calculate(tps, (tps - shooter2.getVelocity()), dt));

        data(tps);
    }

    public void update() {
        double tps = 0;//ShooterLUT.getLut().get(Ballistics.getResult().getDistance());

        double dt = 0 - lastTime;
        lastTime = System.nanoTime();

        shooter1.setPower(pid1.calculate(tps, (tps - shooter1.getVelocity()), dt));
        shooter2.setPower(pid2.calculate(tps, (tps - shooter2.getVelocity()), dt));

        data(tps);
    }

}
