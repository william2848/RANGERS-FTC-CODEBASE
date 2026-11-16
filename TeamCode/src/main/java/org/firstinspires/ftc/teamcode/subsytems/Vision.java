package org.firstinspires.ftc.teamcode.subsytems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Vision extends SubsystemBase {

    private Limelight3A ll;
    private LLResult result;
    public Vision(HardwareMap hardwareMap){
        ll = hardwareMap.get(Limelight3A.class, "limelight");
        ll.pipelineSwitch(0);
        result = ll.getLatestResult();
    }

    public void periodic(){
        result = ll.getLatestResult();

        if(result.isValid()){

        }
    }
}
