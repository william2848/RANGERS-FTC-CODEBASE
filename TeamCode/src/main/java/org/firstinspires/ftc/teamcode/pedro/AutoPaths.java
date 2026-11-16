package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.interpolator.Interpolator;

import org.firstinspires.ftc.teamcode.OpModes.Holder;
import org.firstinspires.ftc.teamcode.OpModes.OneTipPark;

public class AutoPaths {
    public class OneTipPark{
        private boolean mirror;
        public OneTipPark(boolean mirror){
            this.mirror = mirror;
        }
        private final PoseFactory poseFactory = mirror ?
                PoseFactory.degrees().mirrorX(70.75) :
                PoseFactory.degrees();

        public final Pose start = poseFactory.of(58.0906, 8.2201, 90);
        private final Pose path1 = poseFactory.of(10.2271, 95.3068, 180);
        private final Pose path1Control1 = poseFactory.of(10.4376, 56.2838, 0);

        public Path path1() {
            return Paths.curve(start, path1Control1, path1).linear(start, path1);
        }


        }
}
