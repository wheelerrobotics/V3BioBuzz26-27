package org.firstinspires.ftc.teamcode.bioBuzz.topTele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bioBuzz.BaseTele;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;

@TeleOp
public class RedTele extends BaseTele {
    @Override
    public void init() {
        super.init();
        Alliance.set(Alliance.Color.RED);
    }

    @Override
    public void loop() {
        super.loop();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
