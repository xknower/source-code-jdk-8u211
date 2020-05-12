/*    */ package sun.security.krb5.internal;
/*    */ 
/*    */ import sun.security.krb5.KrbException;
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
/*    */ public class KrbErrException
/*    */   extends KrbException
/*    */ {
/*    */   private static final long serialVersionUID = 2186533836785448317L;
/*    */   
/*    */   public KrbErrException(int paramInt) {
/* 38 */     super(paramInt);
/*    */   }
/*    */   
/*    */   public KrbErrException(int paramInt, String paramString) {
/* 42 */     super(paramInt, paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\KrbErrException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */