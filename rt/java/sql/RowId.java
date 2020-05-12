package java.sql;

public interface RowId {
  boolean equals(Object paramObject);
  
  byte[] getBytes();
  
  String toString();
  
  int hashCode();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\sql\RowId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */