/*    */ package sun.font;
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
/*    */ public class FontManagerNativeLibrary
/*    */ {
/*    */   static {
/* 32 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */         {
/*    */           public Object run()
/*    */           {
/* 36 */             System.loadLibrary("awt");
/* 37 */             if (FontUtilities.isOpenJDK && 
/* 38 */               System.getProperty("os.name").startsWith("Windows"))
/*    */             {
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
/* 59 */               System.loadLibrary("freetype");
/*    */             }
/* 61 */             System.loadLibrary("fontmanager");
/*    */             
/* 63 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public static void load() {}
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\font\FontManagerNativeLibrary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */