// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.PhotonVision;
import com.kauailabs.navx.frc.AHRS;

public class DriveSubsystem extends SubsystemBase {
  // Creating all our variables, we will initialize them and set their values later
  WPI_TalonFX leftLeader;
  WPI_TalonFX leftFollower;
  WPI_TalonFX rightLeader;
  WPI_TalonFX rightFollower;
  DifferentialDrive drive;
  public DigitalInput limitSwitch;
  PhotonVision m_PhotonVision = new PhotonVision();

  private double m_yawCurret = 0.0; ///!< From NavX
  private double m_yawTarget = 0.0; ///!< From apriltag
  private boolean m_haveTarget = false; ///!< Have we seen an april tag lately
  AHRS ahrs;

  public DriveSubsystem() {

    //initialize motor controllers
    leftLeader = new WPI_TalonFX(Constants.leftLeaderCANID);
    leftFollower = new WPI_TalonFX(Constants.leftFollowerCANID);
    rightLeader = new WPI_TalonFX(Constants.rightLeaderCANID);
    rightFollower = new WPI_TalonFX(Constants.rightFollowerCANID);
    leftLeader.configFactoryDefault();
    leftFollower.configFactoryDefault();
    rightLeader.configFactoryDefault();
    rightFollower.configFactoryDefault();
    //set motors to default to braking
    leftLeader.setNeutralMode(NeutralMode.Brake);
    rightLeader.setNeutralMode(NeutralMode.Brake);
    leftFollower.setNeutralMode(NeutralMode.Brake);
    rightFollower.setNeutralMode(NeutralMode.Brake);

    // takes in a value for left speed and right speed, can also change to arcade drive for forward speed and turn
    drive = new DifferentialDrive(
      leftLeader,
      rightLeader
    );

    //Makes follower motors do the same thing as the leaders so that we don't have to pass arguments for all four
    leftFollower.follow(leftLeader);
    rightFollower.follow(rightLeader);

    // inverts left motors from the right motors because they are inverted 180 degrees
    leftFollower.setInverted(true);
    leftLeader.setInverted(true);

    ahrs = new AHRS(SPI.Port.kMXP);
  
    
  }

  public void drive(double left, double right) {
    drive.tankDrive(left, right);
    //readable log printed to roboRIO log accessable from VScode
    //System.out.println("left: "+ left+ ", right: "+ right);
  }

  public void drive1 (double x_target, double y_target){
    //x is distance from the camera, y is positive to the left, and z is positive up.
    
    Transform3d Actual_TF = m_PhotonVision.GetCamData();
    double x_actual = Actual_TF.getX();
    double y_actual = Actual_TF.getY();
    double z_actual = Actual_TF.getZ();
    double kp_x = 1.0;
    double kp_y = 10.0;
    m_yawCurret = Math.toRadians(ahrs.getAngle());

    double fwdbkwd = ((x_actual- x_target)*kp_x);
    double leftright = ((y_actual - y_target)*kp_y);

    boolean haveNewData = x_actual != 0 && y_actual != 0;
    
    if (haveNewData){
      //drive.tankDrive(fwdbkwd+leftright, fwdbkwd-leftright);
      m_haveTarget = true;
      //m_yawTarget = 0;
      m_yawTarget = m_yawCurret + Math.atan2(y_actual, x_actual);
      //System.out.println(x_actual);
      //System.out.println(y_actual);
      //System.out.println(z_actual);
      System.out.println(m_yawCurret);

      //wait
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        Thread.currentThread().interrupt();
      }
    } else {
      // TODO: If it's been too long since we saw anything, maybe give up?
      //drive.tankDrive(0,0);
      System.out.println("no april tag seen!");
    }
    if(m_haveTarget || true)
    {
      System.out.println(m_yawCurret);
      System.out.println(m_yawTarget);
      System.out.println(m_haveTarget);

      final double speedCap = 0.5;
      double turnrate = (m_yawCurret - m_yawTarget) * kp_y;
      if(turnrate >  speedCap){turnrate =  speedCap;}
      if(turnrate < -speedCap){turnrate = -speedCap;}
      drive.arcadeDrive(0, turnrate);
    }else{
      
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
