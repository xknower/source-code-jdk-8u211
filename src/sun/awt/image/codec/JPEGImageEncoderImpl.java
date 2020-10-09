/*     */ package sun.awt.image.codec;
/*     */ 
/*     */ import com.sun.image.codec.jpeg.ImageFormatException;
/*     */ import com.sun.image.codec.jpeg.JPEGDecodeParam;
/*     */ import com.sun.image.codec.jpeg.JPEGEncodeParam;
/*     */ import com.sun.image.codec.jpeg.JPEGImageEncoder;
/*     */ import java.awt.Point;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.awt.image.ComponentSampleModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.RescaleOp;
/*     */ import java.awt.image.SampleModel;
/*     */ import java.awt.image.SinglePixelPackedSampleModel;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JPEGImageEncoderImpl
/*     */   implements JPEGImageEncoder
/*     */ {
/*  62 */   private OutputStream outStream = null;
/*  63 */   private JPEGParam param = null;
/*     */   
/*     */   private boolean pack = false;
/*  66 */   private static final Class OutputStreamClass = OutputStream.class;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  73 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  76 */             System.loadLibrary("jpeg");
/*  77 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JPEGImageEncoderImpl(OutputStream paramOutputStream) {
/*  88 */     if (paramOutputStream == null) {
/*  89 */       throw new IllegalArgumentException("OutputStream is null.");
/*     */     }
/*  91 */     this.outStream = paramOutputStream;
/*  92 */     initEncoder(OutputStreamClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JPEGImageEncoderImpl(OutputStream paramOutputStream, JPEGEncodeParam paramJPEGEncodeParam) {
/* 102 */     this(paramOutputStream);
/* 103 */     setJPEGEncodeParam(paramJPEGEncodeParam);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultColorId(ColorModel paramColorModel) {
/* 118 */     boolean bool = paramColorModel.hasAlpha();
/* 119 */     ColorSpace colorSpace1 = paramColorModel.getColorSpace();
/* 120 */     ColorSpace colorSpace2 = null;
/* 121 */     switch (colorSpace1.getType()) {
/*     */       case 6:
/* 123 */         return 1;
/*     */       
/*     */       case 5:
/* 126 */         if (bool) {
/* 127 */           return 7;
/*     */         }
/* 129 */         return 3;
/*     */       
/*     */       case 3:
/* 132 */         if (colorSpace2 == null) {
/*     */           try {
/* 134 */             colorSpace2 = ColorSpace.getInstance(1002);
/*     */           }
/* 136 */           catch (IllegalArgumentException illegalArgumentException) {}
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 141 */         if (colorSpace1 == colorSpace2) {
/* 142 */           return bool ? 10 : 5;
/*     */         }
/*     */ 
/*     */         
/* 146 */         return bool ? 7 : 3;
/*     */ 
/*     */ 
/*     */       
/*     */       case 9:
/* 151 */         return 4;
/*     */     } 
/* 153 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized OutputStream getOutputStream() {
/* 158 */     return this.outStream;
/*     */   }
/*     */   
/*     */   public synchronized void setJPEGEncodeParam(JPEGEncodeParam paramJPEGEncodeParam) {
/* 162 */     this.param = new JPEGParam(paramJPEGEncodeParam);
/*     */   }
/*     */   public synchronized JPEGEncodeParam getJPEGEncodeParam() {
/* 165 */     return (JPEGEncodeParam)this.param.clone();
/*     */   }
/*     */   
/*     */   public JPEGEncodeParam getDefaultJPEGEncodeParam(Raster paramRaster, int paramInt) {
/* 169 */     JPEGParam jPEGParam = new JPEGParam(paramInt, paramRaster.getNumBands());
/* 170 */     jPEGParam.setWidth(paramRaster.getWidth());
/* 171 */     jPEGParam.setHeight(paramRaster.getHeight());
/*     */     
/* 173 */     return jPEGParam;
/*     */   }
/*     */   
/*     */   public JPEGEncodeParam getDefaultJPEGEncodeParam(BufferedImage paramBufferedImage) {
/*     */     JPEGParam jPEGParam;
/* 178 */     ColorModel colorModel = paramBufferedImage.getColorModel();
/* 179 */     int i = getDefaultColorId(colorModel);
/*     */     
/* 181 */     if (!(colorModel instanceof IndexColorModel)) {
/* 182 */       return getDefaultJPEGEncodeParam(paramBufferedImage.getRaster(), i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (colorModel.hasAlpha()) { jPEGParam = new JPEGParam(i, 4); }
/* 188 */     else { jPEGParam = new JPEGParam(i, 3); }
/*     */     
/* 190 */     jPEGParam.setWidth(paramBufferedImage.getWidth());
/* 191 */     jPEGParam.setHeight(paramBufferedImage.getHeight());
/* 192 */     return jPEGParam;
/*     */   }
/*     */ 
/*     */   
/*     */   public JPEGEncodeParam getDefaultJPEGEncodeParam(int paramInt1, int paramInt2) {
/* 197 */     return new JPEGParam(paramInt2, paramInt1);
/*     */   }
/*     */ 
/*     */   
/*     */   public JPEGEncodeParam getDefaultJPEGEncodeParam(JPEGDecodeParam paramJPEGDecodeParam) throws ImageFormatException {
/* 202 */     return new JPEGParam(paramJPEGDecodeParam);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void encode(BufferedImage paramBufferedImage) throws IOException, ImageFormatException {
/* 208 */     if (this.param == null) {
/* 209 */       setJPEGEncodeParam(getDefaultJPEGEncodeParam(paramBufferedImage));
/*     */     }
/* 211 */     if (paramBufferedImage.getWidth() != this.param.getWidth() || paramBufferedImage
/* 212 */       .getHeight() != this.param.getHeight()) {
/* 213 */       throw new ImageFormatException("Param block's width/height doesn't match the BufferedImage");
/*     */     }
/*     */     
/* 216 */     if (this.param.getEncodedColorID() != 
/* 217 */       getDefaultColorId(paramBufferedImage.getColorModel())) {
/* 218 */       throw new ImageFormatException("The encoded COLOR_ID doesn't match the BufferedImage");
/*     */     }
/*     */     
/* 221 */     WritableRaster writableRaster = paramBufferedImage.getRaster();
/* 222 */     ColorModel colorModel = paramBufferedImage.getColorModel();
/*     */ 
/*     */     
/* 225 */     if (colorModel instanceof IndexColorModel) {
/* 226 */       IndexColorModel indexColorModel = (IndexColorModel)colorModel;
/* 227 */       paramBufferedImage = indexColorModel.convertToIntDiscrete(writableRaster, false);
/* 228 */       writableRaster = paramBufferedImage.getRaster();
/* 229 */       colorModel = paramBufferedImage.getColorModel();
/*     */     } 
/*     */     
/* 232 */     encode(writableRaster, colorModel);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void encode(BufferedImage paramBufferedImage, JPEGEncodeParam paramJPEGEncodeParam) throws IOException, ImageFormatException {
/* 238 */     setJPEGEncodeParam(paramJPEGEncodeParam);
/* 239 */     encode(paramBufferedImage);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(Raster paramRaster) throws IOException, ImageFormatException {
/* 245 */     if (this.param == null) {
/* 246 */       setJPEGEncodeParam(
/* 247 */           getDefaultJPEGEncodeParam(paramRaster, 0));
/*     */     }
/* 249 */     if (paramRaster.getNumBands() != paramRaster.getSampleModel().getNumBands()) {
/* 250 */       throw new ImageFormatException("Raster's number of bands doesn't match the SampleModel");
/*     */     }
/*     */     
/* 253 */     if (paramRaster.getWidth() != this.param.getWidth() || paramRaster
/* 254 */       .getHeight() != this.param.getHeight()) {
/* 255 */       throw new ImageFormatException("Param block's width/height doesn't match the Raster");
/*     */     }
/*     */     
/* 258 */     if (this.param.getEncodedColorID() != 0 && this.param
/* 259 */       .getNumComponents() != paramRaster.getNumBands()) {
/* 260 */       throw new ImageFormatException("Param block's COLOR_ID doesn't match the Raster.");
/*     */     }
/*     */     
/* 263 */     encode(paramRaster, (ColorModel)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(Raster paramRaster, JPEGEncodeParam paramJPEGEncodeParam) throws IOException, ImageFormatException {
/* 269 */     setJPEGEncodeParam(paramJPEGEncodeParam);
/* 270 */     encode(paramRaster);
/*     */   }
/*     */   
/*     */   private boolean useGiven(Raster paramRaster) {
/* 274 */     SampleModel sampleModel = paramRaster.getSampleModel();
/* 275 */     if (sampleModel.getDataType() != 0) {
/* 276 */       return false;
/*     */     }
/* 278 */     if (!(sampleModel instanceof ComponentSampleModel)) {
/* 279 */       return false;
/*     */     }
/* 281 */     ComponentSampleModel componentSampleModel = (ComponentSampleModel)sampleModel;
/* 282 */     if (componentSampleModel.getPixelStride() != sampleModel.getNumBands())
/* 283 */       return false; 
/* 284 */     int[] arrayOfInt = componentSampleModel.getBandOffsets();
/* 285 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/* 286 */       if (arrayOfInt[b] != b) return false; 
/*     */     } 
/* 288 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canPack(Raster paramRaster) {
/* 294 */     SampleModel sampleModel = paramRaster.getSampleModel();
/* 295 */     if (sampleModel.getDataType() != 3) {
/* 296 */       return false;
/*     */     }
/* 298 */     if (!(sampleModel instanceof SinglePixelPackedSampleModel)) {
/* 299 */       return false;
/*     */     }
/* 301 */     SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)sampleModel;
/*     */ 
/*     */     
/* 304 */     int[] arrayOfInt1 = { 16711680, 65280, 255, -16777216 };
/* 305 */     int[] arrayOfInt2 = singlePixelPackedSampleModel.getBitMasks();
/* 306 */     if (arrayOfInt2.length != 3 && arrayOfInt2.length != 4) {
/* 307 */       return false;
/*     */     }
/* 309 */     for (byte b = 0; b < arrayOfInt2.length; b++) {
/* 310 */       if (arrayOfInt2[b] != arrayOfInt1[b]) return false; 
/*     */     } 
/* 312 */     return true;
/*     */   }
/*     */   
/*     */   private void encode(Raster paramRaster, ColorModel paramColorModel) throws IOException, ImageFormatException {
/*     */     byte[] arrayOfByte;
/*     */     int k, m;
/* 318 */     SampleModel sampleModel = paramRaster.getSampleModel();
/*     */     
/* 320 */     int i = sampleModel.getNumBands();
/* 321 */     if (paramColorModel == null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 326 */       for (byte b = 0; b < i; b++) {
/* 327 */         if (sampleModel.getSampleSize(b) > 8) {
/* 328 */           throw new ImageFormatException("JPEG encoder can only accept 8 bit data.");
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 334 */     int j = this.param.getEncodedColorID();
/* 335 */     switch (this.param.getNumComponents()) {
/*     */       
/*     */       case 1:
/* 338 */         if (j != 1 && j != 0 && this.param
/*     */           
/* 340 */           .findAPP0() != null) {
/* 341 */           throw new ImageFormatException("1 band JFIF files imply Y or unknown encoding.\nParam block indicates alternate encoding.");
/*     */         }
/*     */         break;
/*     */       
/*     */       case 3:
/* 346 */         if (j != 3 && this.param
/* 347 */           .findAPP0() != null) {
/* 348 */           throw new ImageFormatException("3 band JFIF files imply YCbCr encoding.\nParam block indicates alternate encoding.");
/*     */         }
/*     */         break;
/*     */       
/*     */       case 4:
/* 353 */         if (j != 4 && this.param
/* 354 */           .findAPP0() != null) {
/* 355 */           throw new ImageFormatException("4 band JFIF files imply CMYK encoding.\nParam block indicates alternate encoding.");
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 364 */     if (!this.param.isImageInfoValid()) {
/*     */       
/* 366 */       writeJPEGStream(this.param, paramColorModel, this.outStream, null, 0, 0);
/*     */       
/*     */       return;
/*     */     } 
/* 370 */     DataBuffer dataBuffer = paramRaster.getDataBuffer();
/*     */ 
/*     */ 
/*     */     
/* 374 */     boolean bool1 = false;
/* 375 */     boolean bool2 = true;
/* 376 */     int[] arrayOfInt = null;
/*     */     
/* 378 */     if (paramColorModel != null) {
/* 379 */       if (paramColorModel.hasAlpha() && paramColorModel.isAlphaPremultiplied()) {
/* 380 */         bool1 = true;
/* 381 */         bool2 = false;
/*     */       } 
/* 383 */       arrayOfInt = paramColorModel.getComponentSize();
/* 384 */       for (byte b = 0; b < i; b++) {
/* 385 */         if (arrayOfInt[b] != 8) {
/* 386 */           bool2 = false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 391 */     this.pack = false;
/* 392 */     if (bool2 && useGiven(paramRaster)) {
/* 393 */       ComponentSampleModel componentSampleModel = (ComponentSampleModel)sampleModel;
/*     */ 
/*     */       
/* 396 */       m = dataBuffer.getOffset() + componentSampleModel.getOffset(paramRaster
/* 397 */           .getMinX() - paramRaster.getSampleModelTranslateX(), paramRaster
/* 398 */           .getMinY() - paramRaster.getSampleModelTranslateY());
/*     */       
/* 400 */       k = componentSampleModel.getScanlineStride();
/* 401 */       arrayOfByte = ((DataBufferByte)dataBuffer).getData();
/*     */     }
/* 403 */     else if (bool2 && canPack(paramRaster)) {
/*     */       
/* 405 */       SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)sampleModel;
/*     */ 
/*     */       
/* 408 */       m = dataBuffer.getOffset() + singlePixelPackedSampleModel.getOffset(paramRaster
/* 409 */           .getMinX() - paramRaster.getSampleModelTranslateX(), paramRaster
/* 410 */           .getMinY() - paramRaster.getSampleModelTranslateY());
/*     */       
/* 412 */       k = singlePixelPackedSampleModel.getScanlineStride();
/* 413 */       int[] arrayOfInt1 = ((DataBufferInt)dataBuffer).getData();
/* 414 */       this.pack = true;
/*     */     } else {
/*     */       
/* 417 */       int[] arrayOfInt1 = new int[i];
/* 418 */       float[] arrayOfFloat = new float[i]; byte b;
/* 419 */       for (b = 0; b < i; b++) {
/* 420 */         arrayOfInt1[b] = b;
/* 421 */         if (!bool2)
/*     */         {
/*     */           
/* 424 */           if (arrayOfInt[b] != 8) {
/* 425 */             arrayOfFloat[b] = 255.0F / ((1 << arrayOfInt[b]) - 1);
/*     */           } else {
/*     */             
/* 428 */             arrayOfFloat[b] = 1.0F;
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 433 */       ComponentSampleModel componentSampleModel = new ComponentSampleModel(0, paramRaster.getWidth(), paramRaster.getHeight(), i, i * paramRaster.getWidth(), arrayOfInt1);
/*     */       
/* 435 */       WritableRaster writableRaster = Raster.createWritableRaster(componentSampleModel, new Point(paramRaster
/* 436 */             .getMinX(), paramRaster.getMinY()));
/*     */       
/* 438 */       if (bool2) {
/* 439 */         writableRaster.setRect(paramRaster);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/* 448 */         float[] arrayOfFloat1 = new float[i];
/*     */         
/* 450 */         RescaleOp rescaleOp = new RescaleOp(arrayOfFloat, arrayOfFloat1, null);
/*     */         
/* 452 */         rescaleOp.filter(paramRaster, writableRaster);
/* 453 */         if (bool1) {
/* 454 */           int[] arrayOfInt2 = new int[i];
/* 455 */           for (b = 0; b < i; b++) {
/* 456 */             arrayOfInt2[b] = 8;
/*     */           }
/*     */ 
/*     */           
/* 460 */           ComponentColorModel componentColorModel = new ComponentColorModel(paramColorModel.getColorSpace(), arrayOfInt2, true, true, 3, 0);
/*     */ 
/*     */           
/* 463 */           componentColorModel.coerceData(writableRaster, false);
/*     */         } 
/*     */       } 
/*     */       
/* 467 */       dataBuffer = writableRaster.getDataBuffer();
/* 468 */       arrayOfByte = ((DataBufferByte)dataBuffer).getData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 475 */       m = dataBuffer.getOffset() + componentSampleModel.getOffset(0, 0);
/* 476 */       k = componentSampleModel.getScanlineStride();
/*     */     } 
/*     */     
/* 479 */     verify(m, k, dataBuffer.getSize());
/*     */     
/* 481 */     writeJPEGStream(this.param, paramColorModel, this.outStream, arrayOfByte, m, k);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void verify(int paramInt1, int paramInt2, int paramInt3) throws ImageFormatException {
/* 487 */     int i = this.param.getWidth();
/* 488 */     int j = this.param.getHeight();
/*     */ 
/*     */     
/* 491 */     byte b = this.pack ? 1 : this.param.getNumComponents();
/*     */     
/* 493 */     if (i <= 0 || j <= 0 || j > Integer.MAX_VALUE / i)
/*     */     {
/* 495 */       throw new ImageFormatException("Invalid image dimensions");
/*     */     }
/*     */     
/* 498 */     if (paramInt2 < 0 || paramInt2 > Integer.MAX_VALUE / j || paramInt2 > paramInt3)
/*     */     {
/*     */       
/* 501 */       throw new ImageFormatException("Invalid scanline stride: " + paramInt2);
/*     */     }
/*     */ 
/*     */     
/* 505 */     int k = (j - 1) * paramInt2;
/*     */     
/* 507 */     if (!b || b > Integer.MAX_VALUE / i || b > paramInt3 || b * i > paramInt2)
/*     */     {
/*     */ 
/*     */       
/* 511 */       throw new ImageFormatException("Invalid pixel stride: " + b);
/*     */     }
/*     */ 
/*     */     
/* 515 */     int m = i * b;
/* 516 */     if (m > Integer.MAX_VALUE - k) {
/* 517 */       throw new ImageFormatException("Invalid raster attributes");
/*     */     }
/*     */     
/* 520 */     int n = k + m;
/* 521 */     if (paramInt1 < 0 || paramInt1 > Integer.MAX_VALUE - n) {
/* 522 */       throw new ImageFormatException("Invalid data offset");
/*     */     }
/*     */     
/* 525 */     int i1 = paramInt1 + n;
/* 526 */     if (i1 > paramInt3) {
/* 527 */       throw new ImageFormatException("Computed buffer size doesn't match DataBuffer");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getNearestColorId(ColorModel paramColorModel) {
/* 544 */     ColorSpace colorSpace = paramColorModel.getColorSpace();
/* 545 */     switch (colorSpace.getType()) {
/*     */       case 5:
/* 547 */         if (paramColorModel.hasAlpha()) return 6; 
/* 548 */         return 2;
/*     */     } 
/*     */     
/* 551 */     return getDefaultColorId(paramColorModel);
/*     */   }
/*     */   
/*     */   private native void initEncoder(Class paramClass);
/*     */   
/*     */   private synchronized native void writeJPEGStream(JPEGEncodeParam paramJPEGEncodeParam, ColorModel paramColorModel, OutputStream paramOutputStream, Object paramObject, int paramInt1, int paramInt2) throws IOException, ImageFormatException;
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\image\codec\JPEGImageEncoderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */