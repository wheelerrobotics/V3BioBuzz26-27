package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.AnalogSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;

@Config
public class Stopper {
    public final Servo stopper;
    @SuppressWarnings("SpellCheckingInspection")
    public final AnalogSensor breakbeams;
    public final AnalogSensor in;
    public final AnalogSensor out;
    public boolean lastIn = false;
    public boolean lastOut = false;
    public static final double stopperIn = 0;
    public static final double stopperOut = 0.5;


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


    public void updateNum() {
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

        if (areBalls()) numBalls = 0;
    }

    public int getNumBalls() {
        return numBalls;
    }

    public boolean isEmpty() {
        return numBalls == 0;
    }



}
