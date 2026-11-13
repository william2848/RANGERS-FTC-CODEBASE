package org.firstinspires.ftc.teamcode.subsytems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

public class FlowerMech extends SubsystemBase {

    ServoEx flowerServo;


    public FlowerMech(HardwareMap hardwareMap){

        flowerServo = new ServoEx(hardwareMap,"flowerServo");
    }


    public void setToZero(){
        this.flowerServo.set(0.0);//DPAD DOWN
    }

    public void down(){
        this.flowerServo.set(0.6);
    }


//    public void sto(){
//        this.flowerServo()
//    }


    public double getPosition(){
        return flowerServo.getRawPosition();
    }


}
