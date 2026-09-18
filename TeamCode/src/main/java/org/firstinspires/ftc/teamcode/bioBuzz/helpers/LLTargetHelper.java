package org.firstinspires.ftc.teamcode.bioBuzz.helpers;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.ArrayList;
import java.util.List;

public class LLTargetHelper {

    public static List<Integer> getCurrentIDs() {
        List<Integer> list = new ArrayList<>();
        if (Alliance.get() == Alliance.Color.RED) {
            if (PoseLib.getTarget() == PoseLib.Target.NECTAR) {
                list.add(34);
                list.add(35);
                list.add(36);
                list.add(37);
            } else {
                list.add(30);
                list.add(31);
                list.add(32);
                list.add(33);
            }
        } else {
            if (PoseLib.getTarget() == PoseLib.Target.NECTAR) {
                list.add(42);
                list.add(43);
                list.add(44);
                list.add(45);
            } else {
                list.add(38);
                list.add(39);
                list.add(40);
                list.add(41);
            }
        }

        return list;

    }
    public static double getHorizontalDistance(LLResult r, Telemetry telemetry) {
        if (!r.isValid()) {
            return 0; // returns 0 if things don't work out
        }

        List<LLResultTypes.FiducialResult> results = r.getFiducialResults();
        LLResultTypes.FiducialResult idealResult = null;
        int ID;
        List<Integer> IDs= getCurrentIDs();
        double lastArea = 0;
        for (LLResultTypes.FiducialResult result : results) {
            for (Integer i : IDs) {
                if (result.getFiducialId() == IDs.get(i)) {
                    if (result.getTargetArea() > lastArea) { //pick the result of the most visible tag
                        ID = result.getFiducialId();
                        idealResult = result;
                    }
                }
            }
        }

        if (idealResult == null) {
            return 0; // returns 0 if things don't work out
        }

        Pose3D tPose = idealResult.getTargetPoseRobotSpace(); //Point-of-Interest Tracking for tag offset
        double distance = tPose.getPosition().x;
        telemetry.addData("distace", distance);
        return distance;

    }


}
