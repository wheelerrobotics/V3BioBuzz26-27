package org.firstinspires.ftc.teamcode.bioBuzz.opModes;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.deadline;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.bottomFlower;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.park;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.sideFlower;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.startNectarSide;

import com.pedropathing.ivy.Command;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotVars;

public abstract class BaseAuto2 extends BaseOpMode {

    private static Path ab() {
        return curve(startNectarSide, bottomFlower, park).tangent();
    }

    private Command shootWhileFollowing(Path path) { //method to shoot while following a path
        return deadline(follow(r.follower, path), r.commands.shootCommand());
    }

    private final Command stopIt = instant(() -> r.commands.shootCommand().cancel());

    private Command auto() {
        return sequential(
                r.commands.shootCommand(),
                waitMs(5000),
                stopIt,
                follow(r.follower,ab())
        );
    }

    @Override
    public void init() {
        super.init();
        r.follower.setPose(startNectarSide);
        schedule(auto());
    }

    @Override
    public void loop() {
        RobotVars.setLastPose(r.follower.pose());
        super.loop();
    }

}
