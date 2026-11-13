package org.firstinspires.ftc.teamcode.bioBuzz.opModes.topAuto.red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;
import org.firstinspires.ftc.teamcode.bioBuzz.opModes.routines.autos.FullCycleAuto;

@Autonomous
public final class RedFullCycleAuto extends FullCycleAuto {
    @Override
    protected Alliance.Color getAlliance() {
        return Alliance.Color.RED;
    }
}
