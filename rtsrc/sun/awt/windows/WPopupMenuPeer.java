/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Event;
/*     */ import java.awt.MenuContainer;
/*     */ import java.awt.Point;
/*     */ import java.awt.PopupMenu;
/*     */ import java.awt.peer.PopupMenuPeer;
/*     */ import sun.awt.AWTAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WPopupMenuPeer
/*     */   extends WMenuPeer
/*     */   implements PopupMenuPeer
/*     */ {
/*     */   WPopupMenuPeer(PopupMenu paramPopupMenu) {
/*  38 */     this.target = paramPopupMenu;
/*  39 */     MenuContainer menuContainer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  44 */     boolean bool = AWTAccessor.getPopupMenuAccessor().isTrayIconPopup(paramPopupMenu);
/*  45 */     if (bool) {
/*  46 */       menuContainer = AWTAccessor.getMenuComponentAccessor().getParent(paramPopupMenu);
/*     */     } else {
/*  48 */       menuContainer = paramPopupMenu.getParent();
/*     */     } 
/*     */     
/*  51 */     if (menuContainer instanceof Component) {
/*  52 */       WComponentPeer wComponentPeer = (WComponentPeer)WToolkit.targetToPeer(menuContainer);
/*  53 */       if (wComponentPeer == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  58 */         menuContainer = WToolkit.getNativeContainer((Component)menuContainer);
/*  59 */         wComponentPeer = (WComponentPeer)WToolkit.targetToPeer(menuContainer);
/*     */       } 
/*  61 */       wComponentPeer.addChildPeer(this);
/*  62 */       createMenu(wComponentPeer);
/*     */       
/*  64 */       checkMenuCreation();
/*     */     } else {
/*  66 */       throw new IllegalArgumentException("illegal popup menu container class");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private native void createMenu(WComponentPeer paramWComponentPeer);
/*     */   
/*     */   public void show(Event paramEvent) {
/*  74 */     Component component = (Component)paramEvent.target;
/*  75 */     WComponentPeer wComponentPeer = (WComponentPeer)WToolkit.targetToPeer(component);
/*  76 */     if (wComponentPeer == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  81 */       Container container = WToolkit.getNativeContainer(component);
/*  82 */       paramEvent.target = container;
/*     */ 
/*     */       
/*  85 */       for (Component component1 = component; component1 != container; component1 = component1.getParent()) {
/*  86 */         Point point = component1.getLocation();
/*  87 */         paramEvent.x += point.x;
/*  88 */         paramEvent.y += point.y;
/*     */       } 
/*     */     } 
/*  91 */     _show(paramEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void show(Component paramComponent, Point paramPoint) {
/*  99 */     WComponentPeer wComponentPeer = (WComponentPeer)WToolkit.targetToPeer(paramComponent);
/* 100 */     Event event = new Event(paramComponent, 0L, 501, paramPoint.x, paramPoint.y, 0, 0);
/* 101 */     if (wComponentPeer == null) {
/* 102 */       Container container = WToolkit.getNativeContainer(paramComponent);
/* 103 */       event.target = container;
/*     */     } 
/* 105 */     event.x = paramPoint.x;
/* 106 */     event.y = paramPoint.y;
/* 107 */     _show(event);
/*     */   }
/*     */   
/*     */   private native void _show(Event paramEvent);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WPopupMenuPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */