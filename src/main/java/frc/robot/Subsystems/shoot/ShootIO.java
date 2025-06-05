package frc.robot.Subsystems.shoot;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.AutoLog;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import frc.lib.logfields.IOBase;
import frc.lib.logfields.LogFieldsTable;

public abstract class ShootIO extends IOBase{
    DoubleSupplier velocity = fields.addDouble("velocty", () -> getSpeed());

    public ShootIO(LogFieldsTable fieldsTable){
        super(fieldsTable);
    }
    public abstract void stopMotor();
    public abstract void setSpeed(double speed);
    public abstract void setVoltage(double voltage);
    public abstract void setSetPoint(double goal);
    public abstract BooleanSupplier atSetPoint();
    public abstract double getSpeed();

}
