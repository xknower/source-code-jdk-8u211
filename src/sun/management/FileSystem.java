/*    */ package sun.management;
/*    */ 
/*    */ import java.io.File;
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
/*    */ public abstract class FileSystem
/*    */ {
/* 38 */   private static final Object lock = new Object();
/*    */ 
/*    */ 
/*    */   
/*    */   private static FileSystem fs;
/*    */ 
/*    */ 
/*    */   
/*    */   public static FileSystem open() {
/* 47 */     synchronized (lock) {
/* 48 */       if (fs == null) {
/* 49 */         fs = new FileSystemImpl();
/*    */       }
/* 51 */       return fs;
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract boolean supportsFileSecurity(File paramFile) throws IOException;
/*    */   
/*    */   public abstract boolean isAccessUserOnly(File paramFile) throws IOException;
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\FileSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */