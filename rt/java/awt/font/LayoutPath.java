package java.awt.font;

import java.awt.geom.Point2D;

public abstract class LayoutPath {
  public abstract boolean pointToPath(Point2D paramPoint2D1, Point2D paramPoint2D2);
  
  public abstract void pathToPoint(Point2D paramPoint2D1, boolean paramBoolean, Point2D paramPoint2D2);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\font\LayoutPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */