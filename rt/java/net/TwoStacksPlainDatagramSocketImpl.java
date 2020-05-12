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
/*     */ class TwoStacksPlainDatagramSocketImpl
/*     */   extends AbstractPlainDatagramSocketImpl
/*     */ {
/*     */   private FileDescriptor fd1;
/*  55 */   private InetAddress anyLocalBoundAddr = null;
/*     */   
/*  57 */   private int fduse = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private int lastfd = -1;
/*     */   
/*     */   static {
/*  66 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean exclusiveBind;
/*     */ 
/*     */   
/*     */   private boolean reuseAddressEmulated;
/*     */ 
/*     */   
/*     */   private boolean isReuseAddress;
/*     */ 
/*     */ 
/*     */   
/*     */   TwoStacksPlainDatagramSocketImpl(boolean paramBoolean) {
/*  82 */     this.exclusiveBind = paramBoolean;
/*     */   }
/*     */   
/*     */   protected synchronized void create() throws SocketException {
/*  86 */     this.fd1 = new FileDescriptor();
/*     */     try {
/*  88 */       super.create();
/*  89 */     } catch (SocketException socketException) {
/*  90 */       this.fd1 = null;
/*  91 */       throw socketException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected synchronized void bind(int paramInt, InetAddress paramInetAddress) throws SocketException {
/*  97 */     super.bind(paramInt, paramInetAddress);
/*  98 */     if (paramInetAddress.isAnyLocalAddress()) {
/*  99 */       this.anyLocalBoundAddr = paramInetAddress;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void bind0(int paramInt, InetAddress paramInetAddress) throws SocketException {
/* 107 */     bind0(paramInt, paramInetAddress, this.exclusiveBind);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void receive(DatagramPacket paramDatagramPacket) throws IOException {
/*     */     try {
/* 114 */       receive0(paramDatagramPacket);
/*     */     } finally {
/* 116 */       this.fduse = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getOption(int paramInt) throws SocketException {
/* 121 */     if (isClosed()) {
/* 122 */       throw new SocketException("Socket Closed");
/*     */     }
/*     */     
/* 125 */     if (paramInt == 15) {
/* 126 */       if (this.fd != null && this.fd1 != null && !this.connected) {
/* 127 */         return this.anyLocalBoundAddr;
/*     */       }
/* 129 */       boolean bool = (this.connectedAddress == null) ? true : this.connectedAddress.holder().getFamily();
/* 130 */       return socketLocalAddress(bool);
/* 131 */     }  if (paramInt == 4 && this.reuseAddressEmulated) {
/* 132 */       return Boolean.valueOf(this.isReuseAddress);
/*     */     }
/* 134 */     return super.getOption(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void socketSetOption(int paramInt, Object paramObject) throws SocketException {
/* 141 */     if (paramInt == 4 && this.exclusiveBind && this.localPort != 0) {
/*     */       
/* 143 */       this.reuseAddressEmulated = true;
/* 144 */       this.isReuseAddress = ((Boolean)paramObject).booleanValue();
/*     */     } else {
/* 146 */       socketNativeSetOption(paramInt, paramObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isClosed() {
/* 152 */     return (this.fd == null && this.fd1 == null);
/*     */   }
/*     */   
/*     */   protected void close() {
/* 156 */     if (this.fd != null || this.fd1 != null) {
/* 157 */       datagramSocketClose();
/* 158 */       ResourceManager.afterUdpClose();
/* 159 */       this.fd = null;
/* 160 */       this.fd1 = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected synchronized native void bind0(int paramInt, InetAddress paramInetAddress, boolean paramBoolean) throws SocketException;
/*     */   
/*     */   protected native void send(DatagramPacket paramDatagramPacket) throws IOException;
/*     */   
/*     */   protected synchronized native int peek(InetAddress paramInetAddress) throws IOException;
/*     */   
/*     */   protected synchronized native int peekData(DatagramPacket paramDatagramPacket) throws IOException;
/*     */   
/*     */   protected synchronized native void receive0(DatagramPacket paramDatagramPacket) throws IOException;
/*     */   
/*     */   protected native void setTimeToLive(int paramInt) throws IOException;
/*     */   
/*     */   protected native int getTimeToLive() throws IOException;
/*     */   
/*     */   @Deprecated
/*     */   protected native void setTTL(byte paramByte) throws IOException;
/*     */   
/*     */   @Deprecated
/*     */   protected native byte getTTL() throws IOException;
/*     */   
/*     */   protected native void join(InetAddress paramInetAddress, NetworkInterface paramNetworkInterface) throws IOException;
/*     */   
/*     */   protected native void leave(InetAddress paramInetAddress, NetworkInterface paramNetworkInterface) throws IOException;
/*     */   
/*     */   protected native void datagramSocketCreate() throws SocketException;
/*     */   
/*     */   protected native void datagramSocketClose();
/*     */   
/*     */   protected native void socketNativeSetOption(int paramInt, Object paramObject) throws SocketException;
/*     */   
/*     */   protected native Object socketGetOption(int paramInt) throws SocketException;
/*     */   
/*     */   protected native void connect0(InetAddress paramInetAddress, int paramInt) throws SocketException;
/*     */   
/*     */   protected native Object socketLocalAddress(int paramInt) throws SocketException;
/*     */   
/*     */   protected native void disconnect0(int paramInt);
/*     */   
/*     */   native int dataAvailable();
/*     */   
/*     */   private static native void init();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\net\TwoStacksPlainDatagramSocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */