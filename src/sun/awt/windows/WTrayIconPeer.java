/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.PopupMenu;
/*     */ import java.awt.TrayIcon;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.awt.peer.TrayIconPeer;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.image.IntegerComponentRaster;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WTrayIconPeer
/*     */   extends WObjectPeer
/*     */   implements TrayIconPeer
/*     */ {
/*     */   static final int TRAY_ICON_WIDTH = 16;
/*     */   static final int TRAY_ICON_HEIGHT = 16;
/*     */   static final int TRAY_ICON_MASK_SIZE = 32;
/*  45 */   IconObserver observer = new IconObserver();
/*     */   boolean firstUpdate = true;
/*  47 */   Frame popupParent = new Frame("PopupMessageWindow");
/*     */   
/*     */   PopupMenu popup;
/*     */   
/*     */   protected void disposeImpl() {
/*  52 */     if (this.popupParent != null) {
/*  53 */       this.popupParent.dispose();
/*     */     }
/*  55 */     this.popupParent.dispose();
/*  56 */     _dispose();
/*  57 */     WToolkit.targetDisposedPeer(this.target, this);
/*     */   }
/*     */   
/*     */   WTrayIconPeer(TrayIcon paramTrayIcon) {
/*  61 */     this.target = paramTrayIcon;
/*  62 */     this.popupParent.addNotify();
/*  63 */     create();
/*  64 */     updateImage();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateImage() {
/*  69 */     Image image = ((TrayIcon)this.target).getImage();
/*  70 */     if (image != null) {
/*  71 */       updateNativeImage(image);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public native void setToolTip(String paramString);
/*     */ 
/*     */   
/*     */   public synchronized void showPopupMenu(final int x, final int y) {
/*  80 */     if (isDisposed()) {
/*     */       return;
/*     */     }
/*  83 */     SunToolkit.executeOnEventHandlerThread(this.target, new Runnable()
/*     */         {
/*     */           public void run() {
/*  86 */             PopupMenu popupMenu = ((TrayIcon)WTrayIconPeer.this.target).getPopupMenu();
/*  87 */             if (WTrayIconPeer.this.popup != popupMenu) {
/*  88 */               if (WTrayIconPeer.this.popup != null) {
/*  89 */                 WTrayIconPeer.this.popupParent.remove(WTrayIconPeer.this.popup);
/*     */               }
/*  91 */               if (popupMenu != null) {
/*  92 */                 WTrayIconPeer.this.popupParent.add(popupMenu);
/*     */               }
/*  94 */               WTrayIconPeer.this.popup = popupMenu;
/*     */             } 
/*  96 */             if (WTrayIconPeer.this.popup != null) {
/*  97 */               ((WPopupMenuPeer)WTrayIconPeer.this.popup.getPeer()).show(WTrayIconPeer.this.popupParent, new Point(x, y));
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayMessage(String paramString1, String paramString2, String paramString3) {
/* 106 */     if (paramString1 == null) {
/* 107 */       paramString1 = "";
/*     */     }
/* 109 */     if (paramString2 == null) {
/* 110 */       paramString2 = "";
/*     */     }
/* 112 */     _displayMessage(paramString1, paramString2, paramString3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void updateNativeImage(Image paramImage) {
/* 121 */     if (isDisposed()) {
/*     */       return;
/*     */     }
/* 124 */     boolean bool = ((TrayIcon)this.target).isImageAutoSize();
/*     */     
/* 126 */     BufferedImage bufferedImage = new BufferedImage(16, 16, 2);
/*     */     
/* 128 */     Graphics2D graphics2D = bufferedImage.createGraphics();
/* 129 */     if (graphics2D != null) {
/*     */       try {
/* 131 */         graphics2D.setPaintMode();
/*     */         
/* 133 */         graphics2D.drawImage(paramImage, 0, 0, bool ? 16 : paramImage.getWidth(this.observer), bool ? 16 : paramImage
/* 134 */             .getHeight(this.observer), this.observer);
/*     */         
/* 136 */         createNativeImage(bufferedImage);
/*     */         
/* 138 */         updateNativeIcon(!this.firstUpdate);
/* 139 */         if (this.firstUpdate) this.firstUpdate = false;
/*     */       
/*     */       } finally {
/* 142 */         graphics2D.dispose();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   void createNativeImage(BufferedImage paramBufferedImage) {
/* 148 */     WritableRaster writableRaster = paramBufferedImage.getRaster();
/* 149 */     byte[] arrayOfByte = new byte[32];
/* 150 */     int[] arrayOfInt = ((DataBufferInt)writableRaster.getDataBuffer()).getData();
/* 151 */     int i = arrayOfInt.length;
/* 152 */     int j = writableRaster.getWidth();
/*     */     
/* 154 */     for (byte b = 0; b < i; b++) {
/* 155 */       int k = b / 8;
/* 156 */       int m = 1 << 7 - b % 8;
/*     */       
/* 158 */       if ((arrayOfInt[b] & 0xFF000000) == 0)
/*     */       {
/* 160 */         if (k < arrayOfByte.length) {
/* 161 */           arrayOfByte[k] = (byte)(arrayOfByte[k] | m);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 166 */     if (writableRaster instanceof IntegerComponentRaster) {
/* 167 */       j = ((IntegerComponentRaster)writableRaster).getScanlineStride();
/*     */     }
/* 169 */     setNativeIcon(((DataBufferInt)paramBufferedImage.getRaster().getDataBuffer()).getData(), arrayOfByte, j, writableRaster
/* 170 */         .getWidth(), writableRaster.getHeight());
/*     */   }
/*     */   
/*     */   void postEvent(AWTEvent paramAWTEvent) {
/* 174 */     WToolkit.postEvent(WToolkit.targetToAppContext(this.target), paramAWTEvent);
/*     */   }
/*     */ 
/*     */   
/*     */   native void create();
/*     */ 
/*     */   
/*     */   synchronized native void _dispose();
/*     */ 
/*     */   
/*     */   native void updateNativeIcon(boolean paramBoolean);
/*     */ 
/*     */   
/*     */   native void setNativeIcon(int[] paramArrayOfint, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   native void _displayMessage(String paramString1, String paramString2, String paramString3);
/*     */   
/*     */   class IconObserver
/*     */     implements ImageObserver
/*     */   {
/*     */     public boolean imageUpdate(Image param1Image, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 195 */       if (param1Image != ((TrayIcon)WTrayIconPeer.this.target).getImage() || WTrayIconPeer.this
/* 196 */         .isDisposed())
/*     */       {
/* 198 */         return false;
/*     */       }
/* 200 */       if ((param1Int1 & 0x33) != 0)
/*     */       {
/*     */         
/* 203 */         WTrayIconPeer.this.updateNativeImage(param1Image);
/*     */       }
/* 205 */       return ((param1Int1 & 0x20) == 0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WTrayIconPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */