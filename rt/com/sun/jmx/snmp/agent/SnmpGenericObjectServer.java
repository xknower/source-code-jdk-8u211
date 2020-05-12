/*     */ package com.sun.jmx.snmp.agent;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.SnmpValue;
/*     */ import com.sun.jmx.snmp.SnmpVarBind;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import javax.management.Attribute;
/*     */ import javax.management.AttributeList;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.InvalidAttributeValueException;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.MBeanRegistrationException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.ReflectionException;
/*     */ import javax.management.RuntimeOperationsException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SnmpGenericObjectServer
/*     */ {
/*     */   protected final MBeanServer server;
/*     */   
/*     */   public SnmpGenericObjectServer(MBeanServer paramMBeanServer) {
/* 102 */     this.server = paramMBeanServer;
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
/*     */ 
/*     */   
/*     */   public void get(SnmpGenericMetaServer paramSnmpGenericMetaServer, ObjectName paramObjectName, SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException {
/*     */     AttributeList attributeList;
/* 144 */     int i = paramSnmpMibSubRequest.getSize();
/* 145 */     Object object = paramSnmpMibSubRequest.getUserData();
/* 146 */     String[] arrayOfString = new String[i];
/* 147 */     SnmpVarBind[] arrayOfSnmpVarBind = new SnmpVarBind[i];
/* 148 */     long[] arrayOfLong = new long[i];
/* 149 */     byte b1 = 0;
/*     */     Enumeration<SnmpVarBind> enumeration;
/* 151 */     for (enumeration = paramSnmpMibSubRequest.getElements(); enumeration.hasMoreElements(); ) {
/* 152 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/*     */       try {
/* 154 */         long l = snmpVarBind.oid.getOidArc(paramInt);
/* 155 */         arrayOfString[b1] = paramSnmpGenericMetaServer.getAttributeName(l);
/* 156 */         arrayOfSnmpVarBind[b1] = snmpVarBind;
/* 157 */         arrayOfLong[b1] = l;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 163 */         paramSnmpGenericMetaServer.checkGetAccess(l, object);
/*     */ 
/*     */         
/* 166 */         b1++;
/* 167 */       } catch (SnmpStatusException snmpStatusException) {
/*     */ 
/*     */         
/* 170 */         paramSnmpMibSubRequest.registerGetException(snmpVarBind, snmpStatusException);
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     enumeration = null;
/* 175 */     char c = 'à';
/*     */     
/*     */     try {
/* 178 */       attributeList = this.server.getAttributes(paramObjectName, arrayOfString);
/* 179 */     } catch (InstanceNotFoundException instanceNotFoundException) {
/*     */ 
/*     */       
/* 182 */       attributeList = new AttributeList();
/* 183 */     } catch (ReflectionException reflectionException) {
/*     */ 
/*     */       
/* 186 */       attributeList = new AttributeList();
/* 187 */     } catch (Exception exception) {
/* 188 */       attributeList = new AttributeList();
/*     */     } 
/*     */ 
/*     */     
/* 192 */     Iterator<Attribute> iterator = attributeList.iterator();
/*     */     
/* 194 */     for (byte b2 = 0; b2 < b1; b2++) {
/* 195 */       if (!iterator.hasNext()) {
/*     */ 
/*     */         
/* 198 */         SnmpStatusException snmpStatusException = new SnmpStatusException(c);
/*     */         
/* 200 */         paramSnmpMibSubRequest.registerGetException(arrayOfSnmpVarBind[b2], snmpStatusException);
/*     */       }
/*     */       else {
/*     */         
/* 204 */         Attribute attribute = iterator.next();
/*     */         
/* 206 */         while (b2 < b1 && !arrayOfString[b2].equals(attribute.getName())) {
/*     */ 
/*     */           
/* 209 */           SnmpStatusException snmpStatusException = new SnmpStatusException(c);
/*     */           
/* 211 */           paramSnmpMibSubRequest.registerGetException(arrayOfSnmpVarBind[b2], snmpStatusException);
/* 212 */           b2++;
/*     */         } 
/*     */         
/* 215 */         if (b2 == b1)
/*     */           break; 
/*     */         try {
/* 218 */           (arrayOfSnmpVarBind[b2])
/* 219 */             .value = paramSnmpGenericMetaServer.buildSnmpValue(arrayOfLong[b2], attribute.getValue());
/* 220 */         } catch (SnmpStatusException snmpStatusException) {
/* 221 */           paramSnmpMibSubRequest.registerGetException(arrayOfSnmpVarBind[b2], snmpStatusException);
/*     */         } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpValue get(SnmpGenericMetaServer paramSnmpGenericMetaServer, ObjectName paramObjectName, long paramLong, Object paramObject) throws SnmpStatusException {
/* 253 */     String str = paramSnmpGenericMetaServer.getAttributeName(paramLong);
/* 254 */     Object object = null;
/*     */     
/*     */     try {
/* 257 */       object = this.server.getAttribute(paramObjectName, str);
/* 258 */     } catch (MBeanException mBeanException) {
/* 259 */       Exception exception = mBeanException.getTargetException();
/* 260 */       if (exception instanceof SnmpStatusException)
/* 261 */         throw (SnmpStatusException)exception; 
/* 262 */       throw new SnmpStatusException(224);
/* 263 */     } catch (Exception exception) {
/* 264 */       throw new SnmpStatusException(224);
/*     */     } 
/*     */     
/* 267 */     return paramSnmpGenericMetaServer.buildSnmpValue(paramLong, object);
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
/*     */   public void set(SnmpGenericMetaServer paramSnmpGenericMetaServer, ObjectName paramObjectName, SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException {
/*     */     AttributeList attributeList2;
/* 307 */     int i = paramSnmpMibSubRequest.getSize();
/* 308 */     AttributeList attributeList1 = new AttributeList(i);
/* 309 */     String[] arrayOfString = new String[i];
/* 310 */     SnmpVarBind[] arrayOfSnmpVarBind = new SnmpVarBind[i];
/* 311 */     long[] arrayOfLong = new long[i];
/* 312 */     byte b1 = 0;
/*     */     
/* 314 */     for (Enumeration<SnmpVarBind> enumeration = paramSnmpMibSubRequest.getElements(); enumeration.hasMoreElements(); ) {
/* 315 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/*     */       try {
/* 317 */         long l = snmpVarBind.oid.getOidArc(paramInt);
/* 318 */         String str = paramSnmpGenericMetaServer.getAttributeName(l);
/*     */         
/* 320 */         Object object = paramSnmpGenericMetaServer.buildAttributeValue(l, snmpVarBind.value);
/* 321 */         Attribute attribute = new Attribute(str, object);
/* 322 */         attributeList1.add(attribute);
/* 323 */         arrayOfString[b1] = str;
/* 324 */         arrayOfSnmpVarBind[b1] = snmpVarBind;
/* 325 */         arrayOfLong[b1] = l;
/* 326 */         b1++;
/* 327 */       } catch (SnmpStatusException snmpStatusException) {
/* 328 */         paramSnmpMibSubRequest.registerSetException(snmpVarBind, snmpStatusException);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 333 */     byte b2 = 6;
/*     */     
/*     */     try {
/* 336 */       attributeList2 = this.server.setAttributes(paramObjectName, attributeList1);
/* 337 */     } catch (InstanceNotFoundException instanceNotFoundException) {
/* 338 */       attributeList2 = new AttributeList();
/* 339 */       b2 = 18;
/* 340 */     } catch (ReflectionException reflectionException) {
/* 341 */       b2 = 18;
/* 342 */       attributeList2 = new AttributeList();
/* 343 */     } catch (Exception exception) {
/* 344 */       attributeList2 = new AttributeList();
/*     */     } 
/*     */     
/* 347 */     Iterator<Attribute> iterator = attributeList2.iterator();
/*     */     
/* 349 */     for (byte b3 = 0; b3 < b1; b3++) {
/* 350 */       if (!iterator.hasNext()) {
/* 351 */         SnmpStatusException snmpStatusException = new SnmpStatusException(b2);
/*     */         
/* 353 */         paramSnmpMibSubRequest.registerSetException(arrayOfSnmpVarBind[b3], snmpStatusException);
/*     */       }
/*     */       else {
/*     */         
/* 357 */         Attribute attribute = iterator.next();
/*     */         
/* 359 */         while (b3 < b1 && !arrayOfString[b3].equals(attribute.getName())) {
/* 360 */           SnmpStatusException snmpStatusException = new SnmpStatusException(6);
/*     */           
/* 362 */           paramSnmpMibSubRequest.registerSetException(arrayOfSnmpVarBind[b3], snmpStatusException);
/* 363 */           b3++;
/*     */         } 
/*     */         
/* 366 */         if (b3 == b1)
/*     */           break; 
/*     */         try {
/* 369 */           (arrayOfSnmpVarBind[b3])
/* 370 */             .value = paramSnmpGenericMetaServer.buildSnmpValue(arrayOfLong[b3], attribute.getValue());
/* 371 */         } catch (SnmpStatusException snmpStatusException) {
/* 372 */           paramSnmpMibSubRequest.registerSetException(arrayOfSnmpVarBind[b3], snmpStatusException);
/*     */         } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpValue set(SnmpGenericMetaServer paramSnmpGenericMetaServer, ObjectName paramObjectName, SnmpValue paramSnmpValue, long paramLong, Object paramObject) throws SnmpStatusException {
/* 404 */     String str = paramSnmpGenericMetaServer.getAttributeName(paramLong);
/*     */     
/* 406 */     Object object1 = paramSnmpGenericMetaServer.buildAttributeValue(paramLong, paramSnmpValue);
/* 407 */     Attribute attribute = new Attribute(str, object1);
/*     */     
/* 409 */     Object object2 = null;
/*     */     
/*     */     try {
/* 412 */       this.server.setAttribute(paramObjectName, attribute);
/* 413 */       object2 = this.server.getAttribute(paramObjectName, str);
/* 414 */     } catch (InvalidAttributeValueException invalidAttributeValueException) {
/* 415 */       throw new SnmpStatusException(10);
/*     */     }
/* 417 */     catch (InstanceNotFoundException instanceNotFoundException) {
/* 418 */       throw new SnmpStatusException(18);
/*     */     }
/* 420 */     catch (ReflectionException reflectionException) {
/* 421 */       throw new SnmpStatusException(18);
/*     */     }
/* 423 */     catch (MBeanException mBeanException) {
/* 424 */       Exception exception = mBeanException.getTargetException();
/* 425 */       if (exception instanceof SnmpStatusException)
/* 426 */         throw (SnmpStatusException)exception; 
/* 427 */       throw new SnmpStatusException(6);
/*     */     }
/* 429 */     catch (Exception exception) {
/* 430 */       throw new SnmpStatusException(6);
/*     */     } 
/*     */ 
/*     */     
/* 434 */     return paramSnmpGenericMetaServer.buildSnmpValue(paramLong, object2);
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
/*     */   public void check(SnmpGenericMetaServer paramSnmpGenericMetaServer, ObjectName paramObjectName, SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException {
/* 469 */     Object object = paramSnmpMibSubRequest.getUserData();
/*     */     
/* 471 */     for (Enumeration<SnmpVarBind> enumeration = paramSnmpMibSubRequest.getElements(); enumeration.hasMoreElements(); ) {
/* 472 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/*     */       try {
/* 474 */         long l = snmpVarBind.oid.getOidArc(paramInt);
/*     */         
/* 476 */         check(paramSnmpGenericMetaServer, paramObjectName, snmpVarBind.value, l, object);
/* 477 */       } catch (SnmpStatusException snmpStatusException) {
/* 478 */         paramSnmpMibSubRequest.registerCheckException(snmpVarBind, snmpStatusException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void check(SnmpGenericMetaServer paramSnmpGenericMetaServer, ObjectName paramObjectName, SnmpValue paramSnmpValue, long paramLong, Object paramObject) throws SnmpStatusException {
/* 519 */     paramSnmpGenericMetaServer.checkSetAccess(paramSnmpValue, paramLong, paramObject);
/*     */     try {
/* 521 */       String str = paramSnmpGenericMetaServer.getAttributeName(paramLong);
/* 522 */       Object object = paramSnmpGenericMetaServer.buildAttributeValue(paramLong, paramSnmpValue);
/* 523 */       Object[] arrayOfObject = new Object[1];
/* 524 */       String[] arrayOfString = new String[1];
/*     */       
/* 526 */       arrayOfObject[0] = object;
/* 527 */       arrayOfString[0] = object.getClass().getName();
/* 528 */       this.server.invoke(paramObjectName, "check" + str, arrayOfObject, arrayOfString);
/*     */     }
/* 530 */     catch (SnmpStatusException snmpStatusException) {
/* 531 */       throw snmpStatusException;
/*     */     }
/* 533 */     catch (InstanceNotFoundException instanceNotFoundException) {
/* 534 */       throw new SnmpStatusException(18);
/*     */     }
/* 536 */     catch (ReflectionException reflectionException) {
/*     */     
/* 538 */     } catch (MBeanException mBeanException) {
/* 539 */       Exception exception = mBeanException.getTargetException();
/* 540 */       if (exception instanceof SnmpStatusException)
/* 541 */         throw (SnmpStatusException)exception; 
/* 542 */       throw new SnmpStatusException(6);
/* 543 */     } catch (Exception exception) {
/* 544 */       throw new SnmpStatusException(6);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerTableEntry(SnmpMibTable paramSnmpMibTable, SnmpOid paramSnmpOid, ObjectName paramObjectName, Object paramObject) throws SnmpStatusException {
/* 552 */     if (paramObjectName == null) {
/* 553 */       throw new SnmpStatusException(18);
/*     */     }
/*     */     try {
/* 556 */       if (paramObject != null && !this.server.isRegistered(paramObjectName))
/* 557 */         this.server.registerMBean(paramObject, paramObjectName); 
/* 558 */     } catch (InstanceAlreadyExistsException instanceAlreadyExistsException) {
/* 559 */       throw new SnmpStatusException(18);
/*     */     }
/* 561 */     catch (MBeanRegistrationException mBeanRegistrationException) {
/* 562 */       throw new SnmpStatusException(6);
/* 563 */     } catch (NotCompliantMBeanException notCompliantMBeanException) {
/* 564 */       throw new SnmpStatusException(5);
/* 565 */     } catch (RuntimeOperationsException runtimeOperationsException) {
/* 566 */       throw new SnmpStatusException(5);
/* 567 */     } catch (Exception exception) {
/* 568 */       throw new SnmpStatusException(5);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpGenericObjectServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */