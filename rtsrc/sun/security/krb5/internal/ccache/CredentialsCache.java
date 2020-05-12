/*     */ package sun.security.krb5.internal.ccache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.LoginOptions;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CredentialsCache
/*     */ {
/*  51 */   static CredentialsCache singleton = null;
/*     */   static String cacheName;
/*  53 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */   
/*     */   public static CredentialsCache getInstance(PrincipalName paramPrincipalName) {
/*  56 */     return FileCredentialsCache.acquireInstance(paramPrincipalName, null);
/*     */   }
/*     */   
/*     */   public static CredentialsCache getInstance(String paramString) {
/*  60 */     if (paramString.length() >= 5 && paramString.substring(0, 5).equalsIgnoreCase("FILE:")) {
/*  61 */       return FileCredentialsCache.acquireInstance(null, paramString.substring(5));
/*     */     }
/*     */ 
/*     */     
/*  65 */     return FileCredentialsCache.acquireInstance(null, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CredentialsCache getInstance(PrincipalName paramPrincipalName, String paramString) {
/*  72 */     if (paramString != null && paramString
/*  73 */       .length() >= 5 && paramString
/*  74 */       .regionMatches(true, 0, "FILE:", 0, 5)) {
/*  75 */       return FileCredentialsCache.acquireInstance(paramPrincipalName, paramString
/*  76 */           .substring(5));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     return FileCredentialsCache.acquireInstance(paramPrincipalName, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CredentialsCache getInstance() {
/*  92 */     return FileCredentialsCache.acquireInstance();
/*     */   }
/*     */   
/*     */   public static CredentialsCache create(PrincipalName paramPrincipalName, String paramString) {
/*  96 */     if (paramString == null) {
/*  97 */       throw new RuntimeException("cache name error");
/*     */     }
/*  99 */     if (paramString.length() >= 5 && paramString
/* 100 */       .regionMatches(true, 0, "FILE:", 0, 5)) {
/* 101 */       paramString = paramString.substring(5);
/* 102 */       return FileCredentialsCache.New(paramPrincipalName, paramString);
/*     */     } 
/*     */ 
/*     */     
/* 106 */     return FileCredentialsCache.New(paramPrincipalName, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public static CredentialsCache create(PrincipalName paramPrincipalName) {
/* 111 */     return FileCredentialsCache.New(paramPrincipalName);
/*     */   }
/*     */   
/*     */   public static String cacheName() {
/* 115 */     return cacheName;
/*     */   }
/*     */   
/*     */   public abstract PrincipalName getPrimaryPrincipal();
/*     */   
/*     */   public abstract void update(Credentials paramCredentials);
/*     */   
/*     */   public abstract void save() throws IOException, KrbException;
/*     */   
/*     */   public abstract Credentials[] getCredsList();
/*     */   
/*     */   public abstract Credentials getDefaultCreds();
/*     */   
/*     */   public abstract Credentials getCreds(PrincipalName paramPrincipalName);
/*     */   
/*     */   public abstract Credentials getCreds(LoginOptions paramLoginOptions, PrincipalName paramPrincipalName);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\ccache\CredentialsCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */