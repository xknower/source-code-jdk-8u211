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
/*    */ public class DCMPL
/*    */   extends Instruction
/*    */   implements TypedInstruction, StackProducer, StackConsumer
/*    */ {
/*    */   public DCMPL() {
/* 71 */     super((short)151, (short)1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Type getType(ConstantPoolGen cp) {
/* 77 */     return Type.DOUBLE;
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
/* 90 */     v.visitTypedInstruction(this);
/* 91 */     v.visitStackProducer(this);
/* 92 */     v.visitStackConsumer(this);
/* 93 */     v.visitDCMPL(this);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\internal\generic\DCMPL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */