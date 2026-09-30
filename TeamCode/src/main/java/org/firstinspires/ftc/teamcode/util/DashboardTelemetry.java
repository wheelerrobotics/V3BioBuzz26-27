package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
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

    private DashboardTelemetry() { }

    public static DashboardTelemetry getInstance() {
        return INSTANCE;
    }

    /** Call once in OpMode.init(), before constructing subsystems. */
    public static DashboardTelemetry begin(Telemetry driverStation) {
        synchronized (INSTANCE) {
            INSTANCE.values.clear();
            INSTANCE.lines.clear();
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

    /** Flush once at the end of a loop, after subsystems have added their readings. */
    public synchronized void update(Telemetry driverStation) {
        TelemetryPacket packet = new TelemetryPacket();
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
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
        driverStation.update();
    }
}
