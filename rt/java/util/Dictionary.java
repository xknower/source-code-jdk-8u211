package java.util;

public abstract class Dictionary<K, V> {
  public abstract int size();
  
  public abstract boolean isEmpty();
  
  public abstract Enumeration<K> keys();
  
  public abstract Enumeration<V> elements();
  
  public abstract V get(Object paramObject);
  
  public abstract V put(K paramK, V paramV);
  
  public abstract V remove(Object paramObject);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\Dictionary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */