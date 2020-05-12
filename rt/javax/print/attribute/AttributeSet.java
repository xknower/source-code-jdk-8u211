package javax.print.attribute;

public interface AttributeSet {
  Attribute get(Class<?> paramClass);
  
  boolean add(Attribute paramAttribute);
  
  boolean remove(Class<?> paramClass);
  
  boolean remove(Attribute paramAttribute);
  
  boolean containsKey(Class<?> paramClass);
  
  boolean containsValue(Attribute paramAttribute);
  
  boolean addAll(AttributeSet paramAttributeSet);
  
  int size();
  
  Attribute[] toArray();
  
  void clear();
  
  boolean isEmpty();
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\print\attribute\AttributeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */