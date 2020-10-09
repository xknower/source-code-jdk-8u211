/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.Asn1Exception;
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
/*     */ public class TicketFlags
/*     */   extends KerberosFlags
/*     */ {
/*     */   public TicketFlags() {
/*  59 */     super(32);
/*     */   }
/*     */   
/*     */   public TicketFlags(boolean[] paramArrayOfboolean) throws Asn1Exception {
/*  63 */     super(paramArrayOfboolean);
/*  64 */     if (paramArrayOfboolean.length > 32) {
/*  65 */       throw new Asn1Exception(502);
/*     */     }
/*     */   }
/*     */   
/*     */   public TicketFlags(int paramInt, byte[] paramArrayOfbyte) throws Asn1Exception {
/*  70 */     super(paramInt, paramArrayOfbyte);
/*  71 */     if (paramInt > paramArrayOfbyte.length * 8 || paramInt > 32)
/*  72 */       throw new Asn1Exception(502); 
/*     */   }
/*     */   
/*     */   public TicketFlags(DerValue paramDerValue) throws IOException, Asn1Exception {
/*  76 */     this(paramDerValue.getUnalignedBitString(true).toBooleanArray());
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
/*     */   public static TicketFlags parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/*  92 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/*  93 */       return null; 
/*  94 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/*  95 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/*  96 */       throw new Asn1Exception(906);
/*     */     }
/*     */     
/*  99 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 100 */     return new TicketFlags(derValue2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 106 */       return new TicketFlags(toBooleanArray());
/*     */     }
/* 108 */     catch (Exception exception) {
/* 109 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean match(LoginOptions paramLoginOptions) {
/* 114 */     boolean bool = false;
/*     */     
/* 116 */     if (get(1) == paramLoginOptions.get(1) && 
/* 117 */       get(3) == paramLoginOptions.get(3) && 
/* 118 */       get(8) == paramLoginOptions.get(8)) {
/* 119 */       bool = true;
/*     */     }
/*     */ 
/*     */     
/* 123 */     return bool;
/*     */   }
/*     */   public boolean match(TicketFlags paramTicketFlags) {
/* 126 */     boolean bool = true;
/* 127 */     for (byte b = 0; b <= 31; b++) {
/* 128 */       if (get(b) != paramTicketFlags.get(b)) {
/* 129 */         return false;
/*     */       }
/*     */     } 
/* 132 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 140 */     StringBuffer stringBuffer = new StringBuffer();
/* 141 */     boolean[] arrayOfBoolean = toBooleanArray();
/* 142 */     for (byte b = 0; b < arrayOfBoolean.length; b++) {
/* 143 */       if (arrayOfBoolean[b] == true) {
/* 144 */         switch (b) {
/*     */           case 0:
/* 146 */             stringBuffer.append("RESERVED;");
/*     */             break;
/*     */           case 1:
/* 149 */             stringBuffer.append("FORWARDABLE;");
/*     */             break;
/*     */           case 2:
/* 152 */             stringBuffer.append("FORWARDED;");
/*     */             break;
/*     */           case 3:
/* 155 */             stringBuffer.append("PROXIABLE;");
/*     */             break;
/*     */           case 4:
/* 158 */             stringBuffer.append("PROXY;");
/*     */             break;
/*     */           case 5:
/* 161 */             stringBuffer.append("MAY-POSTDATE;");
/*     */             break;
/*     */           case 6:
/* 164 */             stringBuffer.append("POSTDATED;");
/*     */             break;
/*     */           case 7:
/* 167 */             stringBuffer.append("INVALID;");
/*     */             break;
/*     */           case 8:
/* 170 */             stringBuffer.append("RENEWABLE;");
/*     */             break;
/*     */           case 9:
/* 173 */             stringBuffer.append("INITIAL;");
/*     */             break;
/*     */           case 10:
/* 176 */             stringBuffer.append("PRE-AUTHENT;");
/*     */             break;
/*     */           case 11:
/* 179 */             stringBuffer.append("HW-AUTHENT;");
/*     */             break;
/*     */         } 
/*     */       }
/*     */     } 
/* 184 */     String str = stringBuffer.toString();
/* 185 */     if (str.length() > 0) {
/* 186 */       str = str.substring(0, str.length() - 1);
/*     */     }
/* 188 */     return str;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\TicketFlags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */