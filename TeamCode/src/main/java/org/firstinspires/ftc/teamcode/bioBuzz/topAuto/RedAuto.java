package org.firstinspires.ftc.teamcode.bioBuzz.topAuto;

import org.firstinspires.ftc.teamcode.bioBuzz.BaseAuto;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;

public class RedAuto extends BaseAuto {

    public void init() {
        Alliance.set(Alliance.Color.RED);
        super.init();
    }


    public void loop() {
        super.loop();
        r.follower.update();
    }


}
