/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.security.Provider;
/*     */ import java.util.Vector;
/*     */ import javax.security.auth.kerberos.ServicePermission;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.GSSCaller;
/*     */ import sun.security.jgss.GSSUtil;
/*     */ import sun.security.jgss.SunProvider;
/*     */ import sun.security.jgss.spi.GSSContextSpi;
/*     */ import sun.security.jgss.spi.GSSCredentialSpi;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.jgss.spi.MechanismFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Krb5MechFactory
/*     */   implements MechanismFactory
/*     */ {
/*  46 */   private static final boolean DEBUG = Krb5Util.DEBUG;
/*     */   
/*  48 */   static final Provider PROVIDER = new SunProvider();
/*     */ 
/*     */ 
/*     */   
/*  52 */   static final Oid GSS_KRB5_MECH_OID = createOid("1.2.840.113554.1.2.2");
/*     */ 
/*     */   
/*  55 */   static final Oid NT_GSS_KRB5_PRINCIPAL = createOid("1.2.840.113554.1.2.2.1");
/*     */   
/*  57 */   private static Oid[] nameTypes = new Oid[] { GSSName.NT_USER_NAME, GSSName.NT_HOSTBASED_SERVICE, GSSName.NT_EXPORT_NAME, NT_GSS_KRB5_PRINCIPAL };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final GSSCaller caller;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Krb5CredElement getCredFromSubject(GSSNameSpi paramGSSNameSpi, boolean paramBoolean) throws GSSException {
/*  69 */     Vector<GSSCredentialSpi> vector = GSSUtil.searchSubject(paramGSSNameSpi, GSS_KRB5_MECH_OID, paramBoolean, paramBoolean ? (Class)Krb5InitCredential.class : (Class)Krb5AcceptCredential.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     Krb5CredElement krb5CredElement = (vector == null || vector.isEmpty()) ? null : (Krb5CredElement)vector.firstElement();
/*     */ 
/*     */     
/*  78 */     if (krb5CredElement != null) {
/*  79 */       if (paramBoolean) {
/*  80 */         checkInitCredPermission((Krb5NameElement)krb5CredElement.getName());
/*     */       } else {
/*     */         
/*  83 */         checkAcceptCredPermission((Krb5NameElement)krb5CredElement.getName(), paramGSSNameSpi);
/*     */       } 
/*     */     }
/*  86 */     return krb5CredElement;
/*     */   }
/*     */   
/*     */   public Krb5MechFactory(GSSCaller paramGSSCaller) {
/*  90 */     this.caller = paramGSSCaller;
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSNameSpi getNameElement(String paramString, Oid paramOid) throws GSSException {
/*  95 */     return Krb5NameElement.getInstance(paramString, paramOid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSNameSpi getNameElement(byte[] paramArrayOfbyte, Oid paramOid) throws GSSException {
/* 103 */     return Krb5NameElement.getInstance(new String(paramArrayOfbyte), paramOid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSCredentialSpi getCredentialElement(GSSNameSpi paramGSSNameSpi, int paramInt1, int paramInt2, int paramInt3) throws GSSException {
/* 110 */     if (paramGSSNameSpi != null && !(paramGSSNameSpi instanceof Krb5NameElement)) {
/* 111 */       paramGSSNameSpi = Krb5NameElement.getInstance(paramGSSNameSpi.toString(), paramGSSNameSpi
/* 112 */           .getStringNameType());
/*     */     }
/*     */ 
/*     */     
/* 116 */     Krb5CredElement krb5CredElement = getCredFromSubject(paramGSSNameSpi, (paramInt3 != 2));
/*     */     
/* 118 */     if (krb5CredElement == null) {
/* 119 */       if (paramInt3 == 1 || paramInt3 == 0) {
/*     */ 
/*     */         
/* 122 */         krb5CredElement = Krb5InitCredential.getInstance(this.caller, (Krb5NameElement)paramGSSNameSpi, paramInt1);
/*     */         
/* 124 */         checkInitCredPermission((Krb5NameElement)krb5CredElement.getName());
/* 125 */       } else if (paramInt3 == 2) {
/*     */         
/* 127 */         krb5CredElement = Krb5AcceptCredential.getInstance(this.caller, (Krb5NameElement)paramGSSNameSpi);
/*     */ 
/*     */         
/* 130 */         checkAcceptCredPermission((Krb5NameElement)krb5CredElement.getName(), paramGSSNameSpi);
/*     */       } else {
/* 132 */         throw new GSSException(11, -1, "Unknown usage mode requested");
/*     */       } 
/*     */     }
/* 135 */     return krb5CredElement;
/*     */   }
/*     */   
/*     */   public static void checkInitCredPermission(Krb5NameElement paramKrb5NameElement) {
/* 139 */     SecurityManager securityManager = System.getSecurityManager();
/* 140 */     if (securityManager != null) {
/* 141 */       String str1 = paramKrb5NameElement.getKrb5PrincipalName().getRealmAsString();
/* 142 */       String str2 = new String("krbtgt/" + str1 + '@' + str1);
/*     */       
/* 144 */       ServicePermission servicePermission = new ServicePermission(str2, "initiate");
/*     */       
/*     */       try {
/* 147 */         securityManager.checkPermission(servicePermission);
/* 148 */       } catch (SecurityException securityException) {
/* 149 */         if (DEBUG) {
/* 150 */           System.out.println("Permission to initiatekerberos init credential" + securityException
/* 151 */               .getMessage());
/*     */         }
/* 153 */         throw securityException;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void checkAcceptCredPermission(Krb5NameElement paramKrb5NameElement, GSSNameSpi paramGSSNameSpi) {
/* 160 */     SecurityManager securityManager = System.getSecurityManager();
/* 161 */     if (securityManager != null && paramKrb5NameElement != null) {
/*     */       
/* 163 */       ServicePermission servicePermission = new ServicePermission(paramKrb5NameElement.getKrb5PrincipalName().getName(), "accept");
/*     */       try {
/* 165 */         securityManager.checkPermission(servicePermission);
/* 166 */       } catch (SecurityException securityException) {
/* 167 */         if (paramGSSNameSpi == null)
/*     */         {
/* 169 */           securityException = new SecurityException("No permission to acquire Kerberos accept credential");
/*     */         }
/*     */ 
/*     */         
/* 173 */         throw securityException;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSContextSpi getMechanismContext(GSSNameSpi paramGSSNameSpi, GSSCredentialSpi paramGSSCredentialSpi, int paramInt) throws GSSException {
/* 181 */     if (paramGSSNameSpi != null && !(paramGSSNameSpi instanceof Krb5NameElement)) {
/* 182 */       paramGSSNameSpi = Krb5NameElement.getInstance(paramGSSNameSpi.toString(), paramGSSNameSpi
/* 183 */           .getStringNameType());
/*     */     }
/*     */     
/* 186 */     if (paramGSSCredentialSpi == null) {
/* 187 */       paramGSSCredentialSpi = getCredentialElement(null, paramInt, 0, 1);
/*     */     }
/*     */     
/* 190 */     return new Krb5Context(this.caller, (Krb5NameElement)paramGSSNameSpi, (Krb5CredElement)paramGSSCredentialSpi, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSContextSpi getMechanismContext(GSSCredentialSpi paramGSSCredentialSpi) throws GSSException {
/* 197 */     if (paramGSSCredentialSpi == null) {
/* 198 */       paramGSSCredentialSpi = getCredentialElement(null, 0, 2147483647, 2);
/*     */     }
/*     */     
/* 201 */     return new Krb5Context(this.caller, (Krb5CredElement)paramGSSCredentialSpi);
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSContextSpi getMechanismContext(byte[] paramArrayOfbyte) throws GSSException {
/* 206 */     return new Krb5Context(this.caller, paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Oid getMechanismOid() {
/* 211 */     return GSS_KRB5_MECH_OID;
/*     */   }
/*     */   
/*     */   public Provider getProvider() {
/* 215 */     return PROVIDER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Oid[] getNameTypes() {
/* 220 */     return nameTypes;
/*     */   }
/*     */   
/*     */   private static Oid createOid(String paramString) {
/* 224 */     Oid oid = null;
/*     */     try {
/* 226 */       oid = new Oid(paramString);
/* 227 */     } catch (GSSException gSSException) {}
/*     */ 
/*     */     
/* 230 */     return oid;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\jgss\krb5\Krb5MechFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */