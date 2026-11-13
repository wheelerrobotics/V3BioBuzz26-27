package org.firstinspires.ftc.teamcode.bioBuzz.opModes.topAuto.red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;
import org.firstinspires.ftc.teamcode.bioBuzz.opModes.routines.FullCycleAuto;

// Restored with the same disabled status as before removal.
@Disabled
@Autonomous
public final class RedFullCycleAuto extends FullCycleAuto {
    @Override
    protected Alliance.Color getAlliance() {
        return Alliance.Color.RED;
    }
}
