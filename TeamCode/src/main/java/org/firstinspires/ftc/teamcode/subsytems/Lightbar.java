package org.firstinspires.ftc.teamcode.subsytems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.subsytems.Shooter;
import org.firstinspires.ftc.teamcode.LightBar.Color;
import org.firstinspires.ftc.teamcode.LightBar.GoBildaPrismDriver;
import org.firstinspires.ftc.teamcode.LightBar.GoBildaPrismDriver.LayerHeight;
import org.firstinspires.ftc.teamcode.LightBar.PrismAnimations;

public class Lightbar extends SubsystemBase {
    private GoBildaPrismDriver prism;
    private String currentInstance = "None";
    PrismAnimations.Solid red = new PrismAnimations.Solid(Color.RED);
    PrismAnimations.Solid green = new PrismAnimations.Solid(Color.GREEN);
    private Shooter shooter;

    public Lightbar(HardwareMap hardwareMap, Shooter shooter){
        prism = hardwareMap.get(GoBildaPrismDriver.class, "prism");
        this.shooter = shooter;

        register();
    }
    public void green(){
        if(!currentInstance.equalsIgnoreCase("GREEN")){
            prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, green);
            currentInstance = "GREEN";
        }
    }
    public void red(){
        if(!currentInstance.equalsIgnoreCase("RED")) {
            prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, red);
            currentInstance = "RED";
        }
    }
    public Command Clear(){return new InstantCommand(()->prism.clearAllAnimations());}

    @Override
    public void periodic(){
        if(shooter.isAtSpeed())
            this.green();
        else
            this.red();
    }

}
