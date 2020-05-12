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
/*     */ public class ICONST
/*     */   extends Instruction
/*     */   implements ConstantPushInstruction, TypedInstruction
/*     */ {
/*     */   private int value;
/*     */   
/*     */   ICONST() {}
/*     */   
/*     */   public ICONST(int i) {
/*  79 */     super((short)3, (short)1);
/*     */     
/*  81 */     if (i >= -1 && i <= 5) {
/*  82 */       this.opcode = (short)(3 + i);
/*     */     } else {
/*  84 */       throw new ClassGenException("ICONST can be used only for value between -1 and 5: " + i);
/*     */     } 
/*  86 */     this.value = i;
/*     */   }
/*     */   public Number getValue() {
/*  89 */     return new Integer(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Type getType(ConstantPoolGen cp) {
/*  94 */     return Type.INT;
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
/* 106 */     v.visitPushInstruction(this);
/* 107 */     v.visitStackProducer(this);
/* 108 */     v.visitTypedInstruction(this);
/* 109 */     v.visitConstantPushInstruction(this);
/* 110 */     v.visitICONST(this);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\internal\generic\ICONST.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */