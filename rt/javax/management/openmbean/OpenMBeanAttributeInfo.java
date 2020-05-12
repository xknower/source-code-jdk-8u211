package javax.management.openmbean;

public interface OpenMBeanAttributeInfo extends OpenMBeanParameterInfo {
  boolean isReadable();
  
  boolean isWritable();
  
  boolean isIs();
  
  boolean equals(Object paramObject);
  
  int hashCode();
  
  String toString();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\openmbean\OpenMBeanAttributeInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */