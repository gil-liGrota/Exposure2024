// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.Subsystems.drive;

import static frc.robot.Subsystems.drive.DriveConstants.*;
import static frc.robot.Subsystems.drive.PhoenixUtil.*;

import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;

import edu.wpi.first.math.util.Units;

/** This drive implementation is for Talon SRXs driving brushed motors (e.g. CIMS) with encoders. */
public class DriveIOTalonSRX implements DriveIO {
  private static final double tickPerRevolution = 1440;

  private final TalonSRX leftLeader = new TalonSRX(leftLeaderCanId);
  private final VictorSPX leftFollower = new VictorSPX(leftFollowerCanId);
  private final TalonSRX rightLeader = new TalonSRX(rightLeaderCanId);
  private final VictorSPX rightFollower = new VictorSPX(rightFollowerCanId);

  public DriveIOTalonSRX() {
    var TalonConfig = new TalonSRXConfiguration();
    TalonConfig.peakCurrentLimit = currentLimit;
    TalonConfig.continuousCurrentLimit = currentLimit - 15;
    TalonConfig.peakCurrentDuration = 250;
    TalonConfig.voltageCompSaturation = 12.0;
    TalonConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;

    var VictorConfig = new VictorSPXConfiguration();

    tryUntilOkV5(5, () -> leftLeader.configAllSettings(TalonConfig));
    tryUntilOkV5(5, () -> leftFollower.configAllSettings(VictorConfig));
    tryUntilOkV5(5, () -> rightLeader.configAllSettings(TalonConfig));
    tryUntilOkV5(5, () -> rightFollower.configAllSettings(VictorConfig));

    leftLeader.setInverted(leftInverted);
    rightLeader.setInverted(rightInverted);
    rightFollower.setInverted(true);

    leftFollower.follow(leftLeader);
    rightFollower.follow(rightLeader);
  }

  @Override
  public void updateInputs(DriveIOInputs inputs) {
    inputs.leftPositionRad =
        Units.rotationsToRadians(leftLeader.getSelectedSensorPosition() / tickPerRevolution);
    inputs.leftVelocityRadPerSec =
        Units.rotationsToRadians(
            leftLeader.getSelectedSensorVelocity()
                / tickPerRevolution
                * 10.0); // Raw units are ticks per 100ms :(
    inputs.leftAppliedVolts = leftLeader.getMotorOutputVoltage();
    inputs.leftCurrentAmps =
        new double[] {leftLeader.getStatorCurrent(), 0};

    inputs.rightPositionRad =
        Units.rotationsToRadians(rightLeader.getSelectedSensorPosition() / tickPerRevolution);
    inputs.rightVelocityRadPerSec =
        Units.rotationsToRadians(
            rightLeader.getSelectedSensorVelocity()
                / tickPerRevolution
                * 10.0); // Raw units are ticks per 100ms :(
    inputs.rightAppliedVolts = rightLeader.getMotorOutputVoltage();
    inputs.rightCurrentAmps =
        new double[] {rightLeader.getStatorCurrent(), 0};
  }

  @Override
  public void setVoltage(double leftVolts, double rightVolts) {
    // OK to just divide by 12 because voltage compensation is enabled
    leftLeader.set(TalonSRXControlMode.PercentOutput, leftVolts / 12.0);
    rightLeader.set(TalonSRXControlMode.PercentOutput, rightVolts / 12.0);
  }

  @Override
  public void setVelocity(
      double leftRadPerSec, double rightRadPerSec, double leftFFVolts, double rightFFVolts) {
    // OK to just divide FF by 12 because voltage compensation is enabled
    leftLeader.set(
        TalonSRXControlMode.Velocity,
        Units.radiansToRotations(leftRadPerSec) / 10.0, // Raw units are ticks per 100ms :(
        DemandType.ArbitraryFeedForward,
        leftFFVolts / 12.0);
    rightLeader.set(
        TalonSRXControlMode.Velocity,
        Units.radiansToRotations(rightRadPerSec) / 10.0, // Raw units are ticks per 100ms :(
        DemandType.ArbitraryFeedForward,
        rightFFVolts / 12.0);
  }
}