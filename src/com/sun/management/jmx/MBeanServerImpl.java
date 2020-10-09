/*     */ package com.sun.management.jmx;
/*     */ 
/*     */ import java.io.ObjectInputStream;
/*     */ import java.util.Set;
/*     */ import javax.management.Attribute;
/*     */ import javax.management.AttributeList;
/*     */ import javax.management.AttributeNotFoundException;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.IntrospectionException;
/*     */ import javax.management.InvalidAttributeValueException;
/*     */ import javax.management.ListenerNotFoundException;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.MBeanInfo;
/*     */ import javax.management.MBeanRegistrationException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.MBeanServerBuilder;
/*     */ import javax.management.MBeanServerDelegate;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.NotificationFilter;
/*     */ import javax.management.NotificationListener;
/*     */ import javax.management.ObjectInstance;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.OperationsException;
/*     */ import javax.management.QueryExp;
/*     */ import javax.management.ReflectionException;
/*     */ import javax.management.loading.ClassLoaderRepository;
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
/*     */ @Deprecated
/*     */ public class MBeanServerImpl
/*     */   implements MBeanServer
/*     */ {
/*     */   private final MBeanServer server;
/*     */   
/*     */   public MBeanServerImpl() {
/*  45 */     this(null);
/*     */   }
/*     */   
/*     */   public MBeanServerImpl(String paramString) {
/*  49 */     MBeanServerBuilder mBeanServerBuilder = new MBeanServerBuilder();
/*  50 */     MBeanServerDelegate mBeanServerDelegate = mBeanServerBuilder.newMBeanServerDelegate();
/*  51 */     this.server = mBeanServerBuilder.newMBeanServer(paramString, null, mBeanServerDelegate);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object instantiate(String paramString) throws ReflectionException, MBeanException {
/*  56 */     return this.server.instantiate(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object instantiate(String paramString, ObjectName paramObjectName) throws ReflectionException, MBeanException, InstanceNotFoundException {
/*  62 */     return this.server.instantiate(paramString, paramObjectName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object instantiate(String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString) throws ReflectionException, MBeanException {
/*  70 */     return this.server.instantiate(paramString, paramArrayOfObject, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object instantiate(String paramString, ObjectName paramObjectName, Object[] paramArrayOfObject, String[] paramArrayOfString) throws ReflectionException, MBeanException, InstanceNotFoundException {
/*  77 */     return this.server.instantiate(paramString, paramObjectName, paramArrayOfObject, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
/*  85 */     return this.server.createMBean(paramString, paramObjectName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName1, ObjectName paramObjectName2) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
/*  93 */     return this.server.createMBean(paramString, paramObjectName1, paramObjectName2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName, Object[] paramArrayOfObject, String[] paramArrayOfString) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
/* 101 */     return this.server.createMBean(paramString, paramObjectName, paramArrayOfObject, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName1, ObjectName paramObjectName2, Object[] paramArrayOfObject, String[] paramArrayOfString) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
/* 110 */     return this.server.createMBean(paramString, paramObjectName1, paramObjectName2, paramArrayOfObject, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectInstance registerMBean(Object paramObject, ObjectName paramObjectName) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
/* 116 */     return this.server.registerMBean(paramObject, paramObjectName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregisterMBean(ObjectName paramObjectName) throws InstanceNotFoundException, MBeanRegistrationException {
/* 121 */     this.server.unregisterMBean(paramObjectName);
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectInstance getObjectInstance(ObjectName paramObjectName) throws InstanceNotFoundException {
/* 126 */     return this.server.getObjectInstance(paramObjectName);
/*     */   }
/*     */   
/*     */   public Set<ObjectInstance> queryMBeans(ObjectName paramObjectName, QueryExp paramQueryExp) {
/* 130 */     return this.server.queryMBeans(paramObjectName, paramQueryExp);
/*     */   }
/*     */   
/*     */   public Set<ObjectName> queryNames(ObjectName paramObjectName, QueryExp paramQueryExp) {
/* 134 */     return this.server.queryNames(paramObjectName, paramQueryExp);
/*     */   }
/*     */   
/*     */   public boolean isRegistered(ObjectName paramObjectName) {
/* 138 */     return this.server.isRegistered(paramObjectName);
/*     */   }
/*     */   
/*     */   public Integer getMBeanCount() {
/* 142 */     return this.server.getMBeanCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getAttribute(ObjectName paramObjectName, String paramString) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException {
/* 150 */     return this.server.getAttribute(paramObjectName, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributeList getAttributes(ObjectName paramObjectName, String[] paramArrayOfString) throws InstanceNotFoundException, ReflectionException {
/* 155 */     return this.server.getAttributes(paramObjectName, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(ObjectName paramObjectName, Attribute paramAttribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
/* 162 */     this.server.setAttribute(paramObjectName, paramAttribute);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeList setAttributes(ObjectName paramObjectName, AttributeList paramAttributeList) throws InstanceNotFoundException, ReflectionException {
/* 168 */     return this.server.setAttributes(paramObjectName, paramAttributeList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(ObjectName paramObjectName, String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString) throws InstanceNotFoundException, MBeanException, ReflectionException {
/* 175 */     return this.server.invoke(paramObjectName, paramString, paramArrayOfObject, paramArrayOfString);
/*     */   }
/*     */   
/*     */   public String getDefaultDomain() {
/* 179 */     return this.server.getDefaultDomain();
/*     */   }
/*     */   
/*     */   public String[] getDomains() {
/* 183 */     return this.server.getDomains();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotificationListener(ObjectName paramObjectName, NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws InstanceNotFoundException {
/* 191 */     this.server.addNotificationListener(paramObjectName, paramNotificationListener, paramNotificationFilter, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotificationListener(ObjectName paramObjectName1, ObjectName paramObjectName2, NotificationFilter paramNotificationFilter, Object paramObject) throws InstanceNotFoundException {
/* 198 */     this.server.addNotificationListener(paramObjectName1, paramObjectName2, paramNotificationFilter, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNotificationListener(ObjectName paramObjectName, NotificationListener paramNotificationListener) throws InstanceNotFoundException, ListenerNotFoundException {
/* 204 */     this.server.removeNotificationListener(paramObjectName, paramNotificationListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNotificationListener(ObjectName paramObjectName1, ObjectName paramObjectName2) throws InstanceNotFoundException, ListenerNotFoundException {
/* 211 */     this.server.removeNotificationListener(paramObjectName1, paramObjectName2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNotificationListener(ObjectName paramObjectName1, ObjectName paramObjectName2, NotificationFilter paramNotificationFilter, Object paramObject) throws InstanceNotFoundException, ListenerNotFoundException {
/* 219 */     this.server.removeNotificationListener(paramObjectName1, paramObjectName2, paramNotificationFilter, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNotificationListener(ObjectName paramObjectName, NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws InstanceNotFoundException, ListenerNotFoundException {
/* 227 */     this.server.removeNotificationListener(paramObjectName, paramNotificationListener, paramNotificationFilter, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MBeanInfo getMBeanInfo(ObjectName paramObjectName) throws InstanceNotFoundException, IntrospectionException, ReflectionException {
/* 233 */     return this.server.getMBeanInfo(paramObjectName);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInstanceOf(ObjectName paramObjectName, String paramString) throws InstanceNotFoundException {
/* 238 */     return this.server.isInstanceOf(paramObjectName, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ObjectInputStream deserialize(ObjectName paramObjectName, byte[] paramArrayOfbyte) throws InstanceNotFoundException, OperationsException {
/* 244 */     return this.server.deserialize(paramObjectName, paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ObjectInputStream deserialize(String paramString, byte[] paramArrayOfbyte) throws OperationsException, ReflectionException {
/* 251 */     return this.server.deserialize(paramString, paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ObjectInputStream deserialize(String paramString, ObjectName paramObjectName, byte[] paramArrayOfbyte) throws InstanceNotFoundException, OperationsException, ReflectionException {
/* 260 */     return this.server.deserialize(paramString, paramObjectName, paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */   
/*     */   public ClassLoader getClassLoaderFor(ObjectName paramObjectName) throws InstanceNotFoundException {
/* 265 */     return this.server.getClassLoaderFor(paramObjectName);
/*     */   }
/*     */ 
/*     */   
/*     */   public ClassLoader getClassLoader(ObjectName paramObjectName) throws InstanceNotFoundException {
/* 270 */     return this.server.getClassLoader(paramObjectName);
/*     */   }
/*     */   
/*     */   public ClassLoaderRepository getClassLoaderRepository() {
/* 274 */     return this.server.getClassLoaderRepository();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\management\jmx\MBeanServerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */