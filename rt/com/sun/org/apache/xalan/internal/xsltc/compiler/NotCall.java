/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.BranchHandle;
/*    */ import com.sun.org.apache.bcel.internal.generic.GOTO;
/*    */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*    */ import java.util.Vector;
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
/*    */ final class NotCall
/*    */   extends FunctionCall
/*    */ {
/*    */   public NotCall(QName fname, Vector arguments) {
/* 40 */     super(fname, arguments);
/*    */   }
/*    */   
/*    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 44 */     InstructionList il = methodGen.getInstructionList();
/* 45 */     argument().translate(classGen, methodGen);
/* 46 */     il.append(ICONST_1);
/* 47 */     il.append(IXOR);
/*    */   }
/*    */ 
/*    */   
/*    */   public void translateDesynthesized(ClassGenerator classGen, MethodGenerator methodGen) {
/* 52 */     InstructionList il = methodGen.getInstructionList();
/* 53 */     Expression exp = argument();
/* 54 */     exp.translateDesynthesized(classGen, methodGen);
/* 55 */     BranchHandle gotoh = il.append(new GOTO(null));
/* 56 */     this._trueList = exp._falseList;
/* 57 */     this._falseList = exp._trueList;
/* 58 */     this._falseList.add(gotoh);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compiler\NotCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */