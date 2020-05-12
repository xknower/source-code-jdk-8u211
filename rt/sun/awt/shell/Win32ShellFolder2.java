/*      */ package sun.awt.shell;
/*      */ import java.awt.Image;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectStreamException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.Callable;
/*      */ import sun.java2d.Disposer;
/*      */ import sun.java2d.DisposerRecord;
/*      */ 
/*      */ final class Win32ShellFolder2 extends ShellFolder {
/*      */   public static final int DESKTOP = 0;
/*      */   public static final int INTERNET = 1;
/*      */   public static final int PROGRAMS = 2;
/*      */   public static final int CONTROLS = 3;
/*      */   public static final int PRINTERS = 4;
/*      */   public static final int PERSONAL = 5;
/*      */   public static final int FAVORITES = 6;
/*      */   public static final int STARTUP = 7;
/*      */   public static final int RECENT = 8;
/*      */   public static final int SENDTO = 9;
/*      */   public static final int BITBUCKET = 10;
/*      */   public static final int STARTMENU = 11;
/*      */   public static final int DESKTOPDIRECTORY = 16;
/*      */   public static final int DRIVES = 17;
/*      */   public static final int NETWORK = 18;
/*      */   public static final int NETHOOD = 19;
/*      */   public static final int FONTS = 20;
/*      */   public static final int TEMPLATES = 21;
/*      */   public static final int COMMON_STARTMENU = 22;
/*      */   public static final int COMMON_PROGRAMS = 23;
/*      */   public static final int COMMON_STARTUP = 24;
/*      */   public static final int COMMON_DESKTOPDIRECTORY = 25;
/*      */   public static final int APPDATA = 26;
/*      */   public static final int PRINTHOOD = 27;
/*      */   public static final int ALTSTARTUP = 29;
/*      */   public static final int COMMON_ALTSTARTUP = 30;
/*      */   public static final int COMMON_FAVORITES = 31;
/*      */   public static final int INTERNET_CACHE = 32;
/*      */   public static final int COOKIES = 33;
/*      */   public static final int HISTORY = 34;
/*      */   public static final int ATTRIB_CANCOPY = 1;
/*      */   public static final int ATTRIB_CANMOVE = 2;
/*      */   public static final int ATTRIB_CANLINK = 4;
/*      */   public static final int ATTRIB_CANRENAME = 16;
/*      */   public static final int ATTRIB_CANDELETE = 32;
/*      */   public static final int ATTRIB_HASPROPSHEET = 64;
/*      */   public static final int ATTRIB_DROPTARGET = 256;
/*      */   public static final int ATTRIB_LINK = 65536;
/*      */   public static final int ATTRIB_SHARE = 131072;
/*      */   public static final int ATTRIB_READONLY = 262144;
/*      */   public static final int ATTRIB_GHOSTED = 524288;
/*      */   public static final int ATTRIB_HIDDEN = 524288;
/*      */   public static final int ATTRIB_FILESYSANCESTOR = 268435456;
/*      */   public static final int ATTRIB_FOLDER = 536870912;
/*      */   public static final int ATTRIB_FILESYSTEM = 1073741824;
/*      */   public static final int ATTRIB_HASSUBFOLDER = -2147483648;
/*      */   public static final int ATTRIB_VALIDATE = 16777216;
/*      */   public static final int ATTRIB_REMOVABLE = 33554432;
/*      */   public static final int ATTRIB_COMPRESSED = 67108864;
/*      */   public static final int ATTRIB_BROWSABLE = 134217728;
/*      */   public static final int ATTRIB_NONENUMERATED = 1048576;
/*      */   public static final int ATTRIB_NEWCONTENT = 2097152;
/*      */   public static final int SHGDN_NORMAL = 0;
/*      */   public static final int SHGDN_INFOLDER = 1;
/*      */   public static final int SHGDN_INCLUDE_NONFILESYS = 8192;
/*      */   public static final int SHGDN_FORADDRESSBAR = 16384;
/*      */   public static final int SHGDN_FORPARSING = 32768;
/*      */   
/*      */   static {
/*   77 */     initIDs();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum SystemIcon
/*      */   {
/*  145 */     IDI_APPLICATION(32512),
/*  146 */     IDI_HAND(32513),
/*  147 */     IDI_ERROR(32513),
/*  148 */     IDI_QUESTION(32514),
/*  149 */     IDI_EXCLAMATION(32515),
/*  150 */     IDI_WARNING(32515),
/*  151 */     IDI_ASTERISK(32516),
/*  152 */     IDI_INFORMATION(32516),
/*  153 */     IDI_WINLOGO(32517);
/*      */     
/*      */     private final int iconID;
/*      */     
/*      */     SystemIcon(int param1Int1) {
/*  158 */       this.iconID = param1Int1;
/*      */     }
/*      */     
/*      */     public int getIconID() {
/*  162 */       return this.iconID;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class FolderDisposer
/*      */     implements DisposerRecord
/*      */   {
/*      */     long absolutePIDL;
/*      */     
/*      */     long pIShellFolder;
/*      */     
/*      */     long relativePIDL;
/*      */     
/*      */     boolean disposed;
/*      */ 
/*      */     
/*      */     public void dispose() {
/*  181 */       if (this.disposed)
/*  182 */         return;  ShellFolder.invoke(new Callable<Void>() {
/*      */             public Void call() {
/*  184 */               if (Win32ShellFolder2.FolderDisposer.this.relativePIDL != 0L) {
/*  185 */                 Win32ShellFolder2.releasePIDL(Win32ShellFolder2.FolderDisposer.this.relativePIDL);
/*      */               }
/*  187 */               if (Win32ShellFolder2.FolderDisposer.this.absolutePIDL != 0L) {
/*  188 */                 Win32ShellFolder2.releasePIDL(Win32ShellFolder2.FolderDisposer.this.absolutePIDL);
/*      */               }
/*  190 */               if (Win32ShellFolder2.FolderDisposer.this.pIShellFolder != 0L) {
/*  191 */                 Win32ShellFolder2.releaseIShellFolder(Win32ShellFolder2.FolderDisposer.this.pIShellFolder);
/*      */               }
/*  193 */               return null;
/*      */             }
/*      */           });
/*  196 */       this.disposed = true;
/*      */     }
/*      */   }
/*  199 */   FolderDisposer disposer = new FolderDisposer();
/*      */   private void setIShellFolder(long paramLong) {
/*  201 */     this.disposer.pIShellFolder = paramLong;
/*      */   }
/*      */   private void setRelativePIDL(long paramLong) {
/*  204 */     this.disposer.relativePIDL = paramLong;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  209 */   private long pIShellIcon = -1L;
/*  210 */   private String folderType = null;
/*  211 */   private String displayName = null;
/*  212 */   private Image smallIcon = null;
/*  213 */   private Image largeIcon = null;
/*  214 */   private Boolean isDir = null;
/*      */   
/*      */   private boolean isPersonal;
/*      */   
/*      */   private volatile Boolean cachedIsFileSystem;
/*      */   private volatile Boolean cachedIsLink;
/*      */   
/*      */   private static String composePathForCsidl(int paramInt) throws IOException, InterruptedException {
/*  222 */     String str = getFileSystemPath(paramInt);
/*  223 */     return (str == null) ? ("ShellFolder: 0x" + 
/*  224 */       Integer.toHexString(paramInt)) : str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Win32ShellFolder2(final int csidl) throws IOException, InterruptedException {
/*  235 */     super((ShellFolder)null, composePathForCsidl(csidl));
/*      */     
/*  237 */     invoke(new Callable<Void>() {
/*      */           public Void call() throws InterruptedException {
/*  239 */             if (csidl == 0) {
/*  240 */               Win32ShellFolder2.this.initDesktop();
/*      */             } else {
/*  242 */               Win32ShellFolder2.this.initSpecial(Win32ShellFolder2.this.getDesktop().getIShellFolder(), csidl);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  248 */               long l = Win32ShellFolder2.this.disposer.relativePIDL;
/*  249 */               Win32ShellFolder2.this.parent = Win32ShellFolder2.this.getDesktop();
/*  250 */               while (l != 0L) {
/*      */                 
/*  252 */                 long l1 = Win32ShellFolder2.copyFirstPIDLEntry(l);
/*  253 */                 if (l1 != 0L) {
/*      */ 
/*      */                   
/*  256 */                   l = Win32ShellFolder2.getNextPIDLEntry(l);
/*  257 */                   if (l != 0L) {
/*      */ 
/*      */ 
/*      */                     
/*  261 */                     Win32ShellFolder2.this.parent = new Win32ShellFolder2((Win32ShellFolder2)Win32ShellFolder2.this.parent, l1);
/*      */                     
/*      */                     continue;
/*      */                   } 
/*  265 */                   Win32ShellFolder2.this.disposer.relativePIDL = l1;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  272 */             return null;
/*      */           }
/*      */         }InterruptedException.class);
/*      */     
/*  276 */     Disposer.addRecord(this, this.disposer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Win32ShellFolder2(Win32ShellFolder2 paramWin32ShellFolder2, long paramLong1, long paramLong2, String paramString) {
/*  284 */     super(paramWin32ShellFolder2, (paramString != null) ? paramString : "ShellFolder: ");
/*  285 */     this.disposer.pIShellFolder = paramLong1;
/*  286 */     this.disposer.relativePIDL = paramLong2;
/*  287 */     Disposer.addRecord(this, this.disposer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Win32ShellFolder2(Win32ShellFolder2 paramWin32ShellFolder2, long paramLong) throws InterruptedException {
/*  295 */     super(paramWin32ShellFolder2, 
/*  296 */         invoke(new Callable<String>(paramWin32ShellFolder2, paramLong) {
/*      */             public String call() {
/*  298 */               return Win32ShellFolder2.getFileSystemPath(parent.getIShellFolder(), relativePIDL);
/*      */             }
/*      */           }RuntimeException.class));
/*      */     
/*  302 */     this.disposer.relativePIDL = paramLong;
/*  303 */     Disposer.addRecord(this, this.disposer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIsPersonal() {
/*  317 */     this.isPersonal = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object writeReplace() throws ObjectStreamException {
/*  332 */     return invoke(new Callable<File>() {
/*      */           public File call() {
/*  334 */             if (Win32ShellFolder2.this.isFileSystem()) {
/*  335 */               return new File(Win32ShellFolder2.this.getPath());
/*      */             }
/*  337 */             Win32ShellFolder2 win32ShellFolder2 = Win32ShellFolderManager2.getDrives();
/*  338 */             if (win32ShellFolder2 != null) {
/*  339 */               File[] arrayOfFile = win32ShellFolder2.listFiles();
/*  340 */               if (arrayOfFile != null) {
/*  341 */                 for (byte b = 0; b < arrayOfFile.length; b++) {
/*  342 */                   if (arrayOfFile[b] instanceof Win32ShellFolder2) {
/*  343 */                     Win32ShellFolder2 win32ShellFolder21 = (Win32ShellFolder2)arrayOfFile[b];
/*  344 */                     if (win32ShellFolder21.isFileSystem() && !win32ShellFolder21.hasAttribute(33554432)) {
/*  345 */                       return new File(win32ShellFolder21.getPath());
/*      */                     }
/*      */                   } 
/*      */                 } 
/*      */               }
/*      */             } 
/*      */             
/*  352 */             return new File("C:\\");
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dispose() {
/*  363 */     this.disposer.dispose();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long getIShellFolder() {
/*  396 */     if (this.disposer.pIShellFolder == 0L) {
/*      */       try {
/*  398 */         this.disposer.pIShellFolder = ((Long)invoke(new Callable<Long>() {
/*      */               public Long call() {
/*  400 */                 assert Win32ShellFolder2.this.isDirectory();
/*  401 */                 assert Win32ShellFolder2.this.parent != null;
/*  402 */                 long l1 = Win32ShellFolder2.this.getParentIShellFolder();
/*  403 */                 if (l1 == 0L) {
/*  404 */                   throw new InternalError("Parent IShellFolder was null for " + Win32ShellFolder2.this
/*  405 */                       .getAbsolutePath());
/*      */                 }
/*      */ 
/*      */ 
/*      */                 
/*  410 */                 long l2 = Win32ShellFolder2.bindToObject(l1, Win32ShellFolder2.this.disposer.relativePIDL);
/*      */                 
/*  412 */                 if (l2 == 0L) {
/*  413 */                   throw new InternalError("Unable to bind " + Win32ShellFolder2.this
/*  414 */                       .getAbsolutePath() + " to parent");
/*      */                 }
/*  416 */                 return Long.valueOf(l2);
/*      */               }
/*      */             }RuntimeException.class)).longValue();
/*  419 */       } catch (InterruptedException interruptedException) {}
/*      */     }
/*      */ 
/*      */     
/*  423 */     return this.disposer.pIShellFolder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getParentIShellFolder() {
/*  430 */     Win32ShellFolder2 win32ShellFolder2 = (Win32ShellFolder2)getParentFile();
/*  431 */     if (win32ShellFolder2 == null)
/*      */     {
/*      */       
/*  434 */       return getIShellFolder();
/*      */     }
/*  436 */     return win32ShellFolder2.getIShellFolder();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getRelativePIDL() {
/*  443 */     if (this.disposer.relativePIDL == 0L) {
/*  444 */       throw new InternalError("Should always have a relative PIDL");
/*      */     }
/*  446 */     return this.disposer.relativePIDL;
/*      */   }
/*      */   
/*      */   private long getAbsolutePIDL() {
/*  450 */     if (this.parent == null)
/*      */     {
/*  452 */       return getRelativePIDL();
/*      */     }
/*  454 */     if (this.disposer.absolutePIDL == 0L) {
/*  455 */       this.disposer.absolutePIDL = combinePIDLs(((Win32ShellFolder2)this.parent).getAbsolutePIDL(), getRelativePIDL());
/*      */     }
/*      */     
/*  458 */     return this.disposer.absolutePIDL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Win32ShellFolder2 getDesktop() {
/*  466 */     return Win32ShellFolderManager2.getDesktop();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getDesktopIShellFolder() {
/*  473 */     return getDesktop().getIShellFolder();
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean pathsEqual(String paramString1, String paramString2) {
/*  478 */     return paramString1.equalsIgnoreCase(paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  485 */     if (paramObject == null || !(paramObject instanceof Win32ShellFolder2)) {
/*      */       
/*  487 */       if (!(paramObject instanceof File)) {
/*  488 */         return super.equals(paramObject);
/*      */       }
/*  490 */       return pathsEqual(getPath(), ((File)paramObject).getPath());
/*      */     } 
/*  492 */     Win32ShellFolder2 win32ShellFolder2 = (Win32ShellFolder2)paramObject;
/*  493 */     if ((this.parent == null && win32ShellFolder2.parent != null) || (this.parent != null && win32ShellFolder2.parent == null))
/*      */     {
/*  495 */       return false;
/*      */     }
/*      */     
/*  498 */     if (isFileSystem() && win32ShellFolder2.isFileSystem())
/*      */     {
/*  500 */       return (pathsEqual(getPath(), win32ShellFolder2.getPath()) && (this.parent == win32ShellFolder2.parent || this.parent
/*  501 */         .equals(win32ShellFolder2.parent)));
/*      */     }
/*      */     
/*  504 */     if (this.parent == win32ShellFolder2.parent || this.parent.equals(win32ShellFolder2.parent)) {
/*      */       try {
/*  506 */         return pidlsEqual(getParentIShellFolder(), this.disposer.relativePIDL, win32ShellFolder2.disposer.relativePIDL);
/*  507 */       } catch (InterruptedException interruptedException) {
/*  508 */         return false;
/*      */       } 
/*      */     }
/*      */     
/*  512 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean pidlsEqual(final long pIShellFolder, final long pidl1, final long pidl2) throws InterruptedException {
/*  517 */     return ((Boolean)invoke(new Callable<Boolean>() {
/*      */           public Boolean call() {
/*  519 */             return Boolean.valueOf((Win32ShellFolder2.compareIDs(pIShellFolder, pidl1, pidl2) == 0));
/*      */           }
/*      */         }RuntimeException.class)).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFileSystem() {
/*  533 */     if (this.cachedIsFileSystem == null) {
/*  534 */       this.cachedIsFileSystem = Boolean.valueOf(hasAttribute(1073741824));
/*      */     }
/*      */     
/*  537 */     return this.cachedIsFileSystem.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasAttribute(final int attribute) {
/*  544 */     Boolean bool = invoke(new Callable<Boolean>()
/*      */         {
/*      */           public Boolean call() {
/*  547 */             return Boolean.valueOf(((Win32ShellFolder2.getAttributes0(Win32ShellFolder2.this.getParentIShellFolder(), Win32ShellFolder2.this
/*  548 */                   .getRelativePIDL(), attribute) & attribute) != 0));
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  553 */     return (bool != null && bool.booleanValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getFileSystemPath(long paramLong1, long paramLong2) {
/*  570 */     int i = 536936448;
/*  571 */     if (paramLong1 == Win32ShellFolderManager2.getNetwork().getIShellFolder() && 
/*  572 */       getAttributes0(paramLong1, paramLong2, i) == i) {
/*      */ 
/*      */       
/*  575 */       String str = getFileSystemPath(Win32ShellFolderManager2.getDesktop().getIShellFolder(), 
/*  576 */           getLinkLocation(paramLong1, paramLong2, false));
/*  577 */       if (str != null && str.startsWith("\\\\")) {
/*  578 */         return str;
/*      */       }
/*      */     } 
/*  581 */     return getDisplayNameOf(paramLong1, paramLong2, 32768);
/*      */   }
/*      */ 
/*      */   
/*      */   static String getFileSystemPath(final int csidl) throws IOException, InterruptedException {
/*  586 */     String str = invoke(new Callable<String>() {
/*      */           public String call() throws IOException {
/*  588 */             return Win32ShellFolder2.getFileSystemPath0(csidl);
/*      */           }
/*      */         },  IOException.class);
/*  591 */     if (str != null) {
/*  592 */       SecurityManager securityManager = System.getSecurityManager();
/*  593 */       if (securityManager != null) {
/*  594 */         securityManager.checkRead(str);
/*      */       }
/*      */     } 
/*  597 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isNetworkRoot(String paramString) {
/*  606 */     return (paramString.equals("\\\\") || paramString.equals("\\") || paramString.equals("//") || paramString.equals("/"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getParentFile() {
/*  614 */     return this.parent;
/*      */   }
/*      */   
/*      */   public boolean isDirectory() {
/*  618 */     if (this.isDir == null)
/*      */     {
/*      */       
/*  621 */       if (hasAttribute(536870912) && !hasAttribute(134217728)) {
/*  622 */         this.isDir = Boolean.TRUE;
/*  623 */       } else if (isLink()) {
/*  624 */         ShellFolder shellFolder = getLinkLocation(false);
/*  625 */         this.isDir = Boolean.valueOf((shellFolder != null && shellFolder.isDirectory()));
/*      */       } else {
/*  627 */         this.isDir = Boolean.FALSE;
/*      */       } 
/*      */     }
/*  630 */     return this.isDir.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long getEnumObjects(final boolean includeHiddenFiles) throws InterruptedException {
/*  639 */     return ((Long)invoke(new Callable<Long>() {
/*      */           public Long call() {
/*  641 */             boolean bool = (Win32ShellFolder2.this.disposer.pIShellFolder == Win32ShellFolder2.this.getDesktopIShellFolder()) ? true : false;
/*      */             
/*  643 */             return Long.valueOf(Win32ShellFolder2.this.getEnumObjects(Win32ShellFolder2.this.disposer.pIShellFolder, bool, includeHiddenFiles));
/*      */           }
/*      */         }RuntimeException.class)).longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File[] listFiles(final boolean includeHiddenFiles) {
/*  674 */     SecurityManager securityManager = System.getSecurityManager();
/*  675 */     if (securityManager != null) {
/*  676 */       securityManager.checkRead(getPath());
/*      */     }
/*      */     
/*      */     try {
/*  680 */       File[] arrayOfFile = invoke((Callable)new Callable<File[]>() {
/*      */             public File[] call() throws InterruptedException {
/*  682 */               if (!Win32ShellFolder2.this.isDirectory()) {
/*  683 */                 return null;
/*      */               }
/*      */ 
/*      */ 
/*      */               
/*  688 */               if (Win32ShellFolder2.this.isLink() && !Win32ShellFolder2.this.hasAttribute(536870912)) {
/*  689 */                 return new File[0];
/*      */               }
/*      */               
/*  692 */               Win32ShellFolder2 win32ShellFolder21 = Win32ShellFolderManager2.getDesktop();
/*  693 */               Win32ShellFolder2 win32ShellFolder22 = Win32ShellFolderManager2.getPersonal();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  698 */               long l1 = Win32ShellFolder2.this.getIShellFolder();
/*      */               
/*  700 */               ArrayList<Win32ShellFolder2> arrayList = new ArrayList();
/*  701 */               long l2 = Win32ShellFolder2.this.getEnumObjects(includeHiddenFiles);
/*  702 */               if (l2 != 0L) {
/*      */                 try {
/*      */                   long l;
/*  705 */                   int i = 1342177280;
/*      */                   do {
/*  707 */                     l = Win32ShellFolder2.this.getNextChild(l2);
/*  708 */                     boolean bool = true;
/*  709 */                     if (l != 0L && (Win32ShellFolder2
/*  710 */                       .getAttributes0(l1, l, i) & i) != 0) {
/*      */                       Win32ShellFolder2 win32ShellFolder2;
/*  712 */                       if (Win32ShellFolder2.this.equals(win32ShellFolder21) && win32ShellFolder22 != null && Win32ShellFolder2
/*      */                         
/*  714 */                         .pidlsEqual(l1, l, win32ShellFolder22.disposer.relativePIDL)) {
/*  715 */                         win32ShellFolder2 = win32ShellFolder22;
/*      */                       } else {
/*  717 */                         win32ShellFolder2 = new Win32ShellFolder2(Win32ShellFolder2.this, l);
/*  718 */                         bool = false;
/*      */                       } 
/*  720 */                       arrayList.add(win32ShellFolder2);
/*      */                     } 
/*  722 */                     if (!bool)
/*  723 */                       continue;  Win32ShellFolder2.releasePIDL(l);
/*      */                   }
/*  725 */                   while (l != 0L && !Thread.currentThread().isInterrupted());
/*      */                 } finally {
/*  727 */                   Win32ShellFolder2.this.releaseEnumObjects(l2);
/*      */                 } 
/*      */               }
/*  730 */               return Thread.currentThread().isInterrupted() ? new File[0] : arrayList
/*      */                 
/*  732 */                 .<File>toArray((File[])new ShellFolder[arrayList.size()]);
/*      */             }
/*      */           }InterruptedException.class);
/*      */       
/*  736 */       return Win32ShellFolderManager2.checkFiles(arrayOfFile);
/*      */     }
/*  738 */     catch (InterruptedException interruptedException) {
/*  739 */       return new File[0];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Win32ShellFolder2 getChildByPath(final String filePath) throws InterruptedException {
/*  750 */     return invoke(new Callable<Win32ShellFolder2>() {
/*      */           public Win32ShellFolder2 call() throws InterruptedException {
/*  752 */             long l1 = Win32ShellFolder2.this.getIShellFolder();
/*  753 */             long l2 = Win32ShellFolder2.this.getEnumObjects(true);
/*  754 */             Win32ShellFolder2 win32ShellFolder2 = null;
/*      */             
/*      */             long l3;
/*  757 */             while ((l3 = Win32ShellFolder2.this.getNextChild(l2)) != 0L) {
/*  758 */               if (Win32ShellFolder2.getAttributes0(l1, l3, 1073741824) != 0) {
/*  759 */                 String str = Win32ShellFolder2.getFileSystemPath(l1, l3);
/*  760 */                 if (str != null && str.equalsIgnoreCase(filePath)) {
/*  761 */                   long l = Win32ShellFolder2.bindToObject(l1, l3);
/*  762 */                   win32ShellFolder2 = new Win32ShellFolder2(Win32ShellFolder2.this, l, l3, str);
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*  767 */               Win32ShellFolder2.releasePIDL(l3);
/*      */             } 
/*  769 */             Win32ShellFolder2.this.releaseEnumObjects(l2);
/*  770 */             return win32ShellFolder2;
/*      */           }
/*      */         }InterruptedException.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLink() {
/*  781 */     if (this.cachedIsLink == null) {
/*  782 */       this.cachedIsLink = Boolean.valueOf(hasAttribute(65536));
/*      */     }
/*      */     
/*  785 */     return this.cachedIsLink.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isHidden() {
/*  792 */     return hasAttribute(524288);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShellFolder getLinkLocation() {
/*  806 */     return getLinkLocation(true);
/*      */   }
/*      */   
/*      */   private ShellFolder getLinkLocation(final boolean resolve) {
/*  810 */     return invoke(new Callable<ShellFolder>() {
/*      */           public ShellFolder call() {
/*  812 */             if (!Win32ShellFolder2.this.isLink()) {
/*  813 */               return null;
/*      */             }
/*      */             
/*  816 */             Win32ShellFolder2 win32ShellFolder2 = null;
/*  817 */             long l = Win32ShellFolder2.getLinkLocation(Win32ShellFolder2.this.getParentIShellFolder(), Win32ShellFolder2.this
/*  818 */                 .getRelativePIDL(), resolve);
/*  819 */             if (l != 0L) {
/*      */               
/*      */               try {
/*  822 */                 win32ShellFolder2 = Win32ShellFolderManager2.createShellFolderFromRelativePIDL(Win32ShellFolder2.this.getDesktop(), l);
/*      */               }
/*  824 */               catch (InterruptedException interruptedException) {
/*      */               
/*  826 */               } catch (InternalError internalError) {}
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  831 */             return win32ShellFolder2;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   long parseDisplayName(final String name) throws IOException, InterruptedException {
/*  838 */     return ((Long)invoke(new Callable<Long>() {
/*      */           public Long call() throws IOException {
/*  840 */             return Long.valueOf(Win32ShellFolder2.parseDisplayName0(Win32ShellFolder2.this.getIShellFolder(), name));
/*      */           }
/*      */         }IOException.class)).longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDisplayName() {
/*  858 */     if (this.displayName == null) {
/*  859 */       this
/*  860 */         .displayName = invoke(new Callable<String>() {
/*      */             public String call() {
/*  862 */               return Win32ShellFolder2.getDisplayNameOf(Win32ShellFolder2.this.getParentIShellFolder(), Win32ShellFolder2.this
/*  863 */                   .getRelativePIDL(), 0);
/*      */             }
/*      */           });
/*      */     }
/*  867 */     return this.displayName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFolderType() {
/*  878 */     if (this.folderType == null) {
/*  879 */       final long absolutePIDL = getAbsolutePIDL();
/*  880 */       this
/*  881 */         .folderType = invoke(new Callable<String>() {
/*      */             public String call() {
/*  883 */               return Win32ShellFolder2.getFolderType(absolutePIDL);
/*      */             }
/*      */           });
/*      */     } 
/*  887 */     return this.folderType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getExecutableType() {
/*  897 */     if (!isFileSystem()) {
/*  898 */       return null;
/*      */     }
/*  900 */     return getExecutableType(getAbsolutePath());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  907 */   private static Map smallSystemImages = new HashMap<>();
/*  908 */   private static Map largeSystemImages = new HashMap<>();
/*  909 */   private static Map smallLinkedSystemImages = new HashMap<>();
/*  910 */   private static Map largeLinkedSystemImages = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int LVCFMT_LEFT = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int LVCFMT_RIGHT = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int LVCFMT_CENTER = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long getIShellIcon() {
/*  942 */     if (this.pIShellIcon == -1L) {
/*  943 */       this.pIShellIcon = getIShellIcon(getIShellFolder());
/*      */     }
/*      */     
/*  946 */     return this.pIShellIcon;
/*      */   }
/*      */   
/*      */   private static Image makeIcon(long paramLong, boolean paramBoolean) {
/*  950 */     if (paramLong != 0L && paramLong != -1L) {
/*      */       
/*  952 */       byte b = paramBoolean ? 32 : 16;
/*  953 */       int[] arrayOfInt = getIconBits(paramLong, b);
/*  954 */       if (arrayOfInt != null) {
/*  955 */         BufferedImage bufferedImage = new BufferedImage(b, b, 2);
/*  956 */         bufferedImage.setRGB(0, 0, b, b, arrayOfInt, 0, b);
/*  957 */         return bufferedImage;
/*      */       } 
/*      */     } 
/*  960 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image getIcon(final boolean getLargeIcon) {
/*  968 */     Image image = getLargeIcon ? this.largeIcon : this.smallIcon;
/*  969 */     if (image == null) {
/*      */       
/*  971 */       image = invoke(new Callable<Image>() {
/*      */             public Image call() {
/*  973 */               Image image = null;
/*  974 */               if (Win32ShellFolder2.this.isFileSystem()) {
/*      */                 
/*  976 */                 long l1 = (Win32ShellFolder2.this.parent != null) ? ((Win32ShellFolder2)Win32ShellFolder2.this.parent).getIShellIcon() : 0L;
/*      */                 
/*  978 */                 long l2 = Win32ShellFolder2.this.getRelativePIDL();
/*      */ 
/*      */                 
/*  981 */                 int i = Win32ShellFolder2.getIconIndex(l1, l2);
/*  982 */                 if (i > 0) {
/*      */                   Map<Integer, Image> map;
/*  984 */                   if (Win32ShellFolder2.this.isLink()) {
/*  985 */                     map = getLargeIcon ? Win32ShellFolder2.largeLinkedSystemImages : Win32ShellFolder2.smallLinkedSystemImages;
/*      */                   } else {
/*  987 */                     map = getLargeIcon ? Win32ShellFolder2.largeSystemImages : Win32ShellFolder2.smallSystemImages;
/*      */                   } 
/*  989 */                   image = (Image)map.get(Integer.valueOf(i));
/*  990 */                   if (image == null) {
/*  991 */                     long l = Win32ShellFolder2.getIcon(Win32ShellFolder2.this.getAbsolutePath(), getLargeIcon);
/*  992 */                     image = Win32ShellFolder2.makeIcon(l, getLargeIcon);
/*  993 */                     Win32ShellFolder2.disposeIcon(l);
/*  994 */                     if (image != null) {
/*  995 */                       map.put(Integer.valueOf(i), image);
/*      */                     }
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */               
/* 1001 */               if (image == null) {
/*      */                 
/* 1003 */                 long l = Win32ShellFolder2.extractIcon(Win32ShellFolder2.this.getParentIShellFolder(), Win32ShellFolder2.this
/* 1004 */                     .getRelativePIDL(), getLargeIcon);
/* 1005 */                 image = Win32ShellFolder2.makeIcon(l, getLargeIcon);
/* 1006 */                 Win32ShellFolder2.disposeIcon(l);
/*      */               } 
/*      */               
/* 1009 */               if (image == null) {
/* 1010 */                 image = Win32ShellFolder2.this.getIcon(getLargeIcon);
/*      */               }
/* 1012 */               return image;
/*      */             }
/*      */           });
/* 1015 */       if (getLargeIcon) {
/* 1016 */         this.largeIcon = image;
/*      */       } else {
/* 1018 */         this.smallIcon = image;
/*      */       } 
/*      */     } 
/* 1021 */     return image;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Image getSystemIcon(SystemIcon paramSystemIcon) {
/* 1028 */     long l = getSystemIcon(paramSystemIcon.getIconID());
/* 1029 */     Image image = makeIcon(l, true);
/* 1030 */     disposeIcon(l);
/* 1031 */     return image;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Image getShell32Icon(int paramInt, boolean paramBoolean) {
/* 1038 */     boolean bool = true;
/*      */     
/* 1040 */     byte b = paramBoolean ? 32 : 16;
/*      */     
/* 1042 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 1043 */     String str = (String)toolkit.getDesktopProperty("win.icon.shellIconBPP");
/* 1044 */     if (str != null) {
/* 1045 */       bool = str.equals("4");
/*      */     }
/*      */     
/* 1048 */     long l = getIconResource("shell32.dll", paramInt, b, b, bool);
/* 1049 */     if (l != 0L) {
/* 1050 */       Image image = makeIcon(l, paramBoolean);
/* 1051 */       disposeIcon(l);
/* 1052 */       return image;
/*      */     } 
/* 1054 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getCanonicalFile() throws IOException {
/* 1064 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSpecial() {
/* 1071 */     return (this.isPersonal || !isFileSystem() || this == getDesktop());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(File paramFile) {
/* 1080 */     if (!(paramFile instanceof Win32ShellFolder2)) {
/* 1081 */       if (isFileSystem() && !isSpecial()) {
/* 1082 */         return super.compareTo(paramFile);
/*      */       }
/* 1084 */       return -1;
/*      */     } 
/*      */     
/* 1087 */     return Win32ShellFolderManager2.compareShellFolders(this, (Win32ShellFolder2)paramFile);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShellFolderColumnInfo[] getFolderColumns() {
/* 1096 */     return invoke((Callable)new Callable<ShellFolderColumnInfo[]>() {
/*      */           public ShellFolderColumnInfo[] call() {
/* 1098 */             ShellFolderColumnInfo[] arrayOfShellFolderColumnInfo = Win32ShellFolder2.this.doGetColumnInfo(Win32ShellFolder2.this.getIShellFolder());
/*      */             
/* 1100 */             if (arrayOfShellFolderColumnInfo != null) {
/* 1101 */               ArrayList<ShellFolderColumnInfo> arrayList = new ArrayList();
/*      */               
/* 1103 */               for (byte b = 0; b < arrayOfShellFolderColumnInfo.length; b++) {
/* 1104 */                 ShellFolderColumnInfo shellFolderColumnInfo = arrayOfShellFolderColumnInfo[b];
/* 1105 */                 if (shellFolderColumnInfo != null) {
/* 1106 */                   shellFolderColumnInfo.setAlignment(Integer.valueOf((shellFolderColumnInfo.getAlignment().intValue() == 1) ? 4 : (
/*      */                         
/* 1108 */                         (shellFolderColumnInfo.getAlignment().intValue() == 2) ? 0 : 10)));
/*      */ 
/*      */ 
/*      */                   
/* 1112 */                   shellFolderColumnInfo.setComparator(new Win32ShellFolder2.ColumnComparator(Win32ShellFolder2.this, b));
/*      */                   
/* 1114 */                   arrayList.add(shellFolderColumnInfo);
/*      */                 } 
/*      */               } 
/* 1117 */               arrayOfShellFolderColumnInfo = new ShellFolderColumnInfo[arrayList.size()];
/* 1118 */               arrayList.toArray(arrayOfShellFolderColumnInfo);
/*      */             } 
/* 1120 */             return arrayOfShellFolderColumnInfo;
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   public Object getFolderColumnValue(final int column) {
/* 1126 */     return invoke(new Callable() {
/*      */           public Object call() {
/* 1128 */             return Win32ShellFolder2.this.doGetColumnValue(Win32ShellFolder2.this.getParentIShellFolder(), Win32ShellFolder2.this.getRelativePIDL(), column);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sortChildren(final List<? extends File> files) {
/* 1146 */     invoke(new Callable<Void>() {
/*      */           public Void call() {
/* 1148 */             Collections.sort(files, new Win32ShellFolder2.ColumnComparator(Win32ShellFolder2.this, 0));
/*      */             
/* 1150 */             return null;
/*      */           }
/*      */         });
/*      */   } private static native void initIDs(); private native void initDesktop(); private native void initSpecial(long paramLong, int paramInt); static native long getNextPIDLEntry(long paramLong); static native long copyFirstPIDLEntry(long paramLong); private static native long combinePIDLs(long paramLong1, long paramLong2); static native void releasePIDL(long paramLong); private static native void releaseIShellFolder(long paramLong); private static native int compareIDs(long paramLong1, long paramLong2, long paramLong3); private static native int getAttributes0(long paramLong1, long paramLong2, int paramInt); private static native String getFileSystemPath0(int paramInt) throws IOException; private native long getEnumObjects(long paramLong, boolean paramBoolean1, boolean paramBoolean2); private native long getNextChild(long paramLong); private native void releaseEnumObjects(long paramLong); private static native long bindToObject(long paramLong1, long paramLong2); private static native long getLinkLocation(long paramLong1, long paramLong2, boolean paramBoolean); private static native long parseDisplayName0(long paramLong, String paramString) throws IOException; private static native String getDisplayNameOf(long paramLong1, long paramLong2, int paramInt); private static native String getFolderType(long paramLong); private native String getExecutableType(String paramString); private static native long getIShellIcon(long paramLong); private static native int getIconIndex(long paramLong1, long paramLong2); private static native long getIcon(String paramString, boolean paramBoolean); private static native long extractIcon(long paramLong1, long paramLong2, boolean paramBoolean); private static native long getSystemIcon(int paramInt); private static native long getIconResource(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean); private static native int[] getIconBits(long paramLong, int paramInt);
/*      */   private static native void disposeIcon(long paramLong);
/*      */   static native int[] getStandardViewButton0(int paramInt);
/*      */   private native ShellFolderColumnInfo[] doGetColumnInfo(long paramLong);
/*      */   private native Object doGetColumnValue(long paramLong1, long paramLong2, int paramInt);
/*      */   private static native int compareIDsByColumn(long paramLong1, long paramLong2, long paramLong3, int paramInt);
/*      */   private static class ColumnComparator implements Comparator<File> { private final Win32ShellFolder2 shellFolder;
/*      */     public ColumnComparator(Win32ShellFolder2 param1Win32ShellFolder2, int param1Int) {
/* 1161 */       this.shellFolder = param1Win32ShellFolder2;
/* 1162 */       this.columnIdx = param1Int;
/*      */     }
/*      */     private final int columnIdx;
/*      */     
/*      */     public int compare(final File o, final File o1) {
/* 1167 */       Integer integer = ShellFolder.<Integer>invoke(new Callable<Integer>() {
/*      */             public Integer call() {
/* 1169 */               if (o instanceof Win32ShellFolder2 && o1 instanceof Win32ShellFolder2)
/*      */               {
/*      */                 
/* 1172 */                 return Integer.valueOf(Win32ShellFolder2.compareIDsByColumn(Win32ShellFolder2.ColumnComparator.this.shellFolder.getIShellFolder(), ((Win32ShellFolder2)o)
/* 1173 */                       .getRelativePIDL(), ((Win32ShellFolder2)o1)
/* 1174 */                       .getRelativePIDL(), Win32ShellFolder2.ColumnComparator.this
/* 1175 */                       .columnIdx));
/*      */               }
/* 1177 */               return Integer.valueOf(0);
/*      */             }
/*      */           });
/*      */       
/* 1181 */       return (integer == null) ? 0 : integer.intValue();
/*      */     } }
/*      */ 
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\shell\Win32ShellFolder2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */