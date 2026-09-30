package org.firstinspires.ftc.teamcode.templateOpModes.debug;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.util.DashboardTelemetry;
import java.util.TreeSet;

/** Read-only test of live Dashboard configuration and motor lookup. Never powers motors. */
@Config
@TeleOp(name = "Dashboard Motor Lookup Debug", group = "Debug")
public class DashboardMotorLookupDebug extends OpMode {
    public static volatile int PROBE_NUMBER = 0;
    public static volatile String TEST_NAME = "s1";
    private DashboardTelemetry output;
    private long loops;
    private long lastReport;
    private String names;

    @Override public void init() {
        output = DashboardTelemetry.begin(telemetry);
        TreeSet<String> configured = new TreeSet<>();
        for (DcMotorEx motor : hardwareMap.getAll(DcMotorEx.class)) {
            configured.addAll(hardwareMap.getNamesOf(motor));
        }
        names = configured.toString();
        report();
    }

    @Override public void init_loop() { loop(); }

    @Override public void loop() {
        loops++;
        if (System.nanoTime() - lastReport >= 250_000_000L) report();
    }

    private void report() {
        lastReport = System.nanoTime();
        String requested = TEST_NAME;
        output.addData("Build marker", "FTC-DASHBOARD-SLOTH-1");
        output.addData("Loop counter", loops);
        output.addData("PROBE_NUMBER (Java)", PROBE_NUMBER);
        output.addData("TEST_NAME (Java)", "[" + requested + "]");
        output.addData("MotorTester.MOTOR_NAME (Java)", MotorTester.MOTOR_NAME);
        output.addData("Configured motors", names);
        output.addData("Fixed s1", lookup("s1"));
        output.addData("Fixed s2", lookup("s2"));
        output.addData("TEST_NAME lookup", lookup(requested));
        output.update(telemetry);
    }

    private String lookup(String name) {
        if (name == null || name.isEmpty()) return "Empty name";
        try {
            return "PASS: " + hardwareMap.get(DcMotorEx.class, name).getClass().getSimpleName();
        } catch (RuntimeException error) {
            return error.getClass().getSimpleName() + ": " + error.getMessage();
        }
    }
}
