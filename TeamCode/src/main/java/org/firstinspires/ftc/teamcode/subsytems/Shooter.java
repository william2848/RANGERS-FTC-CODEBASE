package org.firstinspires.ftc.teamcode.subsytems;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.Commands;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private DcMotorEx flyWheel;

    private DcMotorEx topRoller;

    private boolean isOpen = false;

    private Servo hold;

    private double flyWheelsetVelocity = 0;
    private double topRollersetVelocity = 0;

    private double targetDist = 3.33;
    public Shooter(HardwareMap hardwareMap){
        flyWheel = hardwareMap.get(DcMotorEx.class, "flyWheel");
        topRoller = hardwareMap.get(DcMotorEx.class, "topRoller");
        hold = hardwareMap.get(Servo.class, "hold");

        flyWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        topRoller.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        flyWheel.setVelocityPIDFCoefficients(35,5,0,0);
        topRoller.setVelocityPIDFCoefficients(35,5,0,0);

//        flyWheel.setDirection(DcMotorSimple.Direction.REVERSE);
//        topRoller.setDirection(DcMotorSimple.Direction.REVERSE);

        close();
        register();
    }

    public void periodic(){

        flyWheelsetVelocity = targetDist * 250;
        topRollersetVelocity = targetDist * 250;

        Log.d("flywheel Velocity / Desired", "" + flyWheel.getVelocity() + " " + flyWheelsetVelocity);
    }

    public boolean readyToShoot(){
        return Math.abs(topRoller.getVelocity()-topRollersetVelocity) < 30 &&
                Math.abs(flyWheel.getVelocity()-flyWheelsetVelocity) < 30;
    }

    private void startShoot(){
        flyWheel.setVelocity(flyWheelsetVelocity);
        topRoller.setVelocity(topRollersetVelocity);
    }

    public boolean getIsOpen(){
        return isOpen;
    }

    private void stop(){
        flyWheel.setVelocity(0);
        topRoller.setVelocity(0);
    }

    private void open(){
        hold.setPosition(.5);
        isOpen = true;
    }

    private void close(){
        hold.setPosition(0.1);
        isOpen = false;
    }


    public Command shoot(){
        return Commands.startRun(()->startShoot(),
                ()-> {
            if(readyToShoot()){
                open();
            } else{
                close();
            }
        },this
                );
    }

    public Command idle(){
        return Commands.runOnce(()->{
            stop();
            close();
        },this);
    }
}
