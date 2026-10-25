package org.firstinspires.ftc.teamcode.bioBuzz.helpers.ballistics;

import androidx.annotation.NonNull;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib;

import java.util.HashSet;
import java.util.List;

class TargetHelper {

    @NonNull
    private static HashSet<Integer> getCurrentIDs() {
        HashSet<Integer> list = new HashSet<>();
        if (Alliance.get() == Alliance.Color.RED) {
            if (PoseLib.getActiveCell() == PoseLib.Cell.NECTAR) {
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
            if (PoseLib.getActiveCell() == PoseLib.Cell.NECTAR) {
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

    private static LLResultTypes.FiducialResult getIdealResult(LLResult r) {
        List<LLResultTypes.FiducialResult> results = r.getFiducialResults();
        LLResultTypes.FiducialResult idealResult = null;
        HashSet<Integer> IDs = getCurrentIDs();
        double lastArea = 0;
        for (LLResultTypes.FiducialResult result : results) {
            if (IDs.contains(result.getFiducialId())) {
                if (result.getTargetArea() > lastArea) { //pick the result of the most visible tag
                    lastArea = result.getTargetArea();
                    idealResult = result;
                }
            }

        }
        return idealResult;
    }

    static double getLLX(LLResult r, Telemetry t) {
        if (!r.isValid()) {
            t.addData("distance", "not valid 65");
            return 0; // returns 0 if things don't work out
        }

        LLResultTypes.FiducialResult idealResult = getIdealResult(r);

        if (idealResult == null) {
            t.addData("distance", "null 72");
            return 0; // returns 0 if things don't work out
        }

        Pose3D tPose = idealResult.getTargetPoseRobotSpace(); //Point-of-Interest Tracking for tag offset
        double distance = Math.sqrt(Math.pow(tPose.getPosition().x, 2) + Math.pow(tPose.getPosition().y, 2)); // get flat distance
        t.addData("distance", distance);
        return distance;

    }

    static double getPinpointX(Follower f, Telemetry t) {
        double x = f.pose().distance(PoseLib.targetPose()) * 0.0254; // convert to meters
        t.addData("getPinpointX()", x);
        return x;
    }


}
