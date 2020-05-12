/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Set;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.CausedFocusEvent;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.awt.TimedWindowEvent;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DefaultKeyboardFocusManager
/*      */   extends KeyboardFocusManager
/*      */ {
/*   66 */   private static final PlatformLogger focusLog = PlatformLogger.getLogger("java.awt.focus.DefaultKeyboardFocusManager");
/*      */ 
/*      */   
/*   69 */   private static final WeakReference<Window> NULL_WINDOW_WR = new WeakReference<>(null);
/*      */   
/*   71 */   private static final WeakReference<Component> NULL_COMPONENT_WR = new WeakReference<>(null);
/*      */   
/*   73 */   private WeakReference<Window> realOppositeWindowWR = NULL_WINDOW_WR;
/*   74 */   private WeakReference<Component> realOppositeComponentWR = NULL_COMPONENT_WR;
/*      */   private int inSendMessage;
/*   76 */   private LinkedList<KeyEvent> enqueuedKeyEvents = new LinkedList<>();
/*   77 */   private LinkedList<TypeAheadMarker> typeAheadMarkers = new LinkedList<>();
/*      */   private boolean consumeNextKeyTyped;
/*      */   private Component restoreFocusTo;
/*      */   
/*      */   static {
/*   82 */     AWTAccessor.setDefaultKeyboardFocusManagerAccessor(new AWTAccessor.DefaultKeyboardFocusManagerAccessor()
/*      */         {
/*      */           public void consumeNextKeyTyped(DefaultKeyboardFocusManager param1DefaultKeyboardFocusManager, KeyEvent param1KeyEvent) {
/*   85 */             param1DefaultKeyboardFocusManager.consumeNextKeyTyped(param1KeyEvent);
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private static class TypeAheadMarker {
/*      */     long after;
/*      */     Component untilFocused;
/*      */     
/*      */     TypeAheadMarker(long param1Long, Component param1Component) {
/*   95 */       this.after = param1Long;
/*   96 */       this.untilFocused = param1Component;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  102 */       return ">>> Marker after " + this.after + " on " + this.untilFocused;
/*      */     }
/*      */   }
/*      */   
/*      */   private Window getOwningFrameDialog(Window paramWindow) {
/*  107 */     while (paramWindow != null && !(paramWindow instanceof Frame) && !(paramWindow instanceof Dialog))
/*      */     {
/*  109 */       paramWindow = (Window)paramWindow.getParent();
/*      */     }
/*  111 */     return paramWindow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void restoreFocus(FocusEvent paramFocusEvent, Window paramWindow) {
/*  120 */     Component component1 = this.realOppositeComponentWR.get();
/*  121 */     Component component2 = paramFocusEvent.getComponent();
/*      */     
/*  123 */     if (paramWindow == null || !restoreFocus(paramWindow, component2, false))
/*      */     {
/*      */       
/*  126 */       if ((component1 == null || 
/*  127 */         !doRestoreFocus(component1, component2, false)) && (
/*  128 */         paramFocusEvent.getOppositeComponent() == null || 
/*  129 */         !doRestoreFocus(paramFocusEvent.getOppositeComponent(), component2, false)))
/*      */       {
/*  131 */         clearGlobalFocusOwnerPriv(); }  } 
/*      */   }
/*      */   
/*      */   private void restoreFocus(WindowEvent paramWindowEvent) {
/*  135 */     Window window = this.realOppositeWindowWR.get();
/*  136 */     if (window == null || 
/*  137 */       !restoreFocus(window, (Component)null, false))
/*      */     {
/*      */       
/*  140 */       if (paramWindowEvent.getOppositeWindow() == null || 
/*  141 */         !restoreFocus(paramWindowEvent.getOppositeWindow(), (Component)null, false))
/*      */       {
/*      */ 
/*      */         
/*  145 */         clearGlobalFocusOwnerPriv(); } 
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean restoreFocus(Window paramWindow, Component paramComponent, boolean paramBoolean) {
/*  150 */     this.restoreFocusTo = null;
/*      */     
/*  152 */     Component component = KeyboardFocusManager.getMostRecentFocusOwner(paramWindow);
/*      */     
/*  154 */     if (component != null && component != paramComponent) {
/*  155 */       if (getHeavyweight(paramWindow) != getNativeFocusOwner()) {
/*      */         
/*  157 */         if (!component.isShowing() || !component.canBeFocusOwner()) {
/*  158 */           component = component.getNextFocusCandidate();
/*      */         }
/*  160 */         if (component != null && component != paramComponent) {
/*  161 */           if (!component.requestFocus(false, CausedFocusEvent.Cause.ROLLBACK))
/*      */           {
/*  163 */             this.restoreFocusTo = component;
/*      */           }
/*  165 */           return true;
/*      */         } 
/*  167 */       } else if (doRestoreFocus(component, paramComponent, false)) {
/*  168 */         return true;
/*      */       } 
/*      */     }
/*  171 */     if (paramBoolean) {
/*  172 */       clearGlobalFocusOwnerPriv();
/*  173 */       return true;
/*      */     } 
/*  175 */     return false;
/*      */   }
/*      */   
/*      */   private boolean restoreFocus(Component paramComponent, boolean paramBoolean) {
/*  179 */     return doRestoreFocus(paramComponent, (Component)null, paramBoolean);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean doRestoreFocus(Component paramComponent1, Component paramComponent2, boolean paramBoolean) {
/*  184 */     boolean bool = true;
/*  185 */     if (paramComponent1 != paramComponent2 && paramComponent1.isShowing() && paramComponent1.canBeFocusOwner() && (
/*  186 */       bool = paramComponent1.requestFocus(false, CausedFocusEvent.Cause.ROLLBACK)))
/*      */     {
/*  188 */       return true;
/*      */     }
/*  190 */     if (!bool && getGlobalFocusedWindow() != SunToolkit.getContainingWindow(paramComponent1)) {
/*  191 */       this.restoreFocusTo = paramComponent1;
/*  192 */       return true;
/*      */     } 
/*  194 */     Component component = paramComponent1.getNextFocusCandidate();
/*  195 */     if (component != null && component != paramComponent2 && component
/*  196 */       .requestFocusInWindow(CausedFocusEvent.Cause.ROLLBACK))
/*      */     {
/*  198 */       return true; } 
/*  199 */     if (paramBoolean) {
/*  200 */       clearGlobalFocusOwnerPriv();
/*  201 */       return true;
/*      */     } 
/*  203 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class DefaultKeyboardFocusManagerSentEvent
/*      */     extends SentEvent
/*      */   {
/*      */     private static final long serialVersionUID = -2924743257508701758L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DefaultKeyboardFocusManagerSentEvent(AWTEvent param1AWTEvent, AppContext param1AppContext) {
/*  223 */       super(param1AWTEvent, param1AppContext);
/*      */     }
/*      */     
/*      */     public final void dispatch() {
/*  227 */       KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/*  228 */       DefaultKeyboardFocusManager defaultKeyboardFocusManager = (keyboardFocusManager instanceof DefaultKeyboardFocusManager) ? (DefaultKeyboardFocusManager)keyboardFocusManager : null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  233 */       if (defaultKeyboardFocusManager != null) {
/*  234 */         synchronized (defaultKeyboardFocusManager) {
/*  235 */           defaultKeyboardFocusManager.inSendMessage++;
/*      */         } 
/*      */       }
/*      */       
/*  239 */       super.dispatch();
/*      */       
/*  241 */       if (defaultKeyboardFocusManager != null) {
/*  242 */         synchronized (defaultKeyboardFocusManager) {
/*  243 */           defaultKeyboardFocusManager.inSendMessage--;
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean sendMessage(Component paramComponent, AWTEvent paramAWTEvent) {
/*  259 */     paramAWTEvent.isPosted = true;
/*  260 */     AppContext appContext1 = AppContext.getAppContext();
/*  261 */     final AppContext targetAppContext = paramComponent.appContext;
/*  262 */     final DefaultKeyboardFocusManagerSentEvent se = new DefaultKeyboardFocusManagerSentEvent(paramAWTEvent, appContext1);
/*      */ 
/*      */     
/*  265 */     if (appContext1 == appContext2) {
/*  266 */       defaultKeyboardFocusManagerSentEvent.dispatch();
/*      */     } else {
/*  268 */       if (appContext2.isDisposed()) {
/*  269 */         return false;
/*      */       }
/*  271 */       SunToolkit.postEvent(appContext2, defaultKeyboardFocusManagerSentEvent);
/*  272 */       if (EventQueue.isDispatchThread()) {
/*      */         
/*  274 */         EventDispatchThread eventDispatchThread = (EventDispatchThread)Thread.currentThread();
/*  275 */         eventDispatchThread.pumpEvents(1007, new Conditional() {
/*      */               public boolean evaluate() {
/*  277 */                 return (!se.dispatched && !targetAppContext.isDisposed());
/*      */               }
/*      */             });
/*      */       } else {
/*  281 */         synchronized (defaultKeyboardFocusManagerSentEvent) {
/*  282 */           while (!defaultKeyboardFocusManagerSentEvent.dispatched && !appContext2.isDisposed()) {
/*      */             try {
/*  284 */               defaultKeyboardFocusManagerSentEvent.wait(1000L);
/*  285 */             } catch (InterruptedException interruptedException) {
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  292 */     return defaultKeyboardFocusManagerSentEvent.dispatched;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean repostIfFollowsKeyEvents(WindowEvent paramWindowEvent) {
/*  304 */     if (!(paramWindowEvent instanceof TimedWindowEvent)) {
/*  305 */       return false;
/*      */     }
/*  307 */     TimedWindowEvent timedWindowEvent = (TimedWindowEvent)paramWindowEvent;
/*  308 */     long l = timedWindowEvent.getWhen();
/*  309 */     synchronized (this) {
/*  310 */       KeyEvent keyEvent = this.enqueuedKeyEvents.isEmpty() ? null : this.enqueuedKeyEvents.getFirst();
/*  311 */       if (keyEvent != null && l >= keyEvent.getWhen()) {
/*  312 */         TypeAheadMarker typeAheadMarker = this.typeAheadMarkers.isEmpty() ? null : this.typeAheadMarkers.getFirst();
/*  313 */         if (typeAheadMarker != null) {
/*  314 */           Window window = typeAheadMarker.untilFocused.getContainingWindow();
/*      */ 
/*      */           
/*  317 */           if (window != null && window.isFocused()) {
/*  318 */             SunToolkit.postEvent(AppContext.getAppContext(), new SequencedEvent(paramWindowEvent));
/*  319 */             return true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  324 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean dispatchEvent(AWTEvent paramAWTEvent) {
/*      */     WindowEvent windowEvent2;
/*      */     FocusEvent focusEvent;
/*      */     WindowEvent windowEvent1;
/*      */     Window window1;
/*      */     CausedFocusEvent.Cause cause;
/*      */     Component component1;
/*      */     Window window2;
/*      */     Component component2;
/*      */     Component component3;
/*  342 */     if (focusLog.isLoggable(PlatformLogger.Level.FINE) && (paramAWTEvent instanceof WindowEvent || paramAWTEvent instanceof FocusEvent)) {
/*  343 */       focusLog.fine("" + paramAWTEvent);
/*      */     }
/*  345 */     switch (paramAWTEvent.getID()) {
/*      */       case 207:
/*  347 */         if (!repostIfFollowsKeyEvents((WindowEvent)paramAWTEvent))
/*      */         
/*      */         { 
/*      */           
/*  351 */           WindowEvent windowEvent = (WindowEvent)paramAWTEvent;
/*  352 */           Window window3 = getGlobalFocusedWindow();
/*  353 */           Window window4 = windowEvent.getWindow();
/*  354 */           if (window4 != window3)
/*      */           {
/*      */ 
/*      */             
/*  358 */             if (!window4.isFocusableWindow() || 
/*  359 */               !window4.isVisible() || 
/*  360 */               !window4.isDisplayable())
/*      */             
/*      */             { 
/*  363 */               restoreFocus(windowEvent);
/*      */                }
/*      */             
/*      */             else
/*      */             
/*  368 */             { if (window3 != null) {
/*      */                 
/*  370 */                 boolean bool = sendMessage(window3, new WindowEvent(window3, 208, window4));
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  375 */                 if (!bool) {
/*  376 */                   setGlobalFocusOwner(null);
/*  377 */                   setGlobalFocusedWindow(null);
/*      */                 } 
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  385 */               Window window5 = getOwningFrameDialog(window4);
/*  386 */               Window window6 = getGlobalActiveWindow();
/*  387 */               if (window5 != window6)
/*  388 */               { sendMessage(window5, new WindowEvent(window5, 205, window6));
/*      */ 
/*      */ 
/*      */                 
/*  392 */                 if (window5 != getGlobalActiveWindow())
/*      */                 
/*      */                 { 
/*  395 */                   restoreFocus(windowEvent);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  806 */                   return true; }  }  setGlobalFocusedWindow(window4); if (window4 != getGlobalFocusedWindow()) { restoreFocus(windowEvent); } else { if (this.inSendMessage == 0) { Component component4 = KeyboardFocusManager.getMostRecentFocusOwner(window4); boolean bool = (this.restoreFocusTo != null && component4 == this.restoreFocusTo) ? true : false; if (component4 == null && window4.isFocusableWindow()) component4 = window4.getFocusTraversalPolicy().getInitialComponent(window4);  Component component5 = null; synchronized (KeyboardFocusManager.class) { component5 = window4.setTemporaryLostComponent((Component)null); }  if (focusLog.isLoggable(PlatformLogger.Level.FINER)) focusLog.finer("tempLost {0}, toFocus {1}", new Object[] { component5, component4 });  if (component5 != null) component5.requestFocusInWindow((bool && component5 == component4) ? CausedFocusEvent.Cause.ROLLBACK : CausedFocusEvent.Cause.ACTIVATION);  if (component4 != null && component4 != component5) component4.requestFocusInWindow(CausedFocusEvent.Cause.ACTIVATION);  }  this.restoreFocusTo = null; Window window = this.realOppositeWindowWR.get(); if (window != windowEvent.getOppositeWindow()) windowEvent = new WindowEvent(window4, 207, window);  return typeAheadAssertions(window4, windowEvent); }  }  }  }  return true;case 205: windowEvent2 = (WindowEvent)paramAWTEvent; window1 = getGlobalActiveWindow(); window2 = windowEvent2.getWindow(); if (window1 != window2) { if (window1 != null) { boolean bool = sendMessage(window1, new WindowEvent(window1, 206, window2)); if (!bool) setGlobalActiveWindow(null);  if (getGlobalActiveWindow() != null) return true;  }  setGlobalActiveWindow(window2); if (window2 == getGlobalActiveWindow()) return typeAheadAssertions(window2, windowEvent2);  }  return true;case 1004: this.restoreFocusTo = null; focusEvent = (FocusEvent)paramAWTEvent; cause = (focusEvent instanceof CausedFocusEvent) ? ((CausedFocusEvent)focusEvent).getCause() : CausedFocusEvent.Cause.UNKNOWN; component2 = getGlobalFocusOwner(); component3 = focusEvent.getComponent(); if (component2 == component3) { if (focusLog.isLoggable(PlatformLogger.Level.FINE)) focusLog.fine("Skipping {0} because focus owner is the same", new Object[] { paramAWTEvent });  dequeueKeyEvents(-1L, component3); } else { if (component2 != null) { boolean bool = sendMessage(component2, new CausedFocusEvent(component2, 1005, focusEvent.isTemporary(), component3, cause)); if (!bool) { setGlobalFocusOwner(null); if (!focusEvent.isTemporary()) setGlobalPermanentFocusOwner(null);  }  }  Window window3 = SunToolkit.getContainingWindow(component3); Window window4 = getGlobalFocusedWindow(); if (window3 != null && window3 != window4) { sendMessage(window3, new WindowEvent(window3, 207, window4)); if (window3 != getGlobalFocusedWindow()) { dequeueKeyEvents(-1L, component3); return true; }  }  if (!component3.isFocusable() || !component3.isShowing() || (!component3.isEnabled() && !cause.equals(CausedFocusEvent.Cause.UNKNOWN))) { dequeueKeyEvents(-1L, component3); if (KeyboardFocusManager.isAutoFocusTransferEnabled()) { if (window3 == null) { restoreFocus(focusEvent, window4); } else { restoreFocus(focusEvent, window3); }  setMostRecentFocusOwner(window3, null); }  } else { setGlobalFocusOwner(component3); if (component3 != getGlobalFocusOwner()) { dequeueKeyEvents(-1L, component3); if (KeyboardFocusManager.isAutoFocusTransferEnabled()) restoreFocus(focusEvent, window3);  } else { if (!focusEvent.isTemporary()) { setGlobalPermanentFocusOwner(component3); if (component3 != getGlobalPermanentFocusOwner()) { dequeueKeyEvents(-1L, component3); if (KeyboardFocusManager.isAutoFocusTransferEnabled()) restoreFocus(focusEvent, window3);  return true; }  }  setNativeFocusOwner(getHeavyweight(component3)); Component component = this.realOppositeComponentWR.get(); if (component != null && component != focusEvent.getOppositeComponent()) { focusEvent = new CausedFocusEvent(component3, 1004, focusEvent.isTemporary(), component, cause); focusEvent.isPosted = true; }  return typeAheadAssertions(component3, focusEvent); }  }  }  return true;case 1005: focusEvent = (FocusEvent)paramAWTEvent; component1 = getGlobalFocusOwner(); if (component1 == null) { if (focusLog.isLoggable(PlatformLogger.Level.FINE)) focusLog.fine("Skipping {0} because focus owner is null", new Object[] { paramAWTEvent });  } else if (component1 == focusEvent.getOppositeComponent()) { if (focusLog.isLoggable(PlatformLogger.Level.FINE)) focusLog.fine("Skipping {0} because current focus owner is equal to opposite", new Object[] { paramAWTEvent });  } else { setGlobalFocusOwner(null); if (getGlobalFocusOwner() != null) { restoreFocus(component1, true); } else { if (!focusEvent.isTemporary()) { setGlobalPermanentFocusOwner(null); if (getGlobalPermanentFocusOwner() != null) { restoreFocus(component1, true); return true; }  } else { component2 = component1.getContainingWindow(); if (component2 != null) component2.setTemporaryLostComponent(component1);  }  setNativeFocusOwner(null); focusEvent.setSource(component1); this.realOppositeComponentWR = (focusEvent.getOppositeComponent() != null) ? new WeakReference<>(component1) : NULL_COMPONENT_WR; return typeAheadAssertions(component1, focusEvent); }  }  return true;case 206: windowEvent1 = (WindowEvent)paramAWTEvent; component1 = getGlobalActiveWindow(); if (component1 != null) if (component1 == paramAWTEvent.getSource()) { setGlobalActiveWindow(null); if (getGlobalActiveWindow() == null) { windowEvent1.setSource(component1); return typeAheadAssertions(component1, windowEvent1); }  }   return true;case 208: if (!repostIfFollowsKeyEvents((WindowEvent)paramAWTEvent)) { windowEvent1 = (WindowEvent)paramAWTEvent; component1 = getGlobalFocusedWindow(); component2 = windowEvent1.getWindow(); component3 = getGlobalActiveWindow(); Window window = windowEvent1.getOppositeWindow(); if (focusLog.isLoggable(PlatformLogger.Level.FINE)) focusLog.fine("Active {0}, Current focused {1}, losing focus {2} opposite {3}", new Object[] { component3, component1, component2, window });  if (component1 != null) if (this.inSendMessage != 0 || component2 != component3 || window != component1) { Component component = getGlobalFocusOwner(); if (component != null) { Component component4 = null; if (window != null) { component4 = window.getTemporaryLostComponent(); if (component4 == null) component4 = window.getMostRecentFocusOwner();  }  if (component4 == null) component4 = window;  sendMessage(component, new CausedFocusEvent(component, 1005, true, component4, CausedFocusEvent.Cause.ACTIVATION)); }  setGlobalFocusedWindow(null); if (getGlobalFocusedWindow() != null) { restoreFocus((Window)component1, (Component)null, true); } else { windowEvent1.setSource(component1); this.realOppositeWindowWR = (window != null) ? (WeakReference)new WeakReference<>(component1) : NULL_WINDOW_WR; typeAheadAssertions(component1, windowEvent1); if (window == null) { sendMessage(component3, new WindowEvent((Window)component3, 206, null)); if (getGlobalActiveWindow() != null) restoreFocus((Window)component1, (Component)null, true);  }  }  }   }  return true;
/*      */       case 400:
/*      */       case 401:
/*      */       case 402:
/*      */         return typeAheadAssertions((Component)null, paramAWTEvent);
/*      */     } 
/*      */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
/*  829 */     Component component1 = paramKeyEvent.isPosted ? getFocusOwner() : paramKeyEvent.getComponent();
/*      */     
/*  831 */     if (component1 != null && component1.isShowing() && component1.canBeFocusOwner() && 
/*  832 */       !paramKeyEvent.isConsumed()) {
/*  833 */       Component component = paramKeyEvent.getComponent();
/*  834 */       if (component != null && component.isEnabled()) {
/*  835 */         redispatchEvent(component, paramKeyEvent);
/*      */       }
/*      */     } 
/*      */     
/*  839 */     boolean bool = false;
/*  840 */     List<KeyEventPostProcessor> list = getKeyEventPostProcessors();
/*  841 */     if (list != null) {
/*  842 */       Iterator<KeyEventPostProcessor> iterator = list.iterator();
/*  843 */       while (!bool && iterator.hasNext())
/*      */       {
/*      */         
/*  846 */         bool = ((KeyEventPostProcessor)iterator.next()).postProcessKeyEvent(paramKeyEvent);
/*      */       }
/*      */     } 
/*  849 */     if (!bool) {
/*  850 */       postProcessKeyEvent(paramKeyEvent);
/*      */     }
/*      */ 
/*      */     
/*  854 */     Component component2 = paramKeyEvent.getComponent();
/*  855 */     ComponentPeer componentPeer = component2.getPeer();
/*      */     
/*  857 */     if (componentPeer == null || componentPeer instanceof java.awt.peer.LightweightPeer) {
/*      */ 
/*      */       
/*  860 */       Container container = component2.getNativeContainer();
/*  861 */       if (container != null) {
/*  862 */         componentPeer = container.getPeer();
/*      */       }
/*      */     } 
/*  865 */     if (componentPeer != null) {
/*  866 */       componentPeer.handleEvent(paramKeyEvent);
/*      */     }
/*      */     
/*  869 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean postProcessKeyEvent(KeyEvent paramKeyEvent) {
/*  884 */     if (!paramKeyEvent.isConsumed()) {
/*  885 */       Component component = paramKeyEvent.getComponent();
/*      */       
/*  887 */       Container container = (component instanceof Container) ? (Container)component : component.getParent();
/*  888 */       if (container != null) {
/*  889 */         container.postProcessKeyEvent(paramKeyEvent);
/*      */       }
/*      */     } 
/*  892 */     return true;
/*      */   }
/*      */   
/*      */   private void pumpApprovedKeyEvents() {
/*      */     KeyEvent keyEvent;
/*      */     do {
/*  898 */       keyEvent = null;
/*  899 */       synchronized (this) {
/*  900 */         if (this.enqueuedKeyEvents.size() != 0) {
/*  901 */           keyEvent = this.enqueuedKeyEvents.getFirst();
/*  902 */           if (this.typeAheadMarkers.size() != 0) {
/*  903 */             TypeAheadMarker typeAheadMarker = this.typeAheadMarkers.getFirst();
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  908 */             if (keyEvent.getWhen() > typeAheadMarker.after) {
/*  909 */               keyEvent = null;
/*      */             }
/*      */           } 
/*  912 */           if (keyEvent != null) {
/*  913 */             if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  914 */               focusLog.finer("Pumping approved event {0}", new Object[] { keyEvent });
/*      */             }
/*  916 */             this.enqueuedKeyEvents.removeFirst();
/*      */           } 
/*      */         } 
/*      */       } 
/*  920 */       if (keyEvent == null)
/*  921 */         continue;  preDispatchKeyEvent(keyEvent);
/*      */     }
/*  923 */     while (keyEvent != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void dumpMarkers() {
/*  930 */     if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  931 */       focusLog.finest(">>> Markers dump, time: {0}", new Object[] { Long.valueOf(System.currentTimeMillis()) });
/*  932 */       synchronized (this) {
/*  933 */         if (this.typeAheadMarkers.size() != 0) {
/*  934 */           Iterator<TypeAheadMarker> iterator = this.typeAheadMarkers.iterator();
/*  935 */           while (iterator.hasNext()) {
/*  936 */             TypeAheadMarker typeAheadMarker = iterator.next();
/*  937 */             focusLog.finest("    {0}", new Object[] { typeAheadMarker });
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean typeAheadAssertions(Component paramComponent, AWTEvent paramAWTEvent) {
/*      */     KeyEvent keyEvent;
/*  949 */     pumpApprovedKeyEvents();
/*      */     
/*  951 */     switch (paramAWTEvent.getID()) {
/*      */       case 400:
/*      */       case 401:
/*      */       case 402:
/*  955 */         keyEvent = (KeyEvent)paramAWTEvent;
/*  956 */         synchronized (this) {
/*  957 */           if (paramAWTEvent.isPosted && this.typeAheadMarkers.size() != 0) {
/*  958 */             TypeAheadMarker typeAheadMarker = this.typeAheadMarkers.getFirst();
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  963 */             if (keyEvent.getWhen() > typeAheadMarker.after) {
/*  964 */               if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  965 */                 focusLog.finer("Storing event {0} because of marker {1}", new Object[] { keyEvent, typeAheadMarker });
/*      */               }
/*  967 */               this.enqueuedKeyEvents.addLast(keyEvent);
/*  968 */               return true;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  974 */         return preDispatchKeyEvent(keyEvent);
/*      */ 
/*      */       
/*      */       case 1004:
/*  978 */         if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  979 */           focusLog.finest("Markers before FOCUS_GAINED on {0}", new Object[] { paramComponent });
/*      */         }
/*  981 */         dumpMarkers();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  992 */         synchronized (this) {
/*  993 */           boolean bool = false;
/*  994 */           if (hasMarker(paramComponent)) {
/*  995 */             Iterator<TypeAheadMarker> iterator = this.typeAheadMarkers.iterator();
/*  996 */             while (iterator.hasNext())
/*      */             {
/*  998 */               if (((TypeAheadMarker)iterator.next()).untilFocused == paramComponent) {
/*  999 */                 bool = true;
/* 1000 */               } else if (bool) {
/*      */                 break;
/*      */               } 
/* 1003 */               iterator.remove();
/*      */             }
/*      */           
/*      */           }
/* 1007 */           else if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1008 */             focusLog.finer("Event without marker {0}", new Object[] { paramAWTEvent });
/*      */           } 
/*      */         } 
/*      */         
/* 1012 */         focusLog.finest("Markers after FOCUS_GAINED");
/* 1013 */         dumpMarkers();
/*      */         
/* 1015 */         redispatchEvent(paramComponent, paramAWTEvent);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1020 */         pumpApprovedKeyEvents();
/* 1021 */         return true;
/*      */     } 
/*      */     
/* 1024 */     redispatchEvent(paramComponent, paramAWTEvent);
/* 1025 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasMarker(Component paramComponent) {
/* 1035 */     for (Iterator<TypeAheadMarker> iterator = this.typeAheadMarkers.iterator(); iterator.hasNext();) {
/* 1036 */       if (((TypeAheadMarker)iterator.next()).untilFocused == paramComponent) {
/* 1037 */         return true;
/*      */       }
/*      */     } 
/* 1040 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void clearMarkers() {
/* 1048 */     synchronized (this) {
/* 1049 */       this.typeAheadMarkers.clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean preDispatchKeyEvent(KeyEvent paramKeyEvent) {
/* 1054 */     if (paramKeyEvent.isPosted) {
/* 1055 */       Component component = getFocusOwner();
/* 1056 */       paramKeyEvent.setSource((component != null) ? component : getFocusedWindow());
/*      */     } 
/* 1058 */     if (paramKeyEvent.getSource() == null) {
/* 1059 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1066 */     EventQueue.setCurrentEventAndMostRecentTime(paramKeyEvent);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1075 */     if (KeyboardFocusManager.isProxyActive(paramKeyEvent)) {
/* 1076 */       Component component = (Component)paramKeyEvent.getSource();
/* 1077 */       Container container = component.getNativeContainer();
/* 1078 */       if (container != null) {
/* 1079 */         ComponentPeer componentPeer = container.getPeer();
/* 1080 */         if (componentPeer != null) {
/* 1081 */           componentPeer.handleEvent(paramKeyEvent);
/*      */ 
/*      */ 
/*      */           
/* 1085 */           paramKeyEvent.consume();
/*      */         } 
/*      */       } 
/* 1088 */       return true;
/*      */     } 
/*      */     
/* 1091 */     List<KeyEventDispatcher> list = getKeyEventDispatchers();
/* 1092 */     if (list != null) {
/* 1093 */       Iterator<KeyEventDispatcher> iterator = list.iterator();
/* 1094 */       while (iterator.hasNext()) {
/*      */         
/* 1096 */         if (((KeyEventDispatcher)iterator.next())
/* 1097 */           .dispatchKeyEvent(paramKeyEvent))
/*      */         {
/* 1099 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/* 1103 */     return dispatchKeyEvent(paramKeyEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void consumeNextKeyTyped(KeyEvent paramKeyEvent) {
/* 1111 */     this.consumeNextKeyTyped = true;
/*      */   }
/*      */   
/*      */   private void consumeTraversalKey(KeyEvent paramKeyEvent) {
/* 1115 */     paramKeyEvent.consume();
/* 1116 */     this
/* 1117 */       .consumeNextKeyTyped = (paramKeyEvent.getID() == 401 && !paramKeyEvent.isActionKey());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean consumeProcessedKeyEvent(KeyEvent paramKeyEvent) {
/* 1124 */     if (paramKeyEvent.getID() == 400 && this.consumeNextKeyTyped) {
/* 1125 */       paramKeyEvent.consume();
/* 1126 */       this.consumeNextKeyTyped = false;
/* 1127 */       return true;
/*      */     } 
/* 1129 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processKeyEvent(Component paramComponent, KeyEvent paramKeyEvent) {
/* 1147 */     if (consumeProcessedKeyEvent(paramKeyEvent)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1152 */     if (paramKeyEvent.getID() == 400) {
/*      */       return;
/*      */     }
/*      */     
/* 1156 */     if (paramComponent.getFocusTraversalKeysEnabled() && 
/* 1157 */       !paramKeyEvent.isConsumed()) {
/*      */       
/* 1159 */       AWTKeyStroke aWTKeyStroke1 = AWTKeyStroke.getAWTKeyStrokeForEvent(paramKeyEvent);
/* 1160 */       AWTKeyStroke aWTKeyStroke2 = AWTKeyStroke.getAWTKeyStroke(aWTKeyStroke1.getKeyCode(), aWTKeyStroke1
/* 1161 */           .getModifiers(), 
/* 1162 */           !aWTKeyStroke1.isOnKeyRelease());
/*      */ 
/*      */ 
/*      */       
/* 1166 */       Set<AWTKeyStroke> set = paramComponent.getFocusTraversalKeys(0);
/*      */       
/* 1168 */       boolean bool1 = set.contains(aWTKeyStroke1);
/* 1169 */       boolean bool2 = set.contains(aWTKeyStroke2);
/*      */       
/* 1171 */       if (bool1 || bool2) {
/* 1172 */         consumeTraversalKey(paramKeyEvent);
/* 1173 */         if (bool1)
/* 1174 */           focusNextComponent(paramComponent); 
/*      */         return;
/*      */       } 
/* 1177 */       if (paramKeyEvent.getID() == 401)
/*      */       {
/* 1179 */         this.consumeNextKeyTyped = false;
/*      */       }
/*      */       
/* 1182 */       set = paramComponent.getFocusTraversalKeys(1);
/*      */       
/* 1184 */       bool1 = set.contains(aWTKeyStroke1);
/* 1185 */       bool2 = set.contains(aWTKeyStroke2);
/*      */       
/* 1187 */       if (bool1 || bool2) {
/* 1188 */         consumeTraversalKey(paramKeyEvent);
/* 1189 */         if (bool1) {
/* 1190 */           focusPreviousComponent(paramComponent);
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/* 1195 */       set = paramComponent.getFocusTraversalKeys(2);
/*      */       
/* 1197 */       bool1 = set.contains(aWTKeyStroke1);
/* 1198 */       bool2 = set.contains(aWTKeyStroke2);
/*      */       
/* 1200 */       if (bool1 || bool2) {
/* 1201 */         consumeTraversalKey(paramKeyEvent);
/* 1202 */         if (bool1) {
/* 1203 */           upFocusCycle(paramComponent);
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/* 1208 */       if (!(paramComponent instanceof Container) || 
/* 1209 */         !((Container)paramComponent).isFocusCycleRoot()) {
/*      */         return;
/*      */       }
/*      */       
/* 1213 */       set = paramComponent.getFocusTraversalKeys(3);
/*      */       
/* 1215 */       bool1 = set.contains(aWTKeyStroke1);
/* 1216 */       bool2 = set.contains(aWTKeyStroke2);
/*      */       
/* 1218 */       if (bool1 || bool2) {
/* 1219 */         consumeTraversalKey(paramKeyEvent);
/* 1220 */         if (bool1) {
/* 1221 */           downFocusCycle((Container)paramComponent);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void enqueueKeyEvents(long paramLong, Component paramComponent) {
/* 1244 */     if (paramComponent == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1248 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1249 */       focusLog.finer("Enqueue at {0} for {1}", new Object[] {
/* 1250 */             Long.valueOf(paramLong), paramComponent
/*      */           });
/*      */     }
/* 1253 */     int i = 0;
/* 1254 */     int j = this.typeAheadMarkers.size();
/* 1255 */     ListIterator<TypeAheadMarker> listIterator = this.typeAheadMarkers.listIterator(j);
/*      */     
/* 1257 */     for (; j > 0; j--) {
/* 1258 */       TypeAheadMarker typeAheadMarker = listIterator.previous();
/* 1259 */       if (typeAheadMarker.after <= paramLong) {
/* 1260 */         i = j;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1265 */     this.typeAheadMarkers.add(i, new TypeAheadMarker(paramLong, paramComponent));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void dequeueKeyEvents(long paramLong, Component paramComponent) {
/* 1286 */     if (paramComponent == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1290 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1291 */       focusLog.finer("Dequeue at {0} for {1}", new Object[] {
/* 1292 */             Long.valueOf(paramLong), paramComponent
/*      */           });
/*      */     }
/*      */ 
/*      */     
/* 1297 */     ListIterator<TypeAheadMarker> listIterator = this.typeAheadMarkers.listIterator((paramLong >= 0L) ? this.typeAheadMarkers.size() : 0);
/*      */     
/* 1299 */     if (paramLong < 0L) {
/* 1300 */       while (listIterator.hasNext()) {
/* 1301 */         TypeAheadMarker typeAheadMarker = listIterator.next();
/* 1302 */         if (typeAheadMarker.untilFocused == paramComponent) {
/*      */           
/* 1304 */           listIterator.remove();
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1309 */       while (listIterator.hasPrevious()) {
/* 1310 */         TypeAheadMarker typeAheadMarker = listIterator.previous();
/* 1311 */         if (typeAheadMarker.untilFocused == paramComponent && typeAheadMarker.after == paramLong) {
/*      */ 
/*      */           
/* 1314 */           listIterator.remove();
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void discardKeyEvents(Component paramComponent) {
/* 1332 */     if (paramComponent == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1336 */     long l = -1L;
/*      */     
/* 1338 */     for (Iterator<TypeAheadMarker> iterator = this.typeAheadMarkers.iterator(); iterator.hasNext(); ) {
/* 1339 */       TypeAheadMarker typeAheadMarker = iterator.next();
/* 1340 */       Component component = typeAheadMarker.untilFocused;
/* 1341 */       boolean bool = (component == paramComponent) ? true : false;
/* 1342 */       while (!bool && component != null && !(component instanceof Window)) {
/* 1343 */         component = component.getParent();
/* 1344 */         bool = (component == paramComponent) ? true : false;
/*      */       } 
/* 1346 */       if (bool) {
/* 1347 */         if (l < 0L) {
/* 1348 */           l = typeAheadMarker.after;
/*      */         }
/* 1350 */         iterator.remove(); continue;
/* 1351 */       }  if (l >= 0L) {
/* 1352 */         purgeStampedEvents(l, typeAheadMarker.after);
/* 1353 */         l = -1L;
/*      */       } 
/*      */     } 
/*      */     
/* 1357 */     purgeStampedEvents(l, -1L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void purgeStampedEvents(long paramLong1, long paramLong2) {
/* 1366 */     if (paramLong1 < 0L) {
/*      */       return;
/*      */     }
/*      */     
/* 1370 */     for (Iterator<KeyEvent> iterator = this.enqueuedKeyEvents.iterator(); iterator.hasNext(); ) {
/* 1371 */       KeyEvent keyEvent = iterator.next();
/* 1372 */       long l = keyEvent.getWhen();
/*      */       
/* 1374 */       if (paramLong1 < l && (paramLong2 < 0L || l <= paramLong2)) {
/* 1375 */         iterator.remove();
/*      */       }
/*      */       
/* 1378 */       if (paramLong2 >= 0L && l > paramLong2) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void focusPreviousComponent(Component paramComponent) {
/* 1394 */     if (paramComponent != null) {
/* 1395 */       paramComponent.transferFocusBackward();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void focusNextComponent(Component paramComponent) {
/* 1409 */     if (paramComponent != null) {
/* 1410 */       paramComponent.transferFocus();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void upFocusCycle(Component paramComponent) {
/* 1427 */     if (paramComponent != null) {
/* 1428 */       paramComponent.transferFocusUpCycle();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void downFocusCycle(Container paramContainer) {
/* 1444 */     if (paramContainer != null && paramContainer.isFocusCycleRoot())
/* 1445 */       paramContainer.transferFocusDownCycle(); 
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\DefaultKeyboardFocusManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */