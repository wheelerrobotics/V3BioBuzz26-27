package org.firstinspires.ftc.teamcode.bioBuzz.robot

import com.acmerobotics.dashboard.config.Config
import com.pedropathing.math.Pose
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.lighting.Colorable
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.lighting.Lights

@Config
object RobotVars {
    private var _followerDisabled: Boolean = false

    @JvmStatic
    var followerDisabled: Boolean
        get() = _followerDisabled
        set(value) {
            if (value) Lights.queueAlert(Colorable::violet)
            _followerDisabled = value
        }

    // Null until auto records a pose; shared only for this app session.
    @JvmStatic
    var lastPose: Pose? = null

    @JvmStatic
    var waitTime: Int = 10000

}
