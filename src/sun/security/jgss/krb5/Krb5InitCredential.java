/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.Provider;
/*     */ import java.util.Date;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.kerberos.KerberosPrincipal;
/*     */ import javax.security.auth.kerberos.KerberosTicket;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.GSSCaller;
/*     */ import sun.security.jgss.spi.GSSCredentialSpi;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
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
/*     */ public class Krb5InitCredential
/*     */   extends KerberosTicket
/*     */   implements Krb5CredElement
/*     */ {
/*     */   private static final long serialVersionUID = 7723415700837898232L;
/*     */   private Krb5NameElement name;
/*     */   private Credentials krb5Credentials;
/*     */   
/*     */   private Krb5InitCredential(Krb5NameElement paramKrb5NameElement, byte[] paramArrayOfbyte1, KerberosPrincipal paramKerberosPrincipal1, KerberosPrincipal paramKerberosPrincipal2, byte[] paramArrayOfbyte2, int paramInt, boolean[] paramArrayOfboolean, Date paramDate1, Date paramDate2, Date paramDate3, Date paramDate4, InetAddress[] paramArrayOfInetAddress) throws GSSException {
/*  72 */     super(paramArrayOfbyte1, paramKerberosPrincipal1, paramKerberosPrincipal2, paramArrayOfbyte2, paramInt, paramArrayOfboolean, paramDate1, paramDate2, paramDate3, paramDate4, paramArrayOfInetAddress);
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
/*  84 */     this.name = paramKrb5NameElement;
/*     */ 
/*     */     
/*     */     try {
/*  88 */       this
/*     */         
/*  90 */         .krb5Credentials = new Credentials(paramArrayOfbyte1, paramKerberosPrincipal1.getName(), paramKerberosPrincipal2.getName(), paramArrayOfbyte2, paramInt, paramArrayOfboolean, paramDate1, paramDate2, paramDate3, paramDate4, paramArrayOfInetAddress);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  99 */     catch (KrbException krbException) {
/* 100 */       throw new GSSException(13, -1, krbException
/* 101 */           .getMessage());
/* 102 */     } catch (IOException iOException) {
/* 103 */       throw new GSSException(13, -1, iOException
/* 104 */           .getMessage());
/*     */     } 
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
/*     */   private Krb5InitCredential(Krb5NameElement paramKrb5NameElement, Credentials paramCredentials, byte[] paramArrayOfbyte1, KerberosPrincipal paramKerberosPrincipal1, KerberosPrincipal paramKerberosPrincipal2, byte[] paramArrayOfbyte2, int paramInt, boolean[] paramArrayOfboolean, Date paramDate1, Date paramDate2, Date paramDate3, Date paramDate4, InetAddress[] paramArrayOfInetAddress) throws GSSException {
/* 123 */     super(paramArrayOfbyte1, paramKerberosPrincipal1, paramKerberosPrincipal2, paramArrayOfbyte2, paramInt, paramArrayOfboolean, paramDate1, paramDate2, paramDate3, paramDate4, paramArrayOfInetAddress);
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
/* 135 */     this.name = paramKrb5NameElement;
/*     */ 
/*     */     
/* 138 */     this.krb5Credentials = paramCredentials;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Krb5InitCredential getInstance(GSSCaller paramGSSCaller, Krb5NameElement paramKrb5NameElement, int paramInt) throws GSSException {
/* 145 */     KerberosTicket kerberosTicket = getTgt(paramGSSCaller, paramKrb5NameElement, paramInt);
/* 146 */     if (kerberosTicket == null) {
/* 147 */       throw new GSSException(13, -1, "Failed to find any Kerberos tgt");
/*     */     }
/*     */     
/* 150 */     if (paramKrb5NameElement == null) {
/* 151 */       String str = kerberosTicket.getClient().getName();
/* 152 */       paramKrb5NameElement = Krb5NameElement.getInstance(str, Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL);
/*     */     } 
/*     */ 
/*     */     
/* 156 */     return new Krb5InitCredential(paramKrb5NameElement, kerberosTicket
/* 157 */         .getEncoded(), kerberosTicket
/* 158 */         .getClient(), kerberosTicket
/* 159 */         .getServer(), kerberosTicket
/* 160 */         .getSessionKey().getEncoded(), kerberosTicket
/* 161 */         .getSessionKeyType(), kerberosTicket
/* 162 */         .getFlags(), kerberosTicket
/* 163 */         .getAuthTime(), kerberosTicket
/* 164 */         .getStartTime(), kerberosTicket
/* 165 */         .getEndTime(), kerberosTicket
/* 166 */         .getRenewTill(), kerberosTicket
/* 167 */         .getClientAddresses());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Krb5InitCredential getInstance(Krb5NameElement paramKrb5NameElement, Credentials paramCredentials) throws GSSException {
/* 174 */     EncryptionKey encryptionKey = paramCredentials.getSessionKey();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     PrincipalName principalName1 = paramCredentials.getClient();
/* 182 */     PrincipalName principalName2 = paramCredentials.getServer();
/*     */     
/* 184 */     KerberosPrincipal kerberosPrincipal1 = null;
/* 185 */     KerberosPrincipal kerberosPrincipal2 = null;
/*     */     
/* 187 */     Krb5NameElement krb5NameElement = null;
/*     */     
/* 189 */     if (principalName1 != null) {
/* 190 */       String str = principalName1.getName();
/* 191 */       krb5NameElement = Krb5NameElement.getInstance(str, Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL);
/*     */       
/* 193 */       kerberosPrincipal1 = new KerberosPrincipal(str);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 198 */     if (principalName2 != null)
/*     */     {
/* 200 */       kerberosPrincipal2 = new KerberosPrincipal(principalName2.getName(), 2);
/*     */     }
/*     */ 
/*     */     
/* 204 */     return new Krb5InitCredential(krb5NameElement, paramCredentials, paramCredentials
/*     */         
/* 206 */         .getEncoded(), kerberosPrincipal1, kerberosPrincipal2, encryptionKey
/*     */ 
/*     */         
/* 209 */         .getBytes(), encryptionKey
/* 210 */         .getEType(), paramCredentials
/* 211 */         .getFlags(), paramCredentials
/* 212 */         .getAuthTime(), paramCredentials
/* 213 */         .getStartTime(), paramCredentials
/* 214 */         .getEndTime(), paramCredentials
/* 215 */         .getRenewTill(), paramCredentials
/* 216 */         .getClientAddresses());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final GSSNameSpi getName() throws GSSException {
/* 227 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInitLifetime() throws GSSException {
/* 237 */     Date date = getEndTime();
/* 238 */     if (date == null) {
/* 239 */       return 0;
/*     */     }
/*     */     
/* 242 */     long l = date.getTime() - System.currentTimeMillis();
/* 243 */     return (int)(l / 1000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAcceptLifetime() throws GSSException {
/* 253 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean isInitiatorCredential() throws GSSException {
/* 257 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isAcceptorCredential() throws GSSException {
/* 261 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Oid getMechanism() {
/* 272 */     return Krb5MechFactory.GSS_KRB5_MECH_OID;
/*     */   }
/*     */   
/*     */   public final Provider getProvider() {
/* 276 */     return Krb5MechFactory.PROVIDER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Credentials getKrb5Credentials() {
/* 285 */     return this.krb5Credentials;
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
/*     */   public void dispose() throws GSSException {
/*     */     try {
/* 298 */       destroy();
/* 299 */     } catch (DestroyFailedException destroyFailedException) {
/*     */ 
/*     */       
/* 302 */       GSSException gSSException = new GSSException(11, -1, "Could not destroy credentials - " + destroyFailedException.getMessage());
/* 303 */       gSSException.initCause(destroyFailedException);
/*     */     } 
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
/*     */   private static KerberosTicket getTgt(GSSCaller paramGSSCaller, Krb5NameElement paramKrb5NameElement, int paramInt) throws GSSException {
/*     */     final String clientPrincipal;
/* 320 */     if (paramKrb5NameElement != null) {
/* 321 */       str = paramKrb5NameElement.getKrb5PrincipalName().getName();
/*     */     } else {
/* 323 */       str = null;
/*     */     } 
/*     */     
/* 326 */     final AccessControlContext acc = AccessController.getContext();
/*     */     
/*     */     try {
/* 329 */       final GSSCaller realCaller = (paramGSSCaller == GSSCaller.CALLER_UNKNOWN) ? GSSCaller.CALLER_INITIATE : paramGSSCaller;
/*     */ 
/*     */       
/* 332 */       return AccessController.<KerberosTicket>doPrivileged(new PrivilegedExceptionAction<KerberosTicket>()
/*     */           {
/*     */             
/*     */             public KerberosTicket run() throws Exception
/*     */             {
/* 337 */               return Krb5Util.getTicket(realCaller, clientPrincipal, null, acc);
/*     */             }
/*     */           });
/*     */     }
/* 341 */     catch (PrivilegedActionException privilegedActionException) {
/*     */ 
/*     */ 
/*     */       
/* 345 */       GSSException gSSException = new GSSException(13, -1, "Attempt to obtain new INITIATE credentials failed! (" + privilegedActionException.getMessage() + ")");
/* 346 */       gSSException.initCause(privilegedActionException.getException());
/* 347 */       throw gSSException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSCredentialSpi impersonate(GSSNameSpi paramGSSNameSpi) throws GSSException {
/*     */     try {
/* 354 */       Krb5NameElement krb5NameElement = (Krb5NameElement)paramGSSNameSpi;
/* 355 */       Credentials credentials = Credentials.acquireS4U2selfCreds(krb5NameElement
/* 356 */           .getKrb5PrincipalName(), this.krb5Credentials);
/* 357 */       return new Krb5ProxyCredential(this, krb5NameElement, credentials.getTicket());
/* 358 */     } catch (IOException|KrbException iOException) {
/* 359 */       GSSException gSSException = new GSSException(11, -1, "Attempt to obtain S4U2self credentials failed!");
/*     */ 
/*     */       
/* 362 */       gSSException.initCause(iOException);
/* 363 */       throw gSSException;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\jgss\krb5\Krb5InitCredential.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */