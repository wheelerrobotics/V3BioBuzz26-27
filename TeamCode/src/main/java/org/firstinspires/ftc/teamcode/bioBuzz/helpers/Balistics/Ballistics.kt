package org.firstinspires.ftc.teamcode.bioBuzz.helpers.Balistics

import android.R.attr.value
import com.pedropathing.follower.Follower
import com.qualcomm.hardware.limelightvision.Limelight3A
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.util.RobotLog
import org.firstinspires.ftc.robotcore.external.Telemetry

object Ballistics {
    private var logTag: String = "Ballistics"
    private lateinit var helper: BallisticsHelper;
    private var _result: BallisticResult = BallisticResult();
    @JvmStatic
    var result: BallisticResult
        get() = _result
        private set(value) {
            _result = value
        };

    @JvmStatic
    fun init(f: Follower, ll: Limelight3A, t: Telemetry, vararg motors: DcMotorEx) {
        helper = BallisticsHelper(f, ll, t, *motors)
        _result = helper.result
    }

    @JvmStatic
    fun update() {
        if (!::helper.isInitialized) {
            RobotLog.ee(logTag, "Balistics.init() was not called before result()")
            throw UninitializedPropertyAccessException(
                "Balistics.init() was not called before result()"
            )
        }

        _result = helper.result
    }

}