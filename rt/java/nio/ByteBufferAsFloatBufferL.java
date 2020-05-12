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
/*     */ class ByteBufferAsFloatBufferL
/*     */   extends FloatBuffer
/*     */ {
/*     */   protected final ByteBuffer bb;
/*     */   protected final int offset;
/*     */   
/*     */   ByteBufferAsFloatBufferL(ByteBuffer paramByteBuffer) {
/*  44 */     super(-1, 0, paramByteBuffer
/*  45 */         .remaining() >> 2, paramByteBuffer
/*  46 */         .remaining() >> 2);
/*  47 */     this.bb = paramByteBuffer;
/*     */     
/*  49 */     int i = capacity();
/*  50 */     limit(i);
/*  51 */     int j = position();
/*  52 */     assert j <= i;
/*  53 */     this.offset = j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ByteBufferAsFloatBufferL(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  64 */     super(paramInt1, paramInt2, paramInt3, paramInt4);
/*  65 */     this.bb = paramByteBuffer;
/*  66 */     this.offset = paramInt5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatBuffer slice() {
/*  73 */     int i = position();
/*  74 */     int j = limit();
/*  75 */     assert i <= j;
/*  76 */     boolean bool = (i <= j) ? (j - i) : false;
/*  77 */     int k = (i << 2) + this.offset;
/*  78 */     assert k >= 0;
/*  79 */     return new ByteBufferAsFloatBufferL(this.bb, -1, 0, bool, bool, k);
/*     */   }
/*     */   
/*     */   public FloatBuffer duplicate() {
/*  83 */     return new ByteBufferAsFloatBufferL(this.bb, 
/*  84 */         markValue(), 
/*  85 */         position(), 
/*  86 */         limit(), 
/*  87 */         capacity(), this.offset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatBuffer asReadOnlyBuffer() {
/*  93 */     return new ByteBufferAsFloatBufferRL(this.bb, 
/*  94 */         markValue(), 
/*  95 */         position(), 
/*  96 */         limit(), 
/*  97 */         capacity(), this.offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int ix(int paramInt) {
/* 107 */     return (paramInt << 2) + this.offset;
/*     */   }
/*     */   
/*     */   public float get() {
/* 111 */     return Bits.getFloatL(this.bb, ix(nextGetIndex()));
/*     */   }
/*     */   
/*     */   public float get(int paramInt) {
/* 115 */     return Bits.getFloatL(this.bb, ix(checkIndex(paramInt)));
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
/*     */   public FloatBuffer put(float paramFloat) {
/* 128 */     Bits.putFloatL(this.bb, ix(nextPutIndex()), paramFloat);
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatBuffer put(int paramInt, float paramFloat) {
/* 137 */     Bits.putFloatL(this.bb, ix(checkIndex(paramInt)), paramFloat);
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatBuffer compact() {
/* 146 */     int i = position();
/* 147 */     int j = limit();
/* 148 */     assert i <= j;
/* 149 */     boolean bool = (i <= j) ? (j - i) : false;
/*     */     
/* 151 */     ByteBuffer byteBuffer1 = this.bb.duplicate();
/* 152 */     byteBuffer1.limit(ix(j));
/* 153 */     byteBuffer1.position(ix(0));
/* 154 */     ByteBuffer byteBuffer2 = byteBuffer1.slice();
/* 155 */     byteBuffer2.position(i << 2);
/* 156 */     byteBuffer2.compact();
/* 157 */     position(bool);
/* 158 */     limit(capacity());
/* 159 */     discardMark();
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/* 167 */     return this.bb.isDirect();
/*     */   }
/*     */   
/*     */   public boolean isReadOnly() {
/* 171 */     return false;
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


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\nio\ByteBufferAsFloatBufferL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */