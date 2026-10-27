package org.firstinspires.ftc.teamcode.subsytems;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;
import com.seattlesolvers.solverslib.hardware.servos.ServoExGroup;

public class ServoSubsystem extends SubsystemBase {//For now Manual, but later change so when shooter 95% charged

    private final ServoEx leftServo;
    private final ServoEx rightServo;

    private final ServoExGroup servoGroup;


    public ServoSubsystem(HardwareMap hardwareMap){
        leftServo = new ServoEx(hardwareMap,"leftServo");
        rightServo = new ServoEx(hardwareMap, "rightServo");
        rightServo.setInverted(true);
        servoGroup = new ServoExGroup(leftServo, rightServo);
        servoGroup.set(RobotConstants.servoConstants.startPos);//Default Position, to be changed

    }

    public void setPosition(double position){

        servoGroup.set(position);
    }



}
