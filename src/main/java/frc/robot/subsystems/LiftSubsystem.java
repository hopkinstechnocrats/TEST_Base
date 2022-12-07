// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LiftSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  WPI_TalonSRX Liftmotor;


  public LiftSubsystem() {
    Liftmotor = new WPI_TalonSRX(Constants.liftMotorCANID);
    Liftmotor.setNeutralMode(NeutralMode.Brake);
    
  }

  public void makeSpin(double speed) {
    Liftmotor.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}