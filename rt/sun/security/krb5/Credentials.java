/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.krb5.internal.AuthorizationData;
/*     */ import sun.security.krb5.internal.CredentialsUtil;
/*     */ import sun.security.krb5.internal.HostAddresses;
/*     */ import sun.security.krb5.internal.KDCOptions;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.Ticket;
/*     */ import sun.security.krb5.internal.TicketFlags;
/*     */ import sun.security.krb5.internal.ccache.Credentials;
/*     */ import sun.security.krb5.internal.ccache.CredentialsCache;
/*     */ import sun.security.krb5.internal.crypto.EType;
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
/*     */ public class Credentials
/*     */ {
/*     */   Ticket ticket;
/*     */   PrincipalName client;
/*     */   PrincipalName server;
/*     */   EncryptionKey key;
/*     */   TicketFlags flags;
/*     */   KerberosTime authTime;
/*     */   KerberosTime startTime;
/*     */   KerberosTime endTime;
/*     */   KerberosTime renewTill;
/*     */   HostAddresses cAddr;
/*     */   EncryptionKey serviceKey;
/*     */   AuthorizationData authzData;
/*  61 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */ 
/*     */   
/*     */   private static CredentialsCache cache;
/*     */ 
/*     */   
/*     */   static boolean alreadyLoaded = false;
/*     */ 
/*     */   
/*     */   private static boolean alreadyTried = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private static native Credentials acquireDefaultNativeCreds(int[] paramArrayOfint);
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials(Ticket paramTicket, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, EncryptionKey paramEncryptionKey, TicketFlags paramTicketFlags, KerberosTime paramKerberosTime1, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, KerberosTime paramKerberosTime4, HostAddresses paramHostAddresses, AuthorizationData paramAuthorizationData) {
/*  80 */     this(paramTicket, paramPrincipalName1, paramPrincipalName2, paramEncryptionKey, paramTicketFlags, paramKerberosTime1, paramKerberosTime2, paramKerberosTime3, paramKerberosTime4, paramHostAddresses);
/*     */     
/*  82 */     this.authzData = paramAuthorizationData;
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
/*     */   public Credentials(Ticket paramTicket, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, EncryptionKey paramEncryptionKey, TicketFlags paramTicketFlags, KerberosTime paramKerberosTime1, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, KerberosTime paramKerberosTime4, HostAddresses paramHostAddresses) {
/*  95 */     this.ticket = paramTicket;
/*  96 */     this.client = paramPrincipalName1;
/*  97 */     this.server = paramPrincipalName2;
/*  98 */     this.key = paramEncryptionKey;
/*  99 */     this.flags = paramTicketFlags;
/* 100 */     this.authTime = paramKerberosTime1;
/* 101 */     this.startTime = paramKerberosTime2;
/* 102 */     this.endTime = paramKerberosTime3;
/* 103 */     this.renewTill = paramKerberosTime4;
/* 104 */     this.cAddr = paramHostAddresses;
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
/*     */   public Credentials(byte[] paramArrayOfbyte1, String paramString1, String paramString2, byte[] paramArrayOfbyte2, int paramInt, boolean[] paramArrayOfboolean, Date paramDate1, Date paramDate2, Date paramDate3, Date paramDate4, InetAddress[] paramArrayOfInetAddress) throws KrbException, IOException {
/* 118 */     this(new Ticket(paramArrayOfbyte1), new PrincipalName(paramString1, 1), new PrincipalName(paramString2, 2), new EncryptionKey(paramInt, paramArrayOfbyte2), (paramArrayOfboolean == null) ? null : new TicketFlags(paramArrayOfboolean), (paramDate1 == null) ? null : new KerberosTime(paramDate1), (paramDate2 == null) ? null : new KerberosTime(paramDate2), (paramDate3 == null) ? null : new KerberosTime(paramDate3), (paramDate4 == null) ? null : new KerberosTime(paramDate4), null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PrincipalName getClient() {
/* 142 */     return this.client;
/*     */   }
/*     */   
/*     */   public final PrincipalName getServer() {
/* 146 */     return this.server;
/*     */   }
/*     */   
/*     */   public final EncryptionKey getSessionKey() {
/* 150 */     return this.key;
/*     */   }
/*     */   
/*     */   public final Date getAuthTime() {
/* 154 */     if (this.authTime != null) {
/* 155 */       return this.authTime.toDate();
/*     */     }
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Date getStartTime() {
/* 162 */     if (this.startTime != null)
/*     */     {
/* 164 */       return this.startTime.toDate();
/*     */     }
/* 166 */     return null;
/*     */   }
/*     */   
/*     */   public final Date getEndTime() {
/* 170 */     if (this.endTime != null)
/*     */     {
/* 172 */       return this.endTime.toDate();
/*     */     }
/* 174 */     return null;
/*     */   }
/*     */   
/*     */   public final Date getRenewTill() {
/* 178 */     if (this.renewTill != null)
/*     */     {
/* 180 */       return this.renewTill.toDate();
/*     */     }
/* 182 */     return null;
/*     */   }
/*     */   
/*     */   public final boolean[] getFlags() {
/* 186 */     if (this.flags == null)
/* 187 */       return null; 
/* 188 */     return this.flags.toBooleanArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public final InetAddress[] getClientAddresses() {
/* 193 */     if (this.cAddr == null) {
/* 194 */       return null;
/*     */     }
/* 196 */     return this.cAddr.getInetAddresses();
/*     */   }
/*     */   
/*     */   public final byte[] getEncoded() {
/* 200 */     byte[] arrayOfByte = null;
/*     */     try {
/* 202 */       arrayOfByte = this.ticket.asn1Encode();
/* 203 */     } catch (Asn1Exception asn1Exception) {
/* 204 */       if (DEBUG)
/* 205 */         System.out.println(asn1Exception); 
/* 206 */     } catch (IOException iOException) {
/* 207 */       if (DEBUG)
/* 208 */         System.out.println(iOException); 
/*     */     } 
/* 210 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public boolean isForwardable() {
/* 214 */     return this.flags.get(1);
/*     */   }
/*     */   
/*     */   public boolean isRenewable() {
/* 218 */     return this.flags.get(8);
/*     */   }
/*     */   
/*     */   public Ticket getTicket() {
/* 222 */     return this.ticket;
/*     */   }
/*     */   
/*     */   public TicketFlags getTicketFlags() {
/* 226 */     return this.flags;
/*     */   }
/*     */   
/*     */   public AuthorizationData getAuthzData() {
/* 230 */     return this.authzData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkDelegate() {
/* 238 */     return this.flags.get(13);
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
/*     */   public void resetDelegate() {
/* 250 */     this.flags.set(13, false);
/*     */   }
/*     */   
/*     */   public Credentials renew() throws KrbException, IOException {
/* 254 */     KDCOptions kDCOptions = new KDCOptions();
/* 255 */     kDCOptions.set(30, true);
/*     */ 
/*     */ 
/*     */     
/* 259 */     kDCOptions.set(8, true);
/*     */     
/* 261 */     return (new KrbTgsReq(kDCOptions, this, this.server, null, null, null, null, this.cAddr, null, null, null))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 271 */       .sendAndGetCreds();
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
/*     */   public static Credentials acquireTGTFromCache(PrincipalName paramPrincipalName, String paramString) throws KrbException, IOException {
/* 289 */     if (paramString == null) {
/*     */       
/* 291 */       String str = AccessController.<String>doPrivileged(new GetPropertyAction("os.name"));
/*     */       
/* 293 */       if (str.toUpperCase(Locale.ENGLISH).startsWith("WINDOWS") || str
/* 294 */         .toUpperCase(Locale.ENGLISH).contains("OS X")) {
/* 295 */         Credentials credentials1 = acquireDefaultCreds();
/* 296 */         if (credentials1 == null) {
/* 297 */           if (DEBUG) {
/* 298 */             System.out.println(">>> Found no TGT's in LSA");
/*     */           }
/* 300 */           return null;
/*     */         } 
/* 302 */         if (paramPrincipalName != null) {
/* 303 */           if (credentials1.getClient().equals(paramPrincipalName)) {
/* 304 */             if (DEBUG) {
/* 305 */               System.out.println(">>> Obtained TGT from LSA: " + credentials1);
/*     */             }
/*     */             
/* 308 */             return credentials1;
/*     */           } 
/* 310 */           if (DEBUG) {
/* 311 */             System.out.println(">>> LSA contains TGT for " + credentials1
/* 312 */                 .getClient() + " not " + paramPrincipalName);
/*     */           }
/*     */ 
/*     */           
/* 316 */           return null;
/*     */         } 
/*     */         
/* 319 */         if (DEBUG) {
/* 320 */           System.out.println(">>> Obtained TGT from LSA: " + credentials1);
/*     */         }
/*     */         
/* 323 */         return credentials1;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 333 */     CredentialsCache credentialsCache = CredentialsCache.getInstance(paramPrincipalName, paramString);
/*     */     
/* 335 */     if (credentialsCache == null) {
/* 336 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 340 */     Credentials credentials = credentialsCache.getDefaultCreds();
/*     */     
/* 342 */     if (credentials == null) {
/* 343 */       return null;
/*     */     }
/*     */     
/* 346 */     if (EType.isSupported(credentials.getEType())) {
/* 347 */       return credentials.setKrbCreds();
/*     */     }
/* 349 */     if (DEBUG) {
/* 350 */       System.out.println(">>> unsupported key type found the default TGT: " + credentials
/*     */           
/* 352 */           .getEType());
/*     */     }
/* 354 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized Credentials acquireDefaultCreds() {
/* 381 */     Credentials credentials = null;
/*     */     
/* 383 */     if (cache == null) {
/* 384 */       cache = CredentialsCache.getInstance();
/*     */     }
/* 386 */     if (cache != null) {
/*     */       
/* 388 */       Credentials credentials1 = cache.getDefaultCreds();
/* 389 */       if (credentials1 != null) {
/* 390 */         if (DEBUG) {
/* 391 */           System.out.println(">>> KrbCreds found the default ticket granting ticket in credential cache.");
/*     */         }
/*     */         
/* 394 */         if (EType.isSupported(credentials1.getEType())) {
/* 395 */           credentials = credentials1.setKrbCreds();
/*     */         }
/* 397 */         else if (DEBUG) {
/* 398 */           System.out.println(">>> unsupported key type found the default TGT: " + credentials1
/*     */               
/* 400 */               .getEType());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 405 */     if (credentials == null) {
/*     */ 
/*     */ 
/*     */       
/* 409 */       if (!alreadyTried) {
/*     */         
/*     */         try {
/* 412 */           ensureLoaded();
/* 413 */         } catch (Exception exception) {
/* 414 */           if (DEBUG) {
/* 415 */             System.out.println("Can not load credentials cache");
/* 416 */             exception.printStackTrace();
/*     */           } 
/* 418 */           alreadyTried = true;
/*     */         } 
/*     */       }
/* 421 */       if (alreadyLoaded) {
/*     */         
/* 423 */         if (DEBUG) {
/* 424 */           System.out.println(">> Acquire default native Credentials");
/*     */         }
/*     */         try {
/* 427 */           credentials = acquireDefaultNativeCreds(
/* 428 */               EType.getDefaults("default_tkt_enctypes"));
/* 429 */         } catch (KrbException krbException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 434 */     return credentials;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Credentials acquireServiceCreds(String paramString, Credentials paramCredentials) throws KrbException, IOException {
/* 458 */     return CredentialsUtil.acquireServiceCreds(paramString, paramCredentials);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Credentials acquireS4U2selfCreds(PrincipalName paramPrincipalName, Credentials paramCredentials) throws KrbException, IOException {
/* 463 */     return CredentialsUtil.acquireS4U2selfCreds(paramPrincipalName, paramCredentials);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Credentials acquireS4U2proxyCreds(String paramString, Ticket paramTicket, PrincipalName paramPrincipalName, Credentials paramCredentials) throws KrbException, IOException {
/* 469 */     return CredentialsUtil.acquireS4U2proxyCreds(paramString, paramTicket, paramPrincipalName, paramCredentials);
/*     */   }
/*     */ 
/*     */   
/*     */   public CredentialsCache getCache() {
/* 474 */     return cache;
/*     */   }
/*     */   
/*     */   public EncryptionKey getServiceKey() {
/* 478 */     return this.serviceKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void printDebug(Credentials paramCredentials) {
/* 485 */     System.out.println(">>> DEBUG: ----Credentials----");
/* 486 */     System.out.println("\tclient: " + paramCredentials.client.toString());
/* 487 */     System.out.println("\tserver: " + paramCredentials.server.toString());
/* 488 */     System.out.println("\tticket: sname: " + paramCredentials.ticket.sname.toString());
/* 489 */     if (paramCredentials.startTime != null) {
/* 490 */       System.out.println("\tstartTime: " + paramCredentials.startTime.getTime());
/*     */     }
/* 492 */     System.out.println("\tendTime: " + paramCredentials.endTime.getTime());
/* 493 */     System.out.println("        ----Credentials end----");
/*     */   }
/*     */ 
/*     */   
/*     */   static void ensureLoaded() {
/* 498 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 501 */             if (System.getProperty("os.name").contains("OS X")) {
/* 502 */               System.loadLibrary("osxkrb5");
/*     */             } else {
/* 504 */               System.loadLibrary("w2k_lsa_auth");
/*     */             } 
/* 506 */             return null;
/*     */           }
/*     */         });
/* 509 */     alreadyLoaded = true;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 513 */     StringBuffer stringBuffer = new StringBuffer("Credentials:");
/* 514 */     stringBuffer.append("\n      client=").append(this.client);
/* 515 */     stringBuffer.append("\n      server=").append(this.server);
/* 516 */     if (this.authTime != null) {
/* 517 */       stringBuffer.append("\n    authTime=").append(this.authTime);
/*     */     }
/* 519 */     if (this.startTime != null) {
/* 520 */       stringBuffer.append("\n   startTime=").append(this.startTime);
/*     */     }
/* 522 */     stringBuffer.append("\n     endTime=").append(this.endTime);
/* 523 */     stringBuffer.append("\n   renewTill=").append(this.renewTill);
/* 524 */     stringBuffer.append("\n       flags=").append(this.flags);
/* 525 */     stringBuffer.append("\nEType (skey)=").append(this.key.getEType());
/* 526 */     stringBuffer.append("\n   (tkt key)=").append(this.ticket.encPart.eType);
/* 527 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\Credentials.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */