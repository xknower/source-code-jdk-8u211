/*     */ package sun.java2d.windows;
/*     */ 
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import sun.awt.Win32GraphicsConfig;
/*     */ import sun.awt.Win32GraphicsDevice;
/*     */ import sun.awt.windows.WComponentPeer;
/*     */ import sun.java2d.InvalidPipeException;
/*     */ import sun.java2d.ScreenUpdateManager;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.SurfaceDataProxy;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.GraphicsPrimitive;
/*     */ import sun.java2d.loops.RenderLoops;
/*     */ import sun.java2d.loops.SurfaceType;
/*     */ import sun.java2d.loops.XORComposite;
/*     */ import sun.java2d.pipe.PixelToShapeConverter;
/*     */ import sun.java2d.pipe.Region;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDIWindowSurfaceData
/*     */   extends SurfaceData
/*     */ {
/*     */   private WComponentPeer peer;
/*     */   private Win32GraphicsConfig graphicsConfig;
/*     */   private RenderLoops solidloops;
/*     */   public static final String DESC_GDI = "GDI";
/*  63 */   public static final SurfaceType AnyGdi = SurfaceType.IntRgb
/*  64 */     .deriveSubType("GDI");
/*     */   
/*  66 */   public static final SurfaceType IntRgbGdi = SurfaceType.IntRgb
/*  67 */     .deriveSubType("GDI");
/*     */   
/*  69 */   public static final SurfaceType Ushort565RgbGdi = SurfaceType.Ushort565Rgb
/*  70 */     .deriveSubType("GDI");
/*     */   
/*  72 */   public static final SurfaceType Ushort555RgbGdi = SurfaceType.Ushort555Rgb
/*  73 */     .deriveSubType("GDI");
/*     */   
/*  75 */   public static final SurfaceType ThreeByteBgrGdi = SurfaceType.ThreeByteBgr
/*  76 */     .deriveSubType("GDI");
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  81 */     initIDs(XORComposite.class);
/*  82 */     if (WindowsFlags.isGdiBlitEnabled())
/*     */     {
/*  84 */       GDIBlitLoops.register();
/*     */     }
/*     */   }
/*     */   
/*     */   public static SurfaceType getSurfaceType(ColorModel paramColorModel) {
/*  89 */     switch (paramColorModel.getPixelSize()) {
/*     */       case 24:
/*     */       case 32:
/*  92 */         if (paramColorModel instanceof DirectColorModel) {
/*  93 */           if (((DirectColorModel)paramColorModel).getRedMask() == 16711680) {
/*  94 */             return IntRgbGdi;
/*     */           }
/*  96 */           return SurfaceType.IntRgbx;
/*     */         } 
/*     */         
/*  99 */         return ThreeByteBgrGdi;
/*     */       
/*     */       case 15:
/* 102 */         return Ushort555RgbGdi;
/*     */       case 16:
/* 104 */         if (paramColorModel instanceof DirectColorModel && ((DirectColorModel)paramColorModel)
/* 105 */           .getBlueMask() == 62)
/*     */         {
/* 107 */           return SurfaceType.Ushort555Rgbx;
/*     */         }
/* 109 */         return Ushort565RgbGdi;
/*     */       
/*     */       case 8:
/* 112 */         if (paramColorModel.getColorSpace().getType() == 6 && paramColorModel instanceof java.awt.image.ComponentColorModel)
/*     */         {
/* 114 */           return SurfaceType.ByteGray; } 
/* 115 */         if (paramColorModel instanceof IndexColorModel && 
/* 116 */           isOpaqueGray((IndexColorModel)paramColorModel)) {
/* 117 */           return SurfaceType.Index8Gray;
/*     */         }
/* 119 */         return SurfaceType.ByteIndexedOpaque;
/*     */     } 
/*     */     
/* 122 */     throw new InvalidPipeException("Unsupported bit depth: " + paramColorModel
/*     */         
/* 124 */         .getPixelSize());
/*     */   }
/*     */ 
/*     */   
/*     */   public static GDIWindowSurfaceData createData(WComponentPeer paramWComponentPeer) {
/* 129 */     SurfaceType surfaceType = getSurfaceType(paramWComponentPeer.getDeviceColorModel());
/* 130 */     return new GDIWindowSurfaceData(paramWComponentPeer, surfaceType);
/*     */   }
/*     */ 
/*     */   
/*     */   public SurfaceDataProxy makeProxyFor(SurfaceData paramSurfaceData) {
/* 135 */     return SurfaceDataProxy.UNCACHED;
/*     */   }
/*     */   
/*     */   public Raster getRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 139 */     throw new InternalError("not implemented yet");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   protected static GDIRenderer gdiPipe = new GDIRenderer(); static {
/* 147 */     if (GraphicsPrimitive.tracingEnabled())
/* 148 */       gdiPipe = gdiPipe.traceWrap(); 
/*     */   }
/* 150 */   protected static PixelToShapeConverter gdiTxPipe = new PixelToShapeConverter(gdiPipe);
/*     */ 
/*     */ 
/*     */   
/*     */   public void validatePipe(SunGraphics2D paramSunGraphics2D) {
/* 155 */     if (paramSunGraphics2D.antialiasHint != 2 && paramSunGraphics2D.paintState <= 1 && (paramSunGraphics2D.compositeState <= 0 || paramSunGraphics2D.compositeState == 2)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 160 */       if (paramSunGraphics2D.clipState == 2) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 165 */         super.validatePipe(paramSunGraphics2D);
/*     */       } else {
/* 167 */         switch (paramSunGraphics2D.textAntialiasHint) {
/*     */ 
/*     */           
/*     */           case 0:
/*     */           case 1:
/* 172 */             paramSunGraphics2D.textpipe = solidTextRenderer;
/*     */             break;
/*     */           
/*     */           case 2:
/* 176 */             paramSunGraphics2D.textpipe = aaTextRenderer;
/*     */             break;
/*     */           
/*     */           default:
/* 180 */             switch ((paramSunGraphics2D.getFontInfo()).aaHint) {
/*     */               
/*     */               case 4:
/*     */               case 6:
/* 184 */                 paramSunGraphics2D.textpipe = lcdTextRenderer;
/*     */                 break;
/*     */               
/*     */               case 2:
/* 188 */                 paramSunGraphics2D.textpipe = aaTextRenderer;
/*     */                 break;
/*     */             } 
/*     */             
/* 192 */             paramSunGraphics2D.textpipe = solidTextRenderer;
/*     */             break;
/*     */         } 
/*     */       } 
/* 196 */       paramSunGraphics2D.imagepipe = imagepipe;
/* 197 */       if (paramSunGraphics2D.transformState >= 3) {
/* 198 */         paramSunGraphics2D.drawpipe = gdiTxPipe;
/* 199 */         paramSunGraphics2D.fillpipe = gdiTxPipe;
/* 200 */       } else if (paramSunGraphics2D.strokeState != 0) {
/* 201 */         paramSunGraphics2D.drawpipe = gdiTxPipe;
/* 202 */         paramSunGraphics2D.fillpipe = gdiPipe;
/*     */       } else {
/* 204 */         paramSunGraphics2D.drawpipe = gdiPipe;
/* 205 */         paramSunGraphics2D.fillpipe = gdiPipe;
/*     */       } 
/* 207 */       paramSunGraphics2D.shapepipe = gdiPipe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 213 */       if (paramSunGraphics2D.loops == null)
/*     */       {
/* 215 */         paramSunGraphics2D.loops = getRenderLoops(paramSunGraphics2D);
/*     */       }
/*     */     } else {
/* 218 */       super.validatePipe(paramSunGraphics2D);
/*     */     } 
/*     */   }
/*     */   
/*     */   public RenderLoops getRenderLoops(SunGraphics2D paramSunGraphics2D) {
/* 223 */     if (paramSunGraphics2D.paintState <= 1 && paramSunGraphics2D.compositeState <= 0)
/*     */     {
/*     */       
/* 226 */       return this.solidloops;
/*     */     }
/* 228 */     return super.getRenderLoops(paramSunGraphics2D);
/*     */   }
/*     */   
/*     */   public GraphicsConfiguration getDeviceConfiguration() {
/* 232 */     return this.graphicsConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GDIWindowSurfaceData(WComponentPeer paramWComponentPeer, SurfaceType paramSurfaceType) {
/* 242 */     super(paramSurfaceType, paramWComponentPeer.getDeviceColorModel()); int m;
/* 243 */     ColorModel colorModel = paramWComponentPeer.getDeviceColorModel();
/* 244 */     this.peer = paramWComponentPeer;
/* 245 */     int i = 0, j = 0, k = 0;
/*     */     
/* 247 */     switch (colorModel.getPixelSize()) {
/*     */       case 24:
/*     */       case 32:
/* 250 */         if (colorModel instanceof DirectColorModel) {
/* 251 */           byte b = 32; break;
/*     */         } 
/* 253 */         m = 24;
/*     */         break;
/*     */       
/*     */       default:
/* 257 */         m = colorModel.getPixelSize(); break;
/*     */     } 
/* 259 */     if (colorModel instanceof DirectColorModel) {
/* 260 */       DirectColorModel directColorModel = (DirectColorModel)colorModel;
/* 261 */       i = directColorModel.getRedMask();
/* 262 */       j = directColorModel.getGreenMask();
/* 263 */       k = directColorModel.getBlueMask();
/*     */     } 
/* 265 */     this
/* 266 */       .graphicsConfig = (Win32GraphicsConfig)paramWComponentPeer.getGraphicsConfiguration();
/* 267 */     this.solidloops = this.graphicsConfig.getSolidLoops(paramSurfaceType);
/*     */ 
/*     */     
/* 270 */     Win32GraphicsDevice win32GraphicsDevice = (Win32GraphicsDevice)this.graphicsConfig.getDevice();
/* 271 */     initOps(paramWComponentPeer, m, i, j, k, win32GraphicsDevice.getScreen());
/* 272 */     setBlitProxyKey(this.graphicsConfig.getProxyKey());
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
/*     */   public SurfaceData getReplacement() {
/* 284 */     ScreenUpdateManager screenUpdateManager = ScreenUpdateManager.getInstance();
/* 285 */     return screenUpdateManager.getReplacementScreenSurface(this.peer, this);
/*     */   }
/*     */   
/*     */   public Rectangle getBounds() {
/* 289 */     Rectangle rectangle = this.peer.getBounds();
/* 290 */     rectangle.x = rectangle.y = 0;
/* 291 */     return rectangle;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean copyArea(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 297 */     CompositeType compositeType = paramSunGraphics2D.imageComp;
/* 298 */     if (paramSunGraphics2D.transformState < 3 && paramSunGraphics2D.clipState != 2 && (CompositeType.SrcOverNoEa
/*     */       
/* 300 */       .equals(compositeType) || CompositeType.SrcNoEa
/* 301 */       .equals(compositeType))) {
/*     */       
/* 303 */       paramInt1 += paramSunGraphics2D.transX;
/* 304 */       paramInt2 += paramSunGraphics2D.transY;
/* 305 */       int i = paramInt1 + paramInt5;
/* 306 */       int j = paramInt2 + paramInt6;
/* 307 */       int k = i + paramInt3;
/* 308 */       int m = j + paramInt4;
/* 309 */       Region region = paramSunGraphics2D.getCompClip();
/* 310 */       if (i < region.getLoX()) i = region.getLoX(); 
/* 311 */       if (j < region.getLoY()) j = region.getLoY(); 
/* 312 */       if (k > region.getHiX()) k = region.getHiX(); 
/* 313 */       if (m > region.getHiY()) m = region.getHiY(); 
/* 314 */       if (i < k && j < m) {
/* 315 */         gdiPipe.devCopyArea(this, i - paramInt5, j - paramInt6, paramInt5, paramInt6, k - i, m - j);
/*     */       }
/*     */ 
/*     */       
/* 319 */       return true;
/*     */     } 
/* 321 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 327 */     if (isValid()) {
/* 328 */       invalidateSD();
/* 329 */       super.invalidate();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getDestination() {
/* 339 */     return this.peer.getTarget();
/*     */   }
/*     */   
/*     */   public WComponentPeer getPeer() {
/* 343 */     return this.peer;
/*     */   }
/*     */   
/*     */   private static native void initIDs(Class paramClass);
/*     */   
/*     */   private native void initOps(WComponentPeer paramWComponentPeer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */   
/*     */   private native void invalidateSD();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\windows\GDIWindowSurfaceData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */