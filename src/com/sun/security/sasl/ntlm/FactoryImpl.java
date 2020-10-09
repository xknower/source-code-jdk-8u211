/*     */ package com.sun.security.sasl.ntlm;
/*     */ 
/*     */ import com.sun.security.sasl.util.PolicyUtils;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.sasl.SaslClient;
/*     */ import javax.security.sasl.SaslClientFactory;
/*     */ import javax.security.sasl.SaslException;
/*     */ import javax.security.sasl.SaslServer;
/*     */ import javax.security.sasl.SaslServerFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FactoryImpl
/*     */   implements SaslClientFactory, SaslServerFactory
/*     */ {
/*  46 */   private static final String[] myMechs = new String[] { "NTLM" };
/*  47 */   private static final int[] mechPolicies = new int[] { 17 };
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
/*     */   public SaslClient createSaslClient(String[] paramArrayOfString, String paramString1, String paramString2, String paramString3, Map<String, ?> paramMap, CallbackHandler paramCallbackHandler) throws SaslException {
/*  69 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  70 */       if (paramArrayOfString[b].equals("NTLM") && 
/*  71 */         PolicyUtils.checkPolicy(mechPolicies[0], paramMap)) {
/*     */         
/*  73 */         if (paramCallbackHandler == null) {
/*  74 */           throw new SaslException("Callback handler with support for RealmCallback, NameCallback, and PasswordCallback required");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*  79 */         return new NTLMClient(paramArrayOfString[b], paramString1, paramString2, paramString3, paramMap, paramCallbackHandler);
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     return null;
/*     */   }
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
/*     */   public SaslServer createSaslServer(String paramString1, String paramString2, String paramString3, Map<String, ?> paramMap, CallbackHandler paramCallbackHandler) throws SaslException {
/*  97 */     if (paramString1.equals("NTLM") && 
/*  98 */       PolicyUtils.checkPolicy(mechPolicies[0], paramMap)) {
/*  99 */       if (paramMap != null) {
/* 100 */         String str = (String)paramMap.get("javax.security.sasl.qop");
/* 101 */         if (str != null && !str.equals("auth")) {
/* 102 */           throw new SaslException("NTLM only support auth");
/*     */         }
/*     */       } 
/* 105 */       if (paramCallbackHandler == null) {
/* 106 */         throw new SaslException("Callback handler with support for RealmCallback, NameCallback, and PasswordCallback required");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 111 */       return new NTLMServer(paramString1, paramString2, paramString3, paramMap, paramCallbackHandler);
/*     */     } 
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getMechanismNames(Map<String, ?> paramMap) {
/* 123 */     return PolicyUtils.filterMechs(myMechs, mechPolicies, paramMap);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\security\sasl\ntlm\FactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */