/*     */ package sun.reflect;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UnsafeStaticBooleanFieldAccessorImpl
/*     */   extends UnsafeStaticFieldAccessorImpl
/*     */ {
/*     */   UnsafeStaticBooleanFieldAccessorImpl(Field paramField) {
/*  32 */     super(paramField);
/*     */   }
/*     */   
/*     */   public Object get(Object paramObject) throws IllegalArgumentException {
/*  36 */     return new Boolean(getBoolean(paramObject));
/*     */   }
/*     */   
/*     */   public boolean getBoolean(Object paramObject) throws IllegalArgumentException {
/*  40 */     return unsafe.getBoolean(this.base, this.fieldOffset);
/*     */   }
/*     */   
/*     */   public byte getByte(Object paramObject) throws IllegalArgumentException {
/*  44 */     throw newGetByteIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public char getChar(Object paramObject) throws IllegalArgumentException {
/*  48 */     throw newGetCharIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public short getShort(Object paramObject) throws IllegalArgumentException {
/*  52 */     throw newGetShortIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public int getInt(Object paramObject) throws IllegalArgumentException {
/*  56 */     throw newGetIntIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public long getLong(Object paramObject) throws IllegalArgumentException {
/*  60 */     throw newGetLongIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public float getFloat(Object paramObject) throws IllegalArgumentException {
/*  64 */     throw newGetFloatIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public double getDouble(Object paramObject) throws IllegalArgumentException {
/*  68 */     throw newGetDoubleIllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(Object paramObject1, Object paramObject2) throws IllegalArgumentException, IllegalAccessException {
/*  74 */     if (this.isFinal) {
/*  75 */       throwFinalFieldIllegalAccessException(paramObject2);
/*     */     }
/*  77 */     if (paramObject2 == null) {
/*  78 */       throwSetIllegalArgumentException(paramObject2);
/*     */     }
/*  80 */     if (paramObject2 instanceof Boolean) {
/*  81 */       unsafe.putBoolean(this.base, this.fieldOffset, ((Boolean)paramObject2).booleanValue());
/*     */       return;
/*     */     } 
/*  84 */     throwSetIllegalArgumentException(paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBoolean(Object paramObject, boolean paramBoolean) throws IllegalArgumentException, IllegalAccessException {
/*  90 */     if (this.isFinal) {
/*  91 */       throwFinalFieldIllegalAccessException(paramBoolean);
/*     */     }
/*  93 */     unsafe.putBoolean(this.base, this.fieldOffset, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByte(Object paramObject, byte paramByte) throws IllegalArgumentException, IllegalAccessException {
/*  99 */     throwSetIllegalArgumentException(paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChar(Object paramObject, char paramChar) throws IllegalArgumentException, IllegalAccessException {
/* 105 */     throwSetIllegalArgumentException(paramChar);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShort(Object paramObject, short paramShort) throws IllegalArgumentException, IllegalAccessException {
/* 111 */     throwSetIllegalArgumentException(paramShort);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInt(Object paramObject, int paramInt) throws IllegalArgumentException, IllegalAccessException {
/* 117 */     throwSetIllegalArgumentException(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLong(Object paramObject, long paramLong) throws IllegalArgumentException, IllegalAccessException {
/* 123 */     throwSetIllegalArgumentException(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFloat(Object paramObject, float paramFloat) throws IllegalArgumentException, IllegalAccessException {
/* 129 */     throwSetIllegalArgumentException(paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDouble(Object paramObject, double paramDouble) throws IllegalArgumentException, IllegalAccessException {
/* 135 */     throwSetIllegalArgumentException(paramDouble);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\UnsafeStaticBooleanFieldAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */