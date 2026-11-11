//package org.firstinspires.ftc.teamcode.OpModes;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//
//@TeleOp(name = "ShooterRawTest")
//public class ShooterRawTest extends LinearOpMode {
//    @Override
//    public void runOpMode() {
//        DcMotorEx left = hardwareMap.get(DcMotorEx.class, "leftShooter");
//        DcMotorEx right = hardwareMap.get(DcMotorEx.class, "rightShooter");
//        waitForStart();
//        while (opModeIsActive()) {
//            left.setPower(gamepad1.a ? 1.0 : 0);
//            right.setPower(gamepad1.b ? 1.0 : 0);
//            telemetry.addData("left vel", left.getVelocity());
//            telemetry.addData("right vel", right.getVelocity());
//            telemetry.update();
//        }
//    }
//}
