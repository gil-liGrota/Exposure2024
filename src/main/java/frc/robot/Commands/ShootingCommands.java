package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.tuneables.Tuneable;
import frc.robot.Subsystems.shoot.Shoot;


public class ShootingCommands {
    public static Command setSetPointCommand(Shoot shoot, double setPoint){
        return Commands.run(() -> shoot.getIO().setSetPoint(setPoint), shoot).until(shoot.getIO().atSetPoint()).withName("setSetPointCommand: " + setPoint);
    }

    public static Command shootNote(Shoot shoot, double speed){
        return Commands.startEnd(() -> shoot.getIO().setVoltage(speed), () -> shoot.getIO().stopMotor(), shoot);
    }

    public static Command stop(Shoot shoot){
        return Commands.runOnce(() -> shoot.getIO().stopMotor(), shoot);
    }
}
