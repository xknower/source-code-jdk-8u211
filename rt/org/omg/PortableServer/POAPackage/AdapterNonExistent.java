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
/*    */ public final class AdapterNonExistent
/*    */   extends UserException
/*    */ {
/*    */   public AdapterNonExistent() {
/* 16 */     super(AdapterNonExistentHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AdapterNonExistent(String paramString) {
/* 22 */     super(AdapterNonExistentHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\PortableServer\POAPackage\AdapterNonExistent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */