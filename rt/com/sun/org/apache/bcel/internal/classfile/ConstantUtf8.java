/*     */ package com.sun.org.apache.bcel.internal.classfile;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ConstantUtf8
/*     */   extends Constant
/*     */ {
/*     */   private String bytes;
/*     */   
/*     */   public ConstantUtf8(ConstantUtf8 c) {
/*  79 */     this(c.getBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ConstantUtf8(DataInputStream file) throws IOException {
/*  90 */     super((byte)1);
/*     */     
/*  92 */     this.bytes = file.readUTF();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConstantUtf8(String bytes) {
/* 100 */     super((byte)1);
/*     */     
/* 102 */     if (bytes == null) {
/* 103 */       throw new IllegalArgumentException("bytes must not be null!");
/*     */     }
/* 105 */     this.bytes = bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(Visitor v) {
/* 116 */     v.visitConstantUtf8(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void dump(DataOutputStream file) throws IOException {
/* 127 */     file.writeByte(this.tag);
/* 128 */     file.writeUTF(this.bytes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getBytes() {
/* 134 */     return this.bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setBytes(String bytes) {
/* 140 */     this.bytes = bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 148 */     return super.toString() + "(\"" + Utility.replace(this.bytes, "\n", "\\n") + "\")";
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\internal\classfile\ConstantUtf8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */