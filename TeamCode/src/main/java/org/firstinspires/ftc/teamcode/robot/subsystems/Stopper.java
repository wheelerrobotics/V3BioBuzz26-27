package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.hardware.HardwareNames;

public class Stopper {
    public Servo stopper;
    public Stopper (HardwareMap hardwareMap) {
        stopper = hardwareMap.get(Servo.class, HardwareNames.STOPPER);

    }

    public void setStopperPos(double position) {
        stopper.setPosition(position);
    }
}
