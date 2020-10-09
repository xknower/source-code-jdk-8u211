package sun.java2d.pipe;

public interface AATileGenerator {
  int getTileWidth();
  
  int getTileHeight();
  
  int getTypicalAlpha();
  
  void nextTile();
  
  void getAlpha(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  void dispose();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\pipe\AATileGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */