package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsytems.RobotConstants;

import org.firstinspires.ftc.teamcode.subsytems.Intake;
import org.firstinspires.ftc.teamcode.subsytems.Shooter;

public class shootSequence extends SequentialCommandGroup {
    public shootSequence(Shooter shooter, Intake intake){
        addCommands(
                new SequentialCommandGroup(
                new InstantCommand(shooter::shoot),
                new WaitUntilCommand(()->shooter.targetVelocity(shooter.getTargetTicksPerSec())),
                new InstantCommand(()->intake.setTransitionOnly(RobotConstants.intakeConstants.transitionPower)),
                new InstantCommand(shooter::openBreaker),
                new WaitCommand(RobotConstants.otherConstants.waitForEmpty)
                        ).whenFinished(()->{shooter.end(); intake.stop();})
        );
    }
}
