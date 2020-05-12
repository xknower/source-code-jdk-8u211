/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DCONST
/*     */   extends Instruction
/*     */   implements ConstantPushInstruction, TypedInstruction
/*     */ {
/*     */   private double value;
/*     */   
/*     */   DCONST() {}
/*     */   
/*     */   public DCONST(double f) {
/*  79 */     super((short)14, (short)1);
/*     */     
/*  81 */     if (f == 0.0D) {
/*  82 */       this.opcode = 14;
/*  83 */     } else if (f == 1.0D) {
/*  84 */       this.opcode = 15;
/*     */     } else {
/*  86 */       throw new ClassGenException("DCONST can be used only for 0.0 and 1.0: " + f);
/*     */     } 
/*  88 */     this.value = f;
/*     */   }
/*     */   public Number getValue() {
/*  91 */     return new Double(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Type getType(ConstantPoolGen cp) {
/*  96 */     return Type.DOUBLE;
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
/* 108 */     v.visitPushInstruction(this);
/* 109 */     v.visitStackProducer(this);
/* 110 */     v.visitTypedInstruction(this);
/* 111 */     v.visitConstantPushInstruction(this);
/* 112 */     v.visitDCONST(this);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\internal\generic\DCONST.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */