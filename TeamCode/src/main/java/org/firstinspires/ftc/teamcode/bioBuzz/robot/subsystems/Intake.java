package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;


import com.qualcomm.robotcore.hardware.AnalogSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;


public class Intake {
    public DcMotorEx intake;
    public AnalogSensor intakeSensor;

    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class, HardwareNames.INTAKE);
        intakeSensor = hardwareMap.get(AnalogSensor.class, HardwareNames.INTAKE_SENSOR);

    }
    public void setIntakePower(double Power) {
        intake.setPower(Power);
    }

    public boolean pollenPresent() {
        return intakeSensor.readRawVoltage() > 2;
    }



}
