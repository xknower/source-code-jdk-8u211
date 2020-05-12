/*    */ package jdk.management.resource.internal.inst;
/*    */ 
/*    */ import java.security.AccessControlContext;
/*    */ import jdk.internal.instrumentation.InstrumentationMethod;
/*    */ import jdk.internal.instrumentation.InstrumentationTarget;
/*    */ import jdk.management.resource.ResourceRequest;
/*    */ import jdk.management.resource.ResourceRequestDeniedException;
/*    */ import jdk.management.resource.internal.ApproverGroup;
/*    */ import jdk.management.resource.internal.ResourceIdImpl;
/*    */ import jdk.management.resource.internal.SimpleResourceContext;
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
/*    */ @InstrumentationTarget("java.lang.Thread")
/*    */ public final class ThreadRMHooks
/*    */ {
/*    */   private long tid;
/*    */   
/*    */   private static synchronized long nextThreadID() {
/* 28 */     return 0L;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @InstrumentationMethod
/*    */   private void init(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString, long paramLong, AccessControlContext paramAccessControlContext, boolean paramBoolean) {
/* 39 */     long l1 = nextThreadID();
/* 40 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(Long.valueOf(l1));
/* 41 */     ResourceRequest resourceRequest = ApproverGroup.THREAD_CREATED_GROUP.getApprover(this);
/* 42 */     long l2 = 1L;
/* 43 */     long l3 = 0L;
/*    */     try {
/* 45 */       l3 = resourceRequest.request(l2, resourceIdImpl);
/* 46 */       if (l3 == 0L) {
/* 47 */         throw new ResourceRequestDeniedException("Resource limited: thread creation denied by resource manager");
/*    */       }
/* 49 */       init(paramThreadGroup, paramRunnable, paramString, paramLong, paramAccessControlContext, paramBoolean);
/*    */ 
/*    */       
/* 52 */       SimpleResourceContext simpleResourceContext = (SimpleResourceContext)SimpleResourceContext.getThreadContext(Thread.currentThread());
/* 53 */       ThreadRMHooks threadRMHooks = this;
/* 54 */       simpleResourceContext.bindNewThreadContext((Thread)threadRMHooks);
/*    */     } finally {
/*    */       
/* 57 */       resourceRequest.request(-(l3 - l2), resourceIdImpl);
/*    */     } 
/* 59 */     this.tid = l1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @InstrumentationMethod
/*    */   private void exit() {
/* 72 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(Long.valueOf(this.tid));
/* 73 */     ResourceRequest resourceRequest = ApproverGroup.THREAD_CREATED_GROUP.getApprover(this);
/*    */     
/*    */     try {
/* 76 */       resourceRequest.request(-1L, resourceIdImpl);
/* 77 */       SimpleResourceContext.removeThreadContext();
/*    */     } finally {
/* 79 */       exit();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\ThreadRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */