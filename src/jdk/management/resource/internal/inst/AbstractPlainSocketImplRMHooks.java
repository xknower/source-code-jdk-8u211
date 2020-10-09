/*    */ package jdk.management.resource.internal.inst;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.IOException;
/*    */ import jdk.internal.instrumentation.InstrumentationMethod;
/*    */ import jdk.internal.instrumentation.InstrumentationTarget;
/*    */ import jdk.management.resource.ResourceRequest;
/*    */ import jdk.management.resource.ResourceRequestDeniedException;
/*    */ import jdk.management.resource.internal.ApproverGroup;
/*    */ import jdk.management.resource.internal.ResourceIdImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @InstrumentationTarget("java.net.AbstractPlainSocketImpl")
/*    */ abstract class AbstractPlainSocketImplRMHooks
/*    */ {
/*    */   protected FileDescriptor fd;
/*    */   
/*    */   abstract void socketClose0(boolean paramBoolean);
/*    */   
/*    */   @InstrumentationMethod
/*    */   protected synchronized void create(boolean paramBoolean) throws IOException {
/* 28 */     create(paramBoolean);
/*    */     
/* 30 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.fd);
/* 31 */     ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(this.fd);
/* 32 */     long l = 0L;
/*    */     try {
/* 34 */       l = resourceRequest.request(1L, resourceIdImpl);
/* 35 */       if (l < 1L) {
/* 36 */         socketClose0(false);
/* 37 */         throw new IOException("Resource limited: too many open file descriptors");
/*    */       } 
/* 39 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 40 */       resourceRequest.request(-l, resourceIdImpl);
/* 41 */       socketClose0(false);
/* 42 */       throw new IOException("Resource limited: too many open file descriptors", resourceRequestDeniedException);
/*    */     } 
/*    */     
/* 45 */     resourceRequest.request(-(l - 1L), resourceIdImpl);
/*    */   }
/*    */   
/*    */   @InstrumentationMethod
/*    */   protected void close() throws IOException {
/* 50 */     ResourceIdImpl resourceIdImpl = null;
/* 51 */     ResourceRequest resourceRequest = null;
/*    */     
/* 53 */     byte b = -1;
/* 54 */     if (this.fd != null) {
/* 55 */       resourceIdImpl = ResourceIdImpl.of(this.fd);
/* 56 */       resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(this.fd);
/*    */     } 
/*    */     
/*    */     try {
/* 60 */       close();
/*    */     } finally {
/* 62 */       if (resourceRequest != null)
/* 63 */         resourceRequest.request(-1L, resourceIdImpl); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\AbstractPlainSocketImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */