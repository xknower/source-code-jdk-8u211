/*    */ package java.awt.event;
/*    */ 
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
/*    */ class NativeLibLoader
/*    */ {
/*    */   static void loadLibraries() {
/* 56 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*    */         {
/*    */           public Void run() {
/* 59 */             System.loadLibrary("awt");
/* 60 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\event\NativeLibLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */