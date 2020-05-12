package sun.text.normalizer;

public interface RangeValueIterator {
  boolean next(Element paramElement);
  
  void reset();
  
  public static class Element {
    public int start;
    
    public int limit;
    
    public int value;
  }
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\text\normalizer\RangeValueIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */