/*     */ package com.sun.jmx.snmp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BerDecoder
/*     */ {
/*     */   public static final int BooleanTag = 1;
/*     */   public static final int IntegerTag = 2;
/*     */   public static final int OctetStringTag = 4;
/*     */   public static final int NullTag = 5;
/*     */   public static final int OidTag = 6;
/*     */   public static final int SequenceTag = 48;
/*     */   private final byte[] bytes;
/*     */   private int next;
/*     */   private final int[] stackBuf;
/*     */   private int stackTop;
/*     */   
/*     */   public BerDecoder(byte[] paramArrayOfbyte) {
/* 746 */     this.next = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 754 */     this.stackBuf = new int[200];
/* 755 */     this.stackTop = 0;
/*     */     this.bytes = paramArrayOfbyte;
/*     */     reset();
/*     */   }
/*     */   
/*     */   public void reset() {
/*     */     this.next = 0;
/*     */     this.stackTop = 0;
/*     */   }
/*     */   
/*     */   public int fetchInteger() throws BerException {
/*     */     return fetchInteger(2);
/*     */   }
/*     */   
/*     */   public int fetchInteger(int paramInt) throws BerException {
/*     */     int i = 0;
/*     */     int j = this.next;
/*     */     try {
/*     */       if (fetchTag() != paramInt)
/*     */         throw new BerException(); 
/*     */       i = fetchIntegerValue();
/*     */     } catch (BerException berException) {
/*     */       this.next = j;
/*     */       throw berException;
/*     */     } 
/*     */     return i;
/*     */   }
/*     */   
/*     */   public long fetchIntegerAsLong() throws BerException {
/*     */     return fetchIntegerAsLong(2);
/*     */   }
/*     */   
/*     */   public long fetchIntegerAsLong(int paramInt) throws BerException {
/*     */     long l = 0L;
/*     */     int i = this.next;
/*     */     try {
/*     */       if (fetchTag() != paramInt)
/*     */         throw new BerException(); 
/*     */       l = fetchIntegerValueAsLong();
/*     */     } catch (BerException berException) {
/*     */       this.next = i;
/*     */       throw berException;
/*     */     } 
/*     */     return l;
/*     */   }
/*     */   
/*     */   public byte[] fetchOctetString() throws BerException {
/*     */     return fetchOctetString(4);
/*     */   }
/*     */   
/*     */   public byte[] fetchOctetString(int paramInt) throws BerException {
/*     */     byte[] arrayOfByte = null;
/*     */     int i = this.next;
/*     */     try {
/*     */       if (fetchTag() != paramInt)
/*     */         throw new BerException(); 
/*     */       arrayOfByte = fetchStringValue();
/*     */     } catch (BerException berException) {
/*     */       this.next = i;
/*     */       throw berException;
/*     */     } 
/*     */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public long[] fetchOid() throws BerException {
/*     */     return fetchOid(6);
/*     */   }
/*     */   
/*     */   public long[] fetchOid(int paramInt) throws BerException {
/*     */     long[] arrayOfLong = null;
/*     */     int i = this.next;
/*     */     try {
/*     */       if (fetchTag() != paramInt)
/*     */         throw new BerException(); 
/*     */       arrayOfLong = fetchOidValue();
/*     */     } catch (BerException berException) {
/*     */       this.next = i;
/*     */       throw berException;
/*     */     } 
/*     */     return arrayOfLong;
/*     */   }
/*     */   
/*     */   public void fetchNull() throws BerException {
/*     */     fetchNull(5);
/*     */   }
/*     */   
/*     */   public void fetchNull(int paramInt) throws BerException {
/*     */     int i = this.next;
/*     */     try {
/*     */       if (fetchTag() != paramInt)
/*     */         throw new BerException(); 
/*     */       int j = fetchLength();
/*     */       if (j != 0)
/*     */         throw new BerException(); 
/*     */     } catch (BerException berException) {
/*     */       this.next = i;
/*     */       throw berException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] fetchAny() throws BerException {
/*     */     byte[] arrayOfByte = null;
/*     */     int i = this.next;
/*     */     try {
/*     */       int j = fetchTag();
/*     */       int k = fetchLength();
/*     */       if (k < 0)
/*     */         throw new BerException(); 
/*     */       int m = this.next + k - i;
/*     */       if (k > this.bytes.length - this.next)
/*     */         throw new IndexOutOfBoundsException("Decoded length exceeds buffer"); 
/*     */       byte[] arrayOfByte1 = new byte[m];
/*     */       System.arraycopy(this.bytes, i, arrayOfByte1, 0, m);
/*     */       this.next += k;
/*     */       arrayOfByte = arrayOfByte1;
/*     */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*     */       this.next = i;
/*     */       throw new BerException();
/*     */     } 
/*     */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public byte[] fetchAny(int paramInt) throws BerException {
/*     */     if (getTag() != paramInt)
/*     */       throw new BerException(); 
/*     */     return fetchAny();
/*     */   }
/*     */   
/*     */   public void openSequence() throws BerException {
/*     */     openSequence(48);
/*     */   }
/*     */   
/*     */   public void openSequence(int paramInt) throws BerException {
/*     */     int i = this.next;
/*     */     try {
/*     */       if (fetchTag() != paramInt)
/*     */         throw new BerException(); 
/*     */       int j = fetchLength();
/*     */       if (j < 0)
/*     */         throw new BerException(); 
/*     */       if (j > this.bytes.length - this.next)
/*     */         throw new BerException(); 
/*     */       this.stackBuf[this.stackTop++] = this.next + j;
/*     */     } catch (BerException berException) {
/*     */       this.next = i;
/*     */       throw berException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void closeSequence() throws BerException {
/*     */     if (this.stackBuf[this.stackTop - 1] == this.next) {
/*     */       this.stackTop--;
/*     */     } else {
/*     */       throw new BerException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean cannotCloseSequence() {
/*     */     return (this.next < this.stackBuf[this.stackTop - 1]);
/*     */   }
/*     */   
/*     */   public int getTag() throws BerException {
/*     */     int i = 0;
/*     */     int j = this.next;
/*     */     try {
/*     */       i = fetchTag();
/*     */     } finally {
/*     */       this.next = j;
/*     */     } 
/*     */     return i;
/*     */   }
/*     */   
/*     */   public String toString() {
/*     */     StringBuffer stringBuffer = new StringBuffer(this.bytes.length * 2);
/*     */     for (byte b = 0; b < this.bytes.length; b++) {
/*     */       byte b1 = (this.bytes[b] > 0) ? this.bytes[b] : (this.bytes[b] + 256);
/*     */       if (b == this.next)
/*     */         stringBuffer.append("("); 
/*     */       stringBuffer.append(Character.forDigit(b1 / 16, 16));
/*     */       stringBuffer.append(Character.forDigit(b1 % 16, 16));
/*     */       if (b == this.next)
/*     */         stringBuffer.append(")"); 
/*     */     } 
/*     */     if (this.bytes.length == this.next)
/*     */       stringBuffer.append("()"); 
/*     */     return new String(stringBuffer);
/*     */   }
/*     */   
/*     */   private final int fetchTag() throws BerException {
/*     */     int i = 0;
/*     */     int j = this.next;
/*     */     try {
/*     */       byte b = this.bytes[this.next++];
/*     */       i = (b >= 0) ? b : (b + 256);
/*     */       if ((i & 0x1F) == 31)
/*     */         while ((this.bytes[this.next] & 0x80) != 0) {
/*     */           i <<= 7;
/*     */           i |= this.bytes[this.next++] & Byte.MAX_VALUE;
/*     */         }  
/*     */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*     */       this.next = j;
/*     */       throw new BerException();
/*     */     } 
/*     */     return i;
/*     */   }
/*     */   
/*     */   private final int fetchLength() throws BerException {
/*     */     int i = 0;
/*     */     int j = this.next;
/*     */     try {
/*     */       byte b = this.bytes[this.next++];
/*     */       if (b >= 0) {
/*     */         i = b;
/*     */       } else {
/*     */         for (int k = 128 + b; k > 0; k--) {
/*     */           byte b1 = this.bytes[this.next++];
/*     */           i <<= 8;
/*     */           i |= (b1 >= 0) ? b1 : (b1 + 256);
/*     */         } 
/*     */       } 
/*     */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*     */       this.next = j;
/*     */       throw new BerException();
/*     */     } 
/*     */     return i;
/*     */   }
/*     */   
/*     */   private int fetchIntegerValue() throws BerException {
/*     */     int i = 0;
/*     */     int j = this.next;
/*     */     try {
/*     */       int k = fetchLength();
/*     */       if (k <= 0)
/*     */         throw new BerException(); 
/*     */       if (k > this.bytes.length - this.next)
/*     */         throw new IndexOutOfBoundsException("Decoded length exceeds buffer"); 
/*     */       int m = this.next + k;
/*     */       i = this.bytes[this.next++];
/*     */       while (this.next < m) {
/*     */         byte b = this.bytes[this.next++];
/*     */         if (b < 0) {
/*     */           i = i << 8 | 256 + b;
/*     */           continue;
/*     */         } 
/*     */         i = i << 8 | b;
/*     */       } 
/*     */     } catch (BerException berException) {
/*     */       this.next = j;
/*     */       throw berException;
/*     */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*     */       this.next = j;
/*     */       throw new BerException();
/*     */     } catch (ArithmeticException arithmeticException) {
/*     */       this.next = j;
/*     */       throw new BerException();
/*     */     } 
/*     */     return i;
/*     */   }
/*     */   
/*     */   private final long fetchIntegerValueAsLong() throws BerException {
/*     */     long l = 0L;
/*     */     int i = this.next;
/*     */     try {
/*     */       int j = fetchLength();
/*     */       if (j <= 0)
/*     */         throw new BerException(); 
/*     */       if (j > this.bytes.length - this.next)
/*     */         throw new IndexOutOfBoundsException("Decoded length exceeds buffer"); 
/*     */       int k = this.next + j;
/*     */       l = this.bytes[this.next++];
/*     */       while (this.next < k) {
/*     */         byte b = this.bytes[this.next++];
/*     */         if (b < 0) {
/*     */           l = l << 8L | (256 + b);
/*     */           continue;
/*     */         } 
/*     */         l = l << 8L | b;
/*     */       } 
/*     */     } catch (BerException berException) {
/*     */       this.next = i;
/*     */       throw berException;
/*     */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*     */       this.next = i;
/*     */       throw new BerException();
/*     */     } catch (ArithmeticException arithmeticException) {
/*     */       this.next = i;
/*     */       throw new BerException();
/*     */     } 
/*     */     return l;
/*     */   }
/*     */   
/*     */   private byte[] fetchStringValue() throws BerException {
/*     */     byte[] arrayOfByte = null;
/*     */     int i = this.next;
/*     */     try {
/*     */       int j = fetchLength();
/*     */       if (j < 0)
/*     */         throw new BerException(); 
/*     */       if (j > this.bytes.length - this.next)
/*     */         throw new IndexOutOfBoundsException("Decoded length exceeds buffer"); 
/*     */       byte[] arrayOfByte1 = new byte[j];
/*     */       System.arraycopy(this.bytes, this.next, arrayOfByte1, 0, j);
/*     */       this.next += j;
/*     */       arrayOfByte = arrayOfByte1;
/*     */     } catch (BerException berException) {
/*     */       this.next = i;
/*     */       throw berException;
/*     */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*     */       this.next = i;
/*     */       throw new BerException();
/*     */     } catch (ArithmeticException arithmeticException) {
/*     */       this.next = i;
/*     */       throw new BerException();
/*     */     } 
/*     */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   private final long[] fetchOidValue() throws BerException {
/*     */     long[] arrayOfLong = null;
/*     */     int i = this.next;
/*     */     try {
/*     */       int j = fetchLength();
/*     */       if (j <= 0)
/*     */         throw new BerException(); 
/*     */       if (j > this.bytes.length - this.next)
/*     */         throw new IndexOutOfBoundsException("Decoded length exceeds buffer"); 
/*     */       byte b1 = 2;
/*     */       byte b2;
/*     */       for (b2 = 1; b2 < j; b2++) {
/*     */         if ((this.bytes[this.next + b2] & 0x80) == 0)
/*     */           b1++; 
/*     */       } 
/*     */       b2 = b1;
/*     */       long[] arrayOfLong1 = new long[b2];
/*     */       byte b = this.bytes[this.next++];
/*     */       if (b < 0)
/*     */         throw new BerException(); 
/*     */       long l1 = (b / 40);
/*     */       if (l1 > 2L)
/*     */         throw new BerException(); 
/*     */       long l2 = (b % 40);
/*     */       arrayOfLong1[0] = l1;
/*     */       arrayOfLong1[1] = l2;
/*     */       byte b3 = 2;
/*     */       while (b3 < b2) {
/*     */         long l = 0L;
/*     */         byte b4 = this.bytes[this.next++];
/*     */         while ((b4 & 0x80) != 0) {
/*     */           l = l << 7L | (b4 & Byte.MAX_VALUE);
/*     */           if (l < 0L)
/*     */             throw new BerException(); 
/*     */           b4 = this.bytes[this.next++];
/*     */         } 
/*     */         l = l << 7L | b4;
/*     */         if (l < 0L)
/*     */           throw new BerException(); 
/*     */         arrayOfLong1[b3++] = l;
/*     */       } 
/*     */       arrayOfLong = arrayOfLong1;
/*     */     } catch (BerException berException) {
/*     */       this.next = i;
/*     */       throw berException;
/*     */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*     */       this.next = i;
/*     */       throw new BerException();
/*     */     } 
/*     */     return arrayOfLong;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\BerDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */