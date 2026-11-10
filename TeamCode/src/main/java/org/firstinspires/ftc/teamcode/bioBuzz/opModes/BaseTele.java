package org.firstinspires.ftc.teamcode.bioBuzz.opModes;

import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotVars;

public abstract class BaseTele extends BaseOpMode {
    private boolean shooterToggle = true;
    @Override
    public void init() {
        super.init();
        r.follower.setPose(RobotVars.getLastPose());
        r.commands.stopItPlsInfinite.schedule();
    }

    @Override
    public void loop() {
        super.loop();

        if (gamepad1.right_bumper) r.follower.setPose(new Pose(72,72,0));

        r.follower.manual(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);

        if (gamepad2.xWasPressed()) {
            r.commands.shootCommand().schedule();
        } else if (gamepad2.xWasReleased()) {
            r.commands.shootCommand().cancel();
        }

        if (gamepad2.dpad_up) {
            if (shooterToggle) {
                r.commands.shooterUpdate().cancel();
                shooterToggle = false;
            } else {
                r.commands.shooterUpdate().schedule();
                shooterToggle = true;
            }
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

        if (gamepad2.aWasPressed()) {
            r.commands.intakeOn().schedule();
        } else if (gamepad2.aWasReleased()) {
            r.commands.intakeOn().cancel();
        }

        if (gamepad2.yWasPressed()) {
            r.commands.outtake().schedule();
        } else if (gamepad2.yWasReleased()) {
            r.commands.outtake().cancel();
        }


    }
}
