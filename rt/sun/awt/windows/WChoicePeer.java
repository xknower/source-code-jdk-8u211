/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Choice;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.event.WindowListener;
/*     */ import java.awt.peer.ChoicePeer;
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
/*     */ final class WChoicePeer
/*     */   extends WComponentPeer
/*     */   implements ChoicePeer
/*     */ {
/*     */   private WindowListener windowListener;
/*     */   
/*     */   public Dimension getMinimumSize() {
/*  41 */     FontMetrics fontMetrics = getFontMetrics(((Choice)this.target).getFont());
/*  42 */     Choice choice = (Choice)this.target;
/*  43 */     int i = 0;
/*  44 */     for (int j = choice.getItemCount(); j-- > 0;) {
/*  45 */       i = Math.max(fontMetrics.stringWidth(choice.getItem(j)), i);
/*     */     }
/*  47 */     return new Dimension(28 + i, Math.max(fontMetrics.getHeight() + 6, 15));
/*     */   }
/*     */   
/*     */   public boolean isFocusable() {
/*  51 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public native void select(int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String paramString, int paramInt) {
/*  61 */     addItem(paramString, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldClearRectBeforePaint() {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public native void removeAll();
/*     */ 
/*     */   
/*     */   public native void remove(int paramInt);
/*     */ 
/*     */   
/*     */   public void addItem(String paramString, int paramInt) {
/*  78 */     addItems(new String[] { paramString }, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public native void addItems(String[] paramArrayOfString, int paramInt);
/*     */ 
/*     */   
/*     */   public synchronized native void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   
/*     */   WChoicePeer(Choice paramChoice) {
/*  90 */     super(paramChoice);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   native void create(WComponentPeer paramWComponentPeer);
/*     */ 
/*     */   
/*     */   void initialize() {
/*  99 */     Choice choice = (Choice)this.target;
/* 100 */     int i = choice.getItemCount();
/* 101 */     if (i > 0) {
/* 102 */       String[] arrayOfString = new String[i];
/* 103 */       for (byte b = 0; b < i; b++) {
/* 104 */         arrayOfString[b] = choice.getItem(b);
/*     */       }
/* 106 */       addItems(arrayOfString, 0);
/* 107 */       if (choice.getSelectedIndex() >= 0) {
/* 108 */         select(choice.getSelectedIndex());
/*     */       }
/*     */     } 
/*     */     
/* 112 */     Window window = SunToolkit.getContainingWindow((Component)this.target);
/* 113 */     if (window != null) {
/* 114 */       WWindowPeer wWindowPeer = (WWindowPeer)window.getPeer();
/* 115 */       if (wWindowPeer != null) {
/* 116 */         this.windowListener = new WindowAdapter()
/*     */           {
/*     */             public void windowIconified(WindowEvent param1WindowEvent) {
/* 119 */               WChoicePeer.this.closeList();
/*     */             }
/*     */             
/*     */             public void windowClosing(WindowEvent param1WindowEvent) {
/* 123 */               WChoicePeer.this.closeList();
/*     */             }
/*     */           };
/* 126 */         wWindowPeer.addWindowListener(this.windowListener);
/*     */       } 
/*     */     } 
/* 129 */     super.initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void disposeImpl() {
/* 137 */     Window window = SunToolkit.getContainingWindow((Component)this.target);
/* 138 */     if (window != null) {
/* 139 */       WWindowPeer wWindowPeer = (WWindowPeer)window.getPeer();
/* 140 */       if (wWindowPeer != null) {
/* 141 */         wWindowPeer.removeWindowListener(this.windowListener);
/*     */       }
/*     */     } 
/* 144 */     super.disposeImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void handleAction(final int index) {
/* 150 */     final Choice c = (Choice)this.target;
/* 151 */     WToolkit.executeOnEventHandlerThread(choice, new Runnable()
/*     */         {
/*     */           public void run() {
/* 154 */             c.select(index);
/* 155 */             WChoicePeer.this.postEvent(new ItemEvent(c, 701, c
/* 156 */                   .getItem(index), 1));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   int getDropDownHeight() {
/* 162 */     Choice choice = (Choice)this.target;
/* 163 */     FontMetrics fontMetrics = getFontMetrics(choice.getFont());
/* 164 */     int i = Math.min(choice.getItemCount(), 8);
/* 165 */     return fontMetrics.getHeight() * i;
/*     */   }
/*     */   
/*     */   native void closeList();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WChoicePeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */