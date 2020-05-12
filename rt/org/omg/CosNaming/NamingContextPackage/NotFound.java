/*    */ package org.omg.CosNaming.NamingContextPackage;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ import org.omg.CosNaming.NameComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NotFound
/*    */   extends UserException
/*    */ {
/* 13 */   public NotFoundReason why = null;
/* 14 */   public NameComponent[] rest_of_name = null;
/*    */ 
/*    */   
/*    */   public NotFound() {
/* 18 */     super(NotFoundHelper.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public NotFound(NotFoundReason paramNotFoundReason, NameComponent[] paramArrayOfNameComponent) {
/* 23 */     super(NotFoundHelper.id());
/* 24 */     this.why = paramNotFoundReason;
/* 25 */     this.rest_of_name = paramArrayOfNameComponent;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NotFound(String paramString, NotFoundReason paramNotFoundReason, NameComponent[] paramArrayOfNameComponent) {
/* 31 */     super(NotFoundHelper.id() + "  " + paramString);
/* 32 */     this.why = paramNotFoundReason;
/* 33 */     this.rest_of_name = paramArrayOfNameComponent;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CosNaming\NamingContextPackage\NotFound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */