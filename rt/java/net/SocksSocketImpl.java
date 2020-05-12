/*      */ package java.net;
/*      */ 
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.Iterator;
/*      */ import sun.net.SocksProxy;
/*      */ import sun.net.www.ParseUtil;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class SocksSocketImpl
/*      */   extends PlainSocketImpl
/*      */   implements SocksConsts
/*      */ {
/*   44 */   private String server = null;
/*   45 */   private int serverPort = 1080;
/*      */   private InetSocketAddress external_address;
/*      */   private boolean useV4 = false;
/*   48 */   private Socket cmdsock = null;
/*   49 */   private InputStream cmdIn = null;
/*   50 */   private OutputStream cmdOut = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean applicationSetProxy;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SocksSocketImpl(String paramString, int paramInt) {
/*   60 */     this.server = paramString;
/*   61 */     this.serverPort = (paramInt == -1) ? 1080 : paramInt;
/*      */   }
/*      */   
/*      */   SocksSocketImpl(Proxy paramProxy) {
/*   65 */     SocketAddress socketAddress = paramProxy.address();
/*   66 */     if (socketAddress instanceof InetSocketAddress) {
/*   67 */       InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
/*      */       
/*   69 */       this.server = inetSocketAddress.getHostString();
/*   70 */       this.serverPort = inetSocketAddress.getPort();
/*      */     } 
/*      */   }
/*      */   
/*      */   void setV4() {
/*   75 */     this.useV4 = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void privilegedConnect(final String host, final int port, final int timeout) throws IOException {
/*      */     try {
/*   84 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */           {
/*      */             public Void run() throws IOException {
/*   87 */               SocksSocketImpl.this.superConnectServer(host, port, timeout);
/*   88 */               SocksSocketImpl.this.cmdIn = SocksSocketImpl.this.getInputStream();
/*   89 */               SocksSocketImpl.this.cmdOut = SocksSocketImpl.this.getOutputStream();
/*   90 */               return null;
/*      */             }
/*      */           });
/*   93 */     } catch (PrivilegedActionException privilegedActionException) {
/*   94 */       throw (IOException)privilegedActionException.getException();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void superConnectServer(String paramString, int paramInt1, int paramInt2) throws IOException {
/*  100 */     super.connect(new InetSocketAddress(paramString, paramInt1), paramInt2);
/*      */   }
/*      */   
/*      */   private static int remainingMillis(long paramLong) throws IOException {
/*  104 */     if (paramLong == 0L) {
/*  105 */       return 0;
/*      */     }
/*  107 */     long l = paramLong - System.currentTimeMillis();
/*  108 */     if (l > 0L) {
/*  109 */       return (int)l;
/*      */     }
/*  111 */     throw new SocketTimeoutException();
/*      */   }
/*      */   
/*      */   private int readSocksReply(InputStream paramInputStream, byte[] paramArrayOfbyte) throws IOException {
/*  115 */     return readSocksReply(paramInputStream, paramArrayOfbyte, 0L);
/*      */   }
/*      */   
/*      */   private int readSocksReply(InputStream paramInputStream, byte[] paramArrayOfbyte, long paramLong) throws IOException {
/*  119 */     int i = paramArrayOfbyte.length;
/*  120 */     int j = 0;
/*  121 */     for (byte b = 0; j < i && b < 3; b++) {
/*      */       int k;
/*      */       try {
/*  124 */         k = ((SocketInputStream)paramInputStream).read(paramArrayOfbyte, j, i - j, remainingMillis(paramLong));
/*  125 */       } catch (SocketTimeoutException socketTimeoutException) {
/*  126 */         throw new SocketTimeoutException("Connect timed out");
/*      */       } 
/*  128 */       if (k < 0)
/*  129 */         throw new SocketException("Malformed reply from SOCKS server"); 
/*  130 */       j += k;
/*      */     } 
/*  132 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean authenticate(byte paramByte, InputStream paramInputStream, BufferedOutputStream paramBufferedOutputStream) throws IOException {
/*  140 */     return authenticate(paramByte, paramInputStream, paramBufferedOutputStream, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean authenticate(byte paramByte, InputStream paramInputStream, BufferedOutputStream paramBufferedOutputStream, long paramLong) throws IOException {
/*  147 */     if (paramByte == 0) {
/*  148 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  154 */     if (paramByte == 2) {
/*      */       
/*  156 */       String str1, str2 = null;
/*  157 */       final InetAddress addr = InetAddress.getByName(this.server);
/*      */       
/*  159 */       PasswordAuthentication passwordAuthentication = AccessController.<PasswordAuthentication>doPrivileged(new PrivilegedAction<PasswordAuthentication>()
/*      */           {
/*      */             public PasswordAuthentication run() {
/*  162 */               return Authenticator.requestPasswordAuthentication(SocksSocketImpl.this
/*  163 */                   .server, addr, SocksSocketImpl.this.serverPort, "SOCKS5", "SOCKS authentication", null);
/*      */             }
/*      */           });
/*  166 */       if (passwordAuthentication != null) {
/*  167 */         str1 = passwordAuthentication.getUserName();
/*  168 */         str2 = new String(passwordAuthentication.getPassword());
/*      */       } else {
/*  170 */         str1 = AccessController.<String>doPrivileged(new GetPropertyAction("user.name"));
/*      */       } 
/*      */       
/*  173 */       if (str1 == null)
/*  174 */         return false; 
/*  175 */       paramBufferedOutputStream.write(1);
/*  176 */       paramBufferedOutputStream.write(str1.length());
/*      */       try {
/*  178 */         paramBufferedOutputStream.write(str1.getBytes("ISO-8859-1"));
/*  179 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */         assert false;
/*      */       } 
/*  182 */       if (str2 != null) {
/*  183 */         paramBufferedOutputStream.write(str2.length());
/*      */         try {
/*  185 */           paramBufferedOutputStream.write(str2.getBytes("ISO-8859-1"));
/*  186 */         } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */           assert false;
/*      */         } 
/*      */       } else {
/*  190 */         paramBufferedOutputStream.write(0);
/*  191 */       }  paramBufferedOutputStream.flush();
/*  192 */       byte[] arrayOfByte = new byte[2];
/*  193 */       int i = readSocksReply(paramInputStream, arrayOfByte, paramLong);
/*  194 */       if (i != 2 || arrayOfByte[1] != 0) {
/*      */ 
/*      */         
/*  197 */         paramBufferedOutputStream.close();
/*  198 */         paramInputStream.close();
/*  199 */         return false;
/*      */       } 
/*      */       
/*  202 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  258 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void connectV4(InputStream paramInputStream, OutputStream paramOutputStream, InetSocketAddress paramInetSocketAddress, long paramLong) throws IOException {
/*  264 */     if (!(paramInetSocketAddress.getAddress() instanceof Inet4Address)) {
/*  265 */       throw new SocketException("SOCKS V4 requires IPv4 only addresses");
/*      */     }
/*  267 */     paramOutputStream.write(4);
/*  268 */     paramOutputStream.write(1);
/*  269 */     paramOutputStream.write(paramInetSocketAddress.getPort() >> 8 & 0xFF);
/*  270 */     paramOutputStream.write(paramInetSocketAddress.getPort() >> 0 & 0xFF);
/*  271 */     paramOutputStream.write(paramInetSocketAddress.getAddress().getAddress());
/*  272 */     String str = getUserName();
/*      */     try {
/*  274 */       paramOutputStream.write(str.getBytes("ISO-8859-1"));
/*  275 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */       assert false;
/*      */     } 
/*  278 */     paramOutputStream.write(0);
/*  279 */     paramOutputStream.flush();
/*  280 */     byte[] arrayOfByte = new byte[8];
/*  281 */     int i = readSocksReply(paramInputStream, arrayOfByte, paramLong);
/*  282 */     if (i != 8)
/*  283 */       throw new SocketException("Reply from SOCKS server has bad length: " + i); 
/*  284 */     if (arrayOfByte[0] != 0 && arrayOfByte[0] != 4)
/*  285 */       throw new SocketException("Reply from SOCKS server has bad version"); 
/*  286 */     SocketException socketException = null;
/*  287 */     switch (arrayOfByte[1]) {
/*      */       
/*      */       case 90:
/*  290 */         this.external_address = paramInetSocketAddress;
/*      */         break;
/*      */       case 91:
/*  293 */         socketException = new SocketException("SOCKS request rejected");
/*      */         break;
/*      */       case 92:
/*  296 */         socketException = new SocketException("SOCKS server couldn't reach destination");
/*      */         break;
/*      */       case 93:
/*  299 */         socketException = new SocketException("SOCKS authentication failed");
/*      */         break;
/*      */       default:
/*  302 */         socketException = new SocketException("Reply from SOCKS server contains bad status");
/*      */         break;
/*      */     } 
/*  305 */     if (socketException != null) {
/*  306 */       paramInputStream.close();
/*  307 */       paramOutputStream.close();
/*  308 */       throw socketException;
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
/*      */   protected void connect(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/*      */     long l;
/*      */     byte b, arrayOfByte2[], arrayOfByte3[];
/*  330 */     if (paramInt == 0) {
/*  331 */       l = 0L;
/*      */     } else {
/*  333 */       long l1 = System.currentTimeMillis() + paramInt;
/*  334 */       l = (l1 < 0L) ? Long.MAX_VALUE : l1;
/*      */     } 
/*      */     
/*  337 */     SecurityManager securityManager = System.getSecurityManager();
/*  338 */     if (paramSocketAddress == null || !(paramSocketAddress instanceof InetSocketAddress))
/*  339 */       throw new IllegalArgumentException("Unsupported address type"); 
/*  340 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*  341 */     if (securityManager != null)
/*  342 */       if (inetSocketAddress.isUnresolved()) {
/*  343 */         securityManager.checkConnect(inetSocketAddress.getHostName(), inetSocketAddress
/*  344 */             .getPort());
/*      */       } else {
/*  346 */         securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress
/*  347 */             .getPort());
/*      */       }  
/*  349 */     if (this.server == null) {
/*      */       URI uRI;
/*      */ 
/*      */       
/*  353 */       ProxySelector proxySelector = AccessController.<ProxySelector>doPrivileged(new PrivilegedAction<ProxySelector>()
/*      */           {
/*      */             public ProxySelector run() {
/*  356 */               return ProxySelector.getDefault();
/*      */             }
/*      */           });
/*  359 */       if (proxySelector == null) {
/*      */ 
/*      */ 
/*      */         
/*  363 */         super.connect(inetSocketAddress, remainingMillis(l));
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  368 */       String str = inetSocketAddress.getHostString();
/*      */       
/*  370 */       if (inetSocketAddress.getAddress() instanceof Inet6Address && 
/*  371 */         !str.startsWith("[") && str.indexOf(":") >= 0) {
/*  372 */         str = "[" + str + "]";
/*      */       }
/*      */       try {
/*  375 */         uRI = new URI("socket://" + ParseUtil.encodePath(str) + ":" + inetSocketAddress.getPort());
/*  376 */       } catch (URISyntaxException uRISyntaxException) {
/*      */         
/*  378 */         assert false : uRISyntaxException;
/*  379 */         uRI = null;
/*      */       } 
/*  381 */       Proxy proxy = null;
/*  382 */       IOException iOException = null;
/*  383 */       Iterator<Proxy> iterator = null;
/*  384 */       iterator = proxySelector.select(uRI).iterator();
/*  385 */       if (iterator == null || !iterator.hasNext()) {
/*  386 */         super.connect(inetSocketAddress, remainingMillis(l));
/*      */         return;
/*      */       } 
/*  389 */       while (iterator.hasNext()) {
/*  390 */         proxy = iterator.next();
/*  391 */         if (proxy == null || proxy.type() != Proxy.Type.SOCKS) {
/*  392 */           super.connect(inetSocketAddress, remainingMillis(l));
/*      */           
/*      */           return;
/*      */         } 
/*  396 */         if (!(proxy.address() instanceof InetSocketAddress)) {
/*  397 */           throw new SocketException("Unknown address type for proxy: " + proxy);
/*      */         }
/*  399 */         this.server = ((InetSocketAddress)proxy.address()).getHostString();
/*  400 */         this.serverPort = ((InetSocketAddress)proxy.address()).getPort();
/*  401 */         if (proxy instanceof SocksProxy && (
/*  402 */           (SocksProxy)proxy).protocolVersion() == 4) {
/*  403 */           this.useV4 = true;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  409 */           privilegedConnect(this.server, this.serverPort, remainingMillis(l));
/*      */           
/*      */           break;
/*  412 */         } catch (IOException iOException1) {
/*      */           
/*  414 */           proxySelector.connectFailed(uRI, proxy.address(), iOException1);
/*  415 */           this.server = null;
/*  416 */           this.serverPort = -1;
/*  417 */           iOException = iOException1;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  426 */       if (this.server == null) {
/*  427 */         throw new SocketException("Can't connect to SOCKS proxy:" + iOException
/*  428 */             .getMessage());
/*      */       }
/*      */     } else {
/*      */       
/*      */       try {
/*  433 */         privilegedConnect(this.server, this.serverPort, remainingMillis(l));
/*  434 */       } catch (IOException iOException) {
/*  435 */         throw new SocketException(iOException.getMessage());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  440 */     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.cmdOut, 512);
/*  441 */     InputStream inputStream = this.cmdIn;
/*      */     
/*  443 */     if (this.useV4) {
/*      */ 
/*      */       
/*  446 */       if (inetSocketAddress.isUnresolved())
/*  447 */         throw new UnknownHostException(inetSocketAddress.toString()); 
/*  448 */       connectV4(inputStream, bufferedOutputStream, inetSocketAddress, l);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  453 */     bufferedOutputStream.write(5);
/*  454 */     bufferedOutputStream.write(2);
/*  455 */     bufferedOutputStream.write(0);
/*  456 */     bufferedOutputStream.write(2);
/*  457 */     bufferedOutputStream.flush();
/*  458 */     byte[] arrayOfByte1 = new byte[2];
/*  459 */     int i = readSocksReply(inputStream, arrayOfByte1, l);
/*  460 */     if (i != 2 || arrayOfByte1[0] != 5) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  465 */       if (inetSocketAddress.isUnresolved())
/*  466 */         throw new UnknownHostException(inetSocketAddress.toString()); 
/*  467 */       connectV4(inputStream, bufferedOutputStream, inetSocketAddress, l);
/*      */       return;
/*      */     } 
/*  470 */     if (arrayOfByte1[1] == -1)
/*  471 */       throw new SocketException("SOCKS : No acceptable methods"); 
/*  472 */     if (!authenticate(arrayOfByte1[1], inputStream, bufferedOutputStream, l)) {
/*  473 */       throw new SocketException("SOCKS : authentication failed");
/*      */     }
/*  475 */     bufferedOutputStream.write(5);
/*  476 */     bufferedOutputStream.write(1);
/*  477 */     bufferedOutputStream.write(0);
/*      */     
/*  479 */     if (inetSocketAddress.isUnresolved()) {
/*  480 */       bufferedOutputStream.write(3);
/*  481 */       bufferedOutputStream.write(inetSocketAddress.getHostName().length());
/*      */       try {
/*  483 */         bufferedOutputStream.write(inetSocketAddress.getHostName().getBytes("ISO-8859-1"));
/*  484 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */         assert false;
/*      */       } 
/*  487 */       bufferedOutputStream.write(inetSocketAddress.getPort() >> 8 & 0xFF);
/*  488 */       bufferedOutputStream.write(inetSocketAddress.getPort() >> 0 & 0xFF);
/*  489 */     } else if (inetSocketAddress.getAddress() instanceof Inet6Address) {
/*  490 */       bufferedOutputStream.write(4);
/*  491 */       bufferedOutputStream.write(inetSocketAddress.getAddress().getAddress());
/*  492 */       bufferedOutputStream.write(inetSocketAddress.getPort() >> 8 & 0xFF);
/*  493 */       bufferedOutputStream.write(inetSocketAddress.getPort() >> 0 & 0xFF);
/*      */     } else {
/*  495 */       bufferedOutputStream.write(1);
/*  496 */       bufferedOutputStream.write(inetSocketAddress.getAddress().getAddress());
/*  497 */       bufferedOutputStream.write(inetSocketAddress.getPort() >> 8 & 0xFF);
/*  498 */       bufferedOutputStream.write(inetSocketAddress.getPort() >> 0 & 0xFF);
/*      */     } 
/*  500 */     bufferedOutputStream.flush();
/*  501 */     arrayOfByte1 = new byte[4];
/*  502 */     i = readSocksReply(inputStream, arrayOfByte1, l);
/*  503 */     if (i != 4)
/*  504 */       throw new SocketException("Reply from SOCKS server has bad length"); 
/*  505 */     SocketException socketException = null;
/*      */ 
/*      */     
/*  508 */     switch (arrayOfByte1[1]) {
/*      */       
/*      */       case 0:
/*  511 */         switch (arrayOfByte1[3]) {
/*      */           case 1:
/*  513 */             arrayOfByte2 = new byte[4];
/*  514 */             i = readSocksReply(inputStream, arrayOfByte2, l);
/*  515 */             if (i != 4)
/*  516 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  517 */             arrayOfByte1 = new byte[2];
/*  518 */             i = readSocksReply(inputStream, arrayOfByte1, l);
/*  519 */             if (i != 2)
/*  520 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*      */             break;
/*      */           case 3:
/*  523 */             b = arrayOfByte1[1];
/*  524 */             arrayOfByte3 = new byte[b];
/*  525 */             i = readSocksReply(inputStream, arrayOfByte3, l);
/*  526 */             if (i != b)
/*  527 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  528 */             arrayOfByte1 = new byte[2];
/*  529 */             i = readSocksReply(inputStream, arrayOfByte1, l);
/*  530 */             if (i != 2)
/*  531 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*      */             break;
/*      */           case 4:
/*  534 */             b = arrayOfByte1[1];
/*  535 */             arrayOfByte2 = new byte[b];
/*  536 */             i = readSocksReply(inputStream, arrayOfByte2, l);
/*  537 */             if (i != b)
/*  538 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  539 */             arrayOfByte1 = new byte[2];
/*  540 */             i = readSocksReply(inputStream, arrayOfByte1, l);
/*  541 */             if (i != 2)
/*  542 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*      */             break;
/*      */         } 
/*  545 */         socketException = new SocketException("Reply from SOCKS server contains wrong code");
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/*  550 */         socketException = new SocketException("SOCKS server general failure");
/*      */         break;
/*      */       case 2:
/*  553 */         socketException = new SocketException("SOCKS: Connection not allowed by ruleset");
/*      */         break;
/*      */       case 3:
/*  556 */         socketException = new SocketException("SOCKS: Network unreachable");
/*      */         break;
/*      */       case 4:
/*  559 */         socketException = new SocketException("SOCKS: Host unreachable");
/*      */         break;
/*      */       case 5:
/*  562 */         socketException = new SocketException("SOCKS: Connection refused");
/*      */         break;
/*      */       case 6:
/*  565 */         socketException = new SocketException("SOCKS: TTL expired");
/*      */         break;
/*      */       case 7:
/*  568 */         socketException = new SocketException("SOCKS: Command not supported");
/*      */         break;
/*      */       case 8:
/*  571 */         socketException = new SocketException("SOCKS: address type not supported");
/*      */         break;
/*      */     } 
/*  574 */     if (socketException != null) {
/*  575 */       inputStream.close();
/*  576 */       bufferedOutputStream.close();
/*  577 */       throw socketException;
/*      */     } 
/*  579 */     this.external_address = inetSocketAddress;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void bindV4(InputStream paramInputStream, OutputStream paramOutputStream, InetAddress paramInetAddress, int paramInt) throws IOException {
/*  585 */     if (!(paramInetAddress instanceof Inet4Address)) {
/*  586 */       throw new SocketException("SOCKS V4 requires IPv4 only addresses");
/*      */     }
/*  588 */     bind(paramInetAddress, paramInt);
/*  589 */     byte[] arrayOfByte1 = paramInetAddress.getAddress();
/*      */     
/*  591 */     InetAddress inetAddress = paramInetAddress;
/*  592 */     if (inetAddress.isAnyLocalAddress()) {
/*  593 */       inetAddress = AccessController.<InetAddress>doPrivileged(new PrivilegedAction<InetAddress>()
/*      */           {
/*      */             public InetAddress run() {
/*  596 */               return SocksSocketImpl.this.cmdsock.getLocalAddress();
/*      */             }
/*      */           });
/*      */       
/*  600 */       arrayOfByte1 = inetAddress.getAddress();
/*      */     } 
/*  602 */     paramOutputStream.write(4);
/*  603 */     paramOutputStream.write(2);
/*  604 */     paramOutputStream.write(super.getLocalPort() >> 8 & 0xFF);
/*  605 */     paramOutputStream.write(super.getLocalPort() >> 0 & 0xFF);
/*  606 */     paramOutputStream.write(arrayOfByte1);
/*  607 */     String str = getUserName();
/*      */     try {
/*  609 */       paramOutputStream.write(str.getBytes("ISO-8859-1"));
/*  610 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */       assert false;
/*      */     } 
/*  613 */     paramOutputStream.write(0);
/*  614 */     paramOutputStream.flush();
/*  615 */     byte[] arrayOfByte2 = new byte[8];
/*  616 */     int i = readSocksReply(paramInputStream, arrayOfByte2);
/*  617 */     if (i != 8)
/*  618 */       throw new SocketException("Reply from SOCKS server has bad length: " + i); 
/*  619 */     if (arrayOfByte2[0] != 0 && arrayOfByte2[0] != 4)
/*  620 */       throw new SocketException("Reply from SOCKS server has bad version"); 
/*  621 */     SocketException socketException = null;
/*  622 */     switch (arrayOfByte2[1]) {
/*      */       
/*      */       case 90:
/*  625 */         this.external_address = new InetSocketAddress(paramInetAddress, paramInt);
/*      */         break;
/*      */       case 91:
/*  628 */         socketException = new SocketException("SOCKS request rejected");
/*      */         break;
/*      */       case 92:
/*  631 */         socketException = new SocketException("SOCKS server couldn't reach destination");
/*      */         break;
/*      */       case 93:
/*  634 */         socketException = new SocketException("SOCKS authentication failed");
/*      */         break;
/*      */       default:
/*  637 */         socketException = new SocketException("Reply from SOCKS server contains bad status");
/*      */         break;
/*      */     } 
/*  640 */     if (socketException != null) {
/*  641 */       paramInputStream.close();
/*  642 */       paramOutputStream.close();
/*  643 */       throw socketException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void socksBind(InetSocketAddress paramInetSocketAddress) throws IOException {
/*      */     byte b;
/*      */     int k;
/*      */     byte[] arrayOfByte2, arrayOfByte3;
/*  657 */     if (this.socket != null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  665 */     if (this.server == null) {
/*      */       URI uRI;
/*      */ 
/*      */       
/*  669 */       ProxySelector proxySelector = AccessController.<ProxySelector>doPrivileged(new PrivilegedAction<ProxySelector>()
/*      */           {
/*      */             public ProxySelector run() {
/*  672 */               return ProxySelector.getDefault();
/*      */             }
/*      */           });
/*  675 */       if (proxySelector == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  683 */       String str = paramInetSocketAddress.getHostString();
/*      */       
/*  685 */       if (paramInetSocketAddress.getAddress() instanceof Inet6Address && 
/*  686 */         !str.startsWith("[") && str.indexOf(":") >= 0) {
/*  687 */         str = "[" + str + "]";
/*      */       }
/*      */       try {
/*  690 */         uRI = new URI("serversocket://" + ParseUtil.encodePath(str) + ":" + paramInetSocketAddress.getPort());
/*  691 */       } catch (URISyntaxException uRISyntaxException) {
/*      */         
/*  693 */         assert false : uRISyntaxException;
/*  694 */         uRI = null;
/*      */       } 
/*  696 */       Proxy proxy = null;
/*  697 */       Exception exception = null;
/*  698 */       Iterator<Proxy> iterator = null;
/*  699 */       iterator = proxySelector.select(uRI).iterator();
/*  700 */       if (iterator == null || !iterator.hasNext()) {
/*      */         return;
/*      */       }
/*  703 */       while (iterator.hasNext()) {
/*  704 */         proxy = iterator.next();
/*  705 */         if (proxy == null || proxy.type() != Proxy.Type.SOCKS) {
/*      */           return;
/*      */         }
/*      */         
/*  709 */         if (!(proxy.address() instanceof InetSocketAddress)) {
/*  710 */           throw new SocketException("Unknown address type for proxy: " + proxy);
/*      */         }
/*  712 */         this.server = ((InetSocketAddress)proxy.address()).getHostString();
/*  713 */         this.serverPort = ((InetSocketAddress)proxy.address()).getPort();
/*  714 */         if (proxy instanceof SocksProxy && (
/*  715 */           (SocksProxy)proxy).protocolVersion() == 4) {
/*  716 */           this.useV4 = true;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  722 */           AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */               {
/*      */                 public Void run() throws Exception {
/*  725 */                   SocksSocketImpl.this.cmdsock = new Socket(new PlainSocketImpl());
/*  726 */                   SocksSocketImpl.this.cmdsock.connect(new InetSocketAddress(SocksSocketImpl.this.server, SocksSocketImpl.this.serverPort));
/*  727 */                   SocksSocketImpl.this.cmdIn = SocksSocketImpl.this.cmdsock.getInputStream();
/*  728 */                   SocksSocketImpl.this.cmdOut = SocksSocketImpl.this.cmdsock.getOutputStream();
/*  729 */                   return null;
/*      */                 }
/*      */               });
/*  732 */         } catch (Exception exception1) {
/*      */           
/*  734 */           proxySelector.connectFailed(uRI, proxy.address(), new SocketException(exception1.getMessage()));
/*  735 */           this.server = null;
/*  736 */           this.serverPort = -1;
/*  737 */           this.cmdsock = null;
/*  738 */           exception = exception1;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  747 */       if (this.server == null || this.cmdsock == null) {
/*  748 */         throw new SocketException("Can't connect to SOCKS proxy:" + exception
/*  749 */             .getMessage());
/*      */       }
/*      */     } else {
/*      */       try {
/*  753 */         AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */             {
/*      */               public Void run() throws Exception {
/*  756 */                 SocksSocketImpl.this.cmdsock = new Socket(new PlainSocketImpl());
/*  757 */                 SocksSocketImpl.this.cmdsock.connect(new InetSocketAddress(SocksSocketImpl.this.server, SocksSocketImpl.this.serverPort));
/*  758 */                 SocksSocketImpl.this.cmdIn = SocksSocketImpl.this.cmdsock.getInputStream();
/*  759 */                 SocksSocketImpl.this.cmdOut = SocksSocketImpl.this.cmdsock.getOutputStream();
/*  760 */                 return null;
/*      */               }
/*      */             });
/*  763 */       } catch (Exception exception) {
/*  764 */         throw new SocketException(exception.getMessage());
/*      */       } 
/*      */     } 
/*  767 */     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.cmdOut, 512);
/*  768 */     InputStream inputStream = this.cmdIn;
/*  769 */     if (this.useV4) {
/*  770 */       bindV4(inputStream, bufferedOutputStream, paramInetSocketAddress.getAddress(), paramInetSocketAddress.getPort());
/*      */       return;
/*      */     } 
/*  773 */     bufferedOutputStream.write(5);
/*  774 */     bufferedOutputStream.write(2);
/*  775 */     bufferedOutputStream.write(0);
/*  776 */     bufferedOutputStream.write(2);
/*  777 */     bufferedOutputStream.flush();
/*  778 */     byte[] arrayOfByte1 = new byte[2];
/*  779 */     int i = readSocksReply(inputStream, arrayOfByte1);
/*  780 */     if (i != 2 || arrayOfByte1[0] != 5) {
/*      */ 
/*      */       
/*  783 */       bindV4(inputStream, bufferedOutputStream, paramInetSocketAddress.getAddress(), paramInetSocketAddress.getPort());
/*      */       return;
/*      */     } 
/*  786 */     if (arrayOfByte1[1] == -1)
/*  787 */       throw new SocketException("SOCKS : No acceptable methods"); 
/*  788 */     if (!authenticate(arrayOfByte1[1], inputStream, bufferedOutputStream)) {
/*  789 */       throw new SocketException("SOCKS : authentication failed");
/*      */     }
/*      */     
/*  792 */     bufferedOutputStream.write(5);
/*  793 */     bufferedOutputStream.write(2);
/*  794 */     bufferedOutputStream.write(0);
/*  795 */     int j = paramInetSocketAddress.getPort();
/*  796 */     if (paramInetSocketAddress.isUnresolved()) {
/*  797 */       bufferedOutputStream.write(3);
/*  798 */       bufferedOutputStream.write(paramInetSocketAddress.getHostName().length());
/*      */       try {
/*  800 */         bufferedOutputStream.write(paramInetSocketAddress.getHostName().getBytes("ISO-8859-1"));
/*  801 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */         assert false;
/*      */       } 
/*  804 */       bufferedOutputStream.write(j >> 8 & 0xFF);
/*  805 */       bufferedOutputStream.write(j >> 0 & 0xFF);
/*  806 */     } else if (paramInetSocketAddress.getAddress() instanceof Inet4Address) {
/*  807 */       byte[] arrayOfByte = paramInetSocketAddress.getAddress().getAddress();
/*  808 */       bufferedOutputStream.write(1);
/*  809 */       bufferedOutputStream.write(arrayOfByte);
/*  810 */       bufferedOutputStream.write(j >> 8 & 0xFF);
/*  811 */       bufferedOutputStream.write(j >> 0 & 0xFF);
/*  812 */       bufferedOutputStream.flush();
/*  813 */     } else if (paramInetSocketAddress.getAddress() instanceof Inet6Address) {
/*  814 */       byte[] arrayOfByte = paramInetSocketAddress.getAddress().getAddress();
/*  815 */       bufferedOutputStream.write(4);
/*  816 */       bufferedOutputStream.write(arrayOfByte);
/*  817 */       bufferedOutputStream.write(j >> 8 & 0xFF);
/*  818 */       bufferedOutputStream.write(j >> 0 & 0xFF);
/*  819 */       bufferedOutputStream.flush();
/*      */     } else {
/*  821 */       this.cmdsock.close();
/*  822 */       throw new SocketException("unsupported address type : " + paramInetSocketAddress);
/*      */     } 
/*  824 */     arrayOfByte1 = new byte[4];
/*  825 */     i = readSocksReply(inputStream, arrayOfByte1);
/*  826 */     SocketException socketException = null;
/*      */ 
/*      */     
/*  829 */     switch (arrayOfByte1[1]) {
/*      */       
/*      */       case 0:
/*  832 */         switch (arrayOfByte1[3]) {
/*      */           case 1:
/*  834 */             arrayOfByte2 = new byte[4];
/*  835 */             i = readSocksReply(inputStream, arrayOfByte2);
/*  836 */             if (i != 4)
/*  837 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  838 */             arrayOfByte1 = new byte[2];
/*  839 */             i = readSocksReply(inputStream, arrayOfByte1);
/*  840 */             if (i != 2)
/*  841 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  842 */             k = (arrayOfByte1[0] & 0xFF) << 8;
/*  843 */             k += arrayOfByte1[1] & 0xFF;
/*  844 */             this.external_address = new InetSocketAddress(new Inet4Address("", arrayOfByte2), k);
/*      */             break;
/*      */           
/*      */           case 3:
/*  848 */             b = arrayOfByte1[1];
/*  849 */             arrayOfByte3 = new byte[b];
/*  850 */             i = readSocksReply(inputStream, arrayOfByte3);
/*  851 */             if (i != b)
/*  852 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  853 */             arrayOfByte1 = new byte[2];
/*  854 */             i = readSocksReply(inputStream, arrayOfByte1);
/*  855 */             if (i != 2)
/*  856 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  857 */             k = (arrayOfByte1[0] & 0xFF) << 8;
/*  858 */             k += arrayOfByte1[1] & 0xFF;
/*  859 */             this.external_address = new InetSocketAddress(new String(arrayOfByte3), k);
/*      */             break;
/*      */           case 4:
/*  862 */             b = arrayOfByte1[1];
/*  863 */             arrayOfByte2 = new byte[b];
/*  864 */             i = readSocksReply(inputStream, arrayOfByte2);
/*  865 */             if (i != b)
/*  866 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  867 */             arrayOfByte1 = new byte[2];
/*  868 */             i = readSocksReply(inputStream, arrayOfByte1);
/*  869 */             if (i != 2)
/*  870 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  871 */             k = (arrayOfByte1[0] & 0xFF) << 8;
/*  872 */             k += arrayOfByte1[1] & 0xFF;
/*  873 */             this.external_address = new InetSocketAddress(new Inet6Address("", arrayOfByte2), k);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       case 1:
/*  879 */         socketException = new SocketException("SOCKS server general failure");
/*      */         break;
/*      */       case 2:
/*  882 */         socketException = new SocketException("SOCKS: Bind not allowed by ruleset");
/*      */         break;
/*      */       case 3:
/*  885 */         socketException = new SocketException("SOCKS: Network unreachable");
/*      */         break;
/*      */       case 4:
/*  888 */         socketException = new SocketException("SOCKS: Host unreachable");
/*      */         break;
/*      */       case 5:
/*  891 */         socketException = new SocketException("SOCKS: Connection refused");
/*      */         break;
/*      */       case 6:
/*  894 */         socketException = new SocketException("SOCKS: TTL expired");
/*      */         break;
/*      */       case 7:
/*  897 */         socketException = new SocketException("SOCKS: Command not supported");
/*      */         break;
/*      */       case 8:
/*  900 */         socketException = new SocketException("SOCKS: address type not supported");
/*      */         break;
/*      */     } 
/*  903 */     if (socketException != null) {
/*  904 */       inputStream.close();
/*  905 */       bufferedOutputStream.close();
/*  906 */       this.cmdsock.close();
/*  907 */       this.cmdsock = null;
/*  908 */       throw socketException;
/*      */     } 
/*  910 */     this.cmdIn = inputStream;
/*  911 */     this.cmdOut = bufferedOutputStream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void acceptFrom(SocketImpl paramSocketImpl, InetSocketAddress paramInetSocketAddress) throws IOException {
/*      */     int j;
/*      */     byte[] arrayOfByte;
/*      */     int k;
/*  924 */     if (this.cmdsock == null) {
/*      */       return;
/*      */     }
/*      */     
/*  928 */     InputStream inputStream = this.cmdIn;
/*      */     
/*  930 */     socksBind(paramInetSocketAddress);
/*  931 */     inputStream.read();
/*  932 */     int i = inputStream.read();
/*  933 */     inputStream.read();
/*  934 */     SocketException socketException = null;
/*      */ 
/*      */     
/*  937 */     InetSocketAddress inetSocketAddress = null;
/*  938 */     switch (i) {
/*      */       
/*      */       case 0:
/*  941 */         i = inputStream.read();
/*  942 */         switch (i) {
/*      */           case 1:
/*  944 */             arrayOfByte = new byte[4];
/*  945 */             readSocksReply(inputStream, arrayOfByte);
/*  946 */             j = inputStream.read() << 8;
/*  947 */             j += inputStream.read();
/*  948 */             inetSocketAddress = new InetSocketAddress(new Inet4Address("", arrayOfByte), j);
/*      */             break;
/*      */           
/*      */           case 3:
/*  952 */             k = inputStream.read();
/*  953 */             arrayOfByte = new byte[k];
/*  954 */             readSocksReply(inputStream, arrayOfByte);
/*  955 */             j = inputStream.read() << 8;
/*  956 */             j += inputStream.read();
/*  957 */             inetSocketAddress = new InetSocketAddress(new String(arrayOfByte), j);
/*      */             break;
/*      */           case 4:
/*  960 */             arrayOfByte = new byte[16];
/*  961 */             readSocksReply(inputStream, arrayOfByte);
/*  962 */             j = inputStream.read() << 8;
/*  963 */             j += inputStream.read();
/*  964 */             inetSocketAddress = new InetSocketAddress(new Inet6Address("", arrayOfByte), j);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       case 1:
/*  970 */         socketException = new SocketException("SOCKS server general failure");
/*      */         break;
/*      */       case 2:
/*  973 */         socketException = new SocketException("SOCKS: Accept not allowed by ruleset");
/*      */         break;
/*      */       case 3:
/*  976 */         socketException = new SocketException("SOCKS: Network unreachable");
/*      */         break;
/*      */       case 4:
/*  979 */         socketException = new SocketException("SOCKS: Host unreachable");
/*      */         break;
/*      */       case 5:
/*  982 */         socketException = new SocketException("SOCKS: Connection refused");
/*      */         break;
/*      */       case 6:
/*  985 */         socketException = new SocketException("SOCKS: TTL expired");
/*      */         break;
/*      */       case 7:
/*  988 */         socketException = new SocketException("SOCKS: Command not supported");
/*      */         break;
/*      */       case 8:
/*  991 */         socketException = new SocketException("SOCKS: address type not supported");
/*      */         break;
/*      */     } 
/*  994 */     if (socketException != null) {
/*  995 */       this.cmdIn.close();
/*  996 */       this.cmdOut.close();
/*  997 */       this.cmdsock.close();
/*  998 */       this.cmdsock = null;
/*  999 */       throw socketException;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1007 */     if (paramSocketImpl instanceof SocksSocketImpl) {
/* 1008 */       ((SocksSocketImpl)paramSocketImpl).external_address = inetSocketAddress;
/*      */     }
/* 1010 */     if (paramSocketImpl instanceof PlainSocketImpl) {
/* 1011 */       PlainSocketImpl plainSocketImpl = (PlainSocketImpl)paramSocketImpl;
/* 1012 */       plainSocketImpl.setInputStream((SocketInputStream)inputStream);
/* 1013 */       plainSocketImpl.setFileDescriptor(this.cmdsock.getImpl().getFileDescriptor());
/* 1014 */       plainSocketImpl.setAddress(this.cmdsock.getImpl().getInetAddress());
/* 1015 */       plainSocketImpl.setPort(this.cmdsock.getImpl().getPort());
/* 1016 */       plainSocketImpl.setLocalPort(this.cmdsock.getImpl().getLocalPort());
/*      */     } else {
/* 1018 */       paramSocketImpl.fd = (this.cmdsock.getImpl()).fd;
/* 1019 */       paramSocketImpl.address = (this.cmdsock.getImpl()).address;
/* 1020 */       paramSocketImpl.port = (this.cmdsock.getImpl()).port;
/* 1021 */       paramSocketImpl.localport = (this.cmdsock.getImpl()).localport;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1028 */     this.cmdsock = null;
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
/*      */   protected InetAddress getInetAddress() {
/* 1040 */     if (this.external_address != null) {
/* 1041 */       return this.external_address.getAddress();
/*      */     }
/* 1043 */     return super.getInetAddress();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getPort() {
/* 1054 */     if (this.external_address != null) {
/* 1055 */       return this.external_address.getPort();
/*      */     }
/* 1057 */     return super.getPort();
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getLocalPort() {
/* 1062 */     if (this.socket != null)
/* 1063 */       return super.getLocalPort(); 
/* 1064 */     if (this.external_address != null) {
/* 1065 */       return this.external_address.getPort();
/*      */     }
/* 1067 */     return super.getLocalPort();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void close() throws IOException {
/* 1072 */     if (this.cmdsock != null)
/* 1073 */       this.cmdsock.close(); 
/* 1074 */     this.cmdsock = null;
/* 1075 */     super.close();
/*      */   }
/*      */   
/*      */   private String getUserName() {
/* 1079 */     String str = "";
/* 1080 */     if (this.applicationSetProxy) {
/*      */       try {
/* 1082 */         str = System.getProperty("user.name");
/* 1083 */       } catch (SecurityException securityException) {}
/*      */     } else {
/* 1085 */       str = AccessController.<String>doPrivileged(new GetPropertyAction("user.name"));
/*      */     } 
/*      */     
/* 1088 */     return str;
/*      */   }
/*      */   
/*      */   SocksSocketImpl() {}
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\net\SocksSocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */