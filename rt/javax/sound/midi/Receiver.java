package javax.sound.midi;

public interface Receiver extends AutoCloseable {
  void send(MidiMessage paramMidiMessage, long paramLong);
  
  void close();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sound\midi\Receiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */