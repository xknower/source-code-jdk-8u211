/*    */ package sun.java2d.opengl;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import sun.java2d.SurfaceData;
/*    */ import sun.java2d.SurfaceDataProxy;
/*    */ import sun.java2d.loops.CompositeType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OGLSurfaceDataProxy
/*    */   extends SurfaceDataProxy
/*    */ {
/*    */   OGLGraphicsConfig oglgc;
/*    */   int transparency;
/*    */   
/*    */   public static SurfaceDataProxy createProxy(SurfaceData paramSurfaceData, OGLGraphicsConfig paramOGLGraphicsConfig) {
/* 45 */     if (paramSurfaceData instanceof OGLSurfaceData)
/*    */     {
/*    */       
/* 48 */       return UNCACHED;
/*    */     }
/*    */     
/* 51 */     return new OGLSurfaceDataProxy(paramOGLGraphicsConfig, paramSurfaceData.getTransparency());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OGLSurfaceDataProxy(OGLGraphicsConfig paramOGLGraphicsConfig, int paramInt) {
/* 58 */     this.oglgc = paramOGLGraphicsConfig;
/* 59 */     this.transparency = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SurfaceData validateSurfaceData(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, int paramInt1, int paramInt2) {
/* 67 */     if (paramSurfaceData2 == null) {
/*    */       try {
/* 69 */         paramSurfaceData2 = this.oglgc.createManagedSurface(paramInt1, paramInt2, this.transparency);
/* 70 */       } catch (OutOfMemoryError outOfMemoryError) {
/* 71 */         return null;
/*    */       } 
/*    */     }
/* 74 */     return paramSurfaceData2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSupportedOperation(SurfaceData paramSurfaceData, int paramInt, CompositeType paramCompositeType, Color paramColor) {
/* 83 */     return (paramCompositeType.isDerivedFrom(CompositeType.AnyAlpha) && (paramColor == null || this.transparency == 1));
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\opengl\OGLSurfaceDataProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */