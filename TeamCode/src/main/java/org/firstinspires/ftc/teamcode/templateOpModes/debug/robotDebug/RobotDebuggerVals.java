package org.firstinspires.ftc.teamcode.templateOpModes.debug.robotDebug;


import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class RobotDebuggerVals {
    @Configurable
    public static class DriveDebug {
        public static boolean ENABLED = false;
        public static boolean CONTROLLER = false;
        public static double FRONT_LEFT_POWER = 0.0;
        public static double FRONT_RIGHT_POWER = 0.0;
        public static double BACK_LEFT_POWER = 0.0;
        public static double BACK_RIGHT_POWER = 0.0;
    }

}