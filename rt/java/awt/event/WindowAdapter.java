package java.awt.event;

public abstract class WindowAdapter implements WindowListener, WindowStateListener, WindowFocusListener {
  public void windowOpened(WindowEvent paramWindowEvent) {}
  
  public void windowClosing(WindowEvent paramWindowEvent) {}
  
  public void windowClosed(WindowEvent paramWindowEvent) {}
  
  public void windowIconified(WindowEvent paramWindowEvent) {}
  
  public void windowDeiconified(WindowEvent paramWindowEvent) {}
  
  public void windowActivated(WindowEvent paramWindowEvent) {}
  
  public void windowDeactivated(WindowEvent paramWindowEvent) {}
  
  public void windowStateChanged(WindowEvent paramWindowEvent) {}
  
  public void windowGainedFocus(WindowEvent paramWindowEvent) {}
  
  public void windowLostFocus(WindowEvent paramWindowEvent) {}
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\event\WindowAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */