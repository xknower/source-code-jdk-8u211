package javax.swing.event;

import java.util.EventListener;

public interface InternalFrameListener extends EventListener {
  void internalFrameOpened(InternalFrameEvent paramInternalFrameEvent);
  
  void internalFrameClosing(InternalFrameEvent paramInternalFrameEvent);
  
  void internalFrameClosed(InternalFrameEvent paramInternalFrameEvent);
  
  void internalFrameIconified(InternalFrameEvent paramInternalFrameEvent);
  
  void internalFrameDeiconified(InternalFrameEvent paramInternalFrameEvent);
  
  void internalFrameActivated(InternalFrameEvent paramInternalFrameEvent);
  
  void internalFrameDeactivated(InternalFrameEvent paramInternalFrameEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\event\InternalFrameListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */