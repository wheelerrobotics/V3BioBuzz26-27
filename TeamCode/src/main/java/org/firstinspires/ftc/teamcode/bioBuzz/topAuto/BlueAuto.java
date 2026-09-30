package org.firstinspires.ftc.teamcode.bioBuzz.topAuto;

import static com.pedropathing.ivy.groups.Groups.sequential;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.startNectarSide;

import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.bioBuzz.BaseAuto;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;

public class BlueAuto extends BaseAuto {

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

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
