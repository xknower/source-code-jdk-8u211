/*    */ package sun.security.krb5.internal;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import sun.security.krb5.Asn1Exception;
/*    */ import sun.security.krb5.EncryptionKey;
/*    */ import sun.security.krb5.KrbException;
/*    */ import sun.security.krb5.PrincipalName;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EncASRepPart
/*    */   extends EncKDCRepPart
/*    */ {
/*    */   public EncASRepPart(EncryptionKey paramEncryptionKey, LastReq paramLastReq, int paramInt, KerberosTime paramKerberosTime1, TicketFlags paramTicketFlags, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, KerberosTime paramKerberosTime4, KerberosTime paramKerberosTime5, PrincipalName paramPrincipalName, HostAddresses paramHostAddresses) {
/* 51 */     super(paramEncryptionKey, paramLastReq, paramInt, paramKerberosTime1, paramTicketFlags, paramKerberosTime2, paramKerberosTime3, paramKerberosTime4, paramKerberosTime5, paramPrincipalName, paramHostAddresses, 25);
/*    */   }
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
/*    */   public EncASRepPart(byte[] paramArrayOfbyte) throws Asn1Exception, IOException, KrbException {
/* 71 */     init(new DerValue(paramArrayOfbyte));
/*    */   }
/*    */ 
/*    */   
/*    */   public EncASRepPart(DerValue paramDerValue) throws Asn1Exception, IOException, KrbException {
/* 76 */     init(paramDerValue);
/*    */   }
/*    */ 
/*    */   
/*    */   private void init(DerValue paramDerValue) throws Asn1Exception, IOException, KrbException {
/* 81 */     init(paramDerValue, 25);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 86 */     return asn1Encode(25);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\EncASRepPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */