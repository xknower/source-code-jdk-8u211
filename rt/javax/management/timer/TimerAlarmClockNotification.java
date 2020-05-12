/*    */ package javax.management.timer;
/*    */ 
/*    */ import javax.management.Notification;
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
/*    */ 
/*    */ 
/*    */ class TimerAlarmClockNotification
/*    */   extends Notification
/*    */ {
/*    */   private static final long serialVersionUID = -4841061275673620641L;
/*    */   
/*    */   public TimerAlarmClockNotification(TimerAlarmClock paramTimerAlarmClock) {
/* 50 */     super("", paramTimerAlarmClock, 0L);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\timer\TimerAlarmClockNotification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */