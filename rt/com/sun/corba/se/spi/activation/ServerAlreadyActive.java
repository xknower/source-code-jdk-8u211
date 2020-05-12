/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServerAlreadyActive
/*    */   extends UserException
/*    */ {
/* 13 */   public int serverId = 0;
/*    */ 
/*    */   
/*    */   public ServerAlreadyActive() {
/* 17 */     super(ServerAlreadyActiveHelper.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public ServerAlreadyActive(int paramInt) {
/* 22 */     super(ServerAlreadyActiveHelper.id());
/* 23 */     this.serverId = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerAlreadyActive(String paramString, int paramInt) {
/* 29 */     super(ServerAlreadyActiveHelper.id() + "  " + paramString);
/* 30 */     this.serverId = paramInt;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\ServerAlreadyActive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */