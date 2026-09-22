package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems

import com.bylazar.configurables.annotations.Configurable
import com.seattlesolvers.solverslib.util.InterpLUT

data class ShootPoint(val d: Int, val v: Int)

@Configurable
object ShooterLUT {
    @JvmField
    val points = arrayOf(
        ShootPoint(10, 1500),
        ShootPoint(20, 1500),
        ShootPoint(30, 1500),
        ShootPoint(40, 1500),
        ShootPoint(50, 1500),
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
        lut.createLUT();
    }


}