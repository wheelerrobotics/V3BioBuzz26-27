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
    public static final double stopperIn = 0; // FIXME: 10/23/26 
    public static final double stopperOut = 0.5; // FIXME: 10/23/26 
    public Stopper (HardwareMap hardwareMap) {
        stopper = hardwareMap.get(Servo.class, HardwareNames.STOPPER);
        breakbeams = hardwareMap.get(AnalogSensor.class, HardwareNames.BREAKBEAMS);
    }

    public void setStopperPos(double position) {
        stopper.setPosition(position);
    }


    public boolean isEmpty() {
        return !(breakbeams.readRawVoltage() > 2);
    }


//    public void updateNum() {
//        if (in.readRawVoltage() > 2 && !lastIn) {
//            numBalls++;
//            lastIn = true;
//        } else if (in.readRawVoltage() < 2 && lastIn) {
//            lastIn = false;
//        }
//
//        if (out.readRawVoltage() > 2 && !lastOut) {
//            lastOut = true;
//        } else if (out.readRawVoltage() < 2 && lastOut) {
//            numBalls--;
//            lastOut = false;
//        }
//
//        if (!areBalls()) numBalls = 0;
//
//        GlobalT.get_telemetry().addData("Balls", numBalls);
//    }

}
