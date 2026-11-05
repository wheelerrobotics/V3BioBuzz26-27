package org.firstinspires.ftc.teamcode.bioBuzz.helpers;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

@SuppressWarnings("CanBeFinal")
public class PoseLib {
    private PoseLib() {}
    private static PoseFactory field = PoseFactory.degrees();
    private static Alliance.Color cFactoryAlliance = Alliance.Color.RED;

    public static void setField(Alliance.Color nAlliance) {
        if (cFactoryAlliance != nAlliance) {
            field = field.mirrorX(70.75);
            field = field.mirrorY(70.75);
            target = target.mirrorX(70.75);
            cFactoryAlliance = nAlliance;
            initPoses();
        }
    }

    //field poses
    public static Pose startNectarSide, startEmptySide, passUnderCTRL, sideFlower, sideFlowerCTRL,
            bottomFlower, bottomFlowerCTRL, partnerPickup, partnerPickupCTRL, gardenPickup,
            gardenPickupCTRL, park, nectarCell, emptyCell, park2;

    static {initPoses();}
    private static void initPoses() {
        startNectarSide = field.of(58, 8, 90);
        startEmptySide = field.of(58, 133.5, 270);
        passUnderCTRL = field.of(71, 70.75, 0);
        sideFlower = field.of(47.5, 133.5, 90);
        sideFlowerCTRL = field.of(47.5, 124.5, 90);
        bottomFlower = field.of(8, 47.5, 180);
        bottomFlowerCTRL = field.of(17, 47.5, 180);
        partnerPickup = field.of(31, 133.5, 90);
        partnerPickupCTRL = field.of(35, 113, 90);
        gardenPickup = field.of(9, 9, 270);
        gardenPickupCTRL = field.of(9, 16, 270);
        park = field.of(10, 95, 90);
        park2 = field.of(9, 118, 180);
        nectarCell = field.of(58, 56, 90);
        emptyCell = field.of(nectarCell.x(), nectarCell.invert().y(), 270);
    }



    public enum Cell {
        NECTAR,
        EMPTY
    }
    private static Cell cFactoryCell = Cell.NECTAR;
    private static PoseFactory target = PoseFactory.degrees();
    public static void setActiveCell(Cell nCell) {
        if (cFactoryCell != nCell) {
            target = target.mirrorY(70.75);
            cFactoryCell = nCell;
        }
    }
    public static Cell getActiveCell() {
        return cFactoryCell;
    }

    public static Pose targetPose() {
        return target.of(58, 60, 90);
    }

    public static void update(Follower f) {
        if (f.pose().y() > 72) {
            setActiveCell(Cell.EMPTY);
        } else setActiveCell(Cell.NECTAR);
    }

}
