/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.util.LinkedList;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.security.krb5.internal.util.KerberosString;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
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
/*     */ public class Realm
/*     */   implements Cloneable
/*     */ {
/*  52 */   public static final boolean AUTODEDUCEREALM = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.security.krb5.autodeducerealm"))).booleanValue();
/*     */ 
/*     */   
/*     */   private final String realm;
/*     */ 
/*     */   
/*     */   public Realm(String paramString) throws RealmException {
/*  59 */     this.realm = parseRealm(paramString);
/*     */   }
/*     */   
/*     */   public static Realm getDefault() throws RealmException {
/*     */     try {
/*  64 */       return new Realm(Config.getInstance().getDefaultRealm());
/*  65 */     } catch (RealmException realmException) {
/*  66 */       throw realmException;
/*  67 */     } catch (KrbException krbException) {
/*  68 */       throw new RealmException(krbException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object clone() {
/*  74 */     return this;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  78 */     if (this == paramObject) {
/*  79 */       return true;
/*     */     }
/*     */     
/*  82 */     if (!(paramObject instanceof Realm)) {
/*  83 */       return false;
/*     */     }
/*     */     
/*  86 */     Realm realm = (Realm)paramObject;
/*  87 */     return this.realm.equals(realm.realm);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  91 */     return this.realm.hashCode();
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
/*     */   public Realm(DerValue paramDerValue) throws Asn1Exception, RealmException, IOException {
/* 103 */     if (paramDerValue == null) {
/* 104 */       throw new IllegalArgumentException("encoding can not be null");
/*     */     }
/* 106 */     this.realm = (new KerberosString(paramDerValue)).toString();
/* 107 */     if (this.realm == null || this.realm.length() == 0)
/* 108 */       throw new RealmException(601); 
/* 109 */     if (!isValidRealmString(this.realm))
/* 110 */       throw new RealmException(600); 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 114 */     return this.realm;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String parseRealmAtSeparator(String paramString) throws RealmException {
/* 120 */     if (paramString == null) {
/* 121 */       throw new IllegalArgumentException("null input name is not allowed");
/*     */     }
/*     */     
/* 124 */     String str1 = new String(paramString);
/* 125 */     String str2 = null;
/* 126 */     byte b = 0;
/* 127 */     while (b < str1.length()) {
/* 128 */       if (str1.charAt(b) == '@' && (
/* 129 */         b == 0 || str1.charAt(b - 1) != '\\')) {
/* 130 */         if (b + 1 < str1.length()) {
/* 131 */           str2 = str1.substring(b + 1, str1.length()); break;
/*     */         } 
/* 133 */         throw new IllegalArgumentException("empty realm part not allowed");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       b++;
/*     */     } 
/* 141 */     if (str2 != null) {
/* 142 */       if (str2.length() == 0)
/* 143 */         throw new RealmException(601); 
/* 144 */       if (!isValidRealmString(str2))
/* 145 */         throw new RealmException(600); 
/*     */     } 
/* 147 */     return str2;
/*     */   }
/*     */   
/*     */   public static String parseRealmComponent(String paramString) {
/* 151 */     if (paramString == null) {
/* 152 */       throw new IllegalArgumentException("null input name is not allowed");
/*     */     }
/*     */     
/* 155 */     String str1 = new String(paramString);
/* 156 */     String str2 = null;
/* 157 */     byte b = 0;
/* 158 */     while (b < str1.length()) {
/* 159 */       if (str1.charAt(b) == '.' && (
/* 160 */         b == 0 || str1.charAt(b - 1) != '\\')) {
/* 161 */         if (b + 1 < str1.length()) {
/* 162 */           str2 = str1.substring(b + 1, str1.length());
/*     */         }
/*     */         break;
/*     */       } 
/* 166 */       b++;
/*     */     } 
/* 168 */     return str2;
/*     */   }
/*     */   
/*     */   protected static String parseRealm(String paramString) throws RealmException {
/* 172 */     String str = parseRealmAtSeparator(paramString);
/* 173 */     if (str == null)
/* 174 */       str = paramString; 
/* 175 */     if (str == null || str.length() == 0)
/* 176 */       throw new RealmException(601); 
/* 177 */     if (!isValidRealmString(str))
/* 178 */       throw new RealmException(600); 
/* 179 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isValidRealmString(String paramString) {
/* 185 */     if (paramString == null)
/* 186 */       return false; 
/* 187 */     if (paramString.length() == 0)
/* 188 */       return false; 
/* 189 */     for (byte b = 0; b < paramString.length(); b++) {
/* 190 */       if (paramString.charAt(b) == '/' || paramString
/* 191 */         .charAt(b) == ':' || paramString
/* 192 */         .charAt(b) == '\000') {
/* 193 */         return false;
/*     */       }
/*     */     } 
/* 196 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 207 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 208 */     derOutputStream.putDerValue((new KerberosString(this.realm)).toDerValue());
/* 209 */     return derOutputStream.toByteArray();
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
/*     */   public static Realm parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException, RealmException {
/* 227 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte) {
/* 228 */       return null;
/*     */     }
/* 230 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 231 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 232 */       throw new Asn1Exception(906);
/*     */     }
/* 234 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 235 */     return new Realm(derValue2);
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
/*     */   public static String[] getRealmsList(String paramString1, String paramString2) {
/*     */     try {
/* 259 */       return parseCapaths(paramString1, paramString2);
/* 260 */     } catch (KrbException krbException) {
/*     */       
/* 262 */       return parseHierarchy(paramString1, paramString2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] parseCapaths(String paramString1, String paramString2) throws KrbException {
/* 316 */     Config config = Config.getInstance();
/*     */     
/* 318 */     if (!config.exists(new String[] { "capaths", paramString1, paramString2 })) {
/* 319 */       throw new KrbException("No conf");
/*     */     }
/*     */     
/* 322 */     LinkedList<String> linkedList = new LinkedList();
/*     */     
/* 324 */     String str = paramString2;
/*     */     while (true) {
/* 326 */       String str1 = config.getAll(new String[] { "capaths", paramString1, str });
/* 327 */       if (str1 == null) {
/*     */         break;
/*     */       }
/* 330 */       String[] arrayOfString = str1.split("\\s+");
/* 331 */       boolean bool = false;
/* 332 */       for (int i = arrayOfString.length - 1; i >= 0; i--) {
/* 333 */         if (!linkedList.contains(arrayOfString[i]) && 
/* 334 */           !arrayOfString[i].equals(".") && 
/* 335 */           !arrayOfString[i].equals(paramString1) && 
/* 336 */           !arrayOfString[i].equals(paramString2) && 
/* 337 */           !arrayOfString[i].equals(str)) {
/*     */ 
/*     */ 
/*     */           
/* 341 */           bool = true;
/* 342 */           linkedList.addFirst(arrayOfString[i]);
/*     */         } 
/* 344 */       }  if (!bool)
/* 345 */         break;  str = linkedList.getFirst();
/*     */     } 
/* 347 */     linkedList.addFirst(paramString1);
/* 348 */     return linkedList.<String>toArray(new String[linkedList.size()]);
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
/*     */   private static String[] parseHierarchy(String paramString1, String paramString2) {
/* 361 */     String[] arrayOfString1 = paramString1.split("\\.");
/* 362 */     String[] arrayOfString2 = paramString2.split("\\.");
/*     */     
/* 364 */     int i = arrayOfString1.length;
/* 365 */     int j = arrayOfString2.length;
/*     */     
/* 367 */     boolean bool = false;
/* 368 */     for (; j >= 0 && --i >= 0 && arrayOfString2[--j]
/* 369 */       .equals(arrayOfString1[i]); 
/* 370 */       j--, i--) {
/* 371 */       bool = true;
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
/* 384 */     LinkedList<String> linkedList = new LinkedList();
/*     */     
/*     */     int k;
/* 387 */     for (k = 0; k <= i; k++) {
/* 388 */       linkedList.addLast(subStringFrom(arrayOfString1, k));
/*     */     }
/*     */ 
/*     */     
/* 392 */     if (bool) {
/* 393 */       linkedList.addLast(subStringFrom(arrayOfString1, i + 1));
/*     */     }
/*     */ 
/*     */     
/* 397 */     for (k = j; k >= 0; k--) {
/* 398 */       linkedList.addLast(subStringFrom(arrayOfString2, k));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 403 */     linkedList.removeLast();
/*     */     
/* 405 */     return linkedList.<String>toArray(new String[linkedList.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String subStringFrom(String[] paramArrayOfString, int paramInt) {
/* 413 */     StringBuilder stringBuilder = new StringBuilder();
/* 414 */     for (int i = paramInt; i < paramArrayOfString.length; i++) {
/* 415 */       if (stringBuilder.length() != 0) stringBuilder.append('.'); 
/* 416 */       stringBuilder.append(paramArrayOfString[i]);
/*     */     } 
/* 418 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\Realm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */