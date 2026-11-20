package org.firstinspires.ftc.teamcode.subsytems;

import static com.seattlesolvers.solverslib.hardware.motors.Motor.ZeroPowerBehavior.FLOAT;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

public class Intake extends SubsystemBase {

    private enum TransitionMode { MANUAL, VELOCITY }

    private final Motor intake;
    private TransitionSubsystem transitionSubsystem;
    private TransitionMode mode = TransitionMode.MANUAL;
    private double transitionOutput = 0;   // raw power OR normalized velocity target
    public Intake(HardwareMap hardwareMap){
        intake = new Motor(hardwareMap,"intake");
        transitionSubsystem = new TransitionSubsystem(hardwareMap);
        intake.setZeroPowerBehavior(FLOAT);
        register();
    }

    public void setIntakeAndTransition(double intake, double transition){
        mode = TransitionMode.MANUAL;
        transitionSubsystem.getServoGroup().setRunMode(Motor.RunMode.RawPower);
        this.intake.set(-intake);
        this.transitionSubsystem.setTransitionServo(transition);
    }

    public void setTransition(){
        mode = TransitionMode.VELOCITY;
        transitionSubsystem.getServoGroup().setRunMode(Motor.RunMode.VelocityControl);
        transitionSubsystem.getServoGroup().setVeloCoefficients(
                RobotConstants.intakeConstants.kP,
                RobotConstants.intakeConstants.kI,
                RobotConstants.intakeConstants.kD
        );
        transitionSubsystem.getServoGroup().setFeedforwardCoefficients(
                RobotConstants.intakeConstants.kS,
                RobotConstants.intakeConstants.kV);
        transitionOutput = RobotConstants.intakeConstants.TARGET_RPM_TRANS
                / RobotConstants.intakeConstants.MAX_RPM;
    }
    public void setIntake(double intake){
        this.intake.set(-intake);

    }
    public double getTransitionRPM(){
        return Math.abs(transitionSubsystem.getServoGroup().getCorrectedVelocity()) * 60.0 / 28;
}
    public void stopTransition(){

        mode = TransitionMode.MANUAL;
        transitionSubsystem.getServoGroup().setRunMode(Motor.RunMode.RawPower);
        transitionSubsystem.stop();
    }
    public void setTransitionOnly(double power){
        mode = TransitionMode.MANUAL;
        transitionSubsystem.getServoGroup().setRunMode(Motor.RunMode.RawPower);
        transitionSubsystem.setTransitionServo(power);
    }
    public double getTargetTransitionVel(){
        return RobotConstants.intakeConstants.TARGET_RPM_TRANS;
    }


    public Command outtake(){return new InstantCommand(()->this.setIntakeAndTransition(-1,-1));}
    public void stop(){setIntakeAndTransition(0,0);}

    public Command runIntake(double intakePower, double transitionPower){return new InstantCommand(()->setIntakeAndTransition(intakePower, -transitionPower),this);}
    public Command stopIntake(){return new InstantCommand(this::stop,this);}

    @Override
    public void periodic(){

    }

}
