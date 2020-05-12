/*     */ package com.sun.jmx.snmp.agent;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpEngine;
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpPdu;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.SnmpVarBind;
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.MBeanRegistration;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.ReflectionException;
/*     */ import javax.management.ServiceNotFoundException;
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
/*     */ public abstract class SnmpMibAgent
/*     */   implements SnmpMibAgentMBean, MBeanRegistration, Serializable
/*     */ {
/*     */   protected String mibName;
/*     */   protected MBeanServer server;
/*     */   private ObjectName adaptorName;
/*     */   private transient SnmpMibHandler adaptor;
/*     */   
/*     */   public abstract void init() throws IllegalAccessException;
/*     */   
/*     */   public abstract ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception;
/*     */   
/*     */   public void postRegister(Boolean paramBoolean) {}
/*     */   
/*     */   public void preDeregister() throws Exception {}
/*     */   
/*     */   public void postDeregister() {}
/*     */   
/*     */   public abstract void get(SnmpMibRequest paramSnmpMibRequest) throws SnmpStatusException;
/*     */   
/*     */   public abstract void getNext(SnmpMibRequest paramSnmpMibRequest) throws SnmpStatusException;
/*     */   
/*     */   public abstract void getBulk(SnmpMibRequest paramSnmpMibRequest, int paramInt1, int paramInt2) throws SnmpStatusException;
/*     */   
/*     */   public abstract void set(SnmpMibRequest paramSnmpMibRequest) throws SnmpStatusException;
/*     */   
/*     */   public abstract void check(SnmpMibRequest paramSnmpMibRequest) throws SnmpStatusException;
/*     */   
/*     */   public abstract long[] getRootOid();
/*     */   
/*     */   public MBeanServer getMBeanServer() {
/* 238 */     return this.server;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpMibHandler getSnmpAdaptor() {
/* 249 */     return this.adaptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSnmpAdaptor(SnmpMibHandler paramSnmpMibHandler) {
/* 260 */     if (this.adaptor != null) {
/* 261 */       this.adaptor.removeMib(this);
/*     */     }
/* 263 */     this.adaptor = paramSnmpMibHandler;
/* 264 */     if (this.adaptor != null) {
/* 265 */       this.adaptor.addMib(this);
/*     */     }
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
/*     */   public void setSnmpAdaptor(SnmpMibHandler paramSnmpMibHandler, SnmpOid[] paramArrayOfSnmpOid) {
/* 281 */     if (this.adaptor != null) {
/* 282 */       this.adaptor.removeMib(this);
/*     */     }
/* 284 */     this.adaptor = paramSnmpMibHandler;
/* 285 */     if (this.adaptor != null) {
/* 286 */       this.adaptor.addMib(this, paramArrayOfSnmpOid);
/*     */     }
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
/*     */   public void setSnmpAdaptor(SnmpMibHandler paramSnmpMibHandler, String paramString) {
/* 304 */     if (this.adaptor != null) {
/* 305 */       this.adaptor.removeMib(this, paramString);
/*     */     }
/* 307 */     this.adaptor = paramSnmpMibHandler;
/* 308 */     if (this.adaptor != null) {
/* 309 */       this.adaptor.addMib(this, paramString);
/*     */     }
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
/*     */   public void setSnmpAdaptor(SnmpMibHandler paramSnmpMibHandler, String paramString, SnmpOid[] paramArrayOfSnmpOid) {
/* 328 */     if (this.adaptor != null) {
/* 329 */       this.adaptor.removeMib(this, paramString);
/*     */     }
/* 331 */     this.adaptor = paramSnmpMibHandler;
/* 332 */     if (this.adaptor != null) {
/* 333 */       this.adaptor.addMib(this, paramString, paramArrayOfSnmpOid);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName getSnmpAdaptorName() {
/* 345 */     return this.adaptorName;
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
/*     */ 
/*     */   
/*     */   public void setSnmpAdaptorName(ObjectName paramObjectName) throws InstanceNotFoundException, ServiceNotFoundException {
/* 365 */     if (this.server == null) {
/* 366 */       throw new ServiceNotFoundException(this.mibName + " is not registered in the MBean server");
/*     */     }
/*     */ 
/*     */     
/* 370 */     if (this.adaptor != null) {
/* 371 */       this.adaptor.removeMib(this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 376 */     Object[] arrayOfObject = { this };
/* 377 */     String[] arrayOfString = { "com.sun.jmx.snmp.agent.SnmpMibAgent" };
/*     */     try {
/* 379 */       this.adaptor = (SnmpMibHandler)this.server.invoke(paramObjectName, "addMib", arrayOfObject, arrayOfString);
/*     */     }
/* 381 */     catch (InstanceNotFoundException instanceNotFoundException) {
/* 382 */       throw new InstanceNotFoundException(paramObjectName.toString());
/* 383 */     } catch (ReflectionException reflectionException) {
/* 384 */       throw new ServiceNotFoundException(paramObjectName.toString());
/* 385 */     } catch (MBeanException mBeanException) {}
/*     */ 
/*     */ 
/*     */     
/* 389 */     this.adaptorName = paramObjectName;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSnmpAdaptorName(ObjectName paramObjectName, SnmpOid[] paramArrayOfSnmpOid) throws InstanceNotFoundException, ServiceNotFoundException {
/* 411 */     if (this.server == null) {
/* 412 */       throw new ServiceNotFoundException(this.mibName + " is not registered in the MBean server");
/*     */     }
/*     */ 
/*     */     
/* 416 */     if (this.adaptor != null) {
/* 417 */       this.adaptor.removeMib(this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 422 */     Object[] arrayOfObject = { this, paramArrayOfSnmpOid };
/*     */     
/* 424 */     String[] arrayOfString = { "com.sun.jmx.snmp.agent.SnmpMibAgent", paramArrayOfSnmpOid.getClass().getName() };
/*     */     try {
/* 426 */       this.adaptor = (SnmpMibHandler)this.server.invoke(paramObjectName, "addMib", arrayOfObject, arrayOfString);
/*     */     }
/* 428 */     catch (InstanceNotFoundException instanceNotFoundException) {
/* 429 */       throw new InstanceNotFoundException(paramObjectName.toString());
/* 430 */     } catch (ReflectionException reflectionException) {
/* 431 */       throw new ServiceNotFoundException(paramObjectName.toString());
/* 432 */     } catch (MBeanException mBeanException) {}
/*     */ 
/*     */ 
/*     */     
/* 436 */     this.adaptorName = paramObjectName;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSnmpAdaptorName(ObjectName paramObjectName, String paramString) throws InstanceNotFoundException, ServiceNotFoundException {
/* 457 */     if (this.server == null) {
/* 458 */       throw new ServiceNotFoundException(this.mibName + " is not registered in the MBean server");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 463 */     if (this.adaptor != null) {
/* 464 */       this.adaptor.removeMib(this, paramString);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 469 */     Object[] arrayOfObject = { this, paramString };
/* 470 */     String[] arrayOfString = { "com.sun.jmx.snmp.agent.SnmpMibAgent", "java.lang.String" };
/*     */     try {
/* 472 */       this.adaptor = (SnmpMibHandler)this.server.invoke(paramObjectName, "addMib", arrayOfObject, arrayOfString);
/*     */     }
/* 474 */     catch (InstanceNotFoundException instanceNotFoundException) {
/* 475 */       throw new InstanceNotFoundException(paramObjectName.toString());
/* 476 */     } catch (ReflectionException reflectionException) {
/* 477 */       throw new ServiceNotFoundException(paramObjectName.toString());
/* 478 */     } catch (MBeanException mBeanException) {}
/*     */ 
/*     */ 
/*     */     
/* 482 */     this.adaptorName = paramObjectName;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSnmpAdaptorName(ObjectName paramObjectName, String paramString, SnmpOid[] paramArrayOfSnmpOid) throws InstanceNotFoundException, ServiceNotFoundException {
/* 506 */     if (this.server == null) {
/* 507 */       throw new ServiceNotFoundException(this.mibName + " is not registered in the MBean server");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 512 */     if (this.adaptor != null) {
/* 513 */       this.adaptor.removeMib(this, paramString);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 518 */     Object[] arrayOfObject = { this, paramString, paramArrayOfSnmpOid };
/* 519 */     String[] arrayOfString = { "com.sun.jmx.snmp.agent.SnmpMibAgent", "java.lang.String", paramArrayOfSnmpOid.getClass().getName() };
/*     */     try {
/* 521 */       this.adaptor = (SnmpMibHandler)this.server.invoke(paramObjectName, "addMib", arrayOfObject, arrayOfString);
/*     */     }
/* 523 */     catch (InstanceNotFoundException instanceNotFoundException) {
/* 524 */       throw new InstanceNotFoundException(paramObjectName.toString());
/* 525 */     } catch (ReflectionException reflectionException) {
/* 526 */       throw new ServiceNotFoundException(paramObjectName.toString());
/* 527 */     } catch (MBeanException mBeanException) {}
/*     */ 
/*     */ 
/*     */     
/* 531 */     this.adaptorName = paramObjectName;
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
/*     */   public boolean getBindingState() {
/* 545 */     if (this.adaptor == null) {
/* 546 */       return false;
/*     */     }
/* 548 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMibName() {
/* 558 */     return this.mibName;
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
/*     */ 
/*     */   
/*     */   public static SnmpMibRequest newMibRequest(SnmpPdu paramSnmpPdu, Vector<SnmpVarBind> paramVector, int paramInt, Object paramObject) {
/* 578 */     return new SnmpMibRequestImpl(null, paramSnmpPdu, paramVector, paramInt, paramObject, null, 0, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 585 */         getSecurityModel(paramInt), null, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SnmpMibRequest newMibRequest(SnmpEngine paramSnmpEngine, SnmpPdu paramSnmpPdu, Vector<SnmpVarBind> paramVector, int paramInt1, Object paramObject, String paramString, int paramInt2, int paramInt3, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 611 */     return new SnmpMibRequestImpl(paramSnmpEngine, paramSnmpPdu, paramVector, paramInt1, paramObject, paramString, paramInt2, paramInt3, paramArrayOfbyte1, paramArrayOfbyte2);
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
/*     */   void getBulkWithGetNext(SnmpMibRequest paramSnmpMibRequest, int paramInt1, int paramInt2) throws SnmpStatusException {
/* 650 */     Vector<SnmpVarBind> vector = paramSnmpMibRequest.getSubList();
/*     */ 
/*     */     
/* 653 */     int i = vector.size();
/* 654 */     int j = Math.max(Math.min(paramInt1, i), 0);
/* 655 */     int k = Math.max(paramInt2, 0);
/* 656 */     int m = i - j;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 662 */     if (i != 0) {
/*     */ 
/*     */ 
/*     */       
/* 666 */       getNext(paramSnmpMibRequest);
/*     */ 
/*     */ 
/*     */       
/* 670 */       Vector<SnmpVarBind> vector1 = splitFrom(vector, j);
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
/* 681 */       SnmpMibRequestImpl snmpMibRequestImpl = new SnmpMibRequestImpl(paramSnmpMibRequest.getEngine(), paramSnmpMibRequest.getPdu(), vector1, 1, paramSnmpMibRequest.getUserData(), paramSnmpMibRequest.getPrincipal(), paramSnmpMibRequest.getSecurityLevel(), paramSnmpMibRequest.getSecurityModel(), paramSnmpMibRequest.getContextName(), paramSnmpMibRequest.getAccessContextName());
/* 682 */       for (byte b = 2; b <= k; b++) {
/* 683 */         getNext(snmpMibRequestImpl);
/* 684 */         concatVector(paramSnmpMibRequest, vector1);
/*     */       } 
/*     */     } 
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
/*     */   private Vector<SnmpVarBind> splitFrom(Vector<SnmpVarBind> paramVector, int paramInt) {
/* 703 */     int i = paramVector.size();
/* 704 */     Vector<SnmpVarBind> vector = new Vector(i - paramInt);
/* 705 */     int j = paramInt;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 711 */     for (Enumeration<SnmpVarBind> enumeration = paramVector.elements(); enumeration.hasMoreElements(); j--) {
/* 712 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/* 713 */       if (j <= 0)
/*     */       {
/* 715 */         vector.addElement(new SnmpVarBind(snmpVarBind.oid, snmpVarBind.value)); } 
/*     */     } 
/* 717 */     return vector;
/*     */   }
/*     */   
/*     */   private void concatVector(SnmpMibRequest paramSnmpMibRequest, Vector<SnmpVarBind> paramVector) {
/* 721 */     for (Enumeration<SnmpVarBind> enumeration = paramVector.elements(); enumeration.hasMoreElements(); ) {
/* 722 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/*     */ 
/*     */       
/* 725 */       paramSnmpMibRequest.addVarBind(new SnmpVarBind(snmpVarBind.oid, snmpVarBind.value));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getSecurityModel(int paramInt) {
/* 730 */     switch (paramInt) {
/*     */       case 0:
/* 732 */         return 1;
/*     */     } 
/* 734 */     return 2;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpMibAgent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */