/*    */ package com.sun.org.apache.bcel.internal.generic;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ACONST_NULL
/*    */   extends Instruction
/*    */   implements PushInstruction, TypedInstruction
/*    */ {
/*    */   public ACONST_NULL() {
/* 73 */     super((short)1, (short)1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Type getType(ConstantPoolGen cp) {
/* 79 */     return Type.NULL;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void accept(Visitor v) {
/* 92 */     v.visitStackProducer(this);
/* 93 */     v.visitPushInstruction(this);
/* 94 */     v.visitTypedInstruction(this);
/* 95 */     v.visitACONST_NULL(this);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\internal\generic\ACONST_NULL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */