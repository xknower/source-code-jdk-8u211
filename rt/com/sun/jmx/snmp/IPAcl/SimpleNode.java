/*     */ package com.sun.jmx.snmp.IPAcl;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.util.Hashtable;
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
/*     */ class SimpleNode
/*     */   implements Node
/*     */ {
/*     */   protected Node parent;
/*     */   protected Node[] children;
/*     */   protected int id;
/*     */   protected Parser parser;
/*     */   
/*     */   public SimpleNode(int paramInt) {
/*  42 */     this.id = paramInt;
/*     */   }
/*     */   
/*     */   public SimpleNode(Parser paramParser, int paramInt) {
/*  46 */     this(paramInt);
/*  47 */     this.parser = paramParser;
/*     */   }
/*     */   
/*     */   public static Node jjtCreate(int paramInt) {
/*  51 */     return new SimpleNode(paramInt);
/*     */   }
/*     */   
/*     */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/*  55 */     return new SimpleNode(paramParser, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void jjtOpen() {}
/*     */   
/*     */   public void jjtClose() {}
/*     */   
/*     */   public void jjtSetParent(Node paramNode) {
/*  64 */     this.parent = paramNode; } public Node jjtGetParent() {
/*  65 */     return this.parent;
/*     */   }
/*     */   public void jjtAddChild(Node paramNode, int paramInt) {
/*  68 */     if (this.children == null) {
/*  69 */       this.children = new Node[paramInt + 1];
/*  70 */     } else if (paramInt >= this.children.length) {
/*  71 */       Node[] arrayOfNode = new Node[paramInt + 1];
/*  72 */       System.arraycopy(this.children, 0, arrayOfNode, 0, this.children.length);
/*  73 */       this.children = arrayOfNode;
/*     */     } 
/*  75 */     this.children[paramInt] = paramNode;
/*     */   }
/*     */   
/*     */   public Node jjtGetChild(int paramInt) {
/*  79 */     return this.children[paramInt];
/*     */   }
/*     */   
/*     */   public int jjtGetNumChildren() {
/*  83 */     return (this.children == null) ? 0 : this.children.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildTrapEntries(Hashtable<InetAddress, Vector<String>> paramHashtable) {
/*  94 */     if (this.children != null) {
/*  95 */       for (byte b = 0; b < this.children.length; b++) {
/*  96 */         SimpleNode simpleNode = (SimpleNode)this.children[b];
/*  97 */         if (simpleNode != null) {
/*  98 */           simpleNode.buildTrapEntries(paramHashtable);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildInformEntries(Hashtable<InetAddress, Vector<String>> paramHashtable) {
/* 107 */     if (this.children != null) {
/* 108 */       for (byte b = 0; b < this.children.length; b++) {
/* 109 */         SimpleNode simpleNode = (SimpleNode)this.children[b];
/* 110 */         if (simpleNode != null) {
/* 111 */           simpleNode.buildInformEntries(paramHashtable);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildAclEntries(PrincipalImpl paramPrincipalImpl, AclImpl paramAclImpl) {
/* 121 */     if (this.children != null) {
/* 122 */       for (byte b = 0; b < this.children.length; b++) {
/* 123 */         SimpleNode simpleNode = (SimpleNode)this.children[b];
/* 124 */         if (simpleNode != null) {
/* 125 */           simpleNode.buildAclEntries(paramPrincipalImpl, paramAclImpl);
/*     */         }
/*     */       } 
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
/*     */   public String toString() {
/* 139 */     return ParserTreeConstants.jjtNodeName[this.id]; } public String toString(String paramString) {
/* 140 */     return paramString + toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dump(String paramString) {
/* 146 */     if (this.children != null)
/* 147 */       for (byte b = 0; b < this.children.length; b++) {
/* 148 */         SimpleNode simpleNode = (SimpleNode)this.children[b];
/* 149 */         if (simpleNode != null)
/* 150 */           simpleNode.dump(paramString + " "); 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\SimpleNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */