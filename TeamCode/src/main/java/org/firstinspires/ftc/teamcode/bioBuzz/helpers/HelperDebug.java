package org.firstinspires.ftc.teamcode.bioBuzz.helpers;

import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp
public class HelperDebug extends OpMode {

    Limelight3A limelight;
    Follower f;

    @Override
    public void init() {
        Alliance.set(Alliance.Color.RED);
        f = Constants.createFollower(hardwareMap);
        Scheduler.reset();
        limelight = hardwareMap.get(Limelight3A.class, HardwareNames.LIMELIGHT);
        limelight.start();
    }

    @Override
    public void loop() {
        Scheduler.execute();

        if (gamepad1.x) {
            Alliance.set(Alliance.Color.BLUE);
        } else if (gamepad1.b) {
            Alliance.set(Alliance.Color.RED);
        }

        telemetry.addData("alliance", Alliance.get());

        double v0 = BalisticsHelper.getDepartureSpeed(2000, telemetry);
        double x = LLTargetHelper.getHorizontalDistance(limelight.getLatestResult(), telemetry);
        double angle = BalisticsHelper.getHoodAngle(v0, x, telemetry);
        double time = BalisticsHelper.getFlightTime(v0, x, angle, telemetry);

        f.update();
        f.manual(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);

        SOTM.getLead(f, time, telemetry);


    }
}
