/*    */ package sun.nio.ch;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.IOException;
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
/*    */ 
/*    */ public class FileKey
/*    */ {
/*    */   private long dwVolumeSerialNumber;
/*    */   private long nFileIndexHigh;
/*    */   private long nFileIndexLow;
/*    */   
/*    */   public static FileKey create(FileDescriptor paramFileDescriptor) {
/* 43 */     FileKey fileKey = new FileKey();
/*    */     try {
/* 45 */       fileKey.init(paramFileDescriptor);
/* 46 */     } catch (IOException iOException) {
/* 47 */       throw new Error(iOException);
/*    */     } 
/* 49 */     return fileKey;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 53 */     return (int)(this.dwVolumeSerialNumber ^ this.dwVolumeSerialNumber >>> 32L) + (int)(this.nFileIndexHigh ^ this.nFileIndexHigh >>> 32L) + (int)(this.nFileIndexLow ^ this.nFileIndexHigh >>> 32L);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 59 */     if (paramObject == this)
/* 60 */       return true; 
/* 61 */     if (!(paramObject instanceof FileKey))
/* 62 */       return false; 
/* 63 */     FileKey fileKey = (FileKey)paramObject;
/* 64 */     if (this.dwVolumeSerialNumber != fileKey.dwVolumeSerialNumber || this.nFileIndexHigh != fileKey.nFileIndexHigh || this.nFileIndexLow != fileKey.nFileIndexLow)
/*    */     {
/*    */       
/* 67 */       return false;
/*    */     }
/* 69 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   private native void init(FileDescriptor paramFileDescriptor) throws IOException;
/*    */   
/*    */   static {
/* 76 */     IOUtil.load();
/* 77 */     initIDs();
/*    */   }
/*    */   
/*    */   private static native void initIDs();
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\FileKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */