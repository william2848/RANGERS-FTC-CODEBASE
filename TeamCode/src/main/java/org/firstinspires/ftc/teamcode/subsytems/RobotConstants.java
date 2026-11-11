package org.firstinspires.ftc.teamcode.subsytems;
import com.acmerobotics.dashboard.config.Config;

@Config
public class RobotConstants {
    @Config
    public static class shooterConstants {
        public static final double MAX_HD_HEX = 6000;
        public static final double VEL_BUFFER = 0.9;
        public static double TARGET_RPM = 1150;
        public static double TARGETVelPercent = TARGET_RPM/(MAX_HD_HEX*VEL_BUFFER);

        //velocity is desired rpm * 28 / 60 = ticks/second;
        public static final double CLICKS_PER_REV = 28;
        public static double MAXVel = CLICKS_PER_REV * MAX_HD_HEX / 60.0;

        public static  double kP = 45;
        public static  double kI = 0;
        public static  double kD = 0;
        public static  double kS = 0;
        public static  double kV = 9;//5

    }

    public static class intakeConstants {
        public static double intakePower = 0.8;
        public static double transitionPower = 0.8;
    }
    public static class otherConstants{
        public static long waitForEmpty = 2000;
    }

}
