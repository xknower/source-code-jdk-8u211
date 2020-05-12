/*    */ package org.omg.PortableInterceptor.ORBInitInfoPackage;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
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
/*    */ public final class DuplicateName
/*    */   extends UserException
/*    */ {
/* 17 */   public String name = null;
/*    */ 
/*    */   
/*    */   public DuplicateName() {
/* 21 */     super(DuplicateNameHelper.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public DuplicateName(String paramString) {
/* 26 */     super(DuplicateNameHelper.id());
/* 27 */     this.name = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public DuplicateName(String paramString1, String paramString2) {
/* 33 */     super(DuplicateNameHelper.id() + "  " + paramString1);
/* 34 */     this.name = paramString2;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\PortableInterceptor\ORBInitInfoPackage\DuplicateName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */