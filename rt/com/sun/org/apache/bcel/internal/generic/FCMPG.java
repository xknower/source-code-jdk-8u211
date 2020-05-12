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
/*    */ public class FCMPG
/*    */   extends Instruction
/*    */   implements TypedInstruction, StackProducer, StackConsumer
/*    */ {
/*    */   public FCMPG() {
/* 70 */     super((short)150, (short)1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Type getType(ConstantPoolGen cp) {
/* 76 */     return Type.FLOAT;
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
/* 89 */     v.visitTypedInstruction(this);
/* 90 */     v.visitStackProducer(this);
/* 91 */     v.visitStackConsumer(this);
/* 92 */     v.visitFCMPG(this);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\internal\generic\FCMPG.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */