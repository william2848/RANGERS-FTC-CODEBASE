package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsytems.Constants;

import org.firstinspires.ftc.teamcode.subsytems.Intake;
import org.firstinspires.ftc.teamcode.subsytems.Shooter;

public class shootSequence extends SequentialCommandGroup {
    public shootSequence(Shooter shooter, Intake intake){
        addCommands(
                new SequentialCommandGroup(
                new InstantCommand(()->shooter.runShooter(Constants.shooterConstants.runShooterMotorVel)),
                new WaitUntilCommand(()->shooter.targetVelocity(Constants.shooterConstants.runShooterMotorVel)),
                new InstantCommand(()->intake.runTransfer(Constants.intakeConstants.transferVel)),
                new InstantCommand(shooter::openBreaker),
                new WaitCommand(Constants.otherConstants.waitForEmpty)
                        ).whenFinished(()->{shooter.end(); intake.idle();})
        );
    }
}
