package javax.sound.midi;

public interface Transmitter extends AutoCloseable {
  void setReceiver(Receiver paramReceiver);
  
  Receiver getReceiver();
  
  void close();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sound\midi\Transmitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */