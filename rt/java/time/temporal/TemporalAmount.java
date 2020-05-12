package java.time.temporal;

import java.util.List;

public interface TemporalAmount {
  long get(TemporalUnit paramTemporalUnit);
  
  List<TemporalUnit> getUnits();
  
  Temporal addTo(Temporal paramTemporal);
  
  Temporal subtractFrom(Temporal paramTemporal);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\time\temporal\TemporalAmount.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */