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

public class Constants {

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("leftFront");
        c.frontRightName.set("rightFront");
        c.backLeftName.set("leftBack");
        c.backRightName.set("rightBack");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.manualBrakeMode.set(true);   // brakes when sticks are released in TeleOp
    });

    public static PinpointConfig pinpointLocalizerConfig = new PinpointConfig(c -> {
       c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-1.507294572244479);
        c.yPodOffset.set(1.0313989233782912);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);//left = y, right = x
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(c -> {
        Controller primaryTranslationalForward = Controller.proportional(0.24970465570474473);
        Controller secondaryTranslationalForward = Controller.proportional(0.0922591980516185);
        Controller primaryTranslationalLateral = Controller.proportional(0.2954388019557367);
        Controller secondaryTranslationalLateral = Controller.proportional(0.10915674305246557);

        c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
        c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

        c.coast.set(Controller.proportionalFeedforward(0.016689188481841796));
        c.brake.set(Controller.proportionalFeedforward(0.014185810209565526));

        c.headingFeedback.set(Controller.proportional(3.5655729946840737));
        c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04552527442286921, 0.013332881197448833));

        c.linearBrakeCoefficients.set(Matrix.diag(0.06518120281139246, 0.16425036550259198));
        c.quadraticBrakeCoefficients.set(Matrix.diag(0.003316933648231231, 0.0010772128163544909));

        c.maxAchievableForwardVelocity.set(60.01323190087567);
        c.maxAchievableStrafeVelocity.set(52.93089295589375);
        c.naturalForwardDeceleration.set(30.014845677062493);
        c.naturalStrafeDeceleration.set(30.311062119332142);
    });

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, pinpointLocalizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}