package org.firstinspires.ftc.teamcode.templateOpModes.debug;

import java.util.Locale;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.BarcodeResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.ClassifierResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.ColorResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.DetectorResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.templateOpModes.debug.util.HardwareTelemetry;

import java.util.Arrays;
import java.util.List;

/** Displays live status and pipeline results from any configured Limelight 3A. */
@Configurable
@TeleOp(name = "Limelight Debugger", group = "Debug")
public class LimelightDebugger extends OpMode {
    private TelemetryManager telemetryM;

    public static String LIMELIGHT_NAME = "limelight";
    public static int PIPELINE = 0;
    public static int POLL_RATE_HZ = 50;
    public static boolean ENABLE_CAMERA = true;
    public static int MAX_RESULTS_PER_TYPE = 5;

    private Limelight3A limelight;
    private String loadedName = "";
    private int appliedPipeline = -1;
    private int appliedPollRate = -1;
    private boolean cameraStarted;

    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.addLine("Enter a Limelight configuration name and pipeline in Panels");
        telemetryM.update(telemetry);
    }

    @Override
    public void loop() {
        loadCameraIfNeeded();

        if (limelight == null) {
            telemetryM.addData("Limelight Error", "Could not find: " + LIMELIGHT_NAME);
            telemetryM.update(telemetry);
            return;
        }

        applyConfiguration();
        HardwareTelemetry.addDeviceInfo(telemetry, loadedName, limelight);
        addCameraStatus();

        LLResult result = limelight.getLatestResult();
        if (result == null) {
            telemetryM.addLine("No pipeline result received yet");
        } else {
            addPipelineResult(result);
        }

        telemetryM.update(telemetry);
    }

    private void loadCameraIfNeeded() {
        if (limelight != null && loadedName.equals(LIMELIGHT_NAME)) {
            return;
        }

        stopCamera();
        limelight = hardwareMap.tryGet(Limelight3A.class, LIMELIGHT_NAME);
        loadedName = limelight != null ? LIMELIGHT_NAME : "";
        appliedPipeline = -1;
        appliedPollRate = -1;
        cameraStarted = false;
    }

    private void applyConfiguration() {
        int safePipeline = Range.clip(PIPELINE, 0, 9);
        int safePollRate = Range.clip(POLL_RATE_HZ, 1, 100);

        if (appliedPipeline != safePipeline) {
            limelight.pipelineSwitch(safePipeline);
            appliedPipeline = safePipeline;
        }

        if (appliedPollRate != safePollRate) {
            limelight.setPollRateHz(safePollRate);
            appliedPollRate = safePollRate;
        }

        if (ENABLE_CAMERA && !cameraStarted) {
            limelight.start();
            cameraStarted = true;
        } else if (!ENABLE_CAMERA && cameraStarted) {
            limelight.pause();
            cameraStarted = false;
        }
    }

    private void addCameraStatus() {
        telemetryM.addLine("Camera Status");
        telemetryM.addData("Enabled", ENABLE_CAMERA);
        telemetryM.addData("Running", limelight.isRunning());
        telemetryM.addData("Connected", limelight.isConnected());
        telemetryM.addData("Time Since Update", String.format(Locale.US,  "%d ms", limelight.getTimeSinceLastUpdate()));
        telemetryM.addData("Requested Pipeline", appliedPipeline);
        telemetryM.addData("Poll Rate", String.format(Locale.US,  "%d Hz", appliedPollRate));

        LLStatus status = limelight.getStatus();
        if (status == null) {
            telemetryM.addLine("Detailed status unavailable");
            return;
        }

        telemetryM.addData("Camera Name", status.getName());
        telemetryM.addData("Active Pipeline", status.getPipelineIndex());
        telemetryM.addData("Pipeline Type", status.getPipelineType());
        telemetryM.addData("FPS", String.format(Locale.US,  "%.2f", status.getFps()));
        telemetryM.addData("CPU", String.format(Locale.US,  "%.2f%%", status.getCpu()));
        telemetryM.addData("RAM", String.format(Locale.US,  "%.2f%%", status.getRam()));
        telemetryM.addData("Temperature", String.format(Locale.US,  "%.2f °C", status.getTemp()));
        telemetryM.addData("Processed Images", status.getPipeImgCount());
        telemetryM.addData("Snapshot Mode", status.getSnapshotMode());
        telemetryM.addData("Hardware Type", status.getHwType());
    }

    private void addPipelineResult(LLResult result) {
        telemetryM.addLine("Latest Pipeline Result");
        telemetryM.addData("Valid Target", result.isValid());
        telemetryM.addData("Pipeline", String.format(Locale.US,  "%d (%s)",
                result.getPipelineIndex(), result.getPipelineType()));
        telemetryM.addData("TX / TY", String.format(Locale.US,  "%.3f° / %.3f°", result.getTx(), result.getTy()));
        telemetryM.addData("TXNC / TYNC", String.format(Locale.US,  "%.3f° / %.3f°", result.getTxNC(), result.getTyNC()));
        telemetryM.addData("Target Area", String.format(Locale.US,  "%.4f%%", result.getTa()));
        telemetryM.addData("Focus Metric", String.format(Locale.US,  "%.4f", result.getFocusMetric()));
        telemetryM.addData("Capture Latency", String.format(Locale.US,  "%.3f ms", result.getCaptureLatency()));
        telemetryM.addData("Targeting Latency", String.format(Locale.US,  "%.3f ms", result.getTargetingLatency()));
        telemetryM.addData("Parse Latency", String.format(Locale.US,  "%.3f ms", result.getParseLatency()));
        telemetryM.addData("Staleness", String.format(Locale.US,  "%d ms", result.getStaleness()));
        telemetryM.addData("Control Hub Timestamp", result.getControlHubTimeStamp());

        telemetryM.addLine("Robot Pose");
        telemetryM.addData("MegaTag 1", result.getBotpose());
        telemetryM.addData("MegaTag 2", result.getBotpose_MT2());
        telemetryM.addData("Tag Count", result.getBotposeTagCount());
        telemetryM.addData("Tag Span", String.format(Locale.US,  "%.4f", result.getBotposeSpan()));
        telemetryM.addData("Average Distance", String.format(Locale.US,  "%.4f m", result.getBotposeAvgDist()));
        telemetryM.addData("Average Area", String.format(Locale.US,  "%.4f", result.getBotposeAvgArea()));
        telemetryM.addData("MT1 Std Dev", Arrays.toString(result.getStddevMt1()));
        telemetryM.addData("MT2 Std Dev", Arrays.toString(result.getStddevMt2()));
        telemetryM.addData("Python Output", Arrays.toString(result.getPythonOutput()));

        addFiducials(result.getFiducialResults());
        addDetectors(result.getDetectorResults());
        addClassifiers(result.getClassifierResults());
        addColors(result.getColorResults());
        addBarcodes(result.getBarcodeResults());
    }

    private void addFiducials(List<FiducialResult> results) {
        telemetryM.addLine("Fiducials: " + results.size());
        int limit = resultLimit(results.size());
        for (int index = 0; index < limit; index++) {
            FiducialResult result = results.get(index);
            telemetryM.addData("Fiducial " + index, String.format(Locale.US, 
                    "ID %d | %s | TX %.2f° | TY %.2f° | Area %.3f | Skew %.2f°",
                    result.getFiducialId(), result.getFamily(),
                    result.getTargetXDegrees(), result.getTargetYDegrees(),
                    result.getTargetArea(), result.getSkew()));
        }
    }

    private void addDetectors(List<DetectorResult> results) {
        telemetryM.addLine("Detectors: " + results.size());
        int limit = resultLimit(results.size());
        for (int index = 0; index < limit; index++) {
            DetectorResult result = results.get(index);
            telemetryM.addData("Detector " + index, String.format(Locale.US, 
                    "%s (ID %d) | Confidence %.3f | TX %.2f° | TY %.2f° | Area %.3f",
                    result.getClassName(), result.getClassId(), result.getConfidence(),
                    result.getTargetXDegrees(), result.getTargetYDegrees(), result.getTargetArea()));
        }
    }

    private void addClassifiers(List<ClassifierResult> results) {
        telemetryM.addLine("Classifiers: " + results.size());
        int limit = resultLimit(results.size());
        for (int index = 0; index < limit; index++) {
            ClassifierResult result = results.get(index);
            telemetryM.addData("Classifier " + index, String.format(Locale.US, 
                    "%s (ID %d) | Confidence %.3f",
                    result.getClassName(), result.getClassId(), result.getConfidence()));
        }
    }

    private void addColors(List<ColorResult> results) {
        telemetryM.addLine("Color Results: " + results.size());
        int limit = resultLimit(results.size());
        for (int index = 0; index < limit; index++) {
            ColorResult result = results.get(index);
            telemetryM.addData("Color " + index, String.format(Locale.US, 
                    "TX %.2f° | TY %.2f° | Area %.3f",
                    result.getTargetXDegrees(), result.getTargetYDegrees(), result.getTargetArea()));
        }
    }

    private void addBarcodes(List<BarcodeResult> results) {
        telemetryM.addLine("Barcodes: " + results.size());
        int limit = resultLimit(results.size());
        for (int index = 0; index < limit; index++) {
            BarcodeResult result = results.get(index);
            telemetryM.addData("Barcode " + index, String.format(Locale.US,  "%s | %s",
                    result.getFamily(), result.getData()));
        }
    }

    private int resultLimit(int size) {
        return Math.min(size, Math.max(0, MAX_RESULTS_PER_TYPE));
    }

    private void stopCamera() {
        if (limelight != null) {
            limelight.stop();
        }
    }

    @Override
    public void stop() {
        stopCamera();
    }
}
