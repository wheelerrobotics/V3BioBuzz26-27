package org.firstinspires.ftc.teamcode.bioBuzz.robot;

import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.LIGHT1;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.LIGHT2;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.LIGHT3;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.lighting.Colorable;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.lighting.RGBIndicator;

@TeleOp
public class LightsTest extends OpMode {
    private Colorable l1;
    private Colorable l2;
    private Colorable l3;

    @Override
    public void init() {
        l1 = new RGBIndicator(hardwareMap, LIGHT1);
        l2 = new RGBIndicator(hardwareMap, LIGHT2);
        l3 = new RGBIndicator(hardwareMap, LIGHT3);
    }

    @Override
    public void loop() {
        l1.azure(); //these prob don't need to be in loop but it cant hurt
        l2.orange();
        l3.red();
    }
}
