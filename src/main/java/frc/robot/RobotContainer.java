// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.Compressor;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();

  private final XboxController driveController = new XboxController(Constants.XboxControllerPort);
  public final Compressor phCompressor = new Compressor(PneumaticsModuleType.REVPH);
  private final XboxController operatorController = new XboxController(1);
  private final Shooter m_shooter;
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    driveSubsystem.setDefaultCommand(
            new RunCommand(
                    () -> {
                      driveSubsystem.drive(-0.55*driveController.getLeftY(), -0.55*driveController.getRightY());
                    }
            , driveSubsystem)
    );
    m_shooter = new Shooter();
    Solenoid obj = new Solenoid(PneumaticsModuleType.REVPH, 0);
      obj.set(true);
      phCompressor.enableAnalog(100, 120);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton aButton = new JoystickButton(operatorController, 1);
    JoystickButton bButton = new JoystickButton(operatorController, 2);
    JoystickButton xButton = new JoystickButton(operatorController, 3);
    JoystickButton yButton = new JoystickButton(operatorController, 4);
    JoystickButton aDriverButton = new JoystickButton(driveController, 1);
    JoystickButton bDriverButton = new JoystickButton(driveController, 2);

    xButton.whenPressed(new InstantCommand(m_shooter::armOut, m_shooter));
    yButton.whenPressed(new InstantCommand(m_shooter::armIn, m_shooter));
  }
   
  

  public DriveSubsystem getDriveSubsystem() {
    return driveSubsystem;
  }
  
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new SequentialCommandGroup(
    );
  }
}
