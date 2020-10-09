/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.Config;
/*     */ import sun.security.krb5.KrbException;
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
/*     */ public class KerberosTime
/*     */ {
/*     */   private final long kerberosTime;
/*     */   private final int microSeconds;
/*  73 */   private static long initMilli = System.currentTimeMillis();
/*  74 */   private static long initMicro = System.nanoTime() / 1000L;
/*     */   
/*  76 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */ 
/*     */   
/*     */   private KerberosTime(long paramLong, int paramInt) {
/*  81 */     this.kerberosTime = paramLong;
/*  82 */     this.microSeconds = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosTime(long paramLong) {
/*  89 */     this(paramLong, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosTime(String paramString) throws Asn1Exception {
/*  95 */     this(toKerberosTime(paramString), 0);
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
/*     */   private static long toKerberosTime(String paramString) throws Asn1Exception {
/* 108 */     if (paramString.length() != 15)
/* 109 */       throw new Asn1Exception(900); 
/* 110 */     if (paramString.charAt(14) != 'Z')
/* 111 */       throw new Asn1Exception(900); 
/* 112 */     int i = Integer.parseInt(paramString.substring(0, 4));
/* 113 */     Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
/* 114 */     calendar.clear();
/* 115 */     calendar.set(i, 
/* 116 */         Integer.parseInt(paramString.substring(4, 6)) - 1, 
/* 117 */         Integer.parseInt(paramString.substring(6, 8)), 
/* 118 */         Integer.parseInt(paramString.substring(8, 10)), 
/* 119 */         Integer.parseInt(paramString.substring(10, 12)), 
/* 120 */         Integer.parseInt(paramString.substring(12, 14)));
/* 121 */     return calendar.getTimeInMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosTime(Date paramDate) {
/* 128 */     this(paramDate.getTime(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KerberosTime now() {
/* 136 */     long l1 = System.currentTimeMillis();
/* 137 */     long l2 = System.nanoTime() / 1000L;
/* 138 */     long l3 = l2 - initMicro;
/* 139 */     long l4 = initMilli + l3 / 1000L;
/* 140 */     if (l4 - l1 > 100L || l1 - l4 > 100L) {
/* 141 */       if (DEBUG) {
/* 142 */         System.out.println("System time adjusted");
/*     */       }
/* 144 */       initMilli = l1;
/* 145 */       initMicro = l2;
/* 146 */       return new KerberosTime(l1, 0);
/*     */     } 
/* 148 */     return new KerberosTime(l4, (int)(l3 % 1000L));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toGeneralizedTimeString() {
/* 157 */     Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
/* 158 */     calendar.clear();
/*     */     
/* 160 */     calendar.setTimeInMillis(this.kerberosTime);
/* 161 */     return String.format("%04d%02d%02d%02d%02d%02dZ", new Object[] {
/* 162 */           Integer.valueOf(calendar.get(1)), 
/* 163 */           Integer.valueOf(calendar.get(2) + 1), 
/* 164 */           Integer.valueOf(calendar.get(5)), 
/* 165 */           Integer.valueOf(calendar.get(11)), 
/* 166 */           Integer.valueOf(calendar.get(12)), 
/* 167 */           Integer.valueOf(calendar.get(13))
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 177 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 178 */     derOutputStream.putGeneralizedTime(toDate());
/* 179 */     return derOutputStream.toByteArray();
/*     */   }
/*     */   
/*     */   public long getTime() {
/* 183 */     return this.kerberosTime;
/*     */   }
/*     */   
/*     */   public Date toDate() {
/* 187 */     return new Date(this.kerberosTime);
/*     */   }
/*     */   
/*     */   public int getMicroSeconds() {
/* 191 */     Long long_ = new Long(this.kerberosTime % 1000L * 1000L);
/* 192 */     return long_.intValue() + this.microSeconds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosTime withMicroSeconds(int paramInt) {
/* 200 */     return new KerberosTime(this.kerberosTime - this.kerberosTime % 1000L + paramInt / 1000L, paramInt % 1000);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean inClockSkew(int paramInt) {
/* 206 */     return (Math.abs(this.kerberosTime - System.currentTimeMillis()) <= paramInt * 1000L);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inClockSkew() {
/* 211 */     return inClockSkew(getDefaultSkew());
/*     */   }
/*     */   
/*     */   public boolean greaterThanWRTClockSkew(KerberosTime paramKerberosTime, int paramInt) {
/* 215 */     if (this.kerberosTime - paramKerberosTime.kerberosTime > paramInt * 1000L)
/* 216 */       return true; 
/* 217 */     return false;
/*     */   }
/*     */   
/*     */   public boolean greaterThanWRTClockSkew(KerberosTime paramKerberosTime) {
/* 221 */     return greaterThanWRTClockSkew(paramKerberosTime, getDefaultSkew());
/*     */   }
/*     */   
/*     */   public boolean greaterThan(KerberosTime paramKerberosTime) {
/* 225 */     return (this.kerberosTime > paramKerberosTime.kerberosTime || (this.kerberosTime == paramKerberosTime.kerberosTime && this.microSeconds > paramKerberosTime.microSeconds));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 231 */     if (this == paramObject) {
/* 232 */       return true;
/*     */     }
/*     */     
/* 235 */     if (!(paramObject instanceof KerberosTime)) {
/* 236 */       return false;
/*     */     }
/*     */     
/* 239 */     return (this.kerberosTime == ((KerberosTime)paramObject).kerberosTime && this.microSeconds == ((KerberosTime)paramObject).microSeconds);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 244 */     int i = 629 + (int)(this.kerberosTime ^ this.kerberosTime >>> 32L);
/* 245 */     return i * 17 + this.microSeconds;
/*     */   }
/*     */   
/*     */   public boolean isZero() {
/* 249 */     return (this.kerberosTime == 0L && this.microSeconds == 0);
/*     */   }
/*     */   
/*     */   public int getSeconds() {
/* 253 */     Long long_ = new Long(this.kerberosTime / 1000L);
/* 254 */     return long_.intValue();
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
/*     */   public static KerberosTime parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 273 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/* 274 */       return null; 
/* 275 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 276 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 277 */       throw new Asn1Exception(906);
/*     */     }
/*     */     
/* 280 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 281 */     Date date = derValue2.getGeneralizedTime();
/* 282 */     return new KerberosTime(date.getTime(), 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getDefaultSkew() {
/* 287 */     int i = 300;
/*     */     try {
/* 289 */       if ((i = Config.getInstance().getIntValue(new String[] { "libdefaults", "clockskew" })) == Integer.MIN_VALUE)
/*     */       {
/*     */         
/* 292 */         i = 300;
/*     */       }
/* 294 */     } catch (KrbException krbException) {
/* 295 */       if (DEBUG) {
/* 296 */         System.out.println("Exception in getting clockskew from Configuration using default value " + krbException
/*     */ 
/*     */             
/* 299 */             .getMessage());
/*     */       }
/*     */     } 
/* 302 */     return i;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 306 */     return toGeneralizedTimeString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\KerberosTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */