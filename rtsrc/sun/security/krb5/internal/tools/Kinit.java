/*     */ package sun.security.krb5.internal.tools;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import javax.security.auth.kerberos.KeyTab;
/*     */ import sun.security.krb5.Config;
/*     */ import sun.security.krb5.KrbAsReqBuilder;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.RealmException;
/*     */ import sun.security.krb5.internal.HostAddresses;
/*     */ import sun.security.krb5.internal.KDCOptions;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.ccache.Credentials;
/*     */ import sun.security.krb5.internal.ccache.CredentialsCache;
/*     */ import sun.security.util.Password;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Kinit
/*     */ {
/*     */   private KinitOptions options;
/*  52 */   private static final boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/*     */     try {
/* 113 */       Kinit kinit = new Kinit(paramArrayOfString);
/*     */     }
/* 115 */     catch (Exception exception) {
/* 116 */       String str = null;
/* 117 */       if (exception instanceof KrbException) {
/*     */         
/* 119 */         str = ((KrbException)exception).krbErrorMessage() + " " + ((KrbException)exception).returnCodeMessage();
/*     */       } else {
/* 121 */         str = exception.getMessage();
/*     */       } 
/* 123 */       if (str != null) {
/* 124 */         System.err.println("Exception: " + str);
/*     */       } else {
/* 126 */         System.out.println("Exception: " + exception);
/*     */       } 
/* 128 */       exception.printStackTrace();
/* 129 */       System.exit(-1);
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
/*     */   private Kinit(String[] paramArrayOfString) throws IOException, RealmException, KrbException {
/*     */     KrbAsReqBuilder krbAsReqBuilder;
/* 144 */     if (paramArrayOfString == null || paramArrayOfString.length == 0) {
/* 145 */       this.options = new KinitOptions();
/*     */     } else {
/* 147 */       this.options = new KinitOptions(paramArrayOfString);
/*     */     } 
/* 149 */     String str1 = null;
/* 150 */     PrincipalName principalName1 = this.options.getPrincipal();
/* 151 */     if (principalName1 != null) {
/* 152 */       str1 = principalName1.toString();
/*     */     }
/*     */     
/* 155 */     if (DEBUG) {
/* 156 */       System.out.println("Principal is " + principalName1);
/*     */     }
/* 158 */     char[] arrayOfChar = this.options.password;
/* 159 */     boolean bool = this.options.useKeytabFile();
/* 160 */     if (!bool) {
/* 161 */       if (str1 == null) {
/* 162 */         throw new IllegalArgumentException(" Can not obtain principal name");
/*     */       }
/*     */       
/* 165 */       if (arrayOfChar == null) {
/* 166 */         System.out.print("Password for " + str1 + ":");
/* 167 */         System.out.flush();
/* 168 */         arrayOfChar = Password.readPassword(System.in);
/* 169 */         if (DEBUG) {
/* 170 */           System.out.println(">>> Kinit console input " + new String(arrayOfChar));
/*     */         }
/*     */       } 
/*     */       
/* 174 */       krbAsReqBuilder = new KrbAsReqBuilder(principalName1, arrayOfChar);
/*     */     } else {
/* 176 */       if (DEBUG) {
/* 177 */         System.out.println(">>> Kinit using keytab");
/*     */       }
/* 179 */       if (str1 == null) {
/* 180 */         throw new IllegalArgumentException("Principal name must be specified.");
/*     */       }
/*     */       
/* 183 */       String str = this.options.keytabFileName();
/* 184 */       if (str != null && 
/* 185 */         DEBUG) {
/* 186 */         System.out.println(">>> Kinit keytab file name: " + str);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 193 */       krbAsReqBuilder = new KrbAsReqBuilder(principalName1, (str == null) ? KeyTab.getInstance() : KeyTab.getInstance(new File(str)));
/*     */     } 
/*     */     
/* 196 */     KDCOptions kDCOptions = new KDCOptions();
/* 197 */     setOptions(1, this.options.forwardable, kDCOptions);
/* 198 */     setOptions(3, this.options.proxiable, kDCOptions);
/* 199 */     krbAsReqBuilder.setOptions(kDCOptions);
/* 200 */     String str2 = this.options.getKDCRealm();
/* 201 */     if (str2 == null) {
/* 202 */       str2 = Config.getInstance().getDefaultRealm();
/*     */     }
/*     */     
/* 205 */     if (DEBUG) {
/* 206 */       System.out.println(">>> Kinit realm name is " + str2);
/*     */     }
/*     */     
/* 209 */     PrincipalName principalName2 = PrincipalName.tgsService(str2, str2);
/* 210 */     krbAsReqBuilder.setTarget(principalName2);
/*     */     
/* 212 */     if (DEBUG) {
/* 213 */       System.out.println(">>> Creating KrbAsReq");
/*     */     }
/*     */     
/* 216 */     if (this.options.getAddressOption()) {
/* 217 */       krbAsReqBuilder.setAddresses(HostAddresses.getLocalAddresses());
/*     */     }
/* 219 */     krbAsReqBuilder.action();
/*     */ 
/*     */     
/* 222 */     Credentials credentials = krbAsReqBuilder.getCCreds();
/* 223 */     krbAsReqBuilder.destroy();
/*     */ 
/*     */ 
/*     */     
/* 227 */     CredentialsCache credentialsCache = CredentialsCache.create(principalName1, this.options.cachename);
/* 228 */     if (credentialsCache == null) {
/* 229 */       throw new IOException("Unable to create the cache file " + this.options.cachename);
/*     */     }
/*     */     
/* 232 */     credentialsCache.update(credentials);
/* 233 */     credentialsCache.save();
/*     */     
/* 235 */     if (this.options.password == null) {
/*     */       
/* 237 */       System.out.println("New ticket is stored in cache file " + this.options.cachename);
/*     */     } else {
/*     */       
/* 240 */       Arrays.fill(this.options.password, '0');
/*     */     } 
/*     */ 
/*     */     
/* 244 */     if (arrayOfChar != null) {
/* 245 */       Arrays.fill(arrayOfChar, '0');
/*     */     }
/* 247 */     this.options = null;
/*     */   }
/*     */   
/*     */   private static void setOptions(int paramInt1, int paramInt2, KDCOptions paramKDCOptions) {
/* 251 */     switch (paramInt2) {
/*     */ 
/*     */       
/*     */       case -1:
/* 255 */         paramKDCOptions.set(paramInt1, false);
/*     */         break;
/*     */       case 1:
/* 258 */         paramKDCOptions.set(paramInt1, true);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\tools\Kinit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */