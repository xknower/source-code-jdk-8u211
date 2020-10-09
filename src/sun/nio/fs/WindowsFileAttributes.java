/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.nio.file.attribute.DosFileAttributes;
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.security.AccessController;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import sun.misc.Unsafe;
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
/*     */ class WindowsFileAttributes
/*     */   implements DosFileAttributes
/*     */ {
/*  44 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */   
/*     */   private static final short SIZEOF_FILE_INFORMATION = 52;
/*     */   
/*     */   private static final short OFFSETOF_FILE_INFORMATION_ATTRIBUTES = 0;
/*     */   
/*     */   private static final short OFFSETOF_FILE_INFORMATION_CREATETIME = 4;
/*     */   
/*     */   private static final short OFFSETOF_FILE_INFORMATION_LASTACCESSTIME = 12;
/*     */   
/*     */   private static final short OFFSETOF_FILE_INFORMATION_LASTWRITETIME = 20;
/*     */   
/*     */   private static final short OFFSETOF_FILE_INFORMATION_VOLSERIALNUM = 28;
/*     */   
/*     */   private static final short OFFSETOF_FILE_INFORMATION_SIZEHIGH = 32;
/*     */   
/*     */   private static final short OFFSETOF_FILE_INFORMATION_SIZELOW = 36;
/*     */   
/*     */   private static final short OFFSETOF_FILE_INFORMATION_INDEXHIGH = 44;
/*     */   
/*     */   private static final short OFFSETOF_FILE_INFORMATION_INDEXLOW = 48;
/*     */   
/*     */   private static final short SIZEOF_FILE_ATTRIBUTE_DATA = 36;
/*     */   
/*     */   private static final short OFFSETOF_FILE_ATTRIBUTE_DATA_ATTRIBUTES = 0;
/*     */   
/*     */   private static final short OFFSETOF_FILE_ATTRIBUTE_DATA_CREATETIME = 4;
/*     */   
/*     */   private static final short OFFSETOF_FILE_ATTRIBUTE_DATA_LASTACCESSTIME = 12;
/*     */   
/*     */   private static final short OFFSETOF_FILE_ATTRIBUTE_DATA_LASTWRITETIME = 20;
/*     */   
/*     */   private static final short OFFSETOF_FILE_ATTRIBUTE_DATA_SIZEHIGH = 28;
/*     */   
/*     */   private static final short OFFSETOF_FILE_ATTRIBUTE_DATA_SIZELOW = 32;
/*     */   
/*     */   private static final short SIZEOF_FIND_DATA = 592;
/*     */   
/*     */   private static final short OFFSETOF_FIND_DATA_ATTRIBUTES = 0;
/*     */   
/*     */   private static final short OFFSETOF_FIND_DATA_CREATETIME = 4;
/*     */   
/*     */   private static final short OFFSETOF_FIND_DATA_LASTACCESSTIME = 12;
/*     */   
/*     */   private static final short OFFSETOF_FIND_DATA_LASTWRITETIME = 20;
/*     */   
/*     */   private static final short OFFSETOF_FIND_DATA_SIZEHIGH = 28;
/*     */   
/*     */   private static final short OFFSETOF_FIND_DATA_SIZELOW = 32;
/*     */   
/*     */   private static final short OFFSETOF_FIND_DATA_RESERVED0 = 36;
/*     */   
/*     */   private static final long WINDOWS_EPOCH_IN_MICROSECONDS = -11644473600000000L;
/*     */   
/*     */   private static final boolean ensureAccurateMetadata;
/*     */   
/*     */   private final int fileAttrs;
/*     */   
/*     */   private final long creationTime;
/*     */   
/*     */   private final long lastAccessTime;
/*     */   
/*     */   private final long lastWriteTime;
/*     */   
/*     */   private final long size;
/*     */   
/*     */   private final int reparseTag;
/*     */   
/*     */   private final int volSerialNumber;
/*     */   
/*     */   private final int fileIndexHigh;
/*     */   private final int fileIndexLow;
/*     */   
/*     */   static {
/* 118 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.nio.fs.ensureAccurateMetadata", "false"));
/*     */ 
/*     */     
/* 121 */     ensureAccurateMetadata = (str.length() == 0) ? true : Boolean.valueOf(str).booleanValue();
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
/*     */   static FileTime toFileTime(long paramLong) {
/* 143 */     paramLong /= 10L;
/*     */     
/* 145 */     paramLong += -11644473600000000L;
/* 146 */     return FileTime.from(paramLong, TimeUnit.MICROSECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long toWindowsTime(FileTime paramFileTime) {
/* 154 */     long l = paramFileTime.to(TimeUnit.MICROSECONDS);
/*     */     
/* 156 */     l -= -11644473600000000L;
/*     */     
/* 158 */     l *= 10L;
/* 159 */     return l;
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
/*     */   private WindowsFileAttributes(int paramInt1, long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 175 */     this.fileAttrs = paramInt1;
/* 176 */     this.creationTime = paramLong1;
/* 177 */     this.lastAccessTime = paramLong2;
/* 178 */     this.lastWriteTime = paramLong3;
/* 179 */     this.size = paramLong4;
/* 180 */     this.reparseTag = paramInt2;
/* 181 */     this.volSerialNumber = paramInt3;
/* 182 */     this.fileIndexHigh = paramInt4;
/* 183 */     this.fileIndexLow = paramInt5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static WindowsFileAttributes fromFileInformation(long paramLong, int paramInt) {
/* 190 */     int i = unsafe.getInt(paramLong + 0L);
/* 191 */     long l1 = unsafe.getLong(paramLong + 4L);
/* 192 */     long l2 = unsafe.getLong(paramLong + 12L);
/* 193 */     long l3 = unsafe.getLong(paramLong + 20L);
/*     */     
/* 195 */     long l4 = (unsafe.getInt(paramLong + 32L) << 32L) + (unsafe.getInt(paramLong + 36L) & 0xFFFFFFFFL);
/* 196 */     int j = unsafe.getInt(paramLong + 28L);
/* 197 */     int k = unsafe.getInt(paramLong + 44L);
/* 198 */     int m = unsafe.getInt(paramLong + 48L);
/* 199 */     return new WindowsFileAttributes(i, l1, l2, l3, l4, paramInt, j, k, m);
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
/*     */   private static WindowsFileAttributes fromFileAttributeData(long paramLong, int paramInt) {
/* 214 */     int i = unsafe.getInt(paramLong + 0L);
/* 215 */     long l1 = unsafe.getLong(paramLong + 4L);
/* 216 */     long l2 = unsafe.getLong(paramLong + 12L);
/* 217 */     long l3 = unsafe.getLong(paramLong + 20L);
/*     */     
/* 219 */     long l4 = (unsafe.getInt(paramLong + 28L) << 32L) + (unsafe.getInt(paramLong + 32L) & 0xFFFFFFFFL);
/* 220 */     return new WindowsFileAttributes(i, l1, l2, l3, l4, paramInt, 0, 0, 0);
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
/*     */   static NativeBuffer getBufferForFindData() {
/* 236 */     return NativeBuffers.getNativeBuffer(592);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static WindowsFileAttributes fromFindData(long paramLong) {
/* 243 */     int i = unsafe.getInt(paramLong + 0L);
/* 244 */     long l1 = unsafe.getLong(paramLong + 4L);
/* 245 */     long l2 = unsafe.getLong(paramLong + 12L);
/* 246 */     long l3 = unsafe.getLong(paramLong + 20L);
/*     */     
/* 248 */     long l4 = (unsafe.getInt(paramLong + 28L) << 32L) + (unsafe.getInt(paramLong + 32L) & 0xFFFFFFFFL);
/*     */     
/* 250 */     boolean bool = isReparsePoint(i) ? unsafe.getInt(paramLong + 36L) : false;
/* 251 */     return new WindowsFileAttributes(i, l1, l2, l3, l4, bool, 0, 0, 0);
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
/*     */   static WindowsFileAttributes readAttributes(long paramLong) throws WindowsException {
/* 269 */     NativeBuffer nativeBuffer = NativeBuffers.getNativeBuffer(52);
/*     */     try {
/* 271 */       long l = nativeBuffer.address();
/* 272 */       WindowsNativeDispatcher.GetFileInformationByHandle(paramLong, l);
/*     */ 
/*     */       
/* 275 */       int i = 0;
/*     */       
/* 277 */       int j = unsafe.getInt(l + 0L);
/* 278 */       if (isReparsePoint(j)) {
/* 279 */         char c = '䀀';
/* 280 */         NativeBuffer nativeBuffer1 = NativeBuffers.getNativeBuffer(c);
/*     */         try {
/* 282 */           WindowsNativeDispatcher.DeviceIoControlGetReparsePoint(paramLong, nativeBuffer1.address(), c);
/* 283 */           i = (int)unsafe.getLong(nativeBuffer1.address());
/*     */         } finally {
/* 285 */           nativeBuffer1.release();
/*     */         } 
/*     */       } 
/*     */       
/* 289 */       return fromFileInformation(l, i);
/*     */     } finally {
/* 291 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static WindowsFileAttributes get(WindowsPath paramWindowsPath, boolean paramBoolean) throws WindowsException {
/* 301 */     if (!ensureAccurateMetadata) {
/* 302 */       WindowsException windowsException = null;
/*     */ 
/*     */ 
/*     */       
/* 306 */       NativeBuffer nativeBuffer = NativeBuffers.getNativeBuffer(36);
/*     */       try {
/* 308 */         long l1 = nativeBuffer.address();
/* 309 */         WindowsNativeDispatcher.GetFileAttributesEx(paramWindowsPath.getPathForWin32Calls(), l1);
/*     */ 
/*     */ 
/*     */         
/* 313 */         int i = unsafe.getInt(l1 + 0L);
/* 314 */         if (!isReparsePoint(i))
/* 315 */           return fromFileAttributeData(l1, 0); 
/* 316 */       } catch (WindowsException windowsException1) {
/* 317 */         if (windowsException1.lastError() != 32)
/* 318 */           throw windowsException1; 
/* 319 */         windowsException = windowsException1;
/*     */       } finally {
/* 321 */         nativeBuffer.release();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 326 */       if (windowsException != null) {
/* 327 */         String str = paramWindowsPath.getPathForWin32Calls();
/* 328 */         char c = str.charAt(str.length() - 1);
/* 329 */         if (c == ':' || c == '\\')
/* 330 */           throw windowsException; 
/* 331 */         nativeBuffer = getBufferForFindData();
/*     */         try {
/* 333 */           long l1 = WindowsNativeDispatcher.FindFirstFile(str, nativeBuffer.address());
/* 334 */           WindowsNativeDispatcher.FindClose(l1);
/* 335 */           WindowsFileAttributes windowsFileAttributes = fromFindData(nativeBuffer.address());
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 340 */           if (windowsFileAttributes.isReparsePoint())
/* 341 */             throw windowsException; 
/* 342 */           return windowsFileAttributes;
/* 343 */         } catch (WindowsException windowsException1) {
/* 344 */           throw windowsException;
/*     */         } finally {
/* 346 */           nativeBuffer.release();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 352 */     long l = paramWindowsPath.openForReadAttributeAccess(paramBoolean);
/*     */     try {
/* 354 */       return readAttributes(l);
/*     */     } finally {
/* 356 */       WindowsNativeDispatcher.CloseHandle(l);
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
/*     */   static boolean isSameFile(WindowsFileAttributes paramWindowsFileAttributes1, WindowsFileAttributes paramWindowsFileAttributes2) {
/* 368 */     return (paramWindowsFileAttributes1.volSerialNumber == paramWindowsFileAttributes2.volSerialNumber && paramWindowsFileAttributes1.fileIndexHigh == paramWindowsFileAttributes2.fileIndexHigh && paramWindowsFileAttributes1.fileIndexLow == paramWindowsFileAttributes2.fileIndexLow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isReparsePoint(int paramInt) {
/* 377 */     return ((paramInt & 0x400) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   int attributes() {
/* 382 */     return this.fileAttrs;
/*     */   }
/*     */   
/*     */   int volSerialNumber() {
/* 386 */     return this.volSerialNumber;
/*     */   }
/*     */   
/*     */   int fileIndexHigh() {
/* 390 */     return this.fileIndexHigh;
/*     */   }
/*     */   
/*     */   int fileIndexLow() {
/* 394 */     return this.fileIndexLow;
/*     */   }
/*     */ 
/*     */   
/*     */   public long size() {
/* 399 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileTime lastModifiedTime() {
/* 404 */     return toFileTime(this.lastWriteTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public FileTime lastAccessTime() {
/* 409 */     return toFileTime(this.lastAccessTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public FileTime creationTime() {
/* 414 */     return toFileTime(this.creationTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object fileKey() {
/* 419 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isReparsePoint() {
/* 424 */     return isReparsePoint(this.fileAttrs);
/*     */   }
/*     */   
/*     */   boolean isDirectoryLink() {
/* 428 */     return (isSymbolicLink() && (this.fileAttrs & 0x10) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSymbolicLink() {
/* 433 */     return (this.reparseTag == -1610612724);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirectory() {
/* 439 */     if (isSymbolicLink())
/* 440 */       return false; 
/* 441 */     return ((this.fileAttrs & 0x10) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOther() {
/* 446 */     if (isSymbolicLink()) {
/* 447 */       return false;
/*     */     }
/* 449 */     return ((this.fileAttrs & 0x440) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRegularFile() {
/* 454 */     return (!isSymbolicLink() && !isDirectory() && !isOther());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/* 459 */     return ((this.fileAttrs & 0x1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHidden() {
/* 464 */     return ((this.fileAttrs & 0x2) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isArchive() {
/* 469 */     return ((this.fileAttrs & 0x20) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSystem() {
/* 474 */     return ((this.fileAttrs & 0x4) != 0);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsFileAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */