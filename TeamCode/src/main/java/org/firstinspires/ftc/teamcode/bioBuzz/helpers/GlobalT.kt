package org.firstinspires.ftc.teamcode.bioBuzz.helpers

import org.firstinspires.ftc.robotcore.external.Telemetry

object GlobalT {
    @JvmStatic
    lateinit var _telemetry: Telemetry
    var telemetry: Telemetry
        get() = _telemetry
        set(value) {
            _telemetry = value
        };
}