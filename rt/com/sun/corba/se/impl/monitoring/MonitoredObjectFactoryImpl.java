/*    */ package com.sun.corba.se.impl.monitoring;
/*    */ 
/*    */ import com.sun.corba.se.spi.monitoring.MonitoredObject;
/*    */ import com.sun.corba.se.spi.monitoring.MonitoredObjectFactory;
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
/*    */ public class MonitoredObjectFactoryImpl
/*    */   implements MonitoredObjectFactory
/*    */ {
/*    */   public MonitoredObject createMonitoredObject(String paramString1, String paramString2) {
/* 36 */     return new MonitoredObjectImpl(paramString1, paramString2);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\monitoring\MonitoredObjectFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */