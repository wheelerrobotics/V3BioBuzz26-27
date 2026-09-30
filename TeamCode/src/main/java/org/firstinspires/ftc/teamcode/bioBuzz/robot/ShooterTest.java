package org.firstinspires.ftc.teamcode.bioBuzz.robot;

import com.acmerobotics.dashboard.config.Config;
import org.firstinspires.ftc.teamcode.util.DashboardTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Hood;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Shooter;

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
    public static int hoodPos = 0;
    public static int hoodAngle = 10;

    Shooter shooter;
    Hood hood;

    @Override
    public void init() {
        tm = DashboardTelemetry.begin(telemetry);
        shooter = new Shooter(hardwareMap);
        hood = new Hood(hardwareMap);
    }

    @Override
    public void loop() {
        tm.addData("targetRPM JAVA", targetRPM);
        tm.addData("hoodPos JAVA", hoodPos);
        tm.addData("testMode JAVA", testMode.toString());

        switch (testMode) {
            case RUN:
                shooter.test(targetRPM);
                hood.test(hoodPos);
                break;
            case RUN_NO_HOOD:
                shooter.test(targetRPM);
            case RUN_HOOD_DEGREES:
                shooter.test(targetRPM);
                tm.addData("hoodSet", hood.testInDegreesGetPosition(hoodAngle));
                break;
            case STOPPED:
                shooter.getShooter1().setPower(0);
                shooter.getShooter2().setPower(0);
                break;
        }
        tm.update(telemetry);
    }
}
