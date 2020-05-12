/*    */ package sun.security.krb5.internal;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import sun.security.krb5.Asn1Exception;
/*    */ import sun.security.krb5.KrbException;
/*    */ import sun.security.util.DerValue;
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
/*    */ public class ASReq
/*    */   extends KDCReq
/*    */ {
/*    */   public ASReq(PAData[] paramArrayOfPAData, KDCReqBody paramKDCReqBody) throws IOException {
/* 40 */     super(paramArrayOfPAData, paramKDCReqBody, 10);
/*    */   }
/*    */   
/*    */   public ASReq(byte[] paramArrayOfbyte) throws Asn1Exception, KrbException, IOException {
/* 44 */     init(new DerValue(paramArrayOfbyte));
/*    */   }
/*    */   
/*    */   public ASReq(DerValue paramDerValue) throws Asn1Exception, KrbException, IOException {
/* 48 */     init(paramDerValue);
/*    */   }
/*    */   
/*    */   private void init(DerValue paramDerValue) throws Asn1Exception, IOException, KrbException {
/* 52 */     init(paramDerValue, 10);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\ASReq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */