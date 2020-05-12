/*      */ package java.net;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.channels.SocketChannel;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import sun.net.ApplicationProxy;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Socket
/*      */   implements Closeable
/*      */ {
/*      */   private boolean created = false;
/*      */   private boolean bound = false;
/*      */   private boolean connected = false;
/*      */   private boolean closed = false;
/*   62 */   private Object closeLock = new Object();
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shutIn = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shutOut = false;
/*      */ 
/*      */ 
/*      */   
/*      */   SocketImpl impl;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean oldImpl = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket() {
/*   84 */     setImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket(Proxy paramProxy) {
/*  117 */     if (paramProxy == null) {
/*  118 */       throw new IllegalArgumentException("Invalid Proxy");
/*      */     }
/*      */     
/*  121 */     Proxy proxy = (paramProxy == Proxy.NO_PROXY) ? Proxy.NO_PROXY : ApplicationProxy.create(paramProxy);
/*  122 */     Proxy.Type type = proxy.type();
/*  123 */     if (type == Proxy.Type.SOCKS || type == Proxy.Type.HTTP)
/*  124 */     { SecurityManager securityManager = System.getSecurityManager();
/*  125 */       InetSocketAddress inetSocketAddress = (InetSocketAddress)proxy.address();
/*  126 */       if (inetSocketAddress.getAddress() != null) {
/*  127 */         checkAddress(inetSocketAddress.getAddress(), "Socket");
/*      */       }
/*  129 */       if (securityManager != null) {
/*  130 */         if (inetSocketAddress.isUnresolved())
/*  131 */           inetSocketAddress = new InetSocketAddress(inetSocketAddress.getHostName(), inetSocketAddress.getPort()); 
/*  132 */         if (inetSocketAddress.isUnresolved()) {
/*  133 */           securityManager.checkConnect(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
/*      */         } else {
/*  135 */           securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress
/*  136 */               .getPort());
/*      */         } 
/*  138 */       }  this.impl = (type == Proxy.Type.SOCKS) ? new SocksSocketImpl(proxy) : new HttpConnectSocketImpl(proxy);
/*      */       
/*  140 */       this.impl.setSocket(this); }
/*      */     
/*  142 */     else if (proxy == Proxy.NO_PROXY)
/*  143 */     { if (factory == null) {
/*  144 */         this.impl = new PlainSocketImpl();
/*  145 */         this.impl.setSocket(this);
/*      */       } else {
/*  147 */         setImpl();
/*      */       }  }
/*  149 */     else { throw new IllegalArgumentException("Invalid Proxy"); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Socket(SocketImpl paramSocketImpl) throws SocketException {
/*  165 */     this.impl = paramSocketImpl;
/*  166 */     if (paramSocketImpl != null) {
/*  167 */       checkOldImpl();
/*  168 */       this.impl.setSocket(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket(String paramString, int paramInt) throws UnknownHostException, IOException {
/*  211 */     this((paramString != null) ? new InetSocketAddress(paramString, paramInt) : new InetSocketAddress(
/*  212 */           InetAddress.getByName(null), paramInt), (SocketAddress)null, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket(InetAddress paramInetAddress, int paramInt) throws IOException {
/*  244 */     this((paramInetAddress != null) ? new InetSocketAddress(paramInetAddress, paramInt) : null, (SocketAddress)null, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket(String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2) throws IOException {
/*  286 */     this((paramString != null) ? new InetSocketAddress(paramString, paramInt1) : new InetSocketAddress(
/*  287 */           InetAddress.getByName(null), paramInt1), new InetSocketAddress(paramInetAddress, paramInt2), true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket(InetAddress paramInetAddress1, int paramInt1, InetAddress paramInetAddress2, int paramInt2) throws IOException {
/*  328 */     this((paramInetAddress1 != null) ? new InetSocketAddress(paramInetAddress1, paramInt1) : null, new InetSocketAddress(paramInetAddress2, paramInt2), true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Socket(String paramString, int paramInt, boolean paramBoolean) throws IOException {
/*  375 */     this((paramString != null) ? new InetSocketAddress(paramString, paramInt) : new InetSocketAddress(
/*  376 */           InetAddress.getByName(null), paramInt), (SocketAddress)null, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Socket(InetAddress paramInetAddress, int paramInt, boolean paramBoolean) throws IOException {
/*  418 */     this((paramInetAddress != null) ? new InetSocketAddress(paramInetAddress, paramInt) : null, new InetSocketAddress(0), paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Socket(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, boolean paramBoolean) throws IOException {
/*  424 */     setImpl();
/*      */ 
/*      */     
/*  427 */     if (paramSocketAddress1 == null) {
/*  428 */       throw new NullPointerException();
/*      */     }
/*      */     try {
/*  431 */       createImpl(paramBoolean);
/*  432 */       if (paramSocketAddress2 != null)
/*  433 */         bind(paramSocketAddress2); 
/*  434 */       connect(paramSocketAddress1);
/*  435 */     } catch (IOException|IllegalArgumentException|SecurityException iOException) {
/*      */       try {
/*  437 */         close();
/*  438 */       } catch (IOException iOException1) {
/*  439 */         iOException.addSuppressed(iOException1);
/*      */       } 
/*  441 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void createImpl(boolean paramBoolean) throws SocketException {
/*  454 */     if (this.impl == null)
/*  455 */       setImpl(); 
/*      */     try {
/*  457 */       this.impl.create(paramBoolean);
/*  458 */       this.created = true;
/*  459 */     } catch (IOException iOException) {
/*  460 */       throw new SocketException(iOException.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkOldImpl() {
/*  465 */     if (this.impl == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  470 */     this
/*  471 */       .oldImpl = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*      */           public Boolean run() {
/*  473 */             Class<?> clazz = Socket.this.impl.getClass();
/*      */             while (true) {
/*      */               try {
/*  476 */                 clazz.getDeclaredMethod("connect", new Class[] { SocketAddress.class, int.class });
/*  477 */                 return Boolean.FALSE;
/*  478 */               } catch (NoSuchMethodException noSuchMethodException) {
/*  479 */                 clazz = clazz.getSuperclass();
/*      */ 
/*      */ 
/*      */                 
/*  483 */                 if (clazz.equals(SocketImpl.class)) {
/*  484 */                   return Boolean.TRUE;
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           }
/*      */         })).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setImpl() {
/*  497 */     if (factory != null) {
/*  498 */       this.impl = factory.createSocketImpl();
/*  499 */       checkOldImpl();
/*      */     }
/*      */     else {
/*      */       
/*  503 */       this.impl = new SocksSocketImpl();
/*      */     } 
/*  505 */     if (this.impl != null) {
/*  506 */       this.impl.setSocket(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SocketImpl getImpl() throws SocketException {
/*  519 */     if (!this.created)
/*  520 */       createImpl(true); 
/*  521 */     return this.impl;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void connect(SocketAddress paramSocketAddress) throws IOException {
/*  538 */     connect(paramSocketAddress, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void connect(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/*  559 */     if (paramSocketAddress == null) {
/*  560 */       throw new IllegalArgumentException("connect: The address can't be null");
/*      */     }
/*  562 */     if (paramInt < 0) {
/*  563 */       throw new IllegalArgumentException("connect: timeout can't be negative");
/*      */     }
/*  565 */     if (isClosed()) {
/*  566 */       throw new SocketException("Socket is closed");
/*      */     }
/*  568 */     if (!this.oldImpl && isConnected()) {
/*  569 */       throw new SocketException("already connected");
/*      */     }
/*  571 */     if (!(paramSocketAddress instanceof InetSocketAddress)) {
/*  572 */       throw new IllegalArgumentException("Unsupported address type");
/*      */     }
/*  574 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*  575 */     InetAddress inetAddress = inetSocketAddress.getAddress();
/*  576 */     int i = inetSocketAddress.getPort();
/*  577 */     checkAddress(inetAddress, "connect");
/*      */     
/*  579 */     SecurityManager securityManager = System.getSecurityManager();
/*  580 */     if (securityManager != null)
/*  581 */       if (inetSocketAddress.isUnresolved()) {
/*  582 */         securityManager.checkConnect(inetSocketAddress.getHostName(), i);
/*      */       } else {
/*  584 */         securityManager.checkConnect(inetAddress.getHostAddress(), i);
/*      */       }  
/*  586 */     if (!this.created)
/*  587 */       createImpl(true); 
/*  588 */     if (!this.oldImpl)
/*  589 */     { this.impl.connect(inetSocketAddress, paramInt); }
/*  590 */     else if (paramInt == 0)
/*  591 */     { if (inetSocketAddress.isUnresolved()) {
/*  592 */         this.impl.connect(inetAddress.getHostName(), i);
/*      */       } else {
/*  594 */         this.impl.connect(inetAddress, i);
/*      */       }  }
/*  596 */     else { throw new UnsupportedOperationException("SocketImpl.connect(addr, timeout)"); }
/*  597 */      this.connected = true;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  602 */     this.bound = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void bind(SocketAddress paramSocketAddress) throws IOException {
/*  624 */     if (isClosed())
/*  625 */       throw new SocketException("Socket is closed"); 
/*  626 */     if (!this.oldImpl && isBound()) {
/*  627 */       throw new SocketException("Already bound");
/*      */     }
/*  629 */     if (paramSocketAddress != null && !(paramSocketAddress instanceof InetSocketAddress))
/*  630 */       throw new IllegalArgumentException("Unsupported address type"); 
/*  631 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*  632 */     if (inetSocketAddress != null && inetSocketAddress.isUnresolved())
/*  633 */       throw new SocketException("Unresolved address"); 
/*  634 */     if (inetSocketAddress == null) {
/*  635 */       inetSocketAddress = new InetSocketAddress(0);
/*      */     }
/*  637 */     InetAddress inetAddress = inetSocketAddress.getAddress();
/*  638 */     int i = inetSocketAddress.getPort();
/*  639 */     checkAddress(inetAddress, "bind");
/*  640 */     SecurityManager securityManager = System.getSecurityManager();
/*  641 */     if (securityManager != null) {
/*  642 */       securityManager.checkListen(i);
/*      */     }
/*  644 */     getImpl().bind(inetAddress, i);
/*  645 */     this.bound = true;
/*      */   }
/*      */   
/*      */   private void checkAddress(InetAddress paramInetAddress, String paramString) {
/*  649 */     if (paramInetAddress == null) {
/*      */       return;
/*      */     }
/*  652 */     if (!(paramInetAddress instanceof Inet4Address) && !(paramInetAddress instanceof Inet6Address)) {
/*  653 */       throw new IllegalArgumentException(paramString + ": invalid address type");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void postAccept() {
/*  661 */     this.connected = true;
/*  662 */     this.created = true;
/*  663 */     this.bound = true;
/*      */   }
/*      */   
/*      */   void setCreated() {
/*  667 */     this.created = true;
/*      */   }
/*      */   
/*      */   void setBound() {
/*  671 */     this.bound = true;
/*      */   }
/*      */   
/*      */   void setConnected() {
/*  675 */     this.connected = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InetAddress getInetAddress() {
/*  689 */     if (!isConnected())
/*  690 */       return null; 
/*      */     try {
/*  692 */       return getImpl().getInetAddress();
/*  693 */     } catch (SocketException socketException) {
/*      */       
/*  695 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InetAddress getLocalAddress() {
/*  715 */     if (!isBound())
/*  716 */       return InetAddress.anyLocalAddress(); 
/*  717 */     InetAddress inetAddress = null;
/*      */     try {
/*  719 */       inetAddress = (InetAddress)getImpl().getOption(15);
/*  720 */       SecurityManager securityManager = System.getSecurityManager();
/*  721 */       if (securityManager != null)
/*  722 */         securityManager.checkConnect(inetAddress.getHostAddress(), -1); 
/*  723 */       if (inetAddress.isAnyLocalAddress()) {
/*  724 */         inetAddress = InetAddress.anyLocalAddress();
/*      */       }
/*  726 */     } catch (SecurityException securityException) {
/*  727 */       inetAddress = InetAddress.getLoopbackAddress();
/*  728 */     } catch (Exception exception) {
/*  729 */       inetAddress = InetAddress.anyLocalAddress();
/*      */     } 
/*  731 */     return inetAddress;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPort() {
/*  745 */     if (!isConnected())
/*  746 */       return 0; 
/*      */     try {
/*  748 */       return getImpl().getPort();
/*  749 */     } catch (SocketException socketException) {
/*      */ 
/*      */       
/*  752 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLocalPort() {
/*  766 */     if (!isBound())
/*  767 */       return -1; 
/*      */     try {
/*  769 */       return getImpl().getLocalPort();
/*  770 */     } catch (SocketException socketException) {
/*      */ 
/*      */       
/*  773 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SocketAddress getRemoteSocketAddress() {
/*  794 */     if (!isConnected())
/*  795 */       return null; 
/*  796 */     return new InetSocketAddress(getInetAddress(), getPort());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SocketAddress getLocalSocketAddress() {
/*  830 */     if (!isBound())
/*  831 */       return null; 
/*  832 */     return new InetSocketAddress(getLocalAddress(), getLocalPort());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SocketChannel getChannel() {
/*  853 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream getInputStream() throws IOException {
/*  902 */     if (isClosed())
/*  903 */       throw new SocketException("Socket is closed"); 
/*  904 */     if (!isConnected())
/*  905 */       throw new SocketException("Socket is not connected"); 
/*  906 */     if (isInputShutdown())
/*  907 */       throw new SocketException("Socket input is shutdown"); 
/*  908 */     Socket socket = this;
/*  909 */     InputStream inputStream = null;
/*      */     try {
/*  911 */       inputStream = AccessController.<InputStream>doPrivileged(new PrivilegedExceptionAction<InputStream>()
/*      */           {
/*      */             public InputStream run() throws IOException {
/*  914 */               return Socket.this.impl.getInputStream();
/*      */             }
/*      */           });
/*  917 */     } catch (PrivilegedActionException privilegedActionException) {
/*  918 */       throw (IOException)privilegedActionException.getException();
/*      */     } 
/*  920 */     return inputStream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OutputStream getOutputStream() throws IOException {
/*  942 */     if (isClosed())
/*  943 */       throw new SocketException("Socket is closed"); 
/*  944 */     if (!isConnected())
/*  945 */       throw new SocketException("Socket is not connected"); 
/*  946 */     if (isOutputShutdown())
/*  947 */       throw new SocketException("Socket output is shutdown"); 
/*  948 */     Socket socket = this;
/*  949 */     OutputStream outputStream = null;
/*      */     try {
/*  951 */       outputStream = AccessController.<OutputStream>doPrivileged(new PrivilegedExceptionAction<OutputStream>()
/*      */           {
/*      */             public OutputStream run() throws IOException {
/*  954 */               return Socket.this.impl.getOutputStream();
/*      */             }
/*      */           });
/*  957 */     } catch (PrivilegedActionException privilegedActionException) {
/*  958 */       throw (IOException)privilegedActionException.getException();
/*      */     } 
/*  960 */     return outputStream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTcpNoDelay(boolean paramBoolean) throws SocketException {
/*  978 */     if (isClosed())
/*  979 */       throw new SocketException("Socket is closed"); 
/*  980 */     getImpl().setOption(1, Boolean.valueOf(paramBoolean));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTcpNoDelay() throws SocketException {
/*  994 */     if (isClosed())
/*  995 */       throw new SocketException("Socket is closed"); 
/*  996 */     return ((Boolean)getImpl().getOption(1)).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSoLinger(boolean paramBoolean, int paramInt) throws SocketException {
/* 1015 */     if (isClosed())
/* 1016 */       throw new SocketException("Socket is closed"); 
/* 1017 */     if (!paramBoolean) {
/* 1018 */       getImpl().setOption(128, new Boolean(paramBoolean));
/*      */     } else {
/* 1020 */       if (paramInt < 0) {
/* 1021 */         throw new IllegalArgumentException("invalid value for SO_LINGER");
/*      */       }
/* 1023 */       if (paramInt > 65535)
/* 1024 */         paramInt = 65535; 
/* 1025 */       getImpl().setOption(128, new Integer(paramInt));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSoLinger() throws SocketException {
/* 1043 */     if (isClosed())
/* 1044 */       throw new SocketException("Socket is closed"); 
/* 1045 */     Object object = getImpl().getOption(128);
/* 1046 */     if (object instanceof Integer) {
/* 1047 */       return ((Integer)object).intValue();
/*      */     }
/* 1049 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendUrgentData(int paramInt) throws IOException {
/* 1064 */     if (!getImpl().supportsUrgentData()) {
/* 1065 */       throw new SocketException("Urgent data not supported");
/*      */     }
/* 1067 */     getImpl().sendUrgentData(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOOBInline(boolean paramBoolean) throws SocketException {
/* 1096 */     if (isClosed())
/* 1097 */       throw new SocketException("Socket is closed"); 
/* 1098 */     getImpl().setOption(4099, Boolean.valueOf(paramBoolean));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOOBInline() throws SocketException {
/* 1113 */     if (isClosed())
/* 1114 */       throw new SocketException("Socket is closed"); 
/* 1115 */     return ((Boolean)getImpl().getOption(4099)).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setSoTimeout(int paramInt) throws SocketException {
/* 1136 */     if (isClosed())
/* 1137 */       throw new SocketException("Socket is closed"); 
/* 1138 */     if (paramInt < 0) {
/* 1139 */       throw new IllegalArgumentException("timeout can't be negative");
/*      */     }
/* 1141 */     getImpl().setOption(4102, new Integer(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int getSoTimeout() throws SocketException {
/* 1156 */     if (isClosed())
/* 1157 */       throw new SocketException("Socket is closed"); 
/* 1158 */     Object object = getImpl().getOption(4102);
/*      */     
/* 1160 */     if (object instanceof Integer) {
/* 1161 */       return ((Integer)object).intValue();
/*      */     }
/* 1163 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setSendBufferSize(int paramInt) throws SocketException {
/* 1192 */     if (paramInt <= 0) {
/* 1193 */       throw new IllegalArgumentException("negative send size");
/*      */     }
/* 1195 */     if (isClosed())
/* 1196 */       throw new SocketException("Socket is closed"); 
/* 1197 */     getImpl().setOption(4097, new Integer(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int getSendBufferSize() throws SocketException {
/* 1214 */     if (isClosed())
/* 1215 */       throw new SocketException("Socket is closed"); 
/* 1216 */     int i = 0;
/* 1217 */     Object object = getImpl().getOption(4097);
/* 1218 */     if (object instanceof Integer) {
/* 1219 */       i = ((Integer)object).intValue();
/*      */     }
/* 1221 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setReceiveBufferSize(int paramInt) throws SocketException {
/* 1266 */     if (paramInt <= 0) {
/* 1267 */       throw new IllegalArgumentException("invalid receive size");
/*      */     }
/* 1269 */     if (isClosed())
/* 1270 */       throw new SocketException("Socket is closed"); 
/* 1271 */     getImpl().setOption(4098, new Integer(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int getReceiveBufferSize() throws SocketException {
/* 1288 */     if (isClosed())
/* 1289 */       throw new SocketException("Socket is closed"); 
/* 1290 */     int i = 0;
/* 1291 */     Object object = getImpl().getOption(4098);
/* 1292 */     if (object instanceof Integer) {
/* 1293 */       i = ((Integer)object).intValue();
/*      */     }
/* 1295 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setKeepAlive(boolean paramBoolean) throws SocketException {
/* 1308 */     if (isClosed())
/* 1309 */       throw new SocketException("Socket is closed"); 
/* 1310 */     getImpl().setOption(8, Boolean.valueOf(paramBoolean));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getKeepAlive() throws SocketException {
/* 1324 */     if (isClosed())
/* 1325 */       throw new SocketException("Socket is closed"); 
/* 1326 */     return ((Boolean)getImpl().getOption(8)).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTrafficClass(int paramInt) throws SocketException {
/* 1376 */     if (paramInt < 0 || paramInt > 255) {
/* 1377 */       throw new IllegalArgumentException("tc is not in range 0 -- 255");
/*      */     }
/* 1379 */     if (isClosed())
/* 1380 */       throw new SocketException("Socket is closed"); 
/*      */     try {
/* 1382 */       getImpl().setOption(3, Integer.valueOf(paramInt));
/* 1383 */     } catch (SocketException socketException) {
/*      */ 
/*      */       
/* 1386 */       if (!isConnected()) {
/* 1387 */         throw socketException;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTrafficClass() throws SocketException {
/* 1408 */     return ((Integer)getImpl().getOption(3)).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReuseAddress(boolean paramBoolean) throws SocketException {
/* 1447 */     if (isClosed())
/* 1448 */       throw new SocketException("Socket is closed"); 
/* 1449 */     getImpl().setOption(4, Boolean.valueOf(paramBoolean));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getReuseAddress() throws SocketException {
/* 1463 */     if (isClosed())
/* 1464 */       throw new SocketException("Socket is closed"); 
/* 1465 */     return ((Boolean)getImpl().getOption(4)).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void close() throws IOException {
/* 1491 */     synchronized (this.closeLock) {
/* 1492 */       if (isClosed())
/*      */         return; 
/* 1494 */       if (this.created)
/* 1495 */         this.impl.close(); 
/* 1496 */       this.closed = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdownInput() throws IOException {
/* 1520 */     if (isClosed())
/* 1521 */       throw new SocketException("Socket is closed"); 
/* 1522 */     if (!isConnected())
/* 1523 */       throw new SocketException("Socket is not connected"); 
/* 1524 */     if (isInputShutdown())
/* 1525 */       throw new SocketException("Socket input is already shutdown"); 
/* 1526 */     getImpl().shutdownInput();
/* 1527 */     this.shutIn = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdownOutput() throws IOException {
/* 1550 */     if (isClosed())
/* 1551 */       throw new SocketException("Socket is closed"); 
/* 1552 */     if (!isConnected())
/* 1553 */       throw new SocketException("Socket is not connected"); 
/* 1554 */     if (isOutputShutdown())
/* 1555 */       throw new SocketException("Socket output is already shutdown"); 
/* 1556 */     getImpl().shutdownOutput();
/* 1557 */     this.shutOut = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*      */     try {
/* 1567 */       if (isConnected())
/* 1568 */         return "Socket[addr=" + getImpl().getInetAddress() + ",port=" + 
/* 1569 */           getImpl().getPort() + ",localport=" + 
/* 1570 */           getImpl().getLocalPort() + "]"; 
/* 1571 */     } catch (SocketException socketException) {}
/*      */     
/* 1573 */     return "Socket[unconnected]";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isConnected() {
/* 1589 */     return (this.connected || this.oldImpl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBound() {
/* 1606 */     return (this.bound || this.oldImpl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isClosed() {
/* 1617 */     synchronized (this.closeLock) {
/* 1618 */       return this.closed;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInputShutdown() {
/* 1630 */     return this.shutIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOutputShutdown() {
/* 1641 */     return this.shutOut;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1647 */   private static SocketImplFactory factory = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized void setSocketImplFactory(SocketImplFactory paramSocketImplFactory) throws IOException {
/* 1676 */     if (factory != null) {
/* 1677 */       throw new SocketException("factory already defined");
/*      */     }
/* 1679 */     SecurityManager securityManager = System.getSecurityManager();
/* 1680 */     if (securityManager != null) {
/* 1681 */       securityManager.checkSetFactory();
/*      */     }
/* 1683 */     factory = paramSocketImplFactory;
/*      */   }
/*      */   
/*      */   public void setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3) {}
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\net\Socket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */