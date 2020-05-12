/*     */ package sun.awt.shell;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.stream.Stream;
/*     */ import sun.awt.OSInfo;
/*     */ import sun.awt.windows.WToolkit;
/*     */ import sun.misc.ThreadGroupUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Win32ShellFolderManager2
/*     */   extends ShellFolderManager
/*     */ {
/*     */   private static final int VIEW_LIST = 2;
/*     */   private static final int VIEW_DETAILS = 3;
/*     */   private static final int VIEW_PARENTFOLDER = 8;
/*     */   private static final int VIEW_NEWFOLDER = 11;
/*     */   
/*     */   static {
/*  59 */     WToolkit.loadLibraries();
/*     */   }
/*     */   
/*     */   public ShellFolder createShellFolder(File paramFile) throws FileNotFoundException {
/*     */     try {
/*  64 */       return createShellFolder(getDesktop(), paramFile);
/*  65 */     } catch (InterruptedException interruptedException) {
/*  66 */       throw new FileNotFoundException("Execution was interrupted");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static Win32ShellFolder2 createShellFolder(Win32ShellFolder2 paramWin32ShellFolder2, File paramFile) throws FileNotFoundException, InterruptedException {
/*     */     long l;
/*     */     try {
/*  74 */       l = paramWin32ShellFolder2.parseDisplayName(paramFile.getCanonicalPath());
/*  75 */     } catch (IOException iOException) {
/*  76 */       l = 0L;
/*     */     } 
/*  78 */     if (l == 0L)
/*     */     {
/*  80 */       throw new FileNotFoundException("File " + paramFile.getAbsolutePath() + " not found");
/*     */     }
/*     */     
/*     */     try {
/*  84 */       return createShellFolderFromRelativePIDL(paramWin32ShellFolder2, l);
/*     */     } finally {
/*  86 */       Win32ShellFolder2.releasePIDL(l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static Win32ShellFolder2 createShellFolderFromRelativePIDL(Win32ShellFolder2 paramWin32ShellFolder2, long paramLong) throws InterruptedException {
/*  93 */     while (paramLong != 0L) {
/*  94 */       long l = Win32ShellFolder2.copyFirstPIDLEntry(paramLong);
/*  95 */       if (l != 0L) {
/*  96 */         paramWin32ShellFolder2 = new Win32ShellFolder2(paramWin32ShellFolder2, l);
/*  97 */         paramLong = Win32ShellFolder2.getNextPIDLEntry(paramLong);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 103 */     return paramWin32ShellFolder2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   private static final Image[] STANDARD_VIEW_BUTTONS = new Image[12]; private static Win32ShellFolder2 desktop; private static Win32ShellFolder2 drives; private static Win32ShellFolder2 recent;
/*     */   
/*     */   private static Image getStandardViewButton(int paramInt) {
/* 114 */     Image image = STANDARD_VIEW_BUTTONS[paramInt];
/*     */     
/* 116 */     if (image != null) {
/* 117 */       return image;
/*     */     }
/*     */     
/* 120 */     BufferedImage bufferedImage = new BufferedImage(16, 16, 2);
/*     */     
/* 122 */     bufferedImage.setRGB(0, 0, 16, 16, Win32ShellFolder2.getStandardViewButton0(paramInt), 0, 16);
/*     */     
/* 124 */     STANDARD_VIEW_BUTTONS[paramInt] = bufferedImage;
/*     */     
/* 126 */     return bufferedImage;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Win32ShellFolder2 network;
/*     */   
/*     */   private static Win32ShellFolder2 personal;
/*     */   
/*     */   private static File[] roots;
/*     */   
/*     */   static Win32ShellFolder2 getDesktop() {
/* 137 */     if (desktop == null) {
/*     */       try {
/* 139 */         desktop = new Win32ShellFolder2(0);
/* 140 */       } catch (SecurityException securityException) {
/*     */       
/* 142 */       } catch (IOException iOException) {
/*     */       
/* 144 */       } catch (InterruptedException interruptedException) {}
/*     */     }
/*     */ 
/*     */     
/* 148 */     return desktop;
/*     */   }
/*     */   
/*     */   static Win32ShellFolder2 getDrives() {
/* 152 */     if (drives == null) {
/*     */       try {
/* 154 */         drives = new Win32ShellFolder2(17);
/* 155 */       } catch (SecurityException securityException) {
/*     */       
/* 157 */       } catch (IOException iOException) {
/*     */       
/* 159 */       } catch (InterruptedException interruptedException) {}
/*     */     }
/*     */ 
/*     */     
/* 163 */     return drives;
/*     */   }
/*     */   
/*     */   static Win32ShellFolder2 getRecent() {
/* 167 */     if (recent == null) {
/*     */       try {
/* 169 */         String str = Win32ShellFolder2.getFileSystemPath(8);
/* 170 */         if (str != null) {
/* 171 */           recent = createShellFolder(getDesktop(), new File(str));
/*     */         }
/* 173 */       } catch (SecurityException securityException) {
/*     */       
/* 175 */       } catch (InterruptedException interruptedException) {
/*     */       
/* 177 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */     
/* 181 */     return recent;
/*     */   }
/*     */   
/*     */   static Win32ShellFolder2 getNetwork() {
/* 185 */     if (network == null) {
/*     */       try {
/* 187 */         network = new Win32ShellFolder2(18);
/* 188 */       } catch (SecurityException securityException) {
/*     */       
/* 190 */       } catch (IOException iOException) {
/*     */       
/* 192 */       } catch (InterruptedException interruptedException) {}
/*     */     }
/*     */ 
/*     */     
/* 196 */     return network;
/*     */   }
/*     */   
/*     */   static Win32ShellFolder2 getPersonal() {
/* 200 */     if (personal == null) {
/*     */       try {
/* 202 */         String str = Win32ShellFolder2.getFileSystemPath(5);
/* 203 */         if (str != null) {
/* 204 */           Win32ShellFolder2 win32ShellFolder2 = getDesktop();
/* 205 */           personal = win32ShellFolder2.getChildByPath(str);
/* 206 */           if (personal == null) {
/* 207 */             personal = createShellFolder(getDesktop(), new File(str));
/*     */           }
/* 209 */           if (personal != null) {
/* 210 */             personal.setIsPersonal();
/*     */           }
/*     */         } 
/* 213 */       } catch (SecurityException securityException) {
/*     */       
/* 215 */       } catch (InterruptedException interruptedException) {
/*     */       
/* 217 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */     
/* 221 */     return personal;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(String paramString) {
/* 250 */     if (paramString.equals("fileChooserDefaultFolder")) {
/* 251 */       Win32ShellFolder2 win32ShellFolder2 = getPersonal();
/* 252 */       if (win32ShellFolder2 == null) {
/* 253 */         win32ShellFolder2 = getDesktop();
/*     */       }
/* 255 */       return checkFile(win32ShellFolder2);
/* 256 */     }  if (paramString.equals("roots")) {
/*     */       
/* 258 */       if (roots == null) {
/* 259 */         Win32ShellFolder2 win32ShellFolder2 = getDesktop();
/* 260 */         if (win32ShellFolder2 != null) {
/* 261 */           roots = new File[] { win32ShellFolder2 };
/*     */         } else {
/* 263 */           roots = (File[])super.get(paramString);
/*     */         } 
/*     */       } 
/* 266 */       return checkFiles(roots);
/* 267 */     }  if (paramString.equals("fileChooserComboBoxFolders")) {
/* 268 */       Win32ShellFolder2 win32ShellFolder2 = getDesktop();
/*     */       
/* 270 */       if (win32ShellFolder2 != null && checkFile(win32ShellFolder2) != null) {
/* 271 */         ArrayList<Win32ShellFolder2> arrayList = new ArrayList();
/* 272 */         Win32ShellFolder2 win32ShellFolder21 = getDrives();
/*     */         
/* 274 */         Win32ShellFolder2 win32ShellFolder22 = getRecent();
/* 275 */         if (win32ShellFolder22 != null && OSInfo.getWindowsVersion().compareTo(OSInfo.WINDOWS_2000) >= 0) {
/* 276 */           arrayList.add(win32ShellFolder22);
/*     */         }
/*     */         
/* 279 */         arrayList.add(win32ShellFolder2);
/*     */         
/* 281 */         File[] arrayOfFile = checkFiles(win32ShellFolder2.listFiles());
/* 282 */         Arrays.sort((Object[])arrayOfFile);
/* 283 */         for (File file : arrayOfFile) {
/* 284 */           Win32ShellFolder2 win32ShellFolder23 = (Win32ShellFolder2)file;
/* 285 */           if (!win32ShellFolder23.isFileSystem() || (win32ShellFolder23.isDirectory() && !win32ShellFolder23.isLink())) {
/* 286 */             arrayList.add(win32ShellFolder23);
/*     */             
/* 288 */             if (win32ShellFolder23.equals(win32ShellFolder21)) {
/* 289 */               File[] arrayOfFile1 = checkFiles(win32ShellFolder23.listFiles());
/* 290 */               if (arrayOfFile1 != null && arrayOfFile1.length > 0) {
/* 291 */                 List<File> list = Arrays.asList(arrayOfFile1);
/*     */                 
/* 293 */                 win32ShellFolder23.sortChildren(list);
/* 294 */                 arrayList.addAll(list);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 299 */         return checkFiles((List)arrayList);
/*     */       } 
/* 301 */       return super.get(paramString);
/*     */     } 
/* 303 */     if (paramString.equals("fileChooserShortcutPanelFolders")) {
/* 304 */       Object object; Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 305 */       ArrayList<Win32ShellFolder2> arrayList = new ArrayList();
/* 306 */       byte b = 0;
/*     */       
/*     */       do {
/* 309 */         object = toolkit.getDesktopProperty("win.comdlg.placesBarPlace" + b++);
/*     */         try {
/* 311 */           if (object instanceof Integer) {
/*     */             
/* 313 */             arrayList.add(new Win32ShellFolder2(((Integer)object).intValue()));
/* 314 */           } else if (object instanceof String) {
/*     */             
/* 316 */             arrayList.add(createShellFolder(new File((String)object)));
/*     */           } 
/* 318 */         } catch (IOException iOException) {
/*     */         
/* 320 */         } catch (InterruptedException interruptedException) {
/*     */           
/* 322 */           return new File[0];
/*     */         } 
/* 324 */       } while (object != null);
/*     */       
/* 326 */       if (arrayList.size() == 0)
/*     */       {
/* 328 */         for (File file : new File[] {
/* 329 */             getRecent(), getDesktop(), getPersonal(), getDrives(), getNetwork()
/*     */           }) {
/* 331 */           if (file != null) {
/* 332 */             arrayList.add(file);
/*     */           }
/*     */         } 
/*     */       }
/* 336 */       return checkFiles((List)arrayList);
/* 337 */     }  if (paramString.startsWith("fileChooserIcon ")) {
/* 338 */       byte b; String str = paramString.substring(paramString.indexOf(" ") + 1);
/*     */ 
/*     */ 
/*     */       
/* 342 */       if (str.equals("ListView") || str.equals("ViewMenu")) {
/* 343 */         b = 2;
/* 344 */       } else if (str.equals("DetailsView")) {
/* 345 */         b = 3;
/* 346 */       } else if (str.equals("UpFolder")) {
/* 347 */         b = 8;
/* 348 */       } else if (str.equals("NewFolder")) {
/* 349 */         b = 11;
/*     */       } else {
/* 351 */         return null;
/*     */       } 
/*     */       
/* 354 */       return getStandardViewButton(b);
/* 355 */     }  if (paramString.startsWith("optionPaneIcon ")) {
/*     */       Win32ShellFolder2.SystemIcon systemIcon;
/* 357 */       if (paramString == "optionPaneIcon Error") {
/* 358 */         systemIcon = Win32ShellFolder2.SystemIcon.IDI_ERROR;
/* 359 */       } else if (paramString == "optionPaneIcon Information") {
/* 360 */         systemIcon = Win32ShellFolder2.SystemIcon.IDI_INFORMATION;
/* 361 */       } else if (paramString == "optionPaneIcon Question") {
/* 362 */         systemIcon = Win32ShellFolder2.SystemIcon.IDI_QUESTION;
/* 363 */       } else if (paramString == "optionPaneIcon Warning") {
/* 364 */         systemIcon = Win32ShellFolder2.SystemIcon.IDI_EXCLAMATION;
/*     */       } else {
/* 366 */         return null;
/*     */       } 
/* 368 */       return Win32ShellFolder2.getSystemIcon(systemIcon);
/* 369 */     }  if (paramString.startsWith("shell32Icon ") || paramString.startsWith("shell32LargeIcon ")) {
/* 370 */       String str = paramString.substring(paramString.indexOf(" ") + 1);
/*     */       try {
/* 372 */         int i = Integer.parseInt(str);
/* 373 */         if (i >= 0) {
/* 374 */           return Win32ShellFolder2.getShell32Icon(i, paramString.startsWith("shell32LargeIcon "));
/*     */         }
/* 376 */       } catch (NumberFormatException numberFormatException) {}
/*     */     } 
/*     */     
/* 379 */     return null;
/*     */   }
/*     */   
/*     */   private static File checkFile(File paramFile) {
/* 383 */     SecurityManager securityManager = System.getSecurityManager();
/* 384 */     return (securityManager == null || paramFile == null) ? paramFile : checkFile(paramFile, securityManager);
/*     */   }
/*     */   
/*     */   private static File checkFile(File paramFile, SecurityManager paramSecurityManager) {
/*     */     try {
/* 389 */       paramSecurityManager.checkRead(paramFile.getPath());
/*     */       
/* 391 */       if (paramFile instanceof Win32ShellFolder2) {
/* 392 */         Win32ShellFolder2 win32ShellFolder2 = (Win32ShellFolder2)paramFile;
/* 393 */         if (win32ShellFolder2.isLink()) {
/* 394 */           Win32ShellFolder2 win32ShellFolder21 = (Win32ShellFolder2)win32ShellFolder2.getLinkLocation();
/* 395 */           if (win32ShellFolder21 != null)
/* 396 */             paramSecurityManager.checkRead(win32ShellFolder21.getPath()); 
/*     */         } 
/*     */       } 
/* 399 */       return paramFile;
/* 400 */     } catch (SecurityException securityException) {
/* 401 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   static File[] checkFiles(File[] paramArrayOfFile) {
/* 406 */     SecurityManager securityManager = System.getSecurityManager();
/* 407 */     if (securityManager == null || paramArrayOfFile == null || paramArrayOfFile.length == 0) {
/* 408 */       return paramArrayOfFile;
/*     */     }
/* 410 */     return checkFiles(Arrays.stream(paramArrayOfFile), securityManager);
/*     */   }
/*     */   
/*     */   private static File[] checkFiles(List<File> paramList) {
/* 414 */     SecurityManager securityManager = System.getSecurityManager();
/* 415 */     if (securityManager == null || paramList.isEmpty()) {
/* 416 */       return paramList.<File>toArray(new File[paramList.size()]);
/*     */     }
/* 418 */     return checkFiles(paramList.stream(), securityManager);
/*     */   }
/*     */   
/*     */   private static File[] checkFiles(Stream<File> paramStream, SecurityManager paramSecurityManager) {
/* 422 */     return (File[])paramStream.filter(paramFile -> (checkFile(paramFile, paramSecurityManager) != null))
/* 423 */       .toArray(paramInt -> new File[paramInt]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComputerNode(final File dir) {
/* 431 */     if (dir != null && dir == getDrives()) {
/* 432 */       return true;
/*     */     }
/* 434 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public String run() {
/* 436 */             return dir.getAbsolutePath();
/*     */           }
/*     */         });
/*     */     
/* 440 */     return (str.startsWith("\\\\") && str.indexOf("\\", 2) < 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFileSystemRoot(File paramFile) {
/* 446 */     if (paramFile != null) {
/* 447 */       Win32ShellFolder2 win32ShellFolder2 = getDrives();
/* 448 */       if (paramFile instanceof Win32ShellFolder2) {
/* 449 */         Win32ShellFolder2 win32ShellFolder21 = (Win32ShellFolder2)paramFile;
/* 450 */         if (win32ShellFolder21.isFileSystem()) {
/* 451 */           if (win32ShellFolder21.parent != null) {
/* 452 */             return win32ShellFolder21.parent.equals(win32ShellFolder2);
/*     */           }
/*     */         } else {
/*     */           
/* 456 */           return false;
/*     */         } 
/*     */       } 
/* 459 */       String str = paramFile.getPath();
/*     */       
/* 461 */       if (str.length() != 3 || str.charAt(1) != ':') {
/* 462 */         return false;
/*     */       }
/*     */       
/* 465 */       File[] arrayOfFile = win32ShellFolder2.listFiles();
/*     */       
/* 467 */       return (arrayOfFile != null && Arrays.<File>asList(arrayOfFile).contains(paramFile));
/*     */     } 
/* 469 */     return false;
/*     */   }
/*     */   
/* 472 */   private static List topFolderList = null;
/*     */   static int compareShellFolders(Win32ShellFolder2 paramWin32ShellFolder21, Win32ShellFolder2 paramWin32ShellFolder22) {
/* 474 */     boolean bool1 = paramWin32ShellFolder21.isSpecial();
/* 475 */     boolean bool2 = paramWin32ShellFolder22.isSpecial();
/*     */     
/* 477 */     if (bool1 || bool2) {
/* 478 */       if (topFolderList == null) {
/* 479 */         ArrayList<Win32ShellFolder2> arrayList = new ArrayList();
/* 480 */         arrayList.add(getPersonal());
/* 481 */         arrayList.add(getDesktop());
/* 482 */         arrayList.add(getDrives());
/* 483 */         arrayList.add(getNetwork());
/* 484 */         topFolderList = arrayList;
/*     */       } 
/* 486 */       int i = topFolderList.indexOf(paramWin32ShellFolder21);
/* 487 */       int j = topFolderList.indexOf(paramWin32ShellFolder22);
/* 488 */       if (i >= 0 && j >= 0)
/* 489 */         return i - j; 
/* 490 */       if (i >= 0)
/* 491 */         return -1; 
/* 492 */       if (j >= 0) {
/* 493 */         return 1;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 498 */     if (bool1 && !bool2)
/* 499 */       return -1; 
/* 500 */     if (bool2 && !bool1) {
/* 501 */       return 1;
/*     */     }
/*     */     
/* 504 */     return compareNames(paramWin32ShellFolder21.getAbsolutePath(), paramWin32ShellFolder22.getAbsolutePath());
/*     */   }
/*     */ 
/*     */   
/*     */   static int compareNames(String paramString1, String paramString2) {
/* 509 */     int i = paramString1.compareToIgnoreCase(paramString2);
/* 510 */     if (i != 0) {
/* 511 */       return i;
/*     */     }
/*     */ 
/*     */     
/* 515 */     return paramString1.compareTo(paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ShellFolder.Invoker createInvoker() {
/* 521 */     return new ComInvoker();
/*     */   }
/*     */   static native void initializeCom();
/*     */   
/*     */   static native void uninitializeCom();
/*     */   
/*     */   private static class ComInvoker extends ThreadPoolExecutor implements ThreadFactory, ShellFolder.Invoker { private ComInvoker() {
/* 528 */       super(1, 1, 0L, TimeUnit.DAYS, new LinkedBlockingQueue<>());
/* 529 */       allowCoreThreadTimeOut(false);
/* 530 */       setThreadFactory(this);
/* 531 */       final Runnable shutdownHook = new Runnable() {
/*     */           public void run() {
/* 533 */             AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */                   public Void run() {
/* 535 */                     Win32ShellFolderManager2.ComInvoker.this.shutdownNow();
/* 536 */                     return null;
/*     */                   }
/*     */                 });
/*     */           }
/*     */         };
/* 541 */       AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */             public Void run() {
/* 543 */               Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook));
/*     */ 
/*     */               
/* 546 */               return null;
/*     */             }
/*     */           });
/*     */     }
/*     */     private static Thread comThread;
/*     */     public synchronized Thread newThread(final Runnable task) {
/* 552 */       Runnable runnable = new Runnable() {
/*     */           public void run() {
/*     */             try {
/* 555 */               Win32ShellFolderManager2.initializeCom();
/* 556 */               task.run();
/*     */             } finally {
/* 558 */               Win32ShellFolderManager2.uninitializeCom();
/*     */             } 
/*     */           }
/*     */         };
/* 562 */       comThread = AccessController.<Thread>doPrivileged(() -> {
/*     */             ThreadGroup threadGroup = ThreadGroupUtils.getRootThreadGroup();
/*     */ 
/*     */             
/*     */             Thread thread = new Thread(threadGroup, param1Runnable, "Swing-Shell");
/*     */             
/*     */             thread.setDaemon(true);
/*     */             
/*     */             return thread;
/*     */           });
/*     */       
/* 573 */       return comThread;
/*     */     }
/*     */     public <T> T invoke(Callable<T> param1Callable) throws Exception {
/*     */       final Future<T> future;
/* 577 */       if (Thread.currentThread() == comThread)
/*     */       {
/*     */         
/* 580 */         return param1Callable.call();
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 585 */         future = submit(param1Callable);
/* 586 */       } catch (RejectedExecutionException rejectedExecutionException) {
/* 587 */         throw new InterruptedException(rejectedExecutionException.getMessage());
/*     */       } 
/*     */       
/*     */       try {
/* 591 */         return future.get();
/* 592 */       } catch (InterruptedException interruptedException) {
/* 593 */         AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */               public Void run() {
/* 595 */                 future.cancel(true);
/*     */                 
/* 597 */                 return null;
/*     */               }
/*     */             });
/*     */         
/* 601 */         throw interruptedException;
/* 602 */       } catch (ExecutionException executionException) {
/* 603 */         Throwable throwable = executionException.getCause();
/*     */         
/* 605 */         if (throwable instanceof Exception) {
/* 606 */           throw (Exception)throwable;
/*     */         }
/*     */         
/* 609 */         if (throwable instanceof Error) {
/* 610 */           throw (Error)throwable;
/*     */         }
/*     */         
/* 613 */         throw new RuntimeException("Unexpected error", throwable);
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\shell\Win32ShellFolderManager2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */