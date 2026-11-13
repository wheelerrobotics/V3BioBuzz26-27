package org.firstinspires.ftc.teamcode.bioBuzz.opModes.baseOpmodes;

import static com.pedropathing.ivy.Scheduler.schedule;

import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotVars;

public abstract class BaseAuto extends BaseOpMode {
    protected abstract Pose getStartingPose();

    protected abstract Command buildRoutine();

    @Override
    public void init() {
        super.init();
        r.follower.setPose(getStartingPose());
        RobotVars.setLastPose(r.follower.pose());
        schedule(buildRoutine());
    }

    @Override
    public void loop() {
        super.loop();
        // Save after the follower updates so TeleOp receives the latest pose.
        RobotVars.setLastPose(r.follower.pose());
    }

    @Override
    public void stop() {
        RobotVars.setLastPose(r.follower.pose());
        super.stop();
    }
}
