/*    */ package org.omg.PortableServer.POAPackage;
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
/*    */ public final class WrongAdapter
/*    */   extends UserException
/*    */ {
/*    */   public WrongAdapter() {
/* 16 */     super(WrongAdapterHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public WrongAdapter(String paramString) {
/* 22 */     super(WrongAdapterHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\PortableServer\POAPackage\WrongAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */