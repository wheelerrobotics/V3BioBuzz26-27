package org.firstinspires.ftc.teamcode.bioBuzz.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Stopper;

@TeleOp
@Config
public class StopperTester extends OpMode {
    private Stopper stopper;

    public static double stopperPos = 0;
    public static boolean closed = false;
    public static int testInt = 0;
    @SuppressWarnings("FieldMayBeFinal")
    public static int testPos = 0;

    @Override
    public void init() {
        stopper = new Stopper(hardwareMap);
        stopperPos = 0;
        closed = false;
    }

    @Override
    public void loop() {
        telemetry.update();
        switch (testInt) {
            case 0:
                stopper.setStopperPos(testPos);
                break;
            case 1:
                double pos = closed ? Stopper.stopperIn : Stopper.stopperOut;
                stopper.setStopperPos(pos);
                break;
        }
    }
}
