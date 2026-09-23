package org.firstinspires.ftc.teamcode.robot.subsystems;

import static org.firstinspires.ftc.teamcode.robot.config.RobotConstants.Transfer.transferPower;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.robot.hardware.HardwareNames;

public class Transfer {
    public DcMotorEx transfer;

    public Transfer(HardwareMap hardwareMap) {
        transfer = hardwareMap.get(DcMotorEx.class, HardwareNames.TRANSFER);

    }

    public void setTransferPower(double Power) {
        transfer.setPower(Power);
    }


}
