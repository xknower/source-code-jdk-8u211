/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.nio.file.InvalidPathException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WindowsPathParser
/*     */ {
/*     */   private static final String reservedChars = "<>:\"|?*";
/*     */   
/*     */   static class Result
/*     */   {
/*     */     private final WindowsPathType type;
/*     */     private final String root;
/*     */     private final String path;
/*     */     
/*     */     Result(WindowsPathType param1WindowsPathType, String param1String1, String param1String2) {
/*  46 */       this.type = param1WindowsPathType;
/*  47 */       this.root = param1String1;
/*  48 */       this.path = param1String2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     WindowsPathType type() {
/*  55 */       return this.type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String root() {
/*  62 */       return this.root;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String path() {
/*  69 */       return this.path;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Result parse(String paramString) {
/*  77 */     return parse(paramString, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Result parseNormalizedPath(String paramString) {
/*  85 */     return parse(paramString, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Result parse(String paramString, boolean paramBoolean) {
/*  95 */     String str = "";
/*  96 */     WindowsPathType windowsPathType = null;
/*     */     
/*  98 */     int i = paramString.length();
/*  99 */     int j = 0;
/* 100 */     if (i > 1) {
/* 101 */       char c1 = paramString.charAt(0);
/* 102 */       char c2 = paramString.charAt(1);
/* 103 */       boolean bool = false;
/* 104 */       int k = 2;
/* 105 */       if (isSlash(c1) && isSlash(c2)) {
/*     */ 
/*     */ 
/*     */         
/* 109 */         windowsPathType = WindowsPathType.UNC;
/* 110 */         j = nextNonSlash(paramString, k, i);
/* 111 */         k = nextSlash(paramString, j, i);
/* 112 */         if (j == k)
/* 113 */           throw new InvalidPathException(paramString, "UNC path is missing hostname"); 
/* 114 */         String str1 = paramString.substring(j, k);
/* 115 */         j = nextNonSlash(paramString, k, i);
/* 116 */         k = nextSlash(paramString, j, i);
/* 117 */         if (j == k)
/* 118 */           throw new InvalidPathException(paramString, "UNC path is missing sharename"); 
/* 119 */         str = "\\\\" + str1 + "\\" + paramString.substring(j, k) + "\\";
/* 120 */         j = k;
/*     */       }
/* 122 */       else if (isLetter(c1) && c2 == ':') {
/*     */         char c;
/* 124 */         if (i > 2 && isSlash(c = paramString.charAt(2))) {
/*     */           
/* 126 */           if (c == '\\') {
/* 127 */             str = paramString.substring(0, 3);
/*     */           } else {
/* 129 */             str = paramString.substring(0, 2) + '\\';
/*     */           } 
/* 131 */           j = 3;
/* 132 */           windowsPathType = WindowsPathType.ABSOLUTE;
/*     */         } else {
/* 134 */           str = paramString.substring(0, 2);
/* 135 */           j = 2;
/* 136 */           windowsPathType = WindowsPathType.DRIVE_RELATIVE;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     if (j == 0) {
/* 142 */       if (i > 0 && isSlash(paramString.charAt(0))) {
/* 143 */         windowsPathType = WindowsPathType.DIRECTORY_RELATIVE;
/* 144 */         str = "\\";
/*     */       } else {
/* 146 */         windowsPathType = WindowsPathType.RELATIVE;
/*     */       } 
/*     */     }
/*     */     
/* 150 */     if (paramBoolean) {
/* 151 */       StringBuilder stringBuilder = new StringBuilder(paramString.length());
/* 152 */       stringBuilder.append(str);
/* 153 */       return new Result(windowsPathType, str, normalize(stringBuilder, paramString, j));
/*     */     } 
/* 155 */     return new Result(windowsPathType, str, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String normalize(StringBuilder paramStringBuilder, String paramString, int paramInt) {
/* 164 */     int i = paramString.length();
/* 165 */     paramInt = nextNonSlash(paramString, paramInt, i);
/* 166 */     int j = paramInt;
/* 167 */     char c = Character.MIN_VALUE;
/* 168 */     while (paramInt < i) {
/* 169 */       char c1 = paramString.charAt(paramInt);
/* 170 */       if (isSlash(c1)) {
/* 171 */         if (c == ' ') {
/* 172 */           throw new InvalidPathException(paramString, "Trailing char <" + c + ">", paramInt - 1);
/*     */         }
/*     */         
/* 175 */         paramStringBuilder.append(paramString, j, paramInt);
/* 176 */         paramInt = nextNonSlash(paramString, paramInt, i);
/* 177 */         if (paramInt != i)
/* 178 */           paramStringBuilder.append('\\'); 
/* 179 */         j = paramInt; continue;
/*     */       } 
/* 181 */       if (isInvalidPathChar(c1)) {
/* 182 */         throw new InvalidPathException(paramString, "Illegal char <" + c1 + ">", paramInt);
/*     */       }
/*     */       
/* 185 */       c = c1;
/* 186 */       paramInt++;
/*     */     } 
/*     */     
/* 189 */     if (j != paramInt) {
/* 190 */       if (c == ' ') {
/* 191 */         throw new InvalidPathException(paramString, "Trailing char <" + c + ">", paramInt - 1);
/*     */       }
/*     */       
/* 194 */       paramStringBuilder.append(paramString, j, paramInt);
/*     */     } 
/* 196 */     return paramStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static final boolean isSlash(char paramChar) {
/* 200 */     return (paramChar == '\\' || paramChar == '/');
/*     */   }
/*     */   
/*     */   private static final int nextNonSlash(String paramString, int paramInt1, int paramInt2) {
/* 204 */     for (; paramInt1 < paramInt2 && isSlash(paramString.charAt(paramInt1)); paramInt1++);
/* 205 */     return paramInt1;
/*     */   }
/*     */   
/*     */   private static final int nextSlash(String paramString, int paramInt1, int paramInt2) {
/*     */     char c;
/* 210 */     while (paramInt1 < paramInt2 && !isSlash(c = paramString.charAt(paramInt1))) {
/* 211 */       if (isInvalidPathChar(c)) {
/* 212 */         throw new InvalidPathException(paramString, "Illegal character [" + c + "] in path", paramInt1);
/*     */       }
/*     */       
/* 215 */       paramInt1++;
/*     */     } 
/* 217 */     return paramInt1;
/*     */   }
/*     */   
/*     */   private static final boolean isLetter(char paramChar) {
/* 221 */     return ((paramChar >= 'a' && paramChar <= 'z') || (paramChar >= 'A' && paramChar <= 'Z'));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean isInvalidPathChar(char paramChar) {
/* 227 */     return (paramChar < ' ' || "<>:\"|?*".indexOf(paramChar) != -1);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsPathParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */