package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;

@Config
public class Intake {
    public final DcMotorEx intake;
//    public final AnalogSensor intakeSensor;
    private final Gamepad gamepad;

    public static final double intakePower = 1;

    public Intake(HardwareMap hardwareMap, Gamepad gamepad) {
        intake = hardwareMap.get(DcMotorEx.class, HardwareNames.INTAKE);
//        intakeSensor = hardwareMap.get(AnalogSensor.class, HardwareNames.INTAKE_SENSOR);
        this.gamepad = gamepad;
    }
    public void setIntakePower(double Power) {
        intake.setPower(Power);
    }

    public boolean pollenPresent() {
        return gamepad.a;
    }



}
