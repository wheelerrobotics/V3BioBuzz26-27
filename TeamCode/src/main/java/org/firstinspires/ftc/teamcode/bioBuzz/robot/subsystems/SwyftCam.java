package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.util.DashboardTelemetry;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SwyftCam {

    @Config
    public static class SwyftCamConstants {
        // THESE SHOULD BE FOR TURRET OFFSET IF CAMERA IS TURRET MOUNTED
        public static final double cameraPitch = 55; // FIXME: 10/24/26 ask Eric
        public static final double cameraX = 0;
        public static final double cameraY = 2; // FIXME: 10/24/26
//        public static double cameraZ = 0; // pedro pose is 2d
        public static final Size resolution = new Size(640, 480);
    }
    public final VisionPortal portal;
    public final AprilTagProcessor processor = new AprilTagProcessor.Builder().build();
    private final HashMap<Integer, Double> offsets = new HashMap<>(); // FIXME: 10/25/26 
    private final PoseFactory poseFactory = PoseFactory.degrees();
//    private final DashboardTelemetry t;

    public SwyftCam(HardwareMap hardwareMap, String name, DashboardTelemetry t) {
//        this.t = t;
        WebcamName camera = hardwareMap.get(WebcamName.class, name);
        RobotLog.ii("SWYFT CAM SERIAL", camera.getSerialNumber().toString());
        portal = new VisionPortal.Builder()
                .enableLiveView(true)
                .setCamera(camera)
                .setCameraResolution(SwyftCamConstants.resolution)
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .addProcessor(processor)
                .build();
    }


    private Pose getPoseFromDetection(AprilTagDetection detection) {
        if (detection.ftcPose == null) return poseFactory.of(0,0,0);

        double x = detection.ftcPose.x;
        double y = detection.ftcPose.y;
        double z = detection.ftcPose.z;
        RobotLog.ii("cx", String.valueOf(x));
        RobotLog.ii("cy", String.valueOf(y));
        RobotLog.ii("cz", String.valueOf(z));

        double pitch = Math.toRadians(SwyftCamConstants.cameraPitch);

        //this math is from chat, seems good tho
        double cos = Math.cos(pitch);
        double sin = Math.sin(pitch);
        double robotX = x + SwyftCamConstants.cameraX;
        double robotY = y * cos - z * sin + SwyftCamConstants.cameraY;
//        double robotZ = y * sin + z * cos + SwyftCamConstants.cameraZ; // pedro pose is 2d

        //my math for angle to the target
        double degrees = Math.toDegrees(Math.atan2(robotX, robotY));

        Pose pose = poseFactory.of(robotX, robotY, -degrees);
        RobotLog.ii("x", String.valueOf(pose.x()));
        RobotLog.ii("y", String.valueOf(pose.y()));
        RobotLog.ii("heading", String.valueOf(pose.heading()));

        return pose;
    }

    public Pose getBestTagPosition() {
        HashSet<Integer> ids = new HashSet<>();
        for (int i = 0; i <= 99; i++) {
            Integer integer = i;
            ids.add(integer);
        }
        return getBestTagPosition(ids);
    }

    public Pose getBestTagPosition(List<Integer> ids) {
        return getBestTagPosition(new HashSet<>(ids));
    }

    public Pose getBestTagPosition(HashSet<Integer> ids) {
        double bestSize = 0;
        AprilTagDetection bestDetection = null;
        for (AprilTagDetection detection : processor.getDetections()) {

            if (ids.contains(detection.id)) {
                double size = Imgproc.contourArea(new MatOfPoint(detection.corners));
                if (size > bestSize) {
                    bestSize = size;
                    bestDetection = detection;
                }
            }
        }
        if (bestDetection != null) {
            return getPoseFromDetection(bestDetection);
        }
        return new Pose(0,0,0);
    }

}
