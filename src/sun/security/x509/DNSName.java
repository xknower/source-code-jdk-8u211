/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Locale;
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
/*     */ public class DNSName
/*     */   implements GeneralNameInterface
/*     */ {
/*     */   private String name;
/*     */   private static final String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
/*     */   private static final String digitsAndHyphen = "0123456789-";
/*     */   private static final String alphaDigitsAndHyphen = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-";
/*     */   
/*     */   public DNSName(DerValue paramDerValue) throws IOException {
/*  65 */     this.name = paramDerValue.getIA5String();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DNSName(String paramString) throws IOException {
/*  75 */     if (paramString == null || paramString.length() == 0)
/*  76 */       throw new IOException("DNS name must not be null"); 
/*  77 */     if (paramString.indexOf(' ') != -1)
/*  78 */       throw new IOException("DNS names or NameConstraints with blank components are not permitted"); 
/*  79 */     if (paramString.charAt(0) == '.' || paramString.charAt(paramString.length() - 1) == '.') {
/*  80 */       throw new IOException("DNS names or NameConstraints may not begin or end with a .");
/*     */     }
/*     */ 
/*     */     
/*  84 */     for (int i = 0; i < paramString.length(); i = j + 1) {
/*  85 */       int j = paramString.indexOf('.', i);
/*  86 */       if (j < 0) {
/*  87 */         j = paramString.length();
/*     */       }
/*  89 */       if (j - i < 1) {
/*  90 */         throw new IOException("DNSName SubjectAltNames with empty components are not permitted");
/*     */       }
/*     */       
/*  93 */       if ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".indexOf(paramString.charAt(i)) < 0) {
/*  94 */         throw new IOException("DNSName components must begin with a letter");
/*     */       }
/*  96 */       for (int k = i + 1; k < j; k++) {
/*  97 */         char c = paramString.charAt(k);
/*  98 */         if ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-".indexOf(c) < 0)
/*  99 */           throw new IOException("DNSName components must consist of letters, digits, and hyphens"); 
/*     */       } 
/*     */     } 
/* 102 */     this.name = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 109 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 116 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 126 */     paramDerOutputStream.putIA5String(this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 133 */     return "DNSName: " + this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 143 */     if (this == paramObject) {
/* 144 */       return true;
/*     */     }
/* 146 */     if (!(paramObject instanceof DNSName)) {
/* 147 */       return false;
/*     */     }
/* 149 */     DNSName dNSName = (DNSName)paramObject;
/*     */ 
/*     */ 
/*     */     
/* 153 */     return this.name.equalsIgnoreCase(dNSName.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 162 */     return this.name.toUpperCase(Locale.ENGLISH).hashCode();
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
/*     */   public int constrains(GeneralNameInterface paramGeneralNameInterface) throws UnsupportedOperationException {
/*     */     byte b;
/* 197 */     if (paramGeneralNameInterface == null) {
/* 198 */       b = -1;
/* 199 */     } else if (paramGeneralNameInterface.getType() != 2) {
/* 200 */       b = -1;
/*     */     } else {
/*     */       
/* 203 */       String str1 = ((DNSName)paramGeneralNameInterface).getName().toLowerCase(Locale.ENGLISH);
/* 204 */       String str2 = this.name.toLowerCase(Locale.ENGLISH);
/* 205 */       if (str1.equals(str2))
/* 206 */       { b = 0; }
/* 207 */       else if (str2.endsWith(str1))
/* 208 */       { int i = str2.lastIndexOf(str1);
/* 209 */         if (str2.charAt(i - 1) == '.')
/* 210 */         { b = 2; }
/*     */         else
/* 212 */         { b = 3; }  }
/* 213 */       else if (str1.endsWith(str2))
/* 214 */       { int i = str1.lastIndexOf(str2);
/* 215 */         if (str1.charAt(i - 1) == '.') {
/* 216 */           b = 1;
/*     */         } else {
/* 218 */           b = 3;
/*     */         }  }
/* 220 */       else { b = 3; }
/*     */     
/*     */     } 
/* 223 */     return b;
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
/*     */   public int subtreeDepth() throws UnsupportedOperationException {
/* 236 */     byte b = 1;
/*     */ 
/*     */     
/* 239 */     for (int i = this.name.indexOf('.'); i >= 0; i = this.name.indexOf('.', i + 1)) {
/* 240 */       b++;
/*     */     }
/*     */     
/* 243 */     return b;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\x509\DNSName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */