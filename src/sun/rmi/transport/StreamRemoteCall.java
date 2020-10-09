/*     */ package sun.rmi.transport;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.UnmarshalException;
/*     */ import java.rmi.server.ObjID;
/*     */ import java.rmi.server.RemoteCall;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.server.UnicastRef;
/*     */ import sun.rmi.transport.tcp.TCPEndpoint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamRemoteCall
/*     */   implements RemoteCall
/*     */ {
/*  50 */   private ConnectionInputStream in = null;
/*  51 */   private ConnectionOutputStream out = null;
/*     */   private Connection conn;
/*     */   private boolean resultStarted = false;
/*  54 */   private Exception serverException = null;
/*     */   
/*     */   public StreamRemoteCall(Connection paramConnection) {
/*  57 */     this.conn = paramConnection;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StreamRemoteCall(Connection paramConnection, ObjID paramObjID, int paramInt, long paramLong) throws RemoteException {
/*     */     try {
/*  64 */       this.conn = paramConnection;
/*  65 */       Transport.transportLog.log(Log.VERBOSE, "write remote call header...");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  70 */       this.conn.getOutputStream().write(80);
/*  71 */       getOutputStream();
/*  72 */       paramObjID.write(this.out);
/*     */       
/*  74 */       this.out.writeInt(paramInt);
/*  75 */       this.out.writeLong(paramLong);
/*  76 */     } catch (IOException iOException) {
/*  77 */       throw new MarshalException("Error marshaling call header", iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() {
/*  85 */     return this.conn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOutput getOutputStream() throws IOException {
/*  93 */     return getOutputStream(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ObjectOutput getOutputStream(boolean paramBoolean) throws IOException {
/*  99 */     if (this.out == null) {
/* 100 */       Transport.transportLog.log(Log.VERBOSE, "getting output stream");
/*     */       
/* 102 */       this.out = new ConnectionOutputStream(this.conn, paramBoolean);
/*     */     } 
/* 104 */     return this.out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseOutputStream() throws IOException {
/*     */     try {
/* 113 */       if (this.out != null) {
/*     */         try {
/* 115 */           this.out.flush();
/*     */         } finally {
/* 117 */           this.out.done();
/*     */         } 
/*     */       }
/* 120 */       this.conn.releaseOutputStream();
/*     */     } finally {
/* 122 */       this.out = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectInput getInputStream() throws IOException {
/* 131 */     if (this.in == null) {
/* 132 */       Transport.transportLog.log(Log.VERBOSE, "getting input stream");
/*     */       
/* 134 */       this.in = new ConnectionInputStream(this.conn.getInputStream());
/*     */     } 
/* 136 */     return this.in;
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
/*     */   public void releaseInputStream() throws IOException {
/*     */     try {
/* 149 */       if (this.in != null) {
/*     */         
/*     */         try {
/* 152 */           this.in.done();
/* 153 */         } catch (RuntimeException runtimeException) {}
/*     */ 
/*     */ 
/*     */         
/* 157 */         this.in.registerRefs();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 162 */         this.in.done(this.conn);
/*     */       } 
/* 164 */       this.conn.releaseInputStream();
/*     */     } finally {
/* 166 */       this.in = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void discardPendingRefs() {
/* 174 */     this.in.discardRefs();
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
/*     */   public ObjectOutput getResultStream(boolean paramBoolean) throws IOException {
/* 188 */     if (this.resultStarted) {
/* 189 */       throw new StreamCorruptedException("result already in progress");
/*     */     }
/* 191 */     this.resultStarted = true;
/*     */ 
/*     */ 
/*     */     
/* 195 */     DataOutputStream dataOutputStream = new DataOutputStream(this.conn.getOutputStream());
/* 196 */     dataOutputStream.writeByte(81);
/* 197 */     getOutputStream(true);
/*     */     
/* 199 */     if (paramBoolean) {
/* 200 */       this.out.writeByte(1);
/*     */     } else {
/* 202 */       this.out.writeByte(2);
/* 203 */     }  this.out.writeID();
/* 204 */     return this.out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeCall() throws Exception {
/*     */     byte b;
/*     */     Object object;
/* 215 */     DGCAckHandler dGCAckHandler = null;
/*     */     try {
/* 217 */       if (this.out != null) {
/* 218 */         dGCAckHandler = this.out.getDGCAckHandler();
/*     */       }
/* 220 */       releaseOutputStream();
/* 221 */       object = new DataInputStream(this.conn.getInputStream());
/* 222 */       byte b1 = object.readByte();
/* 223 */       if (b1 != 81) {
/* 224 */         if (Transport.transportLog.isLoggable(Log.BRIEF)) {
/* 225 */           Transport.transportLog.log(Log.BRIEF, "transport return code invalid: " + b1);
/*     */         }
/*     */         
/* 228 */         throw new UnmarshalException("Transport return code invalid");
/*     */       } 
/* 230 */       getInputStream();
/* 231 */       b = this.in.readByte();
/* 232 */       this.in.readID();
/* 233 */     } catch (UnmarshalException null) {
/* 234 */       throw object;
/* 235 */     } catch (IOException null) {
/* 236 */       throw new UnmarshalException("Error unmarshaling return header", object);
/*     */     } finally {
/*     */       
/* 239 */       if (dGCAckHandler != null) {
/* 240 */         dGCAckHandler.release();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 245 */     switch (b) {
/*     */       case 1:
/*     */         return;
/*     */ 
/*     */       
/*     */       case 2:
/*     */         try {
/* 252 */           object = this.in.readObject();
/* 253 */         } catch (Exception exception) {
/* 254 */           throw new UnmarshalException("Error unmarshaling return", exception);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 259 */         if (object instanceof Exception) {
/* 260 */           exceptionReceivedFromServer((Exception)object); break;
/*     */         } 
/* 262 */         throw new UnmarshalException("Return type not Exception");
/*     */     } 
/*     */ 
/*     */     
/* 266 */     if (Transport.transportLog.isLoggable(Log.BRIEF)) {
/* 267 */       Transport.transportLog.log(Log.BRIEF, "return code invalid: " + b);
/*     */     }
/*     */     
/* 270 */     throw new UnmarshalException("Return code invalid");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void exceptionReceivedFromServer(Exception paramException) throws Exception {
/* 280 */     this.serverException = paramException;
/*     */     
/* 282 */     StackTraceElement[] arrayOfStackTraceElement1 = paramException.getStackTrace();
/* 283 */     StackTraceElement[] arrayOfStackTraceElement2 = (new Throwable()).getStackTrace();
/* 284 */     StackTraceElement[] arrayOfStackTraceElement3 = new StackTraceElement[arrayOfStackTraceElement1.length + arrayOfStackTraceElement2.length];
/*     */     
/* 286 */     System.arraycopy(arrayOfStackTraceElement1, 0, arrayOfStackTraceElement3, 0, arrayOfStackTraceElement1.length);
/*     */     
/* 288 */     System.arraycopy(arrayOfStackTraceElement2, 0, arrayOfStackTraceElement3, arrayOfStackTraceElement1.length, arrayOfStackTraceElement2.length);
/*     */     
/* 290 */     paramException.setStackTrace(arrayOfStackTraceElement3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 296 */     if (UnicastRef.clientCallLog.isLoggable(Log.BRIEF)) {
/*     */       
/* 298 */       TCPEndpoint tCPEndpoint = (TCPEndpoint)this.conn.getChannel().getEndpoint();
/* 299 */       UnicastRef.clientCallLog.log(Log.BRIEF, "outbound call received exception: [" + tCPEndpoint
/* 300 */           .getHost() + ":" + tCPEndpoint
/* 301 */           .getPort() + "] exception: ", paramException);
/*     */     } 
/*     */     
/* 304 */     throw paramException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exception getServerException() {
/* 312 */     return this.serverException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void done() throws IOException {
/* 320 */     releaseInputStream();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\rmi\transport\StreamRemoteCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */