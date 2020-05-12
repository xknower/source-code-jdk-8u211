/*    */ package org.omg.CORBA.portable;
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
/*    */ public class ApplicationException
/*    */   extends Exception
/*    */ {
/*    */   private String id;
/*    */   private InputStream ins;
/*    */   
/*    */   public ApplicationException(String paramString, InputStream paramInputStream) {
/* 41 */     this.id = paramString;
/* 42 */     this.ins = paramInputStream;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getId() {
/* 51 */     return this.id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getInputStream() {
/* 59 */     return this.ins;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\portable\ApplicationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */