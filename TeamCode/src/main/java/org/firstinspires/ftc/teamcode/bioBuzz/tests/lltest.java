package org.firstinspires.ftc.teamcode.bioBuzz.tests;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;

import org.firstinspires.ftc.teamcode.bioBuzz.helpers.Alliance;
import org.firstinspires.ftc.teamcode.bioBuzz.helpers.ballistics.TargetHelper;
import org.firstinspires.ftc.teamcode.bioBuzz.robot.subsystems.LL;

@TeleOp
public class lltest extends OpMode {
    private Limelight3A ll;
    private AnalogInput s;
    @Override
    public void init() {
        Alliance.set(Alliance.Color.BLUE);
        LL llm = new LL(hardwareMap);
        ll = hardwareMap.get(Limelight3A.class, "limelight");
        s = hardwareMap.get(AnalogInput.class, "as");
    }

    @Override
    public void loop() {
        LLResult result = ll.getLatestResult();
        LLResultTypes.FiducialResult fr = TargetHelper.getIdealResult(result);

        telemetry.addData("getTargetPoseCameraSpace", fr.getTargetPoseCameraSpace().toString());
        telemetry.addData("getRobotPoseTargetSpace", fr.getRobotPoseTargetSpace().toString());
        telemetry.addData("getTargetPoseRobotSpace", fr.getTargetPoseRobotSpace().toString());

        telemetry.addData("volts", s.getVoltage());
    }
}
