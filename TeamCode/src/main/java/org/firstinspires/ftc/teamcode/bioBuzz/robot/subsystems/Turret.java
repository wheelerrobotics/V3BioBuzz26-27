package org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.ballistics.TargetHelper;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.hardware.HardwareNames;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.lighting.Colorable;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.lighting.Lights;
import org.firstinspires.ftc.teamcode.util.DashboardTelemetry;

// This is made for two positional servos
@Config
public class Turret {
    private final Servo servo1;
    private final Servo servo2;
    public static final Servo.Direction SERVO_1_DIRECTION = Servo.Direction.REVERSE;
    public static final Servo.Direction SERVO_2_DIRECTION = Servo.Direction.REVERSE;


    // Servo to turret
    public static final double servoRatio = 1/1.8/360;
    public static final double minAngleDegrees = -90;
    public static final double maxAngleDegrees = 90;

    public static double llmultiplier = 1;
    private final Limelight3A ll;
    public Turret(HardwareMap hardwareMap) {
        servo1 = hardwareMap.get(Servo.class, HardwareNames.TURRET_SERVO_1);
        servo2 = hardwareMap.get(Servo.class, HardwareNames.TURRET_SERVO_2);

        servo1.setDirection(SERVO_1_DIRECTION);
        servo2.setDirection(SERVO_2_DIRECTION);


        ll = hardwareMap.get(Limelight3A.class, "limelight");
    }

    public void turnTo(double deg) {
        deg = Range.clip(
                deg,
                minAngleDegrees,
                maxAngleDegrees
        );

        if (Double.isNaN(deg)) {
            Lights.queueAlert(Colorable::sage);
            DashboardTelemetry.getInstance().addData("turnTo()", "bad");
            return;
        }
        DashboardTelemetry.getInstance().addData("turnTo()", "good");


        double servoPos = ((deg+90) * servoRatio);
        DashboardTelemetry.getInstance().addData("turnTo() servoPos", servoPos);

        servo1.setPosition(servoPos);
        servo2.setPosition(servoPos);
    }

    public void update() {
        LLResultTypes.FiducialResult result = TargetHelper.getIdealResult(ll.getLatestResult());
        double deg = result !=  null ? result.getTargetXDegrees() : 0;
//                Math.toDegrees(Ballistics.getResult().getTurretAngleRS());
        DashboardTelemetry.getInstance().addData("Turret: update() deg", deg);
        turnTo(-deg * llmultiplier);
    }

}
