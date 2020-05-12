/*     */ package com.sun.jmx.snmp.daemon;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketException;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SnmpSocket
/*     */   implements Runnable
/*     */ {
/*  39 */   private DatagramSocket _socket = null;
/*  40 */   private SnmpResponseHandler _dgramHdlr = null;
/*  41 */   private Thread _sockThread = null;
/*  42 */   private byte[] _buffer = null;
/*     */   
/*     */   private transient boolean isClosing = false;
/*  45 */   int _socketPort = 0;
/*  46 */   int responseBufSize = 1024;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpSocket(SnmpResponseHandler paramSnmpResponseHandler, InetAddress paramInetAddress, int paramInt) throws SocketException {
/*  60 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*  61 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSocket.class.getName(), "constructor", "Creating new SNMP datagram socket");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  66 */     this._socket = new DatagramSocket(0, paramInetAddress);
/*  67 */     this._socketPort = this._socket.getLocalPort();
/*  68 */     this.responseBufSize = paramInt;
/*  69 */     this._buffer = new byte[this.responseBufSize];
/*  70 */     this._dgramHdlr = paramSnmpResponseHandler;
/*  71 */     this._sockThread = new Thread(this, "SnmpSocket");
/*  72 */     this._sockThread.start();
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
/*     */   public synchronized void sendPacket(byte[] paramArrayOfbyte, int paramInt1, InetAddress paramInetAddress, int paramInt2) throws IOException {
/*  88 */     DatagramPacket datagramPacket = new DatagramPacket(paramArrayOfbyte, paramInt1, paramInetAddress, paramInt2);
/*  89 */     sendPacket(datagramPacket);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void sendPacket(DatagramPacket paramDatagramPacket) throws IOException {
/*     */     try {
/* 100 */       if (isValid())
/* 101 */       { if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 102 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSocket.class.getName(), "sendPacket", "Sending DatagramPacket. Length = " + paramDatagramPacket
/* 103 */               .getLength() + " through socket = " + this._socket
/* 104 */               .toString());
/*     */         }
/* 106 */         this._socket.send(paramDatagramPacket); }
/*     */       else
/* 108 */       { throw new IOException("Invalid state of SNMP datagram socket."); } 
/* 109 */     } catch (IOException iOException) {
/* 110 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 111 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSocket.class.getName(), "sendPacket", "I/O error while sending", iOException);
/*     */       }
/*     */       
/* 114 */       throw iOException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isValid() {
/* 124 */     return (this._socket != null && this._sockThread != null && this._sockThread.isAlive());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() {
/* 132 */     this.isClosing = true;
/*     */     
/* 134 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 135 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSocket.class.getName(), "close", "Closing and destroying the SNMP datagram socket -> " + 
/* 136 */           toString());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 142 */       DatagramSocket datagramSocket = new DatagramSocket(0);
/* 143 */       byte[] arrayOfByte = new byte[1];
/* 144 */       DatagramPacket datagramPacket = new DatagramPacket(arrayOfByte, 1, InetAddress.getLocalHost(), this._socketPort);
/* 145 */       datagramSocket.send(datagramPacket);
/* 146 */       datagramSocket.close();
/* 147 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     if (this._socket != null) {
/* 153 */       this._socket.close();
/* 154 */       this._socket = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 159 */     if (this._sockThread != null && this._sockThread.isAlive()) {
/* 160 */       this._sockThread.interrupt();
/*     */ 
/*     */       
/*     */       try {
/* 164 */         this._sockThread.join();
/* 165 */       } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */       
/* 168 */       this._sockThread = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 178 */     Thread.currentThread().setPriority(8);
/*     */     
/*     */     while (true) {
/*     */       try {
/* 182 */         DatagramPacket datagramPacket = new DatagramPacket(this._buffer, this._buffer.length);
/*     */         
/* 184 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 185 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSocket.class.getName(), "run", "[" + 
/* 186 */               Thread.currentThread().toString() + "]:Blocking for receiving packet");
/*     */         }
/*     */         
/* 189 */         this._socket.receive(datagramPacket);
/*     */ 
/*     */ 
/*     */         
/* 193 */         if (this.isClosing) {
/*     */           break;
/*     */         }
/* 196 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 197 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSocket.class.getName(), "run", "[" + 
/* 198 */               Thread.currentThread().toString() + "]:Received a packet");
/*     */         }
/*     */         
/* 201 */         if (datagramPacket.getLength() <= 0) {
/*     */           continue;
/*     */         }
/* 204 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 205 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpSocket.class.getName(), "run", "[" + 
/* 206 */               Thread.currentThread().toString() + "]:Received a packet from : " + datagramPacket
/* 207 */               .getAddress().toString() + ", Length = " + datagramPacket.getLength());
/*     */         }
/*     */         
/* 210 */         handleDatagram(datagramPacket);
/*     */ 
/*     */ 
/*     */         
/* 214 */         if (this.isClosing) {
/*     */           break;
/*     */         }
/* 217 */       } catch (IOException iOException) {
/*     */ 
/*     */ 
/*     */         
/* 221 */         if (this.isClosing) {
/*     */           break;
/*     */         }
/* 224 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 225 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSocket.class.getName(), "run", "IOEXception while receiving datagram", iOException);
/*     */         }
/*     */       }
/* 228 */       catch (Exception exception) {
/*     */ 
/*     */ 
/*     */         
/* 232 */         if (this.isClosing) {
/*     */           break;
/*     */         }
/* 235 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 236 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSocket.class.getName(), "run", "Exception in socket thread...", exception);
/*     */         }
/*     */       }
/* 239 */       catch (ThreadDeath threadDeath) {
/* 240 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 241 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSocket.class.getName(), "run", "Socket Thread DEAD..." + 
/* 242 */               toString(), threadDeath);
/*     */         }
/* 244 */         close();
/* 245 */         throw threadDeath;
/* 246 */       } catch (Error error) {
/* 247 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 248 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSocket.class.getName(), "run", "Got unexpected error", error);
/*     */         }
/*     */         
/* 251 */         handleJavaError(error);
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
/*     */   protected synchronized void finalize() {
/* 264 */     close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void handleJavaError(Throwable paramThrowable) {
/* 274 */     if (paramThrowable instanceof OutOfMemoryError) {
/* 275 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 276 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSocket.class.getName(), "handleJavaError", "OutOfMemory error", paramThrowable);
/*     */       }
/*     */       
/* 279 */       Thread.yield();
/*     */       return;
/*     */     } 
/* 282 */     if (this._socket != null) {
/* 283 */       this._socket.close();
/* 284 */       this._socket = null;
/*     */     } 
/*     */     
/* 287 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 288 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpSocket.class.getName(), "handleJavaError", "Global Internal error");
/*     */     }
/*     */     
/* 291 */     Thread.yield();
/*     */   }
/*     */   
/*     */   private synchronized void handleDatagram(DatagramPacket paramDatagramPacket) {
/* 295 */     this._dgramHdlr.processDatagram(paramDatagramPacket);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SnmpSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */