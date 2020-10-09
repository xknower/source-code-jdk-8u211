/*    */ package com.sun.jndi.cosnaming;
/*    */ 
/*    */ import java.util.Hashtable;
/*    */ import javax.naming.Context;
/*    */ import javax.naming.NamingException;
/*    */ import javax.naming.spi.InitialContextFactory;
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
/*    */ public class CNCtxFactory
/*    */   implements InitialContextFactory
/*    */ {
/*    */   public Context getInitialContext(Hashtable<?, ?> paramHashtable) throws NamingException {
/* 49 */     return new CNCtx(paramHashtable);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jndi\cosnaming\CNCtxFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */