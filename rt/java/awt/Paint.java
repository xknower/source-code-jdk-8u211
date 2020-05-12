package java.awt;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

public interface Paint extends Transparency {
  PaintContext createContext(ColorModel paramColorModel, Rectangle paramRectangle, Rectangle2D paramRectangle2D, AffineTransform paramAffineTransform, RenderingHints paramRenderingHints);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\Paint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */