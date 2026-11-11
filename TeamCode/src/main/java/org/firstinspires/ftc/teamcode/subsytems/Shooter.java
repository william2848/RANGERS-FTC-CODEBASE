package org.firstinspires.ftc.teamcode.subsytems;

import static com.seattlesolvers.solverslib.hardware.motors.Motor.ZeroPowerBehavior.FLOAT;

import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;


public class Shooter extends SubsystemBase {

  private final Motor rightShooter;
    private double currentPercent = 0;
  private final Motor leftShooter;
  private final MotorGroup shooterMotors;
  private boolean shooting = false;
//private final Servo breakerLeft;
//private final Servo breakerRight;

    public Shooter(HardwareMap hardwareMap){
        rightShooter = new Motor(hardwareMap, "rightShooter",
                RobotConstants.shooterConstants.CLICKS_PER_REV,
                RobotConstants.shooterConstants.MAX_HD_HEX);
        leftShooter = new Motor(hardwareMap, "leftShooter",
                RobotConstants.shooterConstants.CLICKS_PER_REV,
                RobotConstants.shooterConstants.MAX_HD_HEX);
        leftShooter.setInverted(true);
        shooterMotors = new MotorGroup(leftShooter, rightShooter);

        shooterMotors.setRunMode(Motor.RunMode.VelocityControl);

        shooterMotors.setVeloCoefficients(
                RobotConstants.shooterConstants.kP,
                RobotConstants.shooterConstants.kI,
                RobotConstants.shooterConstants.kD);//Set PID Value
        shooterMotors.setFeedforwardCoefficients(
                RobotConstants.shooterConstants.kS,
                RobotConstants.shooterConstants.kV);
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

    public double getLeftRPM(){ return Math.abs(leftShooter.getCorrectedVelocity()) * 60.0 / RobotConstants.shooterConstants.CLICKS_PER_REV; }
    public double getRightRPM(){ return Math.abs(rightShooter.getCorrectedVelocity()) * 60.0 / RobotConstants.shooterConstants.CLICKS_PER_REV; }
    public double getRPM(){ return (getLeftRPM() + getRightRPM()) / 2.0; }

    public double getTargetTicksPerSec(){
      if(!shooting)
          return 0;


        return RobotConstants.shooterConstants.TARGET_RPM * RobotConstants.shooterConstants.CLICKS_PER_REV / 60.0;

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
      shooting = true;
        this.currentPercent =
                RobotConstants.shooterConstants.TARGET_RPM /
                RobotConstants.shooterConstants.MAX_HD_HEX;
    }

    public void stop(){
    shooting = false;
        currentPercent = 0.1;
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




//    public double getCurrentVelocity(){
//        return getLeftVelocity() * 60/28;
//    }
    public boolean isAtSpeed(){
        return shooting && getRPM() >= RobotConstants.shooterConstants.TARGET_RPM - 50;
    }
    public double getTargetRPM(){
        return RobotConstants.shooterConstants.TARGET_RPM;
    }


    public void readyToStart(){
    }
    public Command shootSequence(Intake intake){
        return  new SequentialCommandGroup(
                new InstantCommand(this::shoot),
                //new WaitUntilCommand(()->this.targetVelocity(this.getTargetTicksPerSec())),
                new InstantCommand(()->intake.setTransitionOnly(RobotConstants.intakeConstants.transitionPower)),
                new InstantCommand(this::openBreaker),
                new WaitCommand(RobotConstants.otherConstants.waitForEmpty)
        ).whenFinished(()->{this.end(); intake.stop();});
    }

    @Override
    public void periodic(){
       ;//we need to set S and V Value
        updateCoefficiants();

        shooterMotors.set(currentPercent);


    }
}
