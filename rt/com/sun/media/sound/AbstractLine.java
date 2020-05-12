/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.sound.sampled.Control;
/*     */ import javax.sound.sampled.Line;
/*     */ import javax.sound.sampled.LineEvent;
/*     */ import javax.sound.sampled.LineListener;
/*     */ import javax.sound.sampled.LineUnavailableException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractLine
/*     */   implements Line
/*     */ {
/*     */   protected final Line.Info info;
/*     */   protected Control[] controls;
/*     */   AbstractMixer mixer;
/*     */   private boolean open = false;
/*  51 */   private final Vector listeners = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private static final Map<ThreadGroup, EventDispatcher> dispatchers = new WeakHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractLine(Line.Info paramInfo, AbstractMixer paramAbstractMixer, Control[] paramArrayOfControl) {
/*  66 */     if (paramArrayOfControl == null) {
/*  67 */       paramArrayOfControl = new Control[0];
/*     */     }
/*     */     
/*  70 */     this.info = paramInfo;
/*  71 */     this.mixer = paramAbstractMixer;
/*  72 */     this.controls = paramArrayOfControl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Line.Info getLineInfo() {
/*  79 */     return this.info;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isOpen() {
/*  84 */     return this.open;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void addLineListener(LineListener paramLineListener) {
/*  89 */     synchronized (this.listeners) {
/*  90 */       if (!this.listeners.contains(paramLineListener)) {
/*  91 */         this.listeners.addElement(paramLineListener);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void removeLineListener(LineListener paramLineListener) {
/* 102 */     this.listeners.removeElement(paramLineListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Control[] getControls() {
/* 113 */     Control[] arrayOfControl = new Control[this.controls.length];
/*     */     
/* 115 */     for (byte b = 0; b < this.controls.length; b++) {
/* 116 */       arrayOfControl[b] = this.controls[b];
/*     */     }
/*     */     
/* 119 */     return arrayOfControl;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isControlSupported(Control.Type paramType) {
/* 125 */     if (paramType == null) {
/* 126 */       return false;
/*     */     }
/*     */     
/* 129 */     for (byte b = 0; b < this.controls.length; b++) {
/* 130 */       if (paramType == this.controls[b].getType()) {
/* 131 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 135 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Control getControl(Control.Type paramType) {
/* 141 */     if (paramType != null)
/*     */     {
/* 143 */       for (byte b = 0; b < this.controls.length; b++) {
/* 144 */         if (paramType == this.controls[b].getType()) {
/* 145 */           return this.controls[b];
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 150 */     throw new IllegalArgumentException("Unsupported control type: " + paramType);
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
/*     */   final void setOpen(boolean paramBoolean) {
/* 165 */     boolean bool = false;
/* 166 */     long l = getLongFramePosition();
/*     */     
/* 168 */     synchronized (this) {
/* 169 */       if (this.open != paramBoolean) {
/* 170 */         this.open = paramBoolean;
/* 171 */         bool = true;
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     if (bool) {
/* 176 */       if (paramBoolean) {
/* 177 */         sendEvents(new LineEvent(this, LineEvent.Type.OPEN, l));
/*     */       } else {
/* 179 */         sendEvents(new LineEvent(this, LineEvent.Type.CLOSE, l));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void sendEvents(LineEvent paramLineEvent) {
/* 190 */     getEventDispatcher().sendAudioEvents(paramLineEvent, this.listeners);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getFramePosition() {
/* 200 */     return (int)getLongFramePosition();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLongFramePosition() {
/* 209 */     return -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final AbstractMixer getMixer() {
/* 217 */     return this.mixer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final EventDispatcher getEventDispatcher() {
/* 223 */     ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
/* 224 */     synchronized (dispatchers) {
/* 225 */       EventDispatcher eventDispatcher = dispatchers.get(threadGroup);
/* 226 */       if (eventDispatcher == null) {
/* 227 */         eventDispatcher = new EventDispatcher();
/* 228 */         dispatchers.put(threadGroup, eventDispatcher);
/* 229 */         eventDispatcher.start();
/*     */       } 
/* 231 */       return eventDispatcher;
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract void open() throws LineUnavailableException;
/*     */   
/*     */   public abstract void close();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\AbstractLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */