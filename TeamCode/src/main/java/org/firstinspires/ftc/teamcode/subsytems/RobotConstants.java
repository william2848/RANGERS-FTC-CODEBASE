package org.firstinspires.ftc.teamcode.subsytems;
import com.acmerobotics.dashboard.config.Config;

@Config
public class RobotConstants {
    @Config
    public static class shooterConstants {
        public static final double MAX_HD_HEX = 6000;
        public static double TARGET_RPM = 5000;

        public static final double CLICKS_PER_REV = 28;


        public static  double kP = 0;//todo change these later
        public static  double kI = 0;
        public static  double kD = 0;
        public static  double kS = 0;
        public static  double kV = 0.47;


    }

    public static class intakeConstants {



    }
    public static class servoConstants{

        public static final double startPos = 0.5;
    }

}
