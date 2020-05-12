/*    */ package com.sun.jmx.snmp.daemon;
/*    */ 
/*    */ import javax.management.JMRuntimeException;
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
/*    */ public class CommunicationException
/*    */   extends JMRuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -2499186113233316177L;
/*    */   
/*    */   public CommunicationException(Throwable paramThrowable) {
/* 50 */     super(paramThrowable.getMessage());
/* 51 */     initCause(paramThrowable);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CommunicationException(Throwable paramThrowable, String paramString) {
/* 59 */     super(paramString);
/* 60 */     initCause(paramThrowable);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CommunicationException(String paramString) {
/* 67 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable getTargetException() {
/* 74 */     return getCause();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\CommunicationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */