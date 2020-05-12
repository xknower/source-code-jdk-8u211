/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*    */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*    */ import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ final class LocalNameCall
/*    */   extends NameBase
/*    */ {
/*    */   public LocalNameCall(QName fname) {
/* 44 */     super(fname);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LocalNameCall(QName fname, Vector arguments) {
/* 51 */     super(fname, arguments);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 60 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 61 */     InstructionList il = methodGen.getInstructionList();
/*    */ 
/*    */     
/* 64 */     int getNodeName = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getNodeName", "(I)Ljava/lang/String;");
/*    */ 
/*    */ 
/*    */     
/* 68 */     int getLocalName = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary", "getLocalName", "(Ljava/lang/String;)Ljava/lang/String;");
/*    */ 
/*    */ 
/*    */     
/* 72 */     super.translate(classGen, methodGen);
/* 73 */     il.append(new INVOKEINTERFACE(getNodeName, 2));
/* 74 */     il.append(new INVOKESTATIC(getLocalName));
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compiler\LocalNameCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */