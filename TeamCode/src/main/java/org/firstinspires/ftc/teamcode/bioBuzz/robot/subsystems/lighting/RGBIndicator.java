package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.lighting;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

class RGBIndicator implements Colorable {
    final Servo light;
    public RGBIndicator(HardwareMap hardwareMap, String name) {
        light = hardwareMap.get(Servo.class, name);
    }

    @Override
    public void off() {
        light.setPosition(0.0);
    }

    @Override
    public void red() {
        light.setPosition(0.277);
    }

    @Override
    public void orange() {
        light.setPosition(0.333);
    }

    @Override
    public void yellow() {
        light.setPosition(0.388);
    }

    @Override
    public void sage() {
        light.setPosition(0.444);
    }

    @Override
    public void green() {
        light.setPosition(0.500);
    }

    @Override
    public void azure() {
        light.setPosition(0.555);
    }

    @Override
    public void blue() {
        light.setPosition(0.611);
    }

    @Override
    public void indigo() {
        light.setPosition(0.666);
    }

    @Override
    public void violet() {
        light.setPosition(0.722);
    }

    @Override
    public void white() {
        light.setPosition(1.0);
    }

}