package org.firstinspires.ftc.teamcode.subsytems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Shooter extends SubsystemBase {
private final DcMotorEx shooterMotorLeft;
private final DcMotorEx shooterMotorRight;
private final Servo breakerLeft;
private final Servo breakerRight;
    public Shooter(HardwareMap hardwareMap){
        this.shooterMotorLeft = hardwareMap.get(DcMotorEx.class, "shooterLeft");
        this.shooterMotorRight = hardwareMap.get(DcMotorEx.class, "shooterRight");
        this.shooterMotorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        this.shooterMotorRight.setDirection(DcMotorSimple.Direction.REVERSE);
        this.breakerLeft = hardwareMap.get(Servo.class, "breakerLeft");
        this.breakerRight = hardwareMap.get(Servo.class, "breakerRight");
        this.breakerLeft.setDirection(Servo.Direction.FORWARD);
        this.breakerRight.setDirection(Servo.Direction.REVERSE);
        register();
    }
    public void runShooter(double velocity){
        shooterMotorLeft.setVelocity(velocity);
        shooterMotorRight.setVelocity(velocity);
    }
    public void openBreaker(){
        breakerLeft.setPosition(Constants.shooterConstants.breakerOpenPos);
        breakerRight.setPosition(Constants.shooterConstants.breakerOpenPos);
    }
    public void closeBreaker(){
        breakerLeft.setPosition(Constants.shooterConstants.breakerClosePos);
        breakerRight.setPosition(Constants.shooterConstants.breakerClosePos);
    }
    public void end(){
        shooterMotorLeft.setVelocity(0);
        shooterMotorRight.setVelocity(0);
        breakerLeft.setPosition(Constants.shooterConstants.breakerClosePos);
        breakerRight.setPosition(Constants.shooterConstants.breakerClosePos);
    }
    public double velocity(){return (shooterMotorLeft.getVelocity()+shooterMotorRight.getVelocity())/2;}

    public boolean targetVelocity(double target){return(Math.abs(velocity())>=Math.abs(target));}
}
