package org.firstinspires.ftc.teamcode.bioBuzz.opModes.topAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.bioBuzz.opModes.BaseAuto;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;

@Autonomous
public final class BlueAuto extends BaseAuto {

    @Override
    public void init() {
        Alliance.set(Alliance.Color.BLUE);
        super.init();
    }

    @Override
    public void loop() {
        super.loop();
        r.follower.update();
    }

}
