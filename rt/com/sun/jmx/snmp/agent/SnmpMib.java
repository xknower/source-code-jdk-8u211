/*     */ package com.sun.jmx.snmp.agent;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.SnmpVarBind;
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.MBeanRegistrationException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.MalformedObjectNameException;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.ObjectName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SnmpMib
/*     */   extends SnmpMibAgent
/*     */   implements Serializable
/*     */ {
/*  69 */   protected SnmpMibOid root = new SnmpMibOid();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getGroupOid(String paramString1, String paramString2) {
/* 105 */     return paramString2;
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
/*     */   protected ObjectName getGroupObjectName(String paramString1, String paramString2, String paramString3) throws MalformedObjectNameException {
/* 141 */     return new ObjectName(paramString3);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerGroupNode(String paramString1, String paramString2, ObjectName paramObjectName, SnmpMibNode paramSnmpMibNode, Object paramObject, MBeanServer paramMBeanServer) throws NotCompliantMBeanException, MBeanRegistrationException, InstanceAlreadyExistsException, IllegalAccessException {
/* 188 */     this.root.registerNode(paramString2, paramSnmpMibNode);
/* 189 */     if (paramMBeanServer != null && paramObjectName != null && paramObject != null) {
/* 190 */       paramMBeanServer.registerMBean(paramObject, paramObjectName);
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
/*     */   public abstract void registerTableMeta(String paramString, SnmpMibTable paramSnmpMibTable);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract SnmpMibTable getRegisteredTableMeta(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void get(SnmpMibRequest paramSnmpMibRequest) throws SnmpStatusException {
/* 247 */     SnmpRequestTree snmpRequestTree = getHandlers(paramSnmpMibRequest, false, false, 160);
/*     */     
/* 249 */     SnmpRequestTree.Handler handler = null;
/* 250 */     SnmpMibNode snmpMibNode = null;
/*     */     
/* 252 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 253 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class.getName(), "get", "Processing handlers for GET... ");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     for (Enumeration<SnmpRequestTree.Handler> enumeration = snmpRequestTree.getHandlers(); enumeration.hasMoreElements(); ) {
/* 260 */       handler = enumeration.nextElement();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 265 */       snmpMibNode = snmpRequestTree.getMetaNode(handler);
/*     */ 
/*     */       
/* 268 */       int i = snmpRequestTree.getOidDepth(handler);
/*     */       
/* 270 */       Enumeration<SnmpMibSubRequest> enumeration1 = snmpRequestTree.getSubRequests(handler);
/* 271 */       while (enumeration1.hasMoreElements())
/*     */       {
/*     */         
/* 274 */         snmpMibNode.get(enumeration1.nextElement(), i);
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
/*     */   public void set(SnmpMibRequest paramSnmpMibRequest) throws SnmpStatusException {
/* 289 */     SnmpRequestTree snmpRequestTree = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 295 */     if (paramSnmpMibRequest instanceof SnmpMibRequestImpl) {
/* 296 */       snmpRequestTree = ((SnmpMibRequestImpl)paramSnmpMibRequest).getRequestTree();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     if (snmpRequestTree == null) snmpRequestTree = getHandlers(paramSnmpMibRequest, false, true, 163); 
/* 305 */     snmpRequestTree.switchCreationFlag(false);
/* 306 */     snmpRequestTree.setPduType(163);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 311 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 312 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class.getName(), "set", "Processing handlers for SET... ");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 318 */     for (Enumeration<SnmpRequestTree.Handler> enumeration = snmpRequestTree.getHandlers(); enumeration.hasMoreElements(); ) {
/* 319 */       SnmpRequestTree.Handler handler = enumeration.nextElement();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 324 */       SnmpMibNode snmpMibNode = snmpRequestTree.getMetaNode(handler);
/*     */ 
/*     */       
/* 327 */       int i = snmpRequestTree.getOidDepth(handler);
/*     */       
/* 329 */       Enumeration<SnmpMibSubRequest> enumeration1 = snmpRequestTree.getSubRequests(handler);
/* 330 */       while (enumeration1.hasMoreElements())
/*     */       {
/*     */         
/* 333 */         snmpMibNode.set(enumeration1.nextElement(), i);
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
/*     */   public void check(SnmpMibRequest paramSnmpMibRequest) throws SnmpStatusException {
/* 353 */     SnmpRequestTree snmpRequestTree = getHandlers(paramSnmpMibRequest, true, true, 253);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 359 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class.getName(), "check", "Processing handlers for CHECK... ");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 365 */     for (Enumeration<SnmpRequestTree.Handler> enumeration = snmpRequestTree.getHandlers(); enumeration.hasMoreElements(); ) {
/* 366 */       SnmpRequestTree.Handler handler = enumeration.nextElement();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 371 */       SnmpMibNode snmpMibNode = snmpRequestTree.getMetaNode(handler);
/*     */ 
/*     */       
/* 374 */       int i = snmpRequestTree.getOidDepth(handler);
/*     */       
/* 376 */       Enumeration<SnmpMibSubRequest> enumeration1 = snmpRequestTree.getSubRequests(handler);
/* 377 */       while (enumeration1.hasMoreElements())
/*     */       {
/*     */         
/* 380 */         snmpMibNode.check(enumeration1.nextElement(), i);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 387 */     if (paramSnmpMibRequest instanceof SnmpMibRequestImpl) {
/* 388 */       ((SnmpMibRequestImpl)paramSnmpMibRequest).setRequestTree(snmpRequestTree);
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
/*     */   public void getNext(SnmpMibRequest paramSnmpMibRequest) throws SnmpStatusException {
/* 404 */     SnmpRequestTree snmpRequestTree = getGetNextHandlers(paramSnmpMibRequest);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 409 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 410 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class.getName(), "getNext", "Processing handlers for GET-NEXT... ");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 415 */     for (Enumeration<SnmpRequestTree.Handler> enumeration = snmpRequestTree.getHandlers(); enumeration.hasMoreElements(); ) {
/* 416 */       SnmpRequestTree.Handler handler = enumeration.nextElement();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 421 */       SnmpMibNode snmpMibNode = snmpRequestTree.getMetaNode(handler);
/*     */ 
/*     */       
/* 424 */       int i = snmpRequestTree.getOidDepth(handler);
/*     */       
/* 426 */       Enumeration<SnmpMibSubRequest> enumeration1 = snmpRequestTree.getSubRequests(handler);
/* 427 */       while (enumeration1.hasMoreElements())
/*     */       {
/*     */         
/* 430 */         snmpMibNode.get(enumeration1.nextElement(), i);
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
/*     */   public void getBulk(SnmpMibRequest paramSnmpMibRequest, int paramInt1, int paramInt2) throws SnmpStatusException {
/* 449 */     getBulkWithGetNext(paramSnmpMibRequest, paramInt1, paramInt2);
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
/*     */   public long[] getRootOid() {
/* 463 */     if (this.rootOid == null) {
/* 464 */       Vector<Integer> vector = new Vector(10);
/*     */ 
/*     */ 
/*     */       
/* 468 */       this.root.getRootOid(vector);
/*     */ 
/*     */ 
/*     */       
/* 472 */       this.rootOid = new long[vector.size()];
/* 473 */       byte b = 0;
/* 474 */       for (Enumeration<Integer> enumeration = vector.elements(); enumeration.hasMoreElements(); ) {
/* 475 */         Integer integer = enumeration.nextElement();
/* 476 */         this.rootOid[b++] = integer.longValue();
/*     */       } 
/*     */     } 
/* 479 */     return (long[])this.rootOid.clone();
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
/*     */   private SnmpRequestTree getHandlers(SnmpMibRequest paramSnmpMibRequest, boolean paramBoolean1, boolean paramBoolean2, int paramInt) throws SnmpStatusException {
/* 507 */     SnmpRequestTree snmpRequestTree = new SnmpRequestTree(paramSnmpMibRequest, paramBoolean1, paramInt);
/*     */ 
/*     */     
/* 510 */     byte b = 0;
/*     */     
/* 512 */     int i = paramSnmpMibRequest.getVersion();
/*     */ 
/*     */     
/* 515 */     for (Enumeration<SnmpVarBind> enumeration = paramSnmpMibRequest.getElements(); enumeration.hasMoreElements(); b++) {
/*     */       
/* 517 */       SnmpVarBind snmpVarBind = enumeration.nextElement();
/*     */ 
/*     */       
/*     */       try {
/* 521 */         this.root.findHandlingNode(snmpVarBind, snmpVarBind.oid.longValue(false), 0, snmpRequestTree);
/*     */       }
/* 523 */       catch (SnmpStatusException snmpStatusException) {
/*     */         
/* 525 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 526 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class
/* 527 */               .getName(), "getHandlers", "Couldn't find a handling node for " + snmpVarBind.oid
/*     */ 
/*     */               
/* 530 */               .toString());
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 536 */         if (i == 0) {
/*     */           
/* 538 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 539 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class
/* 540 */                 .getName(), "getHandlers", "\tV1: Throwing exception");
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 547 */           SnmpStatusException snmpStatusException1 = new SnmpStatusException(snmpStatusException, b + 1);
/*     */           
/* 549 */           snmpStatusException1.initCause(snmpStatusException);
/* 550 */           throw snmpStatusException1;
/* 551 */         }  if (paramInt == 253 || paramInt == 163) {
/*     */ 
/*     */           
/* 554 */           int k = SnmpRequestTree.mapSetException(snmpStatusException.getStatus(), i);
/*     */           
/* 556 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 557 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class
/* 558 */                 .getName(), "getHandlers", "\tSET: Throwing exception");
/*     */           }
/*     */ 
/*     */           
/* 562 */           SnmpStatusException snmpStatusException1 = new SnmpStatusException(k, b + 1);
/*     */           
/* 564 */           snmpStatusException1.initCause(snmpStatusException);
/* 565 */           throw snmpStatusException1;
/* 566 */         }  if (paramBoolean2) {
/*     */ 
/*     */           
/* 569 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 570 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class
/* 571 */                 .getName(), "getHandlers", "\tATOMIC: Throwing exception");
/*     */           }
/*     */ 
/*     */           
/* 575 */           SnmpStatusException snmpStatusException1 = new SnmpStatusException(snmpStatusException, b + 1);
/*     */           
/* 577 */           snmpStatusException1.initCause(snmpStatusException);
/* 578 */           throw snmpStatusException1;
/*     */         } 
/*     */ 
/*     */         
/* 582 */         int j = SnmpRequestTree.mapGetException(snmpStatusException.getStatus(), i);
/*     */         
/* 584 */         if (j == 224) {
/*     */           
/* 586 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 587 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class
/* 588 */                 .getName(), "getHandlers", "\tGET: Registering noSuchInstance");
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 593 */           snmpVarBind.value = SnmpVarBind.noSuchInstance;
/*     */         }
/* 595 */         else if (j == 225) {
/* 596 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 597 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class
/* 598 */                 .getName(), "getHandlers", "\tGET: Registering noSuchObject");
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 603 */           snmpVarBind.value = SnmpVarBind.noSuchObject;
/*     */         }
/*     */         else {
/*     */           
/* 607 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 608 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class
/* 609 */                 .getName(), "getHandlers", "\tGET: Registering global error: " + j);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 614 */           SnmpStatusException snmpStatusException1 = new SnmpStatusException(j, b + 1);
/*     */           
/* 616 */           snmpStatusException1.initCause(snmpStatusException);
/* 617 */           throw snmpStatusException1;
/*     */         } 
/*     */       } 
/*     */     } 
/* 621 */     return snmpRequestTree;
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
/*     */   private SnmpRequestTree getGetNextHandlers(SnmpMibRequest paramSnmpMibRequest) throws SnmpStatusException {
/* 640 */     SnmpRequestTree snmpRequestTree = new SnmpRequestTree(paramSnmpMibRequest, false, 161);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 645 */     snmpRequestTree.setGetNextFlag();
/*     */     
/* 647 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 648 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class.getName(), "getGetNextHandlers", "Received MIB request : " + paramSnmpMibRequest);
/*     */     }
/*     */     
/* 651 */     AcmChecker acmChecker = new AcmChecker(paramSnmpMibRequest);
/* 652 */     byte b = 0;
/* 653 */     SnmpVarBind snmpVarBind = null;
/* 654 */     int i = paramSnmpMibRequest.getVersion();
/* 655 */     Object object = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 661 */     for (Enumeration<SnmpVarBind> enumeration = paramSnmpMibRequest.getElements(); enumeration.hasMoreElements(); b++) {
/*     */       
/* 663 */       snmpVarBind = enumeration.nextElement();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 669 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 670 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class
/* 671 */               .getName(), "getGetNextHandlers", " Next OID of : " + snmpVarBind.oid);
/*     */         }
/*     */ 
/*     */         
/* 675 */         SnmpOid snmpOid = new SnmpOid(this.root.findNextHandlingNode(snmpVarBind, snmpVarBind.oid.longValue(false), 0, 0, snmpRequestTree, acmChecker));
/*     */ 
/*     */         
/* 678 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 679 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class
/* 680 */               .getName(), "getGetNextHandlers", " is : " + snmpOid);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 685 */         snmpVarBind.oid = snmpOid;
/* 686 */       } catch (SnmpStatusException snmpStatusException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 693 */         if (i == 0) {
/* 694 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 695 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class
/* 696 */                 .getName(), "getGetNextHandlers", "\tThrowing exception " + snmpStatusException
/*     */                 
/* 698 */                 .toString());
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 703 */           throw new SnmpStatusException(snmpStatusException, b + 1);
/*     */         } 
/* 705 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 706 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, SnmpMib.class
/* 707 */               .getName(), "getGetNextHandlers", "Exception : " + snmpStatusException
/*     */               
/* 709 */               .getStatus());
/*     */         }
/*     */         
/* 712 */         snmpVarBind.setSnmpValue(SnmpVarBind.endOfMibView);
/*     */       } 
/*     */     } 
/* 715 */     return snmpRequestTree;
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
/* 736 */   private transient long[] rootOid = null;
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpMib.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */