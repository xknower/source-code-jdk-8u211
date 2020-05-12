/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.kerberos.KerberosPrincipal;
/*     */ import javax.security.auth.kerberos.KerberosTicket;
/*     */ import javax.security.auth.kerberos.KeyTab;
/*     */ import javax.security.auth.login.LoginException;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.security.jgss.GSSCaller;
/*     */ import sun.security.jgss.GSSUtil;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KerberosSecrets;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.internal.ktab.KeyTab;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Krb5Util
/*     */ {
/*  53 */   static final boolean DEBUG = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.security.krb5.debug")))
/*     */     
/*  55 */     .booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KerberosTicket getTicketFromSubjectAndTgs(GSSCaller paramGSSCaller, String paramString1, String paramString2, String paramString3, AccessControlContext paramAccessControlContext) throws LoginException, KrbException, IOException {
/*     */     boolean bool;
/*  81 */     Subject subject1 = Subject.getSubject(paramAccessControlContext);
/*  82 */     KerberosTicket kerberosTicket1 = SubjectComber.<KerberosTicket>find(subject1, paramString2, paramString1, KerberosTicket.class);
/*     */ 
/*     */     
/*  85 */     if (kerberosTicket1 != null) {
/*  86 */       return kerberosTicket1;
/*     */     }
/*     */     
/*  89 */     Subject subject2 = null;
/*  90 */     if (!GSSUtil.useSubjectCredsOnly(paramGSSCaller)) {
/*     */       
/*     */       try {
/*  93 */         subject2 = GSSUtil.login(paramGSSCaller, GSSUtil.GSS_KRB5_MECH_OID);
/*  94 */         kerberosTicket1 = SubjectComber.<KerberosTicket>find(subject2, paramString2, paramString1, KerberosTicket.class);
/*     */         
/*  96 */         if (kerberosTicket1 != null) {
/*  97 */           return kerberosTicket1;
/*     */         }
/*  99 */       } catch (LoginException loginException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     KerberosTicket kerberosTicket2 = SubjectComber.<KerberosTicket>find(subject1, paramString3, paramString1, KerberosTicket.class);
/*     */ 
/*     */ 
/*     */     
/* 113 */     if (kerberosTicket2 == null && subject2 != null) {
/*     */       
/* 115 */       kerberosTicket2 = SubjectComber.<KerberosTicket>find(subject2, paramString3, paramString1, KerberosTicket.class);
/*     */       
/* 117 */       bool = false;
/*     */     } else {
/* 119 */       bool = true;
/*     */     } 
/*     */ 
/*     */     
/* 123 */     if (kerberosTicket2 != null) {
/* 124 */       Credentials credentials1 = ticketToCreds(kerberosTicket2);
/* 125 */       Credentials credentials2 = Credentials.acquireServiceCreds(paramString2, credentials1);
/*     */       
/* 127 */       if (credentials2 != null) {
/* 128 */         kerberosTicket1 = credsToTicket(credentials2);
/*     */ 
/*     */         
/* 131 */         if (bool && subject1 != null && !subject1.isReadOnly()) {
/* 132 */           subject1.getPrivateCredentials().add(kerberosTicket1);
/*     */         }
/*     */       } 
/*     */     } 
/* 136 */     return kerberosTicket1;
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
/*     */   static KerberosTicket getTicket(GSSCaller paramGSSCaller, String paramString1, String paramString2, AccessControlContext paramAccessControlContext) throws LoginException {
/* 151 */     Subject subject = Subject.getSubject(paramAccessControlContext);
/*     */     
/* 153 */     KerberosTicket kerberosTicket = SubjectComber.<KerberosTicket>find(subject, paramString2, paramString1, KerberosTicket.class);
/*     */ 
/*     */ 
/*     */     
/* 157 */     if (kerberosTicket == null && !GSSUtil.useSubjectCredsOnly(paramGSSCaller)) {
/* 158 */       Subject subject1 = GSSUtil.login(paramGSSCaller, GSSUtil.GSS_KRB5_MECH_OID);
/* 159 */       kerberosTicket = SubjectComber.<KerberosTicket>find(subject1, paramString2, paramString1, KerberosTicket.class);
/*     */     } 
/*     */     
/* 162 */     return kerberosTicket;
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
/*     */   public static Subject getSubject(GSSCaller paramGSSCaller, AccessControlContext paramAccessControlContext) throws LoginException {
/* 180 */     Subject subject = Subject.getSubject(paramAccessControlContext);
/*     */ 
/*     */     
/* 183 */     if (subject == null && !GSSUtil.useSubjectCredsOnly(paramGSSCaller)) {
/* 184 */       subject = GSSUtil.login(paramGSSCaller, GSSUtil.GSS_KRB5_MECH_OID);
/*     */     }
/* 186 */     return subject;
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
/*     */   public static ServiceCreds getServiceCreds(GSSCaller paramGSSCaller, String paramString, AccessControlContext paramAccessControlContext) throws LoginException {
/* 200 */     Subject subject = Subject.getSubject(paramAccessControlContext);
/* 201 */     ServiceCreds serviceCreds = null;
/* 202 */     if (subject != null) {
/* 203 */       serviceCreds = ServiceCreds.getInstance(subject, paramString);
/*     */     }
/* 205 */     if (serviceCreds == null && !GSSUtil.useSubjectCredsOnly(paramGSSCaller)) {
/* 206 */       Subject subject1 = GSSUtil.login(paramGSSCaller, GSSUtil.GSS_KRB5_MECH_OID);
/* 207 */       serviceCreds = ServiceCreds.getInstance(subject1, paramString);
/*     */     } 
/* 209 */     return serviceCreds;
/*     */   }
/*     */   
/*     */   public static KerberosTicket credsToTicket(Credentials paramCredentials) {
/* 213 */     EncryptionKey encryptionKey = paramCredentials.getSessionKey();
/* 214 */     return new KerberosTicket(paramCredentials
/* 215 */         .getEncoded(), new KerberosPrincipal(paramCredentials
/* 216 */           .getClient().getName()), new KerberosPrincipal(paramCredentials
/* 217 */           .getServer().getName(), 2), encryptionKey
/*     */         
/* 219 */         .getBytes(), encryptionKey
/* 220 */         .getEType(), paramCredentials
/* 221 */         .getFlags(), paramCredentials
/* 222 */         .getAuthTime(), paramCredentials
/* 223 */         .getStartTime(), paramCredentials
/* 224 */         .getEndTime(), paramCredentials
/* 225 */         .getRenewTill(), paramCredentials
/* 226 */         .getClientAddresses());
/*     */   }
/*     */ 
/*     */   
/*     */   public static Credentials ticketToCreds(KerberosTicket paramKerberosTicket) throws KrbException, IOException {
/* 231 */     return new Credentials(paramKerberosTicket
/* 232 */         .getEncoded(), paramKerberosTicket
/* 233 */         .getClient().getName(), paramKerberosTicket
/* 234 */         .getServer().getName(), paramKerberosTicket
/* 235 */         .getSessionKey().getEncoded(), paramKerberosTicket
/* 236 */         .getSessionKeyType(), paramKerberosTicket
/* 237 */         .getFlags(), paramKerberosTicket
/* 238 */         .getAuthTime(), paramKerberosTicket
/* 239 */         .getStartTime(), paramKerberosTicket
/* 240 */         .getEndTime(), paramKerberosTicket
/* 241 */         .getRenewTill(), paramKerberosTicket
/* 242 */         .getClientAddresses());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTab snapshotFromJavaxKeyTab(KeyTab paramKeyTab) {
/* 252 */     return KerberosSecrets.getJavaxSecurityAuthKerberosAccess()
/* 253 */       .keyTabTakeSnapshot(paramKeyTab);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncryptionKey[] keysFromJavaxKeyTab(KeyTab paramKeyTab, PrincipalName paramPrincipalName) {
/* 264 */     return snapshotFromJavaxKeyTab(paramKeyTab).readServiceKeys(paramPrincipalName);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\jgss\krb5\Krb5Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */