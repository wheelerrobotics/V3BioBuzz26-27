//package org.firstinspires.ftc.teamcode.opmodes.debug.robotDebug;
//
//import com.bylazar.configurables.annotations.Configurable;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.bylazar.telemetry.PanelsTelemetry;
//import com.bylazar.telemetry.TelemetryManager;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import org.firstinspires.ftc.teamcode.opmodes.debug.robotDebug.RobotDebuggerVals.*;
//import org.firstinspires.ftc.teamcode.robot.Robot;
//
///**
// * Season-specific control panel for testing the complete robot in real time.
// *
// * Add another nested debug section and update method whenever a subsystem is
// * added. Put mechanism behavior in the subsystem; this OpMode should only set
// * targets, enable tuning controls, and display live state.
// */
//@Configurable
//@TeleOp(name = "Robot Debugger", group = "Debug")
//public class RobotDebugger extends OpMode {
//    private TelemetryManager telemetryM;
//
//    public static boolean MASTER_ENABLE = true;
//
//    private Robot robot;
//
//    @Override
//    public void init() {
//        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
//        robot = new Robot(hardwareMap);
//        stopDrivetrain();
//
//        telemetryM.addLine("Robot Debugger initialized");
//        telemetryM.addLine("Enable MASTER_ENABLE and the desired subsystem in Panels");
//        telemetryM.update(telemetry);
//    }
//
//    @Override
//    public void loop() {
//        updateDrivetrain();
//        robot.tick(gamepad1,gamepad2);
//
//        telemetryM.addData("Master Enable", MASTER_ENABLE);
//        telemetryM.addLine("================================");
//        telemetryM.update(telemetry);
//    }
//
//    private void updateDrivetrain() {
//        if (!MASTER_ENABLE || !DriveDebug.ENABLED) {
//            stopDrivetrain();
//            return;
//        }
//
//        if (DriveDebug.CONTROLLER) {
//            double x = gamepad1.left_stick_x;
//            double y = -gamepad1.left_stick_y;
//            double rotation = gamepad1.right_stick_x;
//
////            robot.motorDriveXYVectors(x,y,rotation);
//        } else {
//            robot.frontLeft.setPower(DriveDebug.FRONT_LEFT_POWER);
//            robot.frontRight.setPower(DriveDebug.FRONT_RIGHT_POWER);
//            robot.backLeft.setPower(DriveDebug.BACK_LEFT_POWER);
//            robot.backRight.setPower(DriveDebug.BACK_RIGHT_POWER);
//        }
//    }
//    private void stopDrivetrain() {
//        robot.frontLeft.setPower(0);
//        robot.frontRight.setPower(0);
//        robot.backLeft.setPower(0);
//        robot.backRight.setPower(0);
//    }
//
//    @Override
//    public void stop() {
//        stopDrivetrain();
//    }
//}
