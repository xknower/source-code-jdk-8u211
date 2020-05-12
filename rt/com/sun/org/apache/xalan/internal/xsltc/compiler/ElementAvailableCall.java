/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*    */ import com.sun.org.apache.bcel.internal.generic.PUSH;
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
/*    */ 
/*    */ final class ElementAvailableCall
/*    */   extends FunctionCall
/*    */ {
/*    */   public ElementAvailableCall(QName fname, Vector arguments) {
/* 43 */     super(fname, arguments);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 50 */     if (argument() instanceof LiteralExpr) {
/* 51 */       return this._type = Type.Boolean;
/*    */     }
/* 53 */     ErrorMsg err = new ErrorMsg("NEED_LITERAL_ERR", "element-available", this);
/*    */     
/* 55 */     throw new TypeCheckError(err);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evaluateAtCompileTime() {
/* 64 */     return getResult() ? Boolean.TRUE : Boolean.FALSE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getResult() {
/*    */     try {
/* 72 */       LiteralExpr arg = (LiteralExpr)argument();
/* 73 */       String qname = arg.getValue();
/* 74 */       int index = qname.indexOf(':');
/*    */       
/* 76 */       String localName = (index > 0) ? qname.substring(index + 1) : qname;
/* 77 */       return getParser().elementSupported(arg.getNamespace(), localName);
/*    */     
/*    */     }
/* 80 */     catch (ClassCastException e) {
/* 81 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 91 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 92 */     boolean result = getResult();
/* 93 */     methodGen.getInstructionList().append(new PUSH(cpg, result));
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compiler\ElementAvailableCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */