package org.firstinspires.ftc.teamcode.bioBuzz.opModes.topTele.red;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;
import org.firstinspires.ftc.teamcode.bioBuzz.opModes.BaseTele;

@TeleOp
public final class RedBaseTele extends BaseTele {
    @Override
    protected Pose getStartingPose() {
        // Set this TeleOp's fallback x, y (inches), and heading (degrees).
        return PoseFactory.degrees().of(0, 0, 0);
    }

    @Override
    protected Alliance.Color getAlliance() {
        return Alliance.Color.RED;
    }
}
