package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


@TeleOp(name = "autonomous")

public class pinpointCheck extends LinearOpMode {

    private DcMotorEx odoX;
    private DcMotorEx odoY;

    @Override
    public void runOpMode() throws InterruptedException {
        odoX = hardwareMap.get(DcMotorEx.class,"odoX");
        odoX.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        odoY = hardwareMap.get(DcMotorEx.class,"odoY");
        odoY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Status", "Initalized");
        telemetry.update();

        waitForStart();

    }



}
