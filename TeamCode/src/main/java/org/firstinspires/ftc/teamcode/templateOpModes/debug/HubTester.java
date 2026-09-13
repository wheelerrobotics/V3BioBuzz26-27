package org.firstinspires.ftc.teamcode.templateOpModes.debug;

import java.util.Locale;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.TempUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;
import org.firstinspires.ftc.teamcode.templateOpModes.debug.util.HardwareTelemetry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Automatically displays safe, read-only diagnostics for every connected REV hub. */
@TeleOp(name = "REV Hub Tester", group = "Debug")
public class HubTester extends OpMode {
    private TelemetryManager telemetryM;

    private final List<LynxModule> hubs = new ArrayList<>();

    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        hubs.addAll(hardwareMap.getAll(LynxModule.class));
        hubs.sort(Comparator.comparingInt(LynxModule::getModuleAddress));

        telemetryM.addData("REV Hubs Found", hubs.size());
        telemetryM.update(telemetry);
    }

    @Override
    public void loop() {
        telemetryM.addData("REV Hubs Found", hubs.size());

        if (hubs.isEmpty()) {
            telemetryM.addLine("No REV Control Hub or Expansion Hub was found");
            telemetryM.update(telemetry);
            return;
        }

        for (int index = 0; index < hubs.size(); index++) {
            addHubTelemetry(hubs.get(index), index + 1);
        }

        telemetryM.update(telemetry);
    }

    private void addHubTelemetry(LynxModule hub, int number) {
        String hubType = hub.isParent() ? "Control/Parent Hub" : "Expansion Hub";

        telemetryM.addLine("================================");
        telemetryM.addLine("Hub " + number + ": " + hubType);
        HardwareTelemetry.addDeviceInfo(
                telemetry,
                "Module address " + hub.getModuleAddress(),
                hub);

        telemetryM.addLine("Hub Identity and Status");
        telemetryM.addData("Module Address", hub.getModuleAddress());
        telemetryM.addData("Serial Number", hub.getModuleSerialNumber());
        telemetryM.addData("Firmware", valueOrUnavailable(hub.getNullableFirmwareVersionString()));
        telemetryM.addData("Parent Module", hub.isParent());
        telemetryM.addData("User Module", hub.isUserModule());
        telemetryM.addData("Responding", !hub.isNotResponding());
        telemetryM.addData("Arming State", hub.getArmingState());
        telemetryM.addData("Bulk Caching", hub.getBulkCachingMode());

        telemetryM.addLine("Electrical and Temperature Readings");
        addSafeReading("Input Voltage", "%.3f V",
                () -> hub.getInputVoltage(VoltageUnit.VOLTS));
        addSafeReading("Auxiliary Voltage", "%.3f V",
                () -> hub.getAuxiliaryVoltage(VoltageUnit.VOLTS));
        addSafeReading("Total Current", "%.3f A",
                () -> hub.getCurrent(CurrentUnit.AMPS));
        addSafeReading("GPIO Bus Current", "%.3f A",
                () -> hub.getGpioBusCurrent(CurrentUnit.AMPS));
        addSafeReading("I2C Bus Current", "%.3f A",
                () -> hub.getI2cBusCurrent(CurrentUnit.AMPS));
        addSafeReading("Temperature", "%.2f °C",
                () -> hub.getTemperature(TempUnit.CELSIUS));

        List<String> warnings = hub.getGlobalWarnings();
        telemetryM.addLine("Warnings");
        if (warnings == null || warnings.isEmpty()) {
            telemetryM.addLine("None");
        } else {
            for (String warning : warnings) {
                telemetryM.addLine(warning);
            }
        }
    }

    private void addSafeReading(String caption, String format, Reading reading) {
        try {
            telemetryM.addData(caption, String.format(Locale.US,  format, reading.get()));
        } catch (RuntimeException exception) {
            telemetryM.addData(caption, "Unavailable: " + exception.getMessage());
        }
    }

    private String valueOrUnavailable(String value) {
        return value == null || value.isEmpty() ? "Unavailable" : value;
    }

    private interface Reading {
        double get();
    }
}
