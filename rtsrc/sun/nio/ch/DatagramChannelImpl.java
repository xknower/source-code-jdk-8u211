/*      */ package sun.nio.ch;
/*      */ 
/*      */ import java.io.FileDescriptor;
/*      */ import java.io.IOException;
/*      */ import java.net.DatagramSocket;
/*      */ import java.net.Inet4Address;
/*      */ import java.net.InetAddress;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.NetworkInterface;
/*      */ import java.net.PortUnreachableException;
/*      */ import java.net.ProtocolFamily;
/*      */ import java.net.SocketAddress;
/*      */ import java.net.SocketOption;
/*      */ import java.net.StandardProtocolFamily;
/*      */ import java.net.StandardSocketOptions;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.channels.AlreadyBoundException;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.DatagramChannel;
/*      */ import java.nio.channels.MembershipKey;
/*      */ import java.nio.channels.NetworkChannel;
/*      */ import java.nio.channels.NotYetConnectedException;
/*      */ import java.nio.channels.UnsupportedAddressTypeException;
/*      */ import java.nio.channels.spi.SelectorProvider;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Set;
/*      */ import jdk.net.ExtendedSocketOptions;
/*      */ import sun.net.ExtendedOptionsImpl;
/*      */ import sun.net.ResourceManager;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class DatagramChannelImpl
/*      */   extends DatagramChannel
/*      */   implements SelChImpl
/*      */ {
/*   48 */   private static NativeDispatcher nd = new DatagramDispatcher();
/*      */ 
/*      */ 
/*      */   
/*      */   private final FileDescriptor fd;
/*      */ 
/*      */   
/*      */   private final int fdVal;
/*      */ 
/*      */   
/*      */   private final ProtocolFamily family;
/*      */ 
/*      */   
/*   61 */   private volatile long readerThread = 0L;
/*   62 */   private volatile long writerThread = 0L;
/*      */ 
/*      */   
/*      */   private InetAddress cachedSenderInetAddress;
/*      */ 
/*      */   
/*      */   private int cachedSenderPort;
/*      */   
/*   70 */   private final Object readLock = new Object();
/*      */ 
/*      */   
/*   73 */   private final Object writeLock = new Object();
/*      */ 
/*      */ 
/*      */   
/*   77 */   private final Object stateLock = new Object();
/*      */   
/*      */   private static final int ST_UNINITIALIZED = -1;
/*      */   
/*      */   private static final int ST_UNCONNECTED = 0;
/*      */   
/*      */   private static final int ST_CONNECTED = 1;
/*      */   
/*      */   private static final int ST_KILLED = 2;
/*   86 */   private int state = -1;
/*      */ 
/*      */   
/*      */   private InetSocketAddress localAddress;
/*      */ 
/*      */   
/*      */   private InetSocketAddress remoteAddress;
/*      */ 
/*      */   
/*      */   private DatagramSocket socket;
/*      */ 
/*      */   
/*      */   private MembershipRegistry registry;
/*      */ 
/*      */   
/*      */   private boolean reuseAddressEmulated;
/*      */ 
/*      */   
/*      */   private boolean isReuseAddress;
/*      */   
/*      */   private SocketAddress sender;
/*      */ 
/*      */   
/*      */   public DatagramChannelImpl(SelectorProvider paramSelectorProvider) throws IOException {
/*  110 */     super(paramSelectorProvider);
/*  111 */     ResourceManager.beforeUdpCreate();
/*      */     try {
/*  113 */       this.family = Net.isIPv6Available() ? StandardProtocolFamily.INET6 : StandardProtocolFamily.INET;
/*      */       
/*  115 */       this.fd = Net.socket(this.family, false);
/*  116 */       this.fdVal = IOUtil.fdVal(this.fd);
/*  117 */       this.state = 0;
/*  118 */     } catch (IOException iOException) {
/*  119 */       ResourceManager.afterUdpClose();
/*  120 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DatagramChannelImpl(SelectorProvider paramSelectorProvider, ProtocolFamily paramProtocolFamily) throws IOException {
/*  127 */     super(paramSelectorProvider);
/*  128 */     if (paramProtocolFamily != StandardProtocolFamily.INET && paramProtocolFamily != StandardProtocolFamily.INET6) {
/*      */ 
/*      */       
/*  131 */       if (paramProtocolFamily == null) {
/*  132 */         throw new NullPointerException("'family' is null");
/*      */       }
/*  134 */       throw new UnsupportedOperationException("Protocol family not supported");
/*      */     } 
/*  136 */     if (paramProtocolFamily == StandardProtocolFamily.INET6 && 
/*  137 */       !Net.isIPv6Available()) {
/*  138 */       throw new UnsupportedOperationException("IPv6 not available");
/*      */     }
/*      */     
/*  141 */     this.family = paramProtocolFamily;
/*  142 */     this.fd = Net.socket(paramProtocolFamily, false);
/*  143 */     this.fdVal = IOUtil.fdVal(this.fd);
/*  144 */     this.state = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DatagramChannelImpl(SelectorProvider paramSelectorProvider, FileDescriptor paramFileDescriptor) throws IOException {
/*  150 */     super(paramSelectorProvider);
/*  151 */     this.family = Net.isIPv6Available() ? StandardProtocolFamily.INET6 : StandardProtocolFamily.INET;
/*      */     
/*  153 */     this.fd = paramFileDescriptor;
/*  154 */     this.fdVal = IOUtil.fdVal(paramFileDescriptor);
/*  155 */     this.state = 0;
/*  156 */     this.localAddress = Net.localAddress(paramFileDescriptor);
/*      */   }
/*      */   
/*      */   public DatagramSocket socket() {
/*  160 */     synchronized (this.stateLock) {
/*  161 */       if (this.socket == null)
/*  162 */         this.socket = DatagramSocketAdaptor.create(this); 
/*  163 */       return this.socket;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public SocketAddress getLocalAddress() throws IOException {
/*  169 */     synchronized (this.stateLock) {
/*  170 */       if (!isOpen()) {
/*  171 */         throw new ClosedChannelException();
/*      */       }
/*  173 */       return Net.getRevealedLocalAddress(this.localAddress);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public SocketAddress getRemoteAddress() throws IOException {
/*  179 */     synchronized (this.stateLock) {
/*  180 */       if (!isOpen())
/*  181 */         throw new ClosedChannelException(); 
/*  182 */       return this.remoteAddress;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> DatagramChannel setOption(SocketOption<T> paramSocketOption, T paramT) throws IOException {
/*  190 */     if (paramSocketOption == null)
/*  191 */       throw new NullPointerException(); 
/*  192 */     if (!supportedOptions().contains(paramSocketOption)) {
/*  193 */       throw new UnsupportedOperationException("'" + paramSocketOption + "' not supported");
/*      */     }
/*  195 */     synchronized (this.stateLock) {
/*  196 */       ensureOpen();
/*      */       
/*  198 */       if (paramSocketOption == StandardSocketOptions.IP_TOS || paramSocketOption == StandardSocketOptions.IP_MULTICAST_TTL || paramSocketOption == StandardSocketOptions.IP_MULTICAST_LOOP) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  203 */         Net.setSocketOption(this.fd, this.family, paramSocketOption, paramT);
/*  204 */         return this;
/*      */       } 
/*      */       
/*  207 */       if (paramSocketOption == StandardSocketOptions.IP_MULTICAST_IF) {
/*  208 */         if (paramT == null)
/*  209 */           throw new IllegalArgumentException("Cannot set IP_MULTICAST_IF to 'null'"); 
/*  210 */         NetworkInterface networkInterface = (NetworkInterface)paramT;
/*  211 */         if (this.family == StandardProtocolFamily.INET6) {
/*  212 */           int i = networkInterface.getIndex();
/*  213 */           if (i == -1)
/*  214 */             throw new IOException("Network interface cannot be identified"); 
/*  215 */           Net.setInterface6(this.fd, i);
/*      */         } else {
/*      */           
/*  218 */           Inet4Address inet4Address = Net.anyInet4Address(networkInterface);
/*  219 */           if (inet4Address == null)
/*  220 */             throw new IOException("Network interface not configured for IPv4"); 
/*  221 */           int i = Net.inet4AsInt(inet4Address);
/*  222 */           Net.setInterface4(this.fd, i);
/*      */         } 
/*  224 */         return this;
/*      */       } 
/*  226 */       if (paramSocketOption == StandardSocketOptions.SO_REUSEADDR && 
/*  227 */         Net.useExclusiveBind() && this.localAddress != null) {
/*      */         
/*  229 */         this.reuseAddressEmulated = true;
/*  230 */         this.isReuseAddress = ((Boolean)paramT).booleanValue();
/*      */       } 
/*      */ 
/*      */       
/*  234 */       Net.setSocketOption(this.fd, Net.UNSPEC, paramSocketOption, paramT);
/*  235 */       return this;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T getOption(SocketOption<T> paramSocketOption) throws IOException {
/*  244 */     if (paramSocketOption == null)
/*  245 */       throw new NullPointerException(); 
/*  246 */     if (!supportedOptions().contains(paramSocketOption)) {
/*  247 */       throw new UnsupportedOperationException("'" + paramSocketOption + "' not supported");
/*      */     }
/*  249 */     synchronized (this.stateLock) {
/*  250 */       ensureOpen();
/*      */       
/*  252 */       if (paramSocketOption == StandardSocketOptions.IP_TOS || paramSocketOption == StandardSocketOptions.IP_MULTICAST_TTL || paramSocketOption == StandardSocketOptions.IP_MULTICAST_LOOP)
/*      */       {
/*      */ 
/*      */         
/*  256 */         return (T)Net.getSocketOption(this.fd, this.family, paramSocketOption);
/*      */       }
/*      */       
/*  259 */       if (paramSocketOption == StandardSocketOptions.IP_MULTICAST_IF) {
/*  260 */         if (this.family == StandardProtocolFamily.INET) {
/*  261 */           int j = Net.getInterface4(this.fd);
/*  262 */           if (j == 0) {
/*  263 */             return null;
/*      */           }
/*  265 */           InetAddress inetAddress = Net.inet4FromInt(j);
/*  266 */           NetworkInterface networkInterface1 = NetworkInterface.getByInetAddress(inetAddress);
/*  267 */           if (networkInterface1 == null)
/*  268 */             throw new IOException("Unable to map address to interface"); 
/*  269 */           return (T)networkInterface1;
/*      */         } 
/*  271 */         int i = Net.getInterface6(this.fd);
/*  272 */         if (i == 0) {
/*  273 */           return null;
/*      */         }
/*  275 */         NetworkInterface networkInterface = NetworkInterface.getByIndex(i);
/*  276 */         if (networkInterface == null)
/*  277 */           throw new IOException("Unable to map index to interface"); 
/*  278 */         return (T)networkInterface;
/*      */       } 
/*      */ 
/*      */       
/*  282 */       if (paramSocketOption == StandardSocketOptions.SO_REUSEADDR && this.reuseAddressEmulated)
/*      */       {
/*      */         
/*  285 */         return (T)Boolean.valueOf(this.isReuseAddress);
/*      */       }
/*      */ 
/*      */       
/*  289 */       return (T)Net.getSocketOption(this.fd, Net.UNSPEC, paramSocketOption);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static class DefaultOptionsHolder {
/*  294 */     static final Set<SocketOption<?>> defaultOptions = defaultOptions();
/*      */     
/*      */     private static Set<SocketOption<?>> defaultOptions() {
/*  297 */       HashSet<SocketOption<Integer>> hashSet = new HashSet(8);
/*  298 */       hashSet.add(StandardSocketOptions.SO_SNDBUF);
/*  299 */       hashSet.add(StandardSocketOptions.SO_RCVBUF);
/*  300 */       hashSet.add(StandardSocketOptions.SO_REUSEADDR);
/*  301 */       hashSet.add(StandardSocketOptions.SO_BROADCAST);
/*  302 */       hashSet.add(StandardSocketOptions.IP_TOS);
/*  303 */       hashSet.add(StandardSocketOptions.IP_MULTICAST_IF);
/*  304 */       hashSet.add(StandardSocketOptions.IP_MULTICAST_TTL);
/*  305 */       hashSet.add(StandardSocketOptions.IP_MULTICAST_LOOP);
/*  306 */       if (ExtendedOptionsImpl.flowSupported()) {
/*  307 */         hashSet.add(ExtendedSocketOptions.SO_FLOW_SLA);
/*      */       }
/*  309 */       return Collections.unmodifiableSet(hashSet);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public final Set<SocketOption<?>> supportedOptions() {
/*  315 */     return DefaultOptionsHolder.defaultOptions;
/*      */   }
/*      */   
/*      */   private void ensureOpen() throws ClosedChannelException {
/*  319 */     if (!isOpen()) {
/*  320 */       throw new ClosedChannelException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public SocketAddress receive(ByteBuffer paramByteBuffer) throws IOException {
/*  326 */     if (paramByteBuffer.isReadOnly())
/*  327 */       throw new IllegalArgumentException("Read-only buffer"); 
/*  328 */     if (paramByteBuffer == null)
/*  329 */       throw new NullPointerException(); 
/*  330 */     synchronized (this.readLock) {
/*  331 */       ensureOpen();
/*      */       
/*  333 */       if (localAddress() == null)
/*  334 */         bind((SocketAddress)null); 
/*  335 */       int i = 0;
/*  336 */       ByteBuffer byteBuffer = null;
/*      */       try {
/*  338 */         begin();
/*  339 */         if (!isOpen())
/*  340 */           return null; 
/*  341 */         SecurityManager securityManager = System.getSecurityManager();
/*  342 */         this.readerThread = NativeThread.current();
/*  343 */         if (isConnected() || securityManager == null) {
/*      */           do {
/*  345 */             i = receive(this.fd, paramByteBuffer);
/*  346 */           } while (i == -3 && isOpen());
/*  347 */           if (i == -2)
/*  348 */             return null; 
/*      */         } else {
/*  350 */           byteBuffer = Util.getTemporaryDirectBuffer(paramByteBuffer.remaining());
/*      */           
/*      */           while (true) {
/*  353 */             i = receive(this.fd, byteBuffer);
/*  354 */             if (i != -3 || !isOpen()) {
/*  355 */               if (i == -2)
/*  356 */                 return null; 
/*  357 */               InetSocketAddress inetSocketAddress = (InetSocketAddress)this.sender;
/*      */               try {
/*  359 */                 securityManager.checkAccept(inetSocketAddress
/*  360 */                     .getAddress().getHostAddress(), inetSocketAddress
/*  361 */                     .getPort()); break;
/*  362 */               } catch (SecurityException securityException) {
/*      */                 
/*  364 */                 byteBuffer.clear();
/*  365 */                 i = 0;
/*      */               } 
/*      */             } 
/*  368 */           }  byteBuffer.flip();
/*  369 */           paramByteBuffer.put(byteBuffer);
/*      */         } 
/*      */ 
/*      */         
/*  373 */         return this.sender;
/*      */       } finally {
/*  375 */         if (byteBuffer != null)
/*  376 */           Util.releaseTemporaryDirectBuffer(byteBuffer); 
/*  377 */         this.readerThread = 0L;
/*  378 */         end((i > 0 || i == -2));
/*  379 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int receive(FileDescriptor paramFileDescriptor, ByteBuffer paramByteBuffer) throws IOException {
/*  387 */     int i = paramByteBuffer.position();
/*  388 */     int j = paramByteBuffer.limit();
/*  389 */     assert i <= j;
/*  390 */     boolean bool = (i <= j) ? (j - i) : false;
/*  391 */     if (paramByteBuffer instanceof DirectBuffer && bool) {
/*  392 */       return receiveIntoNativeBuffer(paramFileDescriptor, paramByteBuffer, bool, i);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  397 */     int k = Math.max(bool, 1);
/*  398 */     ByteBuffer byteBuffer = Util.getTemporaryDirectBuffer(k);
/*      */     try {
/*  400 */       int m = receiveIntoNativeBuffer(paramFileDescriptor, byteBuffer, k, 0);
/*  401 */       byteBuffer.flip();
/*  402 */       if (m > 0 && bool)
/*  403 */         paramByteBuffer.put(byteBuffer); 
/*  404 */       return m;
/*      */     } finally {
/*  406 */       Util.releaseTemporaryDirectBuffer(byteBuffer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int receiveIntoNativeBuffer(FileDescriptor paramFileDescriptor, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2) throws IOException {
/*  414 */     int i = receive0(paramFileDescriptor, ((DirectBuffer)paramByteBuffer).address() + paramInt2, paramInt1, 
/*  415 */         isConnected());
/*  416 */     if (i > 0)
/*  417 */       paramByteBuffer.position(paramInt2 + i); 
/*  418 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int send(ByteBuffer paramByteBuffer, SocketAddress paramSocketAddress) throws IOException {
/*  424 */     if (paramByteBuffer == null) {
/*  425 */       throw new NullPointerException();
/*      */     }
/*  427 */     synchronized (this.writeLock) {
/*  428 */       ensureOpen();
/*  429 */       InetSocketAddress inetSocketAddress = Net.checkAddress(paramSocketAddress);
/*  430 */       InetAddress inetAddress = inetSocketAddress.getAddress();
/*  431 */       if (inetAddress == null)
/*  432 */         throw new IOException("Target address not resolved"); 
/*  433 */       synchronized (this.stateLock) {
/*  434 */         if (!isConnected()) {
/*  435 */           if (paramSocketAddress == null)
/*  436 */             throw new NullPointerException(); 
/*  437 */           SecurityManager securityManager = System.getSecurityManager();
/*  438 */           if (securityManager != null) {
/*  439 */             if (inetAddress.isMulticastAddress()) {
/*  440 */               securityManager.checkMulticast(inetAddress);
/*      */             } else {
/*  442 */               securityManager.checkConnect(inetAddress.getHostAddress(), inetSocketAddress
/*  443 */                   .getPort());
/*      */             } 
/*      */           }
/*      */         } else {
/*  447 */           if (!paramSocketAddress.equals(this.remoteAddress)) {
/*  448 */             throw new IllegalArgumentException("Connected address not equal to target address");
/*      */           }
/*      */           
/*  451 */           return write(paramByteBuffer);
/*      */         } 
/*      */       } 
/*      */       
/*  455 */       int i = 0;
/*      */       try {
/*  457 */         begin();
/*  458 */         if (!isOpen())
/*  459 */           return 0; 
/*  460 */         this.writerThread = NativeThread.current();
/*      */         do {
/*  462 */           i = send(this.fd, paramByteBuffer, inetSocketAddress);
/*  463 */         } while (i == -3 && isOpen());
/*      */         
/*  465 */         synchronized (this.stateLock) {
/*  466 */           if (isOpen() && this.localAddress == null) {
/*  467 */             this.localAddress = Net.localAddress(this.fd);
/*      */           }
/*      */         } 
/*  470 */         return IOStatus.normalize(i);
/*      */       } finally {
/*  472 */         this.writerThread = 0L;
/*  473 */         end((i > 0 || i == -2));
/*  474 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int send(FileDescriptor paramFileDescriptor, ByteBuffer paramByteBuffer, InetSocketAddress paramInetSocketAddress) throws IOException {
/*  482 */     if (paramByteBuffer instanceof DirectBuffer) {
/*  483 */       return sendFromNativeBuffer(paramFileDescriptor, paramByteBuffer, paramInetSocketAddress);
/*      */     }
/*      */     
/*  486 */     int i = paramByteBuffer.position();
/*  487 */     int j = paramByteBuffer.limit();
/*  488 */     assert i <= j;
/*  489 */     boolean bool = (i <= j) ? (j - i) : false;
/*      */     
/*  491 */     ByteBuffer byteBuffer = Util.getTemporaryDirectBuffer(bool);
/*      */     try {
/*  493 */       byteBuffer.put(paramByteBuffer);
/*  494 */       byteBuffer.flip();
/*      */       
/*  496 */       paramByteBuffer.position(i);
/*      */       
/*  498 */       int k = sendFromNativeBuffer(paramFileDescriptor, byteBuffer, paramInetSocketAddress);
/*  499 */       if (k > 0)
/*      */       {
/*  501 */         paramByteBuffer.position(i + k);
/*      */       }
/*  503 */       return k;
/*      */     } finally {
/*  505 */       Util.releaseTemporaryDirectBuffer(byteBuffer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int sendFromNativeBuffer(FileDescriptor paramFileDescriptor, ByteBuffer paramByteBuffer, InetSocketAddress paramInetSocketAddress) throws IOException {
/*      */     byte b2;
/*  513 */     int i = paramByteBuffer.position();
/*  514 */     int j = paramByteBuffer.limit();
/*  515 */     assert i <= j;
/*  516 */     byte b1 = (i <= j) ? (j - i) : 0;
/*      */     
/*  518 */     boolean bool = (this.family != StandardProtocolFamily.INET) ? true : false;
/*      */     
/*      */     try {
/*  521 */       b2 = send0(bool, paramFileDescriptor, ((DirectBuffer)paramByteBuffer).address() + i, b1, paramInetSocketAddress
/*  522 */           .getAddress(), paramInetSocketAddress.getPort());
/*  523 */     } catch (PortUnreachableException portUnreachableException) {
/*  524 */       if (isConnected())
/*  525 */         throw portUnreachableException; 
/*  526 */       b2 = b1;
/*      */     } 
/*  528 */     if (b2)
/*  529 */       paramByteBuffer.position(i + b2); 
/*  530 */     return b2;
/*      */   }
/*      */   
/*      */   public int read(ByteBuffer paramByteBuffer) throws IOException {
/*  534 */     if (paramByteBuffer == null)
/*  535 */       throw new NullPointerException(); 
/*  536 */     synchronized (this.readLock) {
/*  537 */       synchronized (this.stateLock) {
/*  538 */         ensureOpen();
/*  539 */         if (!isConnected())
/*  540 */           throw new NotYetConnectedException(); 
/*      */       } 
/*  542 */       int i = 0;
/*      */       try {
/*  544 */         begin();
/*  545 */         if (!isOpen())
/*  546 */           return 0; 
/*  547 */         this.readerThread = NativeThread.current();
/*      */         do {
/*  549 */           i = IOUtil.read(this.fd, paramByteBuffer, -1L, nd);
/*  550 */         } while (i == -3 && isOpen());
/*  551 */         return IOStatus.normalize(i);
/*      */       } finally {
/*  553 */         this.readerThread = 0L;
/*  554 */         end((i > 0 || i == -2));
/*  555 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long read(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/*  563 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2)
/*  564 */       throw new IndexOutOfBoundsException(); 
/*  565 */     synchronized (this.readLock) {
/*  566 */       synchronized (this.stateLock) {
/*  567 */         ensureOpen();
/*  568 */         if (!isConnected())
/*  569 */           throw new NotYetConnectedException(); 
/*      */       } 
/*  571 */       long l = 0L;
/*      */       try {
/*  573 */         begin();
/*  574 */         if (!isOpen())
/*  575 */           return 0L; 
/*  576 */         this.readerThread = NativeThread.current();
/*      */         do {
/*  578 */           l = IOUtil.read(this.fd, paramArrayOfByteBuffer, paramInt1, paramInt2, nd);
/*  579 */         } while (l == -3L && isOpen());
/*  580 */         return IOStatus.normalize(l);
/*      */       } finally {
/*  582 */         this.readerThread = 0L;
/*  583 */         end((l > 0L || l == -2L));
/*  584 */         assert IOStatus.check(l);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int write(ByteBuffer paramByteBuffer) throws IOException {
/*  590 */     if (paramByteBuffer == null)
/*  591 */       throw new NullPointerException(); 
/*  592 */     synchronized (this.writeLock) {
/*  593 */       synchronized (this.stateLock) {
/*  594 */         ensureOpen();
/*  595 */         if (!isConnected())
/*  596 */           throw new NotYetConnectedException(); 
/*      */       } 
/*  598 */       int i = 0;
/*      */       try {
/*  600 */         begin();
/*  601 */         if (!isOpen())
/*  602 */           return 0; 
/*  603 */         this.writerThread = NativeThread.current();
/*      */         do {
/*  605 */           i = IOUtil.write(this.fd, paramByteBuffer, -1L, nd);
/*  606 */         } while (i == -3 && isOpen());
/*  607 */         return IOStatus.normalize(i);
/*      */       } finally {
/*  609 */         this.writerThread = 0L;
/*  610 */         end((i > 0 || i == -2));
/*  611 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long write(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/*  619 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2)
/*  620 */       throw new IndexOutOfBoundsException(); 
/*  621 */     synchronized (this.writeLock) {
/*  622 */       synchronized (this.stateLock) {
/*  623 */         ensureOpen();
/*  624 */         if (!isConnected())
/*  625 */           throw new NotYetConnectedException(); 
/*      */       } 
/*  627 */       long l = 0L;
/*      */       try {
/*  629 */         begin();
/*  630 */         if (!isOpen())
/*  631 */           return 0L; 
/*  632 */         this.writerThread = NativeThread.current();
/*      */         do {
/*  634 */           l = IOUtil.write(this.fd, paramArrayOfByteBuffer, paramInt1, paramInt2, nd);
/*  635 */         } while (l == -3L && isOpen());
/*  636 */         return IOStatus.normalize(l);
/*      */       } finally {
/*  638 */         this.writerThread = 0L;
/*  639 */         end((l > 0L || l == -2L));
/*  640 */         assert IOStatus.check(l);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void implConfigureBlocking(boolean paramBoolean) throws IOException {
/*  646 */     IOUtil.configureBlocking(this.fd, paramBoolean);
/*      */   }
/*      */   
/*      */   public SocketAddress localAddress() {
/*  650 */     synchronized (this.stateLock) {
/*  651 */       return this.localAddress;
/*      */     } 
/*      */   }
/*      */   
/*      */   public SocketAddress remoteAddress() {
/*  656 */     synchronized (this.stateLock) {
/*  657 */       return this.remoteAddress;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public DatagramChannel bind(SocketAddress paramSocketAddress) throws IOException {
/*  663 */     synchronized (this.readLock) {
/*  664 */       synchronized (this.writeLock) {
/*  665 */         synchronized (this.stateLock) {
/*  666 */           InetSocketAddress inetSocketAddress; ensureOpen();
/*  667 */           if (this.localAddress != null) {
/*  668 */             throw new AlreadyBoundException();
/*      */           }
/*  670 */           if (paramSocketAddress == null) {
/*      */             
/*  672 */             if (this.family == StandardProtocolFamily.INET) {
/*  673 */               inetSocketAddress = new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0);
/*      */             } else {
/*  675 */               inetSocketAddress = new InetSocketAddress(0);
/*      */             } 
/*      */           } else {
/*  678 */             inetSocketAddress = Net.checkAddress(paramSocketAddress);
/*      */ 
/*      */             
/*  681 */             if (this.family == StandardProtocolFamily.INET) {
/*  682 */               InetAddress inetAddress = inetSocketAddress.getAddress();
/*  683 */               if (!(inetAddress instanceof Inet4Address))
/*  684 */                 throw new UnsupportedAddressTypeException(); 
/*      */             } 
/*      */           } 
/*  687 */           SecurityManager securityManager = System.getSecurityManager();
/*  688 */           if (securityManager != null) {
/*  689 */             securityManager.checkListen(inetSocketAddress.getPort());
/*      */           }
/*  691 */           Net.bind(this.family, this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
/*  692 */           this.localAddress = Net.localAddress(this.fd);
/*      */         } 
/*      */       } 
/*      */     } 
/*  696 */     return this;
/*      */   }
/*      */   
/*      */   public boolean isConnected() {
/*  700 */     synchronized (this.stateLock) {
/*  701 */       return (this.state == 1);
/*      */     } 
/*      */   }
/*      */   
/*      */   void ensureOpenAndUnconnected() throws IOException {
/*  706 */     synchronized (this.stateLock) {
/*  707 */       if (!isOpen())
/*  708 */         throw new ClosedChannelException(); 
/*  709 */       if (this.state != 0) {
/*  710 */         throw new IllegalStateException("Connect already invoked");
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public DatagramChannel connect(SocketAddress paramSocketAddress) throws IOException {
/*  716 */     boolean bool = false;
/*      */     
/*  718 */     synchronized (this.readLock) {
/*  719 */       synchronized (this.writeLock) {
/*  720 */         synchronized (this.stateLock) {
/*  721 */           ensureOpenAndUnconnected();
/*  722 */           InetSocketAddress inetSocketAddress = Net.checkAddress(paramSocketAddress);
/*  723 */           SecurityManager securityManager = System.getSecurityManager();
/*  724 */           if (securityManager != null)
/*  725 */             securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress
/*  726 */                 .getPort()); 
/*  727 */           int i = Net.connect(this.family, this.fd, inetSocketAddress
/*      */               
/*  729 */               .getAddress(), inetSocketAddress
/*  730 */               .getPort());
/*  731 */           if (i <= 0) {
/*  732 */             throw new Error();
/*      */           }
/*      */           
/*  735 */           this.state = 1;
/*  736 */           this.remoteAddress = inetSocketAddress;
/*  737 */           this.sender = inetSocketAddress;
/*  738 */           this.cachedSenderInetAddress = inetSocketAddress.getAddress();
/*  739 */           this.cachedSenderPort = inetSocketAddress.getPort();
/*      */ 
/*      */           
/*  742 */           this.localAddress = Net.localAddress(this.fd);
/*      */ 
/*      */           
/*  745 */           boolean bool1 = false;
/*  746 */           synchronized (blockingLock()) {
/*      */             try {
/*  748 */               bool1 = isBlocking();
/*      */               
/*  750 */               ByteBuffer byteBuffer = ByteBuffer.allocate(1);
/*  751 */               if (bool1) {
/*  752 */                 configureBlocking(false);
/*      */               }
/*      */               do {
/*  755 */                 byteBuffer.clear();
/*  756 */               } while (receive(byteBuffer) != null);
/*      */             } finally {
/*  758 */               if (bool1) {
/*  759 */                 configureBlocking(true);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  766 */     return this;
/*      */   }
/*      */   
/*      */   public DatagramChannel disconnect() throws IOException {
/*  770 */     synchronized (this.readLock) {
/*  771 */       synchronized (this.writeLock) {
/*  772 */         synchronized (this.stateLock) {
/*  773 */           if (!isConnected() || !isOpen())
/*  774 */             return this; 
/*  775 */           InetSocketAddress inetSocketAddress = this.remoteAddress;
/*  776 */           SecurityManager securityManager = System.getSecurityManager();
/*  777 */           if (securityManager != null)
/*  778 */             securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress
/*  779 */                 .getPort()); 
/*  780 */           boolean bool = (this.family == StandardProtocolFamily.INET6) ? true : false;
/*  781 */           disconnect0(this.fd, bool);
/*  782 */           this.remoteAddress = null;
/*  783 */           this.state = 0;
/*      */ 
/*      */           
/*  786 */           this.localAddress = Net.localAddress(this.fd);
/*      */         } 
/*      */       } 
/*      */     } 
/*  790 */     return this;
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
/*      */   private MembershipKey innerJoin(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2) throws IOException {
/*  802 */     if (!paramInetAddress1.isMulticastAddress()) {
/*  803 */       throw new IllegalArgumentException("Group not a multicast address");
/*      */     }
/*      */     
/*  806 */     if (paramInetAddress1 instanceof Inet4Address) {
/*  807 */       if (this.family == StandardProtocolFamily.INET6 && !Net.canIPv6SocketJoinIPv4Group())
/*  808 */         throw new IllegalArgumentException("IPv6 socket cannot join IPv4 multicast group"); 
/*  809 */     } else if (paramInetAddress1 instanceof java.net.Inet6Address) {
/*  810 */       if (this.family != StandardProtocolFamily.INET6)
/*  811 */         throw new IllegalArgumentException("Only IPv6 sockets can join IPv6 multicast group"); 
/*      */     } else {
/*  813 */       throw new IllegalArgumentException("Address type not supported");
/*      */     } 
/*      */ 
/*      */     
/*  817 */     if (paramInetAddress2 != null) {
/*  818 */       if (paramInetAddress2.isAnyLocalAddress())
/*  819 */         throw new IllegalArgumentException("Source address is a wildcard address"); 
/*  820 */       if (paramInetAddress2.isMulticastAddress())
/*  821 */         throw new IllegalArgumentException("Source address is multicast address"); 
/*  822 */       if (paramInetAddress2.getClass() != paramInetAddress1.getClass()) {
/*  823 */         throw new IllegalArgumentException("Source address is different type to group");
/*      */       }
/*      */     } 
/*  826 */     SecurityManager securityManager = System.getSecurityManager();
/*  827 */     if (securityManager != null) {
/*  828 */       securityManager.checkMulticast(paramInetAddress1);
/*      */     }
/*  830 */     synchronized (this.stateLock) {
/*  831 */       MembershipKey membershipKey; if (!isOpen()) {
/*  832 */         throw new ClosedChannelException();
/*      */       }
/*      */       
/*  835 */       if (this.registry == null) {
/*  836 */         this.registry = new MembershipRegistry();
/*      */       } else {
/*      */         
/*  839 */         membershipKey = this.registry.checkMembership(paramInetAddress1, paramNetworkInterface, paramInetAddress2);
/*  840 */         if (membershipKey != null) {
/*  841 */           return membershipKey;
/*      */         }
/*      */       } 
/*      */       
/*  845 */       if (this.family == StandardProtocolFamily.INET6 && (paramInetAddress1 instanceof java.net.Inet6Address || 
/*  846 */         Net.canJoin6WithIPv4Group())) {
/*      */         
/*  848 */         int i = paramNetworkInterface.getIndex();
/*  849 */         if (i == -1) {
/*  850 */           throw new IOException("Network interface cannot be identified");
/*      */         }
/*      */         
/*  853 */         byte[] arrayOfByte1 = Net.inet6AsByteArray(paramInetAddress1);
/*      */         
/*  855 */         byte[] arrayOfByte2 = (paramInetAddress2 == null) ? null : Net.inet6AsByteArray(paramInetAddress2);
/*      */ 
/*      */         
/*  858 */         int j = Net.join6(this.fd, arrayOfByte1, i, arrayOfByte2);
/*  859 */         if (j == -2) {
/*  860 */           throw new UnsupportedOperationException();
/*      */         }
/*  862 */         membershipKey = new MembershipKeyImpl.Type6(this, paramInetAddress1, paramNetworkInterface, paramInetAddress2, arrayOfByte1, i, arrayOfByte2);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  867 */         Inet4Address inet4Address = Net.anyInet4Address(paramNetworkInterface);
/*  868 */         if (inet4Address == null) {
/*  869 */           throw new IOException("Network interface not configured for IPv4");
/*      */         }
/*  871 */         int i = Net.inet4AsInt(paramInetAddress1);
/*  872 */         int j = Net.inet4AsInt(inet4Address);
/*  873 */         boolean bool = (paramInetAddress2 == null) ? false : Net.inet4AsInt(paramInetAddress2);
/*      */ 
/*      */         
/*  876 */         int k = Net.join4(this.fd, i, j, bool);
/*  877 */         if (k == -2) {
/*  878 */           throw new UnsupportedOperationException();
/*      */         }
/*  880 */         membershipKey = new MembershipKeyImpl.Type4(this, paramInetAddress1, paramNetworkInterface, paramInetAddress2, i, j, bool);
/*      */       } 
/*      */ 
/*      */       
/*  884 */       this.registry.add((MembershipKeyImpl)membershipKey);
/*  885 */       return membershipKey;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MembershipKey join(InetAddress paramInetAddress, NetworkInterface paramNetworkInterface) throws IOException {
/*  894 */     return innerJoin(paramInetAddress, paramNetworkInterface, (InetAddress)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MembershipKey join(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2) throws IOException {
/*  903 */     if (paramInetAddress2 == null)
/*  904 */       throw new NullPointerException("source address is null"); 
/*  905 */     return innerJoin(paramInetAddress1, paramNetworkInterface, paramInetAddress2);
/*      */   }
/*      */ 
/*      */   
/*      */   void drop(MembershipKeyImpl paramMembershipKeyImpl) {
/*  910 */     assert paramMembershipKeyImpl.channel() == this;
/*      */     
/*  912 */     synchronized (this.stateLock) {
/*  913 */       if (!paramMembershipKeyImpl.isValid()) {
/*      */         return;
/*      */       }
/*      */       try {
/*  917 */         if (paramMembershipKeyImpl instanceof MembershipKeyImpl.Type6) {
/*  918 */           MembershipKeyImpl.Type6 type6 = (MembershipKeyImpl.Type6)paramMembershipKeyImpl;
/*      */           
/*  920 */           Net.drop6(this.fd, type6.groupAddress(), type6.index(), type6.source());
/*      */         } else {
/*  922 */           MembershipKeyImpl.Type4 type4 = (MembershipKeyImpl.Type4)paramMembershipKeyImpl;
/*  923 */           Net.drop4(this.fd, type4.groupAddress(), type4.interfaceAddress(), type4
/*  924 */               .source());
/*      */         } 
/*  926 */       } catch (IOException iOException) {
/*      */         
/*  928 */         throw new AssertionError(iOException);
/*      */       } 
/*      */       
/*  931 */       paramMembershipKeyImpl.invalidate();
/*  932 */       this.registry.remove(paramMembershipKeyImpl);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void block(MembershipKeyImpl paramMembershipKeyImpl, InetAddress paramInetAddress) throws IOException {
/*  943 */     assert paramMembershipKeyImpl.channel() == this;
/*  944 */     assert paramMembershipKeyImpl.sourceAddress() == null;
/*      */     
/*  946 */     synchronized (this.stateLock) {
/*  947 */       int i; if (!paramMembershipKeyImpl.isValid())
/*  948 */         throw new IllegalStateException("key is no longer valid"); 
/*  949 */       if (paramInetAddress.isAnyLocalAddress())
/*  950 */         throw new IllegalArgumentException("Source address is a wildcard address"); 
/*  951 */       if (paramInetAddress.isMulticastAddress())
/*  952 */         throw new IllegalArgumentException("Source address is multicast address"); 
/*  953 */       if (paramInetAddress.getClass() != paramMembershipKeyImpl.group().getClass()) {
/*  954 */         throw new IllegalArgumentException("Source address is different type to group");
/*      */       }
/*      */       
/*  957 */       if (paramMembershipKeyImpl instanceof MembershipKeyImpl.Type6) {
/*  958 */         MembershipKeyImpl.Type6 type6 = (MembershipKeyImpl.Type6)paramMembershipKeyImpl;
/*      */         
/*  960 */         i = Net.block6(this.fd, type6.groupAddress(), type6.index(), 
/*  961 */             Net.inet6AsByteArray(paramInetAddress));
/*      */       } else {
/*  963 */         MembershipKeyImpl.Type4 type4 = (MembershipKeyImpl.Type4)paramMembershipKeyImpl;
/*      */         
/*  965 */         i = Net.block4(this.fd, type4.groupAddress(), type4.interfaceAddress(), 
/*  966 */             Net.inet4AsInt(paramInetAddress));
/*      */       } 
/*  968 */       if (i == -2)
/*      */       {
/*  970 */         throw new UnsupportedOperationException();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void unblock(MembershipKeyImpl paramMembershipKeyImpl, InetAddress paramInetAddress) {
/*  979 */     assert paramMembershipKeyImpl.channel() == this;
/*  980 */     assert paramMembershipKeyImpl.sourceAddress() == null;
/*      */     
/*  982 */     synchronized (this.stateLock) {
/*  983 */       if (!paramMembershipKeyImpl.isValid()) {
/*  984 */         throw new IllegalStateException("key is no longer valid");
/*      */       }
/*      */       try {
/*  987 */         if (paramMembershipKeyImpl instanceof MembershipKeyImpl.Type6) {
/*  988 */           MembershipKeyImpl.Type6 type6 = (MembershipKeyImpl.Type6)paramMembershipKeyImpl;
/*      */           
/*  990 */           Net.unblock6(this.fd, type6.groupAddress(), type6.index(), 
/*  991 */               Net.inet6AsByteArray(paramInetAddress));
/*      */         } else {
/*  993 */           MembershipKeyImpl.Type4 type4 = (MembershipKeyImpl.Type4)paramMembershipKeyImpl;
/*      */           
/*  995 */           Net.unblock4(this.fd, type4.groupAddress(), type4.interfaceAddress(), 
/*  996 */               Net.inet4AsInt(paramInetAddress));
/*      */         } 
/*  998 */       } catch (IOException iOException) {
/*      */         
/* 1000 */         throw new AssertionError(iOException);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void implCloseSelectableChannel() throws IOException {
/* 1006 */     synchronized (this.stateLock) {
/* 1007 */       if (this.state != 2)
/* 1008 */         nd.preClose(this.fd); 
/* 1009 */       ResourceManager.afterUdpClose();
/*      */ 
/*      */       
/* 1012 */       if (this.registry != null) {
/* 1013 */         this.registry.invalidateAll();
/*      */       }
/*      */       long l;
/* 1016 */       if ((l = this.readerThread) != 0L)
/* 1017 */         NativeThread.signal(l); 
/* 1018 */       if ((l = this.writerThread) != 0L)
/* 1019 */         NativeThread.signal(l); 
/* 1020 */       if (!isRegistered())
/* 1021 */         kill(); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void kill() throws IOException {
/* 1026 */     synchronized (this.stateLock) {
/* 1027 */       if (this.state == 2)
/*      */         return; 
/* 1029 */       if (this.state == -1) {
/* 1030 */         this.state = 2;
/*      */         return;
/*      */       } 
/* 1033 */       assert !isOpen() && !isRegistered();
/* 1034 */       nd.close(this.fd);
/* 1035 */       this.state = 2;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void finalize() throws IOException {
/* 1041 */     if (this.fd != null) {
/* 1042 */       close();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean translateReadyOps(int paramInt1, int paramInt2, SelectionKeyImpl paramSelectionKeyImpl) {
/* 1050 */     int i = paramSelectionKeyImpl.nioInterestOps();
/* 1051 */     int j = paramSelectionKeyImpl.nioReadyOps();
/* 1052 */     int k = paramInt2;
/*      */     
/* 1054 */     if ((paramInt1 & Net.POLLNVAL) != 0)
/*      */     {
/*      */ 
/*      */       
/* 1058 */       return false;
/*      */     }
/*      */     
/* 1061 */     if ((paramInt1 & (Net.POLLERR | Net.POLLHUP)) != 0) {
/* 1062 */       k = i;
/* 1063 */       paramSelectionKeyImpl.nioReadyOps(k);
/* 1064 */       return ((k & (j ^ 0xFFFFFFFF)) != 0);
/*      */     } 
/*      */     
/* 1067 */     if ((paramInt1 & Net.POLLIN) != 0 && (i & 0x1) != 0)
/*      */     {
/* 1069 */       k |= 0x1;
/*      */     }
/* 1071 */     if ((paramInt1 & Net.POLLOUT) != 0 && (i & 0x4) != 0)
/*      */     {
/* 1073 */       k |= 0x4;
/*      */     }
/* 1075 */     paramSelectionKeyImpl.nioReadyOps(k);
/* 1076 */     return ((k & (j ^ 0xFFFFFFFF)) != 0);
/*      */   }
/*      */   
/*      */   public boolean translateAndUpdateReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 1080 */     return translateReadyOps(paramInt, paramSelectionKeyImpl.nioReadyOps(), paramSelectionKeyImpl);
/*      */   }
/*      */   
/*      */   public boolean translateAndSetReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 1084 */     return translateReadyOps(paramInt, 0, paramSelectionKeyImpl);
/*      */   }
/*      */ 
/*      */   
/*      */   int poll(int paramInt, long paramLong) throws IOException {
/* 1089 */     assert Thread.holdsLock(blockingLock()) && !isBlocking();
/*      */     
/* 1091 */     synchronized (this.readLock) {
/* 1092 */       int i = 0;
/*      */       try {
/* 1094 */         begin();
/* 1095 */         synchronized (this.stateLock) {
/* 1096 */           if (!isOpen())
/* 1097 */             return 0; 
/* 1098 */           this.readerThread = NativeThread.current();
/*      */         } 
/* 1100 */         i = Net.poll(this.fd, paramInt, paramLong);
/*      */       } finally {
/* 1102 */         this.readerThread = 0L;
/* 1103 */         end((i > 0));
/*      */       } 
/* 1105 */       return i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void translateAndSetInterestOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) {
/* 1113 */     int i = 0;
/*      */     
/* 1115 */     if ((paramInt & 0x1) != 0)
/* 1116 */       i |= Net.POLLIN; 
/* 1117 */     if ((paramInt & 0x4) != 0)
/* 1118 */       i |= Net.POLLOUT; 
/* 1119 */     if ((paramInt & 0x8) != 0)
/* 1120 */       i |= Net.POLLIN; 
/* 1121 */     paramSelectionKeyImpl.selector.putEventOps(paramSelectionKeyImpl, i);
/*      */   }
/*      */   
/*      */   public FileDescriptor getFD() {
/* 1125 */     return this.fd;
/*      */   }
/*      */   
/*      */   public int getFDVal() {
/* 1129 */     return this.fdVal;
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
/*      */   static {
/* 1149 */     IOUtil.load();
/* 1150 */     initIDs();
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   private static native void disconnect0(FileDescriptor paramFileDescriptor, boolean paramBoolean) throws IOException;
/*      */   
/*      */   private native int receive0(FileDescriptor paramFileDescriptor, long paramLong, int paramInt, boolean paramBoolean) throws IOException;
/*      */   
/*      */   private native int send0(boolean paramBoolean, FileDescriptor paramFileDescriptor, long paramLong, int paramInt1, InetAddress paramInetAddress, int paramInt2) throws IOException;
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\DatagramChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */