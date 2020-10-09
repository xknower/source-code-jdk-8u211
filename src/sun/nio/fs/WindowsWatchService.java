/*     */ package sun.nio.fs;
/*     */ 
/*     */ import com.sun.nio.file.ExtendedWatchEventModifier;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.NotDirectoryException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardWatchEventKinds;
/*     */ import java.nio.file.WatchEvent;
/*     */ import java.nio.file.WatchKey;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WindowsWatchService
/*     */   extends AbstractWatchService
/*     */ {
/*     */   private static final int WAKEUP_COMPLETION_KEY = 0;
/*     */   private final Poller poller;
/*     */   private static final int ALL_FILE_NOTIFY_EVENTS = 351;
/*     */   
/*     */   WindowsWatchService(WindowsFileSystem paramWindowsFileSystem) throws IOException {
/*  61 */     long l = 0L;
/*     */     try {
/*  63 */       l = WindowsNativeDispatcher.CreateIoCompletionPort(-1L, 0L, 0L);
/*  64 */     } catch (WindowsException windowsException) {
/*  65 */       throw new IOException(windowsException.getMessage());
/*     */     } 
/*     */     
/*  68 */     this.poller = new Poller(paramWindowsFileSystem, this, l);
/*  69 */     this.poller.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WatchKey register(Path paramPath, WatchEvent.Kind<?>[] paramArrayOfKind, WatchEvent.Modifier... paramVarArgs) throws IOException {
/*  79 */     return this.poller.register(paramPath, paramArrayOfKind, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void implClose() throws IOException {
/*  85 */     this.poller.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WindowsWatchKey
/*     */     extends AbstractWatchKey
/*     */   {
/*     */     private final WindowsWatchService.FileKey fileKey;
/*     */ 
/*     */     
/*  96 */     private volatile long handle = -1L;
/*     */ 
/*     */ 
/*     */     
/*     */     private Set<? extends WatchEvent.Kind<?>> events;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean watchSubtree;
/*     */ 
/*     */     
/*     */     private NativeBuffer buffer;
/*     */ 
/*     */     
/*     */     private long countAddress;
/*     */ 
/*     */     
/*     */     private long overlappedAddress;
/*     */ 
/*     */     
/*     */     private int completionKey;
/*     */ 
/*     */     
/*     */     private boolean errorStartingOverlapped;
/*     */ 
/*     */ 
/*     */     
/*     */     WindowsWatchKey(Path param1Path, AbstractWatchService param1AbstractWatchService, WindowsWatchService.FileKey param1FileKey) {
/* 124 */       super(param1Path, param1AbstractWatchService);
/* 125 */       this.fileKey = param1FileKey;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     WindowsWatchKey init(long param1Long1, Set<? extends WatchEvent.Kind<?>> param1Set, boolean param1Boolean, NativeBuffer param1NativeBuffer, long param1Long2, long param1Long3, int param1Int) {
/* 136 */       this.handle = param1Long1;
/* 137 */       this.events = param1Set;
/* 138 */       this.watchSubtree = param1Boolean;
/* 139 */       this.buffer = param1NativeBuffer;
/* 140 */       this.countAddress = param1Long2;
/* 141 */       this.overlappedAddress = param1Long3;
/* 142 */       this.completionKey = param1Int;
/* 143 */       return this;
/*     */     }
/*     */     
/*     */     long handle() {
/* 147 */       return this.handle;
/*     */     }
/*     */     
/*     */     Set<? extends WatchEvent.Kind<?>> events() {
/* 151 */       return this.events;
/*     */     }
/*     */     
/*     */     void setEvents(Set<? extends WatchEvent.Kind<?>> param1Set) {
/* 155 */       this.events = param1Set;
/*     */     }
/*     */     
/*     */     boolean watchSubtree() {
/* 159 */       return this.watchSubtree;
/*     */     }
/*     */     
/*     */     NativeBuffer buffer() {
/* 163 */       return this.buffer;
/*     */     }
/*     */     
/*     */     long countAddress() {
/* 167 */       return this.countAddress;
/*     */     }
/*     */     
/*     */     long overlappedAddress() {
/* 171 */       return this.overlappedAddress;
/*     */     }
/*     */     
/*     */     WindowsWatchService.FileKey fileKey() {
/* 175 */       return this.fileKey;
/*     */     }
/*     */     
/*     */     int completionKey() {
/* 179 */       return this.completionKey;
/*     */     }
/*     */     
/*     */     void setErrorStartingOverlapped(boolean param1Boolean) {
/* 183 */       this.errorStartingOverlapped = param1Boolean;
/*     */     }
/*     */     
/*     */     boolean isErrorStartingOverlapped() {
/* 187 */       return this.errorStartingOverlapped;
/*     */     }
/*     */ 
/*     */     
/*     */     void invalidate() {
/* 192 */       ((WindowsWatchService)watcher()).poller.releaseResources(this);
/* 193 */       this.handle = -1L;
/* 194 */       this.buffer = null;
/* 195 */       this.countAddress = 0L;
/* 196 */       this.overlappedAddress = 0L;
/* 197 */       this.errorStartingOverlapped = false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isValid() {
/* 202 */       return (this.handle != -1L);
/*     */     }
/*     */ 
/*     */     
/*     */     public void cancel() {
/* 207 */       if (isValid())
/*     */       {
/* 209 */         ((WindowsWatchService)watcher()).poller.cancel(this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FileKey
/*     */   {
/*     */     private final int volSerialNumber;
/*     */     private final int fileIndexHigh;
/*     */     private final int fileIndexLow;
/*     */     
/*     */     FileKey(int param1Int1, int param1Int2, int param1Int3) {
/* 221 */       this.volSerialNumber = param1Int1;
/* 222 */       this.fileIndexHigh = param1Int2;
/* 223 */       this.fileIndexLow = param1Int3;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 228 */       return this.volSerialNumber ^ this.fileIndexHigh ^ this.fileIndexLow;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 233 */       if (param1Object == this)
/* 234 */         return true; 
/* 235 */       if (!(param1Object instanceof FileKey))
/* 236 */         return false; 
/* 237 */       FileKey fileKey = (FileKey)param1Object;
/* 238 */       if (this.volSerialNumber != fileKey.volSerialNumber) return false; 
/* 239 */       if (this.fileIndexHigh != fileKey.fileIndexHigh) return false; 
/* 240 */       return (this.fileIndexLow == fileKey.fileIndexLow);
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
/*     */   private static class Poller
/*     */     extends AbstractPoller
/*     */   {
/* 258 */     private static final Unsafe UNSAFE = Unsafe.getUnsafe();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final short SIZEOF_DWORD = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final short SIZEOF_OVERLAPPED = 32;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 274 */     private static final short OFFSETOF_HEVENT = (UNSAFE.addressSize() == 4) ? 16 : 24;
/*     */ 
/*     */     
/*     */     private static final short OFFSETOF_NEXTENTRYOFFSET = 0;
/*     */ 
/*     */     
/*     */     private static final short OFFSETOF_ACTION = 4;
/*     */ 
/*     */     
/*     */     private static final short OFFSETOF_FILENAMELENGTH = 8;
/*     */ 
/*     */     
/*     */     private static final short OFFSETOF_FILENAME = 12;
/*     */ 
/*     */     
/*     */     private static final int CHANGES_BUFFER_SIZE = 16384;
/*     */ 
/*     */     
/*     */     private final WindowsFileSystem fs;
/*     */ 
/*     */     
/*     */     private final WindowsWatchService watcher;
/*     */ 
/*     */     
/*     */     private final long port;
/*     */ 
/*     */     
/*     */     private final Map<Integer, WindowsWatchService.WindowsWatchKey> ck2key;
/*     */     
/*     */     private final Map<WindowsWatchService.FileKey, WindowsWatchService.WindowsWatchKey> fk2key;
/*     */     
/*     */     private int lastCompletionKey;
/*     */ 
/*     */     
/*     */     Poller(WindowsFileSystem param1WindowsFileSystem, WindowsWatchService param1WindowsWatchService, long param1Long) {
/* 309 */       this.fs = param1WindowsFileSystem;
/* 310 */       this.watcher = param1WindowsWatchService;
/* 311 */       this.port = param1Long;
/* 312 */       this.ck2key = new HashMap<>();
/* 313 */       this.fk2key = new HashMap<>();
/* 314 */       this.lastCompletionKey = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     void wakeup() throws IOException {
/*     */       try {
/* 320 */         WindowsNativeDispatcher.PostQueuedCompletionStatus(this.port, 0L);
/* 321 */       } catch (WindowsException windowsException) {
/* 322 */         throw new IOException(windowsException.getMessage());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object implRegister(Path param1Path, Set<? extends WatchEvent.Kind<?>> param1Set, WatchEvent.Modifier... param1VarArgs) {
/*     */       long l;
/* 340 */       WindowsPath windowsPath = (WindowsPath)param1Path;
/* 341 */       boolean bool = false;
/*     */ 
/*     */       
/* 344 */       for (WatchEvent.Modifier modifier : param1VarArgs) {
/* 345 */         if (modifier == ExtendedWatchEventModifier.FILE_TREE) {
/* 346 */           bool = true;
/*     */         } else {
/* 348 */           if (modifier == null)
/* 349 */             return new NullPointerException(); 
/* 350 */           if (!(modifier instanceof com.sun.nio.file.SensitivityWatchEventModifier))
/*     */           {
/* 352 */             return new UnsupportedOperationException("Modifier not supported");
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 359 */         l = WindowsNativeDispatcher.CreateFile(windowsPath.getPathForWin32Calls(), 1, 7, 3, 1107296256);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 364 */       catch (WindowsException windowsException) {
/* 365 */         return windowsException.asIOException(windowsPath);
/*     */       } 
/*     */       
/* 368 */       boolean bool1 = false;
/*     */       try {
/*     */         WindowsFileAttributes windowsFileAttributes;
/*     */         WindowsWatchService.WindowsWatchKey windowsWatchKey2;
/*     */         try {
/* 373 */           windowsFileAttributes = WindowsFileAttributes.readAttributes(l);
/* 374 */         } catch (WindowsException windowsException) {
/* 375 */           return windowsException.asIOException(windowsPath);
/*     */         } 
/* 377 */         if (!windowsFileAttributes.isDirectory()) {
/* 378 */           return new NotDirectoryException(windowsPath.getPathForExceptionMessage());
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 384 */         WindowsWatchService.FileKey fileKey = new WindowsWatchService.FileKey(windowsFileAttributes.volSerialNumber(), windowsFileAttributes.fileIndexHigh(), windowsFileAttributes.fileIndexLow());
/* 385 */         WindowsWatchService.WindowsWatchKey windowsWatchKey1 = this.fk2key.get(fileKey);
/*     */ 
/*     */ 
/*     */         
/* 389 */         if (windowsWatchKey1 != null && bool == windowsWatchKey1.watchSubtree()) {
/* 390 */           windowsWatchKey1.setEvents(param1Set);
/* 391 */           return windowsWatchKey1;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 396 */         int i = ++this.lastCompletionKey;
/* 397 */         if (i == 0) {
/* 398 */           i = ++this.lastCompletionKey;
/*     */         }
/*     */         
/*     */         try {
/* 402 */           WindowsNativeDispatcher.CreateIoCompletionPort(l, this.port, i);
/* 403 */         } catch (WindowsException windowsException) {
/* 404 */           return new IOException(windowsException.getMessage());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 409 */         char c = '䀤';
/* 410 */         NativeBuffer nativeBuffer = NativeBuffers.getNativeBuffer(c);
/*     */         
/* 412 */         long l1 = nativeBuffer.address();
/* 413 */         long l2 = l1 + c - 32L;
/* 414 */         long l3 = l2 - 4L;
/*     */ 
/*     */         
/* 417 */         UNSAFE.setMemory(l2, 32L, (byte)0);
/*     */ 
/*     */         
/*     */         try {
/* 421 */           createAndAttachEvent(l2);
/*     */           
/* 423 */           WindowsNativeDispatcher.ReadDirectoryChangesW(l, l1, 16384, bool, 351, l3, l2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 430 */         catch (WindowsException windowsException) {
/* 431 */           closeAttachedEvent(l2);
/* 432 */           nativeBuffer.release();
/* 433 */           return new IOException(windowsException.getMessage());
/*     */         } 
/*     */ 
/*     */         
/* 437 */         if (windowsWatchKey1 == null) {
/*     */ 
/*     */           
/* 440 */           windowsWatchKey2 = (new WindowsWatchService.WindowsWatchKey(windowsPath, this.watcher, fileKey)).init(l, param1Set, bool, nativeBuffer, l3, l2, i);
/*     */ 
/*     */           
/* 443 */           this.fk2key.put(fileKey, windowsWatchKey2);
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 449 */           this.ck2key.remove(Integer.valueOf(windowsWatchKey1.completionKey()));
/* 450 */           releaseResources(windowsWatchKey1);
/* 451 */           windowsWatchKey2 = windowsWatchKey1.init(l, param1Set, bool, nativeBuffer, l3, l2, i);
/*     */         } 
/*     */ 
/*     */         
/* 455 */         this.ck2key.put(Integer.valueOf(i), windowsWatchKey2);
/*     */         
/* 457 */         bool1 = true;
/* 458 */         return windowsWatchKey2;
/*     */       } finally {
/*     */         
/* 461 */         if (!bool1) WindowsNativeDispatcher.CloseHandle(l);
/*     */       
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void releaseResources(WindowsWatchService.WindowsWatchKey param1WindowsWatchKey) {
/* 471 */       if (!param1WindowsWatchKey.isErrorStartingOverlapped()) {
/*     */         try {
/* 473 */           WindowsNativeDispatcher.CancelIo(param1WindowsWatchKey.handle());
/* 474 */           WindowsNativeDispatcher.GetOverlappedResult(param1WindowsWatchKey.handle(), param1WindowsWatchKey.overlappedAddress());
/* 475 */         } catch (WindowsException windowsException) {}
/*     */       }
/*     */ 
/*     */       
/* 479 */       WindowsNativeDispatcher.CloseHandle(param1WindowsWatchKey.handle());
/* 480 */       closeAttachedEvent(param1WindowsWatchKey.overlappedAddress());
/* 481 */       param1WindowsWatchKey.buffer().cleaner().clean();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void createAndAttachEvent(long param1Long) throws WindowsException {
/* 489 */       long l = WindowsNativeDispatcher.CreateEvent(false, false);
/* 490 */       UNSAFE.putAddress(param1Long + OFFSETOF_HEVENT, l);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void closeAttachedEvent(long param1Long) {
/* 498 */       long l = UNSAFE.getAddress(param1Long + OFFSETOF_HEVENT);
/* 499 */       if (l != 0L && l != -1L) {
/* 500 */         WindowsNativeDispatcher.CloseHandle(l);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     void implCancelKey(WatchKey param1WatchKey) {
/* 506 */       WindowsWatchService.WindowsWatchKey windowsWatchKey = (WindowsWatchService.WindowsWatchKey)param1WatchKey;
/* 507 */       if (windowsWatchKey.isValid()) {
/* 508 */         this.fk2key.remove(windowsWatchKey.fileKey());
/* 509 */         this.ck2key.remove(Integer.valueOf(windowsWatchKey.completionKey()));
/* 510 */         windowsWatchKey.invalidate();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void implCloseAll() {
/* 518 */       this.ck2key.values().forEach(WindowsWatchService.WindowsWatchKey::invalidate);
/*     */       
/* 520 */       this.fk2key.clear();
/* 521 */       this.ck2key.clear();
/*     */ 
/*     */       
/* 524 */       WindowsNativeDispatcher.CloseHandle(this.port);
/*     */     }
/*     */ 
/*     */     
/*     */     private WatchEvent.Kind<?> translateActionToEvent(int param1Int) {
/* 529 */       switch (param1Int) {
/*     */         case 3:
/* 531 */           return StandardWatchEventKinds.ENTRY_MODIFY;
/*     */         
/*     */         case 1:
/*     */         case 5:
/* 535 */           return StandardWatchEventKinds.ENTRY_CREATE;
/*     */         
/*     */         case 2:
/*     */         case 4:
/* 539 */           return StandardWatchEventKinds.ENTRY_DELETE;
/*     */       } 
/*     */       
/* 542 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     private void processEvents(WindowsWatchService.WindowsWatchKey param1WindowsWatchKey, int param1Int) {
/*     */       int i;
/* 548 */       long l = param1WindowsWatchKey.buffer().address();
/*     */ 
/*     */       
/*     */       do {
/* 552 */         int j = UNSAFE.getInt(l + 4L);
/*     */ 
/*     */         
/* 555 */         WatchEvent.Kind<?> kind = translateActionToEvent(j);
/* 556 */         if (param1WindowsWatchKey.events().contains(kind)) {
/*     */           
/* 558 */           int k = UNSAFE.getInt(l + 8L);
/* 559 */           if (k % 2 != 0) {
/* 560 */             throw new AssertionError("FileNameLength is not a multiple of 2");
/*     */           }
/* 562 */           char[] arrayOfChar = new char[k / 2];
/* 563 */           UNSAFE.copyMemory(null, l + 12L, arrayOfChar, Unsafe.ARRAY_CHAR_BASE_OFFSET, k);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 568 */           WindowsPath windowsPath = WindowsPath.createFromNormalizedPath(this.fs, new String(arrayOfChar));
/* 569 */           param1WindowsWatchKey.signalEvent(kind, windowsPath);
/*     */         } 
/*     */ 
/*     */         
/* 573 */         i = UNSAFE.getInt(l + 0L);
/* 574 */         l += i;
/* 575 */       } while (i != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       while (true) {
/*     */         WindowsNativeDispatcher.CompletionStatus completionStatus;
/*     */         try {
/* 586 */           completionStatus = WindowsNativeDispatcher.GetQueuedCompletionStatus(this.port);
/* 587 */         } catch (WindowsException windowsException) {
/*     */           
/* 589 */           windowsException.printStackTrace();
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 594 */         if (completionStatus.completionKey() == 0L) {
/* 595 */           boolean bool1 = processRequests();
/* 596 */           if (bool1) {
/*     */             return;
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 603 */         WindowsWatchService.WindowsWatchKey windowsWatchKey = this.ck2key.get(Integer.valueOf((int)completionStatus.completionKey()));
/* 604 */         if (windowsWatchKey == null) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 611 */         boolean bool = false;
/* 612 */         int i = completionStatus.error();
/* 613 */         int j = completionStatus.bytesTransferred();
/* 614 */         if (i == 1022) {
/*     */           
/* 616 */           windowsWatchKey.signalEvent(StandardWatchEventKinds.OVERFLOW, null);
/* 617 */         } else if (i != 0 && i != 234) {
/*     */           
/* 619 */           bool = true;
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 625 */           if (j > 0) {
/*     */             
/* 627 */             processEvents(windowsWatchKey, j);
/* 628 */           } else if (i == 0) {
/*     */ 
/*     */             
/* 631 */             windowsWatchKey.signalEvent(StandardWatchEventKinds.OVERFLOW, null);
/*     */           } 
/*     */ 
/*     */           
/*     */           try {
/* 636 */             WindowsNativeDispatcher.ReadDirectoryChangesW(windowsWatchKey.handle(), windowsWatchKey
/* 637 */                 .buffer().address(), 16384, windowsWatchKey
/*     */                 
/* 639 */                 .watchSubtree(), 351, windowsWatchKey
/*     */                 
/* 641 */                 .countAddress(), windowsWatchKey
/* 642 */                 .overlappedAddress());
/* 643 */           } catch (WindowsException windowsException) {
/*     */             
/* 645 */             bool = true;
/* 646 */             windowsWatchKey.setErrorStartingOverlapped(true);
/*     */           } 
/*     */         } 
/* 649 */         if (bool) {
/* 650 */           implCancelKey(windowsWatchKey);
/* 651 */           windowsWatchKey.signal();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\WindowsWatchService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */