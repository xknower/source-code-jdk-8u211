/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DualStackPlainDatagramSocketImpl
/*     */   extends AbstractPlainDatagramSocketImpl
/*     */ {
/*  46 */   static JavaIOFileDescriptorAccess fdAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*     */   
/*     */   static {
/*  49 */     initIDs();
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
/*     */   DualStackPlainDatagramSocketImpl(boolean paramBoolean) {
/*  65 */     this.exclusiveBind = paramBoolean;
/*     */   }
/*     */   
/*     */   protected void datagramSocketCreate() throws SocketException {
/*  69 */     if (this.fd == null) {
/*  70 */       throw new SocketException("Socket closed");
/*     */     }
/*  72 */     int i = socketCreate(false);
/*     */     
/*  74 */     fdAccess.set(this.fd, i);
/*     */   }
/*     */ 
/*     */   
/*     */   protected synchronized void bind0(int paramInt, InetAddress paramInetAddress) throws SocketException {
/*  79 */     int i = checkAndReturnNativeFD();
/*     */     
/*  81 */     if (paramInetAddress == null) {
/*  82 */       throw new NullPointerException("argument address");
/*     */     }
/*  84 */     socketBind(i, paramInetAddress, paramInt, this.exclusiveBind);
/*  85 */     if (paramInt == 0) {
/*  86 */       this.localPort = socketLocalPort(i);
/*     */     } else {
/*  88 */       this.localPort = paramInt;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected synchronized int peek(InetAddress paramInetAddress) throws IOException {
/*  93 */     int i = checkAndReturnNativeFD();
/*     */     
/*  95 */     if (paramInetAddress == null) {
/*  96 */       throw new NullPointerException("Null address in peek()");
/*     */     }
/*     */     
/*  99 */     DatagramPacket datagramPacket = new DatagramPacket(new byte[1], 1);
/* 100 */     int j = peekData(datagramPacket);
/* 101 */     paramInetAddress = datagramPacket.getAddress();
/* 102 */     return j;
/*     */   }
/*     */   
/*     */   protected synchronized int peekData(DatagramPacket paramDatagramPacket) throws IOException {
/* 106 */     int i = checkAndReturnNativeFD();
/*     */     
/* 108 */     if (paramDatagramPacket == null)
/* 109 */       throw new NullPointerException("packet"); 
/* 110 */     if (paramDatagramPacket.getData() == null) {
/* 111 */       throw new NullPointerException("packet buffer");
/*     */     }
/* 113 */     return socketReceiveOrPeekData(i, paramDatagramPacket, this.timeout, this.connected, true);
/*     */   }
/*     */   
/*     */   protected synchronized void receive0(DatagramPacket paramDatagramPacket) throws IOException {
/* 117 */     int i = checkAndReturnNativeFD();
/*     */     
/* 119 */     if (paramDatagramPacket == null)
/* 120 */       throw new NullPointerException("packet"); 
/* 121 */     if (paramDatagramPacket.getData() == null) {
/* 122 */       throw new NullPointerException("packet buffer");
/*     */     }
/* 124 */     socketReceiveOrPeekData(i, paramDatagramPacket, this.timeout, this.connected, false);
/*     */   }
/*     */   
/*     */   protected void send(DatagramPacket paramDatagramPacket) throws IOException {
/* 128 */     int i = checkAndReturnNativeFD();
/*     */     
/* 130 */     if (paramDatagramPacket == null) {
/* 131 */       throw new NullPointerException("null packet");
/*     */     }
/* 133 */     if (paramDatagramPacket.getAddress() == null || paramDatagramPacket.getData() == null) {
/* 134 */       throw new NullPointerException("null address || null buffer");
/*     */     }
/* 136 */     socketSend(i, paramDatagramPacket.getData(), paramDatagramPacket.getOffset(), paramDatagramPacket.getLength(), paramDatagramPacket
/* 137 */         .getAddress(), paramDatagramPacket.getPort(), this.connected);
/*     */   }
/*     */   
/*     */   protected void connect0(InetAddress paramInetAddress, int paramInt) throws SocketException {
/* 141 */     int i = checkAndReturnNativeFD();
/*     */     
/* 143 */     if (paramInetAddress == null) {
/* 144 */       throw new NullPointerException("address");
/*     */     }
/* 146 */     socketConnect(i, paramInetAddress, paramInt);
/*     */   }
/*     */   
/*     */   protected void disconnect0(int paramInt) {
/* 150 */     if (this.fd == null || !this.fd.valid()) {
/*     */       return;
/*     */     }
/* 153 */     socketDisconnect(fdAccess.get(this.fd));
/*     */   }
/*     */   
/*     */   protected void datagramSocketClose() {
/* 157 */     if (this.fd == null || !this.fd.valid()) {
/*     */       return;
/*     */     }
/* 160 */     socketClose(fdAccess.get(this.fd));
/* 161 */     fdAccess.set(this.fd, -1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void socketSetOption(int paramInt, Object paramObject) throws SocketException {
/* 166 */     int i = checkAndReturnNativeFD();
/*     */     
/* 168 */     int j = 0;
/*     */     
/* 170 */     switch (paramInt) {
/*     */       case 3:
/*     */       case 4097:
/*     */       case 4098:
/* 174 */         j = ((Integer)paramObject).intValue();
/*     */         break;
/*     */       case 4:
/* 177 */         if (this.exclusiveBind && this.localPort != 0) {
/*     */           
/* 179 */           this.reuseAddressEmulated = true;
/* 180 */           this.isReuseAddress = ((Boolean)paramObject).booleanValue();
/*     */           return;
/*     */         } 
/*     */       
/*     */       case 32:
/* 185 */         j = ((Boolean)paramObject).booleanValue() ? 1 : 0;
/*     */         break;
/*     */       default:
/* 188 */         throw new SocketException("Option not supported");
/*     */     } 
/*     */     
/* 191 */     socketSetIntOption(i, paramInt, j);
/*     */   }
/*     */   
/*     */   protected Object socketGetOption(int paramInt) throws SocketException {
/* 195 */     int i = checkAndReturnNativeFD();
/*     */ 
/*     */     
/* 198 */     if (paramInt == 15) {
/* 199 */       return socketLocalAddress(i);
/*     */     }
/* 201 */     if (paramInt == 4 && this.reuseAddressEmulated) {
/* 202 */       return Boolean.valueOf(this.isReuseAddress);
/*     */     }
/* 204 */     int j = socketGetIntOption(i, paramInt);
/* 205 */     Boolean bool = null;
/*     */     
/* 207 */     switch (paramInt) {
/*     */       case 4:
/*     */       case 32:
/* 210 */         bool = (j == 0) ? Boolean.FALSE : Boolean.TRUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 221 */         return bool;
/*     */       case 3:
/*     */       case 4097:
/*     */       case 4098:
/*     */         return new Integer(j);
/*     */     } 
/*     */     throw new SocketException("Option not supported");
/*     */   }
/*     */   
/*     */   protected void join(InetAddress paramInetAddress, NetworkInterface paramNetworkInterface) throws IOException {
/* 231 */     throw new IOException("Method not implemented!");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void leave(InetAddress paramInetAddress, NetworkInterface paramNetworkInterface) throws IOException {
/* 236 */     throw new IOException("Method not implemented!");
/*     */   }
/*     */   
/*     */   protected void setTimeToLive(int paramInt) throws IOException {
/* 240 */     throw new IOException("Method not implemented!");
/*     */   }
/*     */   
/*     */   protected int getTimeToLive() throws IOException {
/* 244 */     throw new IOException("Method not implemented!");
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected void setTTL(byte paramByte) throws IOException {
/* 249 */     throw new IOException("Method not implemented!");
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected byte getTTL() throws IOException {
/* 254 */     throw new IOException("Method not implemented!");
/*     */   }
/*     */ 
/*     */   
/*     */   private int checkAndReturnNativeFD() throws SocketException {
/* 259 */     if (this.fd == null || !this.fd.valid()) {
/* 260 */       throw new SocketException("Socket closed");
/*     */     }
/* 262 */     return fdAccess.get(this.fd);
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private static native int socketCreate(boolean paramBoolean);
/*     */   
/*     */   private static native void socketBind(int paramInt1, InetAddress paramInetAddress, int paramInt2, boolean paramBoolean) throws SocketException;
/*     */   
/*     */   private static native void socketConnect(int paramInt1, InetAddress paramInetAddress, int paramInt2) throws SocketException;
/*     */   
/*     */   private static native void socketDisconnect(int paramInt);
/*     */   
/*     */   private static native void socketClose(int paramInt);
/*     */   
/*     */   private static native int socketLocalPort(int paramInt) throws SocketException;
/*     */   
/*     */   private static native Object socketLocalAddress(int paramInt) throws SocketException;
/*     */   
/*     */   private static native int socketReceiveOrPeekData(int paramInt1, DatagramPacket paramDatagramPacket, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) throws IOException;
/*     */   
/*     */   private static native void socketSend(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, InetAddress paramInetAddress, int paramInt4, boolean paramBoolean) throws IOException;
/*     */   
/*     */   private static native void socketSetIntOption(int paramInt1, int paramInt2, int paramInt3) throws SocketException;
/*     */   
/*     */   private static native int socketGetIntOption(int paramInt1, int paramInt2) throws SocketException;
/*     */   
/*     */   native int dataAvailable();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\net\DualStackPlainDatagramSocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */