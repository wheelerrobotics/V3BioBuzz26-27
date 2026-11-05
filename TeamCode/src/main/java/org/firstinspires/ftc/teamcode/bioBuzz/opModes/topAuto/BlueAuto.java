package org.firstinspires.ftc.teamcode.bioBuzz.opModes.topAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.bioBuzz.opModes.BaseAuto;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;
import org.firstinspires.ftc.teamcode.bioBuzz.opModes.BaseAuto2;

@Autonomous
public final class BlueAuto extends BaseAuto2 {

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
