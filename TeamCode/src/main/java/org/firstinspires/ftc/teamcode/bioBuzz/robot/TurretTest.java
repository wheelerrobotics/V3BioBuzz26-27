package org.firstinspires.ftc.teamcode.bioBuzz.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.LL;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Turret;
import org.firstinspires.ftc.teamcode.util.DashboardTelemetry;

@TeleOp
public class TurretTest extends OpMode {
    Turret turret;
    LL limelight;
    DashboardTelemetry tm;


    @Override
    public void init() {
        tm = DashboardTelemetry.begin(telemetry);
        limelight = new LL(hardwareMap);
        turret = new Turret(hardwareMap, limelight);
    }

    @Override
    public void loop() {
        telemetry.addData("angle: ", turret.getAngle());
        telemetry.update();

        turret.startFollowingTarget();
        turret.update();
    }

}
