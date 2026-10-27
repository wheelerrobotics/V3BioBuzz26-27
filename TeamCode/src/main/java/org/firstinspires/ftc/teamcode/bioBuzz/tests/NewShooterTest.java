package org.firstinspires.ftc.teamcode.bioBuzz.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Hood;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.util.DashboardTelemetry;

@TeleOp
@Config
public class NewShooterTest extends OpMode {
    Shooter shooter;
    Transfer transfer;
    Intake intake;
    Hood hood;

    public static double shooterTPS = 0;
    public static double hoodPos = 0;
    private boolean lastA = false;

    @Override
    public void init() {
        shooter = new Shooter(hardwareMap);
        transfer = new Transfer(hardwareMap);
        intake = new Intake(hardwareMap);
        hood = new Hood(hardwareMap);
        DashboardTelemetry.begin(telemetry);
    }

    @Override
    public void loop() {

        //Transfer gamepad logic
        if (gamepad1.a && !lastA) {
            transfer.setTransferPower(1);
            intake.setIntakePower(1);
            lastA = true;
        } else if (!gamepad1.a && lastA) {
            transfer.setTransferPower(0);
            intake.setIntakePower(0);
            lastA = false;
        }

        shooter.test(shooterTPS); //run shooter
        hood.test(hoodPos); //run hood

        DashboardTelemetry.getInstance().update(telemetry); // weird telemetry update
    }

}
