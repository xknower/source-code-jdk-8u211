package sun.java2d.pipe;

public interface SpanIterator {
  void getPathBox(int[] paramArrayOfint);
  
  void intersectClipBox(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  boolean nextSpan(int[] paramArrayOfint);
  
  void skipDownTo(int paramInt);
  
  long getNativeIterator();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\pipe\SpanIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */