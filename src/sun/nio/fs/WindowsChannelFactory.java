/*     */ package sun.nio.fs;
/*     */ 
/*     */ import com.sun.nio.file.ExtendedOpenOption;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.AsynchronousFileChannel;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.util.Set;
/*     */ import sun.misc.JavaIOFileDescriptorAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.nio.ch.FileChannelImpl;
/*     */ import sun.nio.ch.ThreadPool;
/*     */ import sun.nio.ch.WindowsAsynchronousFileChannelImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WindowsChannelFactory
/*     */ {
/*  54 */   private static final JavaIOFileDescriptorAccess fdAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   static final OpenOption OPEN_REPARSE_POINT = new OpenOption()
/*     */     {
/*     */     
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Flags
/*     */   {
/*     */     boolean read;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean write;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean append;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean truncateExisting;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean create;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean createNew;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean deleteOnClose;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean sparse;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean overlapped;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean sync;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean dsync;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean shareRead = true;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean shareWrite = true;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean shareDelete = true;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean noFollowLinks;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean openReparsePoint;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Flags toFlags(Set<? extends OpenOption> param1Set) {
/*     */       // Byte code:
/*     */       //   0: new sun/nio/fs/WindowsChannelFactory$Flags
/*     */       //   3: dup
/*     */       //   4: invokespecial <init> : ()V
/*     */       //   7: astore_1
/*     */       //   8: aload_0
/*     */       //   9: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */       //   14: astore_2
/*     */       //   15: aload_2
/*     */       //   16: invokeinterface hasNext : ()Z
/*     */       //   21: ifeq -> 322
/*     */       //   24: aload_2
/*     */       //   25: invokeinterface next : ()Ljava/lang/Object;
/*     */       //   30: checkcast java/nio/file/OpenOption
/*     */       //   33: astore_3
/*     */       //   34: aload_3
/*     */       //   35: instanceof java/nio/file/StandardOpenOption
/*     */       //   38: ifeq -> 196
/*     */       //   41: getstatic sun/nio/fs/WindowsChannelFactory$2.$SwitchMap$java$nio$file$StandardOpenOption : [I
/*     */       //   44: aload_3
/*     */       //   45: checkcast java/nio/file/StandardOpenOption
/*     */       //   48: invokevirtual ordinal : ()I
/*     */       //   51: iaload
/*     */       //   52: tableswitch default -> 188, 1 -> 108, 2 -> 116, 3 -> 124, 4 -> 132, 5 -> 140, 6 -> 148, 7 -> 156, 8 -> 164, 9 -> 172, 10 -> 180
/*     */       //   108: aload_1
/*     */       //   109: iconst_1
/*     */       //   110: putfield read : Z
/*     */       //   113: goto -> 15
/*     */       //   116: aload_1
/*     */       //   117: iconst_1
/*     */       //   118: putfield write : Z
/*     */       //   121: goto -> 15
/*     */       //   124: aload_1
/*     */       //   125: iconst_1
/*     */       //   126: putfield append : Z
/*     */       //   129: goto -> 15
/*     */       //   132: aload_1
/*     */       //   133: iconst_1
/*     */       //   134: putfield truncateExisting : Z
/*     */       //   137: goto -> 15
/*     */       //   140: aload_1
/*     */       //   141: iconst_1
/*     */       //   142: putfield create : Z
/*     */       //   145: goto -> 15
/*     */       //   148: aload_1
/*     */       //   149: iconst_1
/*     */       //   150: putfield createNew : Z
/*     */       //   153: goto -> 15
/*     */       //   156: aload_1
/*     */       //   157: iconst_1
/*     */       //   158: putfield deleteOnClose : Z
/*     */       //   161: goto -> 15
/*     */       //   164: aload_1
/*     */       //   165: iconst_1
/*     */       //   166: putfield sparse : Z
/*     */       //   169: goto -> 15
/*     */       //   172: aload_1
/*     */       //   173: iconst_1
/*     */       //   174: putfield sync : Z
/*     */       //   177: goto -> 15
/*     */       //   180: aload_1
/*     */       //   181: iconst_1
/*     */       //   182: putfield dsync : Z
/*     */       //   185: goto -> 15
/*     */       //   188: new java/lang/UnsupportedOperationException
/*     */       //   191: dup
/*     */       //   192: invokespecial <init> : ()V
/*     */       //   195: athrow
/*     */       //   196: aload_3
/*     */       //   197: instanceof com/sun/nio/file/ExtendedOpenOption
/*     */       //   200: ifeq -> 272
/*     */       //   203: getstatic sun/nio/fs/WindowsChannelFactory$2.$SwitchMap$com$sun$nio$file$ExtendedOpenOption : [I
/*     */       //   206: aload_3
/*     */       //   207: checkcast com/sun/nio/file/ExtendedOpenOption
/*     */       //   210: invokevirtual ordinal : ()I
/*     */       //   213: iaload
/*     */       //   214: tableswitch default -> 264, 1 -> 240, 2 -> 248, 3 -> 256
/*     */       //   240: aload_1
/*     */       //   241: iconst_0
/*     */       //   242: putfield shareRead : Z
/*     */       //   245: goto -> 15
/*     */       //   248: aload_1
/*     */       //   249: iconst_0
/*     */       //   250: putfield shareWrite : Z
/*     */       //   253: goto -> 15
/*     */       //   256: aload_1
/*     */       //   257: iconst_0
/*     */       //   258: putfield shareDelete : Z
/*     */       //   261: goto -> 15
/*     */       //   264: new java/lang/UnsupportedOperationException
/*     */       //   267: dup
/*     */       //   268: invokespecial <init> : ()V
/*     */       //   271: athrow
/*     */       //   272: aload_3
/*     */       //   273: getstatic java/nio/file/LinkOption.NOFOLLOW_LINKS : Ljava/nio/file/LinkOption;
/*     */       //   276: if_acmpne -> 287
/*     */       //   279: aload_1
/*     */       //   280: iconst_1
/*     */       //   281: putfield noFollowLinks : Z
/*     */       //   284: goto -> 15
/*     */       //   287: aload_3
/*     */       //   288: getstatic sun/nio/fs/WindowsChannelFactory.OPEN_REPARSE_POINT : Ljava/nio/file/OpenOption;
/*     */       //   291: if_acmpne -> 302
/*     */       //   294: aload_1
/*     */       //   295: iconst_1
/*     */       //   296: putfield openReparsePoint : Z
/*     */       //   299: goto -> 15
/*     */       //   302: aload_3
/*     */       //   303: ifnonnull -> 314
/*     */       //   306: new java/lang/NullPointerException
/*     */       //   309: dup
/*     */       //   310: invokespecial <init> : ()V
/*     */       //   313: athrow
/*     */       //   314: new java/lang/UnsupportedOperationException
/*     */       //   317: dup
/*     */       //   318: invokespecial <init> : ()V
/*     */       //   321: athrow
/*     */       //   322: aload_1
/*     */       //   323: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #88	-> 0
/*     */       //   #89	-> 8
/*     */       //   #90	-> 34
/*     */       //   #91	-> 41
/*     */       //   #92	-> 108
/*     */       //   #93	-> 116
/*     */       //   #94	-> 124
/*     */       //   #95	-> 132
/*     */       //   #96	-> 140
/*     */       //   #97	-> 148
/*     */       //   #98	-> 156
/*     */       //   #99	-> 164
/*     */       //   #100	-> 172
/*     */       //   #101	-> 180
/*     */       //   #102	-> 188
/*     */       //   #106	-> 196
/*     */       //   #107	-> 203
/*     */       //   #108	-> 240
/*     */       //   #109	-> 248
/*     */       //   #110	-> 256
/*     */       //   #111	-> 264
/*     */       //   #115	-> 272
/*     */       //   #116	-> 279
/*     */       //   #117	-> 284
/*     */       //   #119	-> 287
/*     */       //   #120	-> 294
/*     */       //   #121	-> 299
/*     */       //   #123	-> 302
/*     */       //   #124	-> 306
/*     */       //   #125	-> 314
/*     */       //   #127	-> 322
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static FileChannel newFileChannel(String paramString1, String paramString2, Set<? extends OpenOption> paramSet, long paramLong) throws WindowsException {
/* 145 */     Flags flags = Flags.toFlags(paramSet);
/*     */ 
/*     */     
/* 148 */     if (!flags.read && !flags.write) {
/* 149 */       if (flags.append) {
/* 150 */         flags.write = true;
/*     */       } else {
/* 152 */         flags.read = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 157 */     if (flags.read && flags.append)
/* 158 */       throw new IllegalArgumentException("READ + APPEND not allowed"); 
/* 159 */     if (flags.append && flags.truncateExisting) {
/* 160 */       throw new IllegalArgumentException("APPEND + TRUNCATE_EXISTING not allowed");
/*     */     }
/* 162 */     FileDescriptor fileDescriptor = open(paramString1, paramString2, flags, paramLong);
/* 163 */     return FileChannelImpl.open(fileDescriptor, paramString1, flags.read, flags.write, flags.append, null);
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
/*     */   static AsynchronousFileChannel newAsynchronousFileChannel(String paramString1, String paramString2, Set<? extends OpenOption> paramSet, long paramLong, ThreadPool paramThreadPool) throws IOException {
/*     */     FileDescriptor fileDescriptor;
/* 183 */     Flags flags = Flags.toFlags(paramSet);
/*     */ 
/*     */     
/* 186 */     flags.overlapped = true;
/*     */ 
/*     */     
/* 189 */     if (!flags.read && !flags.write) {
/* 190 */       flags.read = true;
/*     */     }
/*     */ 
/*     */     
/* 194 */     if (flags.append) {
/* 195 */       throw new UnsupportedOperationException("APPEND not allowed");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 200 */       fileDescriptor = open(paramString1, paramString2, flags, paramLong);
/* 201 */     } catch (WindowsException windowsException) {
/* 202 */       windowsException.rethrowAsIOException(paramString1);
/* 203 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 208 */       return WindowsAsynchronousFileChannelImpl.open(fileDescriptor, flags.read, flags.write, paramThreadPool);
/* 209 */     } catch (IOException iOException) {
/*     */ 
/*     */       
/* 212 */       long l = fdAccess.getHandle(fileDescriptor);
/* 213 */       WindowsNativeDispatcher.CloseHandle(l);
/* 214 */       throw iOException;
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
/*     */   
/*     */   private static FileDescriptor open(String paramString1, String paramString2, Flags paramFlags, long paramLong) throws WindowsException {
/* 229 */     boolean bool1 = false;
/*     */ 
/*     */     
/* 232 */     int i = 0;
/* 233 */     if (paramFlags.read)
/* 234 */       i |= Integer.MIN_VALUE; 
/* 235 */     if (paramFlags.write) {
/* 236 */       i |= 0x40000000;
/*     */     }
/* 238 */     int j = 0;
/* 239 */     if (paramFlags.shareRead)
/* 240 */       j |= 0x1; 
/* 241 */     if (paramFlags.shareWrite)
/* 242 */       j |= 0x2; 
/* 243 */     if (paramFlags.shareDelete) {
/* 244 */       j |= 0x4;
/*     */     }
/* 246 */     int k = 128;
/* 247 */     byte b = 3;
/* 248 */     if (paramFlags.write) {
/* 249 */       if (paramFlags.createNew) {
/* 250 */         b = 1;
/*     */         
/* 252 */         k |= 0x200000;
/*     */       } else {
/* 254 */         if (paramFlags.create)
/* 255 */           b = 4; 
/* 256 */         if (paramFlags.truncateExisting)
/*     */         {
/*     */ 
/*     */           
/* 260 */           if (b == 4) {
/* 261 */             bool1 = true;
/*     */           } else {
/* 263 */             b = 5;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 269 */     if (paramFlags.dsync || paramFlags.sync)
/* 270 */       k |= Integer.MIN_VALUE; 
/* 271 */     if (paramFlags.overlapped)
/* 272 */       k |= 0x40000000; 
/* 273 */     if (paramFlags.deleteOnClose) {
/* 274 */       k |= 0x4000000;
/*     */     }
/*     */     
/* 277 */     boolean bool2 = true;
/* 278 */     if (b != 1 && (paramFlags.noFollowLinks || paramFlags.openReparsePoint || paramFlags.deleteOnClose)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 283 */       if (paramFlags.noFollowLinks || paramFlags.deleteOnClose)
/* 284 */         bool2 = false; 
/* 285 */       k |= 0x200000;
/*     */     } 
/*     */ 
/*     */     
/* 289 */     if (paramString2 != null) {
/* 290 */       SecurityManager securityManager = System.getSecurityManager();
/* 291 */       if (securityManager != null) {
/* 292 */         if (paramFlags.read)
/* 293 */           securityManager.checkRead(paramString2); 
/* 294 */         if (paramFlags.write)
/* 295 */           securityManager.checkWrite(paramString2); 
/* 296 */         if (paramFlags.deleteOnClose) {
/* 297 */           securityManager.checkDelete(paramString2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 302 */     long l = WindowsNativeDispatcher.CreateFile(paramString1, i, j, paramLong, b, k);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 310 */     if (!bool2) {
/*     */       try {
/* 312 */         if (WindowsFileAttributes.readAttributes(l).isSymbolicLink())
/* 313 */           throw new WindowsException("File is symbolic link"); 
/* 314 */       } catch (WindowsException windowsException) {
/* 315 */         WindowsNativeDispatcher.CloseHandle(l);
/* 316 */         throw windowsException;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 321 */     if (bool1) {
/*     */       try {
/* 323 */         WindowsNativeDispatcher.SetEndOfFile(l);
/* 324 */       } catch (WindowsException windowsException) {
/* 325 */         WindowsNativeDispatcher.CloseHandle(l);
/* 326 */         throw windowsException;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 331 */     if (b == 1 && paramFlags.sparse) {
/*     */       try {
/* 333 */         WindowsNativeDispatcher.DeviceIoControlSetSparse(l);
/* 334 */       } catch (WindowsException windowsException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 340 */     FileDescriptor fileDescriptor = new FileDescriptor();
/* 341 */     fdAccess.setHandle(fileDescriptor, l);
/* 342 */     return fileDescriptor;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsChannelFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */