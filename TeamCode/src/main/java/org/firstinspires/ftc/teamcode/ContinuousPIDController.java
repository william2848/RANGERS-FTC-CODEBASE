package org.firstinspires.ftc.teamcode;


import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class ContinuousPIDController extends PIDController {

    public ContinuousPIDController(double kP, double kI, double kD) { super(kP, kI, kD); }
    public ContinuousPIDController(double[] v) { super(v[0], v[1], v[2]); }

    public void setPID(double[] v) { super.setPID(v[0], v[1], v[2]); }

    @Override
    public double calculate(double angle, double setpoint) {
        // Move the setpoint to the copy of the target within ±π of the current angle
        double error = AngleUnit.normalizeRadians(setpoint - angle);
        return super.calculate(angle, angle + error);
    }
}