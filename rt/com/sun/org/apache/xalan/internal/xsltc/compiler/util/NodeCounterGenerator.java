/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler.util;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.ALOAD;
/*    */ import com.sun.org.apache.bcel.internal.generic.Instruction;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.Stylesheet;
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
/*    */ public final class NodeCounterGenerator
/*    */   extends ClassGenerator
/*    */ {
/*    */   private Instruction _aloadTranslet;
/*    */   
/*    */   public NodeCounterGenerator(String className, String superClassName, String fileName, int accessFlags, String[] interfaces, Stylesheet stylesheet) {
/* 47 */     super(className, superClassName, fileName, accessFlags, interfaces, stylesheet);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTransletIndex(int index) {
/* 56 */     this._aloadTranslet = new ALOAD(index);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Instruction loadTranslet() {
/* 65 */     return this._aloadTranslet;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isExternal() {
/* 73 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compile\\util\NodeCounterGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */