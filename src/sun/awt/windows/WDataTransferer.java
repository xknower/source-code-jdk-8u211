/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.FlavorTable;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import sun.awt.datatransfer.DataTransferer;
/*     */ import sun.awt.datatransfer.ToolkitThreadBlockedHandler;
/*     */ import sun.awt.image.ImageRepresentation;
/*     */ import sun.awt.image.ToolkitImage;
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
/*     */ final class WDataTransferer
/*     */   extends DataTransferer
/*     */ {
/*  88 */   private static final String[] predefinedClipboardNames = new String[] { "", "TEXT", "BITMAP", "METAFILEPICT", "SYLK", "DIF", "TIFF", "OEM TEXT", "DIB", "PALETTE", "PENDATA", "RIFF", "WAVE", "UNICODE TEXT", "ENHMETAFILE", "HDROP", "LOCALE", "DIBV5" };
/*     */ 
/*     */   
/*     */   private static final Map<String, Long> predefinedClipboardNameMap;
/*     */ 
/*     */   
/*     */   public static final int CF_TEXT = 1;
/*     */ 
/*     */   
/*     */   public static final int CF_METAFILEPICT = 3;
/*     */ 
/*     */   
/*     */   public static final int CF_DIB = 8;
/*     */ 
/*     */   
/*     */   public static final int CF_ENHMETAFILE = 14;
/*     */   
/*     */   public static final int CF_HDROP = 15;
/*     */   
/*     */   public static final int CF_LOCALE = 16;
/*     */ 
/*     */   
/*     */   static {
/* 111 */     HashMap<Object, Object> hashMap = new HashMap<>(predefinedClipboardNames.length, 1.0F);
/*     */     
/* 113 */     for (byte b = 1; b < predefinedClipboardNames.length; b++) {
/* 114 */       hashMap.put(predefinedClipboardNames[b], Long.valueOf(b));
/*     */     }
/*     */     
/* 117 */     predefinedClipboardNameMap = Collections.synchronizedMap(hashMap);
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
/* 130 */   public static final long CF_HTML = registerClipboardFormat("HTML Format");
/* 131 */   public static final long CFSTR_INETURL = registerClipboardFormat("UniformResourceLocator");
/* 132 */   public static final long CF_PNG = registerClipboardFormat("PNG");
/* 133 */   public static final long CF_JFIF = registerClipboardFormat("JFIF");
/*     */   
/* 135 */   public static final long CF_FILEGROUPDESCRIPTORW = registerClipboardFormat("FileGroupDescriptorW");
/* 136 */   public static final long CF_FILEGROUPDESCRIPTORA = registerClipboardFormat("FileGroupDescriptor");
/*     */ 
/*     */   
/* 139 */   private static final Long L_CF_LOCALE = predefinedClipboardNameMap
/* 140 */     .get(predefinedClipboardNames[16]);
/*     */   
/* 142 */   private static final DirectColorModel directColorModel = new DirectColorModel(24, 16711680, 65280, 255);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   private static final int[] bandmasks = new int[] { directColorModel
/* 149 */       .getRedMask(), directColorModel
/* 150 */       .getGreenMask(), directColorModel
/* 151 */       .getBlueMask() };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static WDataTransferer transferer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized WDataTransferer getInstanceImpl() {
/* 162 */     if (transferer == null) {
/* 163 */       transferer = new WDataTransferer();
/*     */     }
/* 165 */     return transferer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SortedMap<Long, DataFlavor> getFormatsForFlavors(DataFlavor[] paramArrayOfDataFlavor, FlavorTable paramFlavorTable) {
/* 173 */     SortedMap<Long, DataFlavor> sortedMap = super.getFormatsForFlavors(paramArrayOfDataFlavor, paramFlavorTable);
/*     */ 
/*     */ 
/*     */     
/* 177 */     sortedMap.remove(L_CF_LOCALE);
/*     */     
/* 179 */     return sortedMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDefaultUnicodeEncoding() {
/* 184 */     return "utf-16le";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] translateTransferable(Transferable paramTransferable, DataFlavor paramDataFlavor, long paramLong) throws IOException {
/* 192 */     byte[] arrayOfByte = null;
/* 193 */     if (paramLong == CF_HTML) {
/* 194 */       if (paramTransferable.isDataFlavorSupported(DataFlavor.selectionHtmlFlavor))
/*     */       {
/*     */ 
/*     */         
/* 198 */         arrayOfByte = super.translateTransferable(paramTransferable, DataFlavor.selectionHtmlFlavor, paramLong);
/*     */       
/*     */       }
/* 201 */       else if (paramTransferable.isDataFlavorSupported(DataFlavor.allHtmlFlavor))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 208 */         arrayOfByte = super.translateTransferable(paramTransferable, DataFlavor.allHtmlFlavor, paramLong);
/*     */       
/*     */       }
/*     */       else
/*     */       {
/*     */         
/* 214 */         arrayOfByte = HTMLCodec.convertToHTMLFormat(super.translateTransferable(paramTransferable, paramDataFlavor, paramLong));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 219 */       arrayOfByte = super.translateTransferable(paramTransferable, paramDataFlavor, paramLong);
/*     */     } 
/* 221 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object translateStream(InputStream paramInputStream, DataFlavor paramDataFlavor, long paramLong, Transferable paramTransferable) throws IOException {
/* 231 */     if (paramLong == CF_HTML && paramDataFlavor.isFlavorTextType())
/*     */     {
/* 233 */       paramInputStream = new HTMLCodec(paramInputStream, EHTMLReadMode.getEHTMLReadMode(paramDataFlavor));
/*     */     }
/*     */     
/* 236 */     return super.translateStream(paramInputStream, paramDataFlavor, paramLong, paramTransferable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object translateBytes(byte[] paramArrayOfbyte, DataFlavor paramDataFlavor, long paramLong, Transferable paramTransferable) throws IOException {
/* 247 */     if (paramLong == CF_FILEGROUPDESCRIPTORA || paramLong == CF_FILEGROUPDESCRIPTORW) {
/* 248 */       if (paramArrayOfbyte == null || !DataFlavor.javaFileListFlavor.equals(paramDataFlavor)) {
/* 249 */         throw new IOException("data translation failed");
/*     */       }
/* 251 */       String str = new String(paramArrayOfbyte, 0, paramArrayOfbyte.length, "UTF-16LE");
/* 252 */       String[] arrayOfString = str.split("\000");
/* 253 */       if (0 == arrayOfString.length) {
/* 254 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 258 */       File[] arrayOfFile = new File[arrayOfString.length];
/* 259 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 260 */         arrayOfFile[b] = new File(arrayOfString[b]);
/*     */         
/* 262 */         arrayOfFile[b].deleteOnExit();
/*     */       } 
/*     */       
/* 265 */       return Arrays.asList(arrayOfFile);
/*     */     } 
/*     */     
/* 268 */     if (paramLong == CFSTR_INETURL && URL.class
/* 269 */       .equals(paramDataFlavor.getRepresentationClass())) {
/*     */       
/* 271 */       String str = getDefaultTextCharset();
/* 272 */       if (paramTransferable != null && paramTransferable
/* 273 */         .isDataFlavorSupported(javaTextEncodingFlavor)) {
/*     */         
/*     */         try {
/*     */           
/* 277 */           str = new String((byte[])paramTransferable.getTransferData(javaTextEncodingFlavor), "UTF-8");
/* 278 */         } catch (UnsupportedFlavorException unsupportedFlavorException) {}
/*     */       }
/*     */       
/* 281 */       return new URL(new String(paramArrayOfbyte, str));
/*     */     } 
/*     */     
/* 284 */     return super.translateBytes(paramArrayOfbyte, paramDataFlavor, paramLong, paramTransferable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocaleDependentTextFormat(long paramLong) {
/* 291 */     return (paramLong == 1L || paramLong == CFSTR_INETURL);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFileFormat(long paramLong) {
/* 296 */     return (paramLong == 15L || paramLong == CF_FILEGROUPDESCRIPTORA || paramLong == CF_FILEGROUPDESCRIPTORW);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Long getFormatForNativeAsLong(String paramString) {
/* 301 */     Long long_ = predefinedClipboardNameMap.get(paramString);
/* 302 */     if (long_ == null) {
/* 303 */       long_ = Long.valueOf(registerClipboardFormat(paramString));
/*     */     }
/* 305 */     return long_;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getNativeForFormat(long paramLong) {
/* 310 */     return (paramLong < predefinedClipboardNames.length) ? predefinedClipboardNames[(int)paramLong] : 
/*     */       
/* 312 */       getClipboardFormatName(paramLong);
/*     */   }
/*     */   
/* 315 */   private final ToolkitThreadBlockedHandler handler = new WToolkitThreadBlockedHandler();
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolkitThreadBlockedHandler getToolkitThreadBlockedHandler() {
/* 320 */     return this.handler;
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
/*     */   
/*     */   public boolean isImageFormat(long paramLong) {
/* 337 */     return (paramLong == 8L || paramLong == 14L || paramLong == 3L || paramLong == CF_PNG || paramLong == CF_JFIF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] imageToPlatformBytes(Image paramImage, long paramLong) throws IOException {
/* 345 */     String str = null;
/* 346 */     if (paramLong == CF_PNG) {
/* 347 */       str = "image/png";
/* 348 */     } else if (paramLong == CF_JFIF) {
/* 349 */       str = "image/jpeg";
/*     */     } 
/* 351 */     if (str != null) {
/* 352 */       return imageToStandardBytes(paramImage, str);
/*     */     }
/*     */     
/* 355 */     int i = 0;
/* 356 */     int j = 0;
/*     */     
/* 358 */     if (paramImage instanceof ToolkitImage) {
/* 359 */       ImageRepresentation imageRepresentation = ((ToolkitImage)paramImage).getImageRep();
/* 360 */       imageRepresentation.reconstruct(32);
/* 361 */       i = imageRepresentation.getWidth();
/* 362 */       j = imageRepresentation.getHeight();
/*     */     } else {
/* 364 */       i = paramImage.getWidth(null);
/* 365 */       j = paramImage.getHeight(null);
/*     */     } 
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
/* 379 */     int k = i * 3 % 4;
/* 380 */     byte b = (k > 0) ? (4 - k) : 0;
/*     */     
/* 382 */     ColorSpace colorSpace = ColorSpace.getInstance(1000);
/* 383 */     int[] arrayOfInt1 = { 8, 8, 8 };
/* 384 */     int[] arrayOfInt2 = { 2, 1, 0 };
/* 385 */     ComponentColorModel componentColorModel = new ComponentColorModel(colorSpace, arrayOfInt1, false, false, 1, 0);
/*     */ 
/*     */ 
/*     */     
/* 389 */     WritableRaster writableRaster = Raster.createInterleavedRaster(0, i, j, i * 3 + b, 3, arrayOfInt2, (Point)null);
/*     */ 
/*     */     
/* 392 */     BufferedImage bufferedImage = new BufferedImage(componentColorModel, writableRaster, false, null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 397 */     AffineTransform affineTransform = new AffineTransform(1.0F, 0.0F, 0.0F, -1.0F, 0.0F, j);
/*     */ 
/*     */     
/* 400 */     Graphics2D graphics2D = bufferedImage.createGraphics();
/*     */     
/*     */     try {
/* 403 */       graphics2D.drawImage(paramImage, affineTransform, (ImageObserver)null);
/*     */     } finally {
/* 405 */       graphics2D.dispose();
/*     */     } 
/*     */     
/* 408 */     DataBufferByte dataBufferByte = (DataBufferByte)writableRaster.getDataBuffer();
/*     */     
/* 410 */     byte[] arrayOfByte = dataBufferByte.getData();
/* 411 */     return imageDataToPlatformImageBytes(arrayOfByte, i, j, paramLong);
/*     */   }
/*     */   
/* 414 */   private static final byte[] UNICODE_NULL_TERMINATOR = new byte[] { 0, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteArrayOutputStream convertFileListToBytes(ArrayList<String> paramArrayList) throws IOException {
/* 420 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*     */     
/* 422 */     if (paramArrayList.isEmpty()) {
/*     */       
/* 424 */       byteArrayOutputStream.write(UNICODE_NULL_TERMINATOR);
/*     */     } else {
/* 426 */       for (byte b = 0; b < paramArrayList.size(); b++) {
/* 427 */         byte[] arrayOfByte = ((String)paramArrayList.get(b)).getBytes(getDefaultUnicodeEncoding());
/*     */         
/* 429 */         byteArrayOutputStream.write(arrayOfByte, 0, arrayOfByte.length);
/* 430 */         byteArrayOutputStream.write(UNICODE_NULL_TERMINATOR);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 438 */     byteArrayOutputStream.write(UNICODE_NULL_TERMINATOR);
/* 439 */     return byteArrayOutputStream;
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
/*     */ 
/*     */   
/*     */   protected Image platformImageBytesToImage(byte[] paramArrayOfbyte, long paramLong) throws IOException {
/* 457 */     String str = null;
/* 458 */     if (paramLong == CF_PNG) {
/* 459 */       str = "image/png";
/* 460 */     } else if (paramLong == CF_JFIF) {
/* 461 */       str = "image/jpeg";
/*     */     } 
/* 463 */     if (str != null) {
/* 464 */       return standardImageBytesToImage(paramArrayOfbyte, str);
/*     */     }
/*     */     
/* 467 */     int[] arrayOfInt = platformImageBytesToImageData(paramArrayOfbyte, paramLong);
/* 468 */     if (arrayOfInt == null) {
/* 469 */       throw new IOException("data translation failed");
/*     */     }
/*     */     
/* 472 */     int i = arrayOfInt.length - 2;
/* 473 */     int j = arrayOfInt[i];
/* 474 */     int k = arrayOfInt[i + 1];
/*     */     
/* 476 */     DataBufferInt dataBufferInt = new DataBufferInt(arrayOfInt, i);
/* 477 */     WritableRaster writableRaster = Raster.createPackedRaster(dataBufferInt, j, k, j, bandmasks, (Point)null);
/*     */ 
/*     */ 
/*     */     
/* 481 */     return new BufferedImage(directColorModel, writableRaster, false, null);
/*     */   }
/*     */   
/*     */   private static native long registerClipboardFormat(String paramString);
/*     */   
/*     */   private static native String getClipboardFormatName(long paramLong);
/*     */   
/*     */   private native byte[] imageDataToPlatformImageBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, long paramLong);
/*     */   
/*     */   private native int[] platformImageBytesToImageData(byte[] paramArrayOfbyte, long paramLong) throws IOException;
/*     */   
/*     */   protected native String[] dragQueryFile(byte[] paramArrayOfbyte);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WDataTransferer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */