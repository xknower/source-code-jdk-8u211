/*     */ package sun.rmi.transport.tcp;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.net.ConnectException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.UnknownHostException;
/*     */ import java.rmi.ConnectException;
/*     */ import java.rmi.ConnectIOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.UnknownHostException;
/*     */ import java.rmi.server.RMIClientSocketFactory;
/*     */ import java.rmi.server.RMIServerSocketFactory;
/*     */ import java.rmi.server.RMISocketFactory;
/*     */ import java.security.AccessController;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.runtime.NewThreadAction;
/*     */ import sun.rmi.transport.Channel;
/*     */ import sun.rmi.transport.Endpoint;
/*     */ import sun.rmi.transport.Target;
/*     */ import sun.rmi.transport.Transport;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.security.action.GetIntegerAction;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TCPEndpoint
/*     */   implements Endpoint
/*     */ {
/*     */   private String host;
/*     */   private int port;
/*     */   private final RMIClientSocketFactory csf;
/*     */   private final RMIServerSocketFactory ssf;
/*  74 */   private int listenPort = -1;
/*     */   
/*  76 */   private TCPTransport transport = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private static String localHost;
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getInt(String paramString, int paramInt) {
/*  85 */     return ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction(paramString, paramInt))).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean getBoolean(String paramString) {
/*  90 */     return ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction(paramString))).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getHostnameProperty() {
/*  97 */     return AccessController.<String>doPrivileged(new GetPropertyAction("java.rmi.server.hostname"));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean localHostKnown = true;
/*     */   
/*     */   private static final Map<TCPEndpoint, LinkedList<TCPEndpoint>> localEndpoints;
/*     */   private static final int FORMAT_HOST_PORT = 0;
/*     */   private static final int FORMAT_HOST_PORT_FACTORY = 1;
/*     */   
/*     */   static {
/* 108 */     localHost = getHostnameProperty();
/*     */ 
/*     */     
/* 111 */     if (localHost == null) {
/*     */       try {
/* 113 */         InetAddress inetAddress = InetAddress.getLocalHost();
/* 114 */         byte[] arrayOfByte = inetAddress.getAddress();
/* 115 */         if (arrayOfByte[0] == Byte.MAX_VALUE && arrayOfByte[1] == 0 && arrayOfByte[2] == 0 && arrayOfByte[3] == 1)
/*     */         {
/*     */ 
/*     */           
/* 119 */           localHostKnown = false;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 125 */         if (getBoolean("java.rmi.server.useLocalHostName")) {
/* 126 */           localHost = FQDN.attemptFQDN(inetAddress);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 131 */           localHost = inetAddress.getHostAddress();
/*     */         } 
/* 133 */       } catch (Exception exception) {
/* 134 */         localHostKnown = false;
/* 135 */         localHost = null;
/*     */       } 
/*     */     }
/*     */     
/* 139 */     if (TCPTransport.tcpLog.isLoggable(Log.BRIEF)) {
/* 140 */       TCPTransport.tcpLog.log(Log.BRIEF, "localHostKnown = " + localHostKnown + ", localHost = " + localHost);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     localEndpoints = new HashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TCPEndpoint(String paramString, int paramInt) {
/* 159 */     this(paramString, paramInt, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TCPEndpoint(String paramString, int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory) {
/* 170 */     if (paramString == null)
/* 171 */       paramString = ""; 
/* 172 */     this.host = paramString;
/* 173 */     this.port = paramInt;
/* 174 */     this.csf = paramRMIClientSocketFactory;
/* 175 */     this.ssf = paramRMIServerSocketFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TCPEndpoint getLocalEndpoint(int paramInt) {
/* 184 */     return getLocalEndpoint(paramInt, null, null);
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
/*     */   public static TCPEndpoint getLocalEndpoint(int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory) {
/* 196 */     TCPEndpoint tCPEndpoint = null;
/*     */     
/* 198 */     synchronized (localEndpoints) {
/* 199 */       TCPEndpoint tCPEndpoint1 = new TCPEndpoint(null, paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
/* 200 */       LinkedList<TCPEndpoint> linkedList = localEndpoints.get(tCPEndpoint1);
/* 201 */       String str = resampleLocalHost();
/*     */       
/* 203 */       if (linkedList == null) {
/*     */ 
/*     */ 
/*     */         
/* 207 */         tCPEndpoint = new TCPEndpoint(str, paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
/* 208 */         linkedList = new LinkedList();
/* 209 */         linkedList.add(tCPEndpoint);
/* 210 */         tCPEndpoint.listenPort = paramInt;
/* 211 */         tCPEndpoint.transport = new TCPTransport(linkedList);
/* 212 */         localEndpoints.put(tCPEndpoint1, linkedList);
/*     */         
/* 214 */         if (TCPTransport.tcpLog.isLoggable(Log.BRIEF)) {
/* 215 */           TCPTransport.tcpLog.log(Log.BRIEF, "created local endpoint for socket factory " + paramRMIServerSocketFactory + " on port " + paramInt);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 220 */         synchronized (linkedList) {
/* 221 */           tCPEndpoint = linkedList.getLast();
/* 222 */           String str1 = tCPEndpoint.host;
/* 223 */           int i = tCPEndpoint.port;
/* 224 */           TCPTransport tCPTransport = tCPEndpoint.transport;
/*     */           
/* 226 */           if (str != null && !str.equals(str1)) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 231 */             if (i != 0)
/*     */             {
/*     */ 
/*     */ 
/*     */               
/* 236 */               linkedList.clear();
/*     */             }
/* 238 */             tCPEndpoint = new TCPEndpoint(str, i, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
/* 239 */             tCPEndpoint.listenPort = paramInt;
/* 240 */             tCPEndpoint.transport = tCPTransport;
/* 241 */             linkedList.add(tCPEndpoint);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 247 */     return tCPEndpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String resampleLocalHost() {
/* 256 */     String str = getHostnameProperty();
/*     */     
/* 258 */     synchronized (localEndpoints) {
/*     */ 
/*     */       
/* 261 */       if (str != null) {
/* 262 */         if (!localHostKnown) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 267 */           setLocalHost(str);
/* 268 */         } else if (!str.equals(localHost)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 273 */           localHost = str;
/*     */           
/* 275 */           if (TCPTransport.tcpLog.isLoggable(Log.BRIEF)) {
/* 276 */             TCPTransport.tcpLog.log(Log.BRIEF, "updated local hostname to: " + localHost);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 281 */       return localHost;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void setLocalHost(String paramString) {
/* 291 */     synchronized (localEndpoints) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 296 */       if (!localHostKnown) {
/* 297 */         localHost = paramString;
/* 298 */         localHostKnown = true;
/*     */         
/* 300 */         if (TCPTransport.tcpLog.isLoggable(Log.BRIEF)) {
/* 301 */           TCPTransport.tcpLog.log(Log.BRIEF, "local host set to " + paramString);
/*     */         }
/*     */         
/* 304 */         for (LinkedList<TCPEndpoint> linkedList : localEndpoints.values()) {
/*     */           
/* 306 */           synchronized (linkedList) {
/* 307 */             for (TCPEndpoint tCPEndpoint : linkedList) {
/* 308 */               tCPEndpoint.host = paramString;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
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
/*     */   static void setDefaultPort(int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory) {
/* 325 */     TCPEndpoint tCPEndpoint = new TCPEndpoint(null, 0, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
/*     */     
/* 327 */     synchronized (localEndpoints) {
/* 328 */       LinkedList<TCPEndpoint> linkedList = localEndpoints.get(tCPEndpoint);
/*     */       
/* 330 */       synchronized (linkedList) {
/* 331 */         int i = linkedList.size();
/* 332 */         TCPEndpoint tCPEndpoint2 = linkedList.getLast();
/*     */         
/* 334 */         for (TCPEndpoint tCPEndpoint3 : linkedList) {
/* 335 */           tCPEndpoint3.port = paramInt;
/*     */         }
/* 337 */         if (i > 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 342 */           linkedList.clear();
/* 343 */           linkedList.add(tCPEndpoint2);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 351 */       TCPEndpoint tCPEndpoint1 = new TCPEndpoint(null, paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
/* 352 */       localEndpoints.put(tCPEndpoint1, linkedList);
/*     */       
/* 354 */       if (TCPTransport.tcpLog.isLoggable(Log.BRIEF)) {
/* 355 */         TCPTransport.tcpLog.log(Log.BRIEF, "default port for server socket factory " + paramRMIServerSocketFactory + " and client socket factory " + paramRMIClientSocketFactory + " set to " + paramInt);
/*     */       }
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
/*     */   public Transport getOutboundTransport() {
/* 368 */     TCPEndpoint tCPEndpoint = getLocalEndpoint(0, null, null);
/* 369 */     return tCPEndpoint.transport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Collection<TCPTransport> allKnownTransports() {
/*     */     HashSet<TCPTransport> hashSet;
/* 381 */     synchronized (localEndpoints) {
/*     */       
/* 383 */       hashSet = new HashSet(localEndpoints.size());
/* 384 */       for (LinkedList<TCPEndpoint> linkedList : localEndpoints.values()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 390 */         TCPEndpoint tCPEndpoint = linkedList.getFirst();
/* 391 */         hashSet.add(tCPEndpoint.transport);
/*     */       } 
/*     */     } 
/* 394 */     return hashSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void shedConnectionCaches() {
/* 402 */     for (TCPTransport tCPTransport : allKnownTransports()) {
/* 403 */       tCPTransport.shedConnectionCaches();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exportObject(Target paramTarget) throws RemoteException {
/* 411 */     this.transport.exportObject(paramTarget);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Channel getChannel() {
/* 418 */     return getOutboundTransport().getChannel(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHost() {
/* 425 */     return this.host;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 436 */     return this.port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListenPort() {
/* 447 */     return this.listenPort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transport getInboundTransport() {
/* 456 */     return this.transport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RMIClientSocketFactory getClientSocketFactory() {
/* 463 */     return this.csf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RMIServerSocketFactory getServerSocketFactory() {
/* 470 */     return this.ssf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 477 */     return "[" + this.host + ":" + this.port + ((this.ssf != null) ? ("," + this.ssf) : "") + ((this.csf != null) ? ("," + this.csf) : "") + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 484 */     return this.port;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 488 */     if (paramObject != null && paramObject instanceof TCPEndpoint) {
/* 489 */       TCPEndpoint tCPEndpoint = (TCPEndpoint)paramObject;
/* 490 */       if (this.port != tCPEndpoint.port || !this.host.equals(tCPEndpoint.host))
/* 491 */         return false; 
/* 492 */       if ((((this.csf == null) ? 1 : 0) ^ ((tCPEndpoint.csf == null) ? 1 : 0)) == 0) if ((((this.ssf == null) ? 1 : 0) ^ ((tCPEndpoint.ssf == null) ? 1 : 0)) == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 501 */           if (this.csf != null && (this.csf
/* 502 */             .getClass() != tCPEndpoint.csf.getClass() || !this.csf.equals(tCPEndpoint.csf)))
/* 503 */             return false; 
/* 504 */           if (this.ssf != null && (this.ssf
/* 505 */             .getClass() != tCPEndpoint.ssf.getClass() || !this.ssf.equals(tCPEndpoint.ssf)))
/* 506 */             return false; 
/* 507 */           return true;
/*     */         }   return false;
/* 509 */     }  return false;
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
/*     */   public void write(ObjectOutput paramObjectOutput) throws IOException {
/* 521 */     if (this.csf == null) {
/* 522 */       paramObjectOutput.writeByte(0);
/* 523 */       paramObjectOutput.writeUTF(this.host);
/* 524 */       paramObjectOutput.writeInt(this.port);
/*     */     } else {
/* 526 */       paramObjectOutput.writeByte(1);
/* 527 */       paramObjectOutput.writeUTF(this.host);
/* 528 */       paramObjectOutput.writeInt(this.port);
/* 529 */       paramObjectOutput.writeObject(this.csf);
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
/*     */   public static TCPEndpoint read(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/*     */     String str;
/*     */     int i;
/* 543 */     RMIClientSocketFactory rMIClientSocketFactory = null;
/*     */     
/* 545 */     byte b = paramObjectInput.readByte();
/* 546 */     switch (b) {
/*     */       case 0:
/* 548 */         str = paramObjectInput.readUTF();
/* 549 */         i = paramObjectInput.readInt();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 561 */         return new TCPEndpoint(str, i, rMIClientSocketFactory, null);case 1: str = paramObjectInput.readUTF(); i = paramObjectInput.readInt(); rMIClientSocketFactory = (RMIClientSocketFactory)paramObjectInput.readObject(); return new TCPEndpoint(str, i, rMIClientSocketFactory, null);
/*     */     } 
/*     */     throw new IOException("invalid endpoint format");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeHostPortFormat(DataOutput paramDataOutput) throws IOException {
/* 569 */     if (this.csf != null) {
/* 570 */       throw new InternalError("TCPEndpoint.writeHostPortFormat: called for endpoint with non-null socket factory");
/*     */     }
/*     */     
/* 573 */     paramDataOutput.writeUTF(this.host);
/* 574 */     paramDataOutput.writeInt(this.port);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TCPEndpoint readHostPortFormat(DataInput paramDataInput) throws IOException {
/* 584 */     String str = paramDataInput.readUTF();
/* 585 */     int i = paramDataInput.readInt();
/* 586 */     return new TCPEndpoint(str, i);
/*     */   }
/*     */   
/*     */   private static RMISocketFactory chooseFactory() {
/* 590 */     RMISocketFactory rMISocketFactory = RMISocketFactory.getSocketFactory();
/* 591 */     if (rMISocketFactory == null) {
/* 592 */       rMISocketFactory = TCPTransport.defaultSocketFactory;
/*     */     }
/* 594 */     return rMISocketFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Socket newSocket() throws RemoteException {
/*     */     Socket socket;
/* 601 */     if (TCPTransport.tcpLog.isLoggable(Log.VERBOSE)) {
/* 602 */       TCPTransport.tcpLog.log(Log.VERBOSE, "opening socket to " + this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 609 */       RMIClientSocketFactory rMIClientSocketFactory = this.csf;
/* 610 */       if (rMIClientSocketFactory == null) {
/* 611 */         rMIClientSocketFactory = chooseFactory();
/*     */       }
/* 613 */       socket = rMIClientSocketFactory.createSocket(this.host, this.port);
/*     */     }
/* 615 */     catch (UnknownHostException unknownHostException) {
/* 616 */       throw new UnknownHostException("Unknown host: " + this.host, unknownHostException);
/*     */     }
/* 618 */     catch (ConnectException connectException) {
/* 619 */       throw new ConnectException("Connection refused to host: " + this.host, connectException);
/*     */     }
/* 621 */     catch (IOException iOException) {
/*     */       
/*     */       try {
/* 624 */         shedConnectionCaches();
/*     */       }
/* 626 */       catch (OutOfMemoryError|Exception outOfMemoryError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 631 */       throw new ConnectIOException("Exception creating connection to: " + this.host, iOException);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 638 */       socket.setTcpNoDelay(true);
/* 639 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 645 */       socket.setKeepAlive(true);
/* 646 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 650 */     return socket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ServerSocket newServerSocket() throws IOException {
/* 657 */     if (TCPTransport.tcpLog.isLoggable(Log.VERBOSE)) {
/* 658 */       TCPTransport.tcpLog.log(Log.VERBOSE, "creating server socket on " + this);
/*     */     }
/*     */ 
/*     */     
/* 662 */     RMIServerSocketFactory rMIServerSocketFactory = this.ssf;
/* 663 */     if (rMIServerSocketFactory == null) {
/* 664 */       rMIServerSocketFactory = chooseFactory();
/*     */     }
/* 666 */     ServerSocket serverSocket = rMIServerSocketFactory.createServerSocket(this.listenPort);
/*     */ 
/*     */ 
/*     */     
/* 670 */     if (this.listenPort == 0) {
/* 671 */       setDefaultPort(serverSocket.getLocalPort(), this.csf, this.ssf);
/*     */     }
/* 673 */     return serverSocket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FQDN
/*     */     implements Runnable
/*     */   {
/*     */     private String reverseLookup;
/*     */ 
/*     */ 
/*     */     
/*     */     private String hostAddress;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private FQDN(String param1String) {
/* 693 */       this.hostAddress = param1String;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static String attemptFQDN(InetAddress param1InetAddress) throws UnknownHostException {
/* 717 */       String str = param1InetAddress.getHostName();
/*     */       
/* 719 */       if (str.indexOf('.') < 0) {
/*     */         
/* 721 */         String str1 = param1InetAddress.getHostAddress();
/* 722 */         FQDN fQDN = new FQDN(str1);
/*     */ 
/*     */         
/* 725 */         int i = TCPEndpoint.getInt("sun.rmi.transport.tcp.localHostNameTimeOut", 10000);
/*     */ 
/*     */         
/*     */         try {
/* 729 */           synchronized (fQDN) {
/* 730 */             fQDN.getFQDN();
/*     */ 
/*     */             
/* 733 */             fQDN.wait(i);
/*     */           } 
/* 735 */         } catch (InterruptedException interruptedException) {
/*     */           
/* 737 */           Thread.currentThread().interrupt();
/*     */         } 
/* 739 */         str = fQDN.getHost();
/*     */         
/* 741 */         if (str == null || str.equals("") || str
/* 742 */           .indexOf('.') < 0)
/*     */         {
/* 744 */           str = str1;
/*     */         }
/*     */       } 
/* 747 */       return str;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void getFQDN() {
/* 759 */       Thread thread = AccessController.<Thread>doPrivileged(new NewThreadAction(this, "FQDN Finder", true));
/*     */       
/* 761 */       thread.start();
/*     */     }
/*     */     
/*     */     private synchronized String getHost() {
/* 765 */       return this.reverseLookup;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 773 */       String str = null;
/*     */ 
/*     */       
/* 776 */       try { str = InetAddress.getByName(this.hostAddress).getHostName(); }
/* 777 */       catch (UnknownHostException unknownHostException) {  }
/*     */       finally
/* 779 */       { synchronized (this) {
/* 780 */           this.reverseLookup = str;
/* 781 */           notify();
/*     */         }  }
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\rmi\transport\tcp\TCPEndpoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */