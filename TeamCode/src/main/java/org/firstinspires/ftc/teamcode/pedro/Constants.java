package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.pedro.procedures.ForesightTuner;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, pinpointLocalizerConfig),
                new Mecanum(h, driveConfig),
                new Foresight(foresightConfig)
        );
    }
    public static MecanumConfig driveConfig = new MecanumConfig(
            c -> {
                c.frontLeftName.set("frontLeft");
                c.frontRightName.set("frontRight");
                c.backLeftName.set("backLeft");
                c.backRightName.set("backRight");
                c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
                c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
                c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
                c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);

            }
    );


    public static PinpointConfig pinpointLocalizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-4.911973307451865);
        c.yPodOffset.set(-5.021821570208693);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.21136284560036034);
                Controller secondaryTranslationalForward = Controller.proportional(0.07809292373008317);
                Controller primaryTranslationalLateral = Controller.proportional(0.37195916801256385);
                Controller secondaryTranslationalLateral = Controller.proportional(0.1374289736486251);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.013954639860994034));
                c.brake.set(Controller.proportionalFeedforward(0.01186144388184493));

                c.headingFeedback.set(Controller.proportional(4.006691143720645));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.0471517691302676, 0.006742578864936172));

                c.linearBrakeCoefficients.set(Matrix.diag(0.08207887295266429, 0.037374633563730955));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.001597958707408642, 0.002300000762088253));

                c.maxAchievableForwardVelocity.set(26.209035507213013);
                c.maxAchievableStrafeVelocity.set(24.41699244650022);
                c.naturalForwardDeceleration.set(33.29513246713739);
                c.naturalStrafeDeceleration.set(72.77151613350227);
            }
    );

}