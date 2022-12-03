package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import lib.iotemplates.SolenoidIO;
import lib.iotemplates.SolenoidIOBase;





public class Shooter extends SubsystemBase {
    //Find those IDs
    private final SolenoidIOBase solenoidIO = new SolenoidIOBase(6, 7);



    public void armOut(){
        solenoidIO.set(DoubleSolenoid.Value.kForward);
    }

    public void armIn(){
        solenoidIO.set(DoubleSolenoid.Value.kReverse);
    }

}
