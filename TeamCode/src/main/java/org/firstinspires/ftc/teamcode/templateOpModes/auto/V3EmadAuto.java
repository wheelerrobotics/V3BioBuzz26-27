package org.firstinspires.ftc.teamcode.templateOpModes.auto;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp
public class V3EmadAuto extends OpMode {

    private Follower f; //normal just short to f
    private static final PoseFactory p = PoseFactory.degrees(); //ts is new and weird

    //poses
    private static final Pose a = p.of(24,24,0);
    private static final Pose b = p.of(24, 48, 0);
    private static final Pose c = p.of(48,48,0);
    private static final Pose d = p.of(48,24,0);

    private static Path ab() {
        return line(a,b).constant(0);
    }

    private static Path bc() {
        return line(b,c).constant(0);
    }

    private static Path cd() {
        return line(c,d).constant(0);
    }

    private static Path da() {
        return line(d,a).constant(0);
    }

    private Command autoRoutine() {
        return sequential(
                follow(f, ab()),
                follow(f, bc()),
                follow(f, cd()),
                follow(f, da())
        );
    }

    @Override
    public void init() {
        Scheduler.reset(); //for ivy
        f = Constants.createFollower(hardwareMap); //this is the same
        f.setPose(a); //crucial, this is a replacement for setStartingPose(), i think
    }

    @Override
    public void start() {
        autoRoutine().schedule();
    }

    @Override
    public void loop() {
        Scheduler.execute(); //ivy update
    }

}
