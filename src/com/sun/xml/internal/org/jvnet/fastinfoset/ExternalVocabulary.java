/*    */ package com.sun.xml.internal.org.jvnet.fastinfoset;
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
/*    */ public class ExternalVocabulary
/*    */ {
/*    */   public final String URI;
/*    */   public final Vocabulary vocabulary;
/*    */   
/*    */   public ExternalVocabulary(String URI, Vocabulary vocabulary) {
/* 50 */     if (URI == null || vocabulary == null) {
/* 51 */       throw new IllegalArgumentException();
/*    */     }
/*    */     
/* 54 */     this.URI = URI;
/* 55 */     this.vocabulary = vocabulary;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\org\jvnet\fastinfoset\ExternalVocabulary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */