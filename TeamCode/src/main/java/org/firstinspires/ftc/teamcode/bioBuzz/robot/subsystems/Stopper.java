package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;

import com.qualcomm.robotcore.hardware.AnalogSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;


public class Stopper {
    public Servo stopper;
    public AnalogSensor breakbeams;
    public AnalogSensor in;
    public AnalogSensor out;
    public boolean lastIn = false;
    public boolean lastOut = false;


    public int numBalls;
    public Stopper (HardwareMap hardwareMap) {
        stopper = hardwareMap.get(Servo.class, HardwareNames.STOPPER);
        breakbeams = hardwareMap.get(AnalogSensor.class, HardwareNames.BREAKBEAMS);
        in = hardwareMap.get(AnalogSensor.class, HardwareNames.IN_SWITCH);
        out = hardwareMap.get(AnalogSensor.class, HardwareNames.OUT_SWITCH);


    }

    public void setStopperPos(double position) {
        stopper.setPosition(position);
    }

    public boolean areBalls() {
        return breakbeams.readRawVoltage() > 2;
    }


    public void updateBalls() {

        if (in.readRawVoltage() > 2 && !lastIn) {
            numBalls++;
            lastIn = true;
        } else if (in.readRawVoltage() < 2 && lastIn) {
            lastIn = false;
        }

        if (out.readRawVoltage() > 2 && !lastOut) {
            lastOut = true;
        } else if (out.readRawVoltage() < 2 && lastOut) {
            numBalls--;
            lastOut = false;
        }

    }

    public int getNumBalls() {
        return numBalls;
    }

    public boolean isEmpty() {
        return numBalls == 0;
    }



}
