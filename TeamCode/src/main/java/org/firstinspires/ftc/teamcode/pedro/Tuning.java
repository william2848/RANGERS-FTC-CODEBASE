package org.firstinspires.ftc.teamcode.pedro;


import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;

import org.firstinspires.ftc.teamcode.pedro.procedures.ForesightTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.MecanumTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.PinpointTuner;

public class Tuning {
    @Tuner
    public static Procedure mecanumTuner() {
        return new MecanumTuner();
    }
    @Tuner
    public static Procedure pinpointTuner(){return new PinpointTuner();}
    @Tuner
    public static Procedure foresightTuner(){return new ForesightTuner(h->new PinpointLocalizer(h, Constants.pinpointLocalizerConfig), h->new Mecanum(h,Constants.driveConfig));}
}
