package org.firstinspires.ftc.teamcode.bioBuzz.opModes;

import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotVars;

public abstract class BaseTele extends BaseOpMode {
    @Override
    public void init() {
        super.init();
        r.follower.setPose(RobotVars.getLastPose());
    }

    @Override
    public void loop() {
        super.loop();

        if (gamepad1.right_bumper) r.follower.setPose(new Pose(72,72,0));

        r.follower.manual(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);

        if (gamepad1.aWasPressed()) {
            r.commands.shootCommand().schedule();
        } else if (gamepad1.aWasReleased()) {
            r.commands.shootCommand().cancel();
        }

//        if (!gamepad1.b) lastB = false;
//        else if (!lastB) {
//            if (r.commands.extendCommand().isScheduled()) {
//                r.commands.extendCommand().cancel();
//            } else {
//                r.commands.extendCommand().schedule();
//            }
//            lastB = true;
//        }

        if (gamepad1.bWasPressed()) {
            r.commands.intakeOn().schedule();
        } else if (gamepad1.bWasReleased()) {
            r.commands.intakeOn().cancel();
        }


    }
}
