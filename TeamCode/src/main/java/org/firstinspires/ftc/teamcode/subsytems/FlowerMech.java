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
        this.flowerServo.set(0);//DPAD DOWN
    }

    public void setToHalf(){
        this.flowerServo.set(0.5);//DPAD Right
    }

    public void setToOne(){
        this.flowerServo.set(1);//DPAD UP
    }

    public double getPosition(){
        return flowerServo.getRawPosition();
    }

}
