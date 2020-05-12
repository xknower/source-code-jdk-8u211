/*    */ package com.sun.org.apache.xerces.internal.impl.xs.util;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.xs.SchemaGrammar;
/*    */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*    */ import com.sun.org.apache.xerces.internal.xs.XSObject;
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
/*    */ public final class XSInputSource
/*    */   extends XMLInputSource
/*    */ {
/*    */   private SchemaGrammar[] fGrammars;
/*    */   private XSObject[] fComponents;
/*    */   
/*    */   public XSInputSource(SchemaGrammar[] grammars) {
/* 40 */     super(null, null, null);
/* 41 */     this.fGrammars = grammars;
/* 42 */     this.fComponents = null;
/*    */   }
/*    */   
/*    */   public XSInputSource(XSObject[] component) {
/* 46 */     super(null, null, null);
/* 47 */     this.fGrammars = null;
/* 48 */     this.fComponents = component;
/*    */   }
/*    */   
/*    */   public SchemaGrammar[] getGrammars() {
/* 52 */     return this.fGrammars;
/*    */   }
/*    */   
/*    */   public void setGrammars(SchemaGrammar[] grammars) {
/* 56 */     this.fGrammars = grammars;
/*    */   }
/*    */   
/*    */   public XSObject[] getComponents() {
/* 60 */     return this.fComponents;
/*    */   }
/*    */   
/*    */   public void setComponents(XSObject[] components) {
/* 64 */     this.fComponents = components;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\impl\x\\util\XSInputSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */