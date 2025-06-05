package frc.robot.Subsystems.shoot;

import static frc.robot.Subsystems.shoot.ShootingConstants.*;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logfields.LogFieldsTable;
import frc.lib.tuneables.Tuneable;
import frc.lib.tuneables.TuneableBuilder;
import frc.lib.tuneables.TuneablesManager;
import frc.lib.tuneables.extensions.TuneableTrapezoidProfile;
import frc.robot.Robot;
import frc.robot.Commands.ShootingCommands;

public class Shoot extends SubsystemBase {
    private final LogFieldsTable fieldsTable = new LogFieldsTable(getName());
    private final ShootIO io = Robot.isSimulation()? null : new ShootSparkMax(fieldsTable);

    private PIDController shootingPidController = new PIDController(KP, KI, KD);

    public Shoot(){
        fieldsTable.update();

        fieldsTable.recordOutput("current command", getCurrentCommand() != null ? getCurrentCommand().getName() : "None");
    
        fieldsTable.recordOutput("defualt command", getDefaultCommand() != null ? getDefaultCommand().getName() : "None");

        SmartDashboard.putData("stop shoot command", runOnce(this.getIO()::stopMotor));
        SmartDashboard.putData("shoot 100 RPM command", ShootingCommands.setSetPointCommand(this, 100));
        SmartDashboard.putData("shoot 200 RPM command", ShootingCommands.setSetPointCommand(this, 200));
        SmartDashboard.putData("shoot 500 RPM command", ShootingCommands.setSetPointCommand(this, 500));
        SmartDashboard.putData("shoot 1000 RPM command", ShootingCommands.setSetPointCommand(this, 1000));
        SmartDashboard.putData("shoot 2000 RPM command", ShootingCommands.setSetPointCommand(this, 2000));
        
    }

    public ShootIO getIO(){
        return io;
    }




}