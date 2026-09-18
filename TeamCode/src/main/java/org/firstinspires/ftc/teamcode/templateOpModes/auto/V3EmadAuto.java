package org.firstinspires.ftc.teamcode.templateOpModes.auto;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.api.Paths.line;
import static com.pedropathing.api.Paths.path;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
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
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class V3EmadAuto extends OpMode {

    private Follower f; //normal just short to f
    private static final PoseFactory p = PoseFactory.degrees(); //ts is new and weird

    //poses
    private static final Pose a = p.of(24,24,0);
    private static final Pose b = p.of(24, 48, 0);
    private static final Pose c = p.of(48,48,0);
    private static final Pose d = p.of(48,24,0);

    private static Path ab() {
        return curve(startNectarSide, passUnderCTRL, sideFlower, partnerPickup).constant(90);
    }

    private static Path bc() {
        return line(partnerPickup, sideFlower).linear(90,270);
    }

    private static Path cd() {
        return curve(sideFlower, partnerPickupCTRL, gardenPickupCTRL, gardenPickup).linear(270, 0);
    }

    private static Path da() {
        return curve(gardenPickup,bottomFlowerCTRL,bottomFlower).constant(0);
    }

    private static Path e() {
        return line(bottomFlower, bottomFlowerCTRL).linear(0, 180);
    }

    private static Path f() {
        return line(bottomFlowerCTRL, park).tangent();
    }

    private static Path all(){
        return path(ab(),bc(),cd(),da(),e(),f());
    }

    private Command autoRoutine() {
        return sequential(
                follow(f,all())
        );
    }

    @Override
    public void init() {
        Scheduler.reset(); //for ivy
        f = Constants.createFollower(hardwareMap); //this is the same
        f.setPose(startNectarSide); //crucial, this is a replacement for setStartingPose(), i think
    }

    @Override
    public void start() {
        autoRoutine().schedule();
    }

    @Override
    public void loop() {
        f.update();
        Scheduler.execute(); //ivy update
    }

}
