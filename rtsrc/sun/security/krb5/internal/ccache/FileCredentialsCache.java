/*     */ package sun.security.krb5.internal.ccache;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.krb5.Asn1Exception;
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
/*     */ public class FileCredentialsCache
/*     */   extends CredentialsCache
/*     */   implements FileCCacheConstants
/*     */ {
/*     */   public int version;
/*     */   public Tag tag;
/*     */   public PrincipalName primaryPrincipal;
/*     */   private Vector<Credentials> credentialsList;
/*     */   private static String dir;
/*  64 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */   
/*     */   public static synchronized FileCredentialsCache acquireInstance(PrincipalName paramPrincipalName, String paramString) {
/*     */     try {
/*  69 */       FileCredentialsCache fileCredentialsCache = new FileCredentialsCache();
/*  70 */       if (paramString == null) {
/*  71 */         cacheName = getDefaultCacheName();
/*     */       } else {
/*  73 */         cacheName = checkValidation(paramString);
/*     */       } 
/*  75 */       if (cacheName == null || !(new File(cacheName)).exists())
/*     */       {
/*  77 */         return null;
/*     */       }
/*  79 */       if (paramPrincipalName != null) {
/*  80 */         fileCredentialsCache.primaryPrincipal = paramPrincipalName;
/*     */       }
/*  82 */       fileCredentialsCache.load(cacheName);
/*  83 */       return fileCredentialsCache;
/*  84 */     } catch (IOException iOException) {
/*     */       
/*  86 */       if (DEBUG) {
/*  87 */         iOException.printStackTrace();
/*     */       }
/*  89 */     } catch (KrbException krbException) {
/*     */       
/*  91 */       if (DEBUG) {
/*  92 */         krbException.printStackTrace();
/*     */       }
/*     */     } 
/*  95 */     return null;
/*     */   }
/*     */   
/*     */   public static FileCredentialsCache acquireInstance() {
/*  99 */     return acquireInstance(null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   static synchronized FileCredentialsCache New(PrincipalName paramPrincipalName, String paramString) {
/*     */     try {
/* 105 */       FileCredentialsCache fileCredentialsCache = new FileCredentialsCache();
/* 106 */       cacheName = checkValidation(paramString);
/* 107 */       if (cacheName == null)
/*     */       {
/* 109 */         return null;
/*     */       }
/* 111 */       fileCredentialsCache.init(paramPrincipalName, cacheName);
/* 112 */       return fileCredentialsCache;
/*     */     }
/* 114 */     catch (IOException iOException) {
/*     */     
/* 116 */     } catch (KrbException krbException) {}
/*     */     
/* 118 */     return null;
/*     */   }
/*     */   
/*     */   static synchronized FileCredentialsCache New(PrincipalName paramPrincipalName) {
/*     */     try {
/* 123 */       FileCredentialsCache fileCredentialsCache = new FileCredentialsCache();
/* 124 */       cacheName = getDefaultCacheName();
/* 125 */       fileCredentialsCache.init(paramPrincipalName, cacheName);
/* 126 */       return fileCredentialsCache;
/*     */     }
/* 128 */     catch (IOException iOException) {
/* 129 */       if (DEBUG) {
/* 130 */         iOException.printStackTrace();
/*     */       }
/* 132 */     } catch (KrbException krbException) {
/* 133 */       if (DEBUG) {
/* 134 */         krbException.printStackTrace();
/*     */       }
/*     */     } 
/*     */     
/* 138 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean exists(String paramString) {
/* 145 */     File file = new File(paramString);
/* 146 */     if (file.exists())
/* 147 */       return true; 
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void init(PrincipalName paramPrincipalName, String paramString) throws IOException, KrbException {
/* 153 */     this.primaryPrincipal = paramPrincipalName;
/* 154 */     try(FileOutputStream null = new FileOutputStream(paramString); 
/* 155 */         CCacheOutputStream null = new CCacheOutputStream(fileOutputStream)) {
/* 156 */       this.version = 1283;
/* 157 */       cCacheOutputStream.writeHeader(this.primaryPrincipal, this.version);
/*     */     } 
/* 159 */     load(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void load(String paramString) throws IOException, KrbException {
/* 164 */     try(FileInputStream null = new FileInputStream(paramString); 
/* 165 */         CCacheInputStream null = new CCacheInputStream(fileInputStream)) {
/* 166 */       this.version = cCacheInputStream.readVersion();
/* 167 */       if (this.version == 1284) {
/* 168 */         this.tag = cCacheInputStream.readTag();
/*     */       } else {
/* 170 */         this.tag = null;
/* 171 */         if (this.version == 1281 || this.version == 1282) {
/* 172 */           cCacheInputStream.setNativeByteOrder();
/*     */         }
/*     */       } 
/* 175 */       PrincipalName principalName = cCacheInputStream.readPrincipal(this.version);
/*     */       
/* 177 */       if (this.primaryPrincipal != null) {
/* 178 */         if (!this.primaryPrincipal.match(principalName)) {
/* 179 */           throw new IOException("Primary principals don't match.");
/*     */         }
/*     */       } else {
/* 182 */         this.primaryPrincipal = principalName;
/* 183 */       }  this.credentialsList = new Vector<>();
/* 184 */       while (cCacheInputStream.available() > 0) {
/* 185 */         Credentials credentials = cCacheInputStream.readCred(this.version);
/* 186 */         if (credentials != null) {
/* 187 */           this.credentialsList.addElement(credentials);
/*     */         }
/*     */       } 
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
/*     */   public synchronized void update(Credentials paramCredentials) {
/* 202 */     if (this.credentialsList != null) {
/* 203 */       if (this.credentialsList.isEmpty()) {
/* 204 */         this.credentialsList.addElement(paramCredentials);
/*     */       } else {
/* 206 */         Credentials credentials = null;
/* 207 */         boolean bool = false;
/*     */         
/* 209 */         for (byte b = 0; b < this.credentialsList.size(); b++) {
/* 210 */           credentials = this.credentialsList.elementAt(b);
/* 211 */           if (match(paramCredentials.sname.getNameStrings(), credentials.sname
/* 212 */               .getNameStrings()) && paramCredentials.sname
/* 213 */             .getRealmString().equalsIgnoreCase(credentials.sname
/* 214 */               .getRealmString())) {
/* 215 */             bool = true;
/* 216 */             if (paramCredentials.endtime.getTime() >= credentials.endtime.getTime()) {
/* 217 */               if (DEBUG) {
/* 218 */                 System.out.println(" >>> FileCredentialsCache Ticket matched, overwrite the old one.");
/*     */               }
/*     */ 
/*     */               
/* 222 */               this.credentialsList.removeElementAt(b);
/* 223 */               this.credentialsList.addElement(paramCredentials);
/*     */             } 
/*     */           } 
/*     */         } 
/* 227 */         if (!bool) {
/* 228 */           if (DEBUG) {
/* 229 */             System.out.println(" >>> FileCredentialsCache Ticket not exactly matched, add new one into cache.");
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 234 */           this.credentialsList.addElement(paramCredentials);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized PrincipalName getPrimaryPrincipal() {
/* 241 */     return this.primaryPrincipal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void save() throws IOException, Asn1Exception {
/* 249 */     try(FileOutputStream null = new FileOutputStream(cacheName); 
/* 250 */         CCacheOutputStream null = new CCacheOutputStream(fileOutputStream)) {
/* 251 */       cCacheOutputStream.writeHeader(this.primaryPrincipal, this.version);
/* 252 */       Credentials[] arrayOfCredentials = null;
/* 253 */       if ((arrayOfCredentials = getCredsList()) != null) {
/* 254 */         for (byte b = 0; b < arrayOfCredentials.length; b++) {
/* 255 */           cCacheOutputStream.addCreds(arrayOfCredentials[b]);
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean match(String[] paramArrayOfString1, String[] paramArrayOfString2) {
/* 262 */     if (paramArrayOfString1.length != paramArrayOfString2.length) {
/* 263 */       return false;
/*     */     }
/* 265 */     for (byte b = 0; b < paramArrayOfString1.length; b++) {
/* 266 */       if (!paramArrayOfString1[b].equalsIgnoreCase(paramArrayOfString2[b])) {
/* 267 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 271 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Credentials[] getCredsList() {
/* 278 */     if (this.credentialsList == null || this.credentialsList.isEmpty()) {
/* 279 */       return null;
/*     */     }
/* 281 */     Credentials[] arrayOfCredentials = new Credentials[this.credentialsList.size()];
/* 282 */     for (byte b = 0; b < this.credentialsList.size(); b++) {
/* 283 */       arrayOfCredentials[b] = this.credentialsList.elementAt(b);
/*     */     }
/* 285 */     return arrayOfCredentials;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials getCreds(LoginOptions paramLoginOptions, PrincipalName paramPrincipalName) {
/* 291 */     if (paramLoginOptions == null) {
/* 292 */       return getCreds(paramPrincipalName);
/*     */     }
/* 294 */     Credentials[] arrayOfCredentials = getCredsList();
/* 295 */     if (arrayOfCredentials == null) {
/* 296 */       return null;
/*     */     }
/* 298 */     for (byte b = 0; b < arrayOfCredentials.length; b++) {
/* 299 */       if (paramPrincipalName.match((arrayOfCredentials[b]).sname) && 
/* 300 */         (arrayOfCredentials[b]).flags.match(paramLoginOptions)) {
/* 301 */         return arrayOfCredentials[b];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 306 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials getCreds(PrincipalName paramPrincipalName) {
/* 316 */     Credentials[] arrayOfCredentials = getCredsList();
/* 317 */     if (arrayOfCredentials == null) {
/* 318 */       return null;
/*     */     }
/* 320 */     for (byte b = 0; b < arrayOfCredentials.length; b++) {
/* 321 */       if (paramPrincipalName.match((arrayOfCredentials[b]).sname)) {
/* 322 */         return arrayOfCredentials[b];
/*     */       }
/*     */     } 
/*     */     
/* 326 */     return null;
/*     */   }
/*     */   
/*     */   public Credentials getDefaultCreds() {
/* 330 */     Credentials[] arrayOfCredentials = getCredsList();
/* 331 */     if (arrayOfCredentials == null) {
/* 332 */       return null;
/*     */     }
/* 334 */     for (int i = arrayOfCredentials.length - 1; i >= 0; i--) {
/* 335 */       if ((arrayOfCredentials[i]).sname.toString().startsWith("krbtgt")) {
/* 336 */         String[] arrayOfString = (arrayOfCredentials[i]).sname.getNameStrings();
/*     */         
/* 338 */         if (arrayOfString[1].equals((arrayOfCredentials[i]).sname.getRealm().toString())) {
/* 339 */           return arrayOfCredentials[i];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 344 */     return null;
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
/*     */   public static String getDefaultCacheName() {
/* 359 */     String str1 = "krb5cc";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 364 */     String str2 = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run()
/*     */           {
/* 368 */             String str = System.getenv("KRB5CCNAME");
/* 369 */             if (str != null && str
/* 370 */               .length() >= 5 && str
/* 371 */               .regionMatches(true, 0, "FILE:", 0, 5)) {
/* 372 */               str = str.substring(5);
/*     */             }
/* 374 */             return str;
/*     */           }
/*     */         });
/* 377 */     if (str2 != null) {
/* 378 */       if (DEBUG) {
/* 379 */         System.out.println(">>>KinitOptions cache name is " + str2);
/*     */       }
/* 381 */       return str2;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 386 */     String str3 = AccessController.<String>doPrivileged(new GetPropertyAction("os.name"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 400 */     if (str3 != null) {
/* 401 */       Object object1 = null;
/* 402 */       Object object2 = null;
/* 403 */       long l = 0L;
/*     */       
/* 405 */       if (!str3.startsWith("Windows")) {
/*     */         
/*     */         try {
/* 408 */           Class<?> clazz = Class.forName("com.sun.security.auth.module.UnixSystem");
/* 409 */           Constructor<?> constructor = clazz.getConstructor(new Class[0]);
/* 410 */           Object object = constructor.newInstance(new Object[0]);
/* 411 */           Method method = clazz.getMethod("getUid", new Class[0]);
/* 412 */           l = ((Long)method.invoke(object, new Object[0])).longValue();
/* 413 */           str2 = File.separator + "tmp" + File.separator + str1 + "_" + l;
/*     */           
/* 415 */           if (DEBUG) {
/* 416 */             System.out.println(">>>KinitOptions cache name is " + str2);
/*     */           }
/*     */           
/* 419 */           return str2;
/* 420 */         } catch (Exception exception) {
/* 421 */           if (DEBUG) {
/* 422 */             System.out.println("Exception in obtaining uid for Unix platforms Using user's home directory");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 427 */             exception.printStackTrace();
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 437 */     String str4 = AccessController.<String>doPrivileged(new GetPropertyAction("user.name"));
/*     */ 
/*     */ 
/*     */     
/* 441 */     String str5 = AccessController.<String>doPrivileged(new GetPropertyAction("user.home"));
/*     */ 
/*     */     
/* 444 */     if (str5 == null)
/*     */     {
/* 446 */       str5 = AccessController.<String>doPrivileged(new GetPropertyAction("user.dir"));
/*     */     }
/*     */ 
/*     */     
/* 450 */     if (str4 != null) {
/* 451 */       str2 = str5 + File.separator + str1 + "_" + str4;
/*     */     } else {
/*     */       
/* 454 */       str2 = str5 + File.separator + str1;
/*     */     } 
/*     */     
/* 457 */     if (DEBUG) {
/* 458 */       System.out.println(">>>KinitOptions cache name is " + str2);
/*     */     }
/*     */     
/* 461 */     return str2;
/*     */   }
/*     */   
/*     */   public static String checkValidation(String paramString) {
/* 465 */     String str = null;
/* 466 */     if (paramString == null) {
/* 467 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 471 */       str = (new File(paramString)).getCanonicalPath();
/* 472 */       File file = new File(str);
/* 473 */       if (!file.exists()) {
/*     */         
/* 475 */         File file1 = new File(file.getParent());
/*     */         
/* 477 */         if (!file1.isDirectory())
/* 478 */           str = null; 
/* 479 */         file1 = null;
/*     */       } 
/* 481 */       file = null;
/*     */     }
/* 483 */     catch (IOException iOException) {
/* 484 */       str = null;
/*     */     } 
/* 486 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String exec(String paramString) {
/* 491 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString);
/* 492 */     Vector<String> vector = new Vector();
/* 493 */     while (stringTokenizer.hasMoreTokens()) {
/* 494 */       vector.addElement(stringTokenizer.nextToken());
/*     */     }
/* 496 */     final String[] command = new String[vector.size()];
/* 497 */     vector.copyInto((Object[])arrayOfString);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 502 */       Process process = AccessController.<Process>doPrivileged(new PrivilegedAction<Process>() {
/*     */             public Process run() {
/*     */               try {
/* 505 */                 return Runtime.getRuntime().exec(command);
/* 506 */               } catch (IOException iOException) {
/* 507 */                 if (FileCredentialsCache.DEBUG) {
/* 508 */                   iOException.printStackTrace();
/*     */                 }
/* 510 */                 return null;
/*     */               } 
/*     */             }
/*     */           });
/* 514 */       if (process == null)
/*     */       {
/* 516 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 521 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "8859_1"));
/* 522 */       String str = null;
/* 523 */       if (arrayOfString.length == 1 && arrayOfString[0]
/* 524 */         .equals("/usr/bin/env")) {
/* 525 */         while ((str = bufferedReader.readLine()) != null) {
/* 526 */           if (str.length() >= 11 && 
/* 527 */             str.substring(0, 11)
/* 528 */             .equalsIgnoreCase("KRB5CCNAME=")) {
/* 529 */             str = str.substring(11);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 534 */         str = bufferedReader.readLine();
/* 535 */       }  bufferedReader.close();
/* 536 */       return str;
/* 537 */     } catch (Exception exception) {
/* 538 */       if (DEBUG) {
/* 539 */         exception.printStackTrace();
/*     */       }
/*     */       
/* 542 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\ccache\FileCredentialsCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */