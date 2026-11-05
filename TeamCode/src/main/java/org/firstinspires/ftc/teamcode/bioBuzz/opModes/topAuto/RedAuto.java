package org.firstinspires.ftc.teamcode.bioBuzz.opModes.topAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.bioBuzz.opModes.BaseAuto;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;
import org.firstinspires.ftc.teamcode.bioBuzz.opModes.BaseAuto2;

@Autonomous
public final class RedAuto extends BaseAuto2 {

    public void init() {
        Alliance.set(Alliance.Color.RED);
        super.init();
    }


    public void loop() {
        super.loop();
        r.follower.update();
    }


}
