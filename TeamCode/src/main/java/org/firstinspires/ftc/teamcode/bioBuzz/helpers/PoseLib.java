package org.firstinspires.ftc.teamcode.bioBuzz.helpers;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;

public class PoseLib {
    private PoseLib() {}
    private static final PoseFactory field = PoseFactory.degrees();
    private static Alliance.Color cFactoryAlliance = Alliance.Color.RED;

    public static void setField(Alliance.Color nAlliance) {
        if (cFactoryAlliance != nAlliance) {
            field.mirrorX(70.75);
            field.mirrorY(70.75);
            target.mirrorX(70.75);
            cFactoryAlliance = nAlliance;
        }
    }

    //field poses
    public static Pose startNectarSide = field.of(58,8, 90);
    public static Pose startEmptySide = field.of(58,133.5, 270);
    public static Pose passUnderCTRL = field.of(71,70.75, 0);
    public static Pose sideFlower = field.of(47.5, 133.5, 90); public static Pose sideFlowerCTRL = field.of(47.5, 124.5, 90);
    public static Pose bottomFlower = field.of(8, 47.5, 180); public static Pose bottomFlowerCTRL = field.of(17, 47.5, 180);
    public static Pose partnerPickup = field.of(31, 133.5, 90); public static Pose partnerPickupCTRL = field.of(35, 113, 90);
    public static Pose gardenPickup = field.of(9, 9, 270); public static Pose gardenPickupCTRL = field.of(9, 16, 270);
    public static Pose park = field.of(10, 95, 90);
    public static Pose nectarCell = field.of(58,56,90); public static Pose emptyCell = field.of(nectarCell.x(), nectarCell.invert().y(),270);

    public enum Target {
        NECTAR,
        EMPTY
    }
    private static Target cFactoryTarget = Target.NECTAR;
    private static final PoseFactory target = PoseFactory.degrees();
    private static final Pose tPose = target.of(58, 56, 90);
    public static void setTarget(Target nTarget) {
        if (cFactoryTarget != nTarget) {
            target.mirrorY(70.75);
            cFactoryTarget = nTarget;
        }
    }
    public static Target getTarget() {
        return cFactoryTarget;
    }

    public static Pose tPose() {
        return tPose;
    }


}
