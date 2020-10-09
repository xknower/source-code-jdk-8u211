/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.KrbTgsReq;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.Realm;
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
/*     */ public class CredentialsUtil
/*     */ {
/*  44 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Credentials acquireS4U2selfCreds(PrincipalName paramPrincipalName, Credentials paramCredentials) throws KrbException, IOException {
/*  55 */     String str1 = paramPrincipalName.getRealmString();
/*  56 */     String str2 = paramCredentials.getClient().getRealmString();
/*  57 */     if (!str1.equals(str2))
/*     */     {
/*  59 */       throw new KrbException("Cross realm impersonation not supported");
/*     */     }
/*  61 */     if (!paramCredentials.isForwardable()) {
/*  62 */       throw new KrbException("S4U2self needs a FORWARDABLE ticket");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     KrbTgsReq krbTgsReq = new KrbTgsReq(paramCredentials, paramCredentials.getClient(), new PAData(129, (new PAForUserEnc(paramPrincipalName, paramCredentials.getSessionKey())).asn1Encode()));
/*  70 */     Credentials credentials = krbTgsReq.sendAndGetCreds();
/*  71 */     if (!credentials.getClient().equals(paramPrincipalName)) {
/*  72 */       throw new KrbException("S4U2self request not honored by KDC");
/*     */     }
/*  74 */     if (!credentials.isForwardable()) {
/*  75 */       throw new KrbException("S4U2self ticket must be FORWARDABLE");
/*     */     }
/*  77 */     return credentials;
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
/*     */   
/*     */   public static Credentials acquireS4U2proxyCreds(String paramString, Ticket paramTicket, PrincipalName paramPrincipalName, Credentials paramCredentials) throws KrbException, IOException {
/*  92 */     KrbTgsReq krbTgsReq = new KrbTgsReq(paramCredentials, paramTicket, new PrincipalName(paramString));
/*     */ 
/*     */ 
/*     */     
/*  96 */     Credentials credentials = krbTgsReq.sendAndGetCreds();
/*  97 */     if (!credentials.getClient().equals(paramPrincipalName)) {
/*  98 */       throw new KrbException("S4U2proxy request not honored by KDC");
/*     */     }
/* 100 */     return credentials;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Credentials acquireServiceCreds(String paramString, Credentials paramCredentials) throws KrbException, IOException {
/* 117 */     PrincipalName principalName = new PrincipalName(paramString);
/* 118 */     String str1 = principalName.getRealmString();
/* 119 */     String str2 = paramCredentials.getClient().getRealmString();
/*     */     
/* 121 */     if (str2.equals(str1)) {
/* 122 */       if (DEBUG) {
/* 123 */         System.out.println(">>> Credentials acquireServiceCreds: same realm");
/*     */       }
/*     */       
/* 126 */       return serviceCreds(principalName, paramCredentials);
/*     */     } 
/* 128 */     Credentials credentials1 = null;
/*     */     
/* 130 */     boolean[] arrayOfBoolean = new boolean[1];
/* 131 */     Credentials credentials2 = getTGTforRealm(str2, str1, paramCredentials, arrayOfBoolean);
/*     */     
/* 133 */     if (credentials2 != null) {
/* 134 */       if (DEBUG) {
/* 135 */         System.out.println(">>> Credentials acquireServiceCreds: got right tgt");
/*     */         
/* 137 */         System.out.println(">>> Credentials acquireServiceCreds: obtaining service creds for " + principalName);
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 142 */         credentials1 = serviceCreds(principalName, credentials2);
/* 143 */       } catch (Exception exception) {
/* 144 */         if (DEBUG) {
/* 145 */           System.out.println(exception);
/*     */         }
/* 147 */         credentials1 = null;
/*     */       } 
/*     */     } 
/*     */     
/* 151 */     if (credentials1 != null) {
/* 152 */       if (DEBUG) {
/* 153 */         System.out.println(">>> Credentials acquireServiceCreds: returning creds:");
/*     */         
/* 155 */         Credentials.printDebug(credentials1);
/*     */       } 
/* 157 */       if (!arrayOfBoolean[0]) {
/* 158 */         credentials1.resetDelegate();
/*     */       }
/* 160 */       return credentials1;
/*     */     } 
/* 162 */     throw new KrbApErrException(63, "No service creds");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Credentials getTGTforRealm(String paramString1, String paramString2, Credentials paramCredentials, boolean[] paramArrayOfboolean) throws KrbException {
/* 181 */     String[] arrayOfString = Realm.getRealmsList(paramString1, paramString2);
/*     */     
/* 183 */     int i = 0, j = 0;
/* 184 */     Credentials credentials1 = null, credentials2 = null, credentials3 = null;
/* 185 */     PrincipalName principalName = null;
/* 186 */     String str = null;
/*     */     
/* 188 */     paramArrayOfboolean[0] = true;
/* 189 */     for (credentials1 = paramCredentials, i = 0; i < arrayOfString.length; ) {
/* 190 */       principalName = PrincipalName.tgsService(paramString2, arrayOfString[i]);
/*     */       
/* 192 */       if (DEBUG) {
/* 193 */         System.out.println(">>> Credentials acquireServiceCreds: main loop: [" + i + "] tempService=" + principalName);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 199 */         credentials2 = serviceCreds(principalName, credentials1);
/* 200 */       } catch (Exception exception) {
/* 201 */         credentials2 = null;
/*     */       } 
/*     */       
/* 204 */       if (credentials2 == null) {
/* 205 */         if (DEBUG) {
/* 206 */           System.out.println(">>> Credentials acquireServiceCreds: no tgt; searching thru capath");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 213 */         credentials2 = null; j = i + 1;
/* 214 */         for (; credentials2 == null && j < arrayOfString.length; j++) {
/* 215 */           principalName = PrincipalName.tgsService(arrayOfString[j], arrayOfString[i]);
/* 216 */           if (DEBUG) {
/* 217 */             System.out.println(">>> Credentials acquireServiceCreds: inner loop: [" + j + "] tempService=" + principalName);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 223 */             credentials2 = serviceCreds(principalName, credentials1);
/* 224 */           } catch (Exception exception) {
/* 225 */             credentials2 = null;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 230 */       if (credentials2 == null) {
/* 231 */         if (DEBUG) {
/* 232 */           System.out.println(">>> Credentials acquireServiceCreds: no tgt; cannot get creds");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 242 */       str = credentials2.getServer().getInstanceComponent();
/* 243 */       if (paramArrayOfboolean[0] && !credentials2.checkDelegate()) {
/* 244 */         if (DEBUG) {
/* 245 */           System.out.println(">>> Credentials acquireServiceCreds: global OK-AS-DELEGATE turned off at " + credentials2
/*     */               
/* 247 */               .getServer());
/*     */         }
/* 249 */         paramArrayOfboolean[0] = false;
/*     */       } 
/*     */       
/* 252 */       if (DEBUG) {
/* 253 */         System.out.println(">>> Credentials acquireServiceCreds: got tgt");
/*     */       }
/*     */ 
/*     */       
/* 257 */       if (str.equals(paramString2)) {
/*     */         
/* 259 */         credentials3 = credentials2;
/*     */ 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 268 */       for (j = i + 1; j < arrayOfString.length && 
/* 269 */         !str.equals(arrayOfString[j]); j++);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 274 */       if (j < arrayOfString.length) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 279 */         i = j;
/* 280 */         credentials1 = credentials2;
/*     */         
/* 282 */         if (DEBUG) {
/* 283 */           System.out.println(">>> Credentials acquireServiceCreds: continuing with main loop counter reset to " + i);
/*     */         }
/*     */       } 
/*     */     } 
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
/* 299 */     return credentials3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Credentials serviceCreds(PrincipalName paramPrincipalName, Credentials paramCredentials) throws KrbException, IOException {
/* 308 */     return (new KrbTgsReq(paramCredentials, paramPrincipalName)).sendAndGetCreds();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\CredentialsUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */