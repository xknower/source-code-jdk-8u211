/*      */ package com.sun.jndi.ldap;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InterruptedIOException;
/*      */ import java.io.OutputStream;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.net.Socket;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Arrays;
/*      */ import javax.naming.CommunicationException;
/*      */ import javax.naming.InterruptedNamingException;
/*      */ import javax.naming.NamingException;
/*      */ import javax.naming.ServiceUnavailableException;
/*      */ import javax.naming.ldap.Control;
/*      */ import javax.net.ssl.SSLParameters;
/*      */ import javax.net.ssl.SSLSocket;
/*      */ import sun.misc.IOUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Connection
/*      */   implements Runnable
/*      */ {
/*      */   private static final boolean debug = false;
/*      */   private static final int dump = 0;
/*      */   private final Thread worker;
/*      */   private boolean v3 = true;
/*      */   public final String host;
/*      */   public final int port;
/*      */   private boolean bound = false;
/*  129 */   private OutputStream traceFile = null;
/*  130 */   private String traceTagIn = null;
/*  131 */   private String traceTagOut = null;
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream inStream;
/*      */ 
/*      */ 
/*      */   
/*      */   public OutputStream outStream;
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket sock;
/*      */ 
/*      */ 
/*      */   
/*      */   private final LdapClient parent;
/*      */ 
/*      */   
/*  150 */   private int outMsgId = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  156 */   private LdapRequest pendingRequests = null;
/*      */   
/*  158 */   volatile IOException closureReason = null;
/*      */   
/*      */   volatile boolean useable = true;
/*      */   
/*      */   int readTimeout;
/*      */   int connectTimeout;
/*  164 */   private static final boolean IS_HOSTNAME_VERIFICATION_DISABLED = hostnameVerificationDisabledValue(); private Object pauseLock; private boolean paused;
/*      */   
/*      */   private static boolean hostnameVerificationDisabledValue() {
/*  167 */     PrivilegedAction<String> privilegedAction = () -> System.getProperty("com.sun.jndi.ldap.object.disableEndpointIdentification");
/*      */     
/*  169 */     String str = AccessController.<String>doPrivileged(privilegedAction);
/*  170 */     if (str == null) {
/*  171 */       return false;
/*      */     }
/*  173 */     return str.isEmpty() ? true : Boolean.parseBoolean(str);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void setV3(boolean paramBoolean) {
/*  179 */     this.v3 = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setBound() {
/*  187 */     this.bound = true;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object createInetSocketAddress(String paramString, int paramInt) throws NoSuchMethodException {
/*      */     try {
/*  257 */       Class<?> clazz = Class.forName("java.net.InetSocketAddress");
/*      */ 
/*      */       
/*  260 */       Constructor<?> constructor = clazz.getConstructor(new Class[] { String.class, int.class });
/*      */ 
/*      */       
/*  263 */       return constructor.newInstance(new Object[] { paramString, new Integer(paramInt) });
/*      */     
/*      */     }
/*  266 */     catch (ClassNotFoundException|InstantiationException|InvocationTargetException|IllegalAccessException classNotFoundException) {
/*      */ 
/*      */ 
/*      */       
/*  270 */       throw new NoSuchMethodException();
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
/*      */   private Socket createSocket(String paramString1, int paramInt1, String paramString2, int paramInt2) throws Exception {
/*  287 */     Socket socket = null;
/*      */     
/*  289 */     if (paramString2 != null) {
/*      */ 
/*      */ 
/*      */       
/*  293 */       Class<?> clazz = Obj.helper.loadClass(paramString2);
/*      */       
/*  295 */       Method method1 = clazz.getMethod("getDefault", new Class[0]);
/*  296 */       Object object = method1.invoke(null, new Object[0]);
/*      */ 
/*      */ 
/*      */       
/*  300 */       Method method2 = null;
/*      */       
/*  302 */       if (paramInt2 > 0) {
/*      */         
/*      */         try {
/*  305 */           method2 = clazz.getMethod("createSocket", new Class[0]);
/*      */ 
/*      */           
/*  308 */           Method method = Socket.class.getMethod("connect", new Class[] {
/*  309 */                 Class.forName("java.net.SocketAddress"), int.class
/*      */               });
/*  311 */           Object object1 = createInetSocketAddress(paramString1, paramInt1);
/*      */ 
/*      */ 
/*      */           
/*  315 */           socket = (Socket)method2.invoke(object, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  323 */           method.invoke(socket, new Object[] { object1, new Integer(paramInt2) });
/*      */         
/*      */         }
/*  326 */         catch (NoSuchMethodException noSuchMethodException) {}
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  331 */       if (socket == null) {
/*  332 */         method2 = clazz.getMethod("createSocket", new Class[] { String.class, int.class });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  340 */         socket = (Socket)method2.invoke(object, new Object[] { paramString1, new Integer(paramInt1) });
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  345 */       if (paramInt2 > 0) {
/*      */         
/*      */         try {
/*      */           
/*  349 */           Constructor<Socket> constructor = Socket.class.getConstructor(new Class[0]);
/*      */           
/*  351 */           Method method = Socket.class.getMethod("connect", new Class[] {
/*  352 */                 Class.forName("java.net.SocketAddress"), int.class
/*      */               });
/*  354 */           Object object = createInetSocketAddress(paramString1, paramInt1);
/*      */           
/*  356 */           socket = constructor.newInstance(new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  362 */           method.invoke(socket, new Object[] { object, new Integer(paramInt2) });
/*      */         
/*      */         }
/*  365 */         catch (NoSuchMethodException noSuchMethodException) {}
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  370 */       if (socket == null)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  375 */         socket = new Socket(paramString1, paramInt1);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  383 */     if (socket instanceof SSLSocket) {
/*  384 */       SSLSocket sSLSocket = (SSLSocket)socket;
/*  385 */       if (!IS_HOSTNAME_VERIFICATION_DISABLED) {
/*  386 */         SSLParameters sSLParameters = sSLSocket.getSSLParameters();
/*  387 */         sSLParameters.setEndpointIdentificationAlgorithm("LDAPS");
/*  388 */         sSLSocket.setSSLParameters(sSLParameters);
/*      */       } 
/*  390 */       if (paramInt2 > 0) {
/*  391 */         int i = sSLSocket.getSoTimeout();
/*  392 */         sSLSocket.setSoTimeout(paramInt2);
/*  393 */         sSLSocket.startHandshake();
/*  394 */         sSLSocket.setSoTimeout(i);
/*      */       } 
/*      */     } 
/*  397 */     return socket;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized int getMsgId() {
/*  407 */     return ++this.outMsgId;
/*      */   }
/*      */   
/*      */   LdapRequest writeRequest(BerEncoder paramBerEncoder, int paramInt) throws IOException {
/*  411 */     return writeRequest(paramBerEncoder, paramInt, false, -1);
/*      */   }
/*      */ 
/*      */   
/*      */   LdapRequest writeRequest(BerEncoder paramBerEncoder, int paramInt, boolean paramBoolean) throws IOException {
/*  416 */     return writeRequest(paramBerEncoder, paramInt, paramBoolean, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   LdapRequest writeRequest(BerEncoder paramBerEncoder, int paramInt1, boolean paramBoolean, int paramInt2) throws IOException {
/*  422 */     LdapRequest ldapRequest = new LdapRequest(paramInt1, paramBoolean, paramInt2);
/*      */     
/*  424 */     addRequest(ldapRequest);
/*      */     
/*  426 */     if (this.traceFile != null) {
/*  427 */       Ber.dumpBER(this.traceFile, this.traceTagOut, paramBerEncoder.getBuf(), 0, paramBerEncoder.getDataLen());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  434 */     unpauseReader();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  441 */       synchronized (this) {
/*  442 */         this.outStream.write(paramBerEncoder.getBuf(), 0, paramBerEncoder.getDataLen());
/*  443 */         this.outStream.flush();
/*      */       } 
/*  445 */     } catch (IOException iOException) {
/*  446 */       cleanup(null, true);
/*  447 */       throw this.closureReason = iOException;
/*      */     } 
/*      */     
/*  450 */     return ldapRequest;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   BerDecoder readReply(LdapRequest paramLdapRequest) throws IOException, NamingException {
/*  461 */     long l1 = 0L;
/*  462 */     long l2 = 0L;
/*      */     BerDecoder berDecoder;
/*  464 */     while ((berDecoder = paramLdapRequest.getReplyBer()) == null && (this.readTimeout <= 0 || l1 < this.readTimeout)) {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  469 */         synchronized (this) {
/*  470 */           if (this.sock == null) {
/*  471 */             throw new ServiceUnavailableException(this.host + ":" + this.port + "; socket closed");
/*      */           }
/*      */         } 
/*      */         
/*  475 */         synchronized (paramLdapRequest) {
/*      */           
/*  477 */           berDecoder = paramLdapRequest.getReplyBer();
/*  478 */           if (berDecoder == null) {
/*  479 */             if (this.readTimeout > 0) {
/*  480 */               long l = System.nanoTime();
/*      */ 
/*      */ 
/*      */               
/*  484 */               paramLdapRequest.wait(this.readTimeout - l1);
/*  485 */               l2 += System.nanoTime() - l;
/*  486 */               l1 += l2 / 1000000L;
/*  487 */               l2 %= 1000000L;
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */               
/*  493 */               paramLdapRequest.wait();
/*      */             } 
/*      */           } else {
/*      */             break;
/*      */           } 
/*      */         } 
/*  499 */       } catch (InterruptedException interruptedException) {
/*  500 */         throw new InterruptedNamingException("Interrupted during LDAP operation");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  505 */     if (berDecoder == null && l1 >= this.readTimeout) {
/*  506 */       abandonRequest(paramLdapRequest, null);
/*  507 */       throw new NamingException("LDAP response read timed out, timeout used:" + this.readTimeout + "ms.");
/*      */     } 
/*      */ 
/*      */     
/*  511 */     return berDecoder;
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
/*      */   private synchronized void addRequest(LdapRequest paramLdapRequest) {
/*  523 */     LdapRequest ldapRequest = this.pendingRequests;
/*  524 */     if (ldapRequest == null) {
/*  525 */       this.pendingRequests = paramLdapRequest;
/*  526 */       paramLdapRequest.next = null;
/*      */     } else {
/*  528 */       paramLdapRequest.next = this.pendingRequests;
/*  529 */       this.pendingRequests = paramLdapRequest;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   synchronized LdapRequest findRequest(int paramInt) {
/*  535 */     LdapRequest ldapRequest = this.pendingRequests;
/*  536 */     while (ldapRequest != null) {
/*  537 */       if (ldapRequest.msgId == paramInt) {
/*  538 */         return ldapRequest;
/*      */       }
/*  540 */       ldapRequest = ldapRequest.next;
/*      */     } 
/*  542 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   synchronized void removeRequest(LdapRequest paramLdapRequest) {
/*  547 */     LdapRequest ldapRequest1 = this.pendingRequests;
/*  548 */     LdapRequest ldapRequest2 = null;
/*      */     
/*  550 */     while (ldapRequest1 != null) {
/*  551 */       if (ldapRequest1 == paramLdapRequest) {
/*  552 */         ldapRequest1.cancel();
/*      */         
/*  554 */         if (ldapRequest2 != null) {
/*  555 */           ldapRequest2.next = ldapRequest1.next;
/*      */         } else {
/*  557 */           this.pendingRequests = ldapRequest1.next;
/*      */         } 
/*  559 */         ldapRequest1.next = null;
/*      */       } 
/*  561 */       ldapRequest2 = ldapRequest1;
/*  562 */       ldapRequest1 = ldapRequest1.next;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void abandonRequest(LdapRequest paramLdapRequest, Control[] paramArrayOfControl) {
/*  568 */     removeRequest(paramLdapRequest);
/*      */     
/*  570 */     BerEncoder berEncoder = new BerEncoder(256);
/*  571 */     int i = getMsgId();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  577 */       berEncoder.beginSeq(48);
/*  578 */       berEncoder.encodeInt(i);
/*  579 */       berEncoder.encodeInt(paramLdapRequest.msgId, 80);
/*      */       
/*  581 */       if (this.v3) {
/*  582 */         LdapClient.encodeControls(berEncoder, paramArrayOfControl);
/*      */       }
/*  584 */       berEncoder.endSeq();
/*      */       
/*  586 */       if (this.traceFile != null) {
/*  587 */         Ber.dumpBER(this.traceFile, this.traceTagOut, berEncoder.getBuf(), 0, berEncoder
/*  588 */             .getDataLen());
/*      */       }
/*      */       
/*  591 */       synchronized (this) {
/*  592 */         this.outStream.write(berEncoder.getBuf(), 0, berEncoder.getDataLen());
/*  593 */         this.outStream.flush();
/*      */       }
/*      */     
/*  596 */     } catch (IOException iOException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void abandonOutstandingReqs(Control[] paramArrayOfControl) {
/*  604 */     LdapRequest ldapRequest = this.pendingRequests;
/*      */     
/*  606 */     while (ldapRequest != null) {
/*  607 */       abandonRequest(ldapRequest, paramArrayOfControl);
/*  608 */       this.pendingRequests = ldapRequest = ldapRequest.next;
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
/*      */   private void ldapUnbind(Control[] paramArrayOfControl) {
/*  621 */     BerEncoder berEncoder = new BerEncoder(256);
/*  622 */     int i = getMsgId();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  630 */       berEncoder.beginSeq(48);
/*  631 */       berEncoder.encodeInt(i);
/*      */       
/*  633 */       berEncoder.encodeByte(66);
/*  634 */       berEncoder.encodeByte(0);
/*      */       
/*  636 */       if (this.v3) {
/*  637 */         LdapClient.encodeControls(berEncoder, paramArrayOfControl);
/*      */       }
/*  639 */       berEncoder.endSeq();
/*      */       
/*  641 */       if (this.traceFile != null) {
/*  642 */         Ber.dumpBER(this.traceFile, this.traceTagOut, berEncoder.getBuf(), 0, berEncoder
/*  643 */             .getDataLen());
/*      */       }
/*      */       
/*  646 */       synchronized (this) {
/*  647 */         this.outStream.write(berEncoder.getBuf(), 0, berEncoder.getDataLen());
/*  648 */         this.outStream.flush();
/*      */       }
/*      */     
/*  651 */     } catch (IOException iOException) {}
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
/*      */   void cleanup(Control[] paramArrayOfControl, boolean paramBoolean) {
/*  669 */     boolean bool = false;
/*      */     
/*  671 */     synchronized (this) {
/*  672 */       this.useable = false;
/*      */       
/*  674 */       if (this.sock != null) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  679 */           if (!paramBoolean) {
/*  680 */             abandonOutstandingReqs(paramArrayOfControl);
/*      */           }
/*  682 */           if (this.bound) {
/*  683 */             ldapUnbind(paramArrayOfControl);
/*      */           }
/*      */         } finally {
/*      */           try {
/*  687 */             this.outStream.flush();
/*  688 */             this.sock.close();
/*  689 */             unpauseReader();
/*  690 */           } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */           
/*  694 */           if (!paramBoolean) {
/*  695 */             LdapRequest ldapRequest = this.pendingRequests;
/*  696 */             while (ldapRequest != null) {
/*  697 */               ldapRequest.cancel();
/*  698 */               ldapRequest = ldapRequest.next;
/*      */             } 
/*      */           } 
/*  701 */           this.sock = null;
/*      */         } 
/*  703 */         bool = paramBoolean;
/*      */       } 
/*  705 */       if (bool) {
/*  706 */         LdapRequest ldapRequest = this.pendingRequests;
/*  707 */         while (ldapRequest != null) {
/*      */           
/*  709 */           synchronized (ldapRequest) {
/*  710 */             ldapRequest.notify();
/*  711 */             ldapRequest = ldapRequest.next;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  716 */     if (bool) {
/*  717 */       this.parent.processConnectionClosure();
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
/*      */   public synchronized void replaceStreams(InputStream paramInputStream, OutputStream paramOutputStream) {
/*  732 */     this.inStream = paramInputStream;
/*      */ 
/*      */     
/*      */     try {
/*  736 */       this.outStream.flush();
/*  737 */     } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  743 */     this.outStream = paramOutputStream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized InputStream getInputStream() {
/*  752 */     return this.inStream;
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
/*      */   
/*      */   Connection(LdapClient paramLdapClient, String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3, OutputStream paramOutputStream) throws NamingException {
/*  803 */     this.pauseLock = new Object();
/*  804 */     this.paused = false; this.host = paramString1; this.port = paramInt1; this.parent = paramLdapClient; this.readTimeout = paramInt3; this.connectTimeout = paramInt2; if (paramOutputStream != null) { this.traceFile = paramOutputStream; this.traceTagIn = "<- " + paramString1 + ":" + paramInt1 + "\n\n"; this.traceTagOut = "-> " + paramString1 + ":" + paramInt1 + "\n\n"; }
/*      */      try { this.sock = createSocket(paramString1, paramInt1, paramString2, paramInt2); this.inStream = new BufferedInputStream(this.sock.getInputStream()); this.outStream = new BufferedOutputStream(this.sock.getOutputStream()); }
/*      */     catch (InvocationTargetException invocationTargetException)
/*      */     { Throwable throwable = invocationTargetException.getTargetException(); CommunicationException communicationException = new CommunicationException(paramString1 + ":" + paramInt1); communicationException.setRootCause(throwable); throw communicationException; }
/*      */     catch (Exception exception)
/*      */     { CommunicationException communicationException = new CommunicationException(paramString1 + ":" + paramInt1); communicationException.setRootCause(exception); throw communicationException; }
/*  810 */      this.worker = Obj.helper.createThread(this); this.worker.setDaemon(true); this.worker.start(); } private void unpauseReader() throws IOException { synchronized (this.pauseLock) {
/*  811 */       if (this.paused) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  816 */         this.paused = false;
/*  817 */         this.pauseLock.notify();
/*      */       } 
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void pauseReader() throws IOException {
/*  832 */     this.paused = true;
/*      */     try {
/*  834 */       while (this.paused) {
/*  835 */         this.pauseLock.wait();
/*      */       }
/*  837 */     } catch (InterruptedException interruptedException) {
/*  838 */       throw new InterruptedIOException("Pause/unpause reader has problems.");
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
/*      */   public void run() {
/*  862 */     InputStream inputStream = null;
/*      */ 
/*      */     
/*      */     try {
/*      */       while (true) {
/*      */         try {
/*  868 */           byte[] arrayOfByte1 = new byte[129];
/*      */           
/*  870 */           int j = 0;
/*  871 */           int k = 0;
/*  872 */           int m = 0;
/*      */           
/*  874 */           inputStream = getInputStream();
/*      */ 
/*      */           
/*  877 */           int i = inputStream.read(arrayOfByte1, j, 1);
/*  878 */           if (i < 0) {
/*  879 */             if (inputStream != getInputStream()) {
/*      */               continue;
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/*  886 */           if (arrayOfByte1[j++] != 48) {
/*      */             continue;
/*      */           }
/*      */           
/*  890 */           i = inputStream.read(arrayOfByte1, j, 1);
/*  891 */           if (i < 0)
/*      */             break; 
/*  893 */           k = arrayOfByte1[j++];
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  898 */           if ((k & 0x80) == 128) {
/*  899 */             m = k & 0x7F;
/*      */             
/*  901 */             i = 0;
/*  902 */             boolean bool = false;
/*      */ 
/*      */             
/*  905 */             while (i < m) {
/*  906 */               int n = inputStream.read(arrayOfByte1, j + i, m - i);
/*      */               
/*  908 */               if (n < 0) {
/*  909 */                 bool = true;
/*      */                 break;
/*      */               } 
/*  912 */               i += n;
/*      */             } 
/*      */ 
/*      */             
/*  916 */             if (bool) {
/*      */               break;
/*      */             }
/*      */             
/*  920 */             k = 0;
/*  921 */             for (byte b = 0; b < m; b++) {
/*  922 */               k = (k << 8) + (arrayOfByte1[j + b] & 0xFF);
/*      */             }
/*  924 */             j += i;
/*      */           } 
/*      */ 
/*      */           
/*  928 */           byte[] arrayOfByte2 = IOUtils.readFully(inputStream, k, false);
/*  929 */           arrayOfByte1 = Arrays.copyOf(arrayOfByte1, j + arrayOfByte2.length);
/*  930 */           System.arraycopy(arrayOfByte2, 0, arrayOfByte1, j, arrayOfByte2.length);
/*  931 */           j += arrayOfByte2.length;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/*  943 */             BerDecoder berDecoder = new BerDecoder(arrayOfByte1, 0, j);
/*      */             
/*  945 */             if (this.traceFile != null) {
/*  946 */               Ber.dumpBER(this.traceFile, this.traceTagIn, arrayOfByte1, 0, j);
/*      */             }
/*      */             
/*  949 */             berDecoder.parseSeq(null);
/*  950 */             int n = berDecoder.parseInt();
/*  951 */             berDecoder.reset();
/*      */             
/*  953 */             boolean bool = false;
/*      */             
/*  955 */             if (n == 0) {
/*      */               
/*  957 */               this.parent.processUnsolicited(berDecoder); continue;
/*      */             } 
/*  959 */             LdapRequest ldapRequest = findRequest(n);
/*      */             
/*  961 */             if (ldapRequest != null)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  968 */               synchronized (this.pauseLock) {
/*  969 */                 bool = ldapRequest.addReplyBer(berDecoder);
/*  970 */                 if (bool)
/*      */                 {
/*      */ 
/*      */ 
/*      */                   
/*  975 */                   pauseReader();
/*      */                 
/*      */                 }
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */           
/*      */           }
/*  985 */           catch (DecodeException decodeException) {}
/*      */         
/*      */         }
/*  988 */         catch (IOException iOException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  994 */           if (inputStream != getInputStream()) {
/*      */             continue;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1001 */           throw iOException;
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/* 1010 */     catch (IOException iOException) {
/*      */ 
/*      */ 
/*      */       
/* 1014 */       this.closureReason = iOException;
/*      */     } finally {
/* 1016 */       cleanup(null, true);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jndi\ldap\Connection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */