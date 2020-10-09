/*     */ package sun.java2d.d3d;
/*     */ 
/*     */ import sun.java2d.pipe.BufferedContext;
/*     */ import sun.java2d.pipe.RenderBuffer;
/*     */ import sun.java2d.pipe.RenderQueue;
/*     */ import sun.java2d.pipe.hw.ContextCapabilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class D3DContext
/*     */   extends BufferedContext
/*     */ {
/*     */   private final D3DGraphicsDevice device;
/*     */   
/*     */   D3DContext(RenderQueue paramRenderQueue, D3DGraphicsDevice paramD3DGraphicsDevice) {
/*  46 */     super(paramRenderQueue);
/*  47 */     this.device = paramD3DGraphicsDevice;
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
/*     */   static void invalidateCurrentContext() {
/*  62 */     if (currentContext != null) {
/*  63 */       currentContext.invalidateContext();
/*  64 */       currentContext = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     D3DRenderQueue d3DRenderQueue = D3DRenderQueue.getInstance();
/*  71 */     d3DRenderQueue.ensureCapacity(4);
/*  72 */     d3DRenderQueue.getBuffer().putInt(75);
/*  73 */     d3DRenderQueue.flushNow();
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
/*     */   static void setScratchSurface(D3DContext paramD3DContext) {
/*  92 */     if (paramD3DContext != currentContext) {
/*  93 */       currentContext = null;
/*     */     }
/*     */ 
/*     */     
/*  97 */     D3DRenderQueue d3DRenderQueue = D3DRenderQueue.getInstance();
/*  98 */     RenderBuffer renderBuffer = d3DRenderQueue.getBuffer();
/*  99 */     d3DRenderQueue.ensureCapacity(8);
/* 100 */     renderBuffer.putInt(71);
/* 101 */     renderBuffer.putInt(paramD3DContext.getDevice().getScreen());
/*     */   }
/*     */   
/*     */   public RenderQueue getRenderQueue() {
/* 105 */     return D3DRenderQueue.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveState() {
/* 113 */     invalidateContext();
/* 114 */     invalidateCurrentContext();
/*     */     
/* 116 */     setScratchSurface(this);
/*     */ 
/*     */     
/* 119 */     this.rq.ensureCapacity(4);
/* 120 */     this.buf.putInt(78);
/* 121 */     this.rq.flushNow();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restoreState() {
/* 129 */     invalidateContext();
/* 130 */     invalidateCurrentContext();
/*     */     
/* 132 */     setScratchSurface(this);
/*     */ 
/*     */     
/* 135 */     this.rq.ensureCapacity(4);
/* 136 */     this.buf.putInt(79);
/* 137 */     this.rq.flushNow();
/*     */   }
/*     */   
/*     */   D3DGraphicsDevice getDevice() {
/* 141 */     return this.device;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class D3DContextCaps
/*     */     extends ContextCapabilities
/*     */   {
/*     */     static final int CAPS_LCD_SHADER = 65536;
/*     */ 
/*     */ 
/*     */     
/*     */     static final int CAPS_BIOP_SHADER = 131072;
/*     */ 
/*     */ 
/*     */     
/*     */     static final int CAPS_DEVICE_OK = 262144;
/*     */ 
/*     */ 
/*     */     
/*     */     static final int CAPS_AA_SHADER = 524288;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     D3DContextCaps(int param1Int, String param1String) {
/* 169 */       super(param1Int, param1String);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 174 */       StringBuffer stringBuffer = new StringBuffer(super.toString());
/* 175 */       if ((this.caps & 0x10000) != 0) {
/* 176 */         stringBuffer.append("CAPS_LCD_SHADER|");
/*     */       }
/* 178 */       if ((this.caps & 0x20000) != 0) {
/* 179 */         stringBuffer.append("CAPS_BIOP_SHADER|");
/*     */       }
/* 181 */       if ((this.caps & 0x80000) != 0) {
/* 182 */         stringBuffer.append("CAPS_AA_SHADER|");
/*     */       }
/* 184 */       if ((this.caps & 0x40000) != 0) {
/* 185 */         stringBuffer.append("CAPS_DEVICE_OK|");
/*     */       }
/* 187 */       return stringBuffer.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\d3d\D3DContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */