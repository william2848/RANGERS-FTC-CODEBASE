package org.firstinspires.ftc.teamcode.subsytems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.CRServoEx;
import com.seattlesolvers.solverslib.hardware.motors.CRServoGroup;

public class TransitionSubsystem extends SubsystemBase {

    private final CRServoEx servoLeft;
    private final CRServoEx servoRight;
    private final CRServoGroup servoGroup;
    private double power;


    public TransitionSubsystem(HardwareMap hardwareMap){
        servoLeft = new CRServoEx(hardwareMap,"servoLeft");
        servoRight = new CRServoEx(hardwareMap,"servoRight");
        servoRight.setInverted(true);
        servoGroup = new CRServoGroup(servoLeft,servoRight);

    }
    public CRServoGroup getServoGroup(){return servoGroup;}
    public void setTransitionServo(double power){
        this.power = power;
    }
    public void stop(){
        this.power = 0;
    }

    @Override
    public void periodic(){
        servoGroup.set(power);
    }
}
