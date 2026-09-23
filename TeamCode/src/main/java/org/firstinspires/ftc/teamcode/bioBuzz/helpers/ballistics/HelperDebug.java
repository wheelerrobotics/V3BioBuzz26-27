package org.firstinspires.ftc.teamcode.bioBuzz.helpers.ballistics;

import static com.pedropathing.ivy.Scheduler.execute;
import static com.pedropathing.ivy.Scheduler.reset;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.ExpectedHardware;
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
        reset();
        limelight = hardwareMap.get(Limelight3A.class, HardwareNames.LIMELIGHT);
        limelight.start();
        DcMotorEx fakeShooter = hardwareMap.get(DcMotorEx.class, ExpectedHardware.DEVICES[1].getConfigName());
        Ballistics.init(f, limelight, telemetry, fakeShooter);
    }

    @Override
    public void loop() {
        execute();

        if (gamepad1.x) {
            Alliance.set(Alliance.Color.BLUE);
        } else if (gamepad1.b) {
            Alliance.set(Alliance.Color.RED);
        }

        if (gamepad1.right_bumper) {
            PoseLib.setActiveCell(PoseLib.Cell.NECTAR);
        } else if (gamepad1.left_bumper) {
            PoseLib.setActiveCell(PoseLib.Cell.EMPTY);
        }

        telemetry.addData("target", PoseLib.getActiveCell());
        telemetry.addData("alliance", Alliance.get());

        Ballistics.update();

        Ballistics.getResult();

        f.update();
        f.manual(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);

    }
}
