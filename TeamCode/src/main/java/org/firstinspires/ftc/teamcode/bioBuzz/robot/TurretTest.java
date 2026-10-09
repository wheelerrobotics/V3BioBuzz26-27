package org.firstinspires.ftc.teamcode.bioBuzz.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.LL;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Turret;
import org.firstinspires.ftc.teamcode.util.DashboardTelemetry;

@TeleOp
@Config
public class TurretTest extends OpMode {
    Turret turret;
    LL limelight;
    public static double setAngle_WillBeClipped; //I am sorry for the naming - Emad
    private DashboardTelemetry tm;


    @Override
    public void init() {
        tm = DashboardTelemetry.begin(telemetry);
        limelight = new LL(hardwareMap);
        turret = new Turret(hardwareMap);
    }

    @Override
    public void loop() {
        tm.update(telemetry);
        turret.turnTo(setAngle_WillBeClipped); // FIXME: 10/9/26
    }

}
