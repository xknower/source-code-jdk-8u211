/*    */ package sun.management;
/*    */ 
/*    */ import com.sun.management.OperatingSystemMXBean;
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
/*    */ class OperatingSystemImpl
/*    */   extends BaseOperatingSystemImpl
/*    */   implements OperatingSystemMXBean
/*    */ {
/* 42 */   private static Object psapiLock = new Object();
/*    */   
/*    */   OperatingSystemImpl(VMManagement paramVMManagement) {
/* 45 */     super(paramVMManagement);
/*    */   }
/*    */   
/*    */   public long getCommittedVirtualMemorySize() {
/* 49 */     synchronized (psapiLock) {
/* 50 */       return getCommittedVirtualMemorySize0();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private native long getCommittedVirtualMemorySize0();
/*    */   
/*    */   public native long getTotalSwapSpaceSize();
/*    */   
/*    */   public native long getFreeSwapSpaceSize();
/*    */   
/*    */   public native long getProcessCpuTime();
/*    */   
/*    */   static {
/* 64 */     initialize();
/*    */   }
/*    */   
/*    */   public native long getFreePhysicalMemorySize();
/*    */   
/*    */   public native long getTotalPhysicalMemorySize();
/*    */   
/*    */   public native double getSystemCpuLoad();
/*    */   
/*    */   public native double getProcessCpuLoad();
/*    */   
/*    */   private static native void initialize();
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\OperatingSystemImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */