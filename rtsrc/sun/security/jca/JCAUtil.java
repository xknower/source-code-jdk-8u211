/*    */ package sun.security.jca;
/*    */ 
/*    */ import java.security.SecureRandom;
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
/*    */ public final class JCAUtil
/*    */ {
/*    */   private static final int ARRAY_SIZE = 4096;
/*    */   
/*    */   public static int getTempArraySize(int paramInt) {
/* 54 */     return Math.min(4096, paramInt);
/*    */   }
/*    */   
/*    */   private static class CachedSecureRandomHolder
/*    */   {
/* 59 */     public static SecureRandom instance = new SecureRandom();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SecureRandom getSecureRandom() {
/* 69 */     return CachedSecureRandomHolder.instance;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\jca\JCAUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */