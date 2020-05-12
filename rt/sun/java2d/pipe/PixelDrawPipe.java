package sun.java2d.pipe;

import sun.java2d.SunGraphics2D;

public interface PixelDrawPipe {
  void drawLine(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void drawRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void drawRoundRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  void drawOval(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void drawArc(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  void drawPolyline(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt);
  
  void drawPolygon(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\pipe\PixelDrawPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */