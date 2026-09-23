package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;


import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;


public class Intake {
    public DcMotorEx intake;

    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class, HardwareNames.INTAKE);

    }
    public void setIntakePower(double Power) {
        intake.setPower(Power);
    }



}
