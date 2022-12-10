package lib.iotemplates;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public interface SolenoidIO {
    class SolenoidIOInputs {
        public boolean fwdSolenoidDisabled = false;
        public boolean revSolenoidDisabled = false;
        public DoubleSolenoid.Value state = DoubleSolenoid.Value.kOff;
    }

    void set(boolean state);

}
