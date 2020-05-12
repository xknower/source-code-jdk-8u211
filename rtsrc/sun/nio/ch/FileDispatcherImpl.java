/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
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
/*     */ 
/*     */ 
/*     */ class FileDispatcherImpl
/*     */   extends FileDispatcher
/*     */ {
/*     */   FileDispatcherImpl(boolean paramBoolean) {
/*  46 */     this.append = paramBoolean;
/*     */   }
/*     */   
/*     */   FileDispatcherImpl() {
/*  50 */     this(false);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean needsPositionLock() {
/*  55 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int read(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/*  61 */     return read0(paramFileDescriptor, paramLong, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int pread(FileDescriptor paramFileDescriptor, long paramLong1, int paramInt, long paramLong2) throws IOException {
/*  67 */     return pread0(paramFileDescriptor, paramLong1, paramInt, paramLong2);
/*     */   }
/*     */   
/*     */   long readv(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/*  71 */     return readv0(paramFileDescriptor, paramLong, paramInt);
/*     */   }
/*     */   
/*     */   int write(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/*  75 */     return write0(paramFileDescriptor, paramLong, paramInt, this.append);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int pwrite(FileDescriptor paramFileDescriptor, long paramLong1, int paramInt, long paramLong2) throws IOException {
/*  81 */     return pwrite0(paramFileDescriptor, paramLong1, paramInt, paramLong2);
/*     */   }
/*     */   
/*     */   long writev(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/*  85 */     return writev0(paramFileDescriptor, paramLong, paramInt, this.append);
/*     */   }
/*     */   
/*     */   long seek(FileDescriptor paramFileDescriptor, long paramLong) throws IOException {
/*  89 */     return seek0(paramFileDescriptor, paramLong);
/*     */   }
/*     */   
/*     */   int force(FileDescriptor paramFileDescriptor, boolean paramBoolean) throws IOException {
/*  93 */     return force0(paramFileDescriptor, paramBoolean);
/*     */   }
/*     */   
/*     */   int truncate(FileDescriptor paramFileDescriptor, long paramLong) throws IOException {
/*  97 */     return truncate0(paramFileDescriptor, paramLong);
/*     */   }
/*     */   
/*     */   long size(FileDescriptor paramFileDescriptor) throws IOException {
/* 101 */     return size0(paramFileDescriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int lock(FileDescriptor paramFileDescriptor, boolean paramBoolean1, long paramLong1, long paramLong2, boolean paramBoolean2) throws IOException {
/* 107 */     return lock0(paramFileDescriptor, paramBoolean1, paramLong1, paramLong2, paramBoolean2);
/*     */   }
/*     */   
/*     */   void release(FileDescriptor paramFileDescriptor, long paramLong1, long paramLong2) throws IOException {
/* 111 */     release0(paramFileDescriptor, paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */   void close(FileDescriptor paramFileDescriptor) throws IOException {
/* 115 */     close0(paramFileDescriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   FileDescriptor duplicateForMapping(FileDescriptor paramFileDescriptor) throws IOException {
/* 121 */     JavaIOFileDescriptorAccess javaIOFileDescriptorAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/* 122 */     FileDescriptor fileDescriptor = new FileDescriptor();
/* 123 */     long l = duplicateHandle(javaIOFileDescriptorAccess.getHandle(paramFileDescriptor));
/* 124 */     javaIOFileDescriptorAccess.setHandle(fileDescriptor, l);
/* 125 */     return fileDescriptor;
/*     */   }
/*     */   
/*     */   boolean canTransferToDirectly(SelectableChannel paramSelectableChannel) {
/* 129 */     return (fastFileTransfer && paramSelectableChannel.isBlocking());
/*     */   }
/*     */   
/*     */   boolean transferToDirectlyNeedsPositionLock() {
/* 133 */     return true;
/*     */   } static native int read0(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException; static native int pread0(FileDescriptor paramFileDescriptor, long paramLong1, int paramInt, long paramLong2) throws IOException; static native long readv0(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException; static native int write0(FileDescriptor paramFileDescriptor, long paramLong, int paramInt, boolean paramBoolean) throws IOException; static native int pwrite0(FileDescriptor paramFileDescriptor, long paramLong1, int paramInt, long paramLong2) throws IOException; static native long writev0(FileDescriptor paramFileDescriptor, long paramLong, int paramInt, boolean paramBoolean) throws IOException; static native long seek0(FileDescriptor paramFileDescriptor, long paramLong) throws IOException; static native int force0(FileDescriptor paramFileDescriptor, boolean paramBoolean) throws IOException;
/*     */   static boolean isFastFileTransferRequested() {
/*     */     boolean bool;
/* 137 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run()
/*     */           {
/* 141 */             return System.getProperty("jdk.nio.enableFastFileTransfer");
/*     */           }
/*     */         });
/*     */     
/* 145 */     if ("".equals(str)) {
/* 146 */       bool = true;
/*     */     } else {
/* 148 */       bool = Boolean.parseBoolean(str);
/*     */     } 
/* 150 */     return bool;
/*     */   } static native int truncate0(FileDescriptor paramFileDescriptor, long paramLong) throws IOException; static native long size0(FileDescriptor paramFileDescriptor) throws IOException; static native int lock0(FileDescriptor paramFileDescriptor, boolean paramBoolean1, long paramLong1, long paramLong2, boolean paramBoolean2) throws IOException; static native void release0(FileDescriptor paramFileDescriptor, long paramLong1, long paramLong2) throws IOException; static native void close0(FileDescriptor paramFileDescriptor) throws IOException; static native void closeByHandle(long paramLong) throws IOException;
/*     */   static native long duplicateHandle(long paramLong) throws IOException;
/*     */   static {
/* 154 */     IOUtil.load();
/* 155 */   } private static final boolean fastFileTransfer = isFastFileTransferRequested();
/*     */   private final boolean append;
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\FileDispatcherImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */