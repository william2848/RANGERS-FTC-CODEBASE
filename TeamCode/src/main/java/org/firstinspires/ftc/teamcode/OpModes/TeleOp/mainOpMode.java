package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ConditionalCommand;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
    import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.LightBar.Color;
import org.firstinspires.ftc.teamcode.LightBar.PrismAnimations;
import org.firstinspires.ftc.teamcode.subsytems.DriveBase;
import org.firstinspires.ftc.teamcode.subsytems.FlowerMech;
import org.firstinspires.ftc.teamcode.subsytems.Intake;
import org.firstinspires.ftc.teamcode.subsytems.Lightbar;
import org.firstinspires.ftc.teamcode.subsytems.Limelight;
import org.firstinspires.ftc.teamcode.subsytems.RobotConstants;
import org.firstinspires.ftc.teamcode.subsytems.Shooter;

@TeleOp(name="main")
public class mainOpMode extends CommandOpMode {

    private DriveBase driveBase;
    private GamepadEx driver;
    private GamepadEx operator;
    private Shooter shooter;
    private Intake intake;
    private Lightbar lightbar;
    private FlowerMech flowerMech;
    private Limelight limelight3A;
    private RobotConstants.gamepadAssignments assignment;
    private double shotType;

    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        assignment = new RobotConstants.gamepadAssignments();
        flowerMech = new FlowerMech(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        lightbar = new Lightbar(hardwareMap,shooter);
        limelight3A = new Limelight(hardwareMap);
        operator = new GamepadEx(gamepad2);
        driver = new GamepadEx(gamepad1);
        driveBase = new DriveBase(hardwareMap);
        driveBase.setDefaultCommand(new RunCommand(()->driveBase.manual(driver.getLeftY(), driver.getLeftX(), driver.getRightX()),driveBase));
        flowerMech.setToZero();
        shotType = RobotConstants.shooterConstants.TARGET_RPM;


        driver.getGamepadButton(assignment.INTAKE)
                .whenPressed(new InstantCommand(() -> intake.setIntakeAndTransition(0.8, -1)))
                .whenReleased(new InstantCommand(() -> intake.stop()));


        driver.getGamepadButton(assignment.OUTTAKE).whenPressed(intake.outtake()).whenReleased(new InstantCommand(intake::stop));

       driver.getGamepadButton(assignment.FLOWER)
               .toggleWhenPressed(new InstantCommand(()-> flowerMech.down()),new InstantCommand(() -> flowerMech.setToZero()));

       driver.getGamepadButton(assignment.THREESHOT)
               .toggleWhenPressed(
                        new InstantCommand(()->{
                            this.shotType = RobotConstants.shooterConstants.TARGET_RPM;
                            lightbar.setManual(true);
                            lightbar.getCurrentLight("Yellow");
                        }) ,new InstantCommand(()->{
                           lightbar.setManual(false);
                           lightbar.getCurrentLight("none");
                       }));
        driver.getGamepadButton(assignment.TWOSHOT)
                .toggleWhenPressed(
                        new InstantCommand(
                        ()->{
                            this.shotType = RobotConstants.shooterConstants.TWO_SHOT_RPM;
                            lightbar.setManual(true);
                            lightbar.getCurrentLight("BLUE");
                        }) ,new InstantCommand(()->{
                            lightbar.setManual(false);
                            lightbar.getCurrentLight("none");
                        }));
        driver.getGamepadButton(assignment.ONESHOT)
                .toggleWhenPressed(
                        new InstantCommand(()->{
                            this.shotType = RobotConstants.shooterConstants.ONE_SHOT_RPM;
                            lightbar.setManual(true);
                            lightbar.getCurrentLight("RED");
                        }),
                        new InstantCommand(()->{
                            lightbar.setManual(false);
                            lightbar.getCurrentLight("none");
                        }));
        driver.getGamepadButton(assignment.TRANSITION)
                .whenPressed(new InstantCommand(() -> intake.setTransitionOnly(1)))
                .whenReleased(new InstantCommand(() -> {intake.stop();lightbar.setManual(false);lightbar.getCurrentLight("none"); this.shotType = RobotConstants.shooterConstants.TARGET_RPM;}));

        driver.getGamepadButton(assignment.SPOOLSHOOTER)
                .whenPressed(new InstantCommand(()->{shooter.shoot(this.shotType);}))
                .whenReleased(new InstantCommand(shooter::end));
        driver.getGamepadButton(assignment.AUTOALIGN).whenPressed(
                new ConditionalCommand(
                        new RunCommand(()->driveBase.manual(driver.getLeftY(),driver.getLeftX(),limelight3A.autoAlign()),driveBase,limelight3A)
                                .interruptOn(()->limelight3A.getTx()==null||limelight3A.getTx()<RobotConstants.limelightConstants.buffer && limelight3A.getTx()>-RobotConstants.limelightConstants.buffer)
                                .interruptOn(()->driver.getRightX()>0.1),
                        new InstantCommand(),
                        ()->limelight3A.getTx()!=null));
        //driver.getGamepadButton(assignment.AUTOALIGN).whenPressed(driveBase.align(limelight3A));
    }
    @Override
    public void waitForStart() {
        while (!isStarted() && !isStopRequested()) {
            driver.readButtons();

            if (driver.getButton(GamepadKeys.Button.B)) {
                Holder.setAlliance(Holder.Alliance.BLUE);
            }
            if (driver.getButton(GamepadKeys.Button.Y)) {
                Holder.setAlliance(Holder.Alliance.RED);
            }

            telemetry.addLine("Press B for Blue\n press Y for Red");
            telemetry.update();
        }
    }

    @Override
    public void run() {
        driver.readButtons();
        super.run();

        telemetry.addData("PosX", driveBase.getPose().x());
        telemetry.addData("PosY", driveBase.getPose().y());
        telemetry.addData("Rotation",driveBase.getPose().heading());
        telemetry.addData("Target RPM", this.shotType);
        telemetry.addData("Left RPM", shooter.getLeftRPM());
        telemetry.addData("Current Servo Position", flowerMech.getPosition());

        telemetry.update();
    }
}
