package org.firstinspires.ftc.teamcode.bioBuzz.opModes.topAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.bioBuzz.opModes.BaseAuto;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;

@Autonomous
public final class RedAuto extends BaseAuto {

    public void init() {
        Alliance.set(Alliance.Color.RED);
        super.init();
    }


    public void loop() {
        super.loop();
        r.follower.update();
    }


}
