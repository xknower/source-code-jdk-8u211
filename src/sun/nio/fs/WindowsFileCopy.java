/*     */ package sun.nio.fs;
/*     */ 
/*     */ import com.sun.nio.file.ExtendedCopyOption;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.AtomicMoveNotSupportedException;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.DirectoryNotEmptyException;
/*     */ import java.nio.file.FileAlreadyExistsException;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.LinkPermission;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WindowsFileCopy
/*     */ {
/*     */   static void copy(final WindowsPath source, final WindowsPath target, CopyOption... paramVarArgs) throws IOException {
/*  53 */     boolean bool1 = false;
/*  54 */     boolean bool2 = false;
/*  55 */     boolean bool3 = true;
/*  56 */     boolean bool4 = false;
/*  57 */     for (CopyOption copyOption : paramVarArgs) {
/*  58 */       if (copyOption == StandardCopyOption.REPLACE_EXISTING) {
/*  59 */         bool1 = true;
/*     */       
/*     */       }
/*  62 */       else if (copyOption == LinkOption.NOFOLLOW_LINKS) {
/*  63 */         bool3 = false;
/*     */       
/*     */       }
/*  66 */       else if (copyOption == StandardCopyOption.COPY_ATTRIBUTES) {
/*  67 */         bool2 = true;
/*     */       
/*     */       }
/*  70 */       else if (copyOption == ExtendedCopyOption.INTERRUPTIBLE) {
/*  71 */         bool4 = true;
/*     */       } else {
/*     */         
/*  74 */         if (copyOption == null)
/*  75 */           throw new NullPointerException(); 
/*  76 */         throw new UnsupportedOperationException("Unsupported copy option");
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  81 */     SecurityManager securityManager = System.getSecurityManager();
/*  82 */     if (securityManager != null) {
/*  83 */       source.checkRead();
/*  84 */       target.checkWrite();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     WindowsFileAttributes windowsFileAttributes1 = null;
/*  93 */     WindowsFileAttributes windowsFileAttributes2 = null;
/*     */     
/*  95 */     long l = 0L;
/*     */     try {
/*  97 */       l = source.openForReadAttributeAccess(bool3);
/*  98 */     } catch (WindowsException windowsException) {
/*  99 */       windowsException.rethrowAsIOException(source);
/*     */     } 
/*     */     
/*     */     try {
/*     */       try {
/* 104 */         windowsFileAttributes1 = WindowsFileAttributes.readAttributes(l);
/* 105 */       } catch (WindowsException windowsException) {
/* 106 */         windowsException.rethrowAsIOException(source);
/*     */       } 
/*     */ 
/*     */       
/* 110 */       long l1 = 0L;
/*     */       try {
/* 112 */         l1 = target.openForReadAttributeAccess(false);
/*     */         try {
/* 114 */           windowsFileAttributes2 = WindowsFileAttributes.readAttributes(l1);
/*     */ 
/*     */           
/* 117 */           if (WindowsFileAttributes.isSameFile(windowsFileAttributes1, windowsFileAttributes2)) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 122 */           if (!bool1) {
/* 123 */             throw new FileAlreadyExistsException(target
/* 124 */                 .getPathForExceptionMessage());
/*     */           }
/*     */         } finally {
/*     */           
/* 128 */           WindowsNativeDispatcher.CloseHandle(l1);
/*     */         } 
/* 130 */       } catch (WindowsException windowsException) {}
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 135 */       WindowsNativeDispatcher.CloseHandle(l);
/*     */     } 
/*     */ 
/*     */     
/* 139 */     if (securityManager != null && windowsFileAttributes1.isSymbolicLink()) {
/* 140 */       securityManager.checkPermission(new LinkPermission("symbolic"));
/*     */     }
/*     */     
/* 143 */     final String sourcePath = asWin32Path(source);
/* 144 */     final String targetPath = asWin32Path(target);
/*     */ 
/*     */     
/* 147 */     if (windowsFileAttributes2 != null) {
/*     */       try {
/* 149 */         if (windowsFileAttributes2.isDirectory() || windowsFileAttributes2.isDirectoryLink()) {
/* 150 */           WindowsNativeDispatcher.RemoveDirectory(str2);
/*     */         } else {
/* 152 */           WindowsNativeDispatcher.DeleteFile(str2);
/*     */         } 
/* 154 */       } catch (WindowsException windowsException) {
/* 155 */         if (windowsFileAttributes2.isDirectory())
/*     */         {
/*     */           
/* 158 */           if (windowsException.lastError() == 145 || windowsException
/* 159 */             .lastError() == 183)
/*     */           {
/* 161 */             throw new DirectoryNotEmptyException(target
/* 162 */                 .getPathForExceptionMessage());
/*     */           }
/*     */         }
/* 165 */         windowsException.rethrowAsIOException(target);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 170 */     if (!windowsFileAttributes1.isDirectory() && !windowsFileAttributes1.isDirectoryLink()) {
/*     */       
/* 172 */       final boolean flags = (source.getFileSystem().supportsLinks() && !bool3) ? true : false;
/*     */ 
/*     */       
/* 175 */       if (bool4) {
/*     */         
/* 177 */         Cancellable cancellable = new Cancellable()
/*     */           {
/*     */             public int cancelValue() {
/* 180 */               return 1;
/*     */             }
/*     */             
/*     */             public void implRun() throws IOException {
/*     */               try {
/* 185 */                 WindowsNativeDispatcher.CopyFileEx(sourcePath, targetPath, flags, 
/* 186 */                     addressToPollForCancel());
/* 187 */               } catch (WindowsException windowsException) {
/* 188 */                 windowsException.rethrowAsIOException(source, target);
/*     */               } 
/*     */             }
/*     */           };
/*     */         try {
/* 193 */           Cancellable.runInterruptibly(cancellable);
/* 194 */         } catch (ExecutionException executionException) {
/* 195 */           Throwable throwable = executionException.getCause();
/* 196 */           if (throwable instanceof IOException)
/* 197 */             throw (IOException)throwable; 
/* 198 */           throw new IOException(throwable);
/*     */         } 
/*     */       } else {
/*     */         
/*     */         try {
/* 203 */           WindowsNativeDispatcher.CopyFileEx(str1, str2, bool, 0L);
/* 204 */         } catch (WindowsException windowsException) {
/* 205 */           windowsException.rethrowAsIOException(source, target);
/*     */         } 
/*     */       } 
/* 208 */       if (bool2) {
/*     */         
/*     */         try {
/* 211 */           copySecurityAttributes(source, target, bool3);
/* 212 */         } catch (IOException iOException) {}
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 221 */       if (windowsFileAttributes1.isDirectory()) {
/* 222 */         WindowsNativeDispatcher.CreateDirectory(str2, 0L);
/*     */       } else {
/* 224 */         String str = WindowsLinkSupport.readLink(source);
/* 225 */         final boolean flags = true;
/* 226 */         WindowsNativeDispatcher.CreateSymbolicLink(str2, 
/* 227 */             WindowsPath.addPrefixIfNeeded(str), bool);
/*     */       }
/*     */     
/* 230 */     } catch (WindowsException windowsException) {
/* 231 */       windowsException.rethrowAsIOException(target);
/*     */     } 
/* 233 */     if (bool2) {
/*     */ 
/*     */       
/* 236 */       WindowsFileAttributeViews.Dos dos = WindowsFileAttributeViews.createDosView(target, false);
/*     */       try {
/* 238 */         dos.setAttributes(windowsFileAttributes1);
/* 239 */       } catch (IOException iOException) {
/* 240 */         if (windowsFileAttributes1.isDirectory()) {
/*     */           try {
/* 242 */             WindowsNativeDispatcher.RemoveDirectory(str2);
/* 243 */           } catch (WindowsException windowsException) {}
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 250 */         copySecurityAttributes(source, target, bool3);
/* 251 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void move(WindowsPath paramWindowsPath1, WindowsPath paramWindowsPath2, CopyOption... paramVarArgs) throws IOException {
/* 262 */     boolean bool1 = false;
/* 263 */     boolean bool2 = false;
/* 264 */     for (CopyOption copyOption : paramVarArgs) {
/* 265 */       if (copyOption == StandardCopyOption.ATOMIC_MOVE) {
/* 266 */         bool1 = true;
/*     */       
/*     */       }
/* 269 */       else if (copyOption == StandardCopyOption.REPLACE_EXISTING) {
/* 270 */         bool2 = true;
/*     */       
/*     */       }
/* 273 */       else if (copyOption != LinkOption.NOFOLLOW_LINKS) {
/*     */ 
/*     */ 
/*     */         
/* 277 */         if (copyOption == null) throw new NullPointerException(); 
/* 278 */         throw new UnsupportedOperationException("Unsupported copy option");
/*     */       } 
/*     */     } 
/* 281 */     SecurityManager securityManager = System.getSecurityManager();
/* 282 */     if (securityManager != null) {
/* 283 */       paramWindowsPath1.checkWrite();
/* 284 */       paramWindowsPath2.checkWrite();
/*     */     } 
/*     */     
/* 287 */     String str1 = asWin32Path(paramWindowsPath1);
/* 288 */     String str2 = asWin32Path(paramWindowsPath2);
/*     */ 
/*     */     
/* 291 */     if (bool1) {
/*     */       try {
/* 293 */         WindowsNativeDispatcher.MoveFileEx(str1, str2, 1);
/* 294 */       } catch (WindowsException windowsException) {
/* 295 */         if (windowsException.lastError() == 17) {
/* 296 */           throw new AtomicMoveNotSupportedException(paramWindowsPath1
/* 297 */               .getPathForExceptionMessage(), paramWindowsPath2
/* 298 */               .getPathForExceptionMessage(), windowsException
/* 299 */               .errorString());
/*     */         }
/* 301 */         windowsException.rethrowAsIOException(paramWindowsPath1, paramWindowsPath2);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 311 */     WindowsFileAttributes windowsFileAttributes1 = null;
/* 312 */     WindowsFileAttributes windowsFileAttributes2 = null;
/*     */     
/* 314 */     long l = 0L;
/*     */     try {
/* 316 */       l = paramWindowsPath1.openForReadAttributeAccess(false);
/* 317 */     } catch (WindowsException windowsException) {
/* 318 */       windowsException.rethrowAsIOException(paramWindowsPath1);
/*     */     } 
/*     */     
/*     */     try {
/*     */       try {
/* 323 */         windowsFileAttributes1 = WindowsFileAttributes.readAttributes(l);
/* 324 */       } catch (WindowsException windowsException) {
/* 325 */         windowsException.rethrowAsIOException(paramWindowsPath1);
/*     */       } 
/*     */ 
/*     */       
/* 329 */       long l1 = 0L;
/*     */       try {
/* 331 */         l1 = paramWindowsPath2.openForReadAttributeAccess(false);
/*     */         try {
/* 333 */           windowsFileAttributes2 = WindowsFileAttributes.readAttributes(l1);
/*     */ 
/*     */           
/* 336 */           if (WindowsFileAttributes.isSameFile(windowsFileAttributes1, windowsFileAttributes2)) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 341 */           if (!bool2) {
/* 342 */             throw new FileAlreadyExistsException(paramWindowsPath2
/* 343 */                 .getPathForExceptionMessage());
/*     */           }
/*     */         } finally {
/*     */           
/* 347 */           WindowsNativeDispatcher.CloseHandle(l1);
/*     */         } 
/* 349 */       } catch (WindowsException windowsException) {}
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 354 */       WindowsNativeDispatcher.CloseHandle(l);
/*     */     } 
/*     */ 
/*     */     
/* 358 */     if (windowsFileAttributes2 != null) {
/*     */       try {
/* 360 */         if (windowsFileAttributes2.isDirectory() || windowsFileAttributes2.isDirectoryLink()) {
/* 361 */           WindowsNativeDispatcher.RemoveDirectory(str2);
/*     */         } else {
/* 363 */           WindowsNativeDispatcher.DeleteFile(str2);
/*     */         } 
/* 365 */       } catch (WindowsException windowsException) {
/* 366 */         if (windowsFileAttributes2.isDirectory())
/*     */         {
/*     */           
/* 369 */           if (windowsException.lastError() == 145 || windowsException
/* 370 */             .lastError() == 183)
/*     */           {
/* 372 */             throw new DirectoryNotEmptyException(paramWindowsPath2
/* 373 */                 .getPathForExceptionMessage());
/*     */           }
/*     */         }
/* 376 */         windowsException.rethrowAsIOException(paramWindowsPath2);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 383 */       WindowsNativeDispatcher.MoveFileEx(str1, str2, 0);
/*     */       return;
/* 385 */     } catch (WindowsException windowsException) {
/* 386 */       if (windowsException.lastError() != 17) {
/* 387 */         windowsException.rethrowAsIOException(paramWindowsPath1, paramWindowsPath2);
/*     */       }
/*     */ 
/*     */       
/* 391 */       if (!windowsFileAttributes1.isDirectory() && !windowsFileAttributes1.isDirectoryLink()) {
/*     */         try {
/* 393 */           WindowsNativeDispatcher.MoveFileEx(str1, str2, 2);
/* 394 */         } catch (WindowsException windowsException1) {
/* 395 */           windowsException1.rethrowAsIOException(paramWindowsPath1, paramWindowsPath2);
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 400 */           copySecurityAttributes(paramWindowsPath1, paramWindowsPath2, false);
/* 401 */         } catch (IOException iOException) {}
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/* 408 */       assert windowsFileAttributes1.isDirectory() || windowsFileAttributes1.isDirectoryLink();
/*     */ 
/*     */       
/*     */       try {
/* 412 */         if (windowsFileAttributes1.isDirectory()) {
/* 413 */           WindowsNativeDispatcher.CreateDirectory(str2, 0L);
/*     */         } else {
/* 415 */           String str = WindowsLinkSupport.readLink(paramWindowsPath1);
/* 416 */           WindowsNativeDispatcher.CreateSymbolicLink(str2, 
/* 417 */               WindowsPath.addPrefixIfNeeded(str), 1);
/*     */         }
/*     */       
/* 420 */       } catch (WindowsException windowsException1) {
/* 421 */         windowsException1.rethrowAsIOException(paramWindowsPath2);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 426 */       WindowsFileAttributeViews.Dos dos = WindowsFileAttributeViews.createDosView(paramWindowsPath2, false);
/*     */       try {
/* 428 */         dos.setAttributes(windowsFileAttributes1);
/* 429 */       } catch (IOException iOException) {
/*     */         
/*     */         try {
/* 432 */           WindowsNativeDispatcher.RemoveDirectory(str2);
/* 433 */         } catch (WindowsException windowsException1) {}
/* 434 */         throw iOException;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 440 */         copySecurityAttributes(paramWindowsPath1, paramWindowsPath2, false);
/* 441 */       } catch (IOException iOException) {}
/*     */ 
/*     */       
/*     */       try {
/* 445 */         WindowsNativeDispatcher.RemoveDirectory(str1);
/* 446 */       } catch (WindowsException windowsException1) {
/*     */         
/*     */         try {
/* 449 */           WindowsNativeDispatcher.RemoveDirectory(str2);
/* 450 */         } catch (WindowsException windowsException2) {}
/*     */ 
/*     */         
/* 453 */         if (windowsException1.lastError() == 145 || windowsException1
/* 454 */           .lastError() == 183)
/*     */         {
/* 456 */           throw new DirectoryNotEmptyException(paramWindowsPath2
/* 457 */               .getPathForExceptionMessage());
/*     */         }
/* 459 */         windowsException1.rethrowAsIOException(paramWindowsPath1);
/*     */       } 
/*     */       return;
/*     */     } 
/*     */   }
/*     */   private static String asWin32Path(WindowsPath paramWindowsPath) throws IOException {
/*     */     try {
/* 466 */       return paramWindowsPath.getPathForWin32Calls();
/* 467 */     } catch (WindowsException windowsException) {
/* 468 */       windowsException.rethrowAsIOException(paramWindowsPath);
/* 469 */       return null;
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
/*     */   private static void copySecurityAttributes(WindowsPath paramWindowsPath1, WindowsPath paramWindowsPath2, boolean paramBoolean) throws IOException {
/* 481 */     String str = WindowsLinkSupport.getFinalPath(paramWindowsPath1, paramBoolean);
/*     */ 
/*     */ 
/*     */     
/* 485 */     WindowsSecurity.Privilege privilege = WindowsSecurity.enablePrivilege("SeRestorePrivilege");
/*     */     try {
/* 487 */       byte b = 7;
/*     */ 
/*     */       
/* 490 */       NativeBuffer nativeBuffer = WindowsAclFileAttributeView.getFileSecurity(str, b);
/*     */       try {
/*     */         try {
/* 493 */           WindowsNativeDispatcher.SetFileSecurity(paramWindowsPath2.getPathForWin32Calls(), b, nativeBuffer
/* 494 */               .address());
/* 495 */         } catch (WindowsException windowsException) {
/* 496 */           windowsException.rethrowAsIOException(paramWindowsPath2);
/*     */         } 
/*     */       } finally {
/* 499 */         nativeBuffer.release();
/*     */       } 
/*     */     } finally {
/* 502 */       privilege.drop();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsFileCopy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */