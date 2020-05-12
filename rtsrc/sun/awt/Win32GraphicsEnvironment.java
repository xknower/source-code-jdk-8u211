/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.AWTError;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.ListIterator;
/*     */ import sun.awt.windows.WToolkit;
/*     */ import sun.java2d.SunGraphicsEnvironment;
/*     */ import sun.java2d.SurfaceManagerFactory;
/*     */ import sun.java2d.WindowsSurfaceManagerFactory;
/*     */ import sun.java2d.d3d.D3DGraphicsDevice;
/*     */ import sun.java2d.windows.WindowsFlags;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Win32GraphicsEnvironment
/*     */   extends SunGraphicsEnvironment
/*     */ {
/*     */   private static boolean displayInitialized;
/*     */   private ArrayList<WeakReference<Win32GraphicsDevice>> oldDevices;
/*     */   private static volatile boolean isDWMCompositionEnabled;
/*     */   
/*     */   static {
/*  66 */     WToolkit.loadLibraries();
/*     */     
/*  68 */     WindowsFlags.initFlags();
/*  69 */     initDisplayWrapper();
/*     */ 
/*     */     
/*  72 */     SurfaceManagerFactory.setInstance(new WindowsSurfaceManagerFactory());
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
/*     */   public static void initDisplayWrapper() {
/*  84 */     if (!displayInitialized) {
/*  85 */       displayInitialized = true;
/*  86 */       initDisplay();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsDevice getDefaultScreenDevice() {
/*  97 */     GraphicsDevice[] arrayOfGraphicsDevice = getScreenDevices();
/*  98 */     if (arrayOfGraphicsDevice.length == 0) {
/*  99 */       throw new AWTError("no screen devices");
/*     */     }
/* 101 */     int i = getDefaultScreen();
/* 102 */     return arrayOfGraphicsDevice[(0 < i && i < arrayOfGraphicsDevice.length) ? i : 0];
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayChanged() {
/* 135 */     GraphicsDevice[] arrayOfGraphicsDevice1 = new GraphicsDevice[getNumScreens()];
/* 136 */     GraphicsDevice[] arrayOfGraphicsDevice2 = this.screens;
/*     */ 
/*     */     
/* 139 */     if (arrayOfGraphicsDevice2 != null) {
/* 140 */       for (byte b = 0; b < arrayOfGraphicsDevice2.length; b++) {
/* 141 */         if (!(this.screens[b] instanceof Win32GraphicsDevice)) {
/*     */           
/* 143 */           assert false : arrayOfGraphicsDevice2[b];
/*     */         } else {
/*     */           
/* 146 */           Win32GraphicsDevice win32GraphicsDevice = (Win32GraphicsDevice)arrayOfGraphicsDevice2[b];
/*     */ 
/*     */ 
/*     */           
/* 150 */           if (!win32GraphicsDevice.isValid()) {
/* 151 */             if (this.oldDevices == null) {
/* 152 */               this.oldDevices = new ArrayList<>();
/*     */             }
/*     */             
/* 155 */             this.oldDevices.add(new WeakReference<>(win32GraphicsDevice));
/* 156 */           } else if (b < arrayOfGraphicsDevice1.length) {
/*     */             
/* 158 */             arrayOfGraphicsDevice1[b] = win32GraphicsDevice;
/*     */           } 
/*     */         } 
/* 161 */       }  arrayOfGraphicsDevice2 = null;
/*     */     } 
/*     */     int i;
/* 164 */     for (i = 0; i < arrayOfGraphicsDevice1.length; i++) {
/* 165 */       if (arrayOfGraphicsDevice1[i] == null) {
/* 166 */         arrayOfGraphicsDevice1[i] = makeScreenDevice(i);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 172 */     this.screens = arrayOfGraphicsDevice1;
/* 173 */     for (GraphicsDevice graphicsDevice : this.screens) {
/* 174 */       if (graphicsDevice instanceof DisplayChangedListener) {
/* 175 */         ((DisplayChangedListener)graphicsDevice).displayChanged();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     if (this.oldDevices != null) {
/* 183 */       i = getDefaultScreen();
/*     */       
/* 185 */       for (ListIterator<WeakReference<Win32GraphicsDevice>> listIterator = this.oldDevices.listIterator(); listIterator.hasNext(); ) {
/*     */         
/* 187 */         Win32GraphicsDevice win32GraphicsDevice = ((WeakReference<Win32GraphicsDevice>)listIterator.next()).get();
/* 188 */         if (win32GraphicsDevice != null) {
/* 189 */           win32GraphicsDevice.invalidate(i);
/* 190 */           win32GraphicsDevice.displayChanged();
/*     */           continue;
/*     */         } 
/* 193 */         listIterator.remove();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 198 */     WToolkit.resetGC();
/*     */ 
/*     */ 
/*     */     
/* 202 */     this.displayChanger.notifyListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GraphicsDevice makeScreenDevice(int paramInt) {
/* 212 */     Win32GraphicsDevice win32GraphicsDevice = null;
/* 213 */     if (WindowsFlags.isD3DEnabled()) {
/* 214 */       win32GraphicsDevice = D3DGraphicsDevice.createDevice(paramInt);
/*     */     }
/* 216 */     if (win32GraphicsDevice == null) {
/* 217 */       win32GraphicsDevice = new Win32GraphicsDevice(paramInt);
/*     */     }
/* 219 */     return win32GraphicsDevice;
/*     */   }
/*     */   
/*     */   public boolean isDisplayLocal() {
/* 223 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFlipStrategyPreferred(ComponentPeer paramComponentPeer) {
/*     */     GraphicsConfiguration graphicsConfiguration;
/* 229 */     if (paramComponentPeer != null && (graphicsConfiguration = paramComponentPeer.getGraphicsConfiguration()) != null) {
/* 230 */       GraphicsDevice graphicsDevice = graphicsConfiguration.getDevice();
/* 231 */       if (graphicsDevice instanceof D3DGraphicsDevice) {
/* 232 */         return ((D3DGraphicsDevice)graphicsDevice).isD3DEnabledOnDevice();
/*     */       }
/*     */     } 
/* 235 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDWMCompositionEnabled() {
/* 245 */     return isDWMCompositionEnabled;
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
/*     */   private static void dwmCompositionChanged(boolean paramBoolean) {
/* 258 */     isDWMCompositionEnabled = paramBoolean;
/*     */   }
/*     */   
/*     */   private static native void initDisplay();
/*     */   
/*     */   protected native int getNumScreens();
/*     */   
/*     */   protected native int getDefaultScreen();
/*     */   
/*     */   public native int getXResolution();
/*     */   
/*     */   public native int getYResolution();
/*     */   
/*     */   public static native boolean isVistaOS();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\Win32GraphicsEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */