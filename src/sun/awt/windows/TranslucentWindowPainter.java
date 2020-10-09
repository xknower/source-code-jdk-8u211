/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.awt.Window;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.VolatileImage;
/*     */ import java.security.AccessController;
/*     */ import sun.awt.image.BufImgSurfaceData;
/*     */ import sun.java2d.DestSurfaceProvider;
/*     */ import sun.java2d.InvalidPipeException;
/*     */ import sun.java2d.Surface;
/*     */ import sun.java2d.d3d.D3DSurfaceData;
/*     */ import sun.java2d.opengl.WGLSurfaceData;
/*     */ import sun.java2d.pipe.BufferedContext;
/*     */ import sun.java2d.pipe.RenderQueue;
/*     */ import sun.java2d.pipe.hw.AccelGraphicsConfig;
/*     */ import sun.java2d.pipe.hw.AccelSurface;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class TranslucentWindowPainter
/*     */ {
/*     */   protected Window window;
/*     */   protected WWindowPeer peer;
/*  67 */   private static final boolean forceOpt = Boolean.valueOf(AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.twp.forceopt", "false"))).booleanValue();
/*     */ 
/*     */   
/*  70 */   private static final boolean forceSW = Boolean.valueOf(AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.twp.forcesw", "false"))).booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TranslucentWindowPainter createInstance(WWindowPeer paramWWindowPeer) {
/*  77 */     GraphicsConfiguration graphicsConfiguration = paramWWindowPeer.getGraphicsConfiguration();
/*  78 */     if (!forceSW && graphicsConfiguration instanceof AccelGraphicsConfig) {
/*  79 */       String str = graphicsConfiguration.getClass().getSimpleName();
/*  80 */       AccelGraphicsConfig accelGraphicsConfig = (AccelGraphicsConfig)graphicsConfiguration;
/*     */ 
/*     */       
/*  83 */       if ((accelGraphicsConfig.getContextCapabilities().getCaps() & 0x100) != 0 || forceOpt) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  88 */         if (str.startsWith("D3D"))
/*  89 */           return new VIOptD3DWindowPainter(paramWWindowPeer); 
/*  90 */         if (forceOpt && str.startsWith("WGL"))
/*     */         {
/*     */ 
/*     */           
/*  94 */           return new VIOptWGLWindowPainter(paramWWindowPeer);
/*     */         }
/*     */       } 
/*     */     } 
/*  98 */     return new BIWindowPainter(paramWWindowPeer);
/*     */   }
/*     */   
/*     */   protected TranslucentWindowPainter(WWindowPeer paramWWindowPeer) {
/* 102 */     this.peer = paramWWindowPeer;
/* 103 */     this.window = (Window)paramWWindowPeer.getTarget();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateWindow(boolean paramBoolean) {
/* 132 */     boolean bool = false;
/* 133 */     Image image = getBackBuffer(paramBoolean);
/* 134 */     while (!bool) {
/* 135 */       if (paramBoolean) {
/* 136 */         Graphics2D graphics2D = (Graphics2D)image.getGraphics();
/*     */         try {
/* 138 */           this.window.paintAll(graphics2D);
/*     */         } finally {
/* 140 */           graphics2D.dispose();
/*     */         } 
/*     */       } 
/*     */       
/* 144 */       bool = update(image);
/* 145 */       if (!bool) {
/* 146 */         paramBoolean = true;
/* 147 */         image = getBackBuffer(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final Image clearImage(Image paramImage) {
/* 153 */     Graphics2D graphics2D = (Graphics2D)paramImage.getGraphics();
/* 154 */     int i = paramImage.getWidth(null);
/* 155 */     int j = paramImage.getHeight(null);
/*     */     
/* 157 */     graphics2D.setComposite(AlphaComposite.Src);
/* 158 */     graphics2D.setColor(new Color(0, 0, 0, 0));
/* 159 */     graphics2D.fillRect(0, 0, i, j);
/*     */     
/* 161 */     return paramImage;
/*     */   }
/*     */   
/*     */   protected abstract Image getBackBuffer(boolean paramBoolean);
/*     */   
/*     */   protected abstract boolean update(Image paramImage);
/*     */   
/*     */   public abstract void flush();
/*     */   
/*     */   private static class BIWindowPainter
/*     */     extends TranslucentWindowPainter
/*     */   {
/*     */     private BufferedImage backBuffer;
/*     */     
/*     */     protected BIWindowPainter(WWindowPeer param1WWindowPeer) {
/* 176 */       super(param1WWindowPeer);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Image getBackBuffer(boolean param1Boolean) {
/* 181 */       int i = this.window.getWidth();
/* 182 */       int j = this.window.getHeight();
/* 183 */       if (this.backBuffer == null || this.backBuffer
/* 184 */         .getWidth() != i || this.backBuffer
/* 185 */         .getHeight() != j) {
/*     */         
/* 187 */         flush();
/* 188 */         this.backBuffer = new BufferedImage(i, j, 3);
/*     */       } 
/* 190 */       return param1Boolean ? TranslucentWindowPainter.clearImage(this.backBuffer) : this.backBuffer;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean update(Image param1Image) {
/* 195 */       VolatileImage volatileImage = null;
/*     */       
/* 197 */       if (param1Image instanceof BufferedImage) {
/* 198 */         BufferedImage bufferedImage1 = (BufferedImage)param1Image;
/*     */         
/* 200 */         int[] arrayOfInt1 = ((DataBufferInt)bufferedImage1.getRaster().getDataBuffer()).getData();
/* 201 */         this.peer.updateWindowImpl(arrayOfInt1, bufferedImage1.getWidth(), bufferedImage1.getHeight());
/* 202 */         return true;
/* 203 */       }  if (param1Image instanceof VolatileImage) {
/* 204 */         volatileImage = (VolatileImage)param1Image;
/* 205 */         if (param1Image instanceof DestSurfaceProvider) {
/* 206 */           Surface surface = ((DestSurfaceProvider)param1Image).getDestSurface();
/* 207 */           if (surface instanceof BufImgSurfaceData) {
/*     */ 
/*     */ 
/*     */             
/* 211 */             int i = volatileImage.getWidth();
/* 212 */             int j = volatileImage.getHeight();
/* 213 */             BufImgSurfaceData bufImgSurfaceData = (BufImgSurfaceData)surface;
/*     */             
/* 215 */             int[] arrayOfInt1 = ((DataBufferInt)bufImgSurfaceData.getRaster(0, 0, i, j).getDataBuffer()).getData();
/* 216 */             this.peer.updateWindowImpl(arrayOfInt1, i, j);
/* 217 */             return true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 223 */       BufferedImage bufferedImage = (BufferedImage)TranslucentWindowPainter.clearImage(this.backBuffer);
/*     */ 
/*     */       
/* 226 */       int[] arrayOfInt = ((DataBufferInt)bufferedImage.getRaster().getDataBuffer()).getData();
/* 227 */       this.peer.updateWindowImpl(arrayOfInt, bufferedImage.getWidth(), bufferedImage.getHeight());
/*     */       
/* 229 */       return (volatileImage != null) ? (!volatileImage.contentsLost()) : true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void flush() {
/* 234 */       if (this.backBuffer != null) {
/* 235 */         this.backBuffer.flush();
/* 236 */         this.backBuffer = null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class VIWindowPainter
/*     */     extends BIWindowPainter
/*     */   {
/*     */     private VolatileImage viBB;
/*     */ 
/*     */     
/*     */     protected VIWindowPainter(WWindowPeer param1WWindowPeer) {
/* 250 */       super(param1WWindowPeer);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Image getBackBuffer(boolean param1Boolean) {
/* 255 */       int i = this.window.getWidth();
/* 256 */       int j = this.window.getHeight();
/* 257 */       GraphicsConfiguration graphicsConfiguration = this.peer.getGraphicsConfiguration();
/*     */       
/* 259 */       if (this.viBB == null || this.viBB.getWidth() != i || this.viBB.getHeight() != j || this.viBB
/* 260 */         .validate(graphicsConfiguration) == 2) {
/*     */         
/* 262 */         flush();
/*     */         
/* 264 */         if (graphicsConfiguration instanceof AccelGraphicsConfig) {
/* 265 */           AccelGraphicsConfig accelGraphicsConfig = (AccelGraphicsConfig)graphicsConfiguration;
/* 266 */           this.viBB = accelGraphicsConfig.createCompatibleVolatileImage(i, j, 3, 2);
/*     */         } 
/*     */ 
/*     */         
/* 270 */         if (this.viBB == null) {
/* 271 */           this.viBB = graphicsConfiguration.createCompatibleVolatileImage(i, j, 3);
/*     */         }
/* 273 */         this.viBB.validate(graphicsConfiguration);
/*     */       } 
/*     */       
/* 276 */       return param1Boolean ? TranslucentWindowPainter.clearImage(this.viBB) : this.viBB;
/*     */     }
/*     */ 
/*     */     
/*     */     public void flush() {
/* 281 */       if (this.viBB != null) {
/* 282 */         this.viBB.flush();
/* 283 */         this.viBB = null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class VIOptWindowPainter
/*     */     extends VIWindowPainter
/*     */   {
/*     */     protected VIOptWindowPainter(WWindowPeer param1WWindowPeer) {
/* 296 */       super(param1WWindowPeer);
/*     */     }
/*     */ 
/*     */     
/*     */     protected abstract boolean updateWindowAccel(long param1Long, int param1Int1, int param1Int2);
/*     */     
/*     */     protected boolean update(Image param1Image) {
/* 303 */       if (param1Image instanceof DestSurfaceProvider) {
/* 304 */         Surface surface = ((DestSurfaceProvider)param1Image).getDestSurface();
/* 305 */         if (surface instanceof AccelSurface) {
/* 306 */           final int w = param1Image.getWidth(null);
/* 307 */           final int h = param1Image.getHeight(null);
/* 308 */           final boolean[] arr = { false };
/* 309 */           final AccelSurface as = (AccelSurface)surface;
/* 310 */           RenderQueue renderQueue = accelSurface.getContext().getRenderQueue();
/* 311 */           renderQueue.lock();
/*     */           try {
/* 313 */             BufferedContext.validateContext(accelSurface);
/* 314 */             renderQueue.flushAndInvokeNow(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 317 */                     long l = as.getNativeOps();
/* 318 */                     arr[0] = TranslucentWindowPainter.VIOptWindowPainter.this.updateWindowAccel(l, w, h);
/*     */                   }
/*     */                 });
/* 321 */           } catch (InvalidPipeException invalidPipeException) {
/*     */           
/*     */           } finally {
/* 324 */             renderQueue.unlock();
/*     */           } 
/* 326 */           return arrayOfBoolean[0];
/*     */         } 
/*     */       } 
/* 329 */       return super.update(param1Image);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class VIOptD3DWindowPainter
/*     */     extends VIOptWindowPainter {
/*     */     protected VIOptD3DWindowPainter(WWindowPeer param1WWindowPeer) {
/* 336 */       super(param1WWindowPeer);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean updateWindowAccel(long param1Long, int param1Int1, int param1Int2) {
/* 343 */       return 
/* 344 */         D3DSurfaceData.updateWindowAccelImpl(param1Long, this.peer.getData(), param1Int1, param1Int2);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class VIOptWGLWindowPainter
/*     */     extends VIOptWindowPainter {
/*     */     protected VIOptWGLWindowPainter(WWindowPeer param1WWindowPeer) {
/* 351 */       super(param1WWindowPeer);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean updateWindowAccel(long param1Long, int param1Int1, int param1Int2) {
/* 358 */       return 
/* 359 */         WGLSurfaceData.updateWindowAccelImpl(param1Long, this.peer, param1Int1, param1Int2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\TranslucentWindowPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */