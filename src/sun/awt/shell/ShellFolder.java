/*     */ package sun.awt.shell;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import java.util.concurrent.Callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ShellFolder
/*     */   extends File
/*     */ {
/*     */   private static final String COLUMN_NAME = "FileChooser.fileNameHeaderText";
/*     */   private static final String COLUMN_SIZE = "FileChooser.fileSizeHeaderText";
/*     */   private static final String COLUMN_DATE = "FileChooser.fileDateHeaderText";
/*     */   protected ShellFolder parent;
/*     */   private static final ShellFolderManager shellFolderManager;
/*     */   
/*     */   ShellFolder(ShellFolder paramShellFolder, String paramString) {
/*  52 */     super((paramString != null) ? paramString : "ShellFolder");
/*  53 */     this.parent = paramShellFolder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFileSystem() {
/*  60 */     return !getPath().startsWith("ShellFolder");
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
/*     */   public String getParent() {
/*  88 */     if (this.parent == null && isFileSystem()) {
/*  89 */       return super.getParent();
/*     */     }
/*  91 */     if (this.parent != null) {
/*  92 */       return this.parent.getPath();
/*     */     }
/*  94 */     return null;
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
/*     */   public File getParentFile() {
/* 111 */     if (this.parent != null)
/* 112 */       return this.parent; 
/* 113 */     if (isFileSystem()) {
/* 114 */       return super.getParentFile();
/*     */     }
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public File[] listFiles() {
/* 121 */     return listFiles(true);
/*     */   }
/*     */   
/*     */   public File[] listFiles(boolean paramBoolean) {
/* 125 */     File[] arrayOfFile = super.listFiles();
/*     */     
/* 127 */     if (!paramBoolean) {
/* 128 */       Vector<File> vector = new Vector();
/* 129 */       byte b1 = (arrayOfFile == null) ? 0 : arrayOfFile.length;
/* 130 */       for (byte b2 = 0; b2 < b1; b2++) {
/* 131 */         if (!arrayOfFile[b2].isHidden()) {
/* 132 */           vector.addElement(arrayOfFile[b2]);
/*     */         }
/*     */       } 
/* 135 */       arrayOfFile = vector.<File>toArray(new File[vector.size()]);
/*     */     } 
/*     */     
/* 138 */     return arrayOfFile;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(File paramFile) {
/* 174 */     if (paramFile == null || !(paramFile instanceof ShellFolder) || (paramFile instanceof ShellFolder && ((ShellFolder)paramFile)
/* 175 */       .isFileSystem())) {
/*     */       
/* 177 */       if (isFileSystem()) {
/* 178 */         return super.compareTo(paramFile);
/*     */       }
/* 180 */       return -1;
/*     */     } 
/*     */     
/* 183 */     if (isFileSystem()) {
/* 184 */       return 1;
/*     */     }
/* 186 */     return getName().compareTo(paramFile.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getIcon(boolean paramBoolean) {
/* 196 */     return null;
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
/*     */   static {
/* 208 */     String str = (String)Toolkit.getDefaultToolkit().getDesktopProperty("Shell.shellFolderManager");
/* 209 */     Class<?> clazz = null;
/*     */     
/* 211 */     try { clazz = Class.forName(str, false, null);
/* 212 */       if (!ShellFolderManager.class.isAssignableFrom(clazz)) {
/* 213 */         clazz = null;
/*     */       } }
/*     */     
/* 216 */     catch (ClassNotFoundException classNotFoundException) {  }
/* 217 */     catch (NullPointerException nullPointerException) {  }
/* 218 */     catch (SecurityException securityException) {}
/*     */ 
/*     */     
/* 221 */     if (clazz == null) {
/* 222 */       clazz = ShellFolderManager.class;
/*     */     }
/*     */     
/*     */     try {
/* 226 */       shellFolderManager = (ShellFolderManager)clazz.newInstance();
/* 227 */     } catch (InstantiationException instantiationException) {
/* 228 */       throw new Error("Could not instantiate Shell Folder Manager: " + clazz
/* 229 */           .getName());
/* 230 */     } catch (IllegalAccessException illegalAccessException) {
/* 231 */       throw new Error("Could not access Shell Folder Manager: " + clazz
/* 232 */           .getName());
/*     */     } 
/*     */   }
/* 235 */   private static final Invoker invoker = shellFolderManager.createInvoker();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShellFolder getShellFolder(File paramFile) throws FileNotFoundException {
/* 243 */     if (paramFile instanceof ShellFolder) {
/* 244 */       return (ShellFolder)paramFile;
/*     */     }
/* 246 */     if (!paramFile.exists()) {
/* 247 */       throw new FileNotFoundException();
/*     */     }
/* 249 */     return shellFolderManager.createShellFolder(paramFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object get(String paramString) {
/* 258 */     return shellFolderManager.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isComputerNode(File paramFile) {
/* 266 */     return shellFolderManager.isComputerNode(paramFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFileSystemRoot(File paramFile) {
/* 273 */     return shellFolderManager.isFileSystemRoot(paramFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static File getNormalizedFile(File paramFile) throws IOException {
/* 281 */     File file = paramFile.getCanonicalFile();
/* 282 */     if (paramFile.equals(file))
/*     */     {
/* 284 */       return file;
/*     */     }
/*     */ 
/*     */     
/* 288 */     return new File(paramFile.toURI().normalize());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sort(final List<? extends File> files) {
/* 294 */     if (files == null || files.size() <= 1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 300 */     invoke(new Callable<Void>()
/*     */         {
/*     */           
/*     */           public Void call()
/*     */           {
/* 305 */             File file = null;
/*     */             
/* 307 */             for (File file1 : files) {
/* 308 */               File file2 = file1.getParentFile();
/*     */               
/* 310 */               if (file2 == null || !(file1 instanceof ShellFolder)) {
/* 311 */                 file = null;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */               
/* 316 */               if (file == null) {
/* 317 */                 file = file2; continue;
/*     */               } 
/* 319 */               if (file != file2 && !file.equals(file2)) {
/* 320 */                 file = null;
/*     */ 
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */             
/* 327 */             if (file instanceof ShellFolder) {
/* 328 */               ((ShellFolder)file).sortChildren(files);
/*     */             } else {
/* 330 */               Collections.sort(files, ShellFolder.FILE_COMPARATOR);
/*     */             } 
/*     */             
/* 333 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sortChildren(final List<? extends File> files) {
/* 341 */     invoke(new Callable<Void>() {
/*     */           public Void call() {
/* 343 */             Collections.sort(files, ShellFolder.FILE_COMPARATOR);
/*     */             
/* 345 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public boolean isAbsolute() {
/* 351 */     return (!isFileSystem() || super.isAbsolute());
/*     */   }
/*     */   
/*     */   public File getAbsoluteFile() {
/* 355 */     return isFileSystem() ? super.getAbsoluteFile() : this;
/*     */   }
/*     */   
/*     */   public boolean canRead() {
/* 359 */     return isFileSystem() ? super.canRead() : true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canWrite() {
/* 368 */     return isFileSystem() ? super.canWrite() : false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exists() {
/* 374 */     return (!isFileSystem() || isFileSystemRoot(this) || super.exists());
/*     */   }
/*     */   
/*     */   public boolean isDirectory() {
/* 378 */     return isFileSystem() ? super.isDirectory() : true;
/*     */   }
/*     */   
/*     */   public boolean isFile() {
/* 382 */     return isFileSystem() ? super.isFile() : (!isDirectory());
/*     */   }
/*     */   
/*     */   public long lastModified() {
/* 386 */     return isFileSystem() ? super.lastModified() : 0L;
/*     */   }
/*     */   
/*     */   public long length() {
/* 390 */     return isFileSystem() ? super.length() : 0L;
/*     */   }
/*     */   
/*     */   public boolean createNewFile() throws IOException {
/* 394 */     return isFileSystem() ? super.createNewFile() : false;
/*     */   }
/*     */   
/*     */   public boolean delete() {
/* 398 */     return isFileSystem() ? super.delete() : false;
/*     */   }
/*     */   
/*     */   public void deleteOnExit() {
/* 402 */     if (isFileSystem()) {
/* 403 */       super.deleteOnExit();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mkdir() {
/* 410 */     return isFileSystem() ? super.mkdir() : false;
/*     */   }
/*     */   
/*     */   public boolean mkdirs() {
/* 414 */     return isFileSystem() ? super.mkdirs() : false;
/*     */   }
/*     */   
/*     */   public boolean renameTo(File paramFile) {
/* 418 */     return isFileSystem() ? super.renameTo(paramFile) : false;
/*     */   }
/*     */   
/*     */   public boolean setLastModified(long paramLong) {
/* 422 */     return isFileSystem() ? super.setLastModified(paramLong) : false;
/*     */   }
/*     */   
/*     */   public boolean setReadOnly() {
/* 426 */     return isFileSystem() ? super.setReadOnly() : false;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 430 */     return isFileSystem() ? super.toString() : getDisplayName();
/*     */   }
/*     */   
/*     */   public static ShellFolderColumnInfo[] getFolderColumns(File paramFile) {
/* 434 */     ShellFolderColumnInfo[] arrayOfShellFolderColumnInfo = null;
/*     */     
/* 436 */     if (paramFile instanceof ShellFolder) {
/* 437 */       arrayOfShellFolderColumnInfo = ((ShellFolder)paramFile).getFolderColumns();
/*     */     }
/*     */     
/* 440 */     if (arrayOfShellFolderColumnInfo == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 449 */       arrayOfShellFolderColumnInfo = new ShellFolderColumnInfo[] { new ShellFolderColumnInfo("FileChooser.fileNameHeaderText", Integer.valueOf(150), Integer.valueOf(10), true, null, FILE_COMPARATOR), new ShellFolderColumnInfo("FileChooser.fileSizeHeaderText", Integer.valueOf(75), Integer.valueOf(4), true, null, DEFAULT_COMPARATOR, true), new ShellFolderColumnInfo("FileChooser.fileDateHeaderText", Integer.valueOf(130), Integer.valueOf(10), true, null, DEFAULT_COMPARATOR, true) };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 454 */     return arrayOfShellFolderColumnInfo;
/*     */   }
/*     */   
/*     */   public ShellFolderColumnInfo[] getFolderColumns() {
/* 458 */     return null;
/*     */   }
/*     */   public static Object getFolderColumnValue(File paramFile, int paramInt) {
/*     */     long l;
/* 462 */     if (paramFile instanceof ShellFolder) {
/* 463 */       Object object = ((ShellFolder)paramFile).getFolderColumnValue(paramInt);
/* 464 */       if (object != null) {
/* 465 */         return object;
/*     */       }
/*     */     } 
/*     */     
/* 469 */     if (paramFile == null || !paramFile.exists()) {
/* 470 */       return null;
/*     */     }
/*     */     
/* 473 */     switch (paramInt) {
/*     */       
/*     */       case 0:
/* 476 */         return paramFile;
/*     */       
/*     */       case 1:
/* 479 */         return paramFile.isDirectory() ? null : Long.valueOf(paramFile.length());
/*     */       
/*     */       case 2:
/* 482 */         if (isFileSystemRoot(paramFile)) {
/* 483 */           return null;
/*     */         }
/* 485 */         l = paramFile.lastModified();
/* 486 */         return (l == 0L) ? null : new Date(l);
/*     */     } 
/*     */     
/* 489 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getFolderColumnValue(int paramInt) {
/* 494 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T invoke(Callable<T> paramCallable) {
/*     */     try {
/* 504 */       return invoke(paramCallable, RuntimeException.class);
/* 505 */     } catch (InterruptedException interruptedException) {
/* 506 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T, E extends Throwable> T invoke(Callable<T> paramCallable, Class<E> paramClass) throws InterruptedException, E {
/*     */     try {
/* 518 */       return invoker.invoke(paramCallable);
/* 519 */     } catch (Exception exception) {
/* 520 */       if (exception instanceof RuntimeException)
/*     */       {
/* 522 */         throw (RuntimeException)exception;
/*     */       }
/*     */       
/* 525 */       if (exception instanceof InterruptedException) {
/*     */         
/* 527 */         Thread.currentThread().interrupt();
/*     */ 
/*     */         
/* 530 */         throw (InterruptedException)exception;
/*     */       } 
/*     */       
/* 533 */       if (paramClass.isInstance(exception)) {
/* 534 */         throw (Throwable)paramClass.cast(exception);
/*     */       }
/*     */       
/* 537 */       throw new RuntimeException("Unexpected error", exception);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 558 */   private static final Comparator DEFAULT_COMPARATOR = new Comparator()
/*     */     {
/*     */       public int compare(Object param1Object1, Object param1Object2) {
/*     */         boolean bool;
/* 562 */         if (param1Object1 == null && param1Object2 == null) {
/* 563 */           bool = false;
/* 564 */         } else if (param1Object1 != null && param1Object2 == null) {
/* 565 */           bool = true;
/* 566 */         } else if (param1Object1 == null && param1Object2 != null) {
/* 567 */           bool = true;
/* 568 */         } else if (param1Object1 instanceof Comparable) {
/* 569 */           bool = ((Comparable<Object>)param1Object1).compareTo(param1Object2);
/*     */         } else {
/* 571 */           bool = false;
/*     */         } 
/*     */         
/* 574 */         return bool;
/*     */       }
/*     */     };
/*     */   
/* 578 */   private static final Comparator<File> FILE_COMPARATOR = new Comparator<File>() {
/*     */       public int compare(File param1File1, File param1File2) {
/* 580 */         ShellFolder shellFolder1 = null;
/* 581 */         ShellFolder shellFolder2 = null;
/*     */         
/* 583 */         if (param1File1 instanceof ShellFolder) {
/* 584 */           shellFolder1 = (ShellFolder)param1File1;
/* 585 */           if (shellFolder1.isFileSystem()) {
/* 586 */             shellFolder1 = null;
/*     */           }
/*     */         } 
/* 589 */         if (param1File2 instanceof ShellFolder) {
/* 590 */           shellFolder2 = (ShellFolder)param1File2;
/* 591 */           if (shellFolder2.isFileSystem()) {
/* 592 */             shellFolder2 = null;
/*     */           }
/*     */         } 
/*     */         
/* 596 */         if (shellFolder1 != null && shellFolder2 != null)
/* 597 */           return shellFolder1.compareTo(shellFolder2); 
/* 598 */         if (shellFolder1 != null)
/*     */         {
/* 600 */           return -1; } 
/* 601 */         if (shellFolder2 != null) {
/* 602 */           return 1;
/*     */         }
/* 604 */         String str1 = param1File1.getName();
/* 605 */         String str2 = param1File2.getName();
/*     */ 
/*     */         
/* 608 */         int i = str1.compareToIgnoreCase(str2);
/* 609 */         if (i != 0) {
/* 610 */           return i;
/*     */         }
/*     */ 
/*     */         
/* 614 */         return str1.compareTo(str2);
/*     */       }
/*     */     };
/*     */   
/*     */   protected abstract Object writeReplace() throws ObjectStreamException;
/*     */   
/*     */   public abstract boolean isLink();
/*     */   
/*     */   public abstract ShellFolder getLinkLocation() throws FileNotFoundException;
/*     */   
/*     */   public abstract String getDisplayName();
/*     */   
/*     */   public abstract String getFolderType();
/*     */   
/*     */   public abstract String getExecutableType();
/*     */   
/*     */   public static interface Invoker {
/*     */     <T> T invoke(Callable<T> param1Callable) throws Exception;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\shell\ShellFolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */