/*    */ package com.sun.org.apache.regexp.internal;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CharacterArrayCharacterIterator
/*    */   implements CharacterIterator
/*    */ {
/*    */   private final char[] src;
/*    */   private final int off;
/*    */   private final int len;
/*    */   
/*    */   public CharacterArrayCharacterIterator(char[] src, int off, int len) {
/* 40 */     this.src = src;
/* 41 */     this.off = off;
/* 42 */     this.len = len;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String substring(int beginIndex, int endIndex) {
/* 48 */     if (endIndex > this.len) {
/* 49 */       throw new IndexOutOfBoundsException("endIndex=" + endIndex + "; sequence size=" + this.len);
/*    */     }
/*    */     
/* 52 */     if (beginIndex < 0 || beginIndex > endIndex) {
/* 53 */       throw new IndexOutOfBoundsException("beginIndex=" + beginIndex + "; endIndex=" + endIndex);
/*    */     }
/*    */     
/* 56 */     return new String(this.src, this.off + beginIndex, endIndex - beginIndex);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String substring(int beginIndex) {
/* 62 */     return substring(beginIndex, this.len);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public char charAt(int pos) {
/* 68 */     return this.src[this.off + pos];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEnd(int pos) {
/* 74 */     return (pos >= this.len);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\regexp\internal\CharacterArrayCharacterIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */