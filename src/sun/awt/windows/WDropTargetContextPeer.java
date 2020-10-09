/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import sun.awt.PeerEvent;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.dnd.SunDropTargetContextPeer;
/*     */ import sun.awt.dnd.SunDropTargetEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WDropTargetContextPeer
/*     */   extends SunDropTargetContextPeer
/*     */ {
/*     */   static WDropTargetContextPeer getWDropTargetContextPeer() {
/*  53 */     return new WDropTargetContextPeer();
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
/*     */   private static FileInputStream getFileStream(String paramString, long paramLong) throws IOException {
/*  71 */     return new WDropTargetContextPeerFileStream(paramString, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object getIStream(long paramLong) throws IOException {
/*  79 */     return new WDropTargetContextPeerIStream(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getNativeData(long paramLong) {
/*  84 */     return getData(getNativeDragContext(), paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDropDone(boolean paramBoolean1, int paramInt, boolean paramBoolean2) {
/*  94 */     dropDone(getNativeDragContext(), paramBoolean1, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void eventPosted(final SunDropTargetEvent e) {
/*  99 */     if (e.getID() != 502) {
/* 100 */       Runnable runnable = new Runnable()
/*     */         {
/*     */           public void run() {
/* 103 */             e.getDispatcher().unregisterAllEvents();
/*     */           }
/*     */         };
/*     */ 
/*     */ 
/*     */       
/* 109 */       PeerEvent peerEvent = new PeerEvent(e.getSource(), runnable, 0L);
/* 110 */       SunToolkit.executeOnEventHandlerThread(peerEvent);
/*     */     } 
/*     */   }
/*     */   
/*     */   private native Object getData(long paramLong1, long paramLong2);
/*     */   
/*     */   private native void dropDone(long paramLong, boolean paramBoolean, int paramInt);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WDropTargetContextPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */