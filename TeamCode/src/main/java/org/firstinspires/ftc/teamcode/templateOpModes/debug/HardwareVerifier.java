package org.firstinspires.ftc.teamcode.templateOpModes.debug;

import java.util.Locale;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.ExpectedHardware;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.util.HardwareDeviceSpec;

import java.util.ArrayList;
import java.util.List;

/** Verifies the presence and type of every device declared in ExpectedHardware. */
@TeleOp(name = "Hardware Verifier", group = "Debug")
public class HardwareVerifier extends OpMode {
    private TelemetryManager telemetryM;

    private final List<VerificationResult> results = new ArrayList<>();
    private int passed;

    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        verifyHardware();
    }

    @Override
    public void loop() {
        telemetryM.addData("Summary", String.format(Locale.US,  "%d passed, %d failed",
                passed, results.size() - passed));
        telemetryM.addLine("================================");

        for (VerificationResult result : results) {
            telemetryM.addData(result.passed ? "PASS" : "FAIL", String.format(Locale.US, 
                    "%s | %s | %s",
                    result.configName,
                    result.purpose,
                    result.details));
        }

        telemetryM.update(telemetry);
    }

    private void verifyHardware() {
        results.clear();
        passed = 0;

        for (HardwareDeviceSpec spec : ExpectedHardware.DEVICES) {
            verifyDevice(spec);
        }
    }

    private void verifyDevice(HardwareDeviceSpec spec) {
        String configName = spec.getConfigName();
        String expectedType = spec.getDeviceType().getSimpleName();

        try {
            HardwareDevice typedDevice = hardwareMap.tryGet(
                    spec.getDeviceType(), configName);

            if (typedDevice != null) {
                passed++;
                results.add(new VerificationResult(
                        configName,
                        spec.getPurpose(),
                        true,
                        expectedType + " — " + typedDevice.getDeviceName()));
                return;
            }

            HardwareDevice differentlyTypedDevice = hardwareMap.tryGet(
                    HardwareDevice.class, configName);

            if (differentlyTypedDevice == null) {
                results.add(new VerificationResult(
                        configName,
                        spec.getPurpose(),
                        false,
                        "Not found; expected " + expectedType));
            } else {
                results.add(new VerificationResult(
                        configName,
                        spec.getPurpose(),
                        false,
                        "Expected " + expectedType + ", found "
                                + differentlyTypedDevice.getClass().getSimpleName()));
            }
        } catch (RuntimeException exception) {
            results.add(new VerificationResult(
                    configName,
                    spec.getPurpose(),
                    false,
                    "Could not check: " + exception.getMessage()));
        }
    }

    private static class VerificationResult {
        final String configName;
        final String purpose;
        final boolean passed;
        final String details;

        VerificationResult(
                String configName,
                String purpose,
                boolean passed,
                String details
        ) {
            this.configName = configName;
            this.purpose = purpose;
            this.passed = passed;
            this.details = details;
        }
    }
}
