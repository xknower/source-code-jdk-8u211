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
/*    */ public class FastInfosetException
/*    */   extends Exception
/*    */ {
/*    */   public FastInfosetException(String message) {
/* 33 */     super(message);
/*    */   }
/*    */   
/*    */   public FastInfosetException(String message, Exception e) {
/* 37 */     super(message, e);
/*    */   }
/*    */   
/*    */   public FastInfosetException(Exception e) {
/* 41 */     super(e);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\org\jvnet\fastinfoset\FastInfosetException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */