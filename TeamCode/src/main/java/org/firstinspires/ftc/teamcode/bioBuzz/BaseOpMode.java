package org.firstinspires.ftc.teamcode.bioBuzz;

import static com.pedropathing.ivy.Scheduler.execute;
import static com.pedropathing.ivy.Scheduler.reset;

import org.firstinspires.ftc.teamcode.util.DashboardTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.GlobalT;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.Robot;

public abstract class BaseOpMode extends OpMode {

    public Robot r;
    DashboardTelemetry telemetryManager;

    @Override
    public void init() {
        reset();
        telemetryManager = DashboardTelemetry.begin(telemetry);
        GlobalT.set_telemetry(telemetry);
        r = new Robot(hardwareMap);
    }

    @Override
    public void start() {
        r.commands.shooterUpdate().schedule();
        r.commands.hood().schedule();
        r.commands.intake().schedule();
        r.commands.updateBallistics().schedule();
        r.commands.intakeOn().schedule();
    }

    @Override
    public void loop() {
        r.update(gamepad1, gamepad2);
        r.follower.update();
        execute();
        telemetryManager.update(telemetry);
    }

    @Override
    public void stop() {
        if (r != null) {
            r.stop();
        }
    }

}
