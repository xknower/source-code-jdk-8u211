/*     */ package java.io;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.util.Locale;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WinNTFileSystem
/*     */   extends FileSystem
/*     */ {
/*  45 */   private final char slash = ((String)AccessController.<String>doPrivileged(new GetPropertyAction("file.separator")))
/*  46 */     .charAt(0);
/*  47 */   private final char semicolon = ((String)AccessController.<String>doPrivileged(new GetPropertyAction("path.separator")))
/*  48 */     .charAt(0);
/*  49 */   private final char altSlash = (this.slash == '\\') ? '/' : '\\';
/*     */ 
/*     */   
/*     */   private boolean isSlash(char paramChar) {
/*  53 */     return (paramChar == '\\' || paramChar == '/');
/*     */   }
/*     */   
/*     */   private boolean isLetter(char paramChar) {
/*  57 */     return ((paramChar >= 'a' && paramChar <= 'z') || (paramChar >= 'A' && paramChar <= 'Z'));
/*     */   }
/*     */   
/*     */   private String slashify(String paramString) {
/*  61 */     if (paramString.length() > 0 && paramString.charAt(0) != this.slash) return this.slash + paramString; 
/*  62 */     return paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getSeparator() {
/*  69 */     return this.slash;
/*     */   }
/*     */ 
/*     */   
/*     */   public char getPathSeparator() {
/*  74 */     return this.semicolon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String normalize(String paramString) {
/*  82 */     int i = paramString.length();
/*  83 */     char c1 = this.slash;
/*  84 */     char c2 = this.altSlash;
/*  85 */     char c = Character.MIN_VALUE;
/*  86 */     for (byte b = 0; b < i; b++) {
/*  87 */       char c3 = paramString.charAt(b);
/*  88 */       if (c3 == c2)
/*  89 */         return normalize(paramString, i, (c == c1) ? (b - 1) : b); 
/*  90 */       if (c3 == c1 && c == c1 && b > 1)
/*  91 */         return normalize(paramString, i, b - 1); 
/*  92 */       if (c3 == ':' && b > 1)
/*  93 */         return normalize(paramString, i, 0); 
/*  94 */       c = c3;
/*     */     } 
/*  96 */     if (c == c1) return normalize(paramString, i, i - 1); 
/*  97 */     return paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   private String normalize(String paramString, int paramInt1, int paramInt2) {
/*     */     int i;
/* 103 */     if (paramInt1 == 0) return paramString; 
/* 104 */     if (paramInt2 < 3) paramInt2 = 0;
/*     */     
/* 106 */     char c = this.slash;
/* 107 */     StringBuffer stringBuffer = new StringBuffer(paramInt1);
/*     */     
/* 109 */     if (paramInt2 == 0) {
/*     */       
/* 111 */       i = normalizePrefix(paramString, paramInt1, stringBuffer);
/*     */     } else {
/*     */       
/* 114 */       i = paramInt2;
/* 115 */       stringBuffer.append(paramString.substring(0, paramInt2));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 120 */     while (i < paramInt1) {
/* 121 */       char c1 = paramString.charAt(i++);
/* 122 */       if (isSlash(c1)) {
/* 123 */         for (; i < paramInt1 && isSlash(paramString.charAt(i)); i++);
/* 124 */         if (i == paramInt1) {
/*     */           
/* 126 */           int j = stringBuffer.length();
/* 127 */           if (j == 2 && stringBuffer.charAt(1) == ':') {
/*     */             
/* 129 */             stringBuffer.append(c);
/*     */             break;
/*     */           } 
/* 132 */           if (j == 0) {
/*     */             
/* 134 */             stringBuffer.append(c);
/*     */             break;
/*     */           } 
/* 137 */           if (j == 1 && isSlash(stringBuffer.charAt(0)))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 145 */             stringBuffer.append(c);
/*     */           }
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 152 */         stringBuffer.append(c);
/*     */         continue;
/*     */       } 
/* 155 */       stringBuffer.append(c1);
/*     */     } 
/*     */ 
/*     */     
/* 159 */     return stringBuffer.toString();
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
/*     */   private int normalizePrefix(String paramString, int paramInt, StringBuffer paramStringBuffer) {
/* 176 */     byte b = 0;
/* 177 */     for (; b < paramInt && isSlash(paramString.charAt(b)); b++);
/*     */     char c;
/* 179 */     if (paramInt - b >= 2 && 
/* 180 */       isLetter(c = paramString.charAt(b)) && paramString
/* 181 */       .charAt(b + 1) == ':') {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 186 */       paramStringBuffer.append(c);
/* 187 */       paramStringBuffer.append(':');
/* 188 */       b += 2;
/*     */     } else {
/* 190 */       b = 0;
/* 191 */       if (paramInt >= 2 && 
/* 192 */         isSlash(paramString.charAt(0)) && 
/* 193 */         isSlash(paramString.charAt(1))) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 199 */         b = 1;
/* 200 */         paramStringBuffer.append(this.slash);
/*     */       } 
/*     */     } 
/* 203 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   public int prefixLength(String paramString) {
/* 208 */     char c1 = this.slash;
/* 209 */     int i = paramString.length();
/* 210 */     if (i == 0) return 0; 
/* 211 */     char c2 = paramString.charAt(0);
/* 212 */     char c = (i > 1) ? paramString.charAt(1) : Character.MIN_VALUE;
/* 213 */     if (c2 == c1) {
/* 214 */       if (c == c1) return 2; 
/* 215 */       return 1;
/*     */     } 
/* 217 */     if (isLetter(c2) && c == ':') {
/* 218 */       if (i > 2 && paramString.charAt(2) == c1)
/* 219 */         return 3; 
/* 220 */       return 2;
/*     */     } 
/* 222 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String resolve(String paramString1, String paramString2) {
/* 227 */     int i = paramString1.length();
/* 228 */     if (i == 0) return paramString2; 
/* 229 */     int j = paramString2.length();
/* 230 */     if (j == 0) return paramString1;
/*     */     
/* 232 */     String str = paramString2;
/* 233 */     byte b = 0;
/* 234 */     int k = i;
/*     */     
/* 236 */     if (j > 1 && str.charAt(0) == this.slash) {
/* 237 */       if (str.charAt(1) == this.slash) {
/*     */         
/* 239 */         b = 2;
/*     */       } else {
/*     */         
/* 242 */         b = 1;
/*     */       } 
/*     */       
/* 245 */       if (j == b) {
/* 246 */         if (paramString1.charAt(i - 1) == this.slash)
/* 247 */           return paramString1.substring(0, i - 1); 
/* 248 */         return paramString1;
/*     */       } 
/*     */     } 
/*     */     
/* 252 */     if (paramString1.charAt(i - 1) == this.slash) {
/* 253 */       k--;
/*     */     }
/* 255 */     int m = k + j - b;
/* 256 */     char[] arrayOfChar = null;
/* 257 */     if (paramString2.charAt(b) == this.slash) {
/* 258 */       arrayOfChar = new char[m];
/* 259 */       paramString1.getChars(0, k, arrayOfChar, 0);
/* 260 */       paramString2.getChars(b, j, arrayOfChar, k);
/*     */     } else {
/* 262 */       arrayOfChar = new char[m + 1];
/* 263 */       paramString1.getChars(0, k, arrayOfChar, 0);
/* 264 */       arrayOfChar[k] = this.slash;
/* 265 */       paramString2.getChars(b, j, arrayOfChar, k + 1);
/*     */     } 
/* 267 */     return new String(arrayOfChar);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDefaultParent() {
/* 272 */     return "" + this.slash;
/*     */   }
/*     */ 
/*     */   
/*     */   public String fromURIPath(String paramString) {
/* 277 */     String str = paramString;
/* 278 */     if (str.length() > 2 && str.charAt(2) == ':') {
/*     */       
/* 280 */       str = str.substring(1);
/*     */       
/* 282 */       if (str.length() > 3 && str.endsWith("/"))
/* 283 */         str = str.substring(0, str.length() - 1); 
/* 284 */     } else if (str.length() > 1 && str.endsWith("/")) {
/*     */       
/* 286 */       str = str.substring(0, str.length() - 1);
/*     */     } 
/* 288 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAbsolute(File paramFile) {
/* 295 */     int i = paramFile.getPrefixLength();
/* 296 */     return ((i == 2 && paramFile.getPath().charAt(0) == this.slash) || i == 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String resolve(File paramFile) {
/* 302 */     String str = paramFile.getPath();
/* 303 */     int i = paramFile.getPrefixLength();
/* 304 */     if (i == 2 && str.charAt(0) == this.slash)
/* 305 */       return str; 
/* 306 */     if (i == 3)
/* 307 */       return str; 
/* 308 */     if (i == 0)
/* 309 */       return getUserPath() + slashify(str); 
/* 310 */     if (i == 1) {
/* 311 */       String str1 = getUserPath();
/* 312 */       String str2 = getDrive(str1);
/* 313 */       if (str2 != null) return str2 + str; 
/* 314 */       return str1 + str;
/*     */     } 
/* 316 */     if (i == 2) {
/* 317 */       String str1 = getUserPath();
/* 318 */       String str2 = getDrive(str1);
/* 319 */       if (str2 != null && str.startsWith(str2))
/* 320 */         return str1 + slashify(str.substring(2)); 
/* 321 */       char c = str.charAt(0);
/* 322 */       String str3 = getDriveDirectory(c);
/*     */       
/* 324 */       if (str3 != null) {
/*     */ 
/*     */ 
/*     */         
/* 328 */         String str4 = c + ':' + str3 + slashify(str.substring(2));
/* 329 */         SecurityManager securityManager = System.getSecurityManager();
/*     */         try {
/* 331 */           if (securityManager != null) securityManager.checkRead(str4); 
/* 332 */         } catch (SecurityException securityException) {
/*     */           
/* 334 */           throw new SecurityException("Cannot resolve path " + str);
/*     */         } 
/* 336 */         return str4;
/*     */       } 
/* 338 */       return c + ":" + slashify(str.substring(2));
/*     */     } 
/* 340 */     throw new InternalError("Unresolvable path: " + str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getUserPath() {
/* 346 */     return normalize(System.getProperty("user.dir"));
/*     */   }
/*     */   
/*     */   private String getDrive(String paramString) {
/* 350 */     int i = prefixLength(paramString);
/* 351 */     return (i == 3) ? paramString.substring(0, 2) : null;
/*     */   }
/*     */   
/* 354 */   private static String[] driveDirCache = new String[26];
/*     */   
/*     */   private static int driveIndex(char paramChar) {
/* 357 */     if (paramChar >= 'a' && paramChar <= 'z') return paramChar - 97; 
/* 358 */     if (paramChar >= 'A' && paramChar <= 'Z') return paramChar - 65; 
/* 359 */     return -1;
/*     */   }
/*     */   
/*     */   private native String getDriveDirectory(int paramInt);
/*     */   
/*     */   private String getDriveDirectory(char paramChar) {
/* 365 */     int i = driveIndex(paramChar);
/* 366 */     if (i < 0) return null; 
/* 367 */     String str = driveDirCache[i];
/* 368 */     if (str != null) return str; 
/* 369 */     str = getDriveDirectory(i + 1);
/* 370 */     driveDirCache[i] = str;
/* 371 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 380 */   private ExpiringCache cache = new ExpiringCache();
/* 381 */   private ExpiringCache prefixCache = new ExpiringCache();
/*     */ 
/*     */ 
/*     */   
/*     */   public String canonicalize(String paramString) throws IOException {
/* 386 */     int i = paramString.length();
/* 387 */     if (i == 2 && 
/* 388 */       isLetter(paramString.charAt(0)) && paramString
/* 389 */       .charAt(1) == ':') {
/* 390 */       char c = paramString.charAt(0);
/* 391 */       if (c >= 'A' && c <= 'Z')
/* 392 */         return paramString; 
/* 393 */       return "" + (char)(c - 32) + ':';
/* 394 */     }  if (i == 3 && 
/* 395 */       isLetter(paramString.charAt(0)) && paramString
/* 396 */       .charAt(1) == ':' && paramString
/* 397 */       .charAt(2) == '\\') {
/* 398 */       char c = paramString.charAt(0);
/* 399 */       if (c >= 'A' && c <= 'Z')
/* 400 */         return paramString; 
/* 401 */       return "" + (char)(c - 32) + ':' + '\\';
/*     */     } 
/* 403 */     if (!useCanonCaches) {
/* 404 */       return canonicalize0(paramString);
/*     */     }
/* 406 */     String str = this.cache.get(paramString);
/* 407 */     if (str == null) {
/* 408 */       String str1 = null;
/* 409 */       String str2 = null;
/* 410 */       if (useCanonPrefixCache) {
/* 411 */         str1 = parentOrNull(paramString);
/* 412 */         if (str1 != null) {
/* 413 */           str2 = this.prefixCache.get(str1);
/* 414 */           if (str2 != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 421 */             String str3 = paramString.substring(1 + str1.length());
/* 422 */             str = canonicalizeWithPrefix(str2, str3);
/* 423 */             this.cache.put(str1 + File.separatorChar + str3, str);
/*     */           } 
/*     */         } 
/*     */       } 
/* 427 */       if (str == null) {
/* 428 */         str = canonicalize0(paramString);
/* 429 */         this.cache.put(paramString, str);
/* 430 */         if (useCanonPrefixCache && str1 != null) {
/* 431 */           str2 = parentOrNull(str);
/* 432 */           if (str2 != null) {
/* 433 */             File file = new File(str);
/* 434 */             if (file.exists() && !file.isDirectory()) {
/* 435 */               this.prefixCache.put(str1, str2);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 441 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native String canonicalize0(String paramString) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   private String canonicalizeWithPrefix(String paramString1, String paramString2) throws IOException {
/* 451 */     return canonicalizeWithPrefix0(paramString1, paramString1 + File.separatorChar + paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native String canonicalizeWithPrefix0(String paramString1, String paramString2) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String parentOrNull(String paramString) {
/* 470 */     if (paramString == null) return null; 
/* 471 */     char c = File.separatorChar;
/* 472 */     byte b1 = 47;
/* 473 */     int i = paramString.length() - 1;
/* 474 */     int j = i;
/* 475 */     byte b2 = 0;
/* 476 */     byte b3 = 0;
/* 477 */     while (j > 0) {
/* 478 */       char c1 = paramString.charAt(j);
/* 479 */       if (c1 == '.') {
/* 480 */         if (++b2 >= 2)
/*     */         {
/* 482 */           return null;
/*     */         }
/* 484 */         if (!b3)
/*     */         {
/* 486 */           return null; } 
/*     */       } else {
/* 488 */         if (c1 == c) {
/* 489 */           if (b2 == 1 && !b3)
/*     */           {
/* 491 */             return null;
/*     */           }
/* 493 */           if (j == 0 || j >= i - 1 || paramString
/*     */             
/* 495 */             .charAt(j - 1) == c || paramString
/* 496 */             .charAt(j - 1) == b1)
/*     */           {
/*     */             
/* 499 */             return null;
/*     */           }
/* 501 */           return paramString.substring(0, j);
/* 502 */         }  if (c1 == b1)
/*     */         {
/*     */           
/* 505 */           return null; } 
/* 506 */         if (c1 == '*' || c1 == '?')
/*     */         {
/* 508 */           return null;
/*     */         }
/* 510 */         b3++;
/* 511 */         b2 = 0;
/*     */       } 
/* 513 */       j--;
/*     */     } 
/* 515 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public native int getBooleanAttributes(File paramFile);
/*     */ 
/*     */ 
/*     */   
/*     */   public native boolean checkAccess(File paramFile, int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   public native long getLastModifiedTime(File paramFile);
/*     */ 
/*     */ 
/*     */   
/*     */   public native long getLength(File paramFile);
/*     */ 
/*     */ 
/*     */   
/*     */   public native boolean setPermission(File paramFile, int paramInt, boolean paramBoolean1, boolean paramBoolean2);
/*     */ 
/*     */ 
/*     */   
/*     */   public native boolean createFileExclusively(String paramString) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   public native String[] list(File paramFile);
/*     */ 
/*     */ 
/*     */   
/*     */   public native boolean createDirectory(File paramFile);
/*     */ 
/*     */ 
/*     */   
/*     */   public native boolean setLastModifiedTime(File paramFile, long paramLong);
/*     */ 
/*     */ 
/*     */   
/*     */   public native boolean setReadOnly(File paramFile);
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean delete(File paramFile) {
/* 561 */     this.cache.clear();
/* 562 */     this.prefixCache.clear();
/* 563 */     return delete0(paramFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native boolean delete0(File paramFile);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean rename(File paramFile1, File paramFile2) {
/* 575 */     this.cache.clear();
/* 576 */     this.prefixCache.clear();
/* 577 */     return rename0(paramFile1, paramFile2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native boolean rename0(File paramFile1, File paramFile2);
/*     */ 
/*     */   
/*     */   public File[] listRoots() {
/* 586 */     int i = listRoots0();
/* 587 */     byte b1 = 0;
/* 588 */     for (byte b2 = 0; b2 < 26; b2++) {
/* 589 */       if ((i >> b2 & 0x1) != 0)
/* 590 */         if (!access((char)(65 + b2) + ":" + this.slash)) {
/* 591 */           i &= 1 << b2 ^ 0xFFFFFFFF;
/*     */         } else {
/* 593 */           b1++;
/*     */         }  
/*     */     } 
/* 596 */     File[] arrayOfFile = new File[b1];
/* 597 */     byte b3 = 0;
/* 598 */     char c = this.slash;
/* 599 */     for (byte b4 = 0; b4 < 26; b4++) {
/* 600 */       if ((i >> b4 & 0x1) != 0)
/* 601 */         arrayOfFile[b3++] = new File((char)(65 + b4) + ":" + c); 
/*     */     } 
/* 603 */     return arrayOfFile;
/*     */   }
/*     */   
/*     */   private static native int listRoots0();
/*     */   
/*     */   private boolean access(String paramString) {
/*     */     try {
/* 610 */       SecurityManager securityManager = System.getSecurityManager();
/* 611 */       if (securityManager != null) securityManager.checkRead(paramString); 
/* 612 */       return true;
/* 613 */     } catch (SecurityException securityException) {
/* 614 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSpace(File paramFile, int paramInt) {
/* 622 */     if (paramFile.exists()) {
/* 623 */       return getSpace0(paramFile, paramInt);
/*     */     }
/* 625 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native long getSpace0(File paramFile, int paramInt);
/*     */ 
/*     */   
/*     */   public int compare(File paramFile1, File paramFile2) {
/* 634 */     return paramFile1.getPath().compareToIgnoreCase(paramFile2.getPath());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode(File paramFile) {
/* 640 */     return paramFile.getPath().toLowerCase(Locale.ENGLISH).hashCode() ^ 0x12D591;
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   static {
/* 646 */     initIDs();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\io\WinNTFileSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */