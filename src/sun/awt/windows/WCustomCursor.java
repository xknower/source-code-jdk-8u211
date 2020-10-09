/*    */ package sun.awt.windows;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Image;
/*    */ import java.awt.Point;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.awt.image.DataBuffer;
/*    */ import java.awt.image.DataBufferInt;
/*    */ import java.awt.image.WritableRaster;
/*    */ import sun.awt.CustomCursor;
/*    */ import sun.awt.image.ImageRepresentation;
/*    */ import sun.awt.image.IntegerComponentRaster;
/*    */ import sun.awt.image.ToolkitImage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class WCustomCursor
/*    */   extends CustomCursor
/*    */ {
/*    */   WCustomCursor(Image paramImage, Point paramPoint, String paramString) throws IndexOutOfBoundsException {
/* 45 */     super(paramImage, paramPoint, paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void createNativeCursor(Image paramImage, int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 51 */     BufferedImage bufferedImage = new BufferedImage(paramInt1, paramInt2, 1);
/*    */     
/* 53 */     Graphics graphics = bufferedImage.getGraphics();
/*    */     try {
/* 55 */       if (paramImage instanceof ToolkitImage) {
/* 56 */         ImageRepresentation imageRepresentation = ((ToolkitImage)paramImage).getImageRep();
/* 57 */         imageRepresentation.reconstruct(32);
/*    */       } 
/* 59 */       graphics.drawImage(paramImage, 0, 0, paramInt1, paramInt2, null);
/*    */     } finally {
/* 61 */       graphics.dispose();
/*    */     } 
/* 63 */     WritableRaster writableRaster = bufferedImage.getRaster();
/* 64 */     DataBuffer dataBuffer = writableRaster.getDataBuffer();
/*    */     
/* 66 */     int[] arrayOfInt = ((DataBufferInt)dataBuffer).getData();
/*    */     
/* 68 */     byte[] arrayOfByte = new byte[paramInt1 * paramInt2 / 8];
/* 69 */     int i = paramArrayOfint.length; int j;
/* 70 */     for (j = 0; j < i; j++) {
/* 71 */       int k = j / 8;
/* 72 */       int m = 1 << 7 - j % 8;
/* 73 */       if ((paramArrayOfint[j] & 0xFF000000) == 0)
/*    */       {
/* 75 */         arrayOfByte[k] = (byte)(arrayOfByte[k] | m);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 80 */     j = writableRaster.getWidth();
/* 81 */     if (writableRaster instanceof IntegerComponentRaster) {
/* 82 */       j = ((IntegerComponentRaster)writableRaster).getScanlineStride();
/*    */     }
/* 84 */     createCursorIndirect(((DataBufferInt)bufferedImage
/* 85 */         .getRaster().getDataBuffer()).getData(), arrayOfByte, j, writableRaster
/* 86 */         .getWidth(), writableRaster.getHeight(), paramInt3, paramInt4);
/*    */   }
/*    */   
/*    */   private native void createCursorIndirect(int[] paramArrayOfint, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*    */   
/*    */   static native int getCursorWidth();
/*    */   
/*    */   static native int getCursorHeight();
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WCustomCursor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */