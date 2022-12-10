package lib.iotemplates;

import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class SolenoidIOBase implements SolenoidIO {
    private final Solenoid solenoid;
    boolean currentState;

    public SolenoidIOBase(int fwdChannel) {
        solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, fwdChannel);
        currentState = false;
    }
    @Override
    public void set(boolean state) {
        solenoid.set(state);
    }

    public void pulse(){
        solenoid.setPulseDuration(2);
        solenoid.startPulse();
    }
}
