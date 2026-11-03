package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.math.Pose;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Shared per-loop readings from OpModes and subsystems, sent to both displays. */
public final class DashboardTelemetry {
    private static final DashboardTelemetry INSTANCE = new DashboardTelemetry();
    private final Map<String, Object> values = new LinkedHashMap<>();
    private final List<String> lines = new ArrayList<>();
    private Pose robotPose;

    private DashboardTelemetry() { }

    public static DashboardTelemetry getInstance() {
        return INSTANCE;
    }

    /** Call once in OpMode.init(), before constructing subsystems. */
    public static DashboardTelemetry begin(Telemetry driverStation) {
        synchronized (INSTANCE) {
            INSTANCE.values.clear();
            INSTANCE.lines.clear();
            INSTANCE.robotPose = null;
        }
        driverStation.clearAll();
        return INSTANCE;
    }

    public synchronized void addData(String caption, Object value) {
        if (values.size() >= 200) return;
        // Multiple hubs/devices can use the same caption in one report.
        String key = caption;
        int index = 2;
        while (values.containsKey(key)) key = caption + " (" + index++ + ")";
        values.put(key, value);
    }

    public synchronized void addLine(String line) {
        // Keep accidental missing update() calls from growing memory indefinitely.
        if (lines.size() < 100) lines.add(line);
    }

    /** Include the current Pedro pose in this loop's telemetry and field drawing. */
    public synchronized void setRobotPose(Pose pose) {
        robotPose = pose;
    }

    /** Flush once at the end of a loop, after subsystems have added their readings. */
    public synchronized void update(Telemetry driverStation) {
        TelemetryPacket packet = new TelemetryPacket();
        if (robotPose != null) {
            addData("Pedro X", robotPose.x());
            addData("Pedro Y", robotPose.y());
            addData("Heading degrees", Math.toDegrees(robotPose.heading()));

            // Convert Pedro's corner-origin frame to the FTC Dashboard field frame.
            double x = 72 - robotPose.y();
            double y = robotPose.x() - 72;
            double heading = robotPose.heading() + Math.PI / 2;
            packet.fieldOverlay()
                    .setStroke("#3F51B5")
                    .setStrokeWidth(2)
                    .strokeCircle(x, y, 8)
                    .strokeLine(x, y,
                            x + 8 * Math.cos(heading),
                            y + 8 * Math.sin(heading));
        }
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            packet.put(entry.getKey(), entry.getValue());
            driverStation.addData(entry.getKey(), entry.getValue());
        }
        for (String line : lines) {
            packet.addLine(line);
            driverStation.addLine(line);
        }
        values.clear();
        lines.clear();
        robotPose = null;
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
        driverStation.update();
    }
}
