/*     */ package javax.security.auth.kerberos;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.Principal;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.Realm;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class KerberosPrincipal
/*     */   implements Principal, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7374788026156829911L;
/*     */   public static final int KRB_NT_UNKNOWN = 0;
/*     */   public static final int KRB_NT_PRINCIPAL = 1;
/*     */   public static final int KRB_NT_SRV_INST = 2;
/*     */   public static final int KRB_NT_SRV_HST = 3;
/*     */   public static final int KRB_NT_SRV_XHST = 4;
/*     */   public static final int KRB_NT_UID = 5;
/*     */   private transient String fullName;
/*     */   private transient String realm;
/*     */   private transient int nameType;
/*     */   
/*     */   public KerberosPrincipal(String paramString) {
/* 115 */     this(paramString, 1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosPrincipal(String paramString, int paramInt) {
/* 148 */     PrincipalName principalName = null;
/*     */ 
/*     */     
/*     */     try {
/* 152 */       principalName = new PrincipalName(paramString, paramInt);
/* 153 */     } catch (KrbException krbException) {
/* 154 */       throw new IllegalArgumentException(krbException.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 159 */     if (principalName.isRealmDeduced() && !Realm.AUTODEDUCEREALM) {
/* 160 */       SecurityManager securityManager = System.getSecurityManager();
/* 161 */       if (securityManager != null) {
/*     */         try {
/* 163 */           securityManager.checkPermission(new ServicePermission("@" + principalName
/* 164 */                 .getRealmAsString(), "-"));
/* 165 */         } catch (SecurityException securityException) {
/*     */           
/* 167 */           throw new SecurityException("Cannot read realm info");
/*     */         } 
/*     */       }
/*     */     } 
/* 171 */     this.nameType = paramInt;
/* 172 */     this.fullName = principalName.toString();
/* 173 */     this.realm = principalName.getRealmString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRealm() {
/* 181 */     return this.realm;
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
/*     */   public int hashCode() {
/* 194 */     return getName().hashCode();
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
/*     */   public boolean equals(Object paramObject) {
/* 211 */     if (paramObject == this) {
/* 212 */       return true;
/*     */     }
/* 214 */     if (!(paramObject instanceof KerberosPrincipal)) {
/* 215 */       return false;
/*     */     }
/* 217 */     String str1 = getName();
/* 218 */     String str2 = ((KerberosPrincipal)paramObject).getName();
/* 219 */     return str1.equals(str2);
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*     */     try {
/* 235 */       PrincipalName principalName = new PrincipalName(this.fullName, this.nameType);
/* 236 */       paramObjectOutputStream.writeObject(principalName.asn1Encode());
/* 237 */       paramObjectOutputStream.writeObject(principalName.getRealm().asn1Encode());
/* 238 */     } catch (Exception exception) {
/* 239 */       throw new IOException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 248 */     byte[] arrayOfByte1 = (byte[])paramObjectInputStream.readObject();
/* 249 */     byte[] arrayOfByte2 = (byte[])paramObjectInputStream.readObject();
/*     */     try {
/* 251 */       Realm realm = new Realm(new DerValue(arrayOfByte2));
/* 252 */       PrincipalName principalName = new PrincipalName(new DerValue(arrayOfByte1), realm);
/*     */       
/* 254 */       this.realm = realm.toString();
/* 255 */       this.fullName = principalName.toString();
/* 256 */       this.nameType = principalName.getNameType();
/* 257 */     } catch (Exception exception) {
/* 258 */       throw new IOException(exception);
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
/*     */   public String getName() {
/* 270 */     return this.fullName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNameType() {
/* 281 */     return this.nameType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 286 */     return getName();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\security\auth\kerberos\KerberosPrincipal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */