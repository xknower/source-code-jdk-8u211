/*    */ package com.sun.imageio.plugins.gif;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Locale;
/*    */ import javax.imageio.ImageReader;
/*    */ import javax.imageio.spi.ImageReaderSpi;
/*    */ import javax.imageio.stream.ImageInputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GIFImageReaderSpi
/*    */   extends ImageReaderSpi
/*    */ {
/*    */   private static final String vendorName = "Oracle Corporation";
/*    */   private static final String version = "1.0";
/* 43 */   private static final String[] names = new String[] { "gif", "GIF" };
/*    */   
/* 45 */   private static final String[] suffixes = new String[] { "gif" };
/*    */   
/* 47 */   private static final String[] MIMETypes = new String[] { "image/gif" };
/*    */ 
/*    */   
/*    */   private static final String readerClassName = "com.sun.imageio.plugins.gif.GIFImageReader";
/*    */   
/* 52 */   private static final String[] writerSpiNames = new String[] { "com.sun.imageio.plugins.gif.GIFImageWriterSpi" };
/*    */ 
/*    */ 
/*    */   
/*    */   public GIFImageReaderSpi() {
/* 57 */     super("Oracle Corporation", "1.0", names, suffixes, MIMETypes, "com.sun.imageio.plugins.gif.GIFImageReader", new Class[] { ImageInputStream.class }, writerSpiNames, true, "javax_imageio_gif_stream_1.0", "com.sun.imageio.plugins.gif.GIFStreamMetadataFormat", null, null, true, "javax_imageio_gif_image_1.0", "com.sun.imageio.plugins.gif.GIFImageMetadataFormat", null, null);
/*    */   }
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
/*    */   public String getDescription(Locale paramLocale) {
/* 77 */     return "Standard GIF image reader";
/*    */   }
/*    */   
/*    */   public boolean canDecodeInput(Object paramObject) throws IOException {
/* 81 */     if (!(paramObject instanceof ImageInputStream)) {
/* 82 */       return false;
/*    */     }
/*    */     
/* 85 */     ImageInputStream imageInputStream = (ImageInputStream)paramObject;
/* 86 */     byte[] arrayOfByte = new byte[6];
/* 87 */     imageInputStream.mark();
/* 88 */     imageInputStream.readFully(arrayOfByte);
/* 89 */     imageInputStream.reset();
/*    */     
/* 91 */     return (arrayOfByte[0] == 71 && arrayOfByte[1] == 73 && arrayOfByte[2] == 70 && arrayOfByte[3] == 56 && (arrayOfByte[4] == 55 || arrayOfByte[4] == 57) && arrayOfByte[5] == 97);
/*    */   }
/*    */ 
/*    */   
/*    */   public ImageReader createReaderInstance(Object paramObject) {
/* 96 */     return new GIFImageReader(this);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\imageio\plugins\gif\GIFImageReaderSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */