package org.firstinspires.ftc.teamcode.bioBuzz;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotVars;

public abstract class BaseTele extends BaseOpMode {

    boolean lastA = false;
    boolean lastB = false;
    @Override
    public void init() {
        super.init();
        r.follower.setPose(RobotVars.getLastPose());
    }

    @Override
    public void loop() {
        super.loop();
        r.follower.manual(-gamepad1.left_stick_x, -gamepad1.left_stick_y, -gamepad1.right_stick_x);

        if (gamepad1.a && !lastA) {
            r.commands.shootCommand().schedule();
            lastA = true;
        } else if (!gamepad1.a && lastA) {
            r.commands.shootCommand().cancel();
            lastA = false;
        }

        if (!gamepad1.b) lastB = false;
        else if (!lastB) {
            if (r.commands.extendCommand().isScheduled()) {
                r.commands.extendCommand().cancel();
            } else {
                r.commands.extendCommand().schedule();
            }
            lastB = true;
        }


    }
}
