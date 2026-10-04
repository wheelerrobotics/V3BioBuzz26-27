package org.firstinspires.ftc.teamcode.bioBuzz.topTele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.BaseTele;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;

@TeleOp
public class BlueTele extends BaseTele {
    @Override
    public void init() {
        super.init();
        Alliance.set(Alliance.Color.BLUE);
    }

}
