/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Adjustable;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.ScrollPane;
/*     */ import java.awt.ScrollPaneAdjustable;
/*     */ import java.awt.peer.ScrollPanePeer;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.PeerEvent;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WScrollPanePeer
/*     */   extends WPanelPeer
/*     */   implements ScrollPanePeer
/*     */ {
/*  38 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.windows.WScrollPanePeer");
/*     */   
/*     */   int scrollbarWidth;
/*     */   int scrollbarHeight;
/*     */   int prevx;
/*     */   int prevy;
/*     */   
/*     */   static {
/*  46 */     initIDs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WScrollPanePeer(Component paramComponent) {
/*  55 */     super(paramComponent);
/*  56 */     this.scrollbarWidth = _getVScrollbarWidth();
/*  57 */     this.scrollbarHeight = _getHScrollbarHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   void initialize() {
/*  62 */     super.initialize();
/*  63 */     setInsets();
/*  64 */     Insets insets = getInsets();
/*  65 */     setScrollPosition(-insets.left, -insets.top);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnitIncrement(Adjustable paramAdjustable, int paramInt) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Insets insets() {
/*  75 */     return getInsets();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHScrollbarHeight() {
/*  84 */     return this.scrollbarHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVScrollbarWidth() {
/*  90 */     return this.scrollbarWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public Point getScrollOffset() {
/*  95 */     int i = getOffset(0);
/*  96 */     int j = getOffset(1);
/*  97 */     return new Point(i, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void childResized(int paramInt1, int paramInt2) {
/* 108 */     ScrollPane scrollPane = (ScrollPane)this.target;
/* 109 */     Dimension dimension = scrollPane.getSize();
/* 110 */     setSpans(dimension.width, dimension.height, paramInt1, paramInt2);
/* 111 */     setInsets();
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
/*     */   public void setValue(Adjustable paramAdjustable, int paramInt) {
/* 125 */     Component component = getScrollChild();
/* 126 */     if (component == null) {
/*     */       return;
/*     */     }
/*     */     
/* 130 */     Point point = component.getLocation();
/* 131 */     switch (paramAdjustable.getOrientation()) {
/*     */       case 1:
/* 133 */         setScrollPosition(-point.x, paramInt);
/*     */         break;
/*     */       case 0:
/* 136 */         setScrollPosition(paramInt, -point.y);
/*     */         break;
/*     */     } 
/*     */   } static native void initIDs();
/*     */   native void create(WComponentPeer paramWComponentPeer);
/*     */   private Component getScrollChild() {
/* 142 */     ScrollPane scrollPane = (ScrollPane)this.target;
/* 143 */     Component component = null;
/*     */     try {
/* 145 */       component = scrollPane.getComponent(0);
/* 146 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
/*     */ 
/*     */     
/* 149 */     return component;
/*     */   }
/*     */ 
/*     */   
/*     */   native int getOffset(int paramInt);
/*     */   
/*     */   private native void setInsets();
/*     */   
/*     */   private void postScrollEvent(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 158 */     Adjustor adjustor = new Adjustor(paramInt1, paramInt2, paramInt3, paramBoolean);
/* 159 */     WToolkit.executeOnEventHandlerThread(new ScrollEvent(this.target, adjustor));
/*     */   }
/*     */   public synchronized native void setScrollPosition(int paramInt1, int paramInt2);
/*     */   private native int _getHScrollbarHeight();
/*     */   
/*     */   private native int _getVScrollbarWidth();
/*     */   
/*     */   synchronized native void setSpans(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   class ScrollEvent extends PeerEvent { ScrollEvent(Object param1Object, Runnable param1Runnable) {
/* 169 */       super(param1Object, param1Runnable, 0L);
/*     */     }
/*     */ 
/*     */     
/*     */     public PeerEvent coalesceEvents(PeerEvent param1PeerEvent) {
/* 174 */       if (WScrollPanePeer.log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 175 */         WScrollPanePeer.log.finest("ScrollEvent coalesced: " + param1PeerEvent);
/*     */       }
/* 177 */       if (param1PeerEvent instanceof ScrollEvent) {
/* 178 */         return param1PeerEvent;
/*     */       }
/* 180 */       return null;
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   class Adjustor
/*     */     implements Runnable
/*     */   {
/*     */     int orient;
/*     */     int type;
/*     */     int pos;
/*     */     boolean isAdjusting;
/*     */     
/*     */     Adjustor(int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean) {
/* 194 */       this.orient = param1Int1;
/* 195 */       this.type = param1Int2;
/* 196 */       this.pos = param1Int3;
/* 197 */       this.isAdjusting = param1Boolean;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 202 */       if (WScrollPanePeer.this.getScrollChild() == null) {
/*     */         return;
/*     */       }
/* 205 */       ScrollPane scrollPane = (ScrollPane)WScrollPanePeer.this.target;
/* 206 */       ScrollPaneAdjustable scrollPaneAdjustable = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 213 */       if (this.orient == 1) {
/* 214 */         scrollPaneAdjustable = (ScrollPaneAdjustable)scrollPane.getVAdjustable();
/* 215 */       } else if (this.orient == 0) {
/* 216 */         scrollPaneAdjustable = (ScrollPaneAdjustable)scrollPane.getHAdjustable();
/*     */       }
/* 218 */       else if (WScrollPanePeer.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 219 */         WScrollPanePeer.log.fine("Assertion failed: unknown orient");
/*     */       } 
/*     */ 
/*     */       
/* 223 */       if (scrollPaneAdjustable == null) {
/*     */         return;
/*     */       }
/*     */       
/* 227 */       int i = scrollPaneAdjustable.getValue();
/* 228 */       switch (this.type) {
/*     */         case 2:
/* 230 */           i -= scrollPaneAdjustable.getUnitIncrement();
/*     */           break;
/*     */         case 1:
/* 233 */           i += scrollPaneAdjustable.getUnitIncrement();
/*     */           break;
/*     */         case 3:
/* 236 */           i -= scrollPaneAdjustable.getBlockIncrement();
/*     */           break;
/*     */         case 4:
/* 239 */           i += scrollPaneAdjustable.getBlockIncrement();
/*     */           break;
/*     */         case 5:
/* 242 */           i = this.pos;
/*     */           break;
/*     */         default:
/* 245 */           if (WScrollPanePeer.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 246 */             WScrollPanePeer.log.fine("Assertion failed: unknown type");
/*     */           }
/*     */           return;
/*     */       } 
/*     */ 
/*     */       
/* 252 */       i = Math.max(scrollPaneAdjustable.getMinimum(), i);
/* 253 */       i = Math.min(scrollPaneAdjustable.getMaximum(), i);
/*     */ 
/*     */       
/* 256 */       scrollPaneAdjustable.setValueIsAdjusting(this.isAdjusting);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 261 */       AWTAccessor.getScrollPaneAdjustableAccessor().setTypedValue(scrollPaneAdjustable, i, this.type);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 267 */       Component component = WScrollPanePeer.this.getScrollChild();
/* 268 */       while (component != null && 
/* 269 */         !(component.getPeer() instanceof WComponentPeer))
/*     */       {
/* 271 */         component = component.getParent();
/*     */       }
/* 273 */       if (WScrollPanePeer.log.isLoggable(PlatformLogger.Level.FINE) && 
/* 274 */         component == null) {
/* 275 */         WScrollPanePeer.log.fine("Assertion (hwAncestor != null) failed, couldn't find heavyweight ancestor of scroll pane child");
/*     */       }
/*     */ 
/*     */       
/* 279 */       WComponentPeer wComponentPeer = (WComponentPeer)component.getPeer();
/* 280 */       wComponentPeer.paintDamagedAreaImmediately();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WScrollPanePeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */