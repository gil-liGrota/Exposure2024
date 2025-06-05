package frc.robot.Subsystems.Transfer;
import static frc.robot.Subsystems.Transfer.TransferConstants.TRANSFER_MOTOR_ID;

import com.revrobotics.spark.SparkMax;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.POM_lib.sensors.POMDigitalInput;

public class TransferIOReal implements TransferIO {
    private final POMDigitalInput transferSensor = new POMDigitalInput(0);
    private final SparkMax motor;

    public TransferIOReal() {
    motor = new SparkMax(TRANSFER_MOTOR_ID, MotorType.kBrushless ); 

    

    }

    @Override
    public void updateInputs(TransferIOInputs inputs) {
        inputs.speed = motor.get();
        inputs.voltage = motor.getBusVoltage();
    }

    @Override
    public void setSpeed(double speed) {
        motor.set(speed);
    }

    @Override
    public void setVoltage(double voltage) {
        motor.setVoltage(voltage);
    }

    @Override
    public void stopMotor() {
        motor.stopMotor();
    }

    @Override
    public boolean getTransferSensor() {
        return transferSensor.get();
    }

}
