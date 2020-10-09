/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOError;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.FileSystemException;
/*     */ import java.nio.file.NotLinkException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WindowsLinkSupport
/*     */ {
/*  43 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String readLink(WindowsPath paramWindowsPath) throws IOException {
/*  52 */     long l = 0L;
/*     */     try {
/*  54 */       l = paramWindowsPath.openForReadAttributeAccess(false);
/*  55 */     } catch (WindowsException windowsException) {
/*  56 */       windowsException.rethrowAsIOException(paramWindowsPath);
/*     */     } 
/*     */     try {
/*  59 */       return readLinkImpl(l);
/*     */     } finally {
/*  61 */       WindowsNativeDispatcher.CloseHandle(l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getFinalPath(WindowsPath paramWindowsPath) throws IOException {
/*  70 */     long l = 0L;
/*     */     try {
/*  72 */       l = paramWindowsPath.openForReadAttributeAccess(true);
/*  73 */     } catch (WindowsException windowsException) {
/*  74 */       windowsException.rethrowAsIOException(paramWindowsPath);
/*     */     } 
/*     */     try {
/*  77 */       return stripPrefix(WindowsNativeDispatcher.GetFinalPathNameByHandle(l));
/*  78 */     } catch (WindowsException windowsException) {
/*     */ 
/*     */       
/*  81 */       if (windowsException.lastError() != 124)
/*  82 */         windowsException.rethrowAsIOException(paramWindowsPath); 
/*     */     } finally {
/*  84 */       WindowsNativeDispatcher.CloseHandle(l);
/*     */     } 
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getFinalPath(WindowsPath paramWindowsPath, boolean paramBoolean) throws IOException {
/*  96 */     WindowsFileSystem windowsFileSystem = paramWindowsPath.getFileSystem();
/*     */     
/*     */     try {
/*  99 */       if (!paramBoolean || !windowsFileSystem.supportsLinks()) {
/* 100 */         return paramWindowsPath.getPathForWin32Calls();
/*     */       }
/*     */       
/* 103 */       if (!WindowsFileAttributes.get(paramWindowsPath, false).isSymbolicLink()) {
/* 104 */         return paramWindowsPath.getPathForWin32Calls();
/*     */       }
/* 106 */     } catch (WindowsException windowsException) {
/* 107 */       windowsException.rethrowAsIOException(paramWindowsPath);
/*     */     } 
/*     */ 
/*     */     
/* 111 */     String str = getFinalPath(paramWindowsPath);
/* 112 */     if (str != null) {
/* 113 */       return str;
/*     */     }
/*     */ 
/*     */     
/* 117 */     WindowsPath windowsPath = paramWindowsPath;
/* 118 */     byte b = 0;
/*     */     
/*     */     do {
/*     */       try {
/* 122 */         WindowsFileAttributes windowsFileAttributes = WindowsFileAttributes.get(windowsPath, false);
/*     */         
/* 124 */         if (!windowsFileAttributes.isSymbolicLink()) {
/* 125 */           return windowsPath.getPathForWin32Calls();
/*     */         }
/* 127 */       } catch (WindowsException windowsException) {
/* 128 */         windowsException.rethrowAsIOException(windowsPath);
/*     */       } 
/*     */       
/* 131 */       WindowsPath windowsPath1 = WindowsPath.createFromNormalizedPath(windowsFileSystem, readLink(windowsPath));
/* 132 */       WindowsPath windowsPath2 = windowsPath.getParent();
/* 133 */       if (windowsPath2 == null) {
/*     */         
/* 135 */         final WindowsPath t = windowsPath;
/*     */         
/* 137 */         windowsPath = AccessController.<WindowsPath>doPrivileged(new PrivilegedAction<WindowsPath>()
/*     */             {
/*     */               public WindowsPath run() {
/* 140 */                 return t.toAbsolutePath(); }
/*     */             });
/* 142 */         windowsPath2 = windowsPath.getParent();
/*     */       } 
/* 144 */       windowsPath = windowsPath2.resolve(windowsPath1);
/*     */     }
/* 146 */     while (++b < 32);
/*     */     
/* 148 */     throw new FileSystemException(paramWindowsPath.getPathForExceptionMessage(), null, "Too many links");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getRealPath(WindowsPath paramWindowsPath, boolean paramBoolean) throws IOException {
/*     */     int i;
/* 159 */     WindowsFileSystem windowsFileSystem = paramWindowsPath.getFileSystem();
/* 160 */     if (paramBoolean && !windowsFileSystem.supportsLinks()) {
/* 161 */       paramBoolean = false;
/*     */     }
/*     */     
/* 164 */     String str = null;
/*     */     try {
/* 166 */       str = paramWindowsPath.toAbsolutePath().toString();
/* 167 */     } catch (IOError iOError) {
/* 168 */       throw (IOException)iOError.getCause();
/*     */     } 
/*     */ 
/*     */     
/* 172 */     if (str.indexOf('.') >= 0) {
/*     */       try {
/* 174 */         str = WindowsNativeDispatcher.GetFullPathName(str);
/* 175 */       } catch (WindowsException windowsException) {
/* 176 */         windowsException.rethrowAsIOException(paramWindowsPath);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 181 */     StringBuilder stringBuilder = new StringBuilder(str.length());
/*     */ 
/*     */ 
/*     */     
/* 185 */     char c1 = str.charAt(0);
/* 186 */     char c2 = str.charAt(1);
/* 187 */     if (((c1 <= 'z' && c1 >= 'a') || (c1 <= 'Z' && c1 >= 'A')) && c2 == ':' && str
/* 188 */       .charAt(2) == '\\') {
/*     */       
/* 190 */       stringBuilder.append(Character.toUpperCase(c1));
/* 191 */       stringBuilder.append(":\\");
/* 192 */       i = 3;
/* 193 */     } else if (c1 == '\\' && c2 == '\\') {
/*     */       
/* 195 */       int k = str.length() - 1;
/* 196 */       int m = str.indexOf('\\', 2);
/*     */       
/* 198 */       if (m == -1 || m == k)
/*     */       {
/* 200 */         throw new FileSystemException(paramWindowsPath.getPathForExceptionMessage(), null, "UNC has invalid share");
/*     */       }
/*     */       
/* 203 */       m = str.indexOf('\\', m + 1);
/* 204 */       if (m < 0) {
/* 205 */         m = k;
/* 206 */         stringBuilder.append(str).append("\\");
/*     */       } else {
/* 208 */         stringBuilder.append(str, 0, m + 1);
/*     */       } 
/* 210 */       i = m + 1;
/*     */     } else {
/* 212 */       throw new AssertionError("path type not recognized");
/*     */     } 
/*     */ 
/*     */     
/* 216 */     if (i >= str.length()) {
/* 217 */       String str1 = stringBuilder.toString();
/*     */       try {
/* 219 */         WindowsNativeDispatcher.GetFileAttributes(str1);
/* 220 */       } catch (WindowsException windowsException) {
/* 221 */         windowsException.rethrowAsIOException(str);
/*     */       } 
/* 223 */       return str1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 228 */     int j = i;
/* 229 */     while (j < str.length()) {
/* 230 */       int k = str.indexOf('\\', j);
/* 231 */       int m = (k == -1) ? str.length() : k;
/* 232 */       String str1 = stringBuilder.toString() + str.substring(j, m);
/*     */       try {
/* 234 */         WindowsNativeDispatcher.FirstFile firstFile = WindowsNativeDispatcher.FindFirstFile(WindowsPath.addPrefixIfNeeded(str1));
/* 235 */         WindowsNativeDispatcher.FindClose(firstFile.handle());
/*     */ 
/*     */ 
/*     */         
/* 239 */         if (paramBoolean && 
/* 240 */           WindowsFileAttributes.isReparsePoint(firstFile.attributes())) {
/*     */           
/* 242 */           String str2 = getFinalPath(paramWindowsPath);
/* 243 */           if (str2 == null) {
/*     */ 
/*     */             
/* 246 */             WindowsPath windowsPath = resolveAllLinks(
/* 247 */                 WindowsPath.createFromNormalizedPath(windowsFileSystem, str));
/* 248 */             str2 = getRealPath(windowsPath, false);
/*     */           } 
/* 250 */           return str2;
/*     */         } 
/*     */ 
/*     */         
/* 254 */         stringBuilder.append(firstFile.name());
/* 255 */         if (k != -1) {
/* 256 */           stringBuilder.append('\\');
/*     */         }
/* 258 */       } catch (WindowsException windowsException) {
/* 259 */         windowsException.rethrowAsIOException(str);
/*     */       } 
/* 261 */       j = m + 1;
/*     */     } 
/*     */     
/* 264 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String readLinkImpl(long paramLong) throws IOException {
/* 272 */     char c = '䀀';
/* 273 */     NativeBuffer nativeBuffer = NativeBuffers.getNativeBuffer(c);
/*     */     try {
/*     */       try {
/* 276 */         WindowsNativeDispatcher.DeviceIoControlGetReparsePoint(paramLong, nativeBuffer.address(), c);
/* 277 */       } catch (WindowsException windowsException) {
/*     */         
/* 279 */         if (windowsException.lastError() == 4390)
/* 280 */           throw new NotLinkException(null, null, windowsException.errorString()); 
/* 281 */         windowsException.rethrowAsIOException((String)null);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 315 */       int i = (int)unsafe.getLong(nativeBuffer.address() + 0L);
/* 316 */       if (i != -1610612724)
/*     */       {
/* 318 */         throw new NotLinkException(null, null, "Reparse point is not a symbolic link");
/*     */       }
/*     */ 
/*     */       
/* 322 */       short s1 = unsafe.getShort(nativeBuffer.address() + 8L);
/* 323 */       short s2 = unsafe.getShort(nativeBuffer.address() + 10L);
/* 324 */       if (s2 % 2 != 0) {
/* 325 */         throw new FileSystemException(null, null, "Symbolic link corrupted");
/*     */       }
/*     */       
/* 328 */       char[] arrayOfChar = new char[s2 / 2];
/* 329 */       unsafe.copyMemory(null, nativeBuffer.address() + 20L + s1, arrayOfChar, Unsafe.ARRAY_CHAR_BASE_OFFSET, s2);
/*     */ 
/*     */ 
/*     */       
/* 333 */       String str = stripPrefix(new String(arrayOfChar));
/* 334 */       if (str.length() == 0) {
/* 335 */         throw new IOException("Symbolic link target is invalid");
/*     */       }
/* 337 */       return str;
/*     */     } finally {
/* 339 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static WindowsPath resolveAllLinks(WindowsPath paramWindowsPath) throws IOException {
/* 349 */     assert paramWindowsPath.isAbsolute();
/* 350 */     WindowsFileSystem windowsFileSystem = paramWindowsPath.getFileSystem();
/*     */ 
/*     */ 
/*     */     
/* 354 */     byte b1 = 0;
/* 355 */     byte b2 = 0;
/* 356 */     while (b2 < paramWindowsPath.getNameCount()) {
/* 357 */       WindowsPath windowsPath = paramWindowsPath.getRoot().resolve(paramWindowsPath.subpath(0, b2 + 1));
/*     */       
/* 359 */       WindowsFileAttributes windowsFileAttributes = null;
/*     */       try {
/* 361 */         windowsFileAttributes = WindowsFileAttributes.get(windowsPath, false);
/* 362 */       } catch (WindowsException windowsException) {
/* 363 */         windowsException.rethrowAsIOException(windowsPath);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 373 */       if (windowsFileAttributes.isSymbolicLink()) {
/* 374 */         b1++;
/* 375 */         if (b1 > 32) {
/* 376 */           throw new IOException("Too many links");
/*     */         }
/* 378 */         WindowsPath windowsPath1 = WindowsPath.createFromNormalizedPath(windowsFileSystem, readLink(windowsPath));
/* 379 */         WindowsPath windowsPath2 = null;
/* 380 */         int i = paramWindowsPath.getNameCount();
/* 381 */         if (b2 + 1 < i) {
/* 382 */           windowsPath2 = paramWindowsPath.subpath(b2 + 1, i);
/*     */         }
/* 384 */         paramWindowsPath = windowsPath.getParent().resolve(windowsPath1);
/*     */         try {
/* 386 */           String str = WindowsNativeDispatcher.GetFullPathName(paramWindowsPath.toString());
/* 387 */           if (!str.equals(paramWindowsPath.toString())) {
/* 388 */             paramWindowsPath = WindowsPath.createFromNormalizedPath(windowsFileSystem, str);
/*     */           }
/* 390 */         } catch (WindowsException windowsException) {
/* 391 */           windowsException.rethrowAsIOException(paramWindowsPath);
/*     */         } 
/* 393 */         if (windowsPath2 != null) {
/* 394 */           paramWindowsPath = paramWindowsPath.resolve(windowsPath2);
/*     */         }
/*     */ 
/*     */         
/* 398 */         b2 = 0;
/*     */         continue;
/*     */       } 
/* 401 */       b2++;
/*     */     } 
/*     */ 
/*     */     
/* 405 */     return paramWindowsPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String stripPrefix(String paramString) {
/* 413 */     if (paramString.startsWith("\\\\?\\")) {
/* 414 */       if (paramString.startsWith("\\\\?\\UNC\\")) {
/* 415 */         paramString = "\\" + paramString.substring(7);
/*     */       } else {
/* 417 */         paramString = paramString.substring(4);
/*     */       } 
/* 419 */       return paramString;
/*     */     } 
/*     */ 
/*     */     
/* 423 */     if (paramString.startsWith("\\??\\")) {
/* 424 */       if (paramString.startsWith("\\??\\UNC\\")) {
/* 425 */         paramString = "\\" + paramString.substring(7);
/*     */       } else {
/* 427 */         paramString = paramString.substring(4);
/*     */       } 
/* 429 */       return paramString;
/*     */     } 
/* 431 */     return paramString;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsLinkSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */