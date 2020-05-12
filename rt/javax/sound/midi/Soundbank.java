package javax.sound.midi;

public interface Soundbank {
  String getName();
  
  String getVersion();
  
  String getVendor();
  
  String getDescription();
  
  SoundbankResource[] getResources();
  
  Instrument[] getInstruments();
  
  Instrument getInstrument(Patch paramPatch);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sound\midi\Soundbank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */