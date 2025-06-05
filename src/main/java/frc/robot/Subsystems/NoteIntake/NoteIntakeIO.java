package frc.robot.Subsystems.NoteIntake;

public interface NoteIntakeIO {
    //@AutoLog
    public static class NoteIntakeIOInputs {
        public double speed;
        public double voltage;
    }

    public default void setSpeed(double speed) {
    }

    public default void setVoltage(double voltage) {
    }

    public default void stopMotor() {
    }

    public default void updateInputs(NoteIntakeIOInputs inputs) {
    }
}
