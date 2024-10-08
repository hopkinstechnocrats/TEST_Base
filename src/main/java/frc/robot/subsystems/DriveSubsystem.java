// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  WPI_TalonSRX leftLeader;
  WPI_TalonSRX leftFollower;
  WPI_TalonSRX rightLeader;
  WPI_TalonSRX rightFollower;
  DifferentialDrive drive;
  public DigitalInput limitSwitch;
  

  public DriveSubsystem() {
    leftLeader = new WPI_TalonSRX(Constants.leftLeaderCANID);
    leftFollower = new WPI_TalonSRX(Constants.leftFollowerCANID);
    rightLeader = new WPI_TalonSRX(Constants.rightLeaderCANID);
    rightFollower = new WPI_TalonSRX(Constants.rightFollowerCANID);
    leftLeader.configFactoryDefault();
    leftFollower.configFactoryDefault();
    rightLeader.configFactoryDefault();
    rightFollower.configFactoryDefault();
    leftLeader.setNeutralMode(NeutralMode.Brake);
    rightLeader.setNeutralMode(NeutralMode.Brake);
    leftFollower.setNeutralMode(NeutralMode.Brake);
    rightFollower.setNeutralMode(NeutralMode.Brake);

    drive = new DifferentialDrive(leftLeader, rightLeader);

    leftFollower.follow(leftLeader);
    rightFollower.follow(rightLeader);

    leftLeader.setInverted(true);
    leftFollower.setInverted(true);
    
    
  }

  public void drive(double left, double right) {
    drive.tankDrive(left, right);
    //readable log printed to roboRIO log accessable from VScode
    //System.out.println("left: "+ left+ ", right: "+ right);
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
