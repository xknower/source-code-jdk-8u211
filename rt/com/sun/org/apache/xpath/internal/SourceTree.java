/*    */ package com.sun.org.apache.xpath.internal;
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
/*    */ public class SourceTree
/*    */ {
/*    */   public String m_url;
/*    */   public int m_root;
/*    */   
/*    */   public SourceTree(int root, String url) {
/* 44 */     this.m_root = root;
/* 45 */     this.m_url = url;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\SourceTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */