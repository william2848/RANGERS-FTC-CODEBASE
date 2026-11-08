package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsytems.Intake;
import org.firstinspires.ftc.teamcode.subsytems.Shooter;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;


public class ShootThenMove extends OpMode {

    private Follower follower;
    private Intake intake;
    private Shooter shooter;
    private final PoseFactory p = PoseFactory.degrees();
    private final Pose startPose = p.of(60,9,90);

    private final Pose park = p.of(9.6,97.33,90);
    private final Pose parkControlPose = p.of(26.31,34.22,90);




    private Path park(){
        return curve(startPose,parkControlPose,park).linear(startPose,park);
    }


    private Command shootStationary(){
        return sequential(
                instant(() -> shooter.shoot()),
                waitMs(3000),
                instant(() ->intake.setTransitionOnly(0.8)),
                waitMs(3000),
                instant(() -> shooter.stop()),
                instant(() -> intake.stop()));
    }

    private Command autoRoutine(){
        return sequential(
                shootStationary(),
                follow(follower,park())
                );

    }

    @Override
    public void init(){
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
    }

    @Override
    public void start(){
        schedule(autoRoutine());
    }

    @Override
    public void loop(){
        follower.update();
        Scheduler.execute();
        //getting poses and stuff
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        telemetry.addData("Follower Mode", follower.mode());
        telemetry.update();

    }



}
