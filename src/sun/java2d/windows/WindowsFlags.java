/*     */ package sun.java2d.windows;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.awt.windows.WToolkit;
/*     */ import sun.java2d.opengl.WGLGraphicsConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsFlags
/*     */ {
/*     */   private static boolean gdiBlitEnabled;
/*     */   private static boolean d3dEnabled;
/*     */   private static boolean d3dVerbose;
/*     */   private static boolean d3dSet;
/*     */   private static boolean d3dOnScreenEnabled;
/*     */   private static boolean oglEnabled;
/*     */   private static boolean oglVerbose;
/*     */   private static boolean offscreenSharingEnabled;
/*     */   private static boolean accelReset;
/*     */   private static boolean checkRegistry;
/*     */   private static boolean disableRegistry;
/*     */   private static boolean magPresent;
/*     */   private static boolean setHighDPIAware;
/*     */   private static float autoScalingThreshold;
/*     */   private static boolean autoScaling;
/*     */   private static String javaVersion;
/*     */   private static final float DEFAULT_AUTO_SCALING_THRESHOLD = 1.5F;
/*     */   
/*     */   static {
/* 140 */     WToolkit.loadLibraries();
/*     */     
/* 142 */     initJavaFlags();
/*     */ 
/*     */ 
/*     */     
/* 146 */     initNativeFlags();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initFlags() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean getBooleanProp(String paramString, boolean paramBoolean) {
/* 166 */     String str = System.getProperty(paramString);
/* 167 */     boolean bool = paramBoolean;
/* 168 */     if (str != null) {
/* 169 */       if (str.equals("true") || str
/* 170 */         .equals("t") || str
/* 171 */         .equals("True") || str
/* 172 */         .equals("T") || str
/* 173 */         .equals("")) {
/*     */         
/* 175 */         bool = true;
/* 176 */       } else if (str.equals("false") || str
/* 177 */         .equals("f") || str
/* 178 */         .equals("False") || str
/* 179 */         .equals("F")) {
/*     */         
/* 181 */         bool = false;
/*     */       } 
/*     */     }
/* 184 */     return bool;
/*     */   }
/*     */   
/*     */   private static boolean isBooleanPropTrueVerbose(String paramString) {
/* 188 */     String str = System.getProperty(paramString);
/* 189 */     if (str != null && (
/* 190 */       str.equals("True") || str
/* 191 */       .equals("T")))
/*     */     {
/* 193 */       return true;
/*     */     }
/*     */     
/* 196 */     return false;
/*     */   }
/*     */   
/*     */   private static int getIntProp(String paramString, int paramInt) {
/* 200 */     String str = System.getProperty(paramString);
/* 201 */     int i = paramInt;
/* 202 */     if (str != null) {
/*     */       try {
/* 204 */         i = Integer.parseInt(str);
/* 205 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/* 207 */     return i;
/*     */   }
/*     */   
/*     */   private static boolean getPropertySet(String paramString) {
/* 211 */     String str = System.getProperty(paramString);
/* 212 */     return (str != null);
/*     */   }
/*     */   
/*     */   private static void initJavaFlags() {
/* 216 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run()
/*     */           {
/* 220 */             WindowsFlags.magPresent = WindowsFlags.getBooleanProp("javax.accessibility.screen_magnifier_present", false);
/*     */ 
/*     */             
/* 223 */             boolean bool = !WindowsFlags.getBooleanProp("sun.java2d.noddraw", WindowsFlags.magPresent) ? true : false;
/*     */             
/* 225 */             boolean bool1 = WindowsFlags.getBooleanProp("sun.java2d.ddoffscreen", bool);
/* 226 */             WindowsFlags.d3dEnabled = WindowsFlags.getBooleanProp("sun.java2d.d3d", (bool && bool1));
/*     */ 
/*     */             
/* 229 */             WindowsFlags.d3dOnScreenEnabled = WindowsFlags.getBooleanProp("sun.java2d.d3d.onscreen", WindowsFlags.d3dEnabled);
/* 230 */             WindowsFlags.oglEnabled = WindowsFlags.getBooleanProp("sun.java2d.opengl", false);
/* 231 */             if (WindowsFlags.oglEnabled) {
/* 232 */               WindowsFlags.oglVerbose = WindowsFlags.isBooleanPropTrueVerbose("sun.java2d.opengl");
/* 233 */               if (WGLGraphicsConfig.isWGLAvailable()) {
/* 234 */                 WindowsFlags.d3dEnabled = false;
/*     */               } else {
/* 236 */                 if (WindowsFlags.oglVerbose) {
/* 237 */                   System.out.println("Could not enable OpenGL pipeline (WGL not available)");
/*     */                 }
/*     */ 
/*     */                 
/* 241 */                 WindowsFlags.oglEnabled = false;
/*     */               } 
/*     */             } 
/* 244 */             WindowsFlags.gdiBlitEnabled = WindowsFlags.getBooleanProp("sun.java2d.gdiBlit", true);
/* 245 */             WindowsFlags.d3dSet = WindowsFlags.getPropertySet("sun.java2d.d3d");
/* 246 */             if (WindowsFlags.d3dSet) {
/* 247 */               WindowsFlags.d3dVerbose = WindowsFlags.isBooleanPropTrueVerbose("sun.java2d.d3d");
/*     */             }
/*     */             
/* 250 */             WindowsFlags.offscreenSharingEnabled = WindowsFlags.getBooleanProp("sun.java2d.offscreenSharing", false);
/* 251 */             WindowsFlags.accelReset = WindowsFlags.getBooleanProp("sun.java2d.accelReset", false);
/*     */             
/* 253 */             WindowsFlags.checkRegistry = WindowsFlags.getBooleanProp("sun.java2d.checkRegistry", false);
/*     */             
/* 255 */             WindowsFlags.disableRegistry = WindowsFlags.getBooleanProp("sun.java2d.disableRegistry", false);
/* 256 */             WindowsFlags.javaVersion = System.getProperty("java.version");
/* 257 */             if (WindowsFlags.javaVersion == null) {
/*     */               
/* 259 */               WindowsFlags.javaVersion = "default";
/*     */             } else {
/* 261 */               int i = WindowsFlags.javaVersion.indexOf('-');
/* 262 */               if (i >= 0)
/*     */               {
/* 264 */                 WindowsFlags.javaVersion = WindowsFlags.javaVersion.substring(0, i);
/*     */               }
/*     */             } 
/*     */             
/* 268 */             String str1 = System.getProperty("sun.java2d.autoScaleThreshold");
/* 269 */             if (str1 != null) {
/*     */               try {
/* 271 */                 WindowsFlags.autoScalingThreshold = Float.parseFloat(str1);
/* 272 */               } catch (NumberFormatException numberFormatException) {
/* 273 */                 WindowsFlags.autoScalingThreshold = 1.5F;
/*     */               } 
/*     */             } else {
/* 276 */               WindowsFlags.autoScalingThreshold = 1.5F;
/*     */             } 
/* 278 */             String str2 = System.getProperty("sun.java2d.dpiaware");
/* 279 */             float f = WindowsFlags.getScaleY();
/* 280 */             if (str2 != null) {
/* 281 */               WindowsFlags.setHighDPIAware = str2.equalsIgnoreCase("true");
/* 282 */               WindowsFlags.autoScaling = (!WindowsFlags.setHighDPIAware && f >= WindowsFlags.autoScalingThreshold);
/*     */             } else {
/*     */               
/* 285 */               String str = System.getProperty("sun.java.launcher", "unknown");
/* 286 */               WindowsFlags.autoScaling = (f >= WindowsFlags.autoScalingThreshold);
/* 287 */               WindowsFlags.setHighDPIAware = (str
/* 288 */                 .equalsIgnoreCase("SUN_STANDARD") && 
/* 289 */                 !WindowsFlags.autoScaling);
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 301 */             return null;
/*     */           }
/*     */         });
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
/*     */   public static boolean isD3DEnabled() {
/* 328 */     return d3dEnabled;
/*     */   }
/*     */   
/*     */   public static boolean isD3DSet() {
/* 332 */     return d3dSet;
/*     */   }
/*     */   
/*     */   public static boolean isD3DOnScreenEnabled() {
/* 336 */     return d3dOnScreenEnabled;
/*     */   }
/*     */   
/*     */   public static boolean isD3DVerbose() {
/* 340 */     return d3dVerbose;
/*     */   }
/*     */   
/*     */   public static boolean isGdiBlitEnabled() {
/* 344 */     return gdiBlitEnabled;
/*     */   }
/*     */   
/*     */   public static boolean isTranslucentAccelerationEnabled() {
/* 348 */     return d3dEnabled;
/*     */   }
/*     */   
/*     */   public static boolean isOffscreenSharingEnabled() {
/* 352 */     return offscreenSharingEnabled;
/*     */   }
/*     */   
/*     */   public static boolean isMagPresent() {
/* 356 */     return magPresent;
/*     */   }
/*     */   
/*     */   public static boolean isOGLEnabled() {
/* 360 */     return oglEnabled;
/*     */   }
/*     */   
/*     */   public static boolean isOGLVerbose() {
/* 364 */     return oglVerbose;
/*     */   }
/*     */   
/*     */   public static boolean isAutoScaling() {
/* 368 */     return autoScaling;
/*     */   }
/*     */   
/*     */   private static native boolean initNativeFlags();
/*     */   
/*     */   private static native float getScaleY();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\java2d\windows\WindowsFlags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */