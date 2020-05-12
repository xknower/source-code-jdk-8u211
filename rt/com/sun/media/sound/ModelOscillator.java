package com.sun.media.sound;

public interface ModelOscillator {
  int getChannels();
  
  float getAttenuation();
  
  ModelOscillatorStream open(float paramFloat);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\ModelOscillator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */