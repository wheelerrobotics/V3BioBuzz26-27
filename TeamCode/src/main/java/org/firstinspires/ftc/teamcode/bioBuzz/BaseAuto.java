package org.firstinspires.ftc.teamcode.bioBuzz;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.deadline;
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

import com.pedropathing.ivy.Command;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotVars;

public abstract class BaseAuto extends BaseOpMode {

    private static Path ab() {
        return curve(startNectarSide, passUnderCTRL, sideFlower, partnerPickup).constant(90);
    }

    private static Path bc() {
        return line(partnerPickup, sideFlower).linear(90,270);
    }

    private static Path cd() {
        return curve(sideFlower, partnerPickupCTRL, gardenPickupCTRL, gardenPickup).linear(270, 0);
    }

    private static Path de() {
        return curve(gardenPickup,startNectarSide,bottomFlowerCTRL,bottomFlower).constant(0);
    }

    private static Path ef() {
        return line(bottomFlower, bottomFlowerCTRL).linear(0, 180);
    }

    private static Path fg() {
        return line(bottomFlowerCTRL, park).tangent();
    }

    private Command shootWhileFollowing(Path path) { //method to shoot while following a path
        return deadline(follow(r.follower, path), r.commands.shootCommand());
    }

    private Command auto() {
        return sequential(
                r.commands.shootCommand(),
                follow(r.follower,ab()),
                follow(r.follower,bc()),
                //Flower intake while shooting
                r.commands.shootCommand(),
                follow(r.follower,cd()), //extend intake in parallel
                shootWhileFollowing(de()),
                //flower intake without shooting
                follow(r.follower,ef()), //retract intake in parallel
                follow(r.follower,fg())
        );
    }

    @Override
    public void init() {
        super.init();
        schedule(auto());
    }

    @Override
    public void loop() {
        RobotVars.setLastPose(r.follower.pose());
        super.loop();
    }

}
