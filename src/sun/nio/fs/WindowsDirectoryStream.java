/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.DirectoryIteratorException;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.NotDirectoryException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WindowsDirectoryStream
/*     */   implements DirectoryStream<Path>
/*     */ {
/*     */   private final WindowsPath dir;
/*     */   private final DirectoryStream.Filter<? super Path> filter;
/*     */   private final long handle;
/*     */   private final String firstName;
/*     */   private final NativeBuffer findDataBuffer;
/*  55 */   private final Object closeLock = new Object();
/*     */ 
/*     */   
/*     */   private boolean isOpen = true;
/*     */ 
/*     */   
/*     */   private Iterator<Path> iterator;
/*     */ 
/*     */   
/*     */   WindowsDirectoryStream(WindowsPath paramWindowsPath, DirectoryStream.Filter<? super Path> paramFilter) throws IOException {
/*  65 */     this.dir = paramWindowsPath;
/*  66 */     this.filter = paramFilter;
/*     */ 
/*     */     
/*     */     try {
/*  70 */       String str = paramWindowsPath.getPathForWin32Calls();
/*  71 */       char c = str.charAt(str.length() - 1);
/*  72 */       if (c == ':' || c == '\\') {
/*  73 */         str = str + "*";
/*     */       } else {
/*  75 */         str = str + "\\*";
/*     */       } 
/*     */       
/*  78 */       WindowsNativeDispatcher.FirstFile firstFile = WindowsNativeDispatcher.FindFirstFile(str);
/*  79 */       this.handle = firstFile.handle();
/*  80 */       this.firstName = firstFile.name();
/*  81 */       this.findDataBuffer = WindowsFileAttributes.getBufferForFindData();
/*  82 */     } catch (WindowsException windowsException) {
/*  83 */       if (windowsException.lastError() == 267) {
/*  84 */         throw new NotDirectoryException(paramWindowsPath.getPathForExceptionMessage());
/*     */       }
/*  86 */       windowsException.rethrowAsIOException(paramWindowsPath);
/*     */ 
/*     */       
/*  89 */       throw new AssertionError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  97 */     synchronized (this.closeLock) {
/*  98 */       if (!this.isOpen)
/*     */         return; 
/* 100 */       this.isOpen = false;
/*     */     } 
/* 102 */     this.findDataBuffer.release();
/*     */     try {
/* 104 */       WindowsNativeDispatcher.FindClose(this.handle);
/* 105 */     } catch (WindowsException windowsException) {
/* 106 */       windowsException.rethrowAsIOException(this.dir);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Path> iterator() {
/* 112 */     if (!this.isOpen) {
/* 113 */       throw new IllegalStateException("Directory stream is closed");
/*     */     }
/* 115 */     synchronized (this) {
/* 116 */       if (this.iterator != null)
/* 117 */         throw new IllegalStateException("Iterator already obtained"); 
/* 118 */       this.iterator = new WindowsDirectoryIterator(this.firstName);
/* 119 */       return this.iterator;
/*     */     } 
/*     */   }
/*     */   
/*     */   private class WindowsDirectoryIterator implements Iterator<Path> {
/*     */     private boolean atEof;
/*     */     private String first;
/*     */     private Path nextEntry;
/*     */     private String prefix;
/*     */     
/*     */     WindowsDirectoryIterator(String param1String) {
/* 130 */       this.atEof = false;
/* 131 */       this.first = param1String;
/* 132 */       if (WindowsDirectoryStream.this.dir.needsSlashWhenResolving()) {
/* 133 */         this.prefix = WindowsDirectoryStream.this.dir.toString() + "\\";
/*     */       } else {
/* 135 */         this.prefix = WindowsDirectoryStream.this.dir.toString();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean isSelfOrParent(String param1String) {
/* 141 */       return (param1String.equals(".") || param1String.equals(".."));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Path acceptEntry(String param1String, BasicFileAttributes param1BasicFileAttributes) {
/* 147 */       WindowsPath windowsPath = WindowsPath.createFromNormalizedPath(WindowsDirectoryStream.this.dir.getFileSystem(), this.prefix + param1String, param1BasicFileAttributes);
/*     */       try {
/* 149 */         if (WindowsDirectoryStream.this.filter.accept(windowsPath))
/* 150 */           return windowsPath; 
/* 151 */       } catch (IOException iOException) {
/* 152 */         throw new DirectoryIteratorException(iOException);
/*     */       } 
/* 154 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Path readNextEntry() {
/* 160 */       if (this.first != null) {
/* 161 */         this.nextEntry = isSelfOrParent(this.first) ? null : acceptEntry(this.first, null);
/* 162 */         this.first = null;
/* 163 */         if (this.nextEntry != null)
/* 164 */           return this.nextEntry; 
/*     */       } 
/*     */       while (true) {
/*     */         WindowsFileAttributes windowsFileAttributes;
/* 168 */         String str = null;
/*     */ 
/*     */ 
/*     */         
/* 172 */         synchronized (WindowsDirectoryStream.this.closeLock) {
/*     */           try {
/* 174 */             if (WindowsDirectoryStream.this.isOpen) {
/* 175 */               str = WindowsNativeDispatcher.FindNextFile(WindowsDirectoryStream.this.handle, WindowsDirectoryStream.this.findDataBuffer.address());
/*     */             }
/* 177 */           } catch (WindowsException windowsException) {
/* 178 */             IOException iOException = windowsException.asIOException(WindowsDirectoryStream.this.dir);
/* 179 */             throw new DirectoryIteratorException(iOException);
/*     */           } 
/*     */ 
/*     */           
/* 183 */           if (str == null) {
/* 184 */             this.atEof = true;
/* 185 */             return null;
/*     */           } 
/*     */ 
/*     */           
/* 189 */           if (isSelfOrParent(str)) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 196 */           windowsFileAttributes = WindowsFileAttributes.fromFindData(WindowsDirectoryStream.this.findDataBuffer.address());
/*     */         } 
/*     */ 
/*     */         
/* 200 */         Path path = acceptEntry(str, windowsFileAttributes);
/* 201 */         if (path != null) {
/* 202 */           return path;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public synchronized boolean hasNext() {
/* 208 */       if (this.nextEntry == null && !this.atEof)
/* 209 */         this.nextEntry = readNextEntry(); 
/* 210 */       return (this.nextEntry != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized Path next() {
/* 215 */       Path path = null;
/* 216 */       if (this.nextEntry == null && !this.atEof) {
/* 217 */         path = readNextEntry();
/*     */       } else {
/* 219 */         path = this.nextEntry;
/* 220 */         this.nextEntry = null;
/*     */       } 
/* 222 */       if (path == null)
/* 223 */         throw new NoSuchElementException(); 
/* 224 */       return path;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 229 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsDirectoryStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */