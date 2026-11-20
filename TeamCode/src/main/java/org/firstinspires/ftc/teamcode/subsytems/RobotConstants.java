package org.firstinspires.ftc.teamcode.subsytems;
import com.acmerobotics.dashboard.config.Config;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
//~/Library/Android/sdk/platform-tools/adb connect 192.168.43.1:5555
//~/Library/Android/sdk/platform-tools/adb kill-server; pkill -x adb
@Config
public class RobotConstants {

    @Config
    public static class shooterConstants {
        public static final double MAX_RPM = 6000;
        public static final double TWO_SHOT_RPM = 1125;
        public static final double ONE_SHOT_RPM = 1100;
        public static final double VEL_BUFFER = 0.9;
        public static double TARGET_RPM = 1150;
        public static double IDLE_POWER = 0.5;

        //velocity is desired rpm * 28 / 60 = ticks/second;
        public static final double TICKS_PER_SEC = 28;
        public static double MAXVel = TICKS_PER_SEC * MAX_RPM / 60.0;

        public static  double kP = 45;
        public static  double kI = 0;
        public static  double kD = 0;
        public static  double kS = 0;
        public static  double kV = 4;//5

    }

    @Config
    public static class intakeConstants {
        public static double MAX_RPM = 6000;
        public static double TARGET_RPM_TRANS = 4000;
        public static  double kP = 2;
        public static  double kI = 0;
        public static  double kD = 0;
        public static  double kS = 0;
        public static  double kV = 3;//5
        public static double intakePower = 1;
        public static double transitionPower = 1;

    }
    public static class otherConstants{
        public static long waitForEmpty = 2000;
    }
    public static class limelightConstants{
        public static double offsetAngle;
        public static double slopeOfTurn = 0.02; //tune this. It decides how much velocity is added for x angle. If angle is like 40,
        //then the velocity would be 40. But at 40, you'd probably need 2000 RPM or something. Tune it.
        public static double buffer = 5; //this will be to ensure no oscillation close to tx = 0 and that it doesn't take years to stop.
        public static double cap = 1; //this is meant to cap the top velocity just in case it tries going out of wack.
        public static double FOV = 54.5; //I haven't implemented this yet, but I'll keep it so that if there are any known problems with getting too large tx, it will prevent it.
    }
    public static class gamepadAssignments{
        //I can't keep track of what button is assigned to what so I'll just make them actual variables;
        public final GamepadKeys.Button INTAKE = GamepadKeys.Button.LEFT_BUMPER;
        public final GamepadKeys.Button OUTTAKE = GamepadKeys.Button.DPAD_DOWN;
        public final GamepadKeys.Button FLOWER = GamepadKeys.Button.DPAD_RIGHT;
        public final GamepadKeys.Button THREESHOT = GamepadKeys.Button.Y;
        public final GamepadKeys.Button TWOSHOT = GamepadKeys.Button.X;
        public final GamepadKeys.Button ONESHOT = GamepadKeys.Button.B;
        public final GamepadKeys.Button TRANSITION = GamepadKeys.Button.DPAD_UP;
        public final GamepadKeys.Button SPOOLSHOOTER = GamepadKeys.Button.RIGHT_BUMPER;
        public final GamepadKeys.Button AUTOALIGN = GamepadKeys.Button.A;


    }




}
