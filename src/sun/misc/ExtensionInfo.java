/*     */ package sun.misc;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.jar.Attributes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtensionInfo
/*     */ {
/*     */   public static final int COMPATIBLE = 0;
/*     */   public static final int REQUIRE_SPECIFICATION_UPGRADE = 1;
/*     */   public static final int REQUIRE_IMPLEMENTATION_UPGRADE = 2;
/*     */   public static final int REQUIRE_VENDOR_SWITCH = 3;
/*     */   public static final int INCOMPATIBLE = 4;
/*     */   public String title;
/*     */   public String name;
/*     */   public String specVersion;
/*     */   public String specVendor;
/*     */   public String implementationVersion;
/*     */   public String vendor;
/*     */   public String vendorId;
/*     */   public String url;
/*  73 */   private static final ResourceBundle rb = ResourceBundle.getBundle("sun.misc.resources.Messages");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionInfo() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionInfo(String paramString, Attributes paramAttributes) throws NullPointerException {
/*     */     String str1;
/* 102 */     if (paramString != null) {
/* 103 */       str1 = paramString + "-";
/*     */     } else {
/* 105 */       str1 = "";
/*     */     } 
/*     */     
/* 108 */     String str2 = str1 + Attributes.Name.EXTENSION_NAME.toString();
/* 109 */     this.name = paramAttributes.getValue(str2);
/* 110 */     if (this.name != null) {
/* 111 */       this.name = this.name.trim();
/*     */     }
/* 113 */     str2 = str1 + Attributes.Name.SPECIFICATION_TITLE.toString();
/* 114 */     this.title = paramAttributes.getValue(str2);
/* 115 */     if (this.title != null) {
/* 116 */       this.title = this.title.trim();
/*     */     }
/* 118 */     str2 = str1 + Attributes.Name.SPECIFICATION_VERSION.toString();
/* 119 */     this.specVersion = paramAttributes.getValue(str2);
/* 120 */     if (this.specVersion != null) {
/* 121 */       this.specVersion = this.specVersion.trim();
/*     */     }
/* 123 */     str2 = str1 + Attributes.Name.SPECIFICATION_VENDOR.toString();
/* 124 */     this.specVendor = paramAttributes.getValue(str2);
/* 125 */     if (this.specVendor != null) {
/* 126 */       this.specVendor = this.specVendor.trim();
/*     */     }
/* 128 */     str2 = str1 + Attributes.Name.IMPLEMENTATION_VERSION.toString();
/* 129 */     this.implementationVersion = paramAttributes.getValue(str2);
/* 130 */     if (this.implementationVersion != null) {
/* 131 */       this.implementationVersion = this.implementationVersion.trim();
/*     */     }
/* 133 */     str2 = str1 + Attributes.Name.IMPLEMENTATION_VENDOR.toString();
/* 134 */     this.vendor = paramAttributes.getValue(str2);
/* 135 */     if (this.vendor != null) {
/* 136 */       this.vendor = this.vendor.trim();
/*     */     }
/* 138 */     str2 = str1 + Attributes.Name.IMPLEMENTATION_VENDOR_ID.toString();
/* 139 */     this.vendorId = paramAttributes.getValue(str2);
/* 140 */     if (this.vendorId != null) {
/* 141 */       this.vendorId = this.vendorId.trim();
/*     */     }
/* 143 */     str2 = str1 + Attributes.Name.IMPLEMENTATION_URL.toString();
/* 144 */     this.url = paramAttributes.getValue(str2);
/* 145 */     if (this.url != null) {
/* 146 */       this.url = this.url.trim();
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
/*     */   public int isCompatibleWith(ExtensionInfo paramExtensionInfo) {
/* 160 */     if (this.name == null || paramExtensionInfo.name == null)
/* 161 */       return 4; 
/* 162 */     if (this.name.compareTo(paramExtensionInfo.name) == 0) {
/*     */ 
/*     */       
/* 165 */       if (this.specVersion == null || paramExtensionInfo.specVersion == null) {
/* 166 */         return 0;
/*     */       }
/* 168 */       int i = compareExtensionVersion(this.specVersion, paramExtensionInfo.specVersion);
/* 169 */       if (i < 0) {
/*     */         
/* 171 */         if (this.vendorId != null && paramExtensionInfo.vendorId != null && 
/* 172 */           this.vendorId.compareTo(paramExtensionInfo.vendorId) != 0) {
/* 173 */           return 3;
/*     */         }
/*     */         
/* 176 */         return 1;
/*     */       } 
/*     */ 
/*     */       
/* 180 */       if (this.vendorId != null && paramExtensionInfo.vendorId != null) {
/*     */         
/* 182 */         if (this.vendorId.compareTo(paramExtensionInfo.vendorId) != 0)
/*     */         {
/* 184 */           return 3;
/*     */         }
/*     */         
/* 187 */         if (this.implementationVersion != null && paramExtensionInfo.implementationVersion != null) {
/*     */           
/* 189 */           i = compareExtensionVersion(this.implementationVersion, paramExtensionInfo.implementationVersion);
/* 190 */           if (i < 0)
/*     */           {
/* 192 */             return 2;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 198 */       return 0;
/*     */     } 
/*     */     
/* 201 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 211 */     return "Extension : title(" + this.title + "), name(" + this.name + "), spec vendor(" + this.specVendor + "), spec version(" + this.specVersion + "), impl vendor(" + this.vendor + "), impl vendor id(" + this.vendorId + "), impl version(" + this.implementationVersion + "), impl url(" + this.url + ")";
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
/*     */   private int compareExtensionVersion(String paramString1, String paramString2) throws NumberFormatException {
/* 231 */     paramString1 = paramString1.toLowerCase();
/* 232 */     paramString2 = paramString2.toLowerCase();
/*     */     
/* 234 */     return strictCompareExtensionVersion(paramString1, paramString2);
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
/*     */   private int strictCompareExtensionVersion(String paramString1, String paramString2) throws NumberFormatException {
/* 252 */     if (paramString1.equals(paramString2)) {
/* 253 */       return 0;
/*     */     }
/* 255 */     StringTokenizer stringTokenizer1 = new StringTokenizer(paramString1, ".,");
/* 256 */     StringTokenizer stringTokenizer2 = new StringTokenizer(paramString2, ".,");
/*     */ 
/*     */     
/* 259 */     int i = 0, j = 0; boolean bool = false;
/*     */ 
/*     */     
/* 262 */     if (stringTokenizer1.hasMoreTokens()) {
/* 263 */       i = convertToken(stringTokenizer1.nextToken().toString());
/*     */     }
/*     */     
/* 266 */     if (stringTokenizer2.hasMoreTokens()) {
/* 267 */       j = convertToken(stringTokenizer2.nextToken().toString());
/*     */     }
/* 269 */     if (i > j)
/* 270 */       return 1; 
/* 271 */     if (j > i) {
/* 272 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 276 */     int k = paramString1.indexOf(".");
/* 277 */     int m = paramString2.indexOf(".");
/*     */     
/* 279 */     if (k == -1) {
/* 280 */       k = paramString1.length() - 1;
/*     */     }
/* 282 */     if (m == -1) {
/* 283 */       m = paramString2.length() - 1;
/*     */     }
/* 285 */     return strictCompareExtensionVersion(paramString1.substring(k + 1), paramString2
/* 286 */         .substring(m + 1));
/*     */   }
/*     */ 
/*     */   
/*     */   private int convertToken(String paramString) {
/*     */     int i3;
/* 292 */     if (paramString == null || paramString.equals("")) {
/* 293 */       return 0;
/*     */     }
/* 295 */     int i = 0;
/* 296 */     int j = 0;
/* 297 */     int k = 0;
/* 298 */     int m = paramString.length();
/* 299 */     int n = m;
/*     */ 
/*     */     
/* 302 */     Object[] arrayOfObject = { this.name };
/* 303 */     MessageFormat messageFormat = new MessageFormat(rb.getString("optpkg.versionerror"));
/* 304 */     String str1 = messageFormat.format(arrayOfObject);
/*     */ 
/*     */     
/* 307 */     int i1 = paramString.indexOf("-");
/*     */ 
/*     */     
/* 310 */     int i2 = paramString.indexOf("_");
/*     */     
/* 312 */     if (i1 == -1 && i2 == -1) {
/*     */       
/*     */       try {
/*     */         
/* 316 */         return Integer.parseInt(paramString) * 100;
/* 317 */       } catch (NumberFormatException numberFormatException) {
/* 318 */         System.out.println(str1);
/* 319 */         return 0;
/*     */       } 
/*     */     }
/* 322 */     if (i2 != -1) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 328 */         i3 = Integer.parseInt(paramString.substring(0, i2));
/*     */ 
/*     */         
/* 331 */         char c = paramString.charAt(m - 1);
/* 332 */         if (Character.isLetter(c)) {
/*     */           
/* 334 */           i = Character.getNumericValue(c);
/* 335 */           n = m - 1;
/*     */ 
/*     */           
/* 338 */           k = Integer.parseInt(paramString.substring(i2 + 1, n));
/*     */           
/* 340 */           if (i >= Character.getNumericValue('a') && i <= Character.getNumericValue('z')) {
/*     */             
/* 342 */             j = k * 100 + i;
/*     */           } else {
/*     */             
/* 345 */             j = 0;
/* 346 */             System.out.println(str1);
/*     */           } 
/*     */         } else {
/*     */           
/* 350 */           k = Integer.parseInt(paramString.substring(i2 + 1, n));
/*     */         } 
/* 352 */       } catch (NumberFormatException numberFormatException) {
/* 353 */         System.out.println(str1);
/* 354 */         return 0;
/*     */       } 
/* 356 */       return i3 * 100 + k + j;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 365 */       i3 = Integer.parseInt(paramString.substring(0, i1));
/* 366 */     } catch (NumberFormatException numberFormatException) {
/* 367 */       System.out.println(str1);
/* 368 */       return 0;
/*     */     } 
/*     */ 
/*     */     
/* 372 */     String str2 = paramString.substring(i1 + 1);
/*     */ 
/*     */     
/* 375 */     String str3 = "";
/* 376 */     byte b = 0;
/*     */     
/* 378 */     if (str2.indexOf("ea") != -1) {
/*     */       
/* 380 */       str3 = str2.substring(2);
/* 381 */       b = 50;
/*     */     }
/* 383 */     else if (str2.indexOf("alpha") != -1) {
/*     */       
/* 385 */       str3 = str2.substring(5);
/* 386 */       b = 40;
/*     */     }
/* 388 */     else if (str2.indexOf("beta") != -1) {
/*     */       
/* 390 */       str3 = str2.substring(4);
/* 391 */       b = 30;
/*     */     }
/* 393 */     else if (str2.indexOf("rc") != -1) {
/*     */       
/* 395 */       str3 = str2.substring(2);
/* 396 */       b = 20;
/*     */     } 
/*     */     
/* 399 */     if (str3 == null || str3.equals(""))
/*     */     {
/*     */       
/* 402 */       return i3 * 100 - b;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 408 */       return i3 * 100 - b + Integer.parseInt(str3);
/* 409 */     } catch (NumberFormatException numberFormatException) {
/* 410 */       System.out.println(str1);
/* 411 */       return 0;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\ExtensionInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */