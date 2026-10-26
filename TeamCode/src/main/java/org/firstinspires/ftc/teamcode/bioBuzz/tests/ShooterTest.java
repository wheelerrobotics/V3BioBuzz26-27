package org.firstinspires.ftc.teamcode.bioBuzz.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Hood;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Transfer;
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
    public static int testSwitch = 0;
    private Intake intake;

    boolean lastA = false;
    Shooter shooter;
    Hood hood;
    Transfer transfer;

    @Override
    public void init() {
        tm = DashboardTelemetry.begin(telemetry);
        transfer = new Transfer(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        hood = new Hood(hardwareMap);
    }

    @Override
    public void loop() {
        telemetry.addData("targetRPM JAVA", targetRPM);
        telemetry.addData("hoodPos JAVA", hoodPos);
        telemetry.addData("testMode JAVA", testMode.toString());


        if (gamepad1.x) targetRPM = targetRPM + 10;
        if (gamepad1.y) targetRPM = targetRPM - 10;

        if (gamepad1.a && !lastA) {
            transfer.setTransferPower(1);
            intake.setIntakePower(1);
            lastA = true;
        } else if (!gamepad1.a && lastA) {
            transfer.setTransferPower(0);
            intake.setIntakePower(0);
            lastA = false;
        }

        switch (testSwitch) {
            case 1:
                testMode = TestMode.RUN;
                break;
            case 0:
                testMode = TestMode.STOPPED;
                break;
            case 2:
                testMode = TestMode.RUN_HOOD_DEGREES;
                break;
            case 3:
                testMode = TestMode.RUN_NO_HOOD;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + testSwitch);
        }


        switch (testMode) {
            case RUN:
                shooter.test(targetRPM);
                hood.test(hoodPos);
                telemetry.addData("reached1", testMode.toString());
                return;
            case RUN_NO_HOOD:
                shooter.test(targetRPM);
                return;
            case RUN_HOOD_DEGREES:
                shooter.test(targetRPM);
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
