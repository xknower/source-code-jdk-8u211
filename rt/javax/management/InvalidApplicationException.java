/*    */ package javax.management;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvalidApplicationException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = -3048022274675537269L;
/*    */   private Object val;
/*    */   
/*    */   public InvalidApplicationException(Object paramObject) {
/* 56 */     this.val = paramObject;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\InvalidApplicationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */