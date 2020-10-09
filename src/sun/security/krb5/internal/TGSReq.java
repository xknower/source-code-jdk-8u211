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
/*    */ public class TGSReq
/*    */   extends KDCReq
/*    */ {
/*    */   public TGSReq(PAData[] paramArrayOfPAData, KDCReqBody paramKDCReqBody) throws IOException {
/* 40 */     super(paramArrayOfPAData, paramKDCReqBody, 12);
/*    */   }
/*    */ 
/*    */   
/*    */   public TGSReq(byte[] paramArrayOfbyte) throws Asn1Exception, IOException, KrbException {
/* 45 */     init(new DerValue(paramArrayOfbyte));
/*    */   }
/*    */ 
/*    */   
/*    */   public TGSReq(DerValue paramDerValue) throws Asn1Exception, IOException, KrbException {
/* 50 */     init(paramDerValue);
/*    */   }
/*    */ 
/*    */   
/*    */   private void init(DerValue paramDerValue) throws Asn1Exception, IOException, KrbException {
/* 55 */     init(paramDerValue, 12);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\TGSReq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */