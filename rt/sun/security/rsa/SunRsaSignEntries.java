/*     */ package sun.security.rsa;
/*     */ 
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SunRsaSignEntries
/*     */ {
/*     */   public static void putEntries(Map<Object, Object> paramMap) {
/*  45 */     paramMap.put("KeyFactory.RSA", "sun.security.rsa.RSAKeyFactory");
/*     */     
/*  47 */     paramMap.put("KeyPairGenerator.RSA", "sun.security.rsa.RSAKeyPairGenerator");
/*     */     
/*  49 */     paramMap.put("Signature.MD2withRSA", "sun.security.rsa.RSASignature$MD2withRSA");
/*     */     
/*  51 */     paramMap.put("Signature.MD5withRSA", "sun.security.rsa.RSASignature$MD5withRSA");
/*     */     
/*  53 */     paramMap.put("Signature.SHA1withRSA", "sun.security.rsa.RSASignature$SHA1withRSA");
/*     */     
/*  55 */     paramMap.put("Signature.SHA224withRSA", "sun.security.rsa.RSASignature$SHA224withRSA");
/*     */     
/*  57 */     paramMap.put("Signature.SHA256withRSA", "sun.security.rsa.RSASignature$SHA256withRSA");
/*     */     
/*  59 */     paramMap.put("Signature.SHA384withRSA", "sun.security.rsa.RSASignature$SHA384withRSA");
/*     */     
/*  61 */     paramMap.put("Signature.SHA512withRSA", "sun.security.rsa.RSASignature$SHA512withRSA");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     String str = "java.security.interfaces.RSAPublicKey|java.security.interfaces.RSAPrivateKey";
/*     */     
/*  68 */     paramMap.put("Signature.MD2withRSA SupportedKeyClasses", str);
/*  69 */     paramMap.put("Signature.MD5withRSA SupportedKeyClasses", str);
/*  70 */     paramMap.put("Signature.SHA1withRSA SupportedKeyClasses", str);
/*  71 */     paramMap.put("Signature.SHA224withRSA SupportedKeyClasses", str);
/*  72 */     paramMap.put("Signature.SHA256withRSA SupportedKeyClasses", str);
/*  73 */     paramMap.put("Signature.SHA384withRSA SupportedKeyClasses", str);
/*  74 */     paramMap.put("Signature.SHA512withRSA SupportedKeyClasses", str);
/*     */ 
/*     */ 
/*     */     
/*  78 */     paramMap.put("Alg.Alias.KeyFactory.1.2.840.113549.1.1", "RSA");
/*  79 */     paramMap.put("Alg.Alias.KeyFactory.OID.1.2.840.113549.1.1", "RSA");
/*     */     
/*  81 */     paramMap.put("Alg.Alias.KeyPairGenerator.1.2.840.113549.1.1", "RSA");
/*  82 */     paramMap.put("Alg.Alias.KeyPairGenerator.OID.1.2.840.113549.1.1", "RSA");
/*     */     
/*  84 */     paramMap.put("Alg.Alias.Signature.1.2.840.113549.1.1.2", "MD2withRSA");
/*  85 */     paramMap.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.2", "MD2withRSA");
/*     */     
/*  87 */     paramMap.put("Alg.Alias.Signature.1.2.840.113549.1.1.4", "MD5withRSA");
/*  88 */     paramMap.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.4", "MD5withRSA");
/*     */     
/*  90 */     paramMap.put("Alg.Alias.Signature.1.2.840.113549.1.1.5", "SHA1withRSA");
/*  91 */     paramMap.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.5", "SHA1withRSA");
/*  92 */     paramMap.put("Alg.Alias.Signature.1.3.14.3.2.29", "SHA1withRSA");
/*     */     
/*  94 */     paramMap.put("Alg.Alias.Signature.1.2.840.113549.1.1.14", "SHA224withRSA");
/*  95 */     paramMap.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.14", "SHA224withRSA");
/*     */     
/*  97 */     paramMap.put("Alg.Alias.Signature.1.2.840.113549.1.1.11", "SHA256withRSA");
/*  98 */     paramMap.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.11", "SHA256withRSA");
/*     */     
/* 100 */     paramMap.put("Alg.Alias.Signature.1.2.840.113549.1.1.12", "SHA384withRSA");
/* 101 */     paramMap.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.12", "SHA384withRSA");
/*     */     
/* 103 */     paramMap.put("Alg.Alias.Signature.1.2.840.113549.1.1.13", "SHA512withRSA");
/* 104 */     paramMap.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.13", "SHA512withRSA");
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\rsa\SunRsaSignEntries.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */