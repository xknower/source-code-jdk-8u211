/*     */ package sun.text.bidi;
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
/*     */ public class BidiRun
/*     */ {
/*     */   int start;
/*     */   int limit;
/*     */   int insertRemove;
/*     */   byte level;
/*     */   
/*     */   BidiRun() {
/*  82 */     this(0, 0, (byte)0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BidiRun(int paramInt1, int paramInt2, byte paramByte) {
/*  90 */     this.start = paramInt1;
/*  91 */     this.limit = paramInt2;
/*  92 */     this.level = paramByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void copyFrom(BidiRun paramBidiRun) {
/* 100 */     this.start = paramBidiRun.start;
/* 101 */     this.limit = paramBidiRun.limit;
/* 102 */     this.level = paramBidiRun.level;
/* 103 */     this.insertRemove = paramBidiRun.insertRemove;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getEmbeddingLevel() {
/* 111 */     return this.level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isEvenRun() {
/* 121 */     return ((this.level & 0x1) == 0);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\text\bidi\BidiRun.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */