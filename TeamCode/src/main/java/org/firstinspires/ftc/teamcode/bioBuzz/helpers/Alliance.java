package org.firstinspires.ftc.teamcode.bioBuzz.helpers;

import java.util.Objects;

public class Alliance {
    public enum Color {
        BLUE,
        RED
    }

    private static Color currentAlliance = null;

    public static Color alliance() throws Exception {
        try {
            return Objects.requireNonNull(currentAlliance);
        } catch (NullPointerException e) {
            throw new Exception("Alliance was accessed before set", e);
        }
    }

    public static void set(Color nAlliance) {
        currentAlliance = nAlliance;
    }

}
