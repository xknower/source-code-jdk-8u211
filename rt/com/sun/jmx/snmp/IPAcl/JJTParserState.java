/*     */ package com.sun.jmx.snmp.IPAcl;
/*     */ 
/*     */ import java.util.Stack;
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
/*     */ class JJTParserState
/*     */ {
/*  39 */   private Stack<Node> nodes = new Stack<>();
/*  40 */   private Stack<Integer> marks = new Stack<>();
/*  41 */   private int sp = 0;
/*  42 */   private int mk = 0;
/*     */ 
/*     */   
/*     */   private boolean node_created;
/*     */ 
/*     */   
/*     */   boolean nodeCreated() {
/*  49 */     return this.node_created;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void reset() {
/*  55 */     this.nodes.removeAllElements();
/*  56 */     this.marks.removeAllElements();
/*  57 */     this.sp = 0;
/*  58 */     this.mk = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Node rootNode() {
/*  64 */     return this.nodes.elementAt(0);
/*     */   }
/*     */ 
/*     */   
/*     */   void pushNode(Node paramNode) {
/*  69 */     this.nodes.push(paramNode);
/*  70 */     this.sp++;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Node popNode() {
/*  76 */     if (--this.sp < this.mk) {
/*  77 */       this.mk = ((Integer)this.marks.pop()).intValue();
/*     */     }
/*  79 */     return this.nodes.pop();
/*     */   }
/*     */ 
/*     */   
/*     */   Node peekNode() {
/*  84 */     return this.nodes.peek();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int nodeArity() {
/*  90 */     return this.sp - this.mk;
/*     */   }
/*     */ 
/*     */   
/*     */   void clearNodeScope(Node paramNode) {
/*  95 */     while (this.sp > this.mk) {
/*  96 */       popNode();
/*     */     }
/*  98 */     this.mk = ((Integer)this.marks.pop()).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   void openNodeScope(Node paramNode) {
/* 103 */     this.marks.push(new Integer(this.mk));
/* 104 */     this.mk = this.sp;
/* 105 */     paramNode.jjtOpen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void closeNodeScope(Node paramNode, int paramInt) {
/* 114 */     this.mk = ((Integer)this.marks.pop()).intValue();
/* 115 */     while (paramInt-- > 0) {
/* 116 */       Node node = popNode();
/* 117 */       node.jjtSetParent(paramNode);
/* 118 */       paramNode.jjtAddChild(node, paramInt);
/*     */     } 
/* 120 */     paramNode.jjtClose();
/* 121 */     pushNode(paramNode);
/* 122 */     this.node_created = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void closeNodeScope(Node paramNode, boolean paramBoolean) {
/* 132 */     if (paramBoolean) {
/* 133 */       int i = nodeArity();
/* 134 */       this.mk = ((Integer)this.marks.pop()).intValue();
/* 135 */       while (i-- > 0) {
/* 136 */         Node node = popNode();
/* 137 */         node.jjtSetParent(paramNode);
/* 138 */         paramNode.jjtAddChild(node, i);
/*     */       } 
/* 140 */       paramNode.jjtClose();
/* 141 */       pushNode(paramNode);
/* 142 */       this.node_created = true;
/*     */     } else {
/* 144 */       this.mk = ((Integer)this.marks.pop()).intValue();
/* 145 */       this.node_created = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JJTParserState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */