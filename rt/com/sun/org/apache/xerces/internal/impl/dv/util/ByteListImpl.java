/*     */ package com.sun.org.apache.xerces.internal.impl.dv.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xs.XSException;
/*     */ import com.sun.org.apache.xerces.internal.xs.datatypes.ByteList;
/*     */ import java.util.AbstractList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteListImpl
/*     */   extends AbstractList
/*     */   implements ByteList
/*     */ {
/*     */   protected final byte[] data;
/*     */   protected String canonical;
/*     */   
/*     */   public ByteListImpl(byte[] data) {
/*  45 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/*  53 */     return this.data.length;
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
/*     */   public boolean contains(byte item) {
/*  65 */     for (int i = 0; i < this.data.length; i++) {
/*  66 */       if (this.data[i] == item) {
/*  67 */         return true;
/*     */       }
/*     */     } 
/*  70 */     return false;
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
/*     */   public byte item(int index) throws XSException {
/*  86 */     if (index < 0 || index > this.data.length - 1) {
/*  87 */       throw new XSException((short)2, null);
/*     */     }
/*  89 */     return this.data[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(int index) {
/*  97 */     if (index >= 0 && index < this.data.length) {
/*  98 */       return new Byte(this.data[index]);
/*     */     }
/* 100 */     throw new IndexOutOfBoundsException("Index: " + index);
/*     */   }
/*     */   
/*     */   public int size() {
/* 104 */     return getLength();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\impl\d\\util\ByteListImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */