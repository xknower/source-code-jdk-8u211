/*    */ package sun.security.smartcardio;
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
/*    */ class PlatformPCSC
/*    */ {
/* 41 */   static final Throwable initException = loadLibrary();
/*    */   static final int SCARD_PROTOCOL_T0 = 1;
/*    */   
/*    */   private static Throwable loadLibrary() {
/*    */     try {
/* 46 */       AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*    */             public Void run() {
/* 48 */               System.loadLibrary("j2pcsc");
/* 49 */               return null;
/*    */             }
/*    */           });
/* 52 */       return null;
/* 53 */     } catch (Throwable throwable) {
/* 54 */       return throwable;
/*    */     } 
/*    */   }
/*    */   
/*    */   static final int SCARD_PROTOCOL_T1 = 2;
/*    */   static final int SCARD_PROTOCOL_RAW = 65536;
/*    */   static final int SCARD_UNKNOWN = 0;
/*    */   static final int SCARD_ABSENT = 1;
/*    */   static final int SCARD_PRESENT = 2;
/*    */   static final int SCARD_SWALLOWED = 3;
/*    */   static final int SCARD_POWERED = 4;
/*    */   static final int SCARD_NEGOTIABLE = 5;
/*    */   static final int SCARD_SPECIFIC = 6;
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\smartcardio\PlatformPCSC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */