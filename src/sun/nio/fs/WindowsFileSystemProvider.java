/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.FilePermission;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.nio.channels.AsynchronousFileChannel;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.SeekableByteChannel;
/*     */ import java.nio.file.AccessDeniedException;
/*     */ import java.nio.file.AccessMode;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.DirectoryNotEmptyException;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.FileAlreadyExistsException;
/*     */ import java.nio.file.FileStore;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.FileSystemAlreadyExistsException;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.LinkPermission;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.ProviderMismatchException;
/*     */ import java.nio.file.attribute.AclFileAttributeView;
/*     */ import java.nio.file.attribute.BasicFileAttributeView;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.DosFileAttributeView;
/*     */ import java.nio.file.attribute.DosFileAttributes;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.nio.file.attribute.FileOwnerAttributeView;
/*     */ import java.nio.file.attribute.UserDefinedFileAttributeView;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.nio.ch.ThreadPool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsFileSystemProvider
/*     */   extends AbstractFileSystemProvider
/*     */ {
/*  47 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */ 
/*     */   
/*     */   private static final String USER_DIR = "user.dir";
/*     */ 
/*     */   
/*  53 */   private final WindowsFileSystem theFileSystem = new WindowsFileSystem(this, System.getProperty("user.dir"));
/*     */ 
/*     */ 
/*     */   
/*     */   public String getScheme() {
/*  58 */     return "file";
/*     */   }
/*     */   
/*     */   private void checkUri(URI paramURI) {
/*  62 */     if (!paramURI.getScheme().equalsIgnoreCase(getScheme()))
/*  63 */       throw new IllegalArgumentException("URI does not match this provider"); 
/*  64 */     if (paramURI.getAuthority() != null)
/*  65 */       throw new IllegalArgumentException("Authority component present"); 
/*  66 */     if (paramURI.getPath() == null)
/*  67 */       throw new IllegalArgumentException("Path component is undefined"); 
/*  68 */     if (!paramURI.getPath().equals("/"))
/*  69 */       throw new IllegalArgumentException("Path component should be '/'"); 
/*  70 */     if (paramURI.getQuery() != null)
/*  71 */       throw new IllegalArgumentException("Query component present"); 
/*  72 */     if (paramURI.getFragment() != null) {
/*  73 */       throw new IllegalArgumentException("Fragment component present");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FileSystem newFileSystem(URI paramURI, Map<String, ?> paramMap) throws IOException {
/*  80 */     checkUri(paramURI);
/*  81 */     throw new FileSystemAlreadyExistsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public final FileSystem getFileSystem(URI paramURI) {
/*  86 */     checkUri(paramURI);
/*  87 */     return this.theFileSystem;
/*     */   }
/*     */ 
/*     */   
/*     */   public Path getPath(URI paramURI) {
/*  92 */     return WindowsUriSupport.fromUri(this.theFileSystem, paramURI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileChannel newFileChannel(Path paramPath, Set<? extends OpenOption> paramSet, FileAttribute<?>... paramVarArgs) throws IOException {
/* 101 */     if (paramPath == null)
/* 102 */       throw new NullPointerException(); 
/* 103 */     if (!(paramPath instanceof WindowsPath))
/* 104 */       throw new ProviderMismatchException(); 
/* 105 */     WindowsPath windowsPath = (WindowsPath)paramPath;
/*     */     
/* 107 */     WindowsSecurityDescriptor windowsSecurityDescriptor = WindowsSecurityDescriptor.fromAttribute(paramVarArgs);
/*     */     try {
/* 109 */       return 
/* 110 */         WindowsChannelFactory.newFileChannel(windowsPath.getPathForWin32Calls(), windowsPath
/* 111 */           .getPathForPermissionCheck(), paramSet, windowsSecurityDescriptor
/*     */           
/* 113 */           .address());
/* 114 */     } catch (WindowsException windowsException) {
/* 115 */       windowsException.rethrowAsIOException(windowsPath);
/* 116 */       return null;
/*     */     } finally {
/* 118 */       if (windowsSecurityDescriptor != null) {
/* 119 */         windowsSecurityDescriptor.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AsynchronousFileChannel newAsynchronousFileChannel(Path paramPath, Set<? extends OpenOption> paramSet, ExecutorService paramExecutorService, FileAttribute<?>... paramVarArgs) throws IOException {
/* 130 */     if (paramPath == null)
/* 131 */       throw new NullPointerException(); 
/* 132 */     if (!(paramPath instanceof WindowsPath))
/* 133 */       throw new ProviderMismatchException(); 
/* 134 */     WindowsPath windowsPath = (WindowsPath)paramPath;
/* 135 */     ThreadPool threadPool = (paramExecutorService == null) ? null : ThreadPool.wrap(paramExecutorService, 0);
/*     */     
/* 137 */     WindowsSecurityDescriptor windowsSecurityDescriptor = WindowsSecurityDescriptor.fromAttribute(paramVarArgs);
/*     */     try {
/* 139 */       return 
/* 140 */         WindowsChannelFactory.newAsynchronousFileChannel(windowsPath.getPathForWin32Calls(), windowsPath
/* 141 */           .getPathForPermissionCheck(), paramSet, windowsSecurityDescriptor
/*     */           
/* 143 */           .address(), threadPool);
/*     */     }
/* 145 */     catch (WindowsException windowsException) {
/* 146 */       windowsException.rethrowAsIOException(windowsPath);
/* 147 */       return null;
/*     */     } finally {
/* 149 */       if (windowsSecurityDescriptor != null) {
/* 150 */         windowsSecurityDescriptor.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <V extends java.nio.file.attribute.FileAttributeView> V getFileAttributeView(Path paramPath, Class<V> paramClass, LinkOption... paramVarArgs) {
/* 159 */     WindowsPath windowsPath = WindowsPath.toWindowsPath(paramPath);
/* 160 */     if (paramClass == null)
/* 161 */       throw new NullPointerException(); 
/* 162 */     boolean bool = Util.followLinks(paramVarArgs);
/* 163 */     if (paramClass == BasicFileAttributeView.class)
/* 164 */       return (V)WindowsFileAttributeViews.createBasicView(windowsPath, bool); 
/* 165 */     if (paramClass == DosFileAttributeView.class)
/* 166 */       return (V)WindowsFileAttributeViews.createDosView(windowsPath, bool); 
/* 167 */     if (paramClass == AclFileAttributeView.class)
/* 168 */       return (V)new WindowsAclFileAttributeView(windowsPath, bool); 
/* 169 */     if (paramClass == FileOwnerAttributeView.class) {
/* 170 */       return (V)new FileOwnerAttributeViewImpl(new WindowsAclFileAttributeView(windowsPath, bool));
/*     */     }
/* 172 */     if (paramClass == UserDefinedFileAttributeView.class)
/* 173 */       return (V)new WindowsUserDefinedFileAttributeView(windowsPath, bool); 
/* 174 */     return (V)null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends BasicFileAttributes> A readAttributes(Path paramPath, Class<A> paramClass, LinkOption... paramVarArgs) throws IOException {
/*     */     Class<DosFileAttributeView> clazz;
/* 185 */     if (paramClass == BasicFileAttributes.class)
/* 186 */     { Class<BasicFileAttributeView> clazz1 = BasicFileAttributeView.class; }
/* 187 */     else if (paramClass == DosFileAttributes.class)
/* 188 */     { clazz = DosFileAttributeView.class; }
/* 189 */     else { if (paramClass == null) {
/* 190 */         throw new NullPointerException();
/*     */       }
/* 192 */       throw new UnsupportedOperationException(); }
/* 193 */      return (A)((BasicFileAttributeView)getFileAttributeView(paramPath, (Class)clazz, paramVarArgs)).readAttributes();
/*     */   }
/*     */ 
/*     */   
/*     */   public DynamicFileAttributeView getFileAttributeView(Path paramPath, String paramString, LinkOption... paramVarArgs) {
/* 198 */     WindowsPath windowsPath = WindowsPath.toWindowsPath(paramPath);
/* 199 */     boolean bool = Util.followLinks(paramVarArgs);
/* 200 */     if (paramString.equals("basic"))
/* 201 */       return WindowsFileAttributeViews.createBasicView(windowsPath, bool); 
/* 202 */     if (paramString.equals("dos"))
/* 203 */       return WindowsFileAttributeViews.createDosView(windowsPath, bool); 
/* 204 */     if (paramString.equals("acl"))
/* 205 */       return new WindowsAclFileAttributeView(windowsPath, bool); 
/* 206 */     if (paramString.equals("owner")) {
/* 207 */       return new FileOwnerAttributeViewImpl(new WindowsAclFileAttributeView(windowsPath, bool));
/*     */     }
/* 209 */     if (paramString.equals("user"))
/* 210 */       return new WindowsUserDefinedFileAttributeView(windowsPath, bool); 
/* 211 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SeekableByteChannel newByteChannel(Path paramPath, Set<? extends OpenOption> paramSet, FileAttribute<?>... paramVarArgs) throws IOException {
/* 220 */     WindowsPath windowsPath = WindowsPath.toWindowsPath(paramPath);
/*     */     
/* 222 */     WindowsSecurityDescriptor windowsSecurityDescriptor = WindowsSecurityDescriptor.fromAttribute(paramVarArgs);
/*     */     try {
/* 224 */       return 
/* 225 */         WindowsChannelFactory.newFileChannel(windowsPath.getPathForWin32Calls(), windowsPath
/* 226 */           .getPathForPermissionCheck(), paramSet, windowsSecurityDescriptor
/*     */           
/* 228 */           .address());
/* 229 */     } catch (WindowsException windowsException) {
/* 230 */       windowsException.rethrowAsIOException(windowsPath);
/* 231 */       return null;
/*     */     } finally {
/* 233 */       windowsSecurityDescriptor.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   boolean implDelete(Path paramPath, boolean paramBoolean) throws IOException {
/* 239 */     WindowsPath windowsPath = WindowsPath.toWindowsPath(paramPath);
/* 240 */     windowsPath.checkDelete();
/*     */     
/* 242 */     WindowsFileAttributes windowsFileAttributes = null;
/*     */     
/*     */     try {
/* 245 */       windowsFileAttributes = WindowsFileAttributes.get(windowsPath, false);
/* 246 */       if (windowsFileAttributes.isDirectory() || windowsFileAttributes.isDirectoryLink()) {
/* 247 */         WindowsNativeDispatcher.RemoveDirectory(windowsPath.getPathForWin32Calls());
/*     */       } else {
/* 249 */         WindowsNativeDispatcher.DeleteFile(windowsPath.getPathForWin32Calls());
/*     */       } 
/* 251 */       return true;
/* 252 */     } catch (WindowsException windowsException) {
/*     */ 
/*     */       
/* 255 */       if (!paramBoolean && (windowsException
/* 256 */         .lastError() == 2 || windowsException
/* 257 */         .lastError() == 3)) return false;
/*     */       
/* 259 */       if (windowsFileAttributes != null && windowsFileAttributes.isDirectory())
/*     */       {
/*     */         
/* 262 */         if (windowsException.lastError() == 145 || windowsException
/* 263 */           .lastError() == 183)
/*     */         {
/* 265 */           throw new DirectoryNotEmptyException(windowsPath
/* 266 */               .getPathForExceptionMessage());
/*     */         }
/*     */       }
/* 269 */       windowsException.rethrowAsIOException(windowsPath);
/* 270 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copy(Path paramPath1, Path paramPath2, CopyOption... paramVarArgs) throws IOException {
/* 278 */     WindowsFileCopy.copy(WindowsPath.toWindowsPath(paramPath1), 
/* 279 */         WindowsPath.toWindowsPath(paramPath2), paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void move(Path paramPath1, Path paramPath2, CopyOption... paramVarArgs) throws IOException {
/* 287 */     WindowsFileCopy.move(WindowsPath.toWindowsPath(paramPath1), 
/* 288 */         WindowsPath.toWindowsPath(paramPath2), paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasDesiredAccess(WindowsPath paramWindowsPath, int paramInt) throws IOException {
/* 297 */     boolean bool = false;
/* 298 */     String str = WindowsLinkSupport.getFinalPath(paramWindowsPath, true);
/*     */     
/* 300 */     NativeBuffer nativeBuffer = WindowsAclFileAttributeView.getFileSecurity(str, 7);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 305 */       bool = WindowsSecurity.checkAccessMask(nativeBuffer.address(), paramInt, 1179785, 1179926, 1179808, 2032127);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 310 */     catch (WindowsException windowsException) {
/* 311 */       windowsException.rethrowAsIOException(paramWindowsPath);
/*     */     } finally {
/* 313 */       nativeBuffer.release();
/*     */     } 
/* 315 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkReadAccess(WindowsPath paramWindowsPath) throws IOException {
/*     */     try {
/* 323 */       Set<?> set = Collections.emptySet();
/*     */       
/* 325 */       FileChannel fileChannel = WindowsChannelFactory.newFileChannel(paramWindowsPath.getPathForWin32Calls(), paramWindowsPath
/* 326 */           .getPathForPermissionCheck(), (Set)set, 0L);
/*     */ 
/*     */       
/* 329 */       fileChannel.close();
/* 330 */     } catch (WindowsException windowsException) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 335 */         (new WindowsDirectoryStream(paramWindowsPath, null)).close();
/* 336 */       } catch (IOException iOException) {
/*     */         
/* 338 */         windowsException.rethrowAsIOException(paramWindowsPath);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkAccess(Path paramPath, AccessMode... paramVarArgs) throws IOException {
/* 345 */     WindowsPath windowsPath = WindowsPath.toWindowsPath(paramPath);
/*     */     
/* 347 */     boolean bool1 = false;
/* 348 */     boolean bool2 = false;
/* 349 */     boolean bool3 = false;
/* 350 */     for (AccessMode accessMode : paramVarArgs) {
/* 351 */       switch (accessMode) { case READ:
/* 352 */           bool1 = true; break;
/* 353 */         case WRITE: bool2 = true; break;
/* 354 */         case EXECUTE: bool3 = true; break;
/* 355 */         default: throw new AssertionError("Should not get here"); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 361 */     if (!bool2 && !bool3) {
/* 362 */       checkReadAccess(windowsPath);
/*     */       
/*     */       return;
/*     */     } 
/* 366 */     int i = 0;
/* 367 */     if (bool1) {
/* 368 */       windowsPath.checkRead();
/* 369 */       i |= 0x1;
/*     */     } 
/* 371 */     if (bool2) {
/* 372 */       windowsPath.checkWrite();
/* 373 */       i |= 0x2;
/*     */     } 
/* 375 */     if (bool3) {
/* 376 */       SecurityManager securityManager = System.getSecurityManager();
/* 377 */       if (securityManager != null)
/* 378 */         securityManager.checkExec(windowsPath.getPathForPermissionCheck()); 
/* 379 */       i |= 0x20;
/*     */     } 
/*     */     
/* 382 */     if (!hasDesiredAccess(windowsPath, i)) {
/* 383 */       throw new AccessDeniedException(windowsPath
/* 384 */           .getPathForExceptionMessage(), null, "Permissions does not allow requested access");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 389 */     if (bool2) {
/*     */       try {
/* 391 */         WindowsFileAttributes windowsFileAttributes = WindowsFileAttributes.get(windowsPath, true);
/* 392 */         if (!windowsFileAttributes.isDirectory() && windowsFileAttributes.isReadOnly()) {
/* 393 */           throw new AccessDeniedException(windowsPath
/* 394 */               .getPathForExceptionMessage(), null, "DOS readonly attribute is set");
/*     */         }
/* 396 */       } catch (WindowsException windowsException) {
/* 397 */         windowsException.rethrowAsIOException(windowsPath);
/*     */       } 
/*     */       
/* 400 */       if (WindowsFileStore.create(windowsPath).isReadOnly()) {
/* 401 */         throw new AccessDeniedException(windowsPath
/* 402 */             .getPathForExceptionMessage(), null, "Read-only file system");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSameFile(Path paramPath1, Path paramPath2) throws IOException {
/* 409 */     WindowsPath windowsPath1 = WindowsPath.toWindowsPath(paramPath1);
/* 410 */     if (windowsPath1.equals(paramPath2))
/* 411 */       return true; 
/* 412 */     if (paramPath2 == null)
/* 413 */       throw new NullPointerException(); 
/* 414 */     if (!(paramPath2 instanceof WindowsPath))
/* 415 */       return false; 
/* 416 */     WindowsPath windowsPath2 = (WindowsPath)paramPath2;
/*     */ 
/*     */     
/* 419 */     windowsPath1.checkRead();
/* 420 */     windowsPath2.checkRead();
/*     */ 
/*     */     
/* 423 */     long l = 0L;
/*     */     try {
/* 425 */       l = windowsPath1.openForReadAttributeAccess(true);
/* 426 */     } catch (WindowsException windowsException) {
/* 427 */       windowsException.rethrowAsIOException(windowsPath1);
/*     */     } 
/*     */     try {
/* 430 */       WindowsFileAttributes windowsFileAttributes = null;
/*     */       try {
/* 432 */         windowsFileAttributes = WindowsFileAttributes.readAttributes(l);
/* 433 */       } catch (WindowsException windowsException) {
/* 434 */         windowsException.rethrowAsIOException(windowsPath1);
/*     */       } 
/* 436 */       long l1 = 0L;
/*     */       try {
/* 438 */         l1 = windowsPath2.openForReadAttributeAccess(true);
/* 439 */       } catch (WindowsException windowsException) {
/* 440 */         windowsException.rethrowAsIOException(windowsPath2);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */       
/* 454 */       WindowsNativeDispatcher.CloseHandle(l);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHidden(Path paramPath) throws IOException {
/* 460 */     WindowsPath windowsPath = WindowsPath.toWindowsPath(paramPath);
/* 461 */     windowsPath.checkRead();
/* 462 */     WindowsFileAttributes windowsFileAttributes = null;
/*     */     try {
/* 464 */       windowsFileAttributes = WindowsFileAttributes.get(windowsPath, true);
/* 465 */     } catch (WindowsException windowsException) {
/* 466 */       windowsException.rethrowAsIOException(windowsPath);
/*     */     } 
/*     */     
/* 469 */     if (windowsFileAttributes.isDirectory())
/* 470 */       return false; 
/* 471 */     return windowsFileAttributes.isHidden();
/*     */   }
/*     */ 
/*     */   
/*     */   public FileStore getFileStore(Path paramPath) throws IOException {
/* 476 */     WindowsPath windowsPath = WindowsPath.toWindowsPath(paramPath);
/* 477 */     SecurityManager securityManager = System.getSecurityManager();
/* 478 */     if (securityManager != null) {
/* 479 */       securityManager.checkPermission(new RuntimePermission("getFileStoreAttributes"));
/* 480 */       windowsPath.checkRead();
/*     */     } 
/* 482 */     return WindowsFileStore.create(windowsPath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createDirectory(Path paramPath, FileAttribute<?>... paramVarArgs) throws IOException {
/* 490 */     WindowsPath windowsPath = WindowsPath.toWindowsPath(paramPath);
/* 491 */     windowsPath.checkWrite();
/* 492 */     WindowsSecurityDescriptor windowsSecurityDescriptor = WindowsSecurityDescriptor.fromAttribute(paramVarArgs);
/*     */     try {
/* 494 */       WindowsNativeDispatcher.CreateDirectory(windowsPath.getPathForWin32Calls(), windowsSecurityDescriptor.address());
/* 495 */     } catch (WindowsException windowsException) {
/*     */ 
/*     */       
/* 498 */       if (windowsException.lastError() == 5) {
/*     */         try {
/* 500 */           if (WindowsFileAttributes.get(windowsPath, false).isDirectory())
/* 501 */             throw new FileAlreadyExistsException(windowsPath.toString()); 
/* 502 */         } catch (WindowsException windowsException1) {}
/*     */       }
/* 504 */       windowsException.rethrowAsIOException(windowsPath);
/*     */     } finally {
/* 506 */       windowsSecurityDescriptor.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DirectoryStream<Path> newDirectoryStream(Path paramPath, DirectoryStream.Filter<? super Path> paramFilter) throws IOException {
/* 514 */     WindowsPath windowsPath = WindowsPath.toWindowsPath(paramPath);
/* 515 */     windowsPath.checkRead();
/* 516 */     if (paramFilter == null)
/* 517 */       throw new NullPointerException(); 
/* 518 */     return new WindowsDirectoryStream(windowsPath, paramFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createSymbolicLink(Path paramPath1, Path paramPath2, FileAttribute<?>... paramVarArgs) throws IOException {
/* 525 */     WindowsPath windowsPath3, windowsPath1 = WindowsPath.toWindowsPath(paramPath1);
/* 526 */     WindowsPath windowsPath2 = WindowsPath.toWindowsPath(paramPath2);
/*     */     
/* 528 */     if (!windowsPath1.getFileSystem().supportsLinks()) {
/* 529 */       throw new UnsupportedOperationException("Symbolic links not supported on this operating system");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 534 */     if (paramVarArgs.length > 0) {
/* 535 */       WindowsSecurityDescriptor.fromAttribute(paramVarArgs);
/* 536 */       throw new UnsupportedOperationException("Initial file attributesnot supported when creating symbolic link");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 541 */     SecurityManager securityManager = System.getSecurityManager();
/* 542 */     if (securityManager != null) {
/* 543 */       securityManager.checkPermission(new LinkPermission("symbolic"));
/* 544 */       windowsPath1.checkWrite();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 551 */     if (windowsPath2.type() == WindowsPathType.DRIVE_RELATIVE) {
/* 552 */       throw new IOException("Cannot create symbolic link to working directory relative target");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 561 */     if (windowsPath2.type() == WindowsPathType.RELATIVE) {
/* 562 */       WindowsPath windowsPath = windowsPath1.getParent();
/* 563 */       windowsPath3 = (windowsPath == null) ? windowsPath2 : windowsPath.resolve(windowsPath2);
/*     */     } else {
/* 565 */       windowsPath3 = windowsPath1.resolve(windowsPath2);
/*     */     } 
/* 567 */     int i = 0;
/*     */     try {
/* 569 */       WindowsFileAttributes windowsFileAttributes = WindowsFileAttributes.get(windowsPath3, false);
/* 570 */       if (windowsFileAttributes.isDirectory() || windowsFileAttributes.isDirectoryLink())
/* 571 */         i |= 0x1; 
/* 572 */     } catch (WindowsException windowsException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 578 */       WindowsNativeDispatcher.CreateSymbolicLink(windowsPath1.getPathForWin32Calls(), 
/* 579 */           WindowsPath.addPrefixIfNeeded(windowsPath2.toString()), i);
/*     */     }
/* 581 */     catch (WindowsException windowsException) {
/* 582 */       if (windowsException.lastError() == 4392) {
/* 583 */         windowsException.rethrowAsIOException(windowsPath1, windowsPath2);
/*     */       } else {
/* 585 */         windowsException.rethrowAsIOException(windowsPath1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void createLink(Path paramPath1, Path paramPath2) throws IOException {
/* 592 */     WindowsPath windowsPath1 = WindowsPath.toWindowsPath(paramPath1);
/* 593 */     WindowsPath windowsPath2 = WindowsPath.toWindowsPath(paramPath2);
/*     */ 
/*     */     
/* 596 */     SecurityManager securityManager = System.getSecurityManager();
/* 597 */     if (securityManager != null) {
/* 598 */       securityManager.checkPermission(new LinkPermission("hard"));
/* 599 */       windowsPath1.checkWrite();
/* 600 */       windowsPath2.checkWrite();
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 605 */       WindowsNativeDispatcher.CreateHardLink(windowsPath1.getPathForWin32Calls(), windowsPath2
/* 606 */           .getPathForWin32Calls());
/* 607 */     } catch (WindowsException windowsException) {
/* 608 */       windowsException.rethrowAsIOException(windowsPath1, windowsPath2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Path readSymbolicLink(Path paramPath) throws IOException {
/* 614 */     WindowsPath windowsPath = WindowsPath.toWindowsPath(paramPath);
/* 615 */     WindowsFileSystem windowsFileSystem = windowsPath.getFileSystem();
/* 616 */     if (!windowsFileSystem.supportsLinks()) {
/* 617 */       throw new UnsupportedOperationException("symbolic links not supported");
/*     */     }
/*     */ 
/*     */     
/* 621 */     SecurityManager securityManager = System.getSecurityManager();
/* 622 */     if (securityManager != null) {
/* 623 */       FilePermission filePermission = new FilePermission(windowsPath.getPathForPermissionCheck(), "readlink");
/*     */       
/* 625 */       securityManager.checkPermission(filePermission);
/*     */     } 
/*     */     
/* 628 */     String str = WindowsLinkSupport.readLink(windowsPath);
/* 629 */     return WindowsPath.createFromNormalizedPath(windowsFileSystem, str);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsFileSystemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */