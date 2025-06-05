package frc.robot.Subsystems.NoteIntake;

import frc.robot.POM_lib.Motors.POMVictorSpx;
import static frc.robot.Subsystems.NoteIntake.NoteIntakeConstants.*;

public class NoteIntakeIOReal implements NoteIntakeIO {
    private final POMVictorSpx IntakeMotorOne;
    private final POMVictorSpx IntakeMotorTwo;

    public NoteIntakeIOReal() {
        IntakeMotorOne = new POMVictorSpx(INTAKE_MOTOR_RIGHT_ID);
        IntakeMotorTwo = new POMVictorSpx(INTAKE_MOTOR_LEFT_ID);
        IntakeMotorOne.setInverted(false);
        IntakeMotorTwo.setInverted(false);
    }

    @Override
    public void setSpeed(double speed) {
        IntakeMotorOne.set(speed);
        IntakeMotorTwo.set(speed);
    }

    @Override
    public void setVoltage(double voltage) {
        IntakeMotorOne.setVoltage(voltage);
        IntakeMotorTwo.setVoltage(voltage);
    }

    @Override
    public void stopMotor() {
        IntakeMotorOne.stopMotor();
        IntakeMotorTwo.stopMotor();
    }

    @Override
    public void updateInputs(NoteIntakeIOInputs inputs) {
        inputs.speed = IntakeMotorOne.get();
        inputs.voltage = IntakeMotorOne.getBusVoltage();

    }
}