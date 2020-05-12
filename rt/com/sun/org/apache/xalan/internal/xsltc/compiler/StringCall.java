/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
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
/*    */ final class StringCall
/*    */   extends FunctionCall
/*    */ {
/*    */   public StringCall(QName fname, Vector arguments) {
/* 41 */     super(fname, arguments);
/*    */   }
/*    */   
/*    */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 45 */     int argc = argumentCount();
/* 46 */     if (argc > 1) {
/* 47 */       ErrorMsg err = new ErrorMsg("ILLEGAL_ARG_ERR", this);
/* 48 */       throw new TypeCheckError(err);
/*    */     } 
/*    */     
/* 51 */     if (argc > 0) {
/* 52 */       argument().typeCheck(stable);
/*    */     }
/* 54 */     return this._type = Type.String;
/*    */   }
/*    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/*    */     Type targ;
/* 58 */     InstructionList il = methodGen.getInstructionList();
/*    */ 
/*    */     
/* 61 */     if (argumentCount() == 0) {
/* 62 */       il.append(methodGen.loadContextNode());
/* 63 */       targ = Type.Node;
/*    */     } else {
/*    */       
/* 66 */       Expression arg = argument();
/* 67 */       arg.translate(classGen, methodGen);
/* 68 */       arg.startIterator(classGen, methodGen);
/* 69 */       targ = arg.getType();
/*    */     } 
/*    */     
/* 72 */     if (!targ.identicalTo(Type.String))
/* 73 */       targ.translateTo(classGen, methodGen, Type.String); 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compiler\StringCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */