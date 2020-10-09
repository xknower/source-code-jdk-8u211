/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.security.krb5.internal.ccache.CCacheOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrincipalName
/*     */   implements Cloneable
/*     */ {
/*     */   public static final int KRB_NT_UNKNOWN = 0;
/*     */   public static final int KRB_NT_PRINCIPAL = 1;
/*     */   public static final int KRB_NT_SRV_INST = 2;
/*     */   public static final int KRB_NT_SRV_HST = 3;
/*     */   public static final int KRB_NT_SRV_XHST = 4;
/*     */   public static final int KRB_NT_UID = 5;
/*     */   public static final String TGS_DEFAULT_SRV_NAME = "krbtgt";
/*     */   public static final int TGS_DEFAULT_NT = 2;
/*     */   public static final char NAME_COMPONENT_SEPARATOR = '/';
/*     */   public static final char NAME_REALM_SEPARATOR = '@';
/*     */   public static final char REALM_COMPONENT_SEPARATOR = '.';
/*     */   public static final String NAME_COMPONENT_SEPARATOR_STR = "/";
/*     */   public static final String NAME_REALM_SEPARATOR_STR = "@";
/*     */   public static final String REALM_COMPONENT_SEPARATOR_STR = ".";
/*     */   private final int nameType;
/*     */   private final String[] nameStrings;
/*     */   private final Realm nameRealm;
/*     */   private final boolean realmDeduced;
/* 134 */   private transient String salt = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long NAME_STRINGS_OFFSET;
/*     */ 
/*     */   
/*     */   private static final Unsafe UNSAFE;
/*     */ 
/*     */ 
/*     */   
/*     */   public PrincipalName(int paramInt, String[] paramArrayOfString, Realm paramRealm) {
/* 146 */     if (paramRealm == null) {
/* 147 */       throw new IllegalArgumentException("Null realm not allowed");
/*     */     }
/* 149 */     validateNameStrings(paramArrayOfString);
/* 150 */     this.nameType = paramInt;
/* 151 */     this.nameStrings = (String[])paramArrayOfString.clone();
/* 152 */     this.nameRealm = paramRealm;
/* 153 */     this.realmDeduced = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public PrincipalName(String[] paramArrayOfString, String paramString) throws RealmException {
/* 158 */     this(0, paramArrayOfString, new Realm(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validateNameStrings(String[] paramArrayOfString) {
/* 163 */     if (paramArrayOfString == null) {
/* 164 */       throw new IllegalArgumentException("Null nameStrings not allowed");
/*     */     }
/* 166 */     if (paramArrayOfString.length == 0) {
/* 167 */       throw new IllegalArgumentException("Empty nameStrings not allowed");
/*     */     }
/* 169 */     for (String str : paramArrayOfString) {
/* 170 */       if (str == null) {
/* 171 */         throw new IllegalArgumentException("Null nameString not allowed");
/*     */       }
/* 173 */       if (str.isEmpty()) {
/* 174 */         throw new IllegalArgumentException("Empty nameString not allowed");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 181 */       PrincipalName principalName = (PrincipalName)super.clone();
/* 182 */       UNSAFE.putObject(this, NAME_STRINGS_OFFSET, this.nameStrings.clone());
/* 183 */       return principalName;
/* 184 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 185 */       throw new AssertionError("Should never happen");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 193 */       Unsafe unsafe = Unsafe.getUnsafe();
/* 194 */       NAME_STRINGS_OFFSET = unsafe.objectFieldOffset(PrincipalName.class
/* 195 */           .getDeclaredField("nameStrings"));
/* 196 */       UNSAFE = unsafe;
/* 197 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 198 */       throw new Error(reflectiveOperationException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 204 */     if (this == paramObject) {
/* 205 */       return true;
/*     */     }
/* 207 */     if (paramObject instanceof PrincipalName) {
/* 208 */       PrincipalName principalName = (PrincipalName)paramObject;
/* 209 */       return (this.nameRealm.equals(principalName.nameRealm) && 
/* 210 */         Arrays.equals((Object[])this.nameStrings, (Object[])principalName.nameStrings));
/*     */     } 
/* 212 */     return false;
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
/*     */   public PrincipalName(DerValue paramDerValue, Realm paramRealm) throws Asn1Exception, IOException {
/* 243 */     if (paramRealm == null) {
/* 244 */       throw new IllegalArgumentException("Null realm not allowed");
/*     */     }
/* 246 */     this.realmDeduced = false;
/* 247 */     this.nameRealm = paramRealm;
/*     */     
/* 249 */     if (paramDerValue == null) {
/* 250 */       throw new IllegalArgumentException("Null encoding not allowed");
/*     */     }
/* 252 */     if (paramDerValue.getTag() != 48) {
/* 253 */       throw new Asn1Exception(906);
/*     */     }
/* 255 */     DerValue derValue = paramDerValue.getData().getDerValue();
/* 256 */     if ((derValue.getTag() & 0x1F) == 0) {
/* 257 */       BigInteger bigInteger = derValue.getData().getBigInteger();
/* 258 */       this.nameType = bigInteger.intValue();
/*     */     } else {
/* 260 */       throw new Asn1Exception(906);
/*     */     } 
/* 262 */     derValue = paramDerValue.getData().getDerValue();
/* 263 */     if ((derValue.getTag() & 0x1F) == 1) {
/* 264 */       DerValue derValue1 = derValue.getData().getDerValue();
/* 265 */       if (derValue1.getTag() != 48) {
/* 266 */         throw new Asn1Exception(906);
/*     */       }
/* 268 */       Vector<String> vector = new Vector();
/*     */       
/* 270 */       while (derValue1.getData().available() > 0) {
/* 271 */         DerValue derValue2 = derValue1.getData().getDerValue();
/* 272 */         String str = (new KerberosString(derValue2)).toString();
/* 273 */         vector.addElement(str);
/*     */       } 
/* 275 */       this.nameStrings = new String[vector.size()];
/* 276 */       vector.copyInto((Object[])this.nameStrings);
/* 277 */       validateNameStrings(this.nameStrings);
/*     */     } else {
/* 279 */       throw new Asn1Exception(906);
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
/*     */   public static PrincipalName parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean, Realm paramRealm) throws Asn1Exception, IOException, RealmException {
/* 304 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/*     */     {
/* 306 */       return null; } 
/* 307 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 308 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 309 */       throw new Asn1Exception(906);
/*     */     }
/* 311 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 312 */     if (paramRealm == null) {
/* 313 */       paramRealm = Realm.getDefault();
/*     */     }
/* 315 */     return new PrincipalName(derValue2, paramRealm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] parseName(String paramString) {
/* 324 */     Vector<String> vector = new Vector();
/* 325 */     String str = paramString;
/* 326 */     byte b = 0;
/* 327 */     int i = 0;
/*     */ 
/*     */     
/* 330 */     while (b < str.length()) {
/* 331 */       if (str.charAt(b) == '/') {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 336 */         if (b > 0 && str.charAt(b - 1) == '\\') {
/*     */           
/* 338 */           str = str.substring(0, b - 1) + str.substring(b, str.length());
/*     */           
/*     */           continue;
/*     */         } 
/* 342 */         if (i <= b) {
/* 343 */           String str1 = str.substring(i, b);
/* 344 */           vector.addElement(str1);
/*     */         } 
/* 346 */         i = b + 1;
/*     */       
/*     */       }
/* 349 */       else if (str.charAt(b) == '@') {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 354 */         if (b > 0 && str.charAt(b - 1) == '\\') {
/*     */           
/* 356 */           str = str.substring(0, b - 1) + str.substring(b, str.length());
/*     */           continue;
/*     */         } 
/* 359 */         if (i < b) {
/* 360 */           String str1 = str.substring(i, b);
/* 361 */           vector.addElement(str1);
/*     */         } 
/* 363 */         i = b + 1;
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 368 */       b++;
/*     */     } 
/*     */     
/* 371 */     if (b == str.length()) {
/* 372 */       String str1 = str.substring(i, b);
/* 373 */       vector.addElement(str1);
/*     */     } 
/*     */     
/* 376 */     String[] arrayOfString = new String[vector.size()];
/* 377 */     vector.copyInto((Object[])arrayOfString);
/* 378 */     return arrayOfString;
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
/*     */   public PrincipalName(String paramString1, int paramInt, String paramString2) throws RealmException {
/* 393 */     if (paramString1 == null) {
/* 394 */       throw new IllegalArgumentException("Null name not allowed");
/*     */     }
/* 396 */     String[] arrayOfString = parseName(paramString1);
/* 397 */     validateNameStrings(arrayOfString);
/* 398 */     if (paramString2 == null) {
/* 399 */       paramString2 = Realm.parseRealmAtSeparator(paramString1);
/*     */     }
/*     */ 
/*     */     
/* 403 */     this.realmDeduced = (paramString2 == null);
/*     */     
/* 405 */     switch (paramInt) {
/*     */       case 3:
/* 407 */         if (arrayOfString.length >= 2) {
/* 408 */           String str = arrayOfString[1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 415 */             String str1 = InetAddress.getByName(str).getCanonicalHostName();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 420 */             if (str1.toLowerCase(Locale.ENGLISH).startsWith(str
/* 421 */                 .toLowerCase(Locale.ENGLISH) + ".")) {
/* 422 */               str = str1;
/*     */             }
/* 424 */           } catch (UnknownHostException|SecurityException unknownHostException) {}
/*     */ 
/*     */           
/* 427 */           if (str.endsWith(".")) {
/* 428 */             str = str.substring(0, str.length() - 1);
/*     */           }
/* 430 */           arrayOfString[1] = str.toLowerCase(Locale.ENGLISH);
/*     */         } 
/* 432 */         this.nameStrings = arrayOfString;
/* 433 */         this.nameType = paramInt;
/*     */         
/* 435 */         if (paramString2 != null) {
/* 436 */           this.nameRealm = new Realm(paramString2);
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */           
/* 444 */           String str = mapHostToRealm(arrayOfString[1]);
/* 445 */           if (str != null) {
/* 446 */             this.nameRealm = new Realm(str);
/*     */           } else {
/* 448 */             this.nameRealm = Realm.getDefault();
/*     */           } 
/*     */         } 
/*     */         return;
/*     */       case 0:
/*     */       case 1:
/*     */       case 2:
/*     */       case 4:
/*     */       case 5:
/* 457 */         this.nameStrings = arrayOfString;
/* 458 */         this.nameType = paramInt;
/* 459 */         if (paramString2 != null) {
/* 460 */           this.nameRealm = new Realm(paramString2);
/*     */         } else {
/* 462 */           this.nameRealm = Realm.getDefault();
/*     */         } 
/*     */         return;
/*     */     } 
/* 466 */     throw new IllegalArgumentException("Illegal name type");
/*     */   }
/*     */ 
/*     */   
/*     */   public PrincipalName(String paramString, int paramInt) throws RealmException {
/* 471 */     this(paramString, paramInt, (String)null);
/*     */   }
/*     */   
/*     */   public PrincipalName(String paramString) throws RealmException {
/* 475 */     this(paramString, 0);
/*     */   }
/*     */   
/*     */   public PrincipalName(String paramString1, String paramString2) throws RealmException {
/* 479 */     this(paramString1, 0, paramString2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PrincipalName tgsService(String paramString1, String paramString2) throws KrbException {
/* 484 */     return new PrincipalName(2, new String[] { "krbtgt", paramString1 }, new Realm(paramString2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRealmAsString() {
/* 490 */     return getRealmString();
/*     */   }
/*     */   
/*     */   public String getPrincipalNameAsString() {
/* 494 */     StringBuffer stringBuffer = new StringBuffer(this.nameStrings[0]);
/* 495 */     for (byte b = 1; b < this.nameStrings.length; b++)
/* 496 */       stringBuffer.append(this.nameStrings[b]); 
/* 497 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 501 */     return toString().hashCode();
/*     */   }
/*     */   
/*     */   public String getName() {
/* 505 */     return toString();
/*     */   }
/*     */   
/*     */   public int getNameType() {
/* 509 */     return this.nameType;
/*     */   }
/*     */   
/*     */   public String[] getNameStrings() {
/* 513 */     return (String[])this.nameStrings.clone();
/*     */   }
/*     */   
/*     */   public byte[][] toByteArray() {
/* 517 */     byte[][] arrayOfByte = new byte[this.nameStrings.length][];
/* 518 */     for (byte b = 0; b < this.nameStrings.length; b++) {
/* 519 */       arrayOfByte[b] = new byte[this.nameStrings[b].length()];
/* 520 */       arrayOfByte[b] = this.nameStrings[b].getBytes();
/*     */     } 
/* 522 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public String getRealmString() {
/* 526 */     return this.nameRealm.toString();
/*     */   }
/*     */   
/*     */   public Realm getRealm() {
/* 530 */     return this.nameRealm;
/*     */   }
/*     */   
/*     */   public String getSalt() {
/* 534 */     if (this.salt == null) {
/* 535 */       StringBuffer stringBuffer = new StringBuffer();
/* 536 */       stringBuffer.append(this.nameRealm.toString());
/* 537 */       for (byte b = 0; b < this.nameStrings.length; b++) {
/* 538 */         stringBuffer.append(this.nameStrings[b]);
/*     */       }
/* 540 */       return stringBuffer.toString();
/*     */     } 
/* 542 */     return this.salt;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 546 */     StringBuffer stringBuffer = new StringBuffer();
/* 547 */     for (byte b = 0; b < this.nameStrings.length; b++) {
/* 548 */       if (b > 0)
/* 549 */         stringBuffer.append("/"); 
/* 550 */       stringBuffer.append(this.nameStrings[b]);
/*     */     } 
/* 552 */     stringBuffer.append("@");
/* 553 */     stringBuffer.append(this.nameRealm.toString());
/* 554 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public String getNameString() {
/* 558 */     StringBuffer stringBuffer = new StringBuffer();
/* 559 */     for (byte b = 0; b < this.nameStrings.length; b++) {
/* 560 */       if (b > 0)
/* 561 */         stringBuffer.append("/"); 
/* 562 */       stringBuffer.append(this.nameStrings[b]);
/*     */     } 
/* 564 */     return stringBuffer.toString();
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
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 576 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 577 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 578 */     BigInteger bigInteger = BigInteger.valueOf(this.nameType);
/* 579 */     derOutputStream2.putInteger(bigInteger);
/* 580 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/* 581 */     derOutputStream2 = new DerOutputStream();
/* 582 */     DerValue[] arrayOfDerValue = new DerValue[this.nameStrings.length];
/* 583 */     for (byte b = 0; b < this.nameStrings.length; b++) {
/* 584 */       arrayOfDerValue[b] = (new KerberosString(this.nameStrings[b])).toDerValue();
/*     */     }
/* 586 */     derOutputStream2.putSequence(arrayOfDerValue);
/* 587 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/* 588 */     derOutputStream2 = new DerOutputStream();
/* 589 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 590 */     return derOutputStream2.toByteArray();
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
/*     */   public boolean match(PrincipalName paramPrincipalName) {
/* 602 */     boolean bool = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 607 */     if (this.nameRealm != null && paramPrincipalName.nameRealm != null && 
/* 608 */       !this.nameRealm.toString().equalsIgnoreCase(paramPrincipalName.nameRealm.toString())) {
/* 609 */       bool = false;
/*     */     }
/*     */     
/* 612 */     if (this.nameStrings.length != paramPrincipalName.nameStrings.length) {
/* 613 */       bool = false;
/*     */     } else {
/* 615 */       for (byte b = 0; b < this.nameStrings.length; b++) {
/* 616 */         if (!this.nameStrings[b].equalsIgnoreCase(paramPrincipalName.nameStrings[b])) {
/* 617 */           bool = false;
/*     */         }
/*     */       } 
/*     */     } 
/* 621 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePrincipal(CCacheOutputStream paramCCacheOutputStream) throws IOException {
/* 632 */     paramCCacheOutputStream.write32(this.nameType);
/* 633 */     paramCCacheOutputStream.write32(this.nameStrings.length);
/* 634 */     byte[] arrayOfByte1 = null;
/* 635 */     arrayOfByte1 = this.nameRealm.toString().getBytes();
/* 636 */     paramCCacheOutputStream.write32(arrayOfByte1.length);
/* 637 */     paramCCacheOutputStream.write(arrayOfByte1, 0, arrayOfByte1.length);
/* 638 */     byte[] arrayOfByte2 = null;
/* 639 */     for (byte b = 0; b < this.nameStrings.length; b++) {
/* 640 */       arrayOfByte2 = this.nameStrings[b].getBytes();
/* 641 */       paramCCacheOutputStream.write32(arrayOfByte2.length);
/* 642 */       paramCCacheOutputStream.write(arrayOfByte2, 0, arrayOfByte2.length);
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
/*     */   public String getInstanceComponent() {
/* 656 */     if (this.nameStrings != null && this.nameStrings.length >= 2)
/*     */     {
/* 658 */       return new String(this.nameStrings[1]);
/*     */     }
/*     */     
/* 661 */     return null;
/*     */   }
/*     */   
/*     */   static String mapHostToRealm(String paramString) {
/* 665 */     String str = null;
/*     */     try {
/* 667 */       String str1 = null;
/* 668 */       Config config = Config.getInstance();
/* 669 */       if ((str = config.get(new String[] { "domain_realm", paramString })) != null) {
/* 670 */         return str;
/*     */       }
/* 672 */       for (byte b = 1; b < paramString.length(); b++) {
/* 673 */         if (paramString.charAt(b) == '.' && b != paramString.length() - 1) {
/* 674 */           str1 = paramString.substring(b);
/* 675 */           str = config.get(new String[] { "domain_realm", str1 });
/* 676 */           if (str != null) {
/*     */             break;
/*     */           }
/*     */           
/* 680 */           str1 = paramString.substring(b + 1);
/* 681 */           str = config.get(new String[] { "domain_realm", str1 });
/* 682 */           if (str != null) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 689 */     } catch (KrbException krbException) {}
/*     */     
/* 691 */     return str;
/*     */   }
/*     */   
/*     */   public boolean isRealmDeduced() {
/* 695 */     return this.realmDeduced;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\PrincipalName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */