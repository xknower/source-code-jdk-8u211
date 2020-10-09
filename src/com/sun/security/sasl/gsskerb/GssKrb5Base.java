/*     */ package com.sun.security.sasl.gsskerb;
/*     */ 
/*     */ import com.sun.security.sasl.util.AbstractSaslImpl;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import javax.security.sasl.SaslException;
/*     */ import org.ietf.jgss.GSSContext;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.MessageProp;
/*     */ import org.ietf.jgss.Oid;
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
/*     */ abstract class GssKrb5Base
/*     */   extends AbstractSaslImpl
/*     */ {
/*     */   private static final String KRB5_OID_STR = "1.2.840.113554.1.2.2";
/*     */   protected static Oid KRB5_OID;
/*  39 */   protected static final byte[] EMPTY = new byte[0];
/*     */   
/*     */   static {
/*     */     try {
/*  43 */       KRB5_OID = new Oid("1.2.840.113554.1.2.2");
/*  44 */     } catch (GSSException gSSException) {}
/*     */   }
/*     */   
/*  47 */   protected GSSContext secCtx = null;
/*     */   
/*     */   protected static final int JGSS_QOP = 0;
/*     */   
/*     */   protected GssKrb5Base(Map<String, ?> paramMap, String paramString) throws SaslException {
/*  52 */     super(paramMap, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMechanismName() {
/*  61 */     return "GSSAPI";
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/*  66 */     if (!this.completed) {
/*  67 */       throw new IllegalStateException("GSSAPI authentication not completed");
/*     */     }
/*     */ 
/*     */     
/*  71 */     if (!this.integrity) {
/*  72 */       throw new IllegalStateException("No security layer negotiated");
/*     */     }
/*     */     
/*     */     try {
/*  76 */       MessageProp messageProp = new MessageProp(0, this.privacy);
/*  77 */       byte[] arrayOfByte = this.secCtx.unwrap(paramArrayOfbyte, paramInt1, paramInt2, messageProp);
/*  78 */       if (logger.isLoggable(Level.FINEST)) {
/*  79 */         traceOutput(this.myClassName, "KRB501:Unwrap", "incoming: ", paramArrayOfbyte, paramInt1, paramInt2);
/*     */         
/*  81 */         traceOutput(this.myClassName, "KRB502:Unwrap", "unwrapped: ", arrayOfByte, 0, arrayOfByte.length);
/*     */       } 
/*     */       
/*  84 */       return arrayOfByte;
/*  85 */     } catch (GSSException gSSException) {
/*  86 */       throw new SaslException("Problems unwrapping SASL buffer", gSSException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/*  91 */     if (!this.completed) {
/*  92 */       throw new IllegalStateException("GSSAPI authentication not completed");
/*     */     }
/*     */ 
/*     */     
/*  96 */     if (!this.integrity) {
/*  97 */       throw new IllegalStateException("No security layer negotiated");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 102 */       MessageProp messageProp = new MessageProp(0, this.privacy);
/* 103 */       byte[] arrayOfByte = this.secCtx.wrap(paramArrayOfbyte, paramInt1, paramInt2, messageProp);
/* 104 */       if (logger.isLoggable(Level.FINEST)) {
/* 105 */         traceOutput(this.myClassName, "KRB503:Wrap", "outgoing: ", paramArrayOfbyte, paramInt1, paramInt2);
/*     */         
/* 107 */         traceOutput(this.myClassName, "KRB504:Wrap", "wrapped: ", arrayOfByte, 0, arrayOfByte.length);
/*     */       } 
/*     */       
/* 110 */       return arrayOfByte;
/*     */     }
/* 112 */     catch (GSSException gSSException) {
/* 113 */       throw new SaslException("Problem performing GSS wrap", gSSException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dispose() throws SaslException {
/* 118 */     if (this.secCtx != null) {
/*     */       try {
/* 120 */         this.secCtx.dispose();
/* 121 */       } catch (GSSException gSSException) {
/* 122 */         throw new SaslException("Problem disposing GSS context", gSSException);
/*     */       } 
/* 124 */       this.secCtx = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 129 */     dispose();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\security\sasl\gsskerb\GssKrb5Base.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */