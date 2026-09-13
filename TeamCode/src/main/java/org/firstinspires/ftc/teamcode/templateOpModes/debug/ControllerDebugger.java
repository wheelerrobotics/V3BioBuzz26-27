package org.firstinspires.ftc.teamcode.templateOpModes.debug;

import java.util.Locale;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp(name = "ControllerDebugger", group = "Debug")
public class ControllerDebugger extends OpMode {
    private TelemetryManager telemetryM;


    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.addLine("Controller debugger initialized");
        telemetryM.update(telemetry);
    }

    @Override
    public void loop() {
        addGamepadTelemetry("Gamepad 1", gamepad1);
        telemetryM.addLine("================================");
        addGamepadTelemetry("Gamepad 2", gamepad2);
        telemetryM.update(telemetry);
    }

    private void addGamepadTelemetry(String name, Gamepad gamepad) {
        telemetryM.addLine(name);
        telemetryM.addData(name + " type", gamepad.type);
        telemetryM.addData(name + " connected", gamepad.id != Gamepad.ID_UNASSOCIATED);

        telemetryM.addLine("Face buttons");
        telemetryM.addData("a / cross", String.format(Locale.US,  "%s / %s", gamepad.a, gamepad.cross));
        telemetryM.addData("b / circle", String.format(Locale.US,  "%s / %s", gamepad.b, gamepad.circle));
        telemetryM.addData("x / square", String.format(Locale.US,  "%s / %s", gamepad.x, gamepad.square));
        telemetryM.addData("y / triangle", String.format(Locale.US,  "%s / %s", gamepad.y, gamepad.triangle));

        telemetryM.addLine("Sticks");
        telemetryM.addData("left stick (x, y)", String.format(Locale.US,  "%.3f, %.3f",
                gamepad.left_stick_x, gamepad.left_stick_y));
        telemetryM.addData("right stick (x, y)", String.format(Locale.US,  "%.3f, %.3f",
                gamepad.right_stick_x, gamepad.right_stick_y));
        telemetryM.addData("left stick button", gamepad.left_stick_button);
        telemetryM.addData("right stick button", gamepad.right_stick_button);

        telemetryM.addLine("D-pad");
        telemetryM.addData("up", gamepad.dpad_up);
        telemetryM.addData("down", gamepad.dpad_down);
        telemetryM.addData("left", gamepad.dpad_left);
        telemetryM.addData("right", gamepad.dpad_right);

        telemetryM.addLine("Shoulders");
        telemetryM.addData("left bumper", gamepad.left_bumper);
        telemetryM.addData("right bumper", gamepad.right_bumper);
        telemetryM.addData("left trigger", String.format(Locale.US,  "%.3f (pressed: %s)",
                gamepad.left_trigger, gamepad.left_trigger_pressed));
        telemetryM.addData("right trigger", String.format(Locale.US,  "%.3f (pressed: %s)",
                gamepad.right_trigger, gamepad.right_trigger_pressed));

        telemetryM.addLine("System buttons");
        telemetryM.addData("start / options", String.format(Locale.US,  "%s / %s", gamepad.start, gamepad.options));
        telemetryM.addData("back / share", String.format(Locale.US,  "%s / %s", gamepad.back, gamepad.share));
        telemetryM.addData("guide / PS", String.format(Locale.US,  "%s / %s", gamepad.guide, gamepad.ps));

        telemetryM.addLine("Touchpad");
        telemetryM.addData("touchpad button", gamepad.touchpad);
        telemetryM.addData("finger 1", String.format(Locale.US,  "%s (%.3f, %.3f)",
                gamepad.touchpad_finger_1,
                gamepad.touchpad_finger_1_x,
                gamepad.touchpad_finger_1_y));
        telemetryM.addData("finger 2", String.format(Locale.US,  "%s (%.3f, %.3f)",
                gamepad.touchpad_finger_2,
                gamepad.touchpad_finger_2_x,
                gamepad.touchpad_finger_2_y));
    }
}
