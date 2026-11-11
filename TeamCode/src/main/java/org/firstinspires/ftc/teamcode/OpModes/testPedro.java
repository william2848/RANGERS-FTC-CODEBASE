//package org.firstinspires.ftc.teamcode.OpModes;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.pedropathing.math.Pose;
//import com.seattlesolvers.solverslib.command.CommandOpMode;
//
//import org.firstinspires.ftc.teamcode.subsytems.DriveBase;
//
//@TeleOp(name = "testPedro")
//public class testPedro extends CommandOpMode {
//
//    private DriveBase driveBase;
//
//    @Override
//    public void initialize() {
//        driveBase = new DriveBase(hardwareMap);
//        driveBase.setStartingPose(new Pose(0, 0, 0));
//        driveBase.register();   // makes periodic() run, which calls follower.update()
//    }
//
//    @Override
//    public void run() {
//        super.run();
//
//        Pose pose = driveBase.getPose();
//
//        telemetry.addData("x", pose.x());
//        telemetry.addData("y", pose.y());
//        telemetry.addData("heading (deg)", Math.toDegrees(pose.heading()));
//        telemetry.addData("full pose", pose);
//        telemetry.update();
//    }
//}
