package org.firstinspires.ftc.teamcode.robot.config;

public final class RobotConstants {
    public static final class Drive {
        private Drive() {}
        public static final double MAX_DRIVE_POWER = 1.0;
        public static final double DEFAULT_DEBUG_POWER = 0.20;
        public static final double MAX_DEBUG_POWER = 0.40;
    }

    public static final class Transfer {
        public static final double transferPower = 1;
    }

    public static final class Intake {
        public static final double intakePower = 1;
    }

    public static final class Stopper {
        public static final double stopperIn = 0;
        public static final double stopperOut = 0.5;
    }



    /*
     * Add season-specific groups here as subsystems are created. Example:
     *
     * public static final class Slides {
     *     public static final int MIN_POSITION_TICKS = 0;
     *     public static final int MAX_POSITION_TICKS = 3000;
     *     public static final double KP = 0.01;
     *     public static final double KI = 0.0;
     *     public static final double KD = 0.0005;
     *
     *     private Slides() {}
     * }
     */

    private RobotConstants() {}
}
