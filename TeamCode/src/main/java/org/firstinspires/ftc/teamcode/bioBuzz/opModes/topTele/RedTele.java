package org.firstinspires.ftc.teamcode.bioBuzz.opModes.topTele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.opModes.BaseTele;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;

@TeleOp
public class RedTele extends BaseTele {
    @Override
    public void init() {
        super.init();
        Alliance.set(Alliance.Color.RED);
    }

}
