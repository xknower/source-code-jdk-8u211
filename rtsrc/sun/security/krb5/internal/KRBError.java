/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.Checksum;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.Realm;
/*     */ import sun.security.krb5.RealmException;
/*     */ import sun.security.krb5.internal.util.KerberosString;
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
/*     */ public class KRBError
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 3643809337475284503L;
/*     */   private int pvno;
/*     */   private int msgType;
/*     */   private KerberosTime cTime;
/*     */   private Integer cuSec;
/*     */   private KerberosTime sTime;
/*     */   private Integer suSec;
/*     */   private int errorCode;
/*     */   private PrincipalName cname;
/*     */   private PrincipalName sname;
/*     */   private String eText;
/*     */   private byte[] eData;
/*     */   private Checksum eCksum;
/*     */   private PAData[] pa;
/* 101 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*     */     try {
/* 106 */       init(new DerValue((byte[])paramObjectInputStream.readObject()));
/* 107 */       parseEData(this.eData);
/* 108 */     } catch (Exception exception) {
/* 109 */       throw new IOException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*     */     try {
/* 116 */       paramObjectOutputStream.writeObject(asn1Encode());
/* 117 */     } catch (Exception exception) {
/* 118 */       throw new IOException(exception);
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
/*     */   public KRBError(APOptions paramAPOptions, KerberosTime paramKerberosTime1, Integer paramInteger1, KerberosTime paramKerberosTime2, Integer paramInteger2, int paramInt, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, String paramString, byte[] paramArrayOfbyte) throws IOException, Asn1Exception {
/* 134 */     this.pvno = 5;
/* 135 */     this.msgType = 30;
/* 136 */     this.cTime = paramKerberosTime1;
/* 137 */     this.cuSec = paramInteger1;
/* 138 */     this.sTime = paramKerberosTime2;
/* 139 */     this.suSec = paramInteger2;
/* 140 */     this.errorCode = paramInt;
/* 141 */     this.cname = paramPrincipalName1;
/* 142 */     this.sname = paramPrincipalName2;
/* 143 */     this.eText = paramString;
/* 144 */     this.eData = paramArrayOfbyte;
/*     */     
/* 146 */     parseEData(this.eData);
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
/*     */   public KRBError(APOptions paramAPOptions, KerberosTime paramKerberosTime1, Integer paramInteger1, KerberosTime paramKerberosTime2, Integer paramInteger2, int paramInt, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, String paramString, byte[] paramArrayOfbyte, Checksum paramChecksum) throws IOException, Asn1Exception {
/* 162 */     this.pvno = 5;
/* 163 */     this.msgType = 30;
/* 164 */     this.cTime = paramKerberosTime1;
/* 165 */     this.cuSec = paramInteger1;
/* 166 */     this.sTime = paramKerberosTime2;
/* 167 */     this.suSec = paramInteger2;
/* 168 */     this.errorCode = paramInt;
/* 169 */     this.cname = paramPrincipalName1;
/* 170 */     this.sname = paramPrincipalName2;
/* 171 */     this.eText = paramString;
/* 172 */     this.eData = paramArrayOfbyte;
/* 173 */     this.eCksum = paramChecksum;
/*     */     
/* 175 */     parseEData(this.eData);
/*     */   }
/*     */ 
/*     */   
/*     */   public KRBError(byte[] paramArrayOfbyte) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/* 180 */     init(new DerValue(paramArrayOfbyte));
/* 181 */     parseEData(this.eData);
/*     */   }
/*     */ 
/*     */   
/*     */   public KRBError(DerValue paramDerValue) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/* 186 */     init(paramDerValue);
/* 187 */     showDebug();
/* 188 */     parseEData(this.eData);
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
/*     */   private void parseEData(byte[] paramArrayOfbyte) throws IOException {
/* 215 */     if (paramArrayOfbyte == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 220 */     if (this.errorCode == 25 || this.errorCode == 24) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 226 */         parsePAData(paramArrayOfbyte);
/* 227 */       } catch (Exception exception) {
/* 228 */         if (DEBUG) {
/* 229 */           System.out.println("Unable to parse eData field of KRB-ERROR:\n" + (new HexDumpEncoder())
/* 230 */               .encodeBuffer(paramArrayOfbyte));
/*     */         }
/* 232 */         IOException iOException = new IOException("Unable to parse eData field of KRB-ERROR");
/*     */         
/* 234 */         iOException.initCause(exception);
/* 235 */         throw iOException;
/*     */       }
/*     */     
/* 238 */     } else if (DEBUG) {
/* 239 */       System.out.println("Unknown eData field of KRB-ERROR:\n" + (new HexDumpEncoder())
/* 240 */           .encodeBuffer(paramArrayOfbyte));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parsePAData(byte[] paramArrayOfbyte) throws IOException, Asn1Exception {
/* 251 */     DerValue derValue = new DerValue(paramArrayOfbyte);
/* 252 */     ArrayList<PAData> arrayList = new ArrayList();
/* 253 */     while (derValue.data.available() > 0) {
/*     */       
/* 255 */       DerValue derValue1 = derValue.data.getDerValue();
/* 256 */       PAData pAData = new PAData(derValue1);
/* 257 */       arrayList.add(pAData);
/* 258 */       if (DEBUG) {
/* 259 */         System.out.println(pAData);
/*     */       }
/*     */     } 
/* 262 */     this.pa = arrayList.<PAData>toArray(new PAData[arrayList.size()]);
/*     */   }
/*     */   
/*     */   public final KerberosTime getServerTime() {
/* 266 */     return this.sTime;
/*     */   }
/*     */   
/*     */   public final KerberosTime getClientTime() {
/* 270 */     return this.cTime;
/*     */   }
/*     */   
/*     */   public final Integer getServerMicroSeconds() {
/* 274 */     return this.suSec;
/*     */   }
/*     */   
/*     */   public final Integer getClientMicroSeconds() {
/* 278 */     return this.cuSec;
/*     */   }
/*     */   
/*     */   public final int getErrorCode() {
/* 282 */     return this.errorCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public final PAData[] getPA() {
/* 287 */     return this.pa;
/*     */   }
/*     */   
/*     */   public final String getErrorString() {
/* 291 */     return this.eText;
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
/*     */   private void init(DerValue paramDerValue) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/* 306 */     if ((paramDerValue.getTag() & 0x1F) != 30 || paramDerValue
/* 307 */       .isApplication() != true || paramDerValue
/* 308 */       .isConstructed() != true) {
/* 309 */       throw new Asn1Exception(906);
/*     */     }
/* 311 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/* 312 */     if (derValue1.getTag() != 48) {
/* 313 */       throw new Asn1Exception(906);
/*     */     }
/* 315 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 316 */     if ((derValue2.getTag() & 0x1F) == 0) {
/*     */       
/* 318 */       this.pvno = derValue2.getData().getBigInteger().intValue();
/* 319 */       if (this.pvno != 5)
/* 320 */         throw new KrbApErrException(39); 
/*     */     } else {
/* 322 */       throw new Asn1Exception(906);
/*     */     } 
/*     */     
/* 325 */     derValue2 = derValue1.getData().getDerValue();
/* 326 */     if ((derValue2.getTag() & 0x1F) == 1) {
/* 327 */       this.msgType = derValue2.getData().getBigInteger().intValue();
/* 328 */       if (this.msgType != 30) {
/* 329 */         throw new KrbApErrException(40);
/*     */       }
/*     */     } else {
/* 332 */       throw new Asn1Exception(906);
/*     */     } 
/*     */     
/* 335 */     this.cTime = KerberosTime.parse(derValue1.getData(), (byte)2, true);
/* 336 */     if ((derValue1.getData().peekByte() & 0x1F) == 3) {
/* 337 */       derValue2 = derValue1.getData().getDerValue();
/* 338 */       this.cuSec = new Integer(derValue2.getData().getBigInteger().intValue());
/*     */     } else {
/* 340 */       this.cuSec = null;
/* 341 */     }  this.sTime = KerberosTime.parse(derValue1.getData(), (byte)4, false);
/* 342 */     derValue2 = derValue1.getData().getDerValue();
/* 343 */     if ((derValue2.getTag() & 0x1F) == 5) {
/* 344 */       this.suSec = new Integer(derValue2.getData().getBigInteger().intValue());
/*     */     } else {
/* 346 */       throw new Asn1Exception(906);
/* 347 */     }  derValue2 = derValue1.getData().getDerValue();
/* 348 */     if ((derValue2.getTag() & 0x1F) == 6) {
/* 349 */       this.errorCode = derValue2.getData().getBigInteger().intValue();
/*     */     } else {
/* 351 */       throw new Asn1Exception(906);
/* 352 */     }  Realm realm1 = Realm.parse(derValue1.getData(), (byte)7, true);
/* 353 */     this.cname = PrincipalName.parse(derValue1.getData(), (byte)8, true, realm1);
/* 354 */     Realm realm2 = Realm.parse(derValue1.getData(), (byte)9, false);
/* 355 */     this.sname = PrincipalName.parse(derValue1.getData(), (byte)10, false, realm2);
/* 356 */     this.eText = null;
/* 357 */     this.eData = null;
/* 358 */     this.eCksum = null;
/* 359 */     if (derValue1.getData().available() > 0 && (
/* 360 */       derValue1.getData().peekByte() & 0x1F) == 11) {
/* 361 */       derValue2 = derValue1.getData().getDerValue();
/* 362 */       this
/* 363 */         .eText = (new KerberosString(derValue2.getData().getDerValue())).toString();
/*     */     } 
/*     */     
/* 366 */     if (derValue1.getData().available() > 0 && (
/* 367 */       derValue1.getData().peekByte() & 0x1F) == 12) {
/* 368 */       derValue2 = derValue1.getData().getDerValue();
/* 369 */       this.eData = derValue2.getData().getOctetString();
/*     */     } 
/*     */     
/* 372 */     if (derValue1.getData().available() > 0) {
/* 373 */       this.eCksum = Checksum.parse(derValue1.getData(), (byte)13, true);
/*     */     }
/* 375 */     if (derValue1.getData().available() > 0) {
/* 376 */       throw new Asn1Exception(906);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void showDebug() {
/* 383 */     if (DEBUG) {
/* 384 */       System.out.println(">>>KRBError:");
/* 385 */       if (this.cTime != null)
/* 386 */         System.out.println("\t cTime is " + this.cTime.toDate().toString() + " " + this.cTime.toDate().getTime()); 
/* 387 */       if (this.cuSec != null) {
/* 388 */         System.out.println("\t cuSec is " + this.cuSec.intValue());
/*     */       }
/*     */       
/* 391 */       System.out.println("\t sTime is " + this.sTime.toDate()
/* 392 */           .toString() + " " + this.sTime.toDate().getTime());
/* 393 */       System.out.println("\t suSec is " + this.suSec);
/* 394 */       System.out.println("\t error code is " + this.errorCode);
/* 395 */       System.out.println("\t error Message is " + Krb5.getErrorMessage(this.errorCode));
/* 396 */       if (this.cname != null) {
/* 397 */         System.out.println("\t cname is " + this.cname.toString());
/*     */       }
/* 399 */       if (this.sname != null) {
/* 400 */         System.out.println("\t sname is " + this.sname.toString());
/*     */       }
/* 402 */       if (this.eData != null) {
/* 403 */         System.out.println("\t eData provided.");
/*     */       }
/* 405 */       if (this.eCksum != null) {
/* 406 */         System.out.println("\t checksum provided.");
/*     */       }
/* 408 */       System.out.println("\t msgType is " + this.msgType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 419 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 420 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 422 */     derOutputStream1.putInteger(BigInteger.valueOf(this.pvno));
/* 423 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)0), derOutputStream1);
/* 424 */     derOutputStream1 = new DerOutputStream();
/* 425 */     derOutputStream1.putInteger(BigInteger.valueOf(this.msgType));
/* 426 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)1), derOutputStream1);
/*     */     
/* 428 */     if (this.cTime != null) {
/* 429 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)2), this.cTime.asn1Encode());
/*     */     }
/* 431 */     if (this.cuSec != null) {
/* 432 */       derOutputStream1 = new DerOutputStream();
/* 433 */       derOutputStream1.putInteger(BigInteger.valueOf(this.cuSec.intValue()));
/* 434 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)3), derOutputStream1);
/*     */     } 
/*     */     
/* 437 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)4), this.sTime.asn1Encode());
/* 438 */     derOutputStream1 = new DerOutputStream();
/* 439 */     derOutputStream1.putInteger(BigInteger.valueOf(this.suSec.intValue()));
/* 440 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)5), derOutputStream1);
/* 441 */     derOutputStream1 = new DerOutputStream();
/* 442 */     derOutputStream1.putInteger(BigInteger.valueOf(this.errorCode));
/* 443 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)6), derOutputStream1);
/*     */     
/* 445 */     if (this.cname != null) {
/* 446 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)7), this.cname.getRealm().asn1Encode());
/* 447 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)8), this.cname.asn1Encode());
/*     */     } 
/*     */     
/* 450 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)9), this.sname.getRealm().asn1Encode());
/* 451 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)10), this.sname.asn1Encode());
/*     */     
/* 453 */     if (this.eText != null) {
/* 454 */       derOutputStream1 = new DerOutputStream();
/* 455 */       derOutputStream1.putDerValue((new KerberosString(this.eText)).toDerValue());
/* 456 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)11), derOutputStream1);
/*     */     } 
/* 458 */     if (this.eData != null) {
/* 459 */       derOutputStream1 = new DerOutputStream();
/* 460 */       derOutputStream1.putOctetString(this.eData);
/* 461 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)12), derOutputStream1);
/*     */     } 
/* 463 */     if (this.eCksum != null) {
/* 464 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)13), this.eCksum.asn1Encode());
/*     */     }
/*     */     
/* 467 */     derOutputStream1 = new DerOutputStream();
/* 468 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 469 */     derOutputStream2 = new DerOutputStream();
/* 470 */     derOutputStream2.write(DerValue.createTag((byte)64, true, (byte)30), derOutputStream1);
/* 471 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 475 */     if (this == paramObject) {
/* 476 */       return true;
/*     */     }
/*     */     
/* 479 */     if (!(paramObject instanceof KRBError)) {
/* 480 */       return false;
/*     */     }
/*     */     
/* 483 */     KRBError kRBError = (KRBError)paramObject;
/* 484 */     return (this.pvno == kRBError.pvno && this.msgType == kRBError.msgType && 
/*     */       
/* 486 */       isEqual(this.cTime, kRBError.cTime) && 
/* 487 */       isEqual(this.cuSec, kRBError.cuSec) && 
/* 488 */       isEqual(this.sTime, kRBError.sTime) && 
/* 489 */       isEqual(this.suSec, kRBError.suSec) && this.errorCode == kRBError.errorCode && 
/*     */       
/* 491 */       isEqual(this.cname, kRBError.cname) && 
/* 492 */       isEqual(this.sname, kRBError.sname) && 
/* 493 */       isEqual(this.eText, kRBError.eText) && 
/* 494 */       Arrays.equals(this.eData, kRBError.eData) && 
/* 495 */       isEqual(this.eCksum, kRBError.eCksum));
/*     */   }
/*     */   
/*     */   private static boolean isEqual(Object paramObject1, Object paramObject2) {
/* 499 */     return (paramObject1 == null) ? ((paramObject2 == null)) : paramObject1.equals(paramObject2);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 503 */     int i = 17;
/* 504 */     i = 37 * i + this.pvno;
/* 505 */     i = 37 * i + this.msgType;
/* 506 */     if (this.cTime != null) i = 37 * i + this.cTime.hashCode(); 
/* 507 */     if (this.cuSec != null) i = 37 * i + this.cuSec.hashCode(); 
/* 508 */     if (this.sTime != null) i = 37 * i + this.sTime.hashCode(); 
/* 509 */     if (this.suSec != null) i = 37 * i + this.suSec.hashCode(); 
/* 510 */     i = 37 * i + this.errorCode;
/* 511 */     if (this.cname != null) i = 37 * i + this.cname.hashCode(); 
/* 512 */     if (this.sname != null) i = 37 * i + this.sname.hashCode(); 
/* 513 */     if (this.eText != null) i = 37 * i + this.eText.hashCode(); 
/* 514 */     i = 37 * i + Arrays.hashCode(this.eData);
/* 515 */     if (this.eCksum != null) i = 37 * i + this.eCksum.hashCode(); 
/* 516 */     return i;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\KRBError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */