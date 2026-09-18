package org.firstinspires.ftc.teamcode.templateOpModes.tele;

import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp
public class V3EmadDrive extends OpMode {

    Follower f;

    @Override
    public void init() {
        f = Constants.createFollower(hardwareMap);
        Scheduler.reset();
    }

    @Override
    public void loop() {
        f.update();
        f.manual(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);
    }

}
