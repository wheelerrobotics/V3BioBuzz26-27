package org.firstinspires.ftc.teamcode.bioBuzz.helpers;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

public class LLTargetHelper {
    public static double getHorizontalDistance(LLResult r, Telemetry telemetry) {
        if (!r.isValid()) {
            return 0; // returns 0 if things don't work out
        }

        List<LLResultTypes.FiducialResult> results = r.getFiducialResults();
        LLResultTypes.FiducialResult idealResult = null;
        int ID;
        double lastArea = 0;
        for (LLResultTypes.FiducialResult result : results) {
            if (result.getTargetArea() > lastArea) { //pick the result of the most visible tag
                ID = result.getFiducialId();
                idealResult = result;
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
