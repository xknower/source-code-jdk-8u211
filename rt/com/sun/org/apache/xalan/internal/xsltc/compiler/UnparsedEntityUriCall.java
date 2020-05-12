/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*    */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*    */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
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
/*    */ 
/*    */ final class UnparsedEntityUriCall
/*    */   extends FunctionCall
/*    */ {
/*    */   private Expression _entity;
/*    */   
/*    */   public UnparsedEntityUriCall(QName fname, Vector arguments) {
/* 46 */     super(fname, arguments);
/* 47 */     this._entity = argument();
/*    */   }
/*    */   
/*    */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 51 */     Type entity = this._entity.typeCheck(stable);
/* 52 */     if (!(entity instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringType)) {
/* 53 */       this._entity = new CastExpr(this._entity, Type.String);
/*    */     }
/* 55 */     return this._type = Type.String;
/*    */   }
/*    */   
/*    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 59 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 60 */     InstructionList il = methodGen.getInstructionList();
/*    */     
/* 62 */     il.append(methodGen.loadDOM());
/*    */     
/* 64 */     this._entity.translate(classGen, methodGen);
/*    */     
/* 66 */     il.append(new INVOKEINTERFACE(cpg
/* 67 */           .addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getUnparsedEntityURI", "(Ljava/lang/String;)Ljava/lang/String;"), 2));
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compiler\UnparsedEntityUriCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */