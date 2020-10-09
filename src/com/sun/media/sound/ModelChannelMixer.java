package com.sun.media.sound;

import javax.sound.midi.MidiChannel;

public interface ModelChannelMixer extends MidiChannel {
  boolean process(float[][] paramArrayOffloat, int paramInt1, int paramInt2);
  
  void stop();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\ModelChannelMixer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */