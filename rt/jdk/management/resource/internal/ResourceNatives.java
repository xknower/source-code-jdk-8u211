/*     */ package jdk.management.resource.internal;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ResourceNatives
/*     */ {
/*     */   public static final int SYSTEM_RESOURCE_CONTEXT_ID = 0;
/*     */   public static final int FEATURE_ENABLED = 1;
/*     */   public static final int FEATURE_RETAINED_MEMORY = 2;
/*     */   
/*     */   static {
/*  45 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  48 */             System.loadLibrary("resource");
/*  49 */             return null;
/*     */           }
/*     */         },  (AccessControlContext)null, new Permission[] { new RuntimePermission("loadLibrary.resource") });
/*     */   }
/*     */   
/*  54 */   private static int enabledFeatures = featuresEnabled0();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEnabled() {
/*  79 */     return ((featuresEnabled() & 0x1) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isHeapRetainedEnabled() {
/*  88 */     return ((featuresEnabled() & 0x2) == 2);
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
/*     */   public static int featuresEnabled() {
/* 102 */     return enabledFeatures;
/*     */   }
/*     */   
/*     */   public static int sampleInterval() {
/* 106 */     return sampleInterval0();
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
/*     */   public static void getThreadStats(long[] paramArrayOflong1, long[] paramArrayOflong2, long[] paramArrayOflong3) {
/* 122 */     getThreadStats0(paramArrayOflong1, paramArrayOflong2, paramArrayOflong3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getCurrentThreadCPUTime() {
/* 131 */     return getCurrentThreadCPUTime0();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getCurrentThreadAllocatedHeap() {
/* 140 */     return getCurrentThreadAllocatedHeap0();
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
/*     */   public static int createResourceContext(String paramString) {
/* 193 */     return createResourceContext0(paramString);
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
/*     */   public static void destroyResourceContext(int paramInt1, int paramInt2) {
/* 207 */     destroyResourceContext0(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int setThreadResourceContext(int paramInt) {
/* 217 */     return setThreadResourceContext0(0L, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int setThreadResourceContext(long paramLong, int paramInt) {
/* 228 */     return setThreadResourceContext0(paramLong, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getThreadResourceContext() {
/* 237 */     return getThreadResourceContext0(0L);
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
/*     */   public static boolean getContextsRetainedMemory(int[] paramArrayOfint, long[] paramArrayOflong, byte[] paramArrayOfbyte) {
/* 293 */     return getContextsRetainedMemory0(paramArrayOfint, paramArrayOflong, paramArrayOfbyte);
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
/*     */   public static void setRetainedMemoryNotificationEnabled(Object paramObject) {
/* 305 */     setRetainedMemoryNotificationEnabled0(paramObject);
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
/*     */   public static void computeRetainedMemory(int[] paramArrayOfint, int paramInt) {
/* 328 */     computeRetainedMemory0(paramArrayOfint, (byte)paramInt);
/*     */   }
/*     */   
/*     */   private static native int featuresEnabled0();
/*     */   
/*     */   private static native int sampleInterval0();
/*     */   
/*     */   private static native void getThreadStats0(long[] paramArrayOflong1, long[] paramArrayOflong2, long[] paramArrayOflong3);
/*     */   
/*     */   private static native long getCurrentThreadCPUTime0();
/*     */   
/*     */   private static native long getCurrentThreadAllocatedHeap0();
/*     */   
/*     */   private static native int createResourceContext0(String paramString);
/*     */   
/*     */   private static native void destroyResourceContext0(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native int setThreadResourceContext0(long paramLong, int paramInt);
/*     */   
/*     */   public static native int getThreadResourceContext0(long paramLong);
/*     */   
/*     */   private static native boolean getContextsRetainedMemory0(int[] paramArrayOfint, long[] paramArrayOflong, byte[] paramArrayOfbyte);
/*     */   
/*     */   private static native void setRetainedMemoryNotificationEnabled0(Object paramObject);
/*     */   
/*     */   private static native void computeRetainedMemory0(int[] paramArrayOfint, byte paramByte);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\ResourceNatives.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */