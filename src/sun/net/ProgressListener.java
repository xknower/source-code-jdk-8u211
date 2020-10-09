package sun.net;

import java.util.EventListener;

public interface ProgressListener extends EventListener {
  void progressStart(ProgressEvent paramProgressEvent);
  
  void progressUpdate(ProgressEvent paramProgressEvent);
  
  void progressFinish(ProgressEvent paramProgressEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\net\ProgressListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */