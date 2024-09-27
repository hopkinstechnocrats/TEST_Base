// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
<<<<<<< Updated upstream
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
=======
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
>>>>>>> Stashed changes

import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.PhotonVision;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  WPI_TalonSRX leftLeader;
  WPI_TalonSRX leftFollower;
  WPI_TalonSRX rightLeader;
  WPI_TalonSRX rightFollower;
  DifferentialDrive drive;
  public DigitalInput limitSwitch;
  PhotonVision m_PhotonVision = new PhotonVision();

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

  public void drive1 (double x_target, double y_target){
    
    Transform3d Actual_TF = m_PhotonVision.GetCamData();
    double x_actual = Actual_TF.getX();
    double y_actual = Actual_TF.getY();
    double z_actual = Actual_TF.getZ();
    double fwdbkwd = (x_actual - x_target);
    double leftright = (y_actual - y_target);
    if (x_actual>0){
      drive.tankDrive(-fwdbkwd, -leftright);
      System.out.println(x_actual);
      System.out.println(y_actual);
      System.out.println(z_actual);

      //wait
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        Thread.currentThread().interrupt();
      }
    } else {
      drive.tankDrive(0,0);
    }
    
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
