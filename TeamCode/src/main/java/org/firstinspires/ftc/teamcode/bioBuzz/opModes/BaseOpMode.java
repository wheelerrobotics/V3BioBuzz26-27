package org.firstinspires.ftc.teamcode.bioBuzz.opModes;

import static com.pedropathing.ivy.Scheduler.execute;
import static com.pedropathing.ivy.Scheduler.reset;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.GlobalT;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.Robot;
import org.firstinspires.ftc.teamcode.util.DashboardTelemetry;

public abstract class BaseOpMode extends OpMode {

    public Robot r;
    DashboardTelemetry telemetryManager;

    @Override
    public void init() {
        reset();
        telemetryManager = DashboardTelemetry.begin(telemetry);
        GlobalT.set_telemetry(telemetry);
        r = new Robot(hardwareMap, gamepad1, gamepad2);
    }

    @Override
    public void start() {
        r.commands.shooterUpdate().schedule();
        r.commands.hood().schedule();
        //r.commands.intake().schedule();
        r.commands.updateBallistics().schedule();
    }

    @Override
    public void loop() {
        r.follower.update();
        r.limelight.update();
        r.turret.update();
        execute(); // ivy
        telemetryManager.update(telemetry);
        r.lights.loop();
        PoseLib.update(r.follower);
        telemetryManager.update(telemetry);
    }

    @Override
    public void stop() {
        r.follower.stop();
        r.limelight.stop();
    }

}
