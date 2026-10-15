package org.firstinspires.ftc.teamcode.subsytems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class intake extends SubsystemBase {
    private DcMotorEx intake;
    private DcMotorEx transfer;
    public intake(HardwareMap hardwareMap){
        this.intake = hardwareMap.get(DcMotorEx.class, "intake");
        this.intake.setDirection(DcMotorSimple.Direction.FORWARD);
        this.transfer = hardwareMap.get(DcMotorEx.class,"transfer");
        this.transfer.setDirection(DcMotorSimple.Direction.FORWARD);
        register();
    }
    public void idle(){this.transfer.setVelocity(Constants.intakeConstants.idleTransfer);}
    public void runTransfer(double velocity){this.transfer.setVelocity(velocity);}
    public void runIntake(double intakeVelocity, double transferVelocity){
        this.intake.setVelocity(intakeVelocity);
        this.transfer.setVelocity(transferVelocity);
    }
}
