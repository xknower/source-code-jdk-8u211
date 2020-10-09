/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketImpl;
/*     */ import java.net.SocketOption;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.StandardSocketOptions;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.IllegalBlockingModeException;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SocketAdaptor
/*     */   extends Socket
/*     */ {
/*     */   private final SocketChannelImpl sc;
/*  58 */   private volatile int timeout = 0;
/*     */   private InputStream socketInputStream;
/*     */   
/*  61 */   private SocketAdaptor(SocketChannelImpl paramSocketChannelImpl) throws SocketException { super((SocketImpl)null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     this.socketInputStream = null; this.sc = paramSocketChannelImpl; }
/*     */   public static Socket create(SocketChannelImpl paramSocketChannelImpl) { try { return new SocketAdaptor(paramSocketChannelImpl); } catch (SocketException socketException) { throw new InternalError("Should not reach here"); }  }
/*     */   public SocketChannel getChannel() { return this.sc; }
/* 225 */   public void connect(SocketAddress paramSocketAddress) throws IOException { connect(paramSocketAddress, 0); } public void connect(SocketAddress paramSocketAddress, int paramInt) throws IOException { if (paramSocketAddress == null) throw new IllegalArgumentException("connect: The address can't be null");  if (paramInt < 0) throw new IllegalArgumentException("connect: timeout can't be negative");  synchronized (this.sc.blockingLock()) { if (!this.sc.isBlocking()) throw new IllegalBlockingModeException();  try { if (paramInt == 0) { this.sc.connect(paramSocketAddress); return; }  this.sc.configureBlocking(false); try { if (this.sc.connect(paramSocketAddress)) return;  long l = paramInt; while (true) { if (!this.sc.isOpen()) throw new ClosedChannelException();  long l1 = System.currentTimeMillis(); int i = this.sc.poll(Net.POLLCONN, l); if (i > 0 && this.sc.finishConnect()) break;  l -= System.currentTimeMillis() - l1; if (l <= 0L) { try { this.sc.close(); } catch (IOException iOException) {} throw new SocketTimeoutException(); }  }  } finally { if (this.sc.isOpen()) this.sc.configureBlocking(true);  }  } catch (Exception exception) { Net.translateException(exception, true); }  }  } public void bind(SocketAddress paramSocketAddress) throws IOException { try { this.sc.bind(paramSocketAddress); } catch (Exception exception) { Net.translateException(exception); }  } public InputStream getInputStream() throws IOException { if (!this.sc.isOpen())
/* 226 */       throw new SocketException("Socket is closed"); 
/* 227 */     if (!this.sc.isConnected())
/* 228 */       throw new SocketException("Socket is not connected"); 
/* 229 */     if (!this.sc.isInputOpen())
/* 230 */       throw new SocketException("Socket input is shutdown"); 
/* 231 */     if (this.socketInputStream == null) {
/*     */       try {
/* 233 */         this.socketInputStream = AccessController.<InputStream>doPrivileged(new PrivilegedExceptionAction<InputStream>()
/*     */             {
/*     */               public InputStream run() throws IOException {
/* 236 */                 return new SocketAdaptor.SocketInputStream();
/*     */               }
/*     */             });
/* 239 */       } catch (PrivilegedActionException privilegedActionException) {
/* 240 */         throw (IOException)privilegedActionException.getException();
/*     */       } 
/*     */     }
/* 243 */     return this.socketInputStream; }
/*     */   public InetAddress getInetAddress() { SocketAddress socketAddress = this.sc.remoteAddress(); if (socketAddress == null) return null;  return ((InetSocketAddress)socketAddress).getAddress(); }
/*     */   public InetAddress getLocalAddress() { if (this.sc.isOpen()) { InetSocketAddress inetSocketAddress = this.sc.localAddress(); if (inetSocketAddress != null) return Net.getRevealedLocalAddress(inetSocketAddress).getAddress();  }  return (new InetSocketAddress(0)).getAddress(); }
/*     */   public int getPort() { SocketAddress socketAddress = this.sc.remoteAddress(); if (socketAddress == null) return 0;  return ((InetSocketAddress)socketAddress).getPort(); } public int getLocalPort() { InetSocketAddress inetSocketAddress = this.sc.localAddress(); if (inetSocketAddress == null) return -1;  return inetSocketAddress.getPort(); } private class SocketInputStream extends ChannelInputStream {
/* 247 */     private SocketInputStream() { super(SocketAdaptor.this.sc); } protected int read(ByteBuffer param1ByteBuffer) throws IOException { synchronized (SocketAdaptor.this.sc.blockingLock()) { if (!SocketAdaptor.this.sc.isBlocking()) throw new IllegalBlockingModeException();  if (SocketAdaptor.this.timeout == 0) return SocketAdaptor.this.sc.read(param1ByteBuffer);  SocketAdaptor.this.sc.configureBlocking(false); try { int i; if ((i = SocketAdaptor.this.sc.read(param1ByteBuffer)) != 0) return i;  long l = SocketAdaptor.this.timeout; while (true) { if (!SocketAdaptor.this.sc.isOpen()) throw new ClosedChannelException();  long l1 = System.currentTimeMillis(); int j = SocketAdaptor.this.sc.poll(Net.POLLIN, l); if (j > 0 && (i = SocketAdaptor.this.sc.read(param1ByteBuffer)) != 0) return i;  l -= System.currentTimeMillis() - l1; if (l <= 0L) throw new SocketTimeoutException();  }  } finally { if (SocketAdaptor.this.sc.isOpen()) SocketAdaptor.this.sc.configureBlocking(true);  }  }  } } public OutputStream getOutputStream() throws IOException { if (!this.sc.isOpen())
/* 248 */       throw new SocketException("Socket is closed"); 
/* 249 */     if (!this.sc.isConnected())
/* 250 */       throw new SocketException("Socket is not connected"); 
/* 251 */     if (!this.sc.isOutputOpen())
/* 252 */       throw new SocketException("Socket output is shutdown"); 
/* 253 */     OutputStream outputStream = null;
/*     */     try {
/* 255 */       outputStream = AccessController.<OutputStream>doPrivileged(new PrivilegedExceptionAction<OutputStream>()
/*     */           {
/*     */             public OutputStream run() throws IOException {
/* 258 */               return Channels.newOutputStream(SocketAdaptor.this.sc);
/*     */             }
/*     */           });
/* 261 */     } catch (PrivilegedActionException privilegedActionException) {
/* 262 */       throw (IOException)privilegedActionException.getException();
/*     */     } 
/* 264 */     return outputStream; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setBooleanOption(SocketOption<Boolean> paramSocketOption, boolean paramBoolean) throws SocketException {
/*     */     try {
/* 271 */       this.sc.setOption(paramSocketOption, Boolean.valueOf(paramBoolean));
/* 272 */     } catch (IOException iOException) {
/* 273 */       Net.translateToSocketException(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setIntOption(SocketOption<Integer> paramSocketOption, int paramInt) throws SocketException {
/*     */     try {
/* 281 */       this.sc.setOption(paramSocketOption, Integer.valueOf(paramInt));
/* 282 */     } catch (IOException iOException) {
/* 283 */       Net.translateToSocketException(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean getBooleanOption(SocketOption<Boolean> paramSocketOption) throws SocketException {
/*     */     try {
/* 289 */       return ((Boolean)this.sc.<Boolean>getOption(paramSocketOption)).booleanValue();
/* 290 */     } catch (IOException iOException) {
/* 291 */       Net.translateToSocketException(iOException);
/* 292 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getIntOption(SocketOption<Integer> paramSocketOption) throws SocketException {
/*     */     try {
/* 298 */       return ((Integer)this.sc.<Integer>getOption(paramSocketOption)).intValue();
/* 299 */     } catch (IOException iOException) {
/* 300 */       Net.translateToSocketException(iOException);
/* 301 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setTcpNoDelay(boolean paramBoolean) throws SocketException {
/* 306 */     setBooleanOption(StandardSocketOptions.TCP_NODELAY, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getTcpNoDelay() throws SocketException {
/* 310 */     return getBooleanOption(StandardSocketOptions.TCP_NODELAY);
/*     */   }
/*     */   
/*     */   public void setSoLinger(boolean paramBoolean, int paramInt) throws SocketException {
/* 314 */     if (!paramBoolean)
/* 315 */       paramInt = -1; 
/* 316 */     setIntOption(StandardSocketOptions.SO_LINGER, paramInt);
/*     */   }
/*     */   
/*     */   public int getSoLinger() throws SocketException {
/* 320 */     return getIntOption(StandardSocketOptions.SO_LINGER);
/*     */   }
/*     */   
/*     */   public void sendUrgentData(int paramInt) throws IOException {
/* 324 */     int i = this.sc.sendOutOfBandData((byte)paramInt);
/* 325 */     if (i == 0)
/* 326 */       throw new IOException("Socket buffer full"); 
/*     */   }
/*     */   
/*     */   public void setOOBInline(boolean paramBoolean) throws SocketException {
/* 330 */     setBooleanOption(ExtendedSocketOption.SO_OOBINLINE, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getOOBInline() throws SocketException {
/* 334 */     return getBooleanOption(ExtendedSocketOption.SO_OOBINLINE);
/*     */   }
/*     */   
/*     */   public void setSoTimeout(int paramInt) throws SocketException {
/* 338 */     if (paramInt < 0)
/* 339 */       throw new IllegalArgumentException("timeout can't be negative"); 
/* 340 */     this.timeout = paramInt;
/*     */   }
/*     */   
/*     */   public int getSoTimeout() throws SocketException {
/* 344 */     return this.timeout;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSendBufferSize(int paramInt) throws SocketException {
/* 349 */     if (paramInt <= 0)
/* 350 */       throw new IllegalArgumentException("Invalid send size"); 
/* 351 */     setIntOption(StandardSocketOptions.SO_SNDBUF, paramInt);
/*     */   }
/*     */   
/*     */   public int getSendBufferSize() throws SocketException {
/* 355 */     return getIntOption(StandardSocketOptions.SO_SNDBUF);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReceiveBufferSize(int paramInt) throws SocketException {
/* 360 */     if (paramInt <= 0)
/* 361 */       throw new IllegalArgumentException("Invalid receive size"); 
/* 362 */     setIntOption(StandardSocketOptions.SO_RCVBUF, paramInt);
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize() throws SocketException {
/* 366 */     return getIntOption(StandardSocketOptions.SO_RCVBUF);
/*     */   }
/*     */   
/*     */   public void setKeepAlive(boolean paramBoolean) throws SocketException {
/* 370 */     setBooleanOption(StandardSocketOptions.SO_KEEPALIVE, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getKeepAlive() throws SocketException {
/* 374 */     return getBooleanOption(StandardSocketOptions.SO_KEEPALIVE);
/*     */   }
/*     */   
/*     */   public void setTrafficClass(int paramInt) throws SocketException {
/* 378 */     setIntOption(StandardSocketOptions.IP_TOS, paramInt);
/*     */   }
/*     */   
/*     */   public int getTrafficClass() throws SocketException {
/* 382 */     return getIntOption(StandardSocketOptions.IP_TOS);
/*     */   }
/*     */   
/*     */   public void setReuseAddress(boolean paramBoolean) throws SocketException {
/* 386 */     setBooleanOption(StandardSocketOptions.SO_REUSEADDR, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getReuseAddress() throws SocketException {
/* 390 */     return getBooleanOption(StandardSocketOptions.SO_REUSEADDR);
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 394 */     this.sc.close();
/*     */   }
/*     */   
/*     */   public void shutdownInput() throws IOException {
/*     */     try {
/* 399 */       this.sc.shutdownInput();
/* 400 */     } catch (Exception exception) {
/* 401 */       Net.translateException(exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void shutdownOutput() throws IOException {
/*     */     try {
/* 407 */       this.sc.shutdownOutput();
/* 408 */     } catch (Exception exception) {
/* 409 */       Net.translateException(exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 414 */     if (this.sc.isConnected())
/* 415 */       return "Socket[addr=" + getInetAddress() + ",port=" + 
/* 416 */         getPort() + ",localport=" + 
/* 417 */         getLocalPort() + "]"; 
/* 418 */     return "Socket[unconnected]";
/*     */   }
/*     */   
/*     */   public boolean isConnected() {
/* 422 */     return this.sc.isConnected();
/*     */   }
/*     */   
/*     */   public boolean isBound() {
/* 426 */     return (this.sc.localAddress() != null);
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/* 430 */     return !this.sc.isOpen();
/*     */   }
/*     */   
/*     */   public boolean isInputShutdown() {
/* 434 */     return !this.sc.isInputOpen();
/*     */   }
/*     */   
/*     */   public boolean isOutputShutdown() {
/* 438 */     return !this.sc.isOutputOpen();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\SocketAdaptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */