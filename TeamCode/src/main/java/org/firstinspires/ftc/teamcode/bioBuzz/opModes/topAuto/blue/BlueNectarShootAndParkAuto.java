package org.firstinspires.ftc.teamcode.bioBuzz.opModes.topAuto.blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;
import org.firstinspires.ftc.teamcode.bioBuzz.opModes.routines.NectarShootAndParkAuto;

@Autonomous
public final class BlueNectarShootAndParkAuto extends NectarShootAndParkAuto {
    @Override
    protected Alliance.Color getAlliance() {
        return Alliance.Color.BLUE;
    }
}
