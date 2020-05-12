package javax.sound.midi;

public interface Synthesizer extends MidiDevice {
  int getMaxPolyphony();
  
  long getLatency();
  
  MidiChannel[] getChannels();
  
  VoiceStatus[] getVoiceStatus();
  
  boolean isSoundbankSupported(Soundbank paramSoundbank);
  
  boolean loadInstrument(Instrument paramInstrument);
  
  void unloadInstrument(Instrument paramInstrument);
  
  boolean remapInstrument(Instrument paramInstrument1, Instrument paramInstrument2);
  
  Soundbank getDefaultSoundbank();
  
  Instrument[] getAvailableInstruments();
  
  Instrument[] getLoadedInstruments();
  
  boolean loadAllInstruments(Soundbank paramSoundbank);
  
  void unloadAllInstruments(Soundbank paramSoundbank);
  
  boolean loadInstruments(Soundbank paramSoundbank, Patch[] paramArrayOfPatch);
  
  void unloadInstruments(Soundbank paramSoundbank, Patch[] paramArrayOfPatch);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sound\midi\Synthesizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */