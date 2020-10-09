/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.Config;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.internal.util.KerberosFlags;
/*     */ import sun.security.util.DerInputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KDCOptions
/*     */   extends KerberosFlags
/*     */ {
/*     */   private static final int KDC_OPT_PROXIABLE = 268435456;
/*     */   private static final int KDC_OPT_RENEWABLE_OK = 16;
/*     */   private static final int KDC_OPT_FORWARDABLE = 1073741824;
/*     */   public static final int RESERVED = 0;
/*     */   public static final int FORWARDABLE = 1;
/*     */   public static final int FORWARDED = 2;
/*     */   public static final int PROXIABLE = 3;
/*     */   public static final int PROXY = 4;
/*     */   public static final int ALLOW_POSTDATE = 5;
/*     */   public static final int POSTDATED = 6;
/*     */   public static final int UNUSED7 = 7;
/*     */   public static final int RENEWABLE = 8;
/*     */   public static final int UNUSED9 = 9;
/*     */   public static final int UNUSED10 = 10;
/*     */   public static final int UNUSED11 = 11;
/*     */   public static final int CNAME_IN_ADDL_TKT = 14;
/*     */   public static final int RENEWABLE_OK = 27;
/*     */   public static final int ENC_TKT_IN_SKEY = 28;
/*     */   public static final int RENEW = 30;
/*     */   public static final int VALIDATE = 31;
/* 148 */   private static final String[] names = new String[] { "RESERVED", "FORWARDABLE", "FORWARDED", "PROXIABLE", "PROXY", "ALLOW_POSTDATE", "POSTDATED", "UNUSED7", "RENEWABLE", "UNUSED9", "UNUSED10", "UNUSED11", null, null, "CNAME_IN_ADDL_TKT", null, null, null, null, null, null, null, null, null, null, null, null, "RENEWABLE_OK", "ENC_TKT_IN_SKEY", null, "RENEW", "VALIDATE" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   private boolean DEBUG = Krb5.DEBUG;
/*     */   
/*     */   public static KDCOptions with(int... paramVarArgs) {
/* 174 */     KDCOptions kDCOptions = new KDCOptions();
/* 175 */     for (int i : paramVarArgs) {
/* 176 */       kDCOptions.set(i, true);
/*     */     }
/* 178 */     return kDCOptions;
/*     */   }
/*     */   
/*     */   public KDCOptions() {
/* 182 */     super(32);
/* 183 */     setDefault();
/*     */   }
/*     */   
/*     */   public KDCOptions(int paramInt, byte[] paramArrayOfbyte) throws Asn1Exception {
/* 187 */     super(paramInt, paramArrayOfbyte);
/* 188 */     if (paramInt > paramArrayOfbyte.length * 8 || paramInt > 32) {
/* 189 */       throw new Asn1Exception(502);
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
/*     */   public KDCOptions(boolean[] paramArrayOfboolean) throws Asn1Exception {
/* 201 */     super(paramArrayOfboolean);
/* 202 */     if (paramArrayOfboolean.length > 32) {
/* 203 */       throw new Asn1Exception(502);
/*     */     }
/*     */   }
/*     */   
/*     */   public KDCOptions(DerValue paramDerValue) throws Asn1Exception, IOException {
/* 208 */     this(paramDerValue.getUnalignedBitString(true).toBooleanArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KDCOptions(byte[] paramArrayOfbyte) {
/* 218 */     super(paramArrayOfbyte.length * 8, paramArrayOfbyte);
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
/*     */   public static KDCOptions parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 237 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/* 238 */       return null; 
/* 239 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 240 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 241 */       throw new Asn1Exception(906);
/*     */     }
/* 243 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 244 */     return new KDCOptions(derValue2);
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
/*     */   public void set(int paramInt, boolean paramBoolean) throws ArrayIndexOutOfBoundsException {
/* 257 */     super.set(paramInt, paramBoolean);
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
/*     */   public boolean get(int paramInt) throws ArrayIndexOutOfBoundsException {
/* 270 */     return super.get(paramInt);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 274 */     StringBuilder stringBuilder = new StringBuilder();
/* 275 */     stringBuilder.append("KDCOptions: ");
/* 276 */     for (byte b = 0; b < 32; b++) {
/* 277 */       if (get(b)) {
/* 278 */         if (names[b] != null) {
/* 279 */           stringBuilder.append(names[b]).append(",");
/*     */         } else {
/* 281 */           stringBuilder.append(b).append(",");
/*     */         } 
/*     */       }
/*     */     } 
/* 285 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setDefault() {
/*     */     try {
/* 291 */       Config config = Config.getInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 296 */       int i = config.getIntValue(new String[] { "libdefaults", "kdc_default_options" });
/*     */ 
/*     */       
/* 299 */       if ((i & 0x10) == 16) {
/* 300 */         set(27, true);
/*     */       }
/* 302 */       else if (config.getBooleanValue(new String[] { "libdefaults", "renewable" })) {
/* 303 */         set(27, true);
/*     */       } 
/*     */       
/* 306 */       if ((i & 0x10000000) == 268435456) {
/* 307 */         set(3, true);
/*     */       }
/* 309 */       else if (config.getBooleanValue(new String[] { "libdefaults", "proxiable" })) {
/* 310 */         set(3, true);
/*     */       } 
/*     */ 
/*     */       
/* 314 */       if ((i & 0x40000000) == 1073741824) {
/* 315 */         set(1, true);
/*     */       }
/* 317 */       else if (config.getBooleanValue(new String[] { "libdefaults", "forwardable" })) {
/* 318 */         set(1, true);
/*     */       }
/*     */     
/* 321 */     } catch (KrbException krbException) {
/* 322 */       if (this.DEBUG) {
/* 323 */         System.out.println("Exception in getting default values for KDC Options from the configuration ");
/*     */         
/* 325 */         krbException.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\KDCOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */