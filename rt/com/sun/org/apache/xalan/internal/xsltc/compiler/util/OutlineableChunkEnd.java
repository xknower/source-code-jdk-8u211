/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler.util;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.Instruction;
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
/*    */ class OutlineableChunkEnd
/*    */   extends MarkerInstruction
/*    */ {
/* 36 */   public static final Instruction OUTLINEABLECHUNKEND = new OutlineableChunkEnd();
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
/*    */   public String getName() {
/* 52 */     return OutlineableChunkEnd.class.getName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 60 */     return getName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString(boolean verbose) {
/* 68 */     return getName();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compile\\util\OutlineableChunkEnd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */