/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.classfile.Field;
/*     */ import com.sun.org.apache.bcel.internal.generic.ALOAD;
/*     */ import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
/*     */ import com.sun.org.apache.bcel.internal.generic.ASTORE;
/*     */ import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.GETFIELD;
/*     */ import com.sun.org.apache.bcel.internal.generic.ILOAD;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.NEW;
/*     */ import com.sun.org.apache.bcel.internal.generic.NOP;
/*     */ import com.sun.org.apache.bcel.internal.generic.PUSH;
/*     */ import com.sun.org.apache.bcel.internal.generic.PUTFIELD;
/*     */ import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;
/*     */ import com.sun.org.apache.bcel.internal.generic.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.CompareGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSortRecordFactGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSortRecordGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
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
/*     */ final class Sort
/*     */   extends Instruction
/*     */   implements Closure
/*     */ {
/*     */   private Expression _select;
/*     */   private AttributeValue _order;
/*     */   private AttributeValue _caseOrder;
/*     */   private AttributeValue _dataType;
/*     */   private String _lang;
/*  70 */   private String _className = null;
/*  71 */   private ArrayList<VariableRefBase> _closureVars = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _needsSortRecordFactory = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean inInnerClass() {
/*  81 */     return (this._className != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Closure getParentClosure() {
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInnerClassName() {
/*  96 */     return this._className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addVariable(VariableRefBase variableRef) {
/* 103 */     if (this._closureVars == null) {
/* 104 */       this._closureVars = new ArrayList<>();
/*     */     }
/*     */ 
/*     */     
/* 108 */     if (!this._closureVars.contains(variableRef)) {
/* 109 */       this._closureVars.add(variableRef);
/* 110 */       this._needsSortRecordFactory = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setInnerClassName(String className) {
/* 117 */     this._className = className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseContents(Parser parser) {
/* 125 */     SyntaxTreeNode parent = getParent();
/* 126 */     if (!(parent instanceof ApplyTemplates) && !(parent instanceof ForEach)) {
/*     */       
/* 128 */       reportError(this, parser, "STRAY_SORT_ERR", (String)null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 133 */     this._select = parser.parseExpression(this, "select", "string(.)");
/*     */ 
/*     */     
/* 136 */     String val = getAttribute("order");
/* 137 */     if (val.length() == 0) val = "ascending"; 
/* 138 */     this._order = AttributeValue.create(this, val, parser);
/*     */ 
/*     */     
/* 141 */     val = getAttribute("data-type");
/* 142 */     if (val.length() == 0) {
/*     */       try {
/* 144 */         Type type = this._select.typeCheck(parser.getSymbolTable());
/* 145 */         if (type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.IntType) {
/* 146 */           val = "number";
/*     */         } else {
/* 148 */           val = "text";
/*     */         } 
/* 150 */       } catch (TypeCheckError e) {
/* 151 */         val = "text";
/*     */       } 
/*     */     }
/* 154 */     this._dataType = AttributeValue.create(this, val, parser);
/*     */     
/* 156 */     this._lang = getAttribute("lang");
/*     */ 
/*     */ 
/*     */     
/* 160 */     val = getAttribute("case-order");
/* 161 */     this._caseOrder = AttributeValue.create(this, val, parser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 170 */     Type tselect = this._select.typeCheck(stable);
/*     */ 
/*     */ 
/*     */     
/* 174 */     if (!(tselect instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringType)) {
/* 175 */       this._select = new CastExpr(this._select, Type.String);
/*     */     }
/*     */     
/* 178 */     this._order.typeCheck(stable);
/* 179 */     this._caseOrder.typeCheck(stable);
/* 180 */     this._dataType.typeCheck(stable);
/* 181 */     return Type.Void;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateSortType(ClassGenerator classGen, MethodGenerator methodGen) {
/* 190 */     this._dataType.translate(classGen, methodGen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateSortOrder(ClassGenerator classGen, MethodGenerator methodGen) {
/* 195 */     this._order.translate(classGen, methodGen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateCaseOrder(ClassGenerator classGen, MethodGenerator methodGen) {
/* 200 */     this._caseOrder.translate(classGen, methodGen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateLang(ClassGenerator classGen, MethodGenerator methodGen) {
/* 205 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 206 */     InstructionList il = methodGen.getInstructionList();
/* 207 */     il.append(new PUSH(cpg, this._lang));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateSelect(ClassGenerator classGen, MethodGenerator methodGen) {
/* 217 */     this._select.translate(classGen, methodGen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void translateSortIterator(ClassGenerator classGen, MethodGenerator methodGen, Expression nodeSet, Vector<Sort> sortObjects) {
/* 237 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 238 */     InstructionList il = methodGen.getInstructionList();
/*     */ 
/*     */     
/* 241 */     int init = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.dom.SortingIterator", "<init>", "(Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;Lcom/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecordFactory;)V");
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
/* 257 */     LocalVariableGen nodesTemp = methodGen.addLocalVariable("sort_tmp1", 
/* 258 */         Util.getJCRefType("Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;"), (InstructionHandle)null, (InstructionHandle)null);
/*     */ 
/*     */ 
/*     */     
/* 262 */     LocalVariableGen sortRecordFactoryTemp = methodGen.addLocalVariable("sort_tmp2", 
/* 263 */         Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecordFactory;"), (InstructionHandle)null, (InstructionHandle)null);
/*     */ 
/*     */ 
/*     */     
/* 267 */     if (nodeSet == null) {
/* 268 */       int children = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getAxisIterator", "(I)Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;");
/*     */ 
/*     */ 
/*     */       
/* 272 */       il.append(methodGen.loadDOM());
/* 273 */       il.append(new PUSH(cpg, 3));
/* 274 */       il.append(new INVOKEINTERFACE(children, 2));
/*     */     } else {
/*     */       
/* 277 */       nodeSet.translate(classGen, methodGen);
/*     */     } 
/*     */     
/* 280 */     nodesTemp.setStart(il.append(new ASTORE(nodesTemp.getIndex())));
/*     */ 
/*     */ 
/*     */     
/* 284 */     compileSortRecordFactory(sortObjects, classGen, methodGen);
/* 285 */     sortRecordFactoryTemp.setStart(il
/* 286 */         .append(new ASTORE(sortRecordFactoryTemp.getIndex())));
/*     */     
/* 288 */     il.append(new NEW(cpg.addClass("com.sun.org.apache.xalan.internal.xsltc.dom.SortingIterator")));
/* 289 */     il.append(DUP);
/* 290 */     nodesTemp.setEnd(il.append(new ALOAD(nodesTemp.getIndex())));
/* 291 */     sortRecordFactoryTemp.setEnd(il
/* 292 */         .append(new ALOAD(sortRecordFactoryTemp.getIndex())));
/* 293 */     il.append(new INVOKESPECIAL(init));
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
/*     */   public static void compileSortRecordFactory(Vector<Sort> sortObjects, ClassGenerator classGen, MethodGenerator methodGen) {
/* 305 */     String sortRecordClass = compileSortRecord(sortObjects, classGen, methodGen);
/*     */     
/* 307 */     boolean needsSortRecordFactory = false;
/* 308 */     int nsorts = sortObjects.size();
/* 309 */     for (int i = 0; i < nsorts; i++) {
/* 310 */       Sort sort = sortObjects.elementAt(i);
/* 311 */       needsSortRecordFactory |= sort._needsSortRecordFactory;
/*     */     } 
/*     */     
/* 314 */     String sortRecordFactoryClass = "com/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecordFactory";
/* 315 */     if (needsSortRecordFactory)
/*     */     {
/* 317 */       sortRecordFactoryClass = compileSortRecordFactory(sortObjects, classGen, methodGen, sortRecordClass);
/*     */     }
/*     */ 
/*     */     
/* 321 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 322 */     InstructionList il = methodGen.getInstructionList();
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
/* 335 */     LocalVariableGen sortOrderTemp = methodGen.addLocalVariable("sort_order_tmp", 
/* 336 */         Util.getJCRefType("[Ljava/lang/String;"), (InstructionHandle)null, (InstructionHandle)null);
/*     */     
/* 338 */     il.append(new PUSH(cpg, nsorts));
/* 339 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/* 340 */     for (int level = 0; level < nsorts; level++) {
/* 341 */       Sort sort = sortObjects.elementAt(level);
/* 342 */       il.append(DUP);
/* 343 */       il.append(new PUSH(cpg, level));
/* 344 */       sort.translateSortOrder(classGen, methodGen);
/* 345 */       il.append(AASTORE);
/*     */     } 
/* 347 */     sortOrderTemp.setStart(il.append(new ASTORE(sortOrderTemp.getIndex())));
/*     */ 
/*     */     
/* 350 */     LocalVariableGen sortTypeTemp = methodGen.addLocalVariable("sort_type_tmp", 
/* 351 */         Util.getJCRefType("[Ljava/lang/String;"), (InstructionHandle)null, (InstructionHandle)null);
/*     */     
/* 353 */     il.append(new PUSH(cpg, nsorts));
/* 354 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/* 355 */     for (int k = 0; k < nsorts; k++) {
/* 356 */       Sort sort = sortObjects.elementAt(k);
/* 357 */       il.append(DUP);
/* 358 */       il.append(new PUSH(cpg, k));
/* 359 */       sort.translateSortType(classGen, methodGen);
/* 360 */       il.append(AASTORE);
/*     */     } 
/* 362 */     sortTypeTemp.setStart(il.append(new ASTORE(sortTypeTemp.getIndex())));
/*     */ 
/*     */     
/* 365 */     LocalVariableGen sortLangTemp = methodGen.addLocalVariable("sort_lang_tmp", 
/* 366 */         Util.getJCRefType("[Ljava/lang/String;"), (InstructionHandle)null, (InstructionHandle)null);
/*     */     
/* 368 */     il.append(new PUSH(cpg, nsorts));
/* 369 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/* 370 */     for (int m = 0; m < nsorts; m++) {
/* 371 */       Sort sort = sortObjects.elementAt(m);
/* 372 */       il.append(DUP);
/* 373 */       il.append(new PUSH(cpg, m));
/* 374 */       sort.translateLang(classGen, methodGen);
/* 375 */       il.append(AASTORE);
/*     */     } 
/* 377 */     sortLangTemp.setStart(il.append(new ASTORE(sortLangTemp.getIndex())));
/*     */ 
/*     */     
/* 380 */     LocalVariableGen sortCaseOrderTemp = methodGen.addLocalVariable("sort_case_order_tmp", 
/* 381 */         Util.getJCRefType("[Ljava/lang/String;"), (InstructionHandle)null, (InstructionHandle)null);
/*     */     
/* 383 */     il.append(new PUSH(cpg, nsorts));
/* 384 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/* 385 */     for (int n = 0; n < nsorts; n++) {
/* 386 */       Sort sort = sortObjects.elementAt(n);
/* 387 */       il.append(DUP);
/* 388 */       il.append(new PUSH(cpg, n));
/* 389 */       sort.translateCaseOrder(classGen, methodGen);
/* 390 */       il.append(AASTORE);
/*     */     } 
/* 392 */     sortCaseOrderTemp.setStart(il
/* 393 */         .append(new ASTORE(sortCaseOrderTemp.getIndex())));
/*     */     
/* 395 */     il.append(new NEW(cpg.addClass(sortRecordFactoryClass)));
/* 396 */     il.append(DUP);
/* 397 */     il.append(methodGen.loadDOM());
/* 398 */     il.append(new PUSH(cpg, sortRecordClass));
/* 399 */     il.append(classGen.loadTranslet());
/*     */     
/* 401 */     sortOrderTemp.setEnd(il.append(new ALOAD(sortOrderTemp.getIndex())));
/* 402 */     sortTypeTemp.setEnd(il.append(new ALOAD(sortTypeTemp.getIndex())));
/* 403 */     sortLangTemp.setEnd(il.append(new ALOAD(sortLangTemp.getIndex())));
/* 404 */     sortCaseOrderTemp.setEnd(il
/* 405 */         .append(new ALOAD(sortCaseOrderTemp.getIndex())));
/*     */     
/* 407 */     il.append(new INVOKESPECIAL(cpg
/* 408 */           .addMethodref(sortRecordFactoryClass, "<init>", "(Lcom/sun/org/apache/xalan/internal/xsltc/DOM;Ljava/lang/String;Lcom/sun/org/apache/xalan/internal/xsltc/Translet;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 418 */     ArrayList<VariableRefBase> dups = new ArrayList<>();
/*     */     
/* 420 */     for (int j = 0; j < nsorts; j++) {
/* 421 */       Sort sort = sortObjects.get(j);
/*     */       
/* 423 */       int length = (sort._closureVars == null) ? 0 : sort._closureVars.size();
/*     */       
/* 425 */       for (int i1 = 0; i1 < length; i1++) {
/* 426 */         VariableRefBase varRef = sort._closureVars.get(i1);
/*     */ 
/*     */         
/* 429 */         if (!dups.contains(varRef)) {
/*     */           
/* 431 */           VariableBase var = varRef.getVariable();
/*     */ 
/*     */           
/* 434 */           il.append(DUP);
/* 435 */           il.append(var.loadInstruction());
/* 436 */           il.append(new PUTFIELD(cpg
/* 437 */                 .addFieldref(sortRecordFactoryClass, var.getEscapedName(), var
/* 438 */                   .getType().toSignature())));
/* 439 */           dups.add(varRef);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String compileSortRecordFactory(Vector<Sort> sortObjects, ClassGenerator classGen, MethodGenerator methodGen, String sortRecordClass) {
/* 448 */     XSLTC xsltc = ((Sort)sortObjects.firstElement()).getXSLTC();
/* 449 */     String className = xsltc.getHelperClassName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 457 */     NodeSortRecordFactGenerator sortRecordFactory = new NodeSortRecordFactGenerator(className, "com/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecordFactory", className + ".java", 49, new String[0], classGen.getStylesheet());
/*     */     
/* 459 */     ConstantPoolGen cpg = sortRecordFactory.getConstantPool();
/*     */ 
/*     */     
/* 462 */     int nsorts = sortObjects.size();
/* 463 */     ArrayList<VariableRefBase> dups = new ArrayList<>();
/*     */     
/* 465 */     for (int j = 0; j < nsorts; j++) {
/* 466 */       Sort sort = sortObjects.get(j);
/*     */       
/* 468 */       int length = (sort._closureVars == null) ? 0 : sort._closureVars.size();
/*     */       
/* 470 */       for (int k = 0; k < length; k++) {
/* 471 */         VariableRefBase varRef = sort._closureVars.get(k);
/*     */ 
/*     */         
/* 474 */         if (!dups.contains(varRef)) {
/*     */           
/* 476 */           VariableBase var = varRef.getVariable();
/* 477 */           sortRecordFactory.addField(new Field(1, cpg
/* 478 */                 .addUtf8(var.getEscapedName()), cpg
/* 479 */                 .addUtf8(var.getType().toSignature()), null, cpg
/* 480 */                 .getConstantPool()));
/* 481 */           dups.add(varRef);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 486 */     Type[] argTypes = new Type[7];
/*     */     
/* 488 */     argTypes[0] = Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/DOM;");
/* 489 */     argTypes[1] = Util.getJCRefType("Ljava/lang/String;");
/* 490 */     argTypes[2] = Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/Translet;");
/* 491 */     argTypes[3] = Util.getJCRefType("[Ljava/lang/String;");
/* 492 */     argTypes[4] = Util.getJCRefType("[Ljava/lang/String;");
/* 493 */     argTypes[5] = Util.getJCRefType("[Ljava/lang/String;");
/* 494 */     argTypes[6] = Util.getJCRefType("[Ljava/lang/String;");
/*     */     
/* 496 */     String[] argNames = new String[7];
/* 497 */     argNames[0] = "document";
/* 498 */     argNames[1] = "className";
/* 499 */     argNames[2] = "translet";
/* 500 */     argNames[3] = "order";
/* 501 */     argNames[4] = "type";
/* 502 */     argNames[5] = "lang";
/* 503 */     argNames[6] = "case_order";
/*     */ 
/*     */     
/* 506 */     InstructionList il = new InstructionList();
/* 507 */     MethodGenerator constructor = new MethodGenerator(1, Type.VOID, argTypes, argNames, "<init>", className, il, cpg);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 514 */     il.append(ALOAD_0);
/* 515 */     il.append(ALOAD_1);
/* 516 */     il.append(ALOAD_2);
/* 517 */     il.append(new ALOAD(3));
/* 518 */     il.append(new ALOAD(4));
/* 519 */     il.append(new ALOAD(5));
/* 520 */     il.append(new ALOAD(6));
/* 521 */     il.append(new ALOAD(7));
/* 522 */     il.append(new INVOKESPECIAL(cpg.addMethodref("com/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecordFactory", "<init>", "(Lcom/sun/org/apache/xalan/internal/xsltc/DOM;Ljava/lang/String;Lcom/sun/org/apache/xalan/internal/xsltc/Translet;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 531 */     il.append(RETURN);
/*     */ 
/*     */     
/* 534 */     il = new InstructionList();
/*     */ 
/*     */     
/* 537 */     MethodGenerator makeNodeSortRecord = new MethodGenerator(1, Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecord;"), new Type[] { Type.INT, Type.INT }, new String[] { "node", "last" }, "makeNodeSortRecord", className, il, cpg);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 544 */     il.append(ALOAD_0);
/* 545 */     il.append(ILOAD_1);
/* 546 */     il.append(ILOAD_2);
/* 547 */     il.append(new INVOKESPECIAL(cpg.addMethodref("com/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecordFactory", "makeNodeSortRecord", "(II)Lcom/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecord;")));
/*     */     
/* 549 */     il.append(DUP);
/* 550 */     il.append(new CHECKCAST(cpg.addClass(sortRecordClass)));
/*     */ 
/*     */     
/* 553 */     int ndups = dups.size();
/* 554 */     for (int i = 0; i < ndups; i++) {
/* 555 */       VariableRefBase varRef = dups.get(i);
/* 556 */       VariableBase var = varRef.getVariable();
/* 557 */       Type varType = var.getType();
/*     */       
/* 559 */       il.append(DUP);
/*     */ 
/*     */       
/* 562 */       il.append(ALOAD_0);
/* 563 */       il.append(new GETFIELD(cpg
/* 564 */             .addFieldref(className, var
/* 565 */               .getEscapedName(), varType.toSignature())));
/*     */ 
/*     */       
/* 568 */       il.append(new PUTFIELD(cpg
/* 569 */             .addFieldref(sortRecordClass, var
/* 570 */               .getEscapedName(), varType.toSignature())));
/*     */     } 
/* 572 */     il.append(POP);
/* 573 */     il.append(ARETURN);
/*     */     
/* 575 */     constructor.setMaxLocals();
/* 576 */     constructor.setMaxStack();
/* 577 */     sortRecordFactory.addMethod(constructor);
/* 578 */     makeNodeSortRecord.setMaxLocals();
/* 579 */     makeNodeSortRecord.setMaxStack();
/* 580 */     sortRecordFactory.addMethod(makeNodeSortRecord);
/* 581 */     xsltc.dumpClass(sortRecordFactory.getJavaClass());
/*     */     
/* 583 */     return className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String compileSortRecord(Vector<Sort> sortObjects, ClassGenerator classGen, MethodGenerator methodGen) {
/* 592 */     XSLTC xsltc = ((Sort)sortObjects.firstElement()).getXSLTC();
/* 593 */     String className = xsltc.getHelperClassName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 602 */     NodeSortRecordGenerator sortRecord = new NodeSortRecordGenerator(className, "com.sun.org.apache.xalan.internal.xsltc.dom.NodeSortRecord", "sort$0.java", 49, new String[0], classGen.getStylesheet());
/*     */     
/* 604 */     ConstantPoolGen cpg = sortRecord.getConstantPool();
/*     */ 
/*     */     
/* 607 */     int nsorts = sortObjects.size();
/* 608 */     ArrayList<VariableRefBase> dups = new ArrayList<>();
/*     */     
/* 610 */     for (int j = 0; j < nsorts; j++) {
/* 611 */       Sort sort = sortObjects.get(j);
/*     */ 
/*     */       
/* 614 */       sort.setInnerClassName(className);
/*     */ 
/*     */       
/* 617 */       int length = (sort._closureVars == null) ? 0 : sort._closureVars.size();
/* 618 */       for (int i = 0; i < length; i++) {
/* 619 */         VariableRefBase varRef = sort._closureVars.get(i);
/*     */ 
/*     */         
/* 622 */         if (!dups.contains(varRef)) {
/*     */           
/* 624 */           VariableBase var = varRef.getVariable();
/* 625 */           sortRecord.addField(new Field(1, cpg
/* 626 */                 .addUtf8(var.getEscapedName()), cpg
/* 627 */                 .addUtf8(var.getType().toSignature()), null, cpg
/* 628 */                 .getConstantPool()));
/* 629 */           dups.add(varRef);
/*     */         } 
/*     */       } 
/*     */     } 
/* 633 */     MethodGenerator init = compileInit(sortRecord, cpg, className);
/* 634 */     MethodGenerator extract = compileExtract(sortObjects, sortRecord, cpg, className);
/*     */     
/* 636 */     sortRecord.addMethod(init);
/* 637 */     sortRecord.addMethod(extract);
/*     */     
/* 639 */     xsltc.dumpClass(sortRecord.getJavaClass());
/* 640 */     return className;
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
/*     */   private static MethodGenerator compileInit(NodeSortRecordGenerator sortRecord, ConstantPoolGen cpg, String className) {
/* 652 */     InstructionList il = new InstructionList();
/* 653 */     MethodGenerator init = new MethodGenerator(1, Type.VOID, null, null, "<init>", className, il, cpg);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 660 */     il.append(ALOAD_0);
/* 661 */     il.append(new INVOKESPECIAL(cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.dom.NodeSortRecord", "<init>", "()V")));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 666 */     il.append(RETURN);
/*     */     
/* 668 */     return init;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MethodGenerator compileExtract(Vector<Sort> sortObjects, NodeSortRecordGenerator sortRecord, ConstantPoolGen cpg, String className) {
/* 679 */     InstructionList il = new InstructionList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 689 */     CompareGenerator extractMethod = new CompareGenerator(17, Type.STRING, new Type[] { Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/DOM;"), Type.INT, Type.INT, Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/runtime/AbstractTranslet;"), Type.INT }, new String[] { "dom", "current", "level", "translet", "last" }, "extractValueFromDOM", className, il, cpg);
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
/* 701 */     int levels = sortObjects.size();
/* 702 */     int[] match = new int[levels];
/* 703 */     InstructionHandle[] target = new InstructionHandle[levels];
/* 704 */     InstructionHandle tblswitch = null;
/*     */ 
/*     */     
/* 707 */     if (levels > 1) {
/*     */       
/* 709 */       il.append(new ILOAD(extractMethod.getLocalIndex("level")));
/*     */       
/* 711 */       tblswitch = il.append(new NOP());
/*     */     } 
/*     */ 
/*     */     
/* 715 */     for (int level = 0; level < levels; level++) {
/* 716 */       match[level] = level;
/* 717 */       Sort sort = sortObjects.elementAt(level);
/* 718 */       target[level] = il.append(NOP);
/* 719 */       sort.translateSelect(sortRecord, extractMethod);
/* 720 */       il.append(ARETURN);
/*     */     } 
/*     */ 
/*     */     
/* 724 */     if (levels > 1) {
/*     */ 
/*     */       
/* 727 */       InstructionHandle defaultTarget = il.append(new PUSH(cpg, ""));
/* 728 */       il.insert(tblswitch, new TABLESWITCH(match, target, defaultTarget));
/* 729 */       il.append(ARETURN);
/*     */     } 
/*     */     
/* 732 */     return extractMethod;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compiler\Sort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */