package javax.sound.sampled;

public interface SourceDataLine extends DataLine {
  void open(AudioFormat paramAudioFormat, int paramInt) throws LineUnavailableException;
  
  void open(AudioFormat paramAudioFormat) throws LineUnavailableException;
  
  int write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sound\sampled\SourceDataLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */