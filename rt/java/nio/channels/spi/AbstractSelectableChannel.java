/*     */ package java.nio.channels.spi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.IllegalBlockingModeException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSelectableChannel
/*     */   extends SelectableChannel
/*     */ {
/*     */   private final SelectorProvider provider;
/*  61 */   private SelectionKey[] keys = null;
/*  62 */   private int keyCount = 0;
/*     */ 
/*     */   
/*  65 */   private final Object keyLock = new Object();
/*     */ 
/*     */   
/*  68 */   private final Object regLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean blocking = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractSelectableChannel(SelectorProvider paramSelectorProvider) {
/*  80 */     this.provider = paramSelectorProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final SelectorProvider provider() {
/*  89 */     return this.provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addKey(SelectionKey paramSelectionKey) {
/*  96 */     assert Thread.holdsLock(this.keyLock);
/*  97 */     int i = 0;
/*  98 */     if (this.keys != null && this.keyCount < this.keys.length) {
/*     */       
/* 100 */       for (i = 0; i < this.keys.length && 
/* 101 */         this.keys[i] != null; i++);
/*     */     }
/* 103 */     else if (this.keys == null) {
/* 104 */       this.keys = new SelectionKey[3];
/*     */     } else {
/*     */       
/* 107 */       int j = this.keys.length * 2;
/* 108 */       SelectionKey[] arrayOfSelectionKey = new SelectionKey[j];
/* 109 */       for (i = 0; i < this.keys.length; i++)
/* 110 */         arrayOfSelectionKey[i] = this.keys[i]; 
/* 111 */       this.keys = arrayOfSelectionKey;
/* 112 */       i = this.keyCount;
/*     */     } 
/* 114 */     this.keys[i] = paramSelectionKey;
/* 115 */     this.keyCount++;
/*     */   }
/*     */   
/*     */   private SelectionKey findKey(Selector paramSelector) {
/* 119 */     synchronized (this.keyLock) {
/* 120 */       if (this.keys == null)
/* 121 */         return null; 
/* 122 */       for (byte b = 0; b < this.keys.length; b++) {
/* 123 */         if (this.keys[b] != null && this.keys[b].selector() == paramSelector)
/* 124 */           return this.keys[b]; 
/* 125 */       }  return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   void removeKey(SelectionKey paramSelectionKey) {
/* 130 */     synchronized (this.keyLock) {
/* 131 */       for (byte b = 0; b < this.keys.length; b++) {
/* 132 */         if (this.keys[b] == paramSelectionKey) {
/* 133 */           this.keys[b] = null;
/* 134 */           this.keyCount--;
/*     */         } 
/* 136 */       }  ((AbstractSelectionKey)paramSelectionKey).invalidate();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean haveValidKeys() {
/* 141 */     synchronized (this.keyLock) {
/* 142 */       if (this.keyCount == 0)
/* 143 */         return false; 
/* 144 */       for (byte b = 0; b < this.keys.length; b++) {
/* 145 */         if (this.keys[b] != null && this.keys[b].isValid())
/* 146 */           return true; 
/*     */       } 
/* 148 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isRegistered() {
/* 156 */     synchronized (this.keyLock) {
/* 157 */       return (this.keyCount != 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final SelectionKey keyFor(Selector paramSelector) {
/* 162 */     return findKey(paramSelector);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final SelectionKey register(Selector paramSelector, int paramInt, Object paramObject) throws ClosedChannelException {
/* 195 */     synchronized (this.regLock) {
/* 196 */       if (!isOpen())
/* 197 */         throw new ClosedChannelException(); 
/* 198 */       if ((paramInt & (validOps() ^ 0xFFFFFFFF)) != 0)
/* 199 */         throw new IllegalArgumentException(); 
/* 200 */       if (this.blocking)
/* 201 */         throw new IllegalBlockingModeException(); 
/* 202 */       SelectionKey selectionKey = findKey(paramSelector);
/* 203 */       if (selectionKey != null) {
/* 204 */         selectionKey.interestOps(paramInt);
/* 205 */         selectionKey.attach(paramObject);
/*     */       } 
/* 207 */       if (selectionKey == null)
/*     */       {
/* 209 */         synchronized (this.keyLock) {
/* 210 */           if (!isOpen())
/* 211 */             throw new ClosedChannelException(); 
/* 212 */           selectionKey = ((AbstractSelector)paramSelector).register(this, paramInt, paramObject);
/* 213 */           addKey(selectionKey);
/*     */         } 
/*     */       }
/* 216 */       return selectionKey;
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
/*     */   
/*     */   protected final void implCloseChannel() throws IOException {
/* 234 */     implCloseSelectableChannel();
/* 235 */     synchronized (this.keyLock) {
/* 236 */       byte b1 = (this.keys == null) ? 0 : this.keys.length;
/* 237 */       for (byte b2 = 0; b2 < b1; b2++) {
/* 238 */         SelectionKey selectionKey = this.keys[b2];
/* 239 */         if (selectionKey != null) {
/* 240 */           selectionKey.cancel();
/*     */         }
/*     */       } 
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
/*     */   protected abstract void implCloseSelectableChannel() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isBlocking() {
/* 267 */     synchronized (this.regLock) {
/* 268 */       return this.blocking;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final Object blockingLock() {
/* 273 */     return this.regLock;
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
/*     */   public final SelectableChannel configureBlocking(boolean paramBoolean) throws IOException {
/* 287 */     synchronized (this.regLock) {
/* 288 */       if (!isOpen())
/* 289 */         throw new ClosedChannelException(); 
/* 290 */       if (this.blocking == paramBoolean)
/* 291 */         return this; 
/* 292 */       if (paramBoolean && haveValidKeys())
/* 293 */         throw new IllegalBlockingModeException(); 
/* 294 */       implConfigureBlocking(paramBoolean);
/* 295 */       this.blocking = paramBoolean;
/*     */     } 
/* 297 */     return this;
/*     */   }
/*     */   
/*     */   protected abstract void implConfigureBlocking(boolean paramBoolean) throws IOException;
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\nio\channels\spi\AbstractSelectableChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */