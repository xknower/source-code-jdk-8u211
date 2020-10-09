/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.spi.AbstractSelectionKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SelectionKeyImpl
/*     */   extends AbstractSelectionKey
/*     */ {
/*     */   final SelChImpl channel;
/*     */   public final SelectorImpl selector;
/*     */   private int index;
/*     */   private volatile int interestOps;
/*     */   private int readyOps;
/*     */   
/*     */   SelectionKeyImpl(SelChImpl paramSelChImpl, SelectorImpl paramSelectorImpl) {
/*  51 */     this.channel = paramSelChImpl;
/*  52 */     this.selector = paramSelectorImpl;
/*     */   }
/*     */   
/*     */   public SelectableChannel channel() {
/*  56 */     return (SelectableChannel)this.channel;
/*     */   }
/*     */   
/*     */   public Selector selector() {
/*  60 */     return this.selector;
/*     */   }
/*     */   
/*     */   int getIndex() {
/*  64 */     return this.index;
/*     */   }
/*     */   
/*     */   void setIndex(int paramInt) {
/*  68 */     this.index = paramInt;
/*     */   }
/*     */   
/*     */   private void ensureValid() {
/*  72 */     if (!isValid())
/*  73 */       throw new CancelledKeyException(); 
/*     */   }
/*     */   
/*     */   public int interestOps() {
/*  77 */     ensureValid();
/*  78 */     return this.interestOps;
/*     */   }
/*     */   
/*     */   public SelectionKey interestOps(int paramInt) {
/*  82 */     ensureValid();
/*  83 */     return nioInterestOps(paramInt);
/*     */   }
/*     */   
/*     */   public int readyOps() {
/*  87 */     ensureValid();
/*  88 */     return this.readyOps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nioReadyOps(int paramInt) {
/*  95 */     this.readyOps = paramInt;
/*     */   }
/*     */   
/*     */   public int nioReadyOps() {
/*  99 */     return this.readyOps;
/*     */   }
/*     */   
/*     */   public SelectionKey nioInterestOps(int paramInt) {
/* 103 */     if ((paramInt & (channel().validOps() ^ 0xFFFFFFFF)) != 0)
/* 104 */       throw new IllegalArgumentException(); 
/* 105 */     this.channel.translateAndSetInterestOps(paramInt, this);
/* 106 */     this.interestOps = paramInt;
/* 107 */     return this;
/*     */   }
/*     */   
/*     */   public int nioInterestOps() {
/* 111 */     return this.interestOps;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\SelectionKeyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */