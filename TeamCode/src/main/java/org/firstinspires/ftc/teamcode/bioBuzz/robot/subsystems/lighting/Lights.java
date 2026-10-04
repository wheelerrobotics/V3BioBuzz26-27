package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.lighting;

import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.LIGHT1;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.LIGHT2;
import static org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames.LIGHT3;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.PoseLib;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.Stopper;

import java.util.ArrayList;
import java.util.function.Consumer;

@Config
public class Lights {
    @SuppressWarnings("unused")
    public static InnerModes getInnerMode() {
        return innerMode;
    }

    @SuppressWarnings("unused")
    public static void setInnerMode(InnerModes innerMode) {
        Lights.innerMode = innerMode;
    }

    public enum InnerModes {
        COUNT,
        COUNT_UNTIL_FULL,
        OFF_UNTIL_FULL,
        ON_UNTIL_FULL
    }

    private final Colorable l1; // left of robot
    private final Colorable l2; // center
    private final Colorable l3; // right of robot
    private final Follower f;
    private static InnerModes innerMode = InnerModes.COUNT;
    private static ArrayList<Consumer<Colorable>> alerts;
    private static float alertTime = 0;
    public static final float alertDuration = 1;

    public Lights(HardwareMap hardwareMap, Follower f) {
        this.f = f;
        l1 = new RGBIndicator(hardwareMap, LIGHT1);
        l2 = new RGBIndicator(hardwareMap, LIGHT2);
        l3 = new RGBIndicator(hardwareMap, LIGHT3);
        alerts = new ArrayList<>();
        alertTime = 0;
    }

    private Colorable getCellLight() {
        if (PoseLib.getActiveCell() == PoseLib.Cell.NECTAR) {
            if (f.pose().heading() < 180) return l1;
        }
        return l3;
    }

    private Colorable getAlertLight() {
        if (PoseLib.getActiveCell() == PoseLib.Cell.NECTAR) {
            if (f.pose().heading() > 180) return l1;
        }
        return l3;
    }

    private void setCellLight() {
        if (Alliance.get() == Alliance.Color.BLUE) {
            getCellLight().blue();
            return;
        }
        getCellLight().red();
    }

    private void setAlert() {
        try {
            alerts.get(0).accept(getAlertLight());
            alertTime = System.nanoTime();
        } catch (NullPointerException e) {
            getAlertLight().off();
        }

    }

    private void updateAlertLight() {
        if (alerts.isEmpty()) return;
        if (System.nanoTime() - alertTime > alertDuration || alertTime == 0) {
            if (alerts.size() == 1) {
                setAlert();
                alerts.remove(0);
            }
            alerts.remove(0);
            setAlert();
        }
    }

    private void updateInnerLight() {
        int nBalls = Stopper.getNumBalls();
        switch(innerMode) {
            case COUNT:
                switch (nBalls) {
                    case 0:
                        l2.off();
                    case 1:
                        l2.white();
                        break;
                    case 2:
                        l2.yellow();
                        break;
                    case 3:
                        l2.indigo();
                        break;
                    case 4:
                        l2.green();
                        break;
                    default:
                        queueAlert(Colorable::orange);
                }
                break;
            case COUNT_UNTIL_FULL: //this seems useless in retrospect
                switch (nBalls) {
                    case 0:
                        l2.red();
                        break;
                    case 1:
                        l2.white();
                        break;
                    case 2:
                        l2.yellow();
                        break;
                    case 3:
                        l2.indigo();
                        break;
                    case 4:
                        l2.off();
                        break;
                    default:
                        queueAlert(Colorable::orange);
                }
                break;
            case OFF_UNTIL_FULL:
                if (nBalls == 4) {
                    l2.green();
                } else if (nBalls > 4 || nBalls < 0) {
                    queueAlert(Colorable::orange);
                }
                break;
            case ON_UNTIL_FULL:
                if (nBalls < 4) {
                    l2.green();
                } else if (nBalls == 4) {
                    l2.off();
                } else {
                    queueAlert(Colorable::orange);
                }
                break;
        }
    }

    public static void queueAlert(Consumer<Colorable> alert) {
        alerts.add(alert);
    }

    public void loop() {
        setCellLight(); //TODO: OPTIMIZE
        updateAlertLight();
        updateInnerLight();
    }

}
