package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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


@Autonomous(name = "MainAuto")
public class MainAuto extends OpMode {

    private Follower follower;
    private Intake intake;
    private Shooter shooter;
    private final PoseFactory p = PoseFactory.degrees();
    private final Pose startPose = p.of(56,36,90);
    private final Pose park = p.of(12,108,90);
    private final Pose controlPose = p.of(17.03,40,90);
    private final Pose intakePose = p.of(7,6,270);

    private final Pose secondShootPose = p.of(57.43,134.09,270);
    private final Pose secondControlPose = p.of(20,100,270);
    private final Pose parkControlPose = p.of(53.07,101.16,270);

    private Path park(){
        return curve(secondShootPose,parkControlPose,park).linear(secondShootPose,park);
    }

    private Path toSecondShoot(){
        return curve(intakePose,secondControlPose,secondShootPose).linear(intakePose,secondShootPose);
    }
    private Path gardenIntake(){
        return curve(startPose,controlPose,intakePose).linear(startPose,intakePose);
    }


    private Command shootStationary(){
        return sequential(
                instant(() -> shooter.shoot()),
                waitMs(3000),
                instant(() ->intake.setTransitionOnly(0.8)),
                waitMs(2500),
                instant(() -> shooter.stop()),
                instant(() -> intake.stop()));
    }

    private Command autoRoutine(){
        return sequential(
                shootStationary(),
                parallel(
                follow(follower,gardenIntake()),
                instant(() -> intake.setIntakeAndTransition(0.8,-0.8)
                        )),
                instant(() -> intake.stop()),

                follow(follower,toSecondShoot()),
                shootStationary(),
                follow(follower,park())
                //sequential -> run one after anohter
                //parallel-> run all at once
                //instant-> run the command

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
