// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  WPI_TalonSRX leftMaster;
  WPI_TalonSRX leftFollower;
  WPI_TalonSRX rightMaster;
  WPI_TalonSRX rightFollower;
  DifferentialDrive drive;
  public DigitalInput limitSwitch;
  

  public DriveSubsystem() {
    leftMaster = new WPI_TalonSRX(Constants.leftMasterCANID);
    leftFollower = new WPI_TalonSRX(Constants.leftFollowerCANID);
    rightMaster = new WPI_TalonSRX(Constants.rightMasterCANID);
    rightFollower = new WPI_TalonSRX(Constants.rightFollowerCANID);
    leftMaster.configFactoryDefault();
    leftFollower.configFactoryDefault();
    rightMaster.configFactoryDefault();
    rightFollower.configFactoryDefault();
    leftMaster.setNeutralMode(NeutralMode.Brake);
    rightMaster.setNeutralMode(NeutralMode.Brake);
    leftFollower.setNeutralMode(NeutralMode.Brake);
    rightFollower.setNeutralMode(NeutralMode.Brake);

    drive = new DifferentialDrive(
      leftMaster,
      rightMaster
    );
    leftFollower.follow(leftMaster);
    leftFollower.setInverted(true);
    rightFollower.follow(rightMaster);
    
  }

  public void drive(double left, double right) {
    drive.tankDrive(-left, -right);
    System.out.println("left: "+ left+ ", right: "+ right);
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
