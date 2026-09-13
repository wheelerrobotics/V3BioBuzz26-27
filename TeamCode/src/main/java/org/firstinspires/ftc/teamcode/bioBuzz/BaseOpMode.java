package org.firstinspires.ftc.teamcode.bioBuzz;

import static com.pedropathing.ivy.Scheduler.execute;
import static com.pedropathing.ivy.Scheduler.reset;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.Robot;

public abstract class BaseOpMode extends OpMode {

    Robot r;

    @Override
    public void init() {
        r = new Robot(hardwareMap);
        reset();
    }

    @Override
    public void loop() {
        execute();
        r.follower.update();
    }

}
