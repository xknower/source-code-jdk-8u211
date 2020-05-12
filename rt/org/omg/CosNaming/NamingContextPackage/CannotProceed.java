/*    */ package org.omg.CosNaming.NamingContextPackage;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ import org.omg.CosNaming.NameComponent;
/*    */ import org.omg.CosNaming.NamingContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CannotProceed
/*    */   extends UserException
/*    */ {
/* 13 */   public NamingContext cxt = null;
/* 14 */   public NameComponent[] rest_of_name = null;
/*    */ 
/*    */   
/*    */   public CannotProceed() {
/* 18 */     super(CannotProceedHelper.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public CannotProceed(NamingContext paramNamingContext, NameComponent[] paramArrayOfNameComponent) {
/* 23 */     super(CannotProceedHelper.id());
/* 24 */     this.cxt = paramNamingContext;
/* 25 */     this.rest_of_name = paramArrayOfNameComponent;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CannotProceed(String paramString, NamingContext paramNamingContext, NameComponent[] paramArrayOfNameComponent) {
/* 31 */     super(CannotProceedHelper.id() + "  " + paramString);
/* 32 */     this.cxt = paramNamingContext;
/* 33 */     this.rest_of_name = paramArrayOfNameComponent;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CosNaming\NamingContextPackage\CannotProceed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */