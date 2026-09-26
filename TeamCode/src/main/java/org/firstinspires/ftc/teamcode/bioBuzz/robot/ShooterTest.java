package org.firstinspires.ftc.teamcode.bioBuzz.robot;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Hood;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Shooter;

@TeleOp
@Configurable
public class ShooterTest extends OpMode {

    enum TestMode {
        STOPPED,
        RUN,
        RUN_HOOD_DEGREES //NOT RECOMMENDED
    }

    TestMode testMode = TestMode.STOPPED;
    TelemetryManager tm;
    double targetRPM = 1000;
    int hoodPos = 0;
    int hoodAngle = 10;

    Shooter shooter;
    Hood hood;

    @Override
    public void init() {
        tm = PanelsTelemetry.INSTANCE.getTelemetry();
        shooter = new Shooter(hardwareMap);
        hood = new Hood(hardwareMap);
    }

    @Override
    public void loop() {
        switch (testMode) {
            case RUN:
                shooter.test(targetRPM);
                shooter.data(targetRPM);
                hood.test(hoodPos);
                break;
            case RUN_HOOD_DEGREES:
                shooter.test(targetRPM);
                shooter.data(targetRPM);
                tm.addData("hoodSet", hood.testInDegreesGetPosition(hoodPos));
            case STOPPED:
                shooter.getShooter1().setPower(0);
                shooter.getShooter2().setPower(0);
                break;
        }
    }
}
