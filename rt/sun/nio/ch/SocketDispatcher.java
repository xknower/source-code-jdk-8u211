/*    */ package sun.nio.ch;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class SocketDispatcher
/*    */   extends NativeDispatcher
/*    */ {
/*    */   static {
/* 39 */     IOUtil.load();
/*    */   }
/*    */   
/*    */   int read(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/* 43 */     return read0(paramFileDescriptor, paramLong, paramInt);
/*    */   }
/*    */   
/*    */   long readv(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/* 47 */     return readv0(paramFileDescriptor, paramLong, paramInt);
/*    */   }
/*    */   
/*    */   int write(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/* 51 */     return write0(paramFileDescriptor, paramLong, paramInt);
/*    */   }
/*    */   
/*    */   long writev(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/* 55 */     return writev0(paramFileDescriptor, paramLong, paramInt);
/*    */   }
/*    */   
/*    */   void preClose(FileDescriptor paramFileDescriptor) throws IOException {
/* 59 */     preClose0(paramFileDescriptor);
/*    */   }
/*    */   
/*    */   void close(FileDescriptor paramFileDescriptor) throws IOException {
/* 63 */     close0(paramFileDescriptor);
/*    */   }
/*    */   
/*    */   static native int read0(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException;
/*    */   
/*    */   static native long readv0(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException;
/*    */   
/*    */   static native int write0(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException;
/*    */   
/*    */   static native long writev0(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException;
/*    */   
/*    */   static native void preClose0(FileDescriptor paramFileDescriptor) throws IOException;
/*    */   
/*    */   static native void close0(FileDescriptor paramFileDescriptor) throws IOException;
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\SocketDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */