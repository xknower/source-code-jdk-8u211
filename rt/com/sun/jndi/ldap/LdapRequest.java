/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import javax.naming.CommunicationException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LdapRequest
/*     */ {
/*     */   LdapRequest next;
/*     */   int msgId;
/*  38 */   private int gotten = 0;
/*     */   private BlockingQueue<BerDecoder> replies;
/*  40 */   private int highWatermark = -1;
/*     */   private boolean cancelled = false;
/*     */   private boolean pauseAfterReceipt = false;
/*     */   private boolean completed = false;
/*     */   
/*     */   LdapRequest(int paramInt, boolean paramBoolean) {
/*  46 */     this(paramInt, paramBoolean, -1);
/*     */   }
/*     */   
/*     */   LdapRequest(int paramInt1, boolean paramBoolean, int paramInt2) {
/*  50 */     this.msgId = paramInt1;
/*  51 */     this.pauseAfterReceipt = paramBoolean;
/*  52 */     if (paramInt2 == -1) {
/*  53 */       this.replies = new LinkedBlockingQueue<>();
/*     */     } else {
/*  55 */       this.replies = new LinkedBlockingQueue<>(paramInt2);
/*     */       
/*  57 */       this.highWatermark = paramInt2 * 80 / 100;
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized void cancel() {
/*  62 */     this.cancelled = true;
/*     */ 
/*     */ 
/*     */     
/*  66 */     notify();
/*     */   }
/*     */   
/*     */   synchronized boolean addReplyBer(BerDecoder paramBerDecoder) {
/*  70 */     if (this.cancelled) {
/*  71 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  76 */       this.replies.put(paramBerDecoder);
/*  77 */     } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  83 */       paramBerDecoder.parseSeq(null);
/*  84 */       paramBerDecoder.parseInt();
/*  85 */       this.completed = (paramBerDecoder.peekByte() == 101);
/*  86 */     } catch (IOException iOException) {}
/*     */ 
/*     */     
/*  89 */     paramBerDecoder.reset();
/*     */     
/*  91 */     notify();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     if (this.highWatermark != -1 && this.replies.size() >= this.highWatermark) {
/*  98 */       return true;
/*     */     }
/* 100 */     return this.pauseAfterReceipt;
/*     */   }
/*     */   
/*     */   synchronized BerDecoder getReplyBer() throws CommunicationException {
/* 104 */     if (this.cancelled) {
/* 105 */       throw new CommunicationException("Request: " + this.msgId + " cancelled");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     return this.replies.poll();
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized boolean hasSearchCompleted() {
/* 118 */     return this.completed;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jndi\ldap\LdapRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */