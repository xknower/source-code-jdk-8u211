/*     */ package sun.management;
/*     */ 
/*     */ import java.lang.management.PlatformManagedObject;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import jdk.internal.cmm.SystemResourcePressureImpl;
/*     */ import jdk.management.cmm.SystemResourcePressureMXBean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ExtendedPlatformComponent
/*     */ {
/*  21 */   private static SystemResourcePressureMXBean cmmBeanImpl = null;
/*     */ 
/*     */   
/*     */   private static synchronized SystemResourcePressureMXBean getCMMBean() {
/*  25 */     if (cmmBeanImpl == null) {
/*  26 */       cmmBeanImpl = new SystemResourcePressureImpl();
/*     */     }
/*  28 */     return cmmBeanImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<? extends PlatformManagedObject> getMXBeans() {
/*  36 */     if (shouldRegisterCMMBean()) {
/*  37 */       return Collections.singletonList(getCMMBean());
/*     */     }
/*  39 */     return Collections.emptyList();
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
/*     */   public static <T extends PlatformManagedObject> T getMXBean(Class<T> paramClass) {
/*  51 */     if (paramClass != null && "jdk.management.cmm.SystemResourcePressureMXBean"
/*  52 */       .equals(paramClass
/*  53 */         .getName())) {
/*     */       
/*  55 */       if (isUnlockCommercialFeaturesEnabled()) {
/*  56 */         return paramClass.cast(getCMMBean());
/*     */       }
/*  58 */       throw new IllegalArgumentException("Cooperative Memory Management is a commercial feature which must be unlocked before being used.  To learn more about commercial features and how to unlock them visit http://www.oracle.com/technetwork/java/javaseproducts/");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     return null;
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
/*     */   private static boolean shouldRegisterCMMBean() {
/*  82 */     if (!isUnlockCommercialFeaturesEnabled()) {
/*  83 */       return false;
/*     */     }
/*     */     
/*  86 */     boolean bool = false;
/*  87 */     Class<?> clazz = null;
/*     */     
/*     */     try {
/*  90 */       ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/*  91 */       if (classLoader == null) return false; 
/*  92 */       classLoader = classLoader.getParent();
/*  93 */       clazz = Class.forName("com.oracle.exalogic.ExaManager", false, classLoader);
/*     */       
/*  95 */       Object object = clazz.getMethod("instance", new Class[0]).invoke(null, new Object[0]);
/*  96 */       if (object != null) {
/*     */         
/*  98 */         Object object1 = clazz.getMethod("isExalogicSystem", new Class[0]).invoke(object, new Object[0]);
/*  99 */         bool = ((Boolean)object1).booleanValue();
/*     */       } 
/* 101 */       return bool;
/* 102 */     } catch (ClassNotFoundException|NoSuchMethodException|IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException classNotFoundException) {
/*     */ 
/*     */ 
/*     */       
/* 106 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isUnlockCommercialFeaturesEnabled() {
/* 114 */     Flag flag = Flag.getFlag("UnlockCommercialFeatures");
/* 115 */     if (flag != null && "true".equals(flag.getVMOption().getValue())) {
/* 116 */       return true;
/*     */     }
/* 118 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\ExtendedPlatformComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */