package org.firstinspires.ftc.teamcode.bioBuzz.topAuto;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.bottomFlower;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.bottomFlowerCTRL;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.gardenPickup;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.gardenPickupCTRL;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.park;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.partnerPickup;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.partnerPickupCTRL;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.passUnderCTRL;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.sideFlower;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.startNectarSide;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.CommandBuilder;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.teamcode.bioBuzz.BaseAuto;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;

public class BlueAuto extends BaseAuto {

    private Command autoRoutine() {
        return sequential(


        );
    }

    @Override
    public void init() {
        super.init();
        Alliance.set(Alliance.Color.BLUE);
        r.follower.setPose(startNectarSide);
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
