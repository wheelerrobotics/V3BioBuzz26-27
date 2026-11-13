package org.firstinspires.ftc.teamcode.bioBuzz.opModes.routines.autos;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.bottomFlower;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.park;
import static org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib.startNectarSide;

import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.teamcode.bioBuzz.opModes.baseOpmodes.BaseAuto;

public abstract class NectarShootAndParkAuto extends BaseAuto {

    private static Path ab() {
        return curve(startNectarSide, bottomFlower, park).tangent();
    }

    private final Command stopIt = instant(() -> r.commands.shootCommand().cancel());

    @Override
    protected Command buildRoutine() {
        return sequential(
                r.commands.shootCommand(),
                waitMs(5000),
                stopIt,
                follow(r.follower, ab())
        );
    }

    @Override
    protected Pose getStartingPose() {
        return startNectarSide;
    }
}
