/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.Constants;
/*     */ import com.sun.org.apache.bcel.internal.ExceptionConstants;
/*     */ import com.sun.org.apache.bcel.internal.util.ByteSequence;
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
/*     */ public class NEWARRAY
/*     */   extends Instruction
/*     */   implements AllocationInstruction, ExceptionThrower, StackProducer
/*     */ {
/*     */   private byte type;
/*     */   
/*     */   NEWARRAY() {}
/*     */   
/*     */   public NEWARRAY(byte type) {
/*  81 */     super((short)188, (short)2);
/*  82 */     this.type = type;
/*     */   }
/*     */   
/*     */   public NEWARRAY(BasicType type) {
/*  86 */     this(type.getType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dump(DataOutputStream out) throws IOException {
/*  94 */     out.writeByte(this.opcode);
/*  95 */     out.writeByte(this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte getTypecode() {
/* 101 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Type getType() {
/* 107 */     return new ArrayType(BasicType.getType(this.type), 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(boolean verbose) {
/* 114 */     return super.toString(verbose) + " " + Constants.TYPE_NAMES[this.type];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException {
/* 121 */     this.type = bytes.readByte();
/* 122 */     this.length = 2;
/*     */   }
/*     */   
/*     */   public Class[] getExceptions() {
/* 126 */     return new Class[] { ExceptionConstants.NEGATIVE_ARRAY_SIZE_EXCEPTION };
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
/*     */   public void accept(Visitor v) {
/* 138 */     v.visitAllocationInstruction(this);
/* 139 */     v.visitExceptionThrower(this);
/* 140 */     v.visitStackProducer(this);
/* 141 */     v.visitNEWARRAY(this);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\internal\generic\NEWARRAY.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */