package javax.sound.midi;

import java.util.EventListener;

public interface ControllerEventListener extends EventListener {
  void controlChange(ShortMessage paramShortMessage);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sound\midi\ControllerEventListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */