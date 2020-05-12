/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.DatagramSocketImpl;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketOption;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.StandardSocketOptions;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.DatagramChannel;
/*     */ import java.nio.channels.IllegalBlockingModeException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatagramSocketAdaptor
/*     */   extends DatagramSocket
/*     */ {
/*     */   private final DatagramChannelImpl dc;
/*  49 */   private volatile int timeout = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DatagramSocketAdaptor(DatagramChannelImpl paramDatagramChannelImpl) throws IOException {
/*  57 */     super(dummyDatagramSocket);
/*  58 */     this.dc = paramDatagramChannelImpl;
/*     */   }
/*     */   
/*     */   public static DatagramSocket create(DatagramChannelImpl paramDatagramChannelImpl) {
/*     */     try {
/*  63 */       return new DatagramSocketAdaptor(paramDatagramChannelImpl);
/*  64 */     } catch (IOException iOException) {
/*  65 */       throw new Error(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void connectInternal(SocketAddress paramSocketAddress) throws SocketException {
/*  72 */     InetSocketAddress inetSocketAddress = Net.asInetSocketAddress(paramSocketAddress);
/*  73 */     int i = inetSocketAddress.getPort();
/*  74 */     if (i < 0 || i > 65535)
/*  75 */       throw new IllegalArgumentException("connect: " + i); 
/*  76 */     if (paramSocketAddress == null)
/*  77 */       throw new IllegalArgumentException("connect: null address"); 
/*  78 */     if (isClosed())
/*     */       return; 
/*     */     try {
/*  81 */       this.dc.connect(paramSocketAddress);
/*  82 */     } catch (Exception exception) {
/*  83 */       Net.translateToSocketException(exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void bind(SocketAddress paramSocketAddress) throws SocketException {
/*     */     try {
/*  89 */       if (paramSocketAddress == null)
/*  90 */         paramSocketAddress = new InetSocketAddress(0); 
/*  91 */       this.dc.bind(paramSocketAddress);
/*  92 */     } catch (Exception exception) {
/*  93 */       Net.translateToSocketException(exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void connect(InetAddress paramInetAddress, int paramInt) {
/*     */     try {
/*  99 */       connectInternal(new InetSocketAddress(paramInetAddress, paramInt));
/* 100 */     } catch (SocketException socketException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(SocketAddress paramSocketAddress) throws SocketException {
/* 106 */     if (paramSocketAddress == null)
/* 107 */       throw new IllegalArgumentException("Address can't be null"); 
/* 108 */     connectInternal(paramSocketAddress);
/*     */   }
/*     */   
/*     */   public void disconnect() {
/*     */     try {
/* 113 */       this.dc.disconnect();
/* 114 */     } catch (IOException iOException) {
/* 115 */       throw new Error(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isBound() {
/* 120 */     return (this.dc.localAddress() != null);
/*     */   }
/*     */   
/*     */   public boolean isConnected() {
/* 124 */     return (this.dc.remoteAddress() != null);
/*     */   }
/*     */   
/*     */   public InetAddress getInetAddress() {
/* 128 */     return isConnected() ? 
/* 129 */       Net.asInetSocketAddress(this.dc.remoteAddress()).getAddress() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 134 */     return isConnected() ? 
/* 135 */       Net.asInetSocketAddress(this.dc.remoteAddress()).getPort() : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void send(DatagramPacket paramDatagramPacket) throws IOException {
/* 140 */     synchronized (this.dc.blockingLock()) {
/* 141 */       if (!this.dc.isBlocking())
/* 142 */         throw new IllegalBlockingModeException(); 
/*     */       try {
/* 144 */         synchronized (paramDatagramPacket) {
/* 145 */           ByteBuffer byteBuffer = ByteBuffer.wrap(paramDatagramPacket.getData(), paramDatagramPacket
/* 146 */               .getOffset(), paramDatagramPacket
/* 147 */               .getLength());
/* 148 */           if (this.dc.isConnected()) {
/* 149 */             if (paramDatagramPacket.getAddress() == null) {
/*     */ 
/*     */ 
/*     */               
/* 153 */               InetSocketAddress inetSocketAddress = (InetSocketAddress)this.dc.remoteAddress();
/* 154 */               paramDatagramPacket.setPort(inetSocketAddress.getPort());
/* 155 */               paramDatagramPacket.setAddress(inetSocketAddress.getAddress());
/* 156 */               this.dc.write(byteBuffer);
/*     */             } else {
/*     */               
/* 159 */               this.dc.send(byteBuffer, paramDatagramPacket.getSocketAddress());
/*     */             } 
/*     */           } else {
/*     */             
/* 163 */             this.dc.send(byteBuffer, paramDatagramPacket.getSocketAddress());
/*     */           } 
/*     */         } 
/* 166 */       } catch (IOException iOException) {
/* 167 */         Net.translateException(iOException);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SocketAddress receive(ByteBuffer paramByteBuffer) throws IOException {
/* 175 */     if (this.timeout == 0) {
/* 176 */       return this.dc.receive(paramByteBuffer);
/*     */     }
/*     */     
/* 179 */     this.dc.configureBlocking(false);
/*     */     
/*     */     try {
/*     */       SocketAddress socketAddress;
/* 183 */       if ((socketAddress = this.dc.receive(paramByteBuffer)) != null)
/* 184 */         return socketAddress; 
/* 185 */       long l = this.timeout;
/*     */       while (true) {
/* 187 */         if (!this.dc.isOpen())
/* 188 */           throw new ClosedChannelException(); 
/* 189 */         long l1 = System.currentTimeMillis();
/* 190 */         int i = this.dc.poll(Net.POLLIN, l);
/* 191 */         if (i > 0 && (i & Net.POLLIN) != 0)
/*     */         {
/* 193 */           if ((socketAddress = this.dc.receive(paramByteBuffer)) != null)
/* 194 */             return socketAddress; 
/*     */         }
/* 196 */         l -= System.currentTimeMillis() - l1;
/* 197 */         if (l <= 0L) {
/* 198 */           throw new SocketTimeoutException();
/*     */         }
/*     */       } 
/*     */     } finally {
/* 202 */       if (this.dc.isOpen())
/* 203 */         this.dc.configureBlocking(true); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void receive(DatagramPacket paramDatagramPacket) throws IOException {
/* 208 */     synchronized (this.dc.blockingLock()) {
/* 209 */       if (!this.dc.isBlocking())
/* 210 */         throw new IllegalBlockingModeException(); 
/*     */       try {
/* 212 */         synchronized (paramDatagramPacket) {
/* 213 */           ByteBuffer byteBuffer = ByteBuffer.wrap(paramDatagramPacket.getData(), paramDatagramPacket
/* 214 */               .getOffset(), paramDatagramPacket
/* 215 */               .getLength());
/* 216 */           SocketAddress socketAddress = receive(byteBuffer);
/* 217 */           paramDatagramPacket.setSocketAddress(socketAddress);
/* 218 */           paramDatagramPacket.setLength(byteBuffer.position() - paramDatagramPacket.getOffset());
/*     */         } 
/* 220 */       } catch (IOException iOException) {
/* 221 */         Net.translateException(iOException);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public InetAddress getLocalAddress() {
/* 227 */     if (isClosed())
/* 228 */       return null; 
/* 229 */     SocketAddress socketAddress = this.dc.localAddress();
/* 230 */     if (socketAddress == null)
/* 231 */       socketAddress = new InetSocketAddress(0); 
/* 232 */     InetAddress inetAddress = ((InetSocketAddress)socketAddress).getAddress();
/* 233 */     SecurityManager securityManager = System.getSecurityManager();
/* 234 */     if (securityManager != null) {
/*     */       try {
/* 236 */         securityManager.checkConnect(inetAddress.getHostAddress(), -1);
/* 237 */       } catch (SecurityException securityException) {
/* 238 */         return (new InetSocketAddress(0)).getAddress();
/*     */       } 
/*     */     }
/* 241 */     return inetAddress;
/*     */   }
/*     */   
/*     */   public int getLocalPort() {
/* 245 */     if (isClosed())
/* 246 */       return -1; 
/*     */     try {
/* 248 */       SocketAddress socketAddress = this.dc.getLocalAddress();
/* 249 */       if (socketAddress != null) {
/* 250 */         return ((InetSocketAddress)socketAddress).getPort();
/*     */       }
/* 252 */     } catch (Exception exception) {}
/*     */     
/* 254 */     return 0;
/*     */   }
/*     */   
/*     */   public void setSoTimeout(int paramInt) throws SocketException {
/* 258 */     this.timeout = paramInt;
/*     */   }
/*     */   
/*     */   public int getSoTimeout() throws SocketException {
/* 262 */     return this.timeout;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setBooleanOption(SocketOption<Boolean> paramSocketOption, boolean paramBoolean) throws SocketException {
/*     */     try {
/* 269 */       this.dc.setOption(paramSocketOption, Boolean.valueOf(paramBoolean));
/* 270 */     } catch (IOException iOException) {
/* 271 */       Net.translateToSocketException(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setIntOption(SocketOption<Integer> paramSocketOption, int paramInt) throws SocketException {
/*     */     try {
/* 279 */       this.dc.setOption(paramSocketOption, Integer.valueOf(paramInt));
/* 280 */     } catch (IOException iOException) {
/* 281 */       Net.translateToSocketException(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean getBooleanOption(SocketOption<Boolean> paramSocketOption) throws SocketException {
/*     */     try {
/* 287 */       return ((Boolean)this.dc.<Boolean>getOption(paramSocketOption)).booleanValue();
/* 288 */     } catch (IOException iOException) {
/* 289 */       Net.translateToSocketException(iOException);
/* 290 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getIntOption(SocketOption<Integer> paramSocketOption) throws SocketException {
/*     */     try {
/* 296 */       return ((Integer)this.dc.<Integer>getOption(paramSocketOption)).intValue();
/* 297 */     } catch (IOException iOException) {
/* 298 */       Net.translateToSocketException(iOException);
/* 299 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSendBufferSize(int paramInt) throws SocketException {
/* 304 */     if (paramInt <= 0)
/* 305 */       throw new IllegalArgumentException("Invalid send size"); 
/* 306 */     setIntOption(StandardSocketOptions.SO_SNDBUF, paramInt);
/*     */   }
/*     */   
/*     */   public int getSendBufferSize() throws SocketException {
/* 310 */     return getIntOption(StandardSocketOptions.SO_SNDBUF);
/*     */   }
/*     */   
/*     */   public void setReceiveBufferSize(int paramInt) throws SocketException {
/* 314 */     if (paramInt <= 0)
/* 315 */       throw new IllegalArgumentException("Invalid receive size"); 
/* 316 */     setIntOption(StandardSocketOptions.SO_RCVBUF, paramInt);
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize() throws SocketException {
/* 320 */     return getIntOption(StandardSocketOptions.SO_RCVBUF);
/*     */   }
/*     */   
/*     */   public void setReuseAddress(boolean paramBoolean) throws SocketException {
/* 324 */     setBooleanOption(StandardSocketOptions.SO_REUSEADDR, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getReuseAddress() throws SocketException {
/* 328 */     return getBooleanOption(StandardSocketOptions.SO_REUSEADDR);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBroadcast(boolean paramBoolean) throws SocketException {
/* 333 */     setBooleanOption(StandardSocketOptions.SO_BROADCAST, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getBroadcast() throws SocketException {
/* 337 */     return getBooleanOption(StandardSocketOptions.SO_BROADCAST);
/*     */   }
/*     */   
/*     */   public void setTrafficClass(int paramInt) throws SocketException {
/* 341 */     setIntOption(StandardSocketOptions.IP_TOS, paramInt);
/*     */   }
/*     */   
/*     */   public int getTrafficClass() throws SocketException {
/* 345 */     return getIntOption(StandardSocketOptions.IP_TOS);
/*     */   }
/*     */   
/*     */   public void close() {
/*     */     try {
/* 350 */       this.dc.close();
/* 351 */     } catch (IOException iOException) {
/* 352 */       throw new Error(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/* 357 */     return !this.dc.isOpen();
/*     */   }
/*     */   
/*     */   public DatagramChannel getChannel() {
/* 361 */     return this.dc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 369 */   private static final DatagramSocketImpl dummyDatagramSocket = new DatagramSocketImpl()
/*     */     {
/*     */       protected void create() throws SocketException {}
/*     */       
/*     */       protected void bind(int param1Int, InetAddress param1InetAddress) throws SocketException {}
/*     */       
/*     */       protected void send(DatagramPacket param1DatagramPacket) throws IOException {}
/*     */       
/*     */       protected int peek(InetAddress param1InetAddress) throws IOException {
/* 378 */         return 0;
/*     */       } protected int peekData(DatagramPacket param1DatagramPacket) throws IOException {
/* 380 */         return 0;
/*     */       }
/*     */       protected void receive(DatagramPacket param1DatagramPacket) throws IOException {}
/*     */       @Deprecated
/*     */       protected void setTTL(byte param1Byte) throws IOException {}
/*     */       
/*     */       @Deprecated
/*     */       protected byte getTTL() throws IOException {
/* 388 */         return 0;
/*     */       }
/*     */       protected void setTimeToLive(int param1Int) throws IOException {}
/*     */       protected int getTimeToLive() throws IOException {
/* 392 */         return 0;
/*     */       }
/*     */       
/*     */       protected void join(InetAddress param1InetAddress) throws IOException {}
/*     */       
/*     */       protected void leave(InetAddress param1InetAddress) throws IOException {}
/*     */       
/*     */       protected void joinGroup(SocketAddress param1SocketAddress, NetworkInterface param1NetworkInterface) throws IOException {}
/*     */       
/*     */       protected void leaveGroup(SocketAddress param1SocketAddress, NetworkInterface param1NetworkInterface) throws IOException {}
/*     */       
/*     */       protected void close() {}
/*     */       
/*     */       public Object getOption(int param1Int) throws SocketException {
/* 406 */         return null;
/*     */       }
/*     */       
/*     */       public void setOption(int param1Int, Object param1Object) throws SocketException {}
/*     */     };
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\DatagramSocketAdaptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */