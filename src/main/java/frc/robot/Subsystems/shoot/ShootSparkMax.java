package frc.robot.Subsystems.shoot;

import static frc.robot.Subsystems.shoot.ShootingConstants.KD;
import static frc.robot.Subsystems.shoot.ShootingConstants.KI;
import static frc.robot.Subsystems.shoot.ShootingConstants.KP;
import static frc.robot.Subsystems.shoot.ShootingConstants.TOLERANCE;

import java.util.function.BooleanSupplier;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.PIDController;
import frc.lib.logfields.LogFieldsTable;
import frc.lib.tuneables.Tuneable;
import frc.lib.tuneables.TuneableBuilder;
import frc.lib.tuneables.TuneablesManager;

public class ShootSparkMax extends ShootIO implements Tuneable {
    private SparkMax upperMotor = new SparkMax(5, MotorType.kBrushless);
    private SparkMax downMotor = new SparkMax(6, MotorType.kBrushless);
    private PIDController pidController;
    private double maxInput = 12;


    public ShootSparkMax(LogFieldsTable fieldsTable){
        super(fieldsTable);
        SparkMaxConfig config = new SparkMaxConfig();
        pidController = new PIDController(KP,KI,KD);

        config.idleMode(IdleMode.kCoast).follow(upperMotor, false)
                .smartCurrentLimit(40)
                .voltageCompensation(12);
        downMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        pidController.setTolerance(TOLERANCE);
        TuneablesManager.add("Shoot", (Tuneable) this);
   
    }

    @Override
    public void stopMotor() {
        upperMotor.stopMotor();
    }

    @Override
    public void setSpeed(double speed) {
        upperMotor.set(speed);
    }

    @Override
    public void setVoltage(double voltage) {
        if(Math.abs(voltage) > maxInput){
            voltage = Math.copySign(maxInput, voltage);
        }

        upperMotor.setVoltage(voltage);
    }

    @Override
    public double getSpeed() {
        return (Math.abs(upperMotor.getEncoder().getVelocity()) + Math.abs(downMotor.getEncoder().getVelocity())) / 2;
    }

    @Override
    public void setSetPoint(double goal) {
        pidController.setSetpoint(goal);
        setVoltage(pidController.calculate(getSpeed())*12);
    }

    @Override
    public BooleanSupplier atSetPoint() {
        return () -> pidController.atSetpoint();   
    }


    @Override
    public void initTuneable(TuneableBuilder builder) {
        builder.addChild("PID", pidController);
        builder.addDoubleProperty("max input", ()->maxInput, maxInput -> this.maxInput = maxInput);
    }
    
}