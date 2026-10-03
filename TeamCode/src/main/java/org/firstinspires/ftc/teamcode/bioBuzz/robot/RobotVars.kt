package org.firstinspires.ftc.teamcode.bioBuzz.robot

import com.pedropathing.math.Pose
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.lighting.Colorable
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.lighting.Lights

object RobotVars {
    private var _followerDisabled: Boolean = false

    @JvmStatic
    var followerDisabled: Boolean
        get() = _followerDisabled
        set(value) {
            if (value) Lights.queueAlert(Colorable::violet)
            _followerDisabled = value
        }

    @JvmStatic
    var lastPose: Pose = Pose(0.0,0.0,0.0)

}