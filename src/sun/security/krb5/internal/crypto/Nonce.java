/*    */ package sun.security.krb5.internal.crypto;
/*    */ 
/*    */ import sun.security.krb5.Confounder;
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
/*    */ public class Nonce
/*    */ {
/*    */   public static synchronized int value() {
/* 37 */     return Confounder.intValue() & Integer.MAX_VALUE;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\crypto\Nonce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */