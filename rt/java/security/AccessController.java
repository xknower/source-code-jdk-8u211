/*     */ package java.security;
/*     */ 
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AccessController
/*     */ {
/*     */   @CallerSensitive
/*     */   public static native <T> T doPrivileged(PrivilegedAction<T> paramPrivilegedAction);
/*     */   
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivilegedWithCombiner(PrivilegedAction<T> paramPrivilegedAction) {
/* 327 */     AccessControlContext accessControlContext = getStackAccessControlContext();
/* 328 */     if (accessControlContext == null) {
/* 329 */       return doPrivileged(paramPrivilegedAction);
/*     */     }
/* 331 */     DomainCombiner domainCombiner = accessControlContext.getAssignedCombiner();
/* 332 */     return doPrivileged(paramPrivilegedAction, 
/* 333 */         preserveCombiner(domainCombiner, Reflection.getCallerClass()));
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
/*     */   @CallerSensitive
/*     */   public static native <T> T doPrivileged(PrivilegedAction<T> paramPrivilegedAction, AccessControlContext paramAccessControlContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivileged(PrivilegedAction<T> paramPrivilegedAction, AccessControlContext paramAccessControlContext, Permission... paramVarArgs) {
/* 423 */     AccessControlContext accessControlContext = getContext();
/* 424 */     if (paramVarArgs == null) {
/* 425 */       throw new NullPointerException("null permissions parameter");
/*     */     }
/* 427 */     Class<?> clazz = Reflection.getCallerClass();
/* 428 */     return doPrivileged(paramPrivilegedAction, createWrapper(null, clazz, accessControlContext, paramAccessControlContext, paramVarArgs));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivilegedWithCombiner(PrivilegedAction<T> paramPrivilegedAction, AccessControlContext paramAccessControlContext, Permission... paramVarArgs) {
/* 485 */     AccessControlContext accessControlContext = getContext();
/* 486 */     DomainCombiner domainCombiner = accessControlContext.getCombiner();
/* 487 */     if (domainCombiner == null && paramAccessControlContext != null) {
/* 488 */       domainCombiner = paramAccessControlContext.getCombiner();
/*     */     }
/* 490 */     if (paramVarArgs == null) {
/* 491 */       throw new NullPointerException("null permissions parameter");
/*     */     }
/* 493 */     Class<?> clazz = Reflection.getCallerClass();
/* 494 */     return doPrivileged(paramPrivilegedAction, createWrapper(domainCombiner, clazz, accessControlContext, paramAccessControlContext, paramVarArgs));
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
/*     */   @CallerSensitive
/*     */   public static native <T> T doPrivileged(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction) throws PrivilegedActionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivilegedWithCombiner(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction) throws PrivilegedActionException {
/* 563 */     AccessControlContext accessControlContext = getStackAccessControlContext();
/* 564 */     if (accessControlContext == null) {
/* 565 */       return doPrivileged(paramPrivilegedExceptionAction);
/*     */     }
/* 567 */     DomainCombiner domainCombiner = accessControlContext.getAssignedCombiner();
/* 568 */     return doPrivileged(paramPrivilegedExceptionAction, 
/* 569 */         preserveCombiner(domainCombiner, Reflection.getCallerClass()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AccessControlContext preserveCombiner(DomainCombiner paramDomainCombiner, Class<?> paramClass) {
/* 578 */     return createWrapper(paramDomainCombiner, paramClass, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AccessControlContext createWrapper(DomainCombiner paramDomainCombiner, Class<?> paramClass, AccessControlContext paramAccessControlContext1, AccessControlContext paramAccessControlContext2, Permission[] paramArrayOfPermission) {
/* 589 */     ProtectionDomain protectionDomain = getCallerPD(paramClass);
/*     */     
/* 591 */     if (paramAccessControlContext2 != null && !paramAccessControlContext2.isAuthorized() && 
/* 592 */       System.getSecurityManager() != null && 
/* 593 */       !protectionDomain.impliesCreateAccessControlContext()) {
/*     */       
/* 595 */       ProtectionDomain protectionDomain1 = new ProtectionDomain(null, null);
/* 596 */       return new AccessControlContext(new ProtectionDomain[] { protectionDomain1 });
/*     */     } 
/* 598 */     return new AccessControlContext(protectionDomain, paramDomainCombiner, paramAccessControlContext1, paramAccessControlContext2, paramArrayOfPermission);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtectionDomain getCallerPD(final Class<?> caller) {
/* 605 */     return doPrivileged(new PrivilegedAction<ProtectionDomain>() {
/*     */           public ProtectionDomain run() {
/* 607 */             return caller.getProtectionDomain();
/*     */           }
/*     */         });
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
/*     */ 
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public static native <T> T doPrivileged(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction, AccessControlContext paramAccessControlContext) throws PrivilegedActionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivileged(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction, AccessControlContext paramAccessControlContext, Permission... paramVarArgs) throws PrivilegedActionException {
/* 708 */     AccessControlContext accessControlContext = getContext();
/* 709 */     if (paramVarArgs == null) {
/* 710 */       throw new NullPointerException("null permissions parameter");
/*     */     }
/* 712 */     Class<?> clazz = Reflection.getCallerClass();
/* 713 */     return doPrivileged(paramPrivilegedExceptionAction, createWrapper(null, clazz, accessControlContext, paramAccessControlContext, paramVarArgs));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivilegedWithCombiner(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction, AccessControlContext paramAccessControlContext, Permission... paramVarArgs) throws PrivilegedActionException {
/* 773 */     AccessControlContext accessControlContext = getContext();
/* 774 */     DomainCombiner domainCombiner = accessControlContext.getCombiner();
/* 775 */     if (domainCombiner == null && paramAccessControlContext != null) {
/* 776 */       domainCombiner = paramAccessControlContext.getCombiner();
/*     */     }
/* 778 */     if (paramVarArgs == null) {
/* 779 */       throw new NullPointerException("null permissions parameter");
/*     */     }
/* 781 */     Class<?> clazz = Reflection.getCallerClass();
/* 782 */     return doPrivileged(paramPrivilegedExceptionAction, createWrapper(domainCombiner, clazz, accessControlContext, paramAccessControlContext, paramVarArgs));
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
/*     */   private static native AccessControlContext getStackAccessControlContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native AccessControlContext getInheritedAccessControlContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AccessControlContext getContext() {
/* 820 */     AccessControlContext accessControlContext = getStackAccessControlContext();
/* 821 */     if (accessControlContext == null)
/*     */     {
/*     */       
/* 824 */       return new AccessControlContext(null, true);
/*     */     }
/* 826 */     return accessControlContext.optimize();
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
/*     */   public static void checkPermission(Permission paramPermission) throws AccessControlException {
/* 854 */     if (paramPermission == null) {
/* 855 */       throw new NullPointerException("permission can't be null");
/*     */     }
/*     */     
/* 858 */     AccessControlContext accessControlContext1 = getStackAccessControlContext();
/*     */     
/* 860 */     if (accessControlContext1 == null) {
/* 861 */       Debug debug = AccessControlContext.getDebug();
/* 862 */       int i = 0;
/* 863 */       if (debug != null) {
/* 864 */         i = !Debug.isOn("codebase=") ? 1 : 0;
/* 865 */         i &= (!Debug.isOn("permission=") || 
/* 866 */           Debug.isOn("permission=" + paramPermission.getClass().getCanonicalName())) ? 1 : 0;
/*     */       } 
/*     */       
/* 869 */       if (i != 0 && Debug.isOn("stack")) {
/* 870 */         Thread.dumpStack();
/*     */       }
/*     */       
/* 873 */       if (i != 0 && Debug.isOn("domain")) {
/* 874 */         debug.println("domain (context is null)");
/*     */       }
/*     */       
/* 877 */       if (i != 0) {
/* 878 */         debug.println("access allowed " + paramPermission);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 883 */     AccessControlContext accessControlContext2 = accessControlContext1.optimize();
/* 884 */     accessControlContext2.checkPermission(paramPermission);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\AccessController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */