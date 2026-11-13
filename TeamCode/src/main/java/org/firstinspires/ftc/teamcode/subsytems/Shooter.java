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
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter extends SubsystemBase {

  private final MotorEx rightShooter;
    private double setpoint;
  private final MotorEx leftShooter;
  private final MotorGroup shooterMotors;
  private double targetRPM;
  private Telemetry telemetry;

    public Shooter(HardwareMap hardwareMap, Telemetry shooterTelemetry){
        rightShooter = new MotorEx(hardwareMap, "rightShooter", RobotConstants.shooterConstants.TICKS_PER_SEC, RobotConstants.shooterConstants.MAX_RPM);
        leftShooter = new MotorEx(hardwareMap, "leftShooter", RobotConstants.shooterConstants.TICKS_PER_SEC, RobotConstants.shooterConstants.MAX_RPM);
        shooterMotors = new MotorGroup(leftShooter.setInverted(true), rightShooter);
        shooterMotors.setBuffer(RobotConstants.shooterConstants.VEL_BUFFER);
        shooterMotors.setVeloCoefficients(RobotConstants.shooterConstants.kP, RobotConstants.shooterConstants.kI, RobotConstants.shooterConstants.kD);//Set PID Value
        shooterMotors.setFeedforwardCoefficients(RobotConstants.shooterConstants.kS, RobotConstants.shooterConstants.kV);
        shooterMotors.setZeroPowerBehavior(FLOAT);
        telemetry = shooterTelemetry;
        register();
    }
    public Shooter(HardwareMap hardwareMap){
        rightShooter = new MotorEx(hardwareMap, "rightShooter", RobotConstants.shooterConstants.TICKS_PER_SEC, RobotConstants.shooterConstants.MAX_RPM);
        leftShooter = new MotorEx(hardwareMap, "leftShooter", RobotConstants.shooterConstants.TICKS_PER_SEC, RobotConstants.shooterConstants.MAX_RPM);
        shooterMotors = new MotorGroup(leftShooter, rightShooter);
        leftShooter.setBuffer(RobotConstants.shooterConstants.VEL_BUFFER);
        shooterMotors.setVeloCoefficients(RobotConstants.shooterConstants.kP, RobotConstants.shooterConstants.kI, RobotConstants.shooterConstants.kD);//Set PID Value
        shooterMotors.setFeedforwardCoefficients(RobotConstants.shooterConstants.kS, RobotConstants.shooterConstants.kV);
        shooterMotors.setRunMode(Motor.RunMode.VelocityControl);
        shooterMotors.setZeroPowerBehavior(FLOAT);

        register();
    }


    public void updateCoefficiants(){
        shooterMotors.setVeloCoefficients(
                RobotConstants.shooterConstants.kP,
                RobotConstants.shooterConstants.kI,
                RobotConstants.shooterConstants.kD);
        shooterMotors.setFeedforwardCoefficients(
                RobotConstants.shooterConstants.kS,
                RobotConstants.shooterConstants.kV);
    }
    public void shootMax(){
        shooterMotors.setRunMode(Motor.RunMode.VelocityControl);
        setpoint = 1;
    }
    public void idle(){shooterMotors.set(RobotConstants.shooterConstants.IDLE_POWER);}
    public void shoot(double targetRPM){
        if(targetRPM==0)
            this.targetRPM = RobotConstants.shooterConstants.TARGET_RPM;
        else
            this.targetRPM = targetRPM;
        shooterMotors.setRunMode(Motor.RunMode.VelocityControl);
        setpoint = this.targetRPM/ (RobotConstants.shooterConstants.MAX_RPM * RobotConstants.shooterConstants.VEL_BUFFER);
    }
    public double getTargetRPM(){return this.targetRPM;}
    public Command stopCommand(){return new InstantCommand(this::stop);}
    public void stop(){
        shooterMotors.setRunMode(Motor.RunMode.RawPower);
        setpoint = 0;
    }

    public void end(){stop();}
    public double getVelocity(){return shooterMotors.getVelocity();}
    public double getLeftRPM(){ return Math.abs(leftShooter.getCorrectedVelocity()) * 60.0 / RobotConstants.shooterConstants.TICKS_PER_SEC; }
    public double getRightRPM(){ return Math.abs(rightShooter.getCorrectedVelocity()) * 60.0 / RobotConstants.shooterConstants.TICKS_PER_SEC; }
    public double getRPM(){ return (getLeftRPM() + getRightRPM()) / 2.0; }


    public boolean isAtSpeed(){
        return this.targetRPM > 0 && getLeftRPM() >= this.targetRPM * .95;
    }


    @Override
    public void periodic(){
        if(!(setpoint==0))
            shooterMotors.set(setpoint);

        else
            this.idle();

        if (telemetry != null) telemetry.addData("Shooter Velocity", this.getVelocity());


    }
}
