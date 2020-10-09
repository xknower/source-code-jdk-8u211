/*     */ package com.sun.nio.sctp;
/*     */ 
/*     */ import jdk.Exported;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Exported
/*     */ public class AbstractNotificationHandler<T>
/*     */   implements NotificationHandler<T>
/*     */ {
/*     */   public HandlerResult handleNotification(Notification paramNotification, T paramT) {
/*  67 */     return HandlerResult.CONTINUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HandlerResult handleNotification(AssociationChangeNotification paramAssociationChangeNotification, T paramT) {
/*  85 */     return HandlerResult.CONTINUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HandlerResult handleNotification(PeerAddressChangeNotification paramPeerAddressChangeNotification, T paramT) {
/* 103 */     return HandlerResult.CONTINUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HandlerResult handleNotification(SendFailedNotification paramSendFailedNotification, T paramT) {
/* 121 */     return HandlerResult.CONTINUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HandlerResult handleNotification(ShutdownNotification paramShutdownNotification, T paramT) {
/* 139 */     return HandlerResult.CONTINUE;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\nio\sctp\AbstractNotificationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */