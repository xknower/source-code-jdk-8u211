/*     */ package sun.security.krb5.internal.ccache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.StringTokenizer;
/*     */ import sun.misc.IOUtils;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.Realm;
/*     */ import sun.security.krb5.RealmException;
/*     */ import sun.security.krb5.internal.AuthorizationData;
/*     */ import sun.security.krb5.internal.AuthorizationDataEntry;
/*     */ import sun.security.krb5.internal.HostAddress;
/*     */ import sun.security.krb5.internal.HostAddresses;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.KrbApErrException;
/*     */ import sun.security.krb5.internal.Ticket;
/*     */ import sun.security.krb5.internal.TicketFlags;
/*     */ import sun.security.krb5.internal.util.KrbDataInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CCacheInputStream
/*     */   extends KrbDataInputStream
/*     */   implements FileCCacheConstants
/*     */ {
/*  69 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */   
/*     */   public CCacheInputStream(InputStream paramInputStream) {
/*  72 */     super(paramInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Tag readTag() throws IOException {
/*  78 */     char[] arrayOfChar = new char[1024];
/*     */     
/*  80 */     int j = -1;
/*     */     
/*  82 */     Integer integer1 = null;
/*  83 */     Integer integer2 = null;
/*     */     
/*  85 */     int i = read(2);
/*  86 */     if (i < 0) {
/*  87 */       throw new IOException("stop.");
/*     */     }
/*  89 */     if (i > arrayOfChar.length) {
/*  90 */       throw new IOException("Invalid tag length.");
/*     */     }
/*  92 */     while (i > 0) {
/*  93 */       j = read(2);
/*  94 */       int k = read(2);
/*  95 */       switch (j) {
/*     */         case 1:
/*  97 */           integer1 = new Integer(read(4));
/*  98 */           integer2 = new Integer(read(4));
/*     */           break;
/*     */       } 
/*     */       
/* 102 */       i -= 4 + k;
/*     */     } 
/* 104 */     return new Tag(i, j, integer1, integer2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrincipalName readPrincipal(int paramInt) throws IOException, RealmException {
/*     */     int i;
/* 113 */     Object object = null;
/*     */ 
/*     */     
/* 116 */     if (paramInt == 1281) {
/* 117 */       i = 0;
/*     */     } else {
/* 119 */       i = read(4);
/*     */     } 
/* 121 */     int j = readLength4();
/* 122 */     ArrayList<String> arrayList = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     if (paramInt == 1281)
/* 128 */       j--; 
/* 129 */     for (byte b = 0; b <= j; b++) {
/* 130 */       int k = readLength4();
/* 131 */       byte[] arrayOfByte = IOUtils.readFully(this, k, true);
/* 132 */       arrayList.add(new String(arrayOfByte));
/*     */     } 
/* 134 */     if (arrayList.isEmpty()) {
/* 135 */       throw new IOException("No realm or principal");
/*     */     }
/* 137 */     if (isRealm(arrayList.get(0))) {
/* 138 */       String str = arrayList.remove(0);
/* 139 */       if (arrayList.isEmpty()) {
/* 140 */         throw new IOException("No principal name components");
/*     */       }
/* 142 */       return new PrincipalName(i, arrayList
/*     */           
/* 144 */           .<String>toArray(new String[arrayList.size()]), new Realm(str));
/*     */     } 
/*     */     
/*     */     try {
/* 148 */       return new PrincipalName(i, arrayList
/*     */           
/* 150 */           .<String>toArray(new String[arrayList.size()]), 
/* 151 */           Realm.getDefault());
/* 152 */     } catch (RealmException realmException) {
/* 153 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isRealm(String paramString) {
/*     */     try {
/* 165 */       Realm realm = new Realm(paramString);
/*     */     }
/* 167 */     catch (Exception exception) {
/* 168 */       return false;
/*     */     } 
/* 170 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, ".");
/*     */     
/* 172 */     while (stringTokenizer.hasMoreTokens()) {
/* 173 */       String str = stringTokenizer.nextToken();
/* 174 */       for (byte b = 0; b < str.length(); b++) {
/* 175 */         if (str.charAt(b) >= '') {
/* 176 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 180 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   EncryptionKey readKey(int paramInt) throws IOException {
/* 185 */     int i = read(2);
/* 186 */     if (paramInt == 1283)
/* 187 */       read(2); 
/* 188 */     int j = readLength4();
/* 189 */     byte[] arrayOfByte = IOUtils.readFully(this, j, true);
/* 190 */     return new EncryptionKey(arrayOfByte, i, new Integer(paramInt));
/*     */   }
/*     */   
/*     */   long[] readTimes() throws IOException {
/* 194 */     long[] arrayOfLong = new long[4];
/* 195 */     arrayOfLong[0] = read(4) * 1000L;
/* 196 */     arrayOfLong[1] = read(4) * 1000L;
/* 197 */     arrayOfLong[2] = read(4) * 1000L;
/* 198 */     arrayOfLong[3] = read(4) * 1000L;
/* 199 */     return arrayOfLong;
/*     */   }
/*     */   
/*     */   boolean readskey() throws IOException {
/* 203 */     if (read() == 0) {
/* 204 */       return false;
/*     */     }
/* 206 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   HostAddress[] readAddr() throws IOException, KrbApErrException {
/* 211 */     int i = readLength4();
/* 212 */     if (i > 0) {
/* 213 */       ArrayList<HostAddress> arrayList = new ArrayList();
/* 214 */       for (byte b = 0; b < i; b++) {
/* 215 */         int j = read(2);
/* 216 */         int k = readLength4();
/* 217 */         if (k != 4 && k != 16) {
/* 218 */           if (DEBUG) {
/* 219 */             System.out.println("Incorrect address format.");
/*     */           }
/* 221 */           return null;
/*     */         } 
/* 223 */         byte[] arrayOfByte = new byte[k];
/* 224 */         for (byte b1 = 0; b1 < k; b1++)
/* 225 */           arrayOfByte[b1] = (byte)read(1); 
/* 226 */         arrayList.add(new HostAddress(j, arrayOfByte));
/*     */       } 
/* 228 */       return arrayList.<HostAddress>toArray(new HostAddress[arrayList.size()]);
/*     */     } 
/* 230 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   AuthorizationDataEntry[] readAuth() throws IOException {
/* 235 */     int i = readLength4();
/* 236 */     if (i > 0) {
/* 237 */       ArrayList<AuthorizationDataEntry> arrayList = new ArrayList();
/* 238 */       byte[] arrayOfByte = null;
/* 239 */       for (byte b = 0; b < i; b++) {
/* 240 */         int j = read(2);
/* 241 */         int k = readLength4();
/* 242 */         arrayOfByte = IOUtils.readFully(this, k, true);
/* 243 */         arrayList.add(new AuthorizationDataEntry(j, arrayOfByte));
/*     */       } 
/* 245 */       return arrayList.<AuthorizationDataEntry>toArray(new AuthorizationDataEntry[arrayList.size()]);
/*     */     } 
/* 247 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   byte[] readData() throws IOException {
/* 252 */     int i = readLength4();
/* 253 */     if (i == 0) {
/* 254 */       return null;
/*     */     }
/* 256 */     return IOUtils.readFully(this, i, true);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean[] readFlags() throws IOException {
/* 261 */     boolean[] arrayOfBoolean = new boolean[32];
/*     */     
/* 263 */     int i = read(4);
/* 264 */     if ((i & 0x40000000) == 1073741824)
/* 265 */       arrayOfBoolean[1] = true; 
/* 266 */     if ((i & 0x20000000) == 536870912)
/* 267 */       arrayOfBoolean[2] = true; 
/* 268 */     if ((i & 0x10000000) == 268435456)
/* 269 */       arrayOfBoolean[3] = true; 
/* 270 */     if ((i & 0x8000000) == 134217728)
/* 271 */       arrayOfBoolean[4] = true; 
/* 272 */     if ((i & 0x4000000) == 67108864)
/* 273 */       arrayOfBoolean[5] = true; 
/* 274 */     if ((i & 0x2000000) == 33554432)
/* 275 */       arrayOfBoolean[6] = true; 
/* 276 */     if ((i & 0x1000000) == 16777216)
/* 277 */       arrayOfBoolean[7] = true; 
/* 278 */     if ((i & 0x800000) == 8388608)
/* 279 */       arrayOfBoolean[8] = true; 
/* 280 */     if ((i & 0x400000) == 4194304)
/* 281 */       arrayOfBoolean[9] = true; 
/* 282 */     if ((i & 0x200000) == 2097152)
/* 283 */       arrayOfBoolean[10] = true; 
/* 284 */     if ((i & 0x100000) == 1048576)
/* 285 */       arrayOfBoolean[11] = true; 
/* 286 */     if (DEBUG) {
/* 287 */       String str = ">>> CCacheInputStream: readFlags() ";
/* 288 */       if (arrayOfBoolean[1] == true) {
/* 289 */         str = str + " FORWARDABLE;";
/*     */       }
/* 291 */       if (arrayOfBoolean[2] == true) {
/* 292 */         str = str + " FORWARDED;";
/*     */       }
/* 294 */       if (arrayOfBoolean[3] == true) {
/* 295 */         str = str + " PROXIABLE;";
/*     */       }
/* 297 */       if (arrayOfBoolean[4] == true) {
/* 298 */         str = str + " PROXY;";
/*     */       }
/* 300 */       if (arrayOfBoolean[5] == true) {
/* 301 */         str = str + " MAY_POSTDATE;";
/*     */       }
/* 303 */       if (arrayOfBoolean[6] == true) {
/* 304 */         str = str + " POSTDATED;";
/*     */       }
/* 306 */       if (arrayOfBoolean[7] == true) {
/* 307 */         str = str + " INVALID;";
/*     */       }
/* 309 */       if (arrayOfBoolean[8] == true) {
/* 310 */         str = str + " RENEWABLE;";
/*     */       }
/*     */       
/* 313 */       if (arrayOfBoolean[9] == true) {
/* 314 */         str = str + " INITIAL;";
/*     */       }
/* 316 */       if (arrayOfBoolean[10] == true) {
/* 317 */         str = str + " PRE_AUTH;";
/*     */       }
/* 319 */       if (arrayOfBoolean[11] == true) {
/* 320 */         str = str + " HW_AUTH;";
/*     */       }
/* 322 */       System.out.println(str);
/*     */     } 
/* 324 */     return arrayOfBoolean;
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
/*     */   Credentials readCred(int paramInt) throws IOException, RealmException, KrbApErrException, Asn1Exception {
/* 338 */     PrincipalName principalName1 = null;
/*     */     try {
/* 340 */       principalName1 = readPrincipal(paramInt);
/* 341 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 345 */     if (DEBUG) {
/* 346 */       System.out.println(">>>DEBUG <CCacheInputStream>  client principal is " + principalName1);
/*     */     }
/* 348 */     PrincipalName principalName2 = null;
/*     */     try {
/* 350 */       principalName2 = readPrincipal(paramInt);
/* 351 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 354 */     if (DEBUG) {
/* 355 */       System.out.println(">>>DEBUG <CCacheInputStream> server principal is " + principalName2);
/*     */     }
/* 357 */     EncryptionKey encryptionKey = readKey(paramInt);
/* 358 */     if (DEBUG) {
/* 359 */       System.out.println(">>>DEBUG <CCacheInputStream> key type: " + encryptionKey.getEType());
/*     */     }
/* 361 */     long[] arrayOfLong = readTimes();
/* 362 */     KerberosTime kerberosTime1 = new KerberosTime(arrayOfLong[0]);
/* 363 */     KerberosTime kerberosTime2 = (arrayOfLong[1] == 0L) ? null : new KerberosTime(arrayOfLong[1]);
/*     */     
/* 365 */     KerberosTime kerberosTime3 = new KerberosTime(arrayOfLong[2]);
/* 366 */     KerberosTime kerberosTime4 = (arrayOfLong[3] == 0L) ? null : new KerberosTime(arrayOfLong[3]);
/*     */ 
/*     */     
/* 369 */     if (DEBUG) {
/* 370 */       System.out.println(">>>DEBUG <CCacheInputStream> auth time: " + kerberosTime1.toDate().toString());
/* 371 */       System.out.println(">>>DEBUG <CCacheInputStream> start time: " + ((kerberosTime2 == null) ? "null" : kerberosTime2
/* 372 */           .toDate().toString()));
/* 373 */       System.out.println(">>>DEBUG <CCacheInputStream> end time: " + kerberosTime3.toDate().toString());
/* 374 */       System.out.println(">>>DEBUG <CCacheInputStream> renew_till time: " + ((kerberosTime4 == null) ? "null" : kerberosTime4
/* 375 */           .toDate().toString()));
/*     */     } 
/* 377 */     boolean bool = readskey();
/* 378 */     boolean[] arrayOfBoolean = readFlags();
/* 379 */     TicketFlags ticketFlags = new TicketFlags(arrayOfBoolean);
/* 380 */     HostAddress[] arrayOfHostAddress = readAddr();
/* 381 */     HostAddresses hostAddresses = null;
/* 382 */     if (arrayOfHostAddress != null) {
/* 383 */       hostAddresses = new HostAddresses(arrayOfHostAddress);
/*     */     }
/* 385 */     AuthorizationDataEntry[] arrayOfAuthorizationDataEntry = readAuth();
/* 386 */     AuthorizationData authorizationData = null;
/* 387 */     if (arrayOfAuthorizationDataEntry != null) {
/* 388 */       authorizationData = new AuthorizationData(arrayOfAuthorizationDataEntry);
/*     */     }
/* 390 */     byte[] arrayOfByte1 = readData();
/* 391 */     byte[] arrayOfByte2 = readData();
/*     */ 
/*     */     
/* 394 */     if (principalName1 == null || principalName2 == null) {
/* 395 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 399 */       return new Credentials(principalName1, principalName2, encryptionKey, kerberosTime1, kerberosTime2, kerberosTime3, kerberosTime4, bool, ticketFlags, hostAddresses, authorizationData, (arrayOfByte1 != null) ? new Ticket(arrayOfByte1) : null, (arrayOfByte2 != null) ? new Ticket(arrayOfByte2) : null);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 404 */     catch (Exception exception) {
/* 405 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\ccache\CCacheInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */