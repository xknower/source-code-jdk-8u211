/*     */ package java.awt;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.SunToolkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SequencedEvent
/*     */   extends AWTEvent
/*     */   implements ActiveEvent
/*     */ {
/*     */   private static final long serialVersionUID = 547742659238625067L;
/*     */   private static final int ID = 1006;
/*  52 */   private static final LinkedList<SequencedEvent> list = new LinkedList<>();
/*     */   
/*     */   private final AWTEvent nested;
/*     */   private AppContext appContext;
/*     */   private boolean disposed;
/*     */   
/*     */   static {
/*  59 */     AWTAccessor.setSequencedEventAccessor(new AWTAccessor.SequencedEventAccessor() {
/*     */           public AWTEvent getNested(AWTEvent param1AWTEvent) {
/*  61 */             return ((SequencedEvent)param1AWTEvent).nested;
/*     */           }
/*     */           public boolean isSequencedEvent(AWTEvent param1AWTEvent) {
/*  64 */             return param1AWTEvent instanceof SequencedEvent;
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
/*     */   public SequencedEvent(AWTEvent paramAWTEvent) {
/*  77 */     super(paramAWTEvent.getSource(), 1006);
/*  78 */     this.nested = paramAWTEvent;
/*     */ 
/*     */     
/*  81 */     SunToolkit.setSystemGenerated(paramAWTEvent);
/*  82 */     synchronized (SequencedEvent.class) {
/*  83 */       list.add(this);
/*     */     } 
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
/*     */   public final void dispatch() {
/*     */     try {
/* 101 */       this.appContext = AppContext.getAppContext();
/*     */       
/* 103 */       if (getFirst() != this) {
/* 104 */         if (EventQueue.isDispatchThread()) {
/*     */           
/* 106 */           EventDispatchThread eventDispatchThread = (EventDispatchThread)Thread.currentThread();
/* 107 */           eventDispatchThread.pumpEvents(1007, new Conditional() {
/*     */                 public boolean evaluate() {
/* 109 */                   return !SequencedEvent.this.isFirstOrDisposed();
/*     */                 }
/*     */               });
/*     */         } else {
/* 113 */           while (!isFirstOrDisposed()) {
/* 114 */             synchronized (SequencedEvent.class) {
/*     */               try {
/* 116 */                 SequencedEvent.class.wait(1000L);
/* 117 */               } catch (InterruptedException interruptedException) {
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 125 */       if (!this.disposed) {
/* 126 */         KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 127 */           .setCurrentSequencedEvent(this);
/* 128 */         Toolkit.getEventQueue().dispatchEvent(this.nested);
/*     */       } 
/*     */     } finally {
/* 131 */       dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean isOwnerAppContextDisposed(SequencedEvent paramSequencedEvent) {
/* 139 */     if (paramSequencedEvent != null) {
/* 140 */       Object object = paramSequencedEvent.nested.getSource();
/* 141 */       if (object instanceof Component) {
/* 142 */         return ((Component)object).appContext.isDisposed();
/*     */       }
/*     */     } 
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isFirstOrDisposed() {
/* 155 */     if (this.disposed) {
/* 156 */       return true;
/*     */     }
/*     */     
/* 159 */     return (this == getFirstWithContext() || this.disposed);
/*     */   }
/*     */   
/*     */   private static final synchronized SequencedEvent getFirst() {
/* 163 */     return list.getFirst();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final SequencedEvent getFirstWithContext() {
/* 170 */     SequencedEvent sequencedEvent = getFirst();
/* 171 */     while (isOwnerAppContextDisposed(sequencedEvent)) {
/* 172 */       sequencedEvent.dispose();
/* 173 */       sequencedEvent = getFirst();
/*     */     } 
/* 175 */     return sequencedEvent;
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
/*     */   final void dispose() {
/* 189 */     synchronized (SequencedEvent.class) {
/* 190 */       if (this.disposed) {
/*     */         return;
/*     */       }
/* 193 */       if (KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 194 */         .getCurrentSequencedEvent() == this) {
/* 195 */         KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 196 */           .setCurrentSequencedEvent(null);
/*     */       }
/* 198 */       this.disposed = true;
/*     */     } 
/*     */     
/* 201 */     if (this.appContext != null) {
/* 202 */       SunToolkit.postEvent(this.appContext, new SentEvent());
/*     */     }
/*     */     
/* 205 */     SequencedEvent sequencedEvent = null;
/*     */     
/* 207 */     synchronized (SequencedEvent.class) {
/* 208 */       SequencedEvent.class.notifyAll();
/*     */       
/* 210 */       if (list.getFirst() == this) {
/* 211 */         list.removeFirst();
/*     */         
/* 213 */         if (!list.isEmpty()) {
/* 214 */           sequencedEvent = list.getFirst();
/*     */         }
/*     */       } else {
/* 217 */         list.remove(this);
/*     */       } 
/*     */     } 
/*     */     
/* 221 */     if (sequencedEvent != null && sequencedEvent.appContext != null)
/* 222 */       SunToolkit.postEvent(sequencedEvent.appContext, new SentEvent()); 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\SequencedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */