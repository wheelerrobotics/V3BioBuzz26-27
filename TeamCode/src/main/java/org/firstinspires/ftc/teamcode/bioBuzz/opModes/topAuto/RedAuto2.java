package org.firstinspires.ftc.teamcode.bioBuzz.opModes.topAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;
import org.firstinspires.ftc.teamcode.bioBuzz.opModes.BaseAuto3;

@Autonomous
public final class RedAuto2 extends BaseAuto3 {

    public void init() {
        Alliance.set(Alliance.Color.RED);
        super.init();
    }


    public void loop() {
        super.loop();
        r.follower.update();
    }


}
