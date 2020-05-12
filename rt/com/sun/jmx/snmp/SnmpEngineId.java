/*     */ package com.sun.jmx.snmp;
/*     */ 
/*     */ import com.sun.jmx.snmp.internal.SnmpTools;
/*     */ import java.io.Serializable;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Arrays;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SnmpEngineId
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5434729655830763317L;
/*  45 */   byte[] engineId = null;
/*  46 */   String hexString = null;
/*  47 */   String humanString = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SnmpEngineId(String paramString) {
/*  53 */     this.engineId = SnmpTools.ascii2binary(paramString);
/*  54 */     this.hexString = paramString.toLowerCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SnmpEngineId(byte[] paramArrayOfbyte) {
/*  61 */     this.engineId = paramArrayOfbyte;
/*  62 */     this.hexString = SnmpTools.binary2ascii(paramArrayOfbyte).toLowerCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReadableId() {
/*  70 */     return this.humanString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  78 */     return this.hexString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBytes() {
/*  85 */     return this.engineId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setStringValue(String paramString) {
/*  92 */     this.humanString = paramString;
/*     */   }
/*     */   
/*     */   static void validateId(String paramString) throws IllegalArgumentException {
/*  96 */     byte[] arrayOfByte = SnmpTools.ascii2binary(paramString);
/*  97 */     validateId(arrayOfByte);
/*     */   }
/*     */ 
/*     */   
/*     */   static void validateId(byte[] paramArrayOfbyte) throws IllegalArgumentException {
/* 102 */     if (paramArrayOfbyte.length < 5) throw new IllegalArgumentException("Id size lower than 5 bytes."); 
/* 103 */     if (paramArrayOfbyte.length > 32) throw new IllegalArgumentException("Id size greater than 32 bytes.");
/*     */ 
/*     */     
/* 106 */     if ((paramArrayOfbyte[0] & 0x80) == 0 && paramArrayOfbyte.length != 12) {
/* 107 */       throw new IllegalArgumentException("Very first bit = 0 and length != 12 octets");
/*     */     }
/* 109 */     byte[] arrayOfByte1 = new byte[paramArrayOfbyte.length];
/* 110 */     if (Arrays.equals(arrayOfByte1, paramArrayOfbyte)) throw new IllegalArgumentException("Zeroed Id."); 
/* 111 */     byte[] arrayOfByte2 = new byte[paramArrayOfbyte.length];
/* 112 */     Arrays.fill(arrayOfByte2, (byte)-1);
/* 113 */     if (Arrays.equals(arrayOfByte2, paramArrayOfbyte)) throw new IllegalArgumentException("0xFF Id.");
/*     */   
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
/*     */   public static SnmpEngineId createEngineId(byte[] paramArrayOfbyte) throws IllegalArgumentException {
/* 130 */     if (paramArrayOfbyte == null || paramArrayOfbyte.length == 0) return null; 
/* 131 */     validateId(paramArrayOfbyte);
/* 132 */     return new SnmpEngineId(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SnmpEngineId createEngineId() {
/* 140 */     Object object = null;
/* 141 */     byte[] arrayOfByte = new byte[13];
/* 142 */     byte b = 42;
/* 143 */     long l1 = 255L;
/* 144 */     long l2 = System.currentTimeMillis();
/*     */     
/* 146 */     arrayOfByte[0] = (byte)((b & 0xFF000000) >> 24);
/* 147 */     arrayOfByte[0] = (byte)(arrayOfByte[0] | 0x80);
/* 148 */     arrayOfByte[1] = (byte)((b & 0xFF0000) >> 16);
/* 149 */     arrayOfByte[2] = (byte)((b & 0xFF00) >> 8);
/* 150 */     arrayOfByte[3] = (byte)(b & 0xFF);
/* 151 */     arrayOfByte[4] = 5;
/*     */     
/* 153 */     arrayOfByte[5] = (byte)(int)((l2 & l1 << 56L) >>> 56L);
/* 154 */     arrayOfByte[6] = (byte)(int)((l2 & l1 << 48L) >>> 48L);
/* 155 */     arrayOfByte[7] = (byte)(int)((l2 & l1 << 40L) >>> 40L);
/* 156 */     arrayOfByte[8] = (byte)(int)((l2 & l1 << 32L) >>> 32L);
/* 157 */     arrayOfByte[9] = (byte)(int)((l2 & l1 << 24L) >>> 24L);
/* 158 */     arrayOfByte[10] = (byte)(int)((l2 & l1 << 16L) >>> 16L);
/* 159 */     arrayOfByte[11] = (byte)(int)((l2 & l1 << 8L) >>> 8L);
/* 160 */     arrayOfByte[12] = (byte)(int)(l2 & l1);
/*     */     
/* 162 */     return new SnmpEngineId(arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpOid toOid() {
/* 173 */     long[] arrayOfLong = new long[this.engineId.length + 1];
/* 174 */     arrayOfLong[0] = this.engineId.length;
/* 175 */     for (byte b = 1; b <= this.engineId.length; b++)
/* 176 */       arrayOfLong[b] = (this.engineId[b - 1] & 0xFF); 
/* 177 */     return new SnmpOid(arrayOfLong);
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
/*     */   public static SnmpEngineId createEngineId(String paramString) throws IllegalArgumentException, UnknownHostException {
/* 218 */     return createEngineId(paramString, (String)null);
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
/*     */   public static SnmpEngineId createEngineId(String paramString1, String paramString2) throws IllegalArgumentException, UnknownHostException {
/* 246 */     if (paramString1 == null) return null;
/*     */     
/* 248 */     if (paramString1.startsWith("0x") || paramString1.startsWith("0X")) {
/* 249 */       validateId(paramString1);
/* 250 */       return new SnmpEngineId(paramString1);
/*     */     } 
/* 252 */     paramString2 = (paramString2 == null) ? ":" : paramString2;
/* 253 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString1, paramString2, true);
/*     */ 
/*     */ 
/*     */     
/* 257 */     String str1 = null;
/* 258 */     String str2 = null;
/* 259 */     String str3 = null;
/* 260 */     int i = 161;
/* 261 */     int j = 42;
/* 262 */     InetAddress inetAddress = null;
/* 263 */     SnmpEngineId snmpEngineId = null;
/*     */     
/*     */     try {
/*     */       try {
/* 267 */         str1 = stringTokenizer.nextToken();
/* 268 */       } catch (NoSuchElementException noSuchElementException) {
/* 269 */         throw new IllegalArgumentException("Passed string is invalid : [" + paramString1 + "]");
/*     */       } 
/* 271 */       if (!str1.equals(paramString2)) {
/* 272 */         inetAddress = InetAddress.getByName(str1);
/*     */         try {
/* 274 */           stringTokenizer.nextToken();
/* 275 */         } catch (NoSuchElementException noSuchElementException) {
/*     */           
/* 277 */           snmpEngineId = createEngineId(inetAddress, i, j);
/*     */ 
/*     */           
/* 280 */           snmpEngineId.setStringValue(paramString1);
/* 281 */           return snmpEngineId;
/*     */         } 
/*     */       } else {
/*     */         
/* 285 */         inetAddress = InetAddress.getLocalHost();
/*     */       } 
/*     */       
/*     */       try {
/* 289 */         str2 = stringTokenizer.nextToken();
/* 290 */       } catch (NoSuchElementException noSuchElementException) {
/*     */         
/* 292 */         snmpEngineId = createEngineId(inetAddress, i, j);
/*     */ 
/*     */         
/* 295 */         snmpEngineId.setStringValue(paramString1);
/* 296 */         return snmpEngineId;
/*     */       } 
/*     */       
/* 299 */       if (!str2.equals(paramString2)) {
/* 300 */         i = Integer.parseInt(str2);
/*     */         try {
/* 302 */           stringTokenizer.nextToken();
/* 303 */         } catch (NoSuchElementException noSuchElementException) {
/*     */           
/* 305 */           snmpEngineId = createEngineId(inetAddress, i, j);
/*     */ 
/*     */           
/* 308 */           snmpEngineId.setStringValue(paramString1);
/* 309 */           return snmpEngineId;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 315 */         str3 = stringTokenizer.nextToken();
/* 316 */       } catch (NoSuchElementException noSuchElementException) {
/*     */         
/* 318 */         snmpEngineId = createEngineId(inetAddress, i, j);
/*     */ 
/*     */         
/* 321 */         snmpEngineId.setStringValue(paramString1);
/* 322 */         return snmpEngineId;
/*     */       } 
/*     */       
/* 325 */       if (!str3.equals(paramString2)) {
/* 326 */         j = Integer.parseInt(str3);
/*     */       }
/* 328 */       snmpEngineId = createEngineId(inetAddress, i, j);
/*     */ 
/*     */       
/* 331 */       snmpEngineId.setStringValue(paramString1);
/*     */       
/* 333 */       return snmpEngineId;
/*     */     }
/* 335 */     catch (Exception exception) {
/* 336 */       throw new IllegalArgumentException("Passed string is invalid : [" + paramString1 + "]. Check that the used separator [" + paramString2 + "] is compatible with IPv6 address format.");
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
/*     */   public static SnmpEngineId createEngineId(int paramInt) throws UnknownHostException {
/* 353 */     byte b = 42;
/* 354 */     InetAddress inetAddress = null;
/* 355 */     inetAddress = InetAddress.getLocalHost();
/* 356 */     return createEngineId(inetAddress, paramInt, b);
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
/*     */   public static SnmpEngineId createEngineId(InetAddress paramInetAddress, int paramInt) throws IllegalArgumentException {
/* 370 */     byte b = 42;
/* 371 */     if (paramInetAddress == null)
/* 372 */       throw new IllegalArgumentException("InetAddress is null."); 
/* 373 */     return createEngineId(paramInetAddress, paramInt, b);
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
/*     */   public static SnmpEngineId createEngineId(int paramInt1, int paramInt2) throws UnknownHostException {
/* 386 */     InetAddress inetAddress = null;
/* 387 */     inetAddress = InetAddress.getLocalHost();
/* 388 */     return createEngineId(inetAddress, paramInt1, paramInt2);
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
/*     */   public static SnmpEngineId createEngineId(InetAddress paramInetAddress, int paramInt1, int paramInt2) {
/* 402 */     if (paramInetAddress == null) throw new IllegalArgumentException("InetAddress is null."); 
/* 403 */     byte[] arrayOfByte1 = paramInetAddress.getAddress();
/* 404 */     byte[] arrayOfByte2 = new byte[9 + arrayOfByte1.length];
/* 405 */     arrayOfByte2[0] = (byte)((paramInt2 & 0xFF000000) >> 24);
/* 406 */     arrayOfByte2[0] = (byte)(arrayOfByte2[0] | 0x80);
/* 407 */     arrayOfByte2[1] = (byte)((paramInt2 & 0xFF0000) >> 16);
/* 408 */     arrayOfByte2[2] = (byte)((paramInt2 & 0xFF00) >> 8);
/*     */     
/* 410 */     arrayOfByte2[3] = (byte)(paramInt2 & 0xFF);
/* 411 */     arrayOfByte2[4] = 5;
/*     */     
/* 413 */     if (arrayOfByte1.length == 4) {
/* 414 */       arrayOfByte2[4] = 1;
/*     */     }
/* 416 */     if (arrayOfByte1.length == 16) {
/* 417 */       arrayOfByte2[4] = 2;
/*     */     }
/* 419 */     for (byte b = 0; b < arrayOfByte1.length; b++) {
/* 420 */       arrayOfByte2[b + 5] = arrayOfByte1[b];
/*     */     }
/*     */     
/* 423 */     arrayOfByte2[5 + arrayOfByte1.length] = (byte)((paramInt1 & 0xFF000000) >> 24);
/* 424 */     arrayOfByte2[6 + arrayOfByte1.length] = (byte)((paramInt1 & 0xFF0000) >> 16);
/* 425 */     arrayOfByte2[7 + arrayOfByte1.length] = (byte)((paramInt1 & 0xFF00) >> 8);
/* 426 */     arrayOfByte2[8 + arrayOfByte1.length] = (byte)(paramInt1 & 0xFF);
/*     */     
/* 428 */     return new SnmpEngineId(arrayOfByte2);
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
/*     */   public static SnmpEngineId createEngineId(int paramInt, InetAddress paramInetAddress) {
/* 441 */     if (paramInetAddress == null) throw new IllegalArgumentException("InetAddress is null."); 
/* 442 */     byte[] arrayOfByte1 = paramInetAddress.getAddress();
/* 443 */     byte[] arrayOfByte2 = new byte[5 + arrayOfByte1.length];
/* 444 */     arrayOfByte2[0] = (byte)((paramInt & 0xFF000000) >> 24);
/* 445 */     arrayOfByte2[0] = (byte)(arrayOfByte2[0] | 0x80);
/* 446 */     arrayOfByte2[1] = (byte)((paramInt & 0xFF0000) >> 16);
/* 447 */     arrayOfByte2[2] = (byte)((paramInt & 0xFF00) >> 8);
/*     */     
/* 449 */     arrayOfByte2[3] = (byte)(paramInt & 0xFF);
/* 450 */     if (arrayOfByte1.length == 4) {
/* 451 */       arrayOfByte2[4] = 1;
/*     */     }
/* 453 */     if (arrayOfByte1.length == 16) {
/* 454 */       arrayOfByte2[4] = 2;
/*     */     }
/* 456 */     for (byte b = 0; b < arrayOfByte1.length; b++) {
/* 457 */       arrayOfByte2[b + 5] = arrayOfByte1[b];
/*     */     }
/*     */     
/* 460 */     return new SnmpEngineId(arrayOfByte2);
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
/*     */   public static SnmpEngineId createEngineId(InetAddress paramInetAddress) {
/* 473 */     return createEngineId(42, paramInetAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 482 */     if (!(paramObject instanceof SnmpEngineId)) return false; 
/* 483 */     return this.hexString.equals(((SnmpEngineId)paramObject).toString());
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 487 */     return this.hexString.hashCode();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpEngineId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */