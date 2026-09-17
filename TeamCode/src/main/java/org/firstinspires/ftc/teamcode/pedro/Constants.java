package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Pedro Pathing 3 configuration for this robot.
 */
public final class Constants {
    private Constants() {}

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(0.8906331400232992);
        c.yPodOffset.set(-0.008338344848062111);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("motorFrontLeft");
        c.frontRightName.set("motorFrontRight");
        c.backLeftName.set("motorBackLeft");
        c.backRightName.set("motorBackRight");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.2251399112152447);
                Controller secondaryTranslationalForward = Controller.proportional(0.08318318134480976);
                Controller primaryTranslationalLateral = Controller.proportional(0.3193399466128695);
                Controller secondaryTranslationalLateral = Controller.proportional(0.11798757735292875);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.0184602677303305));
                c.brake.set(Controller.proportionalFeedforward(0.015691227570780925));

                c.headingFeedback.set(Controller.proportional(3.2324548963809905));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.03799643311649978, 0.008625819753101383));

                c.linearBrakeCoefficients.set(Matrix.diag(0.03690602256583665, 0.04414407584156761));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0027581060047426158, 0.002280816242447158));

                c.maxAchievableForwardVelocity.set(57.280454246986395);
                c.maxAchievableStrafeVelocity.set(48.905825291000596);
                c.naturalForwardDeceleration.set(41.65658945223216);
                c.naturalStrafeDeceleration.set(56.21021190176238);
            }
    );

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new Follower(
                new PinpointLocalizer(hardwareMap, localizerConfig),
                new Mecanum(hardwareMap, drivetrainConfig),
                new Foresight(foresightConfig));
    }
}
