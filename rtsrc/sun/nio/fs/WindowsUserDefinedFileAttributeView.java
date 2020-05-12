/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ class WindowsUserDefinedFileAttributeView
/*     */   extends AbstractUserDefinedFileAttributeView
/*     */ {
/*     */   private final WindowsPath file;
/*  46 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */   private final boolean followLinks;
/*     */   
/*     */   private String join(String paramString1, String paramString2) {
/*  50 */     if (paramString2 == null)
/*  51 */       throw new NullPointerException("'name' is null"); 
/*  52 */     return paramString1 + ":" + paramString2;
/*     */   }
/*     */   private String join(WindowsPath paramWindowsPath, String paramString) throws WindowsException {
/*  55 */     return join(paramWindowsPath.getPathForWin32Calls(), paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WindowsUserDefinedFileAttributeView(WindowsPath paramWindowsPath, boolean paramBoolean) {
/*  62 */     this.file = paramWindowsPath;
/*  63 */     this.followLinks = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<String> listUsingStreamEnumeration() throws IOException {
/*  68 */     ArrayList<String> arrayList = new ArrayList();
/*     */     try {
/*  70 */       WindowsNativeDispatcher.FirstStream firstStream = WindowsNativeDispatcher.FindFirstStream(this.file.getPathForWin32Calls());
/*  71 */       if (firstStream != null) {
/*  72 */         long l = firstStream.handle();
/*     */         
/*     */         try {
/*  75 */           String str = firstStream.name();
/*  76 */           if (!str.equals("::$DATA")) {
/*  77 */             String[] arrayOfString = str.split(":");
/*  78 */             arrayList.add(arrayOfString[1]);
/*     */           } 
/*  80 */           while ((str = WindowsNativeDispatcher.FindNextStream(l)) != null) {
/*  81 */             String[] arrayOfString = str.split(":");
/*  82 */             arrayList.add(arrayOfString[1]);
/*     */           } 
/*     */         } finally {
/*  85 */           WindowsNativeDispatcher.FindClose(l);
/*     */         } 
/*     */       } 
/*  88 */     } catch (WindowsException windowsException) {
/*  89 */       windowsException.rethrowAsIOException(this.file);
/*     */     } 
/*  91 */     return Collections.unmodifiableList(arrayList);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> listUsingBackupRead() throws IOException {
/*  97 */     long l = -1L;
/*     */     try {
/*  99 */       int i = 33554432;
/* 100 */       if (!this.followLinks && this.file.getFileSystem().supportsLinks()) {
/* 101 */         i |= 0x200000;
/*     */       }
/* 103 */       l = WindowsNativeDispatcher.CreateFile(this.file.getPathForWin32Calls(), -2147483648, 1, 3, i);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 108 */     catch (WindowsException windowsException) {
/* 109 */       windowsException.rethrowAsIOException(this.file);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 114 */     NativeBuffer nativeBuffer = null;
/*     */ 
/*     */     
/* 117 */     ArrayList<String> arrayList = new ArrayList();
/*     */     
/*     */     try {
/* 120 */       nativeBuffer = NativeBuffers.getNativeBuffer(4096);
/* 121 */       long l1 = nativeBuffer.address();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 137 */       long l2 = 0L;
/*     */       
/*     */       try {
/*     */         while (true) {
/* 141 */           WindowsNativeDispatcher.BackupResult backupResult = WindowsNativeDispatcher.BackupRead(l, l1, 20, false, l2);
/*     */           
/* 143 */           l2 = backupResult.context();
/* 144 */           if (backupResult.bytesTransferred() == 0) {
/*     */             break;
/*     */           }
/* 147 */           int i = unsafe.getInt(l1 + 0L);
/* 148 */           long l3 = unsafe.getLong(l1 + 8L);
/* 149 */           int j = unsafe.getInt(l1 + 16L);
/*     */ 
/*     */           
/* 152 */           if (j > 0) {
/* 153 */             backupResult = WindowsNativeDispatcher.BackupRead(l, l1, j, false, l2);
/* 154 */             if (backupResult.bytesTransferred() != j) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */           
/* 159 */           if (i == 4) {
/* 160 */             char[] arrayOfChar = new char[j / 2];
/* 161 */             unsafe.copyMemory(null, l1, arrayOfChar, Unsafe.ARRAY_CHAR_BASE_OFFSET, j);
/*     */ 
/*     */             
/* 164 */             String[] arrayOfString = (new String(arrayOfChar)).split(":");
/* 165 */             if (arrayOfString.length == 3) {
/* 166 */               arrayList.add(arrayOfString[1]);
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 171 */           if (i == 9) {
/* 172 */             throw new IOException("Spare blocks not handled");
/*     */           }
/*     */ 
/*     */           
/* 176 */           if (l3 > 0L) {
/* 177 */             WindowsNativeDispatcher.BackupSeek(l, l3, l2);
/*     */           }
/*     */         } 
/* 180 */       } catch (WindowsException windowsException) {
/*     */         
/* 182 */         throw new IOException(windowsException.errorString());
/*     */       } finally {
/*     */         
/* 185 */         if (l2 != 0L) {
/*     */           try {
/* 187 */             WindowsNativeDispatcher.BackupRead(l, 0L, 0, true, l2);
/* 188 */           } catch (WindowsException windowsException) {}
/*     */         }
/*     */       } 
/*     */     } finally {
/* 192 */       if (nativeBuffer != null)
/* 193 */         nativeBuffer.release(); 
/* 194 */       WindowsNativeDispatcher.CloseHandle(l);
/*     */     } 
/* 196 */     return Collections.unmodifiableList(arrayList);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> list() throws IOException {
/* 201 */     if (System.getSecurityManager() != null) {
/* 202 */       checkAccess(this.file.getPathForPermissionCheck(), true, false);
/*     */     }
/* 204 */     if (this.file.getFileSystem().supportsStreamEnumeration()) {
/* 205 */       return listUsingStreamEnumeration();
/*     */     }
/* 207 */     return listUsingBackupRead();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size(String paramString) throws IOException {
/* 213 */     if (System.getSecurityManager() != null) {
/* 214 */       checkAccess(this.file.getPathForPermissionCheck(), true, false);
/*     */     }
/*     */     
/* 217 */     FileChannel fileChannel = null;
/*     */     try {
/* 219 */       HashSet<StandardOpenOption> hashSet = new HashSet();
/* 220 */       hashSet.add(StandardOpenOption.READ);
/* 221 */       if (!this.followLinks) {
/* 222 */         hashSet.add(WindowsChannelFactory.OPEN_REPARSE_POINT);
/*     */       }
/* 224 */       fileChannel = WindowsChannelFactory.newFileChannel(join(this.file, paramString), null, (Set)hashSet, 0L);
/* 225 */     } catch (WindowsException windowsException) {
/* 226 */       windowsException.rethrowAsIOException(join(this.file.getPathForPermissionCheck(), paramString));
/*     */     } 
/*     */     try {
/* 229 */       long l = fileChannel.size();
/* 230 */       if (l > 2147483647L)
/* 231 */         throw new ArithmeticException("Stream too large"); 
/* 232 */       return (int)l;
/*     */     } finally {
/* 234 */       fileChannel.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(String paramString, ByteBuffer paramByteBuffer) throws IOException {
/* 240 */     if (System.getSecurityManager() != null) {
/* 241 */       checkAccess(this.file.getPathForPermissionCheck(), true, false);
/*     */     }
/*     */     
/* 244 */     FileChannel fileChannel = null;
/*     */     try {
/* 246 */       HashSet<StandardOpenOption> hashSet = new HashSet();
/* 247 */       hashSet.add(StandardOpenOption.READ);
/* 248 */       if (!this.followLinks) {
/* 249 */         hashSet.add(WindowsChannelFactory.OPEN_REPARSE_POINT);
/*     */       }
/* 251 */       fileChannel = WindowsChannelFactory.newFileChannel(join(this.file, paramString), null, (Set)hashSet, 0L);
/* 252 */     } catch (WindowsException windowsException) {
/* 253 */       windowsException.rethrowAsIOException(join(this.file.getPathForPermissionCheck(), paramString));
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 258 */       if (fileChannel.size() > paramByteBuffer.remaining())
/* 259 */         throw new IOException("Stream too large"); 
/* 260 */       int i = 0;
/* 261 */       while (paramByteBuffer.hasRemaining()) {
/* 262 */         int j = fileChannel.read(paramByteBuffer);
/* 263 */         if (j < 0)
/*     */           break; 
/* 265 */         i += j;
/*     */       } 
/* 267 */       return i;
/*     */     } finally {
/* 269 */       fileChannel.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int write(String paramString, ByteBuffer paramByteBuffer) throws IOException {
/* 275 */     if (System.getSecurityManager() != null) {
/* 276 */       checkAccess(this.file.getPathForPermissionCheck(), false, true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 286 */     long l = -1L;
/*     */     try {
/* 288 */       int i = 33554432;
/* 289 */       if (!this.followLinks) {
/* 290 */         i |= 0x200000;
/*     */       }
/* 292 */       l = WindowsNativeDispatcher.CreateFile(this.file.getPathForWin32Calls(), -2147483648, 7, 3, i);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 297 */     catch (WindowsException windowsException) {
/* 298 */       windowsException.rethrowAsIOException(this.file);
/*     */     } 
/*     */     try {
/* 301 */       HashSet<OpenOption> hashSet = new HashSet();
/* 302 */       if (!this.followLinks)
/* 303 */         hashSet.add(WindowsChannelFactory.OPEN_REPARSE_POINT); 
/* 304 */       hashSet.add(StandardOpenOption.CREATE);
/* 305 */       hashSet.add(StandardOpenOption.WRITE);
/* 306 */       hashSet.add(StandardOpenOption.TRUNCATE_EXISTING);
/* 307 */       FileChannel fileChannel = null;
/*     */       
/*     */       try {
/* 310 */         fileChannel = WindowsChannelFactory.newFileChannel(join(this.file, paramString), null, hashSet, 0L);
/* 311 */       } catch (WindowsException windowsException) {
/* 312 */         windowsException.rethrowAsIOException(join(this.file.getPathForPermissionCheck(), paramString));
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */       
/* 325 */       WindowsNativeDispatcher.CloseHandle(l);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 331 */     if (System.getSecurityManager() != null) {
/* 332 */       checkAccess(this.file.getPathForPermissionCheck(), false, true);
/*     */     }
/* 334 */     String str1 = WindowsLinkSupport.getFinalPath(this.file, this.followLinks);
/* 335 */     String str2 = join(str1, paramString);
/*     */     try {
/* 337 */       WindowsNativeDispatcher.DeleteFile(str2);
/* 338 */     } catch (WindowsException windowsException) {
/* 339 */       windowsException.rethrowAsIOException(str2);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsUserDefinedFileAttributeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */