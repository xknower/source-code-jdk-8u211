/*    */ package org.omg.PortableServer.CurrentPackage;
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
/*    */ public final class NoContext
/*    */   extends UserException
/*    */ {
/*    */   public NoContext() {
/* 16 */     super(NoContextHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NoContext(String paramString) {
/* 22 */     super(NoContextHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\PortableServer\CurrentPackage\NoContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */