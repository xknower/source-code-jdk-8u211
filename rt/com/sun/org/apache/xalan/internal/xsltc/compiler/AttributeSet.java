/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.AttributeSetMethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*     */ import com.sun.org.apache.xml.internal.utils.XML11Char;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class AttributeSet
/*     */   extends TopLevelElement
/*     */ {
/*     */   private static final String AttributeSetPrefix = "$as$";
/*     */   private QName _name;
/*     */   private UseAttributeSets _useSets;
/*     */   private AttributeSet _mergeSet;
/*     */   private String _method;
/*     */   private boolean _ignore = false;
/*     */   
/*     */   public QName getName() {
/*  61 */     return this._name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMethodName() {
/*  69 */     return this._method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignore() {
/*  79 */     this._ignore = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseContents(Parser parser) {
/*  89 */     String name = getAttribute("name");
/*     */     
/*  91 */     if (!XML11Char.isXML11ValidQName(name)) {
/*  92 */       ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
/*  93 */       parser.reportError(3, err);
/*     */     } 
/*  95 */     this._name = parser.getQNameIgnoreDefaultNs(name);
/*  96 */     if (this._name == null || this._name.equals("")) {
/*  97 */       ErrorMsg msg = new ErrorMsg("UNNAMED_ATTRIBSET_ERR", this);
/*  98 */       parser.reportError(3, msg);
/*     */     } 
/*     */ 
/*     */     
/* 102 */     String useSets = getAttribute("use-attribute-sets");
/* 103 */     if (useSets.length() > 0) {
/* 104 */       if (!Util.isValidQNames(useSets)) {
/* 105 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", useSets, this);
/* 106 */         parser.reportError(3, err);
/*     */       } 
/* 108 */       this._useSets = new UseAttributeSets(useSets, parser);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 113 */     List<SyntaxTreeNode> contents = getContents();
/* 114 */     int count = contents.size();
/* 115 */     for (int i = 0; i < count; i++) {
/* 116 */       SyntaxTreeNode child = contents.get(i);
/* 117 */       if (child instanceof XslAttribute) {
/* 118 */         parser.getSymbolTable().setCurrentNode(child);
/* 119 */         child.parseContents(parser);
/*     */       }
/* 121 */       else if (!(child instanceof Text)) {
/*     */ 
/*     */ 
/*     */         
/* 125 */         ErrorMsg msg = new ErrorMsg("ILLEGAL_CHILD_ERR", this);
/* 126 */         parser.reportError(3, msg);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 131 */     parser.getSymbolTable().setCurrentNode(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 139 */     if (this._ignore) return Type.Void;
/*     */ 
/*     */     
/* 142 */     this._mergeSet = stable.addAttributeSet(this);
/*     */     
/* 144 */     this._method = "$as$" + getXSLTC().nextAttributeSetSerial();
/*     */     
/* 146 */     if (this._useSets != null) this._useSets.typeCheck(stable); 
/* 147 */     typeCheckContents(stable);
/* 148 */     return Type.Void;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 156 */     if (this._ignore) {
/*     */       return;
/*     */     }
/* 159 */     methodGen = new AttributeSetMethodGenerator(this._method, classGen);
/*     */ 
/*     */ 
/*     */     
/* 163 */     if (this._mergeSet != null) {
/* 164 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 165 */       InstructionList instructionList = methodGen.getInstructionList();
/* 166 */       String methodName = this._mergeSet.getMethodName();
/*     */       
/* 168 */       instructionList.append(classGen.loadTranslet());
/* 169 */       instructionList.append(methodGen.loadDOM());
/* 170 */       instructionList.append(methodGen.loadIterator());
/* 171 */       instructionList.append(methodGen.loadHandler());
/* 172 */       instructionList.append(methodGen.loadCurrentNode());
/* 173 */       int method = cpg.addMethodref(classGen.getClassName(), methodName, "(Lcom/sun/org/apache/xalan/internal/xsltc/DOM;Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;Lcom/sun/org/apache/xml/internal/serializer/SerializationHandler;I)V");
/*     */       
/* 175 */       instructionList.append(new INVOKESPECIAL(method));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 180 */     if (this._useSets != null) this._useSets.translate(classGen, methodGen);
/*     */ 
/*     */     
/* 183 */     Iterator<SyntaxTreeNode> attributes = elements();
/* 184 */     while (attributes.hasNext()) {
/* 185 */       SyntaxTreeNode element = attributes.next();
/* 186 */       if (element instanceof XslAttribute) {
/* 187 */         XslAttribute attribute = (XslAttribute)element;
/* 188 */         attribute.translate(classGen, methodGen);
/*     */       } 
/*     */     } 
/* 191 */     InstructionList il = methodGen.getInstructionList();
/* 192 */     il.append(RETURN);
/*     */     
/* 194 */     classGen.addMethod(methodGen);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 198 */     StringBuffer buf = new StringBuffer("attribute-set: ");
/*     */     
/* 200 */     Iterator<SyntaxTreeNode> attributes = elements();
/* 201 */     while (attributes.hasNext()) {
/*     */       
/* 203 */       XslAttribute attribute = (XslAttribute)attributes.next();
/* 204 */       buf.append(attribute);
/*     */     } 
/* 206 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compiler\AttributeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */