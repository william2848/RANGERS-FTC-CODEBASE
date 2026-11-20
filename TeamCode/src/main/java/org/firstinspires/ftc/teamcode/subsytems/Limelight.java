package org.firstinspires.ftc.teamcode.subsytems;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import com.pedropathing.math.Pose;
public class Limelight extends SubsystemBase {
    private Limelight3A limelight;
    private LLResult result;
    private Pose3D pose3D;

    public Limelight(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.start();
        register();
    }

    public void update() {
        this.result = this.limelight.getLatestResult();
        //pose3D = this.result.getBotpose(); can't use botpose unless I do a bunch of editing for this season
    }

    public Double getTx() {
        return this.result != null && this.result.isValid() && this.result.getStaleness()<100? this.result.getTx() : null;
    }

    public Double getTy() {
        return this.result != null && this.result.isValid() && this.result.getStaleness()<100? this.result.getTy() : null;
    }

    public Double getTa() {
        return this.result != null && this.result.isValid() && this.result.getStaleness()<100? this.result.getTa() : null;
    }

    public Double autoAlign() {
        double tx;
        if (this.getTx() != null)
            tx = this.getTx();
        else
            return null;


        if(tx<-RobotConstants.limelightConstants.buffer||tx>RobotConstants.limelightConstants.buffer)
            return Math.max(-RobotConstants.limelightConstants.cap,Math.min(tx *RobotConstants.limelightConstants.slopeOfTurn,RobotConstants.limelightConstants.cap));
       else
               return 0.0;
    //IDK if positive velocity moves the opposite direction or not. might be negative
    }


    public Pose3D getCameraPose(){return pose3D;}
    public Double targetHeading(Pose pose){return this.getTx()!=null?pose.heading()-Math.toRadians(this.result.getTx()):null;}
    @Override
    public void periodic(){
        this.update();
    }
}
