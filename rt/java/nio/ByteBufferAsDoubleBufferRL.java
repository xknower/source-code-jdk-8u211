/*     */ package java.nio;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ByteBufferAsDoubleBufferRL
/*     */   extends ByteBufferAsDoubleBufferL
/*     */ {
/*     */   ByteBufferAsDoubleBufferRL(ByteBuffer paramByteBuffer) {
/*  55 */     super(paramByteBuffer);
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
/*     */   ByteBufferAsDoubleBufferRL(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  68 */     super(paramByteBuffer, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*     */   }
/*     */ 
/*     */   
/*     */   public DoubleBuffer slice() {
/*  73 */     int i = position();
/*  74 */     int j = limit();
/*  75 */     assert i <= j;
/*  76 */     boolean bool = (i <= j) ? (j - i) : false;
/*  77 */     int k = (i << 3) + this.offset;
/*  78 */     assert k >= 0;
/*  79 */     return new ByteBufferAsDoubleBufferRL(this.bb, -1, 0, bool, bool, k);
/*     */   }
/*     */   
/*     */   public DoubleBuffer duplicate() {
/*  83 */     return new ByteBufferAsDoubleBufferRL(this.bb, 
/*  84 */         markValue(), 
/*  85 */         position(), 
/*  86 */         limit(), 
/*  87 */         capacity(), this.offset);
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
/*     */   public DoubleBuffer asReadOnlyBuffer() {
/* 100 */     return duplicate();
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
/*     */   public DoubleBuffer put(double paramDouble) {
/* 131 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBuffer put(int paramInt, double paramDouble) {
/* 140 */     throw new ReadOnlyBufferException();
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
/*     */   public DoubleBuffer compact() {
/* 162 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/* 167 */     return this.bb.isDirect();
/*     */   }
/*     */   
/*     */   public boolean isReadOnly() {
/* 171 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOrder order() {
/* 221 */     return ByteOrder.LITTLE_ENDIAN;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\nio\ByteBufferAsDoubleBufferRL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */