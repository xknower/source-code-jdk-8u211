/*     */ package com.sun.org.apache.bcel.internal.classfile;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Unknown
/*     */   extends Attribute
/*     */ {
/*     */   private byte[] bytes;
/*     */   private String name;
/*  84 */   private static HashMap unknown_attributes = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   static Unknown[] getUnknownAttributes() {
/*  89 */     Unknown[] unknowns = new Unknown[unknown_attributes.size()];
/*  90 */     Iterator<Unknown> entries = unknown_attributes.values().iterator();
/*     */     
/*  92 */     for (int i = 0; entries.hasNext(); i++) {
/*  93 */       unknowns[i] = entries.next();
/*     */     }
/*  95 */     unknown_attributes.clear();
/*  96 */     return unknowns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Unknown(Unknown c) {
/* 104 */     this(c.getNameIndex(), c.getLength(), c.getBytes(), c.getConstantPool());
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
/*     */   public Unknown(int name_index, int length, byte[] bytes, ConstantPool constant_pool) {
/* 118 */     super((byte)-1, name_index, length, constant_pool);
/* 119 */     this.bytes = bytes;
/*     */     
/* 121 */     this
/* 122 */       .name = ((ConstantUtf8)constant_pool.getConstant(name_index, (byte)1)).getBytes();
/* 123 */     unknown_attributes.put(this.name, this);
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
/*     */   Unknown(int name_index, int length, DataInputStream file, ConstantPool constant_pool) throws IOException {
/* 138 */     this(name_index, length, (byte[])null, constant_pool);
/*     */     
/* 140 */     if (length > 0) {
/* 141 */       this.bytes = new byte[length];
/* 142 */       file.readFully(this.bytes);
/*     */     } 
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
/* 154 */     v.visitUnknown(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void dump(DataOutputStream file) throws IOException {
/* 164 */     super.dump(file);
/* 165 */     if (this.length > 0) {
/* 166 */       file.write(this.bytes, 0, this.length);
/*     */     }
/*     */   }
/*     */   
/*     */   public final byte[] getBytes() {
/* 171 */     return this.bytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getName() {
/* 176 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setBytes(byte[] bytes) {
/* 182 */     this.bytes = bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/*     */     String hex;
/* 189 */     if (this.length == 0 || this.bytes == null) {
/* 190 */       return "(Unknown attribute " + this.name + ")";
/*     */     }
/*     */     
/* 193 */     if (this.length > 10) {
/* 194 */       byte[] tmp = new byte[10];
/* 195 */       System.arraycopy(this.bytes, 0, tmp, 0, 10);
/* 196 */       hex = Utility.toHexString(tmp) + "... (truncated)";
/*     */     } else {
/*     */       
/* 199 */       hex = Utility.toHexString(this.bytes);
/*     */     } 
/* 201 */     return "(Unknown attribute " + this.name + ": " + hex + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute copy(ConstantPool constant_pool) {
/* 208 */     Unknown c = (Unknown)clone();
/*     */     
/* 210 */     if (this.bytes != null) {
/* 211 */       c.bytes = (byte[])this.bytes.clone();
/*     */     }
/* 213 */     c.constant_pool = constant_pool;
/* 214 */     return c;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\internal\classfile\Unknown.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */