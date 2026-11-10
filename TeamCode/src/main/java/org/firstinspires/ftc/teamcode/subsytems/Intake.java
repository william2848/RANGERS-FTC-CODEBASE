package org.firstinspires.ftc.teamcode.subsytems;

import static com.seattlesolvers.solverslib.hardware.motors.Motor.ZeroPowerBehavior.FLOAT;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

public class Intake extends SubsystemBase {

    private final Motor intake;
    private final Motor transition;

    public Intake(HardwareMap hardwareMap){
        intake = new Motor(hardwareMap,"intake");
        transition = new Motor(hardwareMap, "transition");
        intake.setRunMode(Motor.RunMode.RawPower);
        transition.setRunMode(Motor.RunMode.RawPower);
        intake.setZeroPowerBehavior(FLOAT);
        transition.setZeroPowerBehavior(FLOAT);
        register();
    }

    public void setIntakeAndTransition(double intake, double transition){
        this.intake.set(-intake);
        this.transition.set(transition);
    }

    public void setTransitionOnly(double power){
        this.transition.set(power);
    }

    public Command stop(){return new InstantCommand(()->setIntakeAndTransition(0,0));}

    public Command runIntake(double intakePower, double transitionPower){return new InstantCommand(()->setIntakeAndTransition(intakePower, -transitionPower),this);}
    public Command stopIntake(){return new InstantCommand(this::stop,this);}

}
