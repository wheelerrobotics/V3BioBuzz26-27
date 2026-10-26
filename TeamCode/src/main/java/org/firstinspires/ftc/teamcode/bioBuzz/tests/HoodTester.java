package org.firstinspires.ftc.teamcode.bioBuzz.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Hood;

@Config
@TeleOp
public class HoodTester extends OpMode {

    private Hood hood;
    public static int testInt = 0;
    public static double testPos = 0;
    public static double testDegPos = 15;

    @Override
    public void init() {
        hood = new Hood(hardwareMap);
        testInt = 0;
        testPos = 0;
        testDegPos = 15;
    }

    @Override
    public void loop() {
        telemetry.update();
        switch(testInt) {
            case 0:
                hood.test(testPos);
                break;
            case 1:
                double calcPos = hood.testInDegreesGetPosition(testDegPos);
                telemetry.addData("hoodCalculatedPos", calcPos);
                break;
        }
    }
}
