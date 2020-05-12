/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Event;
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.Font;
/*     */ import java.awt.Window;
/*     */ import java.awt.dnd.DropTarget;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.awt.peer.FileDialogPeer;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.List;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Vector;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.CausedFocusEvent;
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
/*     */ final class WFileDialogPeer
/*     */   extends WWindowPeer
/*     */   implements FileDialogPeer
/*     */ {
/*     */   private WComponentPeer parent;
/*     */   private FilenameFilter fileFilter;
/*     */   
/*     */   static {
/*  43 */     initIDs();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run()
/*     */           {
/*     */             try {
/* 210 */               ResourceBundle resourceBundle = ResourceBundle.getBundle("sun.awt.windows.awtLocalization");
/* 211 */               return resourceBundle.getString("allFiles");
/* 212 */             } catch (MissingResourceException missingResourceException) {
/* 213 */               return "All Files";
/*     */             } 
/*     */           }
/*     */         });
/* 217 */     setFilterString(str);
/*     */   }
/*     */   private Vector<WWindowPeer> blockedWindows = new Vector<>();
/*     */   public void setFilenameFilter(FilenameFilter paramFilenameFilter) { this.fileFilter = paramFilenameFilter; }
/* 221 */   boolean checkFilenameFilter(String paramString) { FileDialog fileDialog = (FileDialog)this.target; if (this.fileFilter == null) return true;  File file = new File(paramString); return this.fileFilter.accept(new File(file.getParent()), file.getName()); } WFileDialogPeer(FileDialog paramFileDialog) { super(paramFileDialog); } void create(WComponentPeer paramWComponentPeer) { this.parent = paramWComponentPeer; } protected void checkCreation() {} void blockWindow(WWindowPeer paramWWindowPeer) { this.blockedWindows.add(paramWWindowPeer);
/*     */ 
/*     */     
/* 224 */     if (this.hwnd != 0L)
/* 225 */       paramWWindowPeer.modalDisable((Dialog)this.target, this.hwnd);  } void initialize() { setFilenameFilter(((FileDialog)this.target).getFilenameFilter()); } protected void disposeImpl() { WToolkit.targetDisposedPeer(this.target, this); _dispose(); } public void show() { (new Thread(new Runnable() { public void run() { WFileDialogPeer.this._show(); } }
/*     */       )).start(); } void hide() { _hide(); } void setHWnd(long paramLong) { if (this.hwnd == paramLong) return;  this.hwnd = paramLong; for (WWindowPeer wWindowPeer : this.blockedWindows) { if (paramLong != 0L) { wWindowPeer.modalDisable((Dialog)this.target, paramLong); continue; }  wWindowPeer.modalEnable((Dialog)this.target); }  } void handleSelected(char[] paramArrayOfchar) { String[] arrayOfString = (new String(paramArrayOfchar)).split("\000"); boolean bool = (arrayOfString.length > 1) ? true : false; String str1 = null; String str2 = null; File[] arrayOfFile = null; if (bool) { str1 = arrayOfString[0]; int i = arrayOfString.length - 1; arrayOfFile = new File[i]; for (byte b = 0; b < i; b++) arrayOfFile[b] = new File(str1, arrayOfString[b + 1]);  str2 = arrayOfString[1]; } else { int i = arrayOfString[0].lastIndexOf(File.separatorChar); if (i == -1) { str1 = "." + File.separator; str2 = arrayOfString[0]; } else { str1 = arrayOfString[0].substring(0, i + 1); str2 = arrayOfString[0].substring(i + 1); }  arrayOfFile = new File[] { new File(str1, str2) }; }  final FileDialog fileDialog = (FileDialog)this.target; AWTAccessor.FileDialogAccessor fileDialogAccessor = AWTAccessor.getFileDialogAccessor(); fileDialogAccessor.setDirectory(fileDialog, str1); fileDialogAccessor.setFile(fileDialog, str2); fileDialogAccessor.setFiles(fileDialog, arrayOfFile); WToolkit.executeOnEventHandlerThread(fileDialog, new Runnable() { public void run() { fileDialog.setVisible(false); } }
/*     */       ); } void handleCancel() { final FileDialog fileDialog = (FileDialog)this.target; AWTAccessor.getFileDialogAccessor().setFile(fileDialog, null); AWTAccessor.getFileDialogAccessor().setFiles(fileDialog, null); AWTAccessor.getFileDialogAccessor().setDirectory(fileDialog, null); WToolkit.executeOnEventHandlerThread(fileDialog, new Runnable() {
/*     */           public void run() { fileDialog.setVisible(false); }
/* 229 */         }); } void unblockWindow(WWindowPeer paramWWindowPeer) { this.blockedWindows.remove(paramWWindowPeer);
/*     */ 
/*     */     
/* 232 */     if (this.hwnd != 0L) {
/* 233 */       paramWWindowPeer.modalEnable((Dialog)this.target);
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public void blockWindows(List<Window> paramList) {
/* 239 */     for (Window window : paramList) {
/* 240 */       WWindowPeer wWindowPeer = (WWindowPeer)AWTAccessor.getComponentAccessor().getPeer(window);
/* 241 */       if (wWindowPeer != null) {
/* 242 */         blockWindow(wWindowPeer);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAlwaysOnTopState() {}
/*     */ 
/*     */   
/*     */   public void setDirectory(String paramString) {}
/*     */ 
/*     */   
/*     */   public void setFile(String paramString) {}
/*     */ 
/*     */   
/*     */   public void setTitle(String paramString) {}
/*     */ 
/*     */   
/*     */   public void setResizable(boolean paramBoolean) {}
/*     */ 
/*     */   
/*     */   void enable() {}
/*     */ 
/*     */   
/*     */   void disable() {}
/*     */   
/*     */   public void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*     */   
/*     */   public boolean handleEvent(Event paramEvent) {
/* 271 */     return false;
/*     */   }
/*     */   public void setForeground(Color paramColor) {}
/*     */   
/*     */   public void setBackground(Color paramColor) {}
/*     */   
/*     */   public void setFont(Font paramFont) {}
/*     */   
/*     */   public void updateMinimumSize() {}
/*     */   
/*     */   public void updateIconImages() {}
/*     */   
/*     */   public boolean requestFocus(boolean paramBoolean1, boolean paramBoolean2) {
/* 284 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean requestFocus(Component paramComponent, boolean paramBoolean1, boolean paramBoolean2, long paramLong, CausedFocusEvent.Cause paramCause) {
/* 292 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   void start() {}
/*     */ 
/*     */   
/*     */   public void beginValidate() {}
/*     */ 
/*     */   
/*     */   public void endValidate() {}
/*     */ 
/*     */   
/*     */   void invalidate(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*     */ 
/*     */   
/*     */   public void addDropTarget(DropTarget paramDropTarget) {}
/*     */ 
/*     */   
/*     */   public void removeDropTarget(DropTarget paramDropTarget) {}
/*     */ 
/*     */   
/*     */   public void updateFocusableWindowState() {}
/*     */ 
/*     */   
/*     */   public void setZOrder(ComponentPeer paramComponentPeer) {}
/*     */ 
/*     */   
/*     */   public void applyShape(Region paramRegion) {}
/*     */   
/*     */   public void setOpacity(float paramFloat) {}
/*     */   
/*     */   public void setOpaque(boolean paramBoolean) {}
/*     */   
/*     */   public void updateWindow(BufferedImage paramBufferedImage) {}
/*     */   
/*     */   public void createScreenSurface(boolean paramBoolean) {}
/*     */   
/*     */   public void replaceSurfaceData() {}
/*     */   
/*     */   public boolean isMultipleMode() {
/* 333 */     FileDialog fileDialog = (FileDialog)this.target;
/* 334 */     return AWTAccessor.getFileDialogAccessor().isMultipleMode(fileDialog);
/*     */   }
/*     */   
/*     */   private static native void setFilterString(String paramString);
/*     */   
/*     */   private native void _dispose();
/*     */   
/*     */   private native void _show();
/*     */   
/*     */   private native void _hide();
/*     */   
/*     */   public native void toFront();
/*     */   
/*     */   public native void toBack();
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WFileDialogPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */