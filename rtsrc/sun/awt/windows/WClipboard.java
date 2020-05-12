/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.io.IOException;
/*     */ import java.util.SortedMap;
/*     */ import sun.awt.datatransfer.DataTransferer;
/*     */ import sun.awt.datatransfer.SunClipboard;
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
/*     */ final class WClipboard
/*     */   extends SunClipboard
/*     */ {
/*     */   private boolean isClipboardViewerRegistered;
/*     */   
/*     */   WClipboard() {
/*  55 */     super("System");
/*     */   }
/*     */ 
/*     */   
/*     */   public long getID() {
/*  60 */     return 0L;
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
/*     */   protected void setContentsNative(Transferable paramTransferable) {
/*  73 */     SortedMap<Long, DataFlavor> sortedMap = WDataTransferer.getInstance().getFormatsForTransferable(paramTransferable, getDefaultFlavorTable());
/*     */     
/*  75 */     openClipboard(this);
/*     */     
/*     */     try {
/*  78 */       for (Long long_ : sortedMap.keySet()) {
/*  79 */         DataFlavor dataFlavor = sortedMap.get(long_);
/*     */ 
/*     */         
/*     */         try {
/*  83 */           byte[] arrayOfByte = WDataTransferer.getInstance().translateTransferable(paramTransferable, dataFlavor, long_.longValue());
/*  84 */           publishClipboardData(long_.longValue(), arrayOfByte);
/*  85 */         } catch (IOException iOException) {
/*     */ 
/*     */ 
/*     */           
/*  89 */           if (!dataFlavor.isMimeTypeEqual("application/x-java-jvm-local-objectref") || !(iOException instanceof java.io.NotSerializableException))
/*     */           {
/*  91 */             iOException.printStackTrace();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/*  96 */       closeClipboard();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void lostSelectionOwnershipImpl() {
/* 101 */     lostOwnershipImpl();
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
/*     */   protected void clearNativeContext() {}
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
/*     */   static {
/* 132 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerClipboardViewerChecked() {
/* 142 */     if (!this.isClipboardViewerRegistered) {
/* 143 */       registerClipboardViewer();
/* 144 */       this.isClipboardViewerRegistered = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unregisterClipboardViewerChecked() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleContentsChanged() {
/* 162 */     if (!areFlavorListenersRegistered()) {
/*     */       return;
/*     */     }
/*     */     
/* 166 */     long[] arrayOfLong = null;
/*     */     try {
/* 168 */       openClipboard((SunClipboard)null);
/* 169 */       arrayOfLong = getClipboardFormats();
/* 170 */     } catch (IllegalStateException illegalStateException) {
/*     */     
/*     */     } finally {
/* 173 */       closeClipboard();
/*     */     } 
/* 175 */     checkChange(arrayOfLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Transferable createLocaleTransferable(long[] paramArrayOflong) throws IOException {
/* 185 */     boolean bool = false;
/* 186 */     for (byte b = 0; b < paramArrayOflong.length; b++) {
/* 187 */       if (paramArrayOflong[b] == 16L) {
/* 188 */         bool = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 192 */     if (!bool) {
/* 193 */       return null;
/*     */     }
/*     */     
/* 196 */     byte[] arrayOfByte1 = null;
/*     */     try {
/* 198 */       arrayOfByte1 = getClipboardData(16L);
/* 199 */     } catch (IOException iOException) {
/* 200 */       return null;
/*     */     } 
/*     */     
/* 203 */     final byte[] localeDataFinal = arrayOfByte1;
/*     */     
/* 205 */     return new Transferable()
/*     */       {
/*     */         public DataFlavor[] getTransferDataFlavors() {
/* 208 */           return new DataFlavor[] { DataTransferer.javaTextEncodingFlavor };
/*     */         }
/*     */         
/*     */         public boolean isDataFlavorSupported(DataFlavor param1DataFlavor) {
/* 212 */           return param1DataFlavor.equals(DataTransferer.javaTextEncodingFlavor);
/*     */         }
/*     */         
/*     */         public Object getTransferData(DataFlavor param1DataFlavor) throws UnsupportedFlavorException {
/* 216 */           if (isDataFlavorSupported(param1DataFlavor)) {
/* 217 */             return localeDataFinal;
/*     */           }
/* 219 */           throw new UnsupportedFlavorException(param1DataFlavor);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public native void openClipboard(SunClipboard paramSunClipboard) throws IllegalStateException;
/*     */   
/*     */   public native void closeClipboard();
/*     */   
/*     */   private native void publishClipboardData(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   private static native void init();
/*     */   
/*     */   protected native long[] getClipboardFormats();
/*     */   
/*     */   protected native byte[] getClipboardData(long paramLong) throws IOException;
/*     */   
/*     */   private native void registerClipboardViewer();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WClipboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */