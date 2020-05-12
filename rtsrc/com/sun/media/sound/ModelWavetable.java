package com.sun.media.sound;

public interface ModelWavetable extends ModelOscillator {
  public static final int LOOP_TYPE_OFF = 0;
  
  public static final int LOOP_TYPE_FORWARD = 1;
  
  public static final int LOOP_TYPE_RELEASE = 2;
  
  public static final int LOOP_TYPE_PINGPONG = 4;
  
  public static final int LOOP_TYPE_REVERSE = 8;
  
  AudioFloatInputStream openStream();
  
  float getLoopLength();
  
  float getLoopStart();
  
  int getLoopType();
  
  float getPitchcorrection();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\ModelWavetable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */