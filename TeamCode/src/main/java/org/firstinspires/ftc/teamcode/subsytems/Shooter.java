package org.firstinspires.ftc.teamcode.subsytems;

import static com.seattlesolvers.solverslib.hardware.motors.Motor.ZeroPowerBehavior.FLOAT;

import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;


public class Shooter extends SubsystemBase {

  private final Motor rightShooter;
    private double currentPercent = 0;
  private final Motor leftShooter;
  private final MotorGroup shooterMotors;
//private final Servo breakerLeft;
//private final Servo breakerRight;

    public Shooter(HardwareMap hardwareMap){
        rightShooter = new Motor(hardwareMap, "rightShooter",
                RobotConstants.shooterConstants.CLICKS_PER_REV,
                RobotConstants.shooterConstants.MAX_HD_HEX);
        leftShooter = new Motor(hardwareMap, "leftShooter",
                RobotConstants.shooterConstants.CLICKS_PER_REV,
                RobotConstants.shooterConstants.MAX_HD_HEX);
        shooterMotors = new MotorGroup(leftShooter, rightShooter);

        shooterMotors.setVeloCoefficients(
                RobotConstants.shooterConstants.kP,
                RobotConstants.shooterConstants.kI,
                RobotConstants.shooterConstants.kD);//Set PID Value
        shooterMotors.setFeedforwardCoefficients(
                RobotConstants.shooterConstants.kS,
                RobotConstants.shooterConstants.kV);
        shooterMotors.setRunMode(Motor.RunMode.VelocityControl);
        shooterMotors.setZeroPowerBehavior(FLOAT);
//        this.breakerLeft = hardwareMap.get(Servo.class, "breakerLeft");
//        this.breakerRight = hardwareMap.get(Servo.class, "breakerRight");
//        this.breakerLeft.setDirection(Servo.Direction.FORWARD);
//        this.breakerRight.setDirection(Servo.Direction.REVERSE);
        register();
    }

    public double getTargetVelocity() {
        return currentPercent;
    }//access

    public double getLeftVelocity(){
        return Math.abs(leftShooter.getCorrectedVelocity());
    }
    public double getRightVelocity(){
        return Math.abs(rightShooter.getCorrectedVelocity());
    }

    public double getTargetTicksPerSec(){
        return currentPercent *
               RobotConstants.shooterConstants.MAX_HD_HEX *
               RobotConstants.shooterConstants.CLICKS_PER_REV / 60.0;

    }
    public void updateCoefficiants(){
        shooterMotors.setVeloCoefficients(
                RobotConstants.shooterConstants.kP,
                RobotConstants.shooterConstants.kI,
                RobotConstants.shooterConstants.kD);//Set PID Value
        shooterMotors.setFeedforwardCoefficients(
                RobotConstants.shooterConstants.kS,
                RobotConstants.shooterConstants.kV);
    }
    public void shoot(){
        this.currentPercent =
                RobotConstants.shooterConstants.TARGET_RPM /
                RobotConstants.shooterConstants.MAX_HD_HEX;
    }

    public void stop(){
    currentPercent = 0;
    }

    public void openBreaker(){
//        breakerLeft.setPosition(RobotConstants.shooterConstants.breakerOpenPos);
//        breakerRight.setPosition(RobotConstants.shooterConstants.breakerOpenPos);
    }
    public void closeBreaker(){
//        breakerLeft.setPosition(RobotConstants.shooterConstants.breakerClosePos);
//        breakerRight.setPosition(RobotConstants.shooterConstants.breakerClosePos);
    }
    public void end(){
        stop();
//        breakerLeft.setPosition(RobotConstants.shooterConstants.breakerClosePos);
//        breakerRight.setPosition(RobotConstants.shooterConstants.breakerClosePos);
    }
    public double velocity(){return (getLeftVelocity()+getRightVelocity())/2;}

    public boolean targetVelocity(double target){return(Math.abs(velocity())>=Math.abs(target));}

    @Override
    public void periodic(){
       ;//we need to set S and V Value
        shooterMotors.set(currentPercent);


    }
}
