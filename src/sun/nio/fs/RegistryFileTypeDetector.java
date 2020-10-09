/*    */ package sun.nio.fs;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Path;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistryFileTypeDetector
/*    */   extends AbstractFileTypeDetector
/*    */ {
/*    */   public String implProbeContentType(Path paramPath) throws IOException {
/* 46 */     if (!(paramPath instanceof Path)) {
/* 47 */       return null;
/*    */     }
/*    */     
/* 50 */     Path path = paramPath.getFileName();
/* 51 */     if (path == null)
/* 52 */       return null; 
/* 53 */     String str1 = path.toString();
/* 54 */     int i = str1.lastIndexOf('.');
/* 55 */     if (i < 0 || i == str1.length() - 1) {
/* 56 */       return null;
/*    */     }
/*    */     
/* 59 */     String str2 = str1.substring(i);
/* 60 */     NativeBuffer nativeBuffer1 = WindowsNativeDispatcher.asNativeBuffer(str2);
/* 61 */     NativeBuffer nativeBuffer2 = WindowsNativeDispatcher.asNativeBuffer("Content Type");
/*    */     try {
/* 63 */       return queryStringValue(nativeBuffer1.address(), nativeBuffer2.address());
/*    */     } finally {
/* 65 */       nativeBuffer2.release();
/* 66 */       nativeBuffer1.release();
/*    */     } 
/*    */   }
/*    */   
/*    */   private static native String queryStringValue(long paramLong1, long paramLong2);
/*    */   
/*    */   static {
/* 73 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*    */         {
/*    */           public Void run()
/*    */           {
/* 77 */             System.loadLibrary("net");
/* 78 */             System.loadLibrary("nio");
/* 79 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\RegistryFileTypeDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */