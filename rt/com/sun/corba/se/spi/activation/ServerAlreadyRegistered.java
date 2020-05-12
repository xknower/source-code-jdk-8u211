/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServerAlreadyRegistered
/*    */   extends UserException
/*    */ {
/* 13 */   public int serverId = 0;
/*    */ 
/*    */   
/*    */   public ServerAlreadyRegistered() {
/* 17 */     super(ServerAlreadyRegisteredHelper.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public ServerAlreadyRegistered(int paramInt) {
/* 22 */     super(ServerAlreadyRegisteredHelper.id());
/* 23 */     this.serverId = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerAlreadyRegistered(String paramString, int paramInt) {
/* 29 */     super(ServerAlreadyRegisteredHelper.id() + "  " + paramString);
/* 30 */     this.serverId = paramInt;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\ServerAlreadyRegistered.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */