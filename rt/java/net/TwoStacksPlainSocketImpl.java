/*     */ package java.net;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import sun.net.ResourceManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TwoStacksPlainSocketImpl
/*     */   extends AbstractPlainSocketImpl
/*     */ {
/*     */   private FileDescriptor fd1;
/*  62 */   private InetAddress anyLocalBoundAddr = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private int lastfd = -1;
/*     */ 
/*     */   
/*     */   private final boolean exclusiveBind;
/*     */   
/*     */   private boolean isReuseAddress;
/*     */ 
/*     */   
/*     */   static {
/*  76 */     initProto();
/*     */   }
/*     */   
/*     */   public TwoStacksPlainSocketImpl(boolean paramBoolean) {
/*  80 */     this.exclusiveBind = paramBoolean;
/*     */   }
/*     */   
/*     */   public TwoStacksPlainSocketImpl(FileDescriptor paramFileDescriptor, boolean paramBoolean) {
/*  84 */     this.fd = paramFileDescriptor;
/*  85 */     this.exclusiveBind = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void create(boolean paramBoolean) throws IOException {
/*  93 */     this.fd1 = new FileDescriptor();
/*     */     try {
/*  95 */       super.create(paramBoolean);
/*  96 */     } catch (IOException iOException) {
/*  97 */       this.fd1 = null;
/*  98 */       throw iOException;
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
/*     */   protected synchronized void bind(InetAddress paramInetAddress, int paramInt) throws IOException {
/* 110 */     super.bind(paramInetAddress, paramInt);
/* 111 */     if (paramInetAddress.isAnyLocalAddress()) {
/* 112 */       this.anyLocalBoundAddr = paramInetAddress;
/*     */     }
/*     */   }
/*     */   
/*     */   public Object getOption(int paramInt) throws SocketException {
/* 117 */     if (isClosedOrPending()) {
/* 118 */       throw new SocketException("Socket Closed");
/*     */     }
/* 120 */     if (paramInt == 15) {
/* 121 */       if (this.fd != null && this.fd1 != null)
/*     */       {
/* 123 */         return this.anyLocalBoundAddr;
/*     */       }
/* 125 */       InetAddressContainer inetAddressContainer = new InetAddressContainer();
/* 126 */       socketGetOption(paramInt, inetAddressContainer);
/* 127 */       return inetAddressContainer.addr;
/* 128 */     }  if (paramInt == 4 && this.exclusiveBind)
/*     */     {
/* 130 */       return Boolean.valueOf(this.isReuseAddress);
/*     */     }
/* 132 */     return super.getOption(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   void socketBind(InetAddress paramInetAddress, int paramInt) throws IOException {
/* 137 */     socketBind(paramInetAddress, paramInt, this.exclusiveBind);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void socketSetOption(int paramInt, boolean paramBoolean, Object paramObject) throws SocketException {
/* 145 */     if (paramInt == 4 && this.exclusiveBind) {
/* 146 */       this.isReuseAddress = paramBoolean;
/*     */     } else {
/* 148 */       socketNativeSetOption(paramInt, paramBoolean, paramObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void close() throws IOException {
/* 156 */     synchronized (this.fdLock) {
/* 157 */       if (this.fd != null || this.fd1 != null) {
/* 158 */         if (!this.stream) {
/* 159 */           ResourceManager.afterUdpClose();
/*     */         }
/* 161 */         if (this.fdUseCount == 0) {
/* 162 */           if (this.closePending) {
/*     */             return;
/*     */           }
/* 165 */           this.closePending = true;
/* 166 */           socketClose();
/* 167 */           this.fd = null;
/* 168 */           this.fd1 = null;
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 177 */         if (!this.closePending) {
/* 178 */           this.closePending = true;
/* 179 */           this.fdUseCount--;
/* 180 */           socketClose();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void reset() throws IOException {
/* 189 */     if (this.fd != null || this.fd1 != null) {
/* 190 */       socketClose();
/*     */     }
/* 192 */     this.fd = null;
/* 193 */     this.fd1 = null;
/* 194 */     super.reset();
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
/*     */   public boolean isClosedOrPending() {
/* 206 */     synchronized (this.fdLock) {
/* 207 */       if (this.closePending || (this.fd == null && this.fd1 == null)) {
/* 208 */         return true;
/*     */       }
/* 210 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   static native void initProto();
/*     */   
/*     */   native void socketCreate(boolean paramBoolean) throws IOException;
/*     */   
/*     */   native void socketConnect(InetAddress paramInetAddress, int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   native void socketBind(InetAddress paramInetAddress, int paramInt, boolean paramBoolean) throws IOException;
/*     */   
/*     */   native void socketListen(int paramInt) throws IOException;
/*     */   
/*     */   native void socketAccept(SocketImpl paramSocketImpl) throws IOException;
/*     */   
/*     */   native int socketAvailable() throws IOException;
/*     */   
/*     */   native void socketClose0(boolean paramBoolean) throws IOException;
/*     */   
/*     */   native void socketShutdown(int paramInt) throws IOException;
/*     */   
/*     */   native void socketNativeSetOption(int paramInt, boolean paramBoolean, Object paramObject) throws SocketException;
/*     */   
/*     */   native int socketGetOption(int paramInt, Object paramObject) throws SocketException;
/*     */   
/*     */   native void socketSendUrgentData(int paramInt) throws IOException;
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\net\TwoStacksPlainSocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */