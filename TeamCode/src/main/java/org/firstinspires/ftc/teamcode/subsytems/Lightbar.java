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
    PrismAnimations.Pulse pulseRed = new PrismAnimations.Pulse(Color.RED);
    PrismAnimations.Blink blinkRed = new PrismAnimations.Blink(Color.RED, Color.TRANSPARENT, 400, 200);
    PrismAnimations.Blink blinkYellow = new PrismAnimations.Blink(Color.YELLOW, Color.TRANSPARENT, 400, 200);
    PrismAnimations.Blink blinkBlue = new PrismAnimations.Blink(Color.BLUE, Color.TRANSPARENT, 400, 200);
    PrismAnimations.Blink blinkGreen = new PrismAnimations.Blink(Color.GREEN);
    PrismAnimations.Solid purple = new PrismAnimations.Solid(Color.PURPLE);
    PrismAnimations.PoliceLights police = new PrismAnimations.PoliceLights();
    PrismAnimations.Blink reference;
    private Shooter shooter;
    private boolean manual = false;
    private String currentLight = "None";

    public Lightbar(HardwareMap hardwareMap, Shooter shooter){
        prism = hardwareMap.get(GoBildaPrismDriver.class, "prism");
        this.shooter = shooter;
        register();
    }
    public void lightSolid(String lightName, PrismAnimations.Solid solid) {
        if (!currentInstance.equalsIgnoreCase(lightName)) {
            prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, solid);
            currentInstance = lightName;
        }
    }
    public void lightBlink(String lightName, PrismAnimations.Blink blink) {
        if (!currentInstance.equalsIgnoreCase(lightName)) {
            prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, blink);
            currentInstance = lightName;
        }
    }
    public void getCurrentLight(String currentLight){
        this.currentLight = currentLight;
        if(currentLight.equalsIgnoreCase("RED"))
            reference = blinkRed;
        else if (currentLight.equalsIgnoreCase("YELLOW"))
            reference = blinkYellow;
        else if (currentLight.equalsIgnoreCase("BLUE"))
            reference = blinkBlue;
    }
    public void setManual(boolean manual){this.manual = manual;}
    public Command Clear(){return new InstantCommand(()->prism.clearAllAnimations());}

    @Override
    public void periodic(){
        if(!manual)
            if(shooter.isAtSpeed())
                this.lightSolid("GREEN", green);
            else
                this.lightSolid("PURPLE",purple);
        else {
            if(shooter.isAtSpeed())
                this.lightSolid("GREEN", green);
            else
                this.lightBlink(this.currentLight, reference);
        }
    }

}
