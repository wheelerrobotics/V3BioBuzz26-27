package org.firstinspires.ftc.teamcode.bioBuzz.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.SwyftCam;
import org.firstinspires.ftc.teamcode.util.DashboardTelemetry;

@TeleOp
public class SwyftCamTest extends OpMode {
    SwyftCam swyftCam;
    @Override
    public void init() {
        DashboardTelemetry.begin(telemetry);
        swyftCam = new SwyftCam(hardwareMap, "camera", DashboardTelemetry.getInstance());
    }

    @Override
    public void loop() {
        swyftCam.getBestTagPosition();
    }

}
