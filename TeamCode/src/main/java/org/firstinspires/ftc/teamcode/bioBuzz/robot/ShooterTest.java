package org.firstinspires.ftc.teamcode.bioBuzz.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Hood;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.DashboardTelemetry;

@TeleOp
@Config
public class ShooterTest extends OpMode {

    public enum TestMode {
        STOPPED,
        RUN_NO_HOOD,
        RUN,
        RUN_HOOD_DEGREES //NOT RECOMMENDED
    }

    public static TestMode testMode = TestMode.STOPPED;
    private DashboardTelemetry tm;
    public static double targetRPM = 1000;
    @SuppressWarnings("CanBeFinal")
    public static double hoodPos = 0;
    @SuppressWarnings("CanBeFinal")
    public static double hoodAngle = 10;

    Shooter shooter;
    Hood hood;

    @Override
    public void init() {
        tm = DashboardTelemetry.begin(telemetry);
        shooter = new Shooter(hardwareMap);
        hood = new Hood(hardwareMap);
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void loop() {
        telemetry.addData("targetRPM JAVA", targetRPM);
        telemetry.addData("hoodPos JAVA", hoodPos);
        telemetry.addData("testMode JAVA", testMode.toString());

        if (gamepad1.a) testMode = TestMode.RUN;
        if (gamepad1.b) testMode = TestMode.STOPPED;

        if (gamepad1.x) targetRPM = targetRPM + 10;
        if (gamepad1.y) targetRPM = targetRPM - 10;

        switch (testMode) {
            case RUN:
                shooter.test(targetRPM);
//                hood.test(hoodPos);
//                shooter.getShooter1().setPower(1);
                telemetry.addData("reached1", testMode.toString());
                return;
            case RUN_NO_HOOD:
                shooter.test(targetRPM);
//                shooter.getShooter1().setPower(1);
                return;
            case RUN_HOOD_DEGREES:
                shooter.test(targetRPM);
//                shooter.getShooter1().setPower(1);
                tm.addData("hoodSet", hood.testInDegreesGetPosition(hoodAngle));
                return;
            case STOPPED:
                shooter.getShooter1().setPower(0);
                shooter.getShooter2().setPower(0);
                break;
        }
        tm.update(telemetry);
    }
}
