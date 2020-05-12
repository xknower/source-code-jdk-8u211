package com.sun.media.sound;

public interface SoftProcess extends SoftControl {
  void init(SoftSynthesizer paramSoftSynthesizer);
  
  double[] get(int paramInt, String paramString);
  
  void processControlLogic();
  
  void reset();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\SoftProcess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */