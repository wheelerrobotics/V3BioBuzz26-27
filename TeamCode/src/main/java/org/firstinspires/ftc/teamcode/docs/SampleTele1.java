package org.firstinspires.ftc.teamcode.docs;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.Robot;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotConstants;

@TeleOp(name = "SampleTele1", group = "Samples")
public class SampleTele1 extends OpMode {
    private Robot robot;
    private TelemetryManager telemetryM;

    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        robot = new Robot(hardwareMap);

        robot.follower.setPose(Pose.zero());
    }

    @Override
    public void start() {
        robot.follower.manual(0, 0, 0);
    }

    @Override
    public void loop() {
        double y = -gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x;
        double r = -gamepad1.right_stick_x;

        robot.follower.manual(
                y * RobotConstants.Drive.MAX_DRIVE_POWER,
                x * RobotConstants.Drive.MAX_DRIVE_POWER,
                r * RobotConstants.Drive.MAX_DRIVE_POWER);

        robot.tick(gamepad1,gamepad2);

        Pose pose = robot.follower.pose();
        telemetryM.addData("X (in)", pose.x());
        telemetryM.addData("Y (in)", pose.y());
        telemetryM.addData("Heading (deg)", Math.toDegrees(pose.heading()));
        telemetryM.update(telemetry);
    }

    @Override
    public void stop() {
        if (robot != null) {
            robot.stop();
        }
    }
}
