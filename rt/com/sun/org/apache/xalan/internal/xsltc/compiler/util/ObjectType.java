/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler.util;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.ALOAD;
/*     */ import com.sun.org.apache.bcel.internal.generic.ASTORE;
/*     */ import com.sun.org.apache.bcel.internal.generic.BranchHandle;
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.GOTO;
/*     */ import com.sun.org.apache.bcel.internal.generic.IFNULL;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.Instruction;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.bcel.internal.generic.PUSH;
/*     */ import com.sun.org.apache.bcel.internal.generic.Type;
/*     */ import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
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
/*     */ public final class ObjectType
/*     */   extends Type
/*     */ {
/*  45 */   private String _javaClassName = "java.lang.Object";
/*  46 */   private Class _clazz = Object.class;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectType(String javaClassName) {
/*  54 */     this._javaClassName = javaClassName;
/*     */     
/*     */     try {
/*  57 */       this._clazz = ObjectFactory.findProviderClass(javaClassName, true);
/*     */     }
/*  59 */     catch (ClassNotFoundException e) {
/*  60 */       this._clazz = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ObjectType(Class clazz) {
/*  65 */     this._clazz = clazz;
/*  66 */     this._javaClassName = clazz.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  74 */     return Object.class.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/*  78 */     return obj instanceof ObjectType;
/*     */   }
/*     */   
/*     */   public String getJavaClassName() {
/*  82 */     return this._javaClassName;
/*     */   }
/*     */   
/*     */   public Class getJavaClass() {
/*  86 */     return this._clazz;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  90 */     return this._javaClassName;
/*     */   }
/*     */   
/*     */   public boolean identicalTo(Type other) {
/*  94 */     return (this == other);
/*     */   }
/*     */   
/*     */   public String toSignature() {
/*  98 */     StringBuffer result = new StringBuffer("L");
/*  99 */     result.append(this._javaClassName.replace('.', '/')).append(';');
/* 100 */     return result.toString();
/*     */   }
/*     */   
/*     */   public Type toJCType() {
/* 104 */     return Util.getJCRefType(toSignature());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type) {
/* 116 */     if (type == Type.String) {
/* 117 */       translateTo(classGen, methodGen, (StringType)type);
/*     */     }
/*     */     else {
/*     */       
/* 121 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), type.toString());
/* 122 */       classGen.getParser().reportError(2, err);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, StringType type) {
/* 134 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 135 */     InstructionList il = methodGen.getInstructionList();
/*     */     
/* 137 */     il.append(DUP);
/* 138 */     BranchHandle ifNull = il.append(new IFNULL(null));
/* 139 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref(this._javaClassName, "toString", "()Ljava/lang/String;")));
/*     */ 
/*     */     
/* 142 */     BranchHandle gotobh = il.append(new GOTO(null));
/* 143 */     ifNull.setTarget(il.append(POP));
/* 144 */     il.append(new PUSH(cpg, ""));
/* 145 */     gotobh.setTarget(il.append(NOP));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Class clazz) {
/* 155 */     if (clazz.isAssignableFrom(this._clazz)) {
/* 156 */       methodGen.getInstructionList().append(NOP);
/*     */     } else {
/*     */       
/* 159 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), clazz.getClass().toString());
/* 160 */       classGen.getParser().reportError(2, err);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen, Class clazz) {
/* 169 */     methodGen.getInstructionList().append(NOP);
/*     */   }
/*     */   
/*     */   public Instruction LOAD(int slot) {
/* 173 */     return new ALOAD(slot);
/*     */   }
/*     */   
/*     */   public Instruction STORE(int slot) {
/* 177 */     return new ASTORE(slot);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compile\\util\ObjectType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */