/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.internal.EncTGSRepPart;
/*     */ import sun.security.krb5.internal.KRBError;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.TGSRep;
/*     */ import sun.security.krb5.internal.TGSReq;
/*     */ import sun.security.krb5.internal.Ticket;
/*     */ import sun.security.krb5.internal.ccache.Credentials;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KrbTgsRep
/*     */   extends KrbKdcRep
/*     */ {
/*     */   private TGSRep rep;
/*     */   private Credentials creds;
/*     */   private Ticket secondTicket;
/*  47 */   private static final boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */   
/*     */   KrbTgsRep(byte[] paramArrayOfbyte, KrbTgsReq paramKrbTgsReq) throws KrbException, IOException {
/*  51 */     DerValue derValue = new DerValue(paramArrayOfbyte);
/*  52 */     TGSReq tGSReq = paramKrbTgsReq.getMessage();
/*  53 */     TGSRep tGSRep = null;
/*     */     try {
/*  55 */       tGSRep = new TGSRep(derValue);
/*  56 */     } catch (Asn1Exception asn1Exception) {
/*  57 */       KrbException krbException; tGSRep = null;
/*  58 */       KRBError kRBError = new KRBError(derValue);
/*  59 */       String str1 = kRBError.getErrorString();
/*  60 */       String str2 = null;
/*  61 */       if (str1 != null && str1.length() > 0) {
/*  62 */         if (str1.charAt(str1.length() - 1) == '\000') {
/*  63 */           str2 = str1.substring(0, str1.length() - 1);
/*     */         } else {
/*  65 */           str2 = str1;
/*     */         } 
/*     */       }
/*  68 */       if (str2 == null) {
/*     */         
/*  70 */         krbException = new KrbException(kRBError.getErrorCode());
/*     */       } else {
/*     */         
/*  73 */         krbException = new KrbException(kRBError.getErrorCode(), str2);
/*     */       } 
/*  75 */       krbException.initCause(asn1Exception);
/*  76 */       throw krbException;
/*     */     } 
/*  78 */     byte[] arrayOfByte1 = tGSRep.encPart.decrypt(paramKrbTgsReq.tgsReqKey, 
/*  79 */         paramKrbTgsReq.usedSubkey() ? 9 : 8);
/*     */ 
/*     */     
/*  82 */     byte[] arrayOfByte2 = tGSRep.encPart.reset(arrayOfByte1);
/*  83 */     derValue = new DerValue(arrayOfByte2);
/*  84 */     EncTGSRepPart encTGSRepPart = new EncTGSRepPart(derValue);
/*  85 */     tGSRep.encKDCRepPart = encTGSRepPart;
/*     */     
/*  87 */     check(false, tGSReq, tGSRep);
/*     */     
/*  89 */     this.creds = new Credentials(tGSRep.ticket, tGSRep.cname, encTGSRepPart.sname, encTGSRepPart.key, encTGSRepPart.flags, encTGSRepPart.authtime, encTGSRepPart.starttime, encTGSRepPart.endtime, encTGSRepPart.renewTill, encTGSRepPart.caddr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     this.rep = tGSRep;
/* 101 */     this.secondTicket = paramKrbTgsReq.getSecondTicket();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials getCreds() {
/* 108 */     return this.creds;
/*     */   }
/*     */   
/*     */   Credentials setCredentials() {
/* 112 */     return new Credentials(this.rep, this.secondTicket);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\KrbTgsRep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */