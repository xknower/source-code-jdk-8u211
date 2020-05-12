/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServerNotRegistered
/*    */   extends UserException
/*    */ {
/* 13 */   public int serverId = 0;
/*    */ 
/*    */   
/*    */   public ServerNotRegistered() {
/* 17 */     super(ServerNotRegisteredHelper.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public ServerNotRegistered(int paramInt) {
/* 22 */     super(ServerNotRegisteredHelper.id());
/* 23 */     this.serverId = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerNotRegistered(String paramString, int paramInt) {
/* 29 */     super(ServerNotRegisteredHelper.id() + "  " + paramString);
/* 30 */     this.serverId = paramInt;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\ServerNotRegistered.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */