package org.firstinspires.ftc.teamcode.subsytems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private DcMotorEx intake;
    private DcMotorEx transfer;
    public Intake(HardwareMap hardwareMap){
        this.intake = hardwareMap.get(DcMotorEx.class, "intake");
        this.intake.setDirection(DcMotorSimple.Direction.FORWARD);
        this.transfer = hardwareMap.get(DcMotorEx.class,"transfer");
        this.transfer.setDirection(DcMotorSimple.Direction.FORWARD);
        register();
    }
    public void idle(){this.transfer.setVelocity(Constants.intakeConstants.idleTransfer);}
    public void runTransfer(double velocity){this.transfer.setVelocity(velocity);}
    public Command runIntake(double intakeVelocity, double transferVelocity){return new InstantCommand(()->{this.intake.setVelocity(intakeVelocity); this.transfer.setVelocity(transferVelocity);},this);}
    public Command stopIntake(){return new InstantCommand(()->{this.intake.setVelocity(0);this.idle();},this);}

}
