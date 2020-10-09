/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.nio.ByteOrder;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NativeObject
/*     */ {
/*  43 */   protected static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long allocationAddress;
/*     */ 
/*     */ 
/*     */   
/*     */   private final long address;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NativeObject(long paramLong) {
/*  58 */     this.allocationAddress = paramLong;
/*  59 */     this.address = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NativeObject(long paramLong1, long paramLong2) {
/*  67 */     this.allocationAddress = paramLong1;
/*  68 */     this.address = paramLong1 + paramLong2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected NativeObject(int paramInt, boolean paramBoolean) {
/*  74 */     if (!paramBoolean) {
/*  75 */       this.allocationAddress = unsafe.allocateMemory(paramInt);
/*  76 */       this.address = this.allocationAddress;
/*     */     } else {
/*  78 */       int i = pageSize();
/*  79 */       long l = unsafe.allocateMemory((paramInt + i));
/*  80 */       this.allocationAddress = l;
/*  81 */       this.address = l + i - (l & (i - 1));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long address() {
/*  91 */     return this.address;
/*     */   }
/*     */   
/*     */   long allocationAddress() {
/*  95 */     return this.allocationAddress;
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
/*     */   NativeObject subObject(int paramInt) {
/* 109 */     return new NativeObject(paramInt + this.address);
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
/*     */   NativeObject getObject(int paramInt) {
/* 124 */     long l = 0L;
/* 125 */     switch (addressSize()) {
/*     */       case 8:
/* 127 */         l = unsafe.getLong(paramInt + this.address);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 136 */         return new NativeObject(l);case 4: l = (unsafe.getInt(paramInt + this.address) & 0xFFFFFFFF); return new NativeObject(l);
/*     */     } 
/*     */     throw new InternalError("Address size not supported");
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
/*     */   void putObject(int paramInt, NativeObject paramNativeObject) {
/* 151 */     switch (addressSize()) {
/*     */       case 8:
/* 153 */         putLong(paramInt, paramNativeObject.address);
/*     */         return;
/*     */       case 4:
/* 156 */         putInt(paramInt, (int)(paramNativeObject.address & 0xFFFFFFFFFFFFFFFFL));
/*     */         return;
/*     */     } 
/* 159 */     throw new InternalError("Address size not supported");
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
/*     */   final byte getByte(int paramInt) {
/* 176 */     return unsafe.getByte(paramInt + this.address);
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
/*     */   final void putByte(int paramInt, byte paramByte) {
/* 190 */     unsafe.putByte(paramInt + this.address, paramByte);
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
/*     */   final short getShort(int paramInt) {
/* 203 */     return unsafe.getShort(paramInt + this.address);
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
/*     */   final void putShort(int paramInt, short paramShort) {
/* 217 */     unsafe.putShort(paramInt + this.address, paramShort);
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
/*     */   final char getChar(int paramInt) {
/* 230 */     return unsafe.getChar(paramInt + this.address);
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
/*     */   final void putChar(int paramInt, char paramChar) {
/* 244 */     unsafe.putChar(paramInt + this.address, paramChar);
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
/*     */   final int getInt(int paramInt) {
/* 257 */     return unsafe.getInt(paramInt + this.address);
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
/*     */   final void putInt(int paramInt1, int paramInt2) {
/* 271 */     unsafe.putInt(paramInt1 + this.address, paramInt2);
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
/*     */   final long getLong(int paramInt) {
/* 284 */     return unsafe.getLong(paramInt + this.address);
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
/*     */   final void putLong(int paramInt, long paramLong) {
/* 298 */     unsafe.putLong(paramInt + this.address, paramLong);
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
/*     */   final float getFloat(int paramInt) {
/* 311 */     return unsafe.getFloat(paramInt + this.address);
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
/*     */   final void putFloat(int paramInt, float paramFloat) {
/* 325 */     unsafe.putFloat(paramInt + this.address, paramFloat);
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
/*     */   final double getDouble(int paramInt) {
/* 338 */     return unsafe.getDouble(paramInt + this.address);
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
/*     */   final void putDouble(int paramInt, double paramDouble) {
/* 352 */     unsafe.putDouble(paramInt + this.address, paramDouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int addressSize() {
/* 361 */     return unsafe.addressSize();
/*     */   }
/*     */ 
/*     */   
/* 365 */   private static ByteOrder byteOrder = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteOrder byteOrder() {
/* 373 */     if (byteOrder != null)
/* 374 */       return byteOrder; 
/* 375 */     long l = unsafe.allocateMemory(8L);
/*     */     try {
/* 377 */       unsafe.putLong(l, 72623859790382856L);
/* 378 */       byte b = unsafe.getByte(l);
/* 379 */       switch (b) { case 1:
/* 380 */           byteOrder = ByteOrder.BIG_ENDIAN; break;
/* 381 */         case 8: byteOrder = ByteOrder.LITTLE_ENDIAN; break;
/*     */         default:
/*     */           assert false; break; }
/*     */     
/*     */     } finally {
/* 386 */       unsafe.freeMemory(l);
/*     */     } 
/* 388 */     return byteOrder;
/*     */   }
/*     */ 
/*     */   
/* 392 */   private static int pageSize = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int pageSize() {
/* 400 */     if (pageSize == -1)
/* 401 */       pageSize = unsafe.pageSize(); 
/* 402 */     return pageSize;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\NativeObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */