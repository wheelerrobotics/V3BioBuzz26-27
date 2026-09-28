package org.firstinspires.ftc.teamcode.bioBuzz;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotVars;

public abstract class BaseAuto extends BaseOpMode {
    @Override
    public void init() {
        super.init();
    }

    @Override
    public void loop() {
        RobotVars.setLastPose(r.follower.pose());
        super.loop();
    }
}
