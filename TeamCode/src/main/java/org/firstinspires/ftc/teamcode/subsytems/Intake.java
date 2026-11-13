package org.firstinspires.ftc.teamcode.subsytems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.Commands;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private DcMotorEx intakeMotor;
    public Intake(HardwareMap hardwareMap){
        intakeMotor = hardwareMap.get(DcMotorEx.class, "transition");
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void setIntakePower(double power){
        intakeMotor.setPower(power);
    }

    public Command runIntake(){
        return Commands.runOnce(()-> setIntakePower(1),this);
    }

    public Command stopIntake(){
        return Commands.runOnce(()-> setIntakePower(0),this);
    }

    public Command reverseIntake(){
        return Commands.runOnce(()-> setIntakePower(-1),this);
    }
}
