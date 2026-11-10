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
  private Telemetry telemetry;

    public Shooter(HardwareMap hardwareMap, Telemetry shooterTelemetry){
        rightShooter = new MotorEx(hardwareMap, "rightShooter", RobotConstants.shooterConstants.CLICKS_PER_REV, RobotConstants.shooterConstants.MAX_HD_HEX);
        leftShooter = new MotorEx(hardwareMap, "leftShooter", RobotConstants.shooterConstants.CLICKS_PER_REV, RobotConstants.shooterConstants.MAX_HD_HEX);
        shooterMotors = new MotorGroup(leftShooter, rightShooter);
        shooterMotors.setVeloCoefficients(RobotConstants.shooterConstants.kP, RobotConstants.shooterConstants.kI, RobotConstants.shooterConstants.kD);//Set PID Value
        shooterMotors.setFeedforwardCoefficients(RobotConstants.shooterConstants.kS, RobotConstants.shooterConstants.kV);
        shooterMotors.setRunMode(Motor.RunMode.VelocityControl);
        shooterMotors.setZeroPowerBehavior(FLOAT);
        telemetry = shooterTelemetry;
        register();
    }
    public Shooter(HardwareMap hardwareMap){
        rightShooter = new MotorEx(hardwareMap, "rightShooter", RobotConstants.shooterConstants.CLICKS_PER_REV, RobotConstants.shooterConstants.MAX_HD_HEX);
        leftShooter = new MotorEx(hardwareMap, "leftShooter", RobotConstants.shooterConstants.CLICKS_PER_REV, RobotConstants.shooterConstants.MAX_HD_HEX);
        shooterMotors = new MotorGroup(leftShooter, rightShooter);
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
                RobotConstants.shooterConstants.kD);//Set PID Value
        shooterMotors.setFeedforwardCoefficients(
                RobotConstants.shooterConstants.kS,
                RobotConstants.shooterConstants.kV);
    }
    public void shootMax(){shooterMotors.set(1);}
    public void shoot(){shooterMotors.set(RobotConstants.shooterConstants.TARGETVelPercent);}

    public Command shootCommand(){return new InstantCommand(this::shoot);}
    public Command stopCommand(){return new InstantCommand(this::shoot);}
    public void stop(){shooterMotors.set(0);}

    public void end(){stop();}
    public double getVelocity(){return shooterMotors.getVelocity();}


    public boolean isAtSpeed(){return(shooterMotors.getVelocity()/RobotConstants.shooterConstants.MAXVel>=(RobotConstants.shooterConstants.TARGETVelPercent));}
    public Command shootSequence(Intake intake, GamepadEx driver, Lightbar lightbar){
        return  new SequentialCommandGroup(
                new InstantCommand(lightbar::red),
                new InstantCommand(this::shoot),
                new WaitUntilCommand(this::isAtSpeed),
                new InstantCommand(lightbar::green),
                new WaitUntilCommand(()-> driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)>=0.8),
                new InstantCommand(()->intake.setTransitionOnly(RobotConstants.intakeConstants.transitionPower)),
                new InstantCommand(lightbar::red),
                new WaitCommand(RobotConstants.otherConstants.waitForEmpty)
        ).interruptOn(()->driver.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)).whenFinished(()->{this.end(); intake.setIntakeAndTransition(0,0);});
    }

    @Override
    public void periodic(){
       ;//we need to set S and V Value
        telemetry.addData("Shooter Velocity", this.getVelocity());


    }
}
