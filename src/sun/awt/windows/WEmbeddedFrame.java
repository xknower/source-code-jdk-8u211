/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.AWTKeyStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.InvocationEvent;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.awt.EmbeddedFrame;
/*     */ import sun.awt.image.ByteInterleavedRaster;
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
/*     */ public class WEmbeddedFrame
/*     */   extends EmbeddedFrame
/*     */ {
/*     */   private long handle;
/*     */   
/*     */   static {
/*  41 */     initIDs();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  46 */   private int bandWidth = 0;
/*  47 */   private int bandHeight = 0;
/*  48 */   private int imgWid = 0;
/*  49 */   private int imgHgt = 0;
/*     */   
/*  51 */   private static int pScale = 0;
/*     */ 
/*     */   
/*     */   private static final int MAX_BAND_SIZE = 30720;
/*     */ 
/*     */   
/*     */   private boolean isEmbeddedInIE = false;
/*     */   
/*  59 */   private static String printScale = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.print.pluginscalefactor"));
/*     */ 
/*     */   
/*     */   public WEmbeddedFrame() {
/*  63 */     this(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public WEmbeddedFrame(int paramInt) {
/*  71 */     this(paramInt);
/*     */   }
/*     */   
/*     */   public WEmbeddedFrame(long paramLong) {
/*  75 */     this.handle = paramLong;
/*  76 */     if (paramLong != 0L) {
/*  77 */       addNotify();
/*  78 */       show();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addNotify() {
/*  84 */     if (getPeer() == null) {
/*  85 */       WToolkit wToolkit = (WToolkit)Toolkit.getDefaultToolkit();
/*  86 */       setPeer(wToolkit.createEmbeddedFrame(this));
/*     */     } 
/*  88 */     super.addNotify();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getEmbedderHandle() {
/*  95 */     return this.handle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void print(long paramLong) {
/* 103 */     BufferedImage bufferedImage = null;
/*     */     
/* 105 */     int i = 1;
/* 106 */     int j = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     if (isPrinterDC(paramLong)) {
/* 127 */       i = j = getPrintScaleFactor();
/*     */     }
/*     */     
/* 130 */     int k = getHeight();
/* 131 */     if (bufferedImage == null) {
/* 132 */       this.bandWidth = getWidth();
/* 133 */       if (this.bandWidth % 4 != 0) {
/* 134 */         this.bandWidth += 4 - this.bandWidth % 4;
/*     */       }
/* 136 */       if (this.bandWidth <= 0) {
/*     */         return;
/*     */       }
/*     */       
/* 140 */       this.bandHeight = Math.min(30720 / this.bandWidth, k);
/*     */       
/* 142 */       this.imgWid = this.bandWidth * i;
/* 143 */       this.imgHgt = this.bandHeight * j;
/* 144 */       bufferedImage = new BufferedImage(this.imgWid, this.imgHgt, 5);
/*     */     } 
/*     */ 
/*     */     
/* 148 */     Graphics graphics = bufferedImage.getGraphics();
/* 149 */     graphics.setColor(Color.white);
/* 150 */     Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
/* 151 */     graphics2D.translate(0, this.imgHgt);
/* 152 */     graphics2D.scale(i, -j);
/*     */     
/* 154 */     ByteInterleavedRaster byteInterleavedRaster = (ByteInterleavedRaster)bufferedImage.getRaster();
/* 155 */     byte[] arrayOfByte = byteInterleavedRaster.getDataStorage();
/*     */     int m;
/* 157 */     for (m = 0; m < k; m += this.bandHeight) {
/* 158 */       graphics.fillRect(0, 0, this.bandWidth, this.bandHeight);
/*     */       
/* 160 */       printComponents(graphics2D);
/* 161 */       int n = 0;
/* 162 */       int i1 = this.bandHeight;
/* 163 */       int i2 = this.imgHgt;
/* 164 */       if (m + this.bandHeight > k) {
/*     */         
/* 166 */         i1 = k - m;
/* 167 */         i2 = i1 * j;
/*     */ 
/*     */         
/* 170 */         n = this.imgWid * (this.imgHgt - i2) * 3;
/*     */       } 
/*     */       
/* 173 */       printBand(paramLong, arrayOfByte, n, 0, 0, this.imgWid, i2, 0, m, this.bandWidth, i1);
/*     */ 
/*     */       
/* 176 */       graphics2D.translate(0, -this.bandHeight);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static int getPrintScaleFactor() {
/* 182 */     if (pScale != 0)
/* 183 */       return pScale; 
/* 184 */     if (printScale == null)
/*     */     {
/*     */       
/* 187 */       printScale = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */           {
/*     */             public String run() {
/* 190 */               return System.getenv("JAVA2D_PLUGIN_PRINT_SCALE");
/*     */             }
/*     */           });
/*     */     }
/*     */     
/* 195 */     byte b = 4;
/* 196 */     int i = b;
/* 197 */     if (printScale != null) {
/*     */       try {
/* 199 */         i = Integer.parseInt(printScale);
/* 200 */         if (i > 8 || i < 1) {
/* 201 */           i = b;
/*     */         }
/* 203 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */     
/* 206 */     pScale = i;
/* 207 */     return pScale;
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
/*     */   public void activateEmbeddingTopLevel() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void synthesizeWindowActivation(boolean paramBoolean) {
/* 236 */     if (!paramBoolean || EventQueue.isDispatchThread()) {
/* 237 */       ((WFramePeer)getPeer()).emulateActivation(paramBoolean);
/*     */     }
/*     */     else {
/*     */       
/* 241 */       Runnable runnable = new Runnable() {
/*     */           public void run() {
/* 243 */             ((WFramePeer)WEmbeddedFrame.this.getPeer()).emulateActivation(true);
/*     */           }
/*     */         };
/* 246 */       WToolkit.postEvent(WToolkit.targetToAppContext(this), new InvocationEvent(this, runnable));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerAccelerator(AWTKeyStroke paramAWTKeyStroke) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterAccelerator(AWTKeyStroke paramAWTKeyStroke) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyModalBlocked(Dialog paramDialog, boolean paramBoolean) {
/*     */     try {
/* 268 */       ComponentPeer componentPeer1 = (ComponentPeer)WToolkit.targetToPeer(this);
/* 269 */       ComponentPeer componentPeer2 = (ComponentPeer)WToolkit.targetToPeer(paramDialog);
/* 270 */       notifyModalBlockedImpl((WEmbeddedFramePeer)componentPeer1, (WWindowPeer)componentPeer2, paramBoolean);
/*     */     }
/* 272 */     catch (Exception exception) {
/* 273 */       exception.printStackTrace(System.err);
/*     */     } 
/*     */   }
/*     */   
/*     */   private native boolean isPrinterDC(long paramLong);
/*     */   
/*     */   private native void printBand(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9);
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   native void notifyModalBlockedImpl(WEmbeddedFramePeer paramWEmbeddedFramePeer, WWindowPeer paramWWindowPeer, boolean paramBoolean);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WEmbeddedFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */