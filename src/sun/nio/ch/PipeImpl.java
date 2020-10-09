/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.Pipe;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PipeImpl
/*     */   extends Pipe
/*     */ {
/*     */   private static final int NUM_SECRET_BYTES = 16;
/*  55 */   private static final Random RANDOM_NUMBER_GENERATOR = new SecureRandom();
/*     */   
/*     */   private Pipe.SourceChannel source;
/*     */   
/*     */   private Pipe.SinkChannel sink;
/*     */ 
/*     */   
/*     */   private class Initializer
/*     */     implements PrivilegedExceptionAction<Void>
/*     */   {
/*     */     private final SelectorProvider sp;
/*     */     
/*  67 */     private IOException ioe = null;
/*     */     
/*     */     private Initializer(SelectorProvider param1SelectorProvider) {
/*  70 */       this.sp = param1SelectorProvider;
/*     */     }
/*     */ 
/*     */     
/*     */     public Void run() throws IOException {
/*  75 */       LoopbackConnector loopbackConnector = new LoopbackConnector();
/*  76 */       loopbackConnector.run();
/*  77 */       if (this.ioe instanceof java.nio.channels.ClosedByInterruptException) {
/*  78 */         this.ioe = null;
/*  79 */         Thread thread = new Thread(loopbackConnector)
/*     */           {
/*     */             public void interrupt() {}
/*     */           };
/*  83 */         thread.start();
/*     */         while (true) {
/*     */           try {
/*  86 */             thread.join();
/*     */             break;
/*  88 */           } catch (InterruptedException interruptedException) {}
/*     */         } 
/*  90 */         Thread.currentThread().interrupt();
/*     */       } 
/*     */       
/*  93 */       if (this.ioe != null) {
/*  94 */         throw new IOException("Unable to establish loopback connection", this.ioe);
/*     */       }
/*  96 */       return null;
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
/*     */     private class LoopbackConnector
/*     */       implements Runnable
/*     */     {
/*     */       private LoopbackConnector() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void run() {
/*     */         // Byte code:
/*     */         //   0: aconst_null
/*     */         //   1: astore_1
/*     */         //   2: aconst_null
/*     */         //   3: astore_2
/*     */         //   4: aconst_null
/*     */         //   5: astore_3
/*     */         //   6: bipush #16
/*     */         //   8: invokestatic allocate : (I)Ljava/nio/ByteBuffer;
/*     */         //   11: astore #4
/*     */         //   13: bipush #16
/*     */         //   15: invokestatic allocate : (I)Ljava/nio/ByteBuffer;
/*     */         //   18: astore #5
/*     */         //   20: ldc '127.0.0.1'
/*     */         //   22: invokestatic getByName : (Ljava/lang/String;)Ljava/net/InetAddress;
/*     */         //   25: astore #6
/*     */         //   27: getstatic sun/nio/ch/PipeImpl$Initializer$LoopbackConnector.$assertionsDisabled : Z
/*     */         //   30: ifne -> 49
/*     */         //   33: aload #6
/*     */         //   35: invokevirtual isLoopbackAddress : ()Z
/*     */         //   38: ifne -> 49
/*     */         //   41: new java/lang/AssertionError
/*     */         //   44: dup
/*     */         //   45: invokespecial <init> : ()V
/*     */         //   48: athrow
/*     */         //   49: aconst_null
/*     */         //   50: astore #7
/*     */         //   52: aload_1
/*     */         //   53: ifnull -> 63
/*     */         //   56: aload_1
/*     */         //   57: invokevirtual isOpen : ()Z
/*     */         //   60: ifne -> 102
/*     */         //   63: invokestatic open : ()Ljava/nio/channels/ServerSocketChannel;
/*     */         //   66: astore_1
/*     */         //   67: aload_1
/*     */         //   68: invokevirtual socket : ()Ljava/net/ServerSocket;
/*     */         //   71: new java/net/InetSocketAddress
/*     */         //   74: dup
/*     */         //   75: aload #6
/*     */         //   77: iconst_0
/*     */         //   78: invokespecial <init> : (Ljava/net/InetAddress;I)V
/*     */         //   81: invokevirtual bind : (Ljava/net/SocketAddress;)V
/*     */         //   84: new java/net/InetSocketAddress
/*     */         //   87: dup
/*     */         //   88: aload #6
/*     */         //   90: aload_1
/*     */         //   91: invokevirtual socket : ()Ljava/net/ServerSocket;
/*     */         //   94: invokevirtual getLocalPort : ()I
/*     */         //   97: invokespecial <init> : (Ljava/net/InetAddress;I)V
/*     */         //   100: astore #7
/*     */         //   102: aload #7
/*     */         //   104: invokestatic open : (Ljava/net/SocketAddress;)Ljava/nio/channels/SocketChannel;
/*     */         //   107: astore_2
/*     */         //   108: invokestatic access$100 : ()Ljava/util/Random;
/*     */         //   111: aload #4
/*     */         //   113: invokevirtual array : ()[B
/*     */         //   116: invokevirtual nextBytes : ([B)V
/*     */         //   119: aload_2
/*     */         //   120: aload #4
/*     */         //   122: invokevirtual write : (Ljava/nio/ByteBuffer;)I
/*     */         //   125: pop
/*     */         //   126: aload #4
/*     */         //   128: invokevirtual hasRemaining : ()Z
/*     */         //   131: ifne -> 119
/*     */         //   134: aload #4
/*     */         //   136: invokevirtual rewind : ()Ljava/nio/Buffer;
/*     */         //   139: pop
/*     */         //   140: aload_1
/*     */         //   141: invokevirtual accept : ()Ljava/nio/channels/SocketChannel;
/*     */         //   144: astore_3
/*     */         //   145: aload_3
/*     */         //   146: aload #5
/*     */         //   148: invokevirtual read : (Ljava/nio/ByteBuffer;)I
/*     */         //   151: pop
/*     */         //   152: aload #5
/*     */         //   154: invokevirtual hasRemaining : ()Z
/*     */         //   157: ifne -> 145
/*     */         //   160: aload #5
/*     */         //   162: invokevirtual rewind : ()Ljava/nio/Buffer;
/*     */         //   165: pop
/*     */         //   166: aload #5
/*     */         //   168: aload #4
/*     */         //   170: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */         //   173: ifeq -> 179
/*     */         //   176: goto -> 190
/*     */         //   179: aload_3
/*     */         //   180: invokevirtual close : ()V
/*     */         //   183: aload_2
/*     */         //   184: invokevirtual close : ()V
/*     */         //   187: goto -> 52
/*     */         //   190: aload_0
/*     */         //   191: getfield this$1 : Lsun/nio/ch/PipeImpl$Initializer;
/*     */         //   194: getfield this$0 : Lsun/nio/ch/PipeImpl;
/*     */         //   197: new sun/nio/ch/SourceChannelImpl
/*     */         //   200: dup
/*     */         //   201: aload_0
/*     */         //   202: getfield this$1 : Lsun/nio/ch/PipeImpl$Initializer;
/*     */         //   205: invokestatic access$300 : (Lsun/nio/ch/PipeImpl$Initializer;)Ljava/nio/channels/spi/SelectorProvider;
/*     */         //   208: aload_2
/*     */         //   209: invokespecial <init> : (Ljava/nio/channels/spi/SelectorProvider;Ljava/nio/channels/SocketChannel;)V
/*     */         //   212: invokestatic access$202 : (Lsun/nio/ch/PipeImpl;Ljava/nio/channels/Pipe$SourceChannel;)Ljava/nio/channels/Pipe$SourceChannel;
/*     */         //   215: pop
/*     */         //   216: aload_0
/*     */         //   217: getfield this$1 : Lsun/nio/ch/PipeImpl$Initializer;
/*     */         //   220: getfield this$0 : Lsun/nio/ch/PipeImpl;
/*     */         //   223: new sun/nio/ch/SinkChannelImpl
/*     */         //   226: dup
/*     */         //   227: aload_0
/*     */         //   228: getfield this$1 : Lsun/nio/ch/PipeImpl$Initializer;
/*     */         //   231: invokestatic access$300 : (Lsun/nio/ch/PipeImpl$Initializer;)Ljava/nio/channels/spi/SelectorProvider;
/*     */         //   234: aload_3
/*     */         //   235: invokespecial <init> : (Ljava/nio/channels/spi/SelectorProvider;Ljava/nio/channels/SocketChannel;)V
/*     */         //   238: invokestatic access$402 : (Lsun/nio/ch/PipeImpl;Ljava/nio/channels/Pipe$SinkChannel;)Ljava/nio/channels/Pipe$SinkChannel;
/*     */         //   241: pop
/*     */         //   242: aload_1
/*     */         //   243: ifnull -> 250
/*     */         //   246: aload_1
/*     */         //   247: invokevirtual close : ()V
/*     */         //   250: goto -> 325
/*     */         //   253: astore #4
/*     */         //   255: goto -> 325
/*     */         //   258: astore #4
/*     */         //   260: aload_2
/*     */         //   261: ifnull -> 268
/*     */         //   264: aload_2
/*     */         //   265: invokevirtual close : ()V
/*     */         //   268: aload_3
/*     */         //   269: ifnull -> 276
/*     */         //   272: aload_3
/*     */         //   273: invokevirtual close : ()V
/*     */         //   276: goto -> 281
/*     */         //   279: astore #5
/*     */         //   281: aload_0
/*     */         //   282: getfield this$1 : Lsun/nio/ch/PipeImpl$Initializer;
/*     */         //   285: aload #4
/*     */         //   287: invokestatic access$502 : (Lsun/nio/ch/PipeImpl$Initializer;Ljava/io/IOException;)Ljava/io/IOException;
/*     */         //   290: pop
/*     */         //   291: aload_1
/*     */         //   292: ifnull -> 299
/*     */         //   295: aload_1
/*     */         //   296: invokevirtual close : ()V
/*     */         //   299: goto -> 325
/*     */         //   302: astore #4
/*     */         //   304: goto -> 325
/*     */         //   307: astore #8
/*     */         //   309: aload_1
/*     */         //   310: ifnull -> 317
/*     */         //   313: aload_1
/*     */         //   314: invokevirtual close : ()V
/*     */         //   317: goto -> 322
/*     */         //   320: astore #9
/*     */         //   322: aload #8
/*     */         //   324: athrow
/*     */         //   325: return
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #103	-> 0
/*     */         //   #104	-> 2
/*     */         //   #105	-> 4
/*     */         //   #109	-> 6
/*     */         //   #110	-> 13
/*     */         //   #113	-> 20
/*     */         //   #114	-> 27
/*     */         //   #115	-> 49
/*     */         //   #119	-> 52
/*     */         //   #120	-> 63
/*     */         //   #121	-> 67
/*     */         //   #122	-> 84
/*     */         //   #127	-> 102
/*     */         //   #128	-> 108
/*     */         //   #130	-> 119
/*     */         //   #131	-> 126
/*     */         //   #132	-> 134
/*     */         //   #135	-> 140
/*     */         //   #137	-> 145
/*     */         //   #138	-> 152
/*     */         //   #139	-> 160
/*     */         //   #141	-> 166
/*     */         //   #142	-> 176
/*     */         //   #144	-> 179
/*     */         //   #145	-> 183
/*     */         //   #149	-> 190
/*     */         //   #150	-> 216
/*     */         //   #161	-> 242
/*     */         //   #162	-> 246
/*     */         //   #163	-> 250
/*     */         //   #164	-> 255
/*     */         //   #151	-> 258
/*     */         //   #153	-> 260
/*     */         //   #154	-> 264
/*     */         //   #155	-> 268
/*     */         //   #156	-> 272
/*     */         //   #157	-> 276
/*     */         //   #158	-> 281
/*     */         //   #161	-> 291
/*     */         //   #162	-> 295
/*     */         //   #163	-> 299
/*     */         //   #164	-> 304
/*     */         //   #160	-> 307
/*     */         //   #161	-> 309
/*     */         //   #162	-> 313
/*     */         //   #163	-> 317
/*     */         //   #164	-> 322
/*     */         //   #165	-> 325
/*     */         // Exception table:
/*     */         //   from	to	target	type
/*     */         //   6	242	258	java/io/IOException
/*     */         //   6	242	307	finally
/*     */         //   242	250	253	java/io/IOException
/*     */         //   258	291	307	finally
/*     */         //   260	276	279	java/io/IOException
/*     */         //   291	299	302	java/io/IOException
/*     */         //   307	309	307	finally
/*     */         //   309	317	320	java/io/IOException
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PipeImpl(SelectorProvider paramSelectorProvider) throws IOException {
/*     */     try {
/* 171 */       AccessController.doPrivileged(new Initializer(paramSelectorProvider));
/* 172 */     } catch (PrivilegedActionException privilegedActionException) {
/* 173 */       throw (IOException)privilegedActionException.getCause();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Pipe.SourceChannel source() {
/* 178 */     return this.source;
/*     */   }
/*     */   
/*     */   public Pipe.SinkChannel sink() {
/* 182 */     return this.sink;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\PipeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */