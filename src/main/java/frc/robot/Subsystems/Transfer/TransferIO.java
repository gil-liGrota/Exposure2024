package frc.robot.Subsystems.Transfer;

public interface TransferIO {

    public static class TransferIOInputs {
        public double speed;
        public double voltage;
    
    }

    public default void updateInputs(TransferIOInputs inputs) {
    }

    public default void setSpeed(double speed) {
    }

    public default void setVoltage(double voltage) {
    }

    public default void stopMotor() {
    }

    public default boolean getTransferSensor(){
        return false;
    }
    
}
