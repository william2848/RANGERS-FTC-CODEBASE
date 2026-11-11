package org.firstinspires.ftc.teamcode.subsytems;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
public class Limelight extends SubsystemBase {
    private Limelight3A limelight;
    private LLResult result;
    private Pose3D pose3D;
    public Limelight(HardwareMap hardwareMap){
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
    }
    public void update(){
        this.result = this.limelight.getLatestResult();
        pose3D = this.result.getBotpose();
    }
    public double getTx(){return this.result.getTx();}
    public double getTy(){return this.result.getTy();}
    public double getTa(){return this.result.getTa();}
    public Pose3D getCameraPose(){return pose3D;}

    @Override
    public void periodic(){
        this.update();
    }
}
