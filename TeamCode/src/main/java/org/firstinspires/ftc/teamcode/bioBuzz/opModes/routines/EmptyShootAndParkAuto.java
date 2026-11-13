package org.firstinspires.ftc.teamcode.bioBuzz.opModes.routines;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.park2;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.sideFlowerCTRL;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.startEmptySide;

import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.teamcode.bioBuzz.opModes.BaseAuto;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.RobotVars;

public abstract class EmptyShootAndParkAuto extends BaseAuto {

    private static Path ab() {
        return curve(startEmptySide, sideFlowerCTRL, park2).tangent();
    }

    private final Command stopIt = instant(() -> r.commands.shootCommand().cancel());

    @Override
    protected Command buildRoutine() {
        return sequential(
                waitMs(RobotVars.getWaitTime()),
                r.commands.shootCommand(),
                waitMs(5000),
                stopIt,
                follow(r.follower, ab())
        );
    }

    @Override
    protected Pose getStartingPose() {
        return startEmptySide;
    }
}
