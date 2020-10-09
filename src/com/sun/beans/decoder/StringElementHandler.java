/*     */ package com.sun.beans.decoder;
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
/*     */ 
/*     */ public class StringElementHandler
/*     */   extends ElementHandler
/*     */ {
/*  54 */   private StringBuilder sb = new StringBuilder();
/*  55 */   private ValueObject value = ValueObjectImpl.NULL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void addCharacter(char paramChar) {
/*  64 */     if (this.sb == null) {
/*  65 */       throw new IllegalStateException("Could not add chararcter to evaluated string element");
/*     */     }
/*  67 */     this.sb.append(paramChar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void addArgument(Object paramObject) {
/*  77 */     if (this.sb == null) {
/*  78 */       throw new IllegalStateException("Could not add argument to evaluated string element");
/*     */     }
/*  80 */     this.sb.append(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ValueObject getValueObject() {
/*  90 */     if (this.sb != null) {
/*     */       try {
/*  92 */         this.value = ValueObjectImpl.create(getValue(this.sb.toString()));
/*     */       }
/*  94 */       catch (RuntimeException runtimeException) {
/*  95 */         getOwner().handleException(runtimeException);
/*     */       } finally {
/*     */         
/*  98 */         this.sb = null;
/*     */       } 
/*     */     }
/* 101 */     return this.value;
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
/*     */   protected Object getValue(String paramString) {
/* 114 */     return paramString;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\beans\decoder\StringElementHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */