package org.firstinspires.ftc.teamcode.bioBuzz;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.lazy;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.pedropathing.ivy.pedro.PedroCommands.hold;

import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.Robot;
import org.firstinspires.ftc.teamcode.pedro.Constants;

import java.util.Objects;

public final class Commands {
    private final Robot robot;

    public Commands(Robot robotInstance) {
        robot = robotInstance;
    }

    public Command driveToPos(Pose destination) {
        return driveToPos(destination, 1.0, 1.0);
    }

    public Command driveToPos(Pose destination, double drivePower, double positionTolerance) {
        return lazy(() -> {
            Pose start = robot.follower.pose();

            if (start.distance(destination) < positionTolerance) {
                return hold(robot.follower, destination)
                        .requiring(robot.follower);
            }

            Path path = line(start, destination)
                    .linear(start, destination)
                    .with(Constants.foresightConfig.maxPathSpeed.at(drivePower));

            robot.follower.holdEnd.set(true);
            return follow(robot.follower, path)
                    .requiring(robot.follower);
        }).requiring(robot.follower);
    }

    public Command stopDriving() {
        return instant(robot.follower::stop)
                .requiring(robot.follower);
    }
}
