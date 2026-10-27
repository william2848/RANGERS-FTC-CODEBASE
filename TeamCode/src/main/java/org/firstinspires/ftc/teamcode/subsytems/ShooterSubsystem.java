package org.firstinspires.ftc.teamcode.subsytems;

import static com.seattlesolvers.solverslib.hardware.motors.Motor.ZeroPowerBehavior.FLOAT;



import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;



public class ShooterSubsystem extends SubsystemBase {

  private final Motor rightShooter;
    private double currentPercent = 0;
  private final Motor leftShooter;
  private final MotorGroup shooterMotors;

    public ShooterSubsystem(HardwareMap hardwareMap){
        rightShooter = new Motor(hardwareMap, "rightShooter",
                RobotConstants.shooterConstants.CLICKS_PER_REV,
                RobotConstants.shooterConstants.MAX_HD_HEX);
        leftShooter = new Motor(hardwareMap, "leftShooter",
                RobotConstants.shooterConstants.CLICKS_PER_REV,
                RobotConstants.shooterConstants.MAX_HD_HEX);
        shooterMotors = new MotorGroup(leftShooter, rightShooter);
        rightShooter.setInverted(true);
        shooterMotors.setVeloCoefficients(
                RobotConstants.shooterConstants.kP,
                RobotConstants.shooterConstants.kI,
                RobotConstants.shooterConstants.kD);//Set PID Value
        shooterMotors.setFeedforwardCoefficients(
                RobotConstants.shooterConstants.kS,
                RobotConstants.shooterConstants.kV);
        shooterMotors.setRunMode(Motor.RunMode.VelocityControl);
        shooterMotors.setZeroPowerBehavior(FLOAT);//Instead of hard braking, Flywheel Motors coasts down
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
    public void shoot(){
        this.currentPercent =
                RobotConstants.shooterConstants.TARGET_RPM /
                RobotConstants.shooterConstants.MAX_HD_HEX;
    }

    public void stop(){
    currentPercent = 0;
    }

    @Override
    public void periodic(){//temporary for PID Tuning
       ;//Set S and V Value
        shooterMotors.set(currentPercent);


    }
}
