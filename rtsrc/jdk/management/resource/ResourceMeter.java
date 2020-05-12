package jdk.management.resource;

public interface ResourceMeter {
  long getValue();
  
  long getAllocated();
  
  ResourceType getType();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\ResourceMeter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */