/*    */ package com.sun.org.glassfish.gmbal;
/*    */ 
/*    */ import javax.management.Attribute;
/*    */ import javax.management.AttributeList;
/*    */ import javax.management.AttributeNotFoundException;
/*    */ import javax.management.InvalidAttributeValueException;
/*    */ import javax.management.ListenerNotFoundException;
/*    */ import javax.management.MBeanException;
/*    */ import javax.management.MBeanInfo;
/*    */ import javax.management.MBeanNotificationInfo;
/*    */ import javax.management.NotificationFilter;
/*    */ import javax.management.NotificationListener;
/*    */ import javax.management.ReflectionException;
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
/*    */ public class GmbalMBeanNOPImpl
/*    */   implements GmbalMBean
/*    */ {
/*    */   public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {}
/*    */ 
/*    */ 
/*    */   
/*    */   public AttributeList getAttributes(String[] attributes) {
/* 61 */     return null;
/*    */   }
/*    */   
/*    */   public AttributeList setAttributes(AttributeList attributes) {
/* 65 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
/* 71 */     return null;
/*    */   }
/*    */   
/*    */   public MBeanInfo getMBeanInfo() {
/* 75 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws ListenerNotFoundException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws IllegalArgumentException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MBeanNotificationInfo[] getNotificationInfo() {
/* 98 */     return new MBeanNotificationInfo[0];
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\glassfish\gmbal\GmbalMBeanNOPImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */