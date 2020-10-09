package sun.dc.path;

public interface FastPathProducer {
  void getBox(float[] paramArrayOffloat) throws PathError;
  
  void sendTo(PathConsumer paramPathConsumer) throws PathError, PathException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\dc\path\FastPathProducer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */