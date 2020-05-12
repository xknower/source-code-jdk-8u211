/*     */ package java.net;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.ServerSocketChannel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerSocket
/*     */   implements Closeable
/*     */ {
/*     */   private boolean created = false;
/*     */   private boolean bound = false;
/*     */   private boolean closed = false;
/*  59 */   private Object closeLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SocketImpl impl;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean oldImpl = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ServerSocket(SocketImpl paramSocketImpl) {
/*  76 */     this.impl = paramSocketImpl;
/*  77 */     paramSocketImpl.setServerSocket(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocket() throws IOException {
/*  87 */     setImpl();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocket(int paramInt) throws IOException {
/* 128 */     this(paramInt, 50, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocket(int paramInt1, int paramInt2) throws IOException {
/* 181 */     this(paramInt1, paramInt2, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocket(int paramInt1, int paramInt2, InetAddress paramInetAddress) throws IOException {
/* 230 */     setImpl();
/* 231 */     if (paramInt1 < 0 || paramInt1 > 65535) {
/* 232 */       throw new IllegalArgumentException("Port value out of range: " + paramInt1);
/*     */     }
/* 234 */     if (paramInt2 < 1)
/* 235 */       paramInt2 = 50; 
/*     */     try {
/* 237 */       bind(new InetSocketAddress(paramInetAddress, paramInt1), paramInt2);
/* 238 */     } catch (SecurityException securityException) {
/* 239 */       close();
/* 240 */       throw securityException;
/* 241 */     } catch (IOException iOException) {
/* 242 */       close();
/* 243 */       throw iOException;
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
/*     */   SocketImpl getImpl() throws SocketException {
/* 256 */     if (!this.created)
/* 257 */       createImpl(); 
/* 258 */     return this.impl;
/*     */   }
/*     */   
/*     */   private void checkOldImpl() {
/* 262 */     if (this.impl == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 267 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */           {
/*     */             public Void run() throws NoSuchMethodException {
/* 270 */               ServerSocket.this.impl.getClass().getDeclaredMethod("connect", new Class[] { SocketAddress.class, int.class });
/*     */ 
/*     */               
/* 273 */               return null;
/*     */             }
/*     */           });
/* 276 */     } catch (PrivilegedActionException privilegedActionException) {
/* 277 */       this.oldImpl = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setImpl() {
/* 282 */     if (factory != null) {
/* 283 */       this.impl = factory.createSocketImpl();
/* 284 */       checkOldImpl();
/*     */     }
/*     */     else {
/*     */       
/* 288 */       this.impl = new SocksSocketImpl();
/*     */     } 
/* 290 */     if (this.impl != null) {
/* 291 */       this.impl.setServerSocket(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void createImpl() throws SocketException {
/* 301 */     if (this.impl == null)
/* 302 */       setImpl(); 
/*     */     try {
/* 304 */       this.impl.create(true);
/* 305 */       this.created = true;
/* 306 */     } catch (IOException iOException) {
/* 307 */       throw new SocketException(iOException.getMessage());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(SocketAddress paramSocketAddress) throws IOException {
/* 329 */     bind(paramSocketAddress, 50);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/* 358 */     if (isClosed())
/* 359 */       throw new SocketException("Socket is closed"); 
/* 360 */     if (!this.oldImpl && isBound())
/* 361 */       throw new SocketException("Already bound"); 
/* 362 */     if (paramSocketAddress == null)
/* 363 */       paramSocketAddress = new InetSocketAddress(0); 
/* 364 */     if (!(paramSocketAddress instanceof InetSocketAddress))
/* 365 */       throw new IllegalArgumentException("Unsupported address type"); 
/* 366 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketAddress;
/* 367 */     if (inetSocketAddress.isUnresolved())
/* 368 */       throw new SocketException("Unresolved address"); 
/* 369 */     if (paramInt < 1)
/* 370 */       paramInt = 50; 
/*     */     try {
/* 372 */       SecurityManager securityManager = System.getSecurityManager();
/* 373 */       if (securityManager != null)
/* 374 */         securityManager.checkListen(inetSocketAddress.getPort()); 
/* 375 */       getImpl().bind(inetSocketAddress.getAddress(), inetSocketAddress.getPort());
/* 376 */       getImpl().listen(paramInt);
/* 377 */       this.bound = true;
/* 378 */     } catch (SecurityException securityException) {
/* 379 */       this.bound = false;
/* 380 */       throw securityException;
/* 381 */     } catch (IOException iOException) {
/* 382 */       this.bound = false;
/* 383 */       throw iOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetAddress getInetAddress() {
/* 406 */     if (!isBound())
/* 407 */       return null; 
/*     */     try {
/* 409 */       InetAddress inetAddress = getImpl().getInetAddress();
/* 410 */       SecurityManager securityManager = System.getSecurityManager();
/* 411 */       if (securityManager != null)
/* 412 */         securityManager.checkConnect(inetAddress.getHostAddress(), -1); 
/* 413 */       return inetAddress;
/* 414 */     } catch (SecurityException securityException) {
/* 415 */       return InetAddress.getLoopbackAddress();
/* 416 */     } catch (SocketException socketException) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 421 */       return null;
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
/*     */   public int getLocalPort() {
/* 435 */     if (!isBound())
/* 436 */       return -1; 
/*     */     try {
/* 438 */       return getImpl().getLocalPort();
/* 439 */     } catch (SocketException socketException) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 444 */       return -1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketAddress getLocalSocketAddress() {
/* 474 */     if (!isBound())
/* 475 */       return null; 
/* 476 */     return new InetSocketAddress(getInetAddress(), getLocalPort());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Socket accept() throws IOException {
/* 508 */     if (isClosed())
/* 509 */       throw new SocketException("Socket is closed"); 
/* 510 */     if (!isBound())
/* 511 */       throw new SocketException("Socket is not bound yet"); 
/* 512 */     Socket socket = new Socket((SocketImpl)null);
/* 513 */     implAccept(socket);
/* 514 */     return socket;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void implAccept(Socket paramSocket) throws IOException {
/* 534 */     SocketImpl socketImpl = null;
/*     */     try {
/* 536 */       if (paramSocket.impl == null) {
/* 537 */         paramSocket.setImpl();
/*     */       } else {
/* 539 */         paramSocket.impl.reset();
/*     */       } 
/* 541 */       socketImpl = paramSocket.impl;
/* 542 */       paramSocket.impl = null;
/* 543 */       socketImpl.address = new InetAddress();
/* 544 */       socketImpl.fd = new FileDescriptor();
/* 545 */       getImpl().accept(socketImpl);
/*     */       
/* 547 */       SecurityManager securityManager = System.getSecurityManager();
/* 548 */       if (securityManager != null) {
/* 549 */         securityManager.checkAccept(socketImpl.getInetAddress().getHostAddress(), socketImpl
/* 550 */             .getPort());
/*     */       }
/* 552 */     } catch (IOException iOException) {
/* 553 */       if (socketImpl != null)
/* 554 */         socketImpl.reset(); 
/* 555 */       paramSocket.impl = socketImpl;
/* 556 */       throw iOException;
/* 557 */     } catch (SecurityException securityException) {
/* 558 */       if (socketImpl != null)
/* 559 */         socketImpl.reset(); 
/* 560 */       paramSocket.impl = socketImpl;
/* 561 */       throw securityException;
/*     */     } 
/* 563 */     paramSocket.impl = socketImpl;
/* 564 */     paramSocket.postAccept();
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
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 581 */     synchronized (this.closeLock) {
/* 582 */       if (isClosed())
/*     */         return; 
/* 584 */       if (this.created)
/* 585 */         this.impl.close(); 
/* 586 */       this.closed = true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocketChannel getChannel() {
/* 607 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBound() {
/* 618 */     return (this.bound || this.oldImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 628 */     synchronized (this.closeLock) {
/* 629 */       return this.closed;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSoTimeout(int paramInt) throws SocketException {
/* 650 */     if (isClosed())
/* 651 */       throw new SocketException("Socket is closed"); 
/* 652 */     getImpl().setOption(4102, new Integer(paramInt));
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
/*     */   public synchronized int getSoTimeout() throws IOException {
/* 664 */     if (isClosed())
/* 665 */       throw new SocketException("Socket is closed"); 
/* 666 */     Object object = getImpl().getOption(4102);
/*     */     
/* 668 */     if (object instanceof Integer) {
/* 669 */       return ((Integer)object).intValue();
/*     */     }
/* 671 */     return 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReuseAddress(boolean paramBoolean) throws SocketException {
/* 712 */     if (isClosed())
/* 713 */       throw new SocketException("Socket is closed"); 
/* 714 */     getImpl().setOption(4, Boolean.valueOf(paramBoolean));
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
/*     */   public boolean getReuseAddress() throws SocketException {
/* 728 */     if (isClosed())
/* 729 */       throw new SocketException("Socket is closed"); 
/* 730 */     return ((Boolean)getImpl().getOption(4)).booleanValue();
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
/*     */   
/*     */   public String toString() {
/*     */     InetAddress inetAddress;
/* 747 */     if (!isBound()) {
/* 748 */       return "ServerSocket[unbound]";
/*     */     }
/* 750 */     if (System.getSecurityManager() != null) {
/* 751 */       inetAddress = InetAddress.getLoopbackAddress();
/*     */     } else {
/* 753 */       inetAddress = this.impl.getInetAddress();
/* 754 */     }  return "ServerSocket[addr=" + inetAddress + ",localport=" + this.impl
/* 755 */       .getLocalPort() + "]";
/*     */   }
/*     */   
/*     */   void setBound() {
/* 759 */     this.bound = true;
/*     */   }
/*     */   
/*     */   void setCreated() {
/* 763 */     this.created = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 769 */   private static SocketImplFactory factory = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void setSocketFactory(SocketImplFactory paramSocketImplFactory) throws IOException {
/* 797 */     if (factory != null) {
/* 798 */       throw new SocketException("factory already defined");
/*     */     }
/* 800 */     SecurityManager securityManager = System.getSecurityManager();
/* 801 */     if (securityManager != null) {
/* 802 */       securityManager.checkSetFactory();
/*     */     }
/* 804 */     factory = paramSocketImplFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setReceiveBufferSize(int paramInt) throws SocketException {
/* 844 */     if (paramInt <= 0) {
/* 845 */       throw new IllegalArgumentException("negative receive size");
/*     */     }
/* 847 */     if (isClosed())
/* 848 */       throw new SocketException("Socket is closed"); 
/* 849 */     getImpl().setOption(4098, new Integer(paramInt));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getReceiveBufferSize() throws SocketException {
/* 868 */     if (isClosed())
/* 869 */       throw new SocketException("Socket is closed"); 
/* 870 */     int i = 0;
/* 871 */     Object object = getImpl().getOption(4098);
/* 872 */     if (object instanceof Integer) {
/* 873 */       i = ((Integer)object).intValue();
/*     */     }
/* 875 */     return i;
/*     */   }
/*     */   
/*     */   public void setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3) {}
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\net\ServerSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */