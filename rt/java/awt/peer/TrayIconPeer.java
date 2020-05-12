package java.awt.peer;

public interface TrayIconPeer {
  void dispose();
  
  void setToolTip(String paramString);
  
  void updateImage();
  
  void displayMessage(String paramString1, String paramString2, String paramString3);
  
  void showPopupMenu(int paramInt1, int paramInt2);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\peer\TrayIconPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */