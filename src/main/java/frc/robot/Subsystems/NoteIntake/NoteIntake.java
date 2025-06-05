package frc.robot.Subsystems.NoteIntake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NoteIntake extends SubsystemBase {

    private final NoteIntakeIO noteIntakeIO;
    //private final NoteIntakeIOAutoLogged noteIntakeInputs = new NoteIntakeIOAutoLogged();

    public NoteIntake(NoteIntakeIO noteIntakeIO) {
        this.noteIntakeIO = noteIntakeIO;
    }

    public void periodic() {
        // noteIntakeIO.updateInputs(noteIntakeInputs);
        // Logger.
    }

    public NoteIntakeIO getNoteIntakeIO() {
        return noteIntakeIO;
    }

    public void setSpeed(double speed) {
        noteIntakeIO.setSpeed(speed);
    }

    public void setVoltage(double voltage) {
        noteIntakeIO.setVoltage(voltage);
    }

    public void stopMotor() {
        noteIntakeIO.stopMotor();
    }
}
