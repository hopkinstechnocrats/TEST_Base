package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import lib.iotemplates.SolenoidIO;
import lib.iotemplates.SolenoidIOBase;
import edu.wpi.first.wpilibj.Compressor;





public class Shooter extends SubsystemBase {
    //Find those IDs
    private final SolenoidIOBase solenoidIO = new SolenoidIOBase(5);
    //what module type

    public void armOut(){
        solenoidIO.set(Value.kForward);
    }

    public void armIn(){
        solenoidIO.set(Value.kReverse);
    }

}
