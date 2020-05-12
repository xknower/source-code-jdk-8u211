/*     */ package java.lang;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import sun.misc.JavaIOFileDescriptorAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ProcessImpl
/*     */   extends Process
/*     */ {
/*  54 */   private static final JavaIOFileDescriptorAccess fdAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*     */ 
/*     */   
/*     */   private static final int VERIFICATION_CMD_BAT = 0;
/*     */ 
/*     */   
/*     */   private static final int VERIFICATION_WIN32 = 1;
/*     */   
/*     */   private static final int VERIFICATION_LEGACY = 2;
/*     */ 
/*     */   
/*     */   private static FileOutputStream newFileOutputStream(File paramFile, boolean paramBoolean) throws IOException {
/*  66 */     if (paramBoolean) {
/*  67 */       String str = paramFile.getPath();
/*  68 */       SecurityManager securityManager = System.getSecurityManager();
/*  69 */       if (securityManager != null)
/*  70 */         securityManager.checkWrite(str); 
/*  71 */       long l = openForAtomicAppend(str);
/*  72 */       final FileDescriptor fd = new FileDescriptor();
/*  73 */       fdAccess.setHandle(fileDescriptor, l);
/*  74 */       return AccessController.<FileOutputStream>doPrivileged(new PrivilegedAction<FileOutputStream>()
/*     */           {
/*     */             public FileOutputStream run() {
/*  77 */               return new FileOutputStream(fd);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/*  82 */     return new FileOutputStream(paramFile);
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
/*     */   static Process start(String[] paramArrayOfString, Map<String, String> paramMap, String paramString, ProcessBuilder.Redirect[] paramArrayOfRedirect, boolean paramBoolean) throws IOException {
/*  94 */     String str = ProcessEnvironment.toEnvironmentBlock(paramMap);
/*     */     
/*  96 */     FileInputStream fileInputStream = null;
/*  97 */     FileOutputStream fileOutputStream1 = null;
/*  98 */     FileOutputStream fileOutputStream2 = null;
/*     */     
/*     */     try {
/*     */       long[] arrayOfLong;
/* 102 */       if (paramArrayOfRedirect == null) {
/* 103 */         arrayOfLong = new long[] { -1L, -1L, -1L };
/*     */       } else {
/* 105 */         arrayOfLong = new long[3];
/*     */         
/* 107 */         if (paramArrayOfRedirect[0] == ProcessBuilder.Redirect.PIPE) {
/* 108 */           arrayOfLong[0] = -1L;
/* 109 */         } else if (paramArrayOfRedirect[0] == ProcessBuilder.Redirect.INHERIT) {
/* 110 */           arrayOfLong[0] = fdAccess.getHandle(FileDescriptor.in);
/*     */         } else {
/* 112 */           fileInputStream = new FileInputStream(paramArrayOfRedirect[0].file());
/* 113 */           arrayOfLong[0] = fdAccess.getHandle(fileInputStream.getFD());
/*     */         } 
/*     */         
/* 116 */         if (paramArrayOfRedirect[1] == ProcessBuilder.Redirect.PIPE) {
/* 117 */           arrayOfLong[1] = -1L;
/* 118 */         } else if (paramArrayOfRedirect[1] == ProcessBuilder.Redirect.INHERIT) {
/* 119 */           arrayOfLong[1] = fdAccess.getHandle(FileDescriptor.out);
/*     */         } else {
/* 121 */           fileOutputStream1 = newFileOutputStream(paramArrayOfRedirect[1].file(), paramArrayOfRedirect[1]
/* 122 */               .append());
/* 123 */           arrayOfLong[1] = fdAccess.getHandle(fileOutputStream1.getFD());
/*     */         } 
/*     */         
/* 126 */         if (paramArrayOfRedirect[2] == ProcessBuilder.Redirect.PIPE) {
/* 127 */           arrayOfLong[2] = -1L;
/* 128 */         } else if (paramArrayOfRedirect[2] == ProcessBuilder.Redirect.INHERIT) {
/* 129 */           arrayOfLong[2] = fdAccess.getHandle(FileDescriptor.err);
/*     */         } else {
/* 131 */           fileOutputStream2 = newFileOutputStream(paramArrayOfRedirect[2].file(), paramArrayOfRedirect[2]
/* 132 */               .append());
/* 133 */           arrayOfLong[2] = fdAccess.getHandle(fileOutputStream2.getFD());
/*     */         } 
/*     */       } 
/*     */       
/* 137 */       return new ProcessImpl(paramArrayOfString, str, paramString, arrayOfLong, paramBoolean);
/*     */     } finally {
/*     */ 
/*     */ 
/*     */       
/* 142 */       try { if (fileInputStream != null) fileInputStream.close();  }
/*     */       finally { 
/* 144 */         try { if (fileOutputStream1 != null) fileOutputStream1.close();  }
/* 145 */         finally { if (fileOutputStream2 != null) fileOutputStream2.close();
/*     */            }
/*     */          }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LazyPattern
/*     */   {
/* 155 */     private static final Pattern PATTERN = Pattern.compile("[^\\s\"]+|\"[^\"]*\"");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] getTokensFromCommand(String paramString) {
/* 166 */     ArrayList<String> arrayList = new ArrayList(8);
/* 167 */     Matcher matcher = LazyPattern.PATTERN.matcher(paramString);
/* 168 */     while (matcher.find())
/* 169 */       arrayList.add(matcher.group()); 
/* 170 */     return arrayList.<String>toArray(new String[arrayList.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   private static final char[][] ESCAPE_VERIFICATION = new char[][] { { ' ', '\t', '<', '>', '&', '|', '^' }, { ' ', '\t', '<', '>' }, { ' ', '\t' } };
/*     */ 
/*     */   
/*     */   private long handle;
/*     */   
/*     */   private OutputStream stdin_stream;
/*     */   
/*     */   private InputStream stdout_stream;
/*     */   
/*     */   private InputStream stderr_stream;
/*     */ 
/*     */   
/*     */   private static String createCommandLine(int paramInt, String paramString, String[] paramArrayOfString) {
/* 189 */     StringBuilder stringBuilder = new StringBuilder(80);
/*     */     
/* 191 */     stringBuilder.append(paramString);
/*     */     
/* 193 */     for (byte b = 1; b < paramArrayOfString.length; b++) {
/* 194 */       stringBuilder.append(' ');
/* 195 */       String str = paramArrayOfString[b];
/* 196 */       if (needsEscaping(paramInt, str)) {
/* 197 */         stringBuilder.append('"').append(str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 211 */         if (paramInt != 0 && str.endsWith("\\")) {
/* 212 */           stringBuilder.append('\\');
/*     */         }
/* 214 */         stringBuilder.append('"');
/*     */       } else {
/* 216 */         stringBuilder.append(str);
/*     */       } 
/*     */     } 
/* 219 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isQuoted(boolean paramBoolean, String paramString1, String paramString2) {
/* 224 */     int i = paramString1.length() - 1;
/* 225 */     if (i >= 1 && paramString1.charAt(0) == '"' && paramString1.charAt(i) == '"') {
/*     */       
/* 227 */       if (paramBoolean && 
/* 228 */         paramString1.indexOf('"', 1) != i)
/*     */       {
/* 230 */         throw new IllegalArgumentException(paramString2);
/*     */       }
/*     */       
/* 233 */       return true;
/*     */     } 
/* 235 */     if (paramBoolean && 
/* 236 */       paramString1.indexOf('"') >= 0)
/*     */     {
/* 238 */       throw new IllegalArgumentException(paramString2);
/*     */     }
/*     */     
/* 241 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean needsEscaping(int paramInt, String paramString) {
/* 252 */     boolean bool = isQuoted((paramInt == 0), paramString, "Argument has embedded quote, use the explicit CMD.EXE call.");
/*     */ 
/*     */ 
/*     */     
/* 256 */     if (!bool) {
/* 257 */       char[] arrayOfChar = ESCAPE_VERIFICATION[paramInt];
/* 258 */       for (byte b = 0; b < arrayOfChar.length; b++) {
/* 259 */         if (paramString.indexOf(arrayOfChar[b]) >= 0) {
/* 260 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 264 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getExecutablePath(String paramString) throws IOException {
/* 270 */     boolean bool = isQuoted(true, paramString, "Executable name has embedded quote, split the arguments");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 275 */     File file = new File(bool ? paramString.substring(1, paramString.length() - 1) : paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     return file.getPath();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isShellFile(String paramString) {
/* 299 */     String str = paramString.toUpperCase();
/* 300 */     return (str.endsWith(".CMD") || str.endsWith(".BAT"));
/*     */   }
/*     */   
/*     */   private String quoteString(String paramString) {
/* 304 */     StringBuilder stringBuilder = new StringBuilder(paramString.length() + 2);
/* 305 */     return stringBuilder.append('"').append(paramString).append('"').toString();
/*     */   }
/*     */   private ProcessImpl(String[] paramArrayOfString, String paramString1, String paramString2, final long[] stdHandles, boolean paramBoolean) throws IOException {
/*     */     String str;
/* 309 */     this.handle = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     SecurityManager securityManager = System.getSecurityManager();
/* 323 */     boolean bool = false;
/* 324 */     if (securityManager == null) {
/* 325 */       bool = true;
/* 326 */       String str1 = System.getProperty("jdk.lang.Process.allowAmbiguousCommands");
/* 327 */       if (str1 != null)
/* 328 */         bool = !"false".equalsIgnoreCase(str1) ? true : false; 
/*     */     } 
/* 330 */     if (bool) {
/*     */ 
/*     */ 
/*     */       
/* 334 */       String str1 = (new File(paramArrayOfString[0])).getPath();
/*     */ 
/*     */       
/* 337 */       if (needsEscaping(2, str1)) {
/* 338 */         str1 = quoteString(str1);
/*     */       }
/* 340 */       str = createCommandLine(2, str1, paramArrayOfString);
/*     */     } else {
/*     */       String str1;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 348 */         str1 = getExecutablePath(paramArrayOfString[0]);
/* 349 */       } catch (IllegalArgumentException illegalArgumentException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 360 */         StringBuilder stringBuilder = new StringBuilder();
/*     */         
/* 362 */         for (String str2 : paramArrayOfString) {
/* 363 */           stringBuilder.append(str2).append(' ');
/*     */         }
/*     */         
/* 366 */         paramArrayOfString = getTokensFromCommand(stringBuilder.toString());
/* 367 */         str1 = getExecutablePath(paramArrayOfString[0]);
/*     */ 
/*     */         
/* 370 */         if (securityManager != null) {
/* 371 */           securityManager.checkExec(str1);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 377 */       str = createCommandLine(
/*     */           
/* 379 */           isShellFile(str1) ? 0 : 1, 
/*     */ 
/*     */           
/* 382 */           quoteString(str1), paramArrayOfString);
/*     */     } 
/*     */ 
/*     */     
/* 386 */     this.handle = create(str, paramString1, paramString2, stdHandles, paramBoolean);
/*     */ 
/*     */     
/* 389 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 392 */             if (stdHandles[0] == -1L) {
/* 393 */               ProcessImpl.this.stdin_stream = ProcessBuilder.NullOutputStream.INSTANCE;
/*     */             } else {
/* 395 */               FileDescriptor fileDescriptor = new FileDescriptor();
/* 396 */               ProcessImpl.fdAccess.setHandle(fileDescriptor, stdHandles[0]);
/* 397 */               ProcessImpl.this.stdin_stream = new BufferedOutputStream(new FileOutputStream(fileDescriptor));
/*     */             } 
/*     */ 
/*     */             
/* 401 */             if (stdHandles[1] == -1L) {
/* 402 */               ProcessImpl.this.stdout_stream = ProcessBuilder.NullInputStream.INSTANCE;
/*     */             } else {
/* 404 */               FileDescriptor fileDescriptor = new FileDescriptor();
/* 405 */               ProcessImpl.fdAccess.setHandle(fileDescriptor, stdHandles[1]);
/* 406 */               ProcessImpl.this.stdout_stream = new BufferedInputStream(new FileInputStream(fileDescriptor));
/*     */             } 
/*     */ 
/*     */             
/* 410 */             if (stdHandles[2] == -1L) {
/* 411 */               ProcessImpl.this.stderr_stream = ProcessBuilder.NullInputStream.INSTANCE;
/*     */             } else {
/* 413 */               FileDescriptor fileDescriptor = new FileDescriptor();
/* 414 */               ProcessImpl.fdAccess.setHandle(fileDescriptor, stdHandles[2]);
/* 415 */               ProcessImpl.this.stderr_stream = new FileInputStream(fileDescriptor);
/*     */             } 
/*     */             
/* 418 */             return null;
/*     */           }
/*     */         });
/*     */   } public OutputStream getOutputStream() {
/* 422 */     return this.stdin_stream;
/*     */   }
/*     */   
/*     */   public InputStream getInputStream() {
/* 426 */     return this.stdout_stream;
/*     */   }
/*     */   
/*     */   public InputStream getErrorStream() {
/* 430 */     return this.stderr_stream;
/*     */   }
/*     */   
/*     */   protected void finalize() {
/* 434 */     closeHandle(this.handle);
/*     */   }
/*     */   
/* 437 */   private static final int STILL_ACTIVE = getStillActive();
/*     */ 
/*     */   
/*     */   public int exitValue() {
/* 441 */     int i = getExitCodeProcess(this.handle);
/* 442 */     if (i == STILL_ACTIVE)
/* 443 */       throw new IllegalThreadStateException("process has not exited"); 
/* 444 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int waitFor() throws InterruptedException {
/* 449 */     waitForInterruptibly(this.handle);
/* 450 */     if (Thread.interrupted())
/* 451 */       throw new InterruptedException(); 
/* 452 */     return exitValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean waitFor(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/* 461 */     if (getExitCodeProcess(this.handle) != STILL_ACTIVE) return true; 
/* 462 */     if (paramLong <= 0L) return false;
/*     */     
/* 464 */     long l1 = paramTimeUnit.toNanos(paramLong);
/* 465 */     long l2 = System.nanoTime() + l1;
/*     */ 
/*     */     
/*     */     do {
/* 469 */       long l = TimeUnit.NANOSECONDS.toMillis(l1 + 999999L);
/* 470 */       waitForTimeoutInterruptibly(this.handle, l);
/* 471 */       if (Thread.interrupted())
/* 472 */         throw new InterruptedException(); 
/* 473 */       if (getExitCodeProcess(this.handle) != STILL_ACTIVE) {
/* 474 */         return true;
/*     */       }
/* 476 */       l1 = l2 - System.nanoTime();
/* 477 */     } while (l1 > 0L);
/*     */     
/* 479 */     return (getExitCodeProcess(this.handle) != STILL_ACTIVE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 485 */     terminateProcess(this.handle);
/*     */   }
/*     */   
/*     */   public Process destroyForcibly() {
/* 489 */     destroy();
/* 490 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAlive() {
/* 497 */     return isProcessAlive(this.handle);
/*     */   }
/*     */   
/*     */   private static native int getStillActive();
/*     */   
/*     */   private static native int getExitCodeProcess(long paramLong);
/*     */   
/*     */   private static native void waitForInterruptibly(long paramLong);
/*     */   
/*     */   private static native void waitForTimeoutInterruptibly(long paramLong1, long paramLong2);
/*     */   
/*     */   private static native void terminateProcess(long paramLong);
/*     */   
/*     */   private static native boolean isProcessAlive(long paramLong);
/*     */   
/*     */   private static synchronized native long create(String paramString1, String paramString2, String paramString3, long[] paramArrayOflong, boolean paramBoolean) throws IOException;
/*     */   
/*     */   private static native long openForAtomicAppend(String paramString) throws IOException;
/*     */   
/*     */   private static native boolean closeHandle(long paramLong);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\ProcessImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */