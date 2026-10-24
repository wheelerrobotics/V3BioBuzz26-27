package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems

import com.acmerobotics.dashboard.config.Config
import com.seattlesolvers.solverslib.util.InterpLUT

data class ShootPoint(val d: Int, val v: Int)

@Config
object ShooterLUT {
    @JvmField
    val points = arrayOf(
        ShootPoint(10, 450),
        ShootPoint(20, 500),
        ShootPoint(30, 600),
        ShootPoint(40, 700),
        ShootPoint(50, 800),
    )

    private lateinit var _lut: InterpLUT

    @JvmStatic
    var lut: InterpLUT
        get() = _lut
        set(value) {
            _lut = value
        }

    @JvmStatic
    fun init() {
        val listD = points.map { it.d.toDouble() }.toList()
        val listV = points.map { it.v.toDouble() }.toList()
        lut = InterpLUT(listD, listV)
        lut.createLUT()
    }


}