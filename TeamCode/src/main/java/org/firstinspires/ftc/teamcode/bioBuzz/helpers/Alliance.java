package org.firstinspires.ftc.teamcode.bioBuzz.helpers;

import java.util.Objects;

public class Alliance {
    public enum Color {
        BLUE,
        RED
    }

    private static Color currentAlliance = null;

    public static Color get()  {
        return Objects.requireNonNull(currentAlliance);
    }

    public static void set(Color nAlliance) {
        currentAlliance = nAlliance;
    }

}
