package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;

@Config
public class Transfer {
    public final DcMotorEx transfer;
    public static final double transferPower = 1;


    public Transfer(HardwareMap hardwareMap) {
        transfer = hardwareMap.get(DcMotorEx.class, HardwareNames.TRANSFER);

    }

    public void setTransferPower(double Power) {
        transfer.setPower(Power);
    }


}
