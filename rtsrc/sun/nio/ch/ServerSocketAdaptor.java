/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.StandardSocketOptions;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.IllegalBlockingModeException;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerSocketAdaptor
/*     */   extends ServerSocket
/*     */ {
/*     */   private final ServerSocketChannelImpl ssc;
/*  48 */   private volatile int timeout = 0;
/*     */   
/*     */   public static ServerSocket create(ServerSocketChannelImpl paramServerSocketChannelImpl) {
/*     */     try {
/*  52 */       return new ServerSocketAdaptor(paramServerSocketChannelImpl);
/*  53 */     } catch (IOException iOException) {
/*  54 */       throw new Error(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerSocketAdaptor(ServerSocketChannelImpl paramServerSocketChannelImpl) throws IOException {
/*  62 */     this.ssc = paramServerSocketChannelImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(SocketAddress paramSocketAddress) throws IOException {
/*  67 */     bind(paramSocketAddress, 50);
/*     */   }
/*     */   
/*     */   public void bind(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/*  71 */     if (paramSocketAddress == null)
/*  72 */       paramSocketAddress = new InetSocketAddress(0); 
/*     */     try {
/*  74 */       this.ssc.bind(paramSocketAddress, paramInt);
/*  75 */     } catch (Exception exception) {
/*  76 */       Net.translateException(exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public InetAddress getInetAddress() {
/*  81 */     if (!this.ssc.isBound())
/*  82 */       return null; 
/*  83 */     return Net.getRevealedLocalAddress(this.ssc.localAddress()).getAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLocalPort() {
/*  88 */     if (!this.ssc.isBound())
/*  89 */       return -1; 
/*  90 */     return Net.asInetSocketAddress(this.ssc.localAddress()).getPort();
/*     */   }
/*     */ 
/*     */   
/*     */   public Socket accept() throws IOException {
/*  95 */     synchronized (this.ssc.blockingLock()) {
/*  96 */       if (!this.ssc.isBound())
/*  97 */         throw new IllegalBlockingModeException(); 
/*     */       try {
/*  99 */         if (this.timeout == 0) {
/* 100 */           SocketChannel socketChannel = this.ssc.accept();
/* 101 */           if (socketChannel == null && !this.ssc.isBlocking())
/* 102 */             throw new IllegalBlockingModeException(); 
/* 103 */           return socketChannel.socket();
/*     */         } 
/*     */         
/* 106 */         this.ssc.configureBlocking(false);
/*     */         try {
/*     */           SocketChannel socketChannel;
/* 109 */           if ((socketChannel = this.ssc.accept()) != null)
/* 110 */             return socketChannel.socket(); 
/* 111 */           long l = this.timeout;
/*     */           while (true) {
/* 113 */             if (!this.ssc.isOpen())
/* 114 */               throw new ClosedChannelException(); 
/* 115 */             long l1 = System.currentTimeMillis();
/* 116 */             int i = this.ssc.poll(Net.POLLIN, l);
/* 117 */             if (i > 0 && (socketChannel = this.ssc.accept()) != null)
/* 118 */               return socketChannel.socket(); 
/* 119 */             l -= System.currentTimeMillis() - l1;
/* 120 */             if (l <= 0L)
/* 121 */               throw new SocketTimeoutException(); 
/*     */           } 
/*     */         } finally {
/* 124 */           if (this.ssc.isOpen()) {
/* 125 */             this.ssc.configureBlocking(true);
/*     */           }
/*     */         } 
/* 128 */       } catch (Exception exception) {
/* 129 */         Net.translateException(exception);
/*     */         assert false;
/* 131 */         return null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 137 */     this.ssc.close();
/*     */   }
/*     */   
/*     */   public ServerSocketChannel getChannel() {
/* 141 */     return this.ssc;
/*     */   }
/*     */   
/*     */   public boolean isBound() {
/* 145 */     return this.ssc.isBound();
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/* 149 */     return !this.ssc.isOpen();
/*     */   }
/*     */   
/*     */   public void setSoTimeout(int paramInt) throws SocketException {
/* 153 */     this.timeout = paramInt;
/*     */   }
/*     */   
/*     */   public int getSoTimeout() throws SocketException {
/* 157 */     return this.timeout;
/*     */   }
/*     */   
/*     */   public void setReuseAddress(boolean paramBoolean) throws SocketException {
/*     */     try {
/* 162 */       this.ssc.setOption(StandardSocketOptions.SO_REUSEADDR, Boolean.valueOf(paramBoolean));
/* 163 */     } catch (IOException iOException) {
/* 164 */       Net.translateToSocketException(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean getReuseAddress() throws SocketException {
/*     */     try {
/* 170 */       return ((Boolean)this.ssc.<Boolean>getOption(StandardSocketOptions.SO_REUSEADDR)).booleanValue();
/* 171 */     } catch (IOException iOException) {
/* 172 */       Net.translateToSocketException(iOException);
/* 173 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 178 */     if (!isBound())
/* 179 */       return "ServerSocket[unbound]"; 
/* 180 */     return "ServerSocket[addr=" + getInetAddress() + ",localport=" + 
/*     */       
/* 182 */       getLocalPort() + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReceiveBufferSize(int paramInt) throws SocketException {
/* 187 */     if (paramInt <= 0)
/* 188 */       throw new IllegalArgumentException("size cannot be 0 or negative"); 
/*     */     try {
/* 190 */       this.ssc.setOption(StandardSocketOptions.SO_RCVBUF, Integer.valueOf(paramInt));
/* 191 */     } catch (IOException iOException) {
/* 192 */       Net.translateToSocketException(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize() throws SocketException {
/*     */     try {
/* 198 */       return ((Integer)this.ssc.<Integer>getOption(StandardSocketOptions.SO_RCVBUF)).intValue();
/* 199 */     } catch (IOException iOException) {
/* 200 */       Net.translateToSocketException(iOException);
/* 201 */       return -1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\ServerSocketAdaptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */