package com.sun.media.sound;

import java.io.IOException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.VoiceStatus;

public interface ModelOscillatorStream {
  void setPitch(float paramFloat);
  
  void noteOn(MidiChannel paramMidiChannel, VoiceStatus paramVoiceStatus, int paramInt1, int paramInt2);
  
  void noteOff(int paramInt);
  
  int read(float[][] paramArrayOffloat, int paramInt1, int paramInt2) throws IOException;
  
  void close() throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\ModelOscillatorStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */