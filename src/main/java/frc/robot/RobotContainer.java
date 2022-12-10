// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();

  private final ConveyorSubsystem conveyorSubsystem = new ConveyorSubsystem();

  private final XboxController driveController = new XboxController(Constants.DriverControllerPort);
  
  private final XboxController operatorController = new XboxController(Constants.OperatorControllerPort);

  private final Command spinAuto = new SequentialCommandGroup(
    new RunCommand(()-> {conveyorSubsystem.makeSpin(.4);}, conveyorSubsystem).withTimeout(2),
    new RunCommand(()-> {driveSubsystem.drive(.55,0);}, driveSubsystem).withTimeout(8) 
  );

  private final Command goGoAuto = new SequentialCommandGroup(
    new RunCommand(()-> {conveyorSubsystem.makeSpin(.4);}, conveyorSubsystem).withTimeout(2),
    new RunCommand(()-> {driveSubsystem.drive(-.55, -.55);}, driveSubsystem).withTimeout(8) 
  );

  SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
  m_autoChooser.setDefaultOption("Spin To Hook", spinAuto);
  m_autoChooser.addOption("Drive Away From Hook", goGoAuto);
    // Configure the button bindings
    configureButtonBindings();
    driveSubsystem.setDefaultCommand(
      new RunCommand(
        () -> {
          driveSubsystem.drive(0.65*driveController.getLeftY(), 0.65*driveController.getRightY());
        }
        , driveSubsystem)
    );

    conveyorSubsystem.setDefaultCommand(
      new RunCommand(
        () -> {
          conveyorSubsystem.makeSpin(0);
        }
        , conveyorSubsystem)
    );
  
    SmartDashboard.putData(m_autoChooser);
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
    JoystickButton aDriverButton = new JoystickButton(driveController, 1);
    JoystickButton bDriverButton = new JoystickButton(driveController, 2);

    aButton.whileHeld(new RunCommand(() -> {conveyorSubsystem.makeSpin(.7);}, conveyorSubsystem));
    bButton.whileHeld(new RunCommand(() -> {conveyorSubsystem.makeSpin(-.7);}, conveyorSubsystem));
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
    return m_autoChooser.getSelected();
  }
}

 