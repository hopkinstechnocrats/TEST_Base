package lib.iotemplates;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class SolenoidIOBase implements SolenoidIO {
    private final Solenoid solenoid;
    boolean currentState;

    public SolenoidIOBase(int fwdChannel) {
        solenoid = new Solenoid(PneumaticsModuleType.REVPH, fwdChannel);
        currentState = false;
    }

    @Override
    public void updateInputs(SolenoidIOInputs inputs) {
        
    }

    @Override
    public void set(DoubleSolenoid.Value state) {
        if(state == Value.kForward && currentState == false) {
            solenoid.toggle();
            currentState = true;
        }else if(state == Value.kReverse && !currentState){
            solenoid.toggle();
            currentState = false;
        }
    }
}
