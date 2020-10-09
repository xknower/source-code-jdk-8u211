/*     */ package sun.net;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Proxy;
/*     */ import java.net.Socket;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetworkClient
/*     */ {
/*     */   public static final int DEFAULT_READ_TIMEOUT = -1;
/*     */   public static final int DEFAULT_CONNECT_TIMEOUT = -1;
/*  49 */   protected Proxy proxy = Proxy.NO_PROXY;
/*     */   
/*  51 */   protected Socket serverSocket = null;
/*     */ 
/*     */   
/*     */   public PrintStream serverOutput;
/*     */   
/*     */   public InputStream serverInput;
/*     */   
/*     */   protected static int defaultSoTimeout;
/*     */   
/*     */   protected static int defaultConnectTimeout;
/*     */   
/*  62 */   protected int readTimeout = -1;
/*  63 */   protected int connectTimeout = -1;
/*     */   
/*     */   protected static String encoding;
/*     */   
/*     */   static {
/*  68 */     final int[] vals = { 0, 0 };
/*  69 */     final String[] encs = { null };
/*     */     
/*  71 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  74 */             vals[0] = Integer.getInteger("sun.net.client.defaultReadTimeout", 0).intValue();
/*  75 */             vals[1] = Integer.getInteger("sun.net.client.defaultConnectTimeout", 0).intValue();
/*  76 */             encs[0] = System.getProperty("file.encoding", "ISO8859_1");
/*  77 */             return null;
/*     */           }
/*     */         });
/*  80 */     if (arrayOfInt[0] != 0) {
/*  81 */       defaultSoTimeout = arrayOfInt[0];
/*     */     }
/*  83 */     if (arrayOfInt[1] != 0) {
/*  84 */       defaultConnectTimeout = arrayOfInt[1];
/*     */     }
/*     */     
/*  87 */     encoding = arrayOfString[0];
/*     */     try {
/*  89 */       if (!isASCIISuperset(encoding)) {
/*  90 */         encoding = "ISO8859_1";
/*     */       }
/*  92 */     } catch (Exception exception) {
/*  93 */       encoding = "ISO8859_1";
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
/*     */   private static boolean isASCIISuperset(String paramString) throws Exception {
/* 117 */     String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_.!~*'();/?:@&=+$,";
/*     */ 
/*     */ 
/*     */     
/* 121 */     byte[] arrayOfByte1 = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 45, 95, 46, 33, 126, 42, 39, 40, 41, 59, 47, 63, 58, 64, 38, 61, 43, 36, 44 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     byte[] arrayOfByte2 = str.getBytes(paramString);
/* 128 */     return Arrays.equals(arrayOfByte2, arrayOfByte1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void openServer(String paramString, int paramInt) throws IOException, UnknownHostException {
/* 134 */     if (this.serverSocket != null)
/* 135 */       closeServer(); 
/* 136 */     this.serverSocket = doConnect(paramString, paramInt);
/*     */     try {
/* 138 */       this
/* 139 */         .serverOutput = new PrintStream(new BufferedOutputStream(this.serverSocket.getOutputStream()), true, encoding);
/*     */     }
/* 141 */     catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 142 */       throw new InternalError(encoding + "encoding not found", unsupportedEncodingException);
/*     */     } 
/* 144 */     this.serverInput = new BufferedInputStream(this.serverSocket.getInputStream());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Socket doConnect(String paramString, int paramInt) throws IOException, UnknownHostException {
/*     */     Socket socket;
/* 154 */     if (this.proxy != null) {
/* 155 */       if (this.proxy.type() == Proxy.Type.SOCKS) {
/* 156 */         socket = AccessController.<Socket>doPrivileged(new PrivilegedAction<Socket>()
/*     */             {
/*     */               public Socket run() {
/* 159 */                 return new Socket(NetworkClient.this.proxy); }
/*     */             });
/* 161 */       } else if (this.proxy.type() == Proxy.Type.DIRECT) {
/* 162 */         socket = createSocket();
/*     */       }
/*     */       else {
/*     */         
/* 166 */         socket = new Socket(Proxy.NO_PROXY);
/*     */       } 
/*     */     } else {
/* 169 */       socket = createSocket();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 174 */     if (this.connectTimeout >= 0) {
/* 175 */       socket.connect(new InetSocketAddress(paramString, paramInt), this.connectTimeout);
/*     */     }
/* 177 */     else if (defaultConnectTimeout > 0) {
/* 178 */       socket.connect(new InetSocketAddress(paramString, paramInt), defaultConnectTimeout);
/*     */     } else {
/* 180 */       socket.connect(new InetSocketAddress(paramString, paramInt));
/*     */     } 
/*     */     
/* 183 */     if (this.readTimeout >= 0) {
/* 184 */       socket.setSoTimeout(this.readTimeout);
/* 185 */     } else if (defaultSoTimeout > 0) {
/* 186 */       socket.setSoTimeout(defaultSoTimeout);
/*     */     } 
/* 188 */     return socket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Socket createSocket() throws IOException {
/* 197 */     return new Socket();
/*     */   }
/*     */   
/*     */   protected InetAddress getLocalAddress() throws IOException {
/* 201 */     if (this.serverSocket == null)
/* 202 */       throw new IOException("not connected"); 
/* 203 */     return AccessController.<InetAddress>doPrivileged(new PrivilegedAction<InetAddress>()
/*     */         {
/*     */           public InetAddress run() {
/* 206 */             return NetworkClient.this.serverSocket.getLocalAddress();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeServer() throws IOException {
/* 214 */     if (!serverIsOpen()) {
/*     */       return;
/*     */     }
/* 217 */     this.serverSocket.close();
/* 218 */     this.serverSocket = null;
/* 219 */     this.serverInput = null;
/* 220 */     this.serverOutput = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean serverIsOpen() {
/* 225 */     return (this.serverSocket != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public NetworkClient(String paramString, int paramInt) throws IOException {
/* 230 */     openServer(paramString, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConnectTimeout(int paramInt) {
/* 236 */     this.connectTimeout = paramInt;
/*     */   }
/*     */   
/*     */   public int getConnectTimeout() {
/* 240 */     return this.connectTimeout;
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
/*     */   public void setReadTimeout(int paramInt) {
/* 257 */     if (paramInt == -1) {
/* 258 */       paramInt = defaultSoTimeout;
/*     */     }
/* 260 */     if (this.serverSocket != null && paramInt >= 0) {
/*     */       try {
/* 262 */         this.serverSocket.setSoTimeout(paramInt);
/* 263 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */     
/* 267 */     this.readTimeout = paramInt;
/*     */   }
/*     */   
/*     */   public int getReadTimeout() {
/* 271 */     return this.readTimeout;
/*     */   }
/*     */   
/*     */   public NetworkClient() {}
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\net\NetworkClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */