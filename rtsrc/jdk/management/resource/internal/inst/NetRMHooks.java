/*    */ package jdk.management.resource.internal.inst;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.IOException;
/*    */ import java.net.ProtocolFamily;
/*    */ import jdk.Exported;
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
/*    */ @Exported(false)
/*    */ @InstrumentationTarget("sun.nio.ch.Net")
/*    */ public class NetRMHooks
/*    */ {
/*    */   @InstrumentationMethod
/*    */   static FileDescriptor socket(ProtocolFamily paramProtocolFamily, boolean paramBoolean) throws IOException {
/* 27 */     FileDescriptor fileDescriptor = socket(paramProtocolFamily, paramBoolean);
/* 28 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(fileDescriptor);
/* 29 */     ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(fileDescriptor);
/*    */     
/* 31 */     long l1 = 0L;
/* 32 */     long l2 = 0L;
/*    */     try {
/*    */       try {
/* 35 */         l1 = resourceRequest.request(1L, resourceIdImpl);
/* 36 */         if (l1 < 1L) {
/* 37 */           throw new IOException("Resource limited: too many open file descriptors");
/*    */         }
/* 39 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 40 */         throw new IOException("Resource limited: too many open file descriptors", resourceRequestDeniedException);
/*    */       } 
/* 42 */       l2 = 1L;
/*    */     } finally {
/* 44 */       resourceRequest.request(-(l1 - l2), resourceIdImpl);
/*    */     } 
/*    */     
/* 47 */     return fileDescriptor;
/*    */   }
/*    */   
/*    */   @InstrumentationMethod
/*    */   static FileDescriptor serverSocket(boolean paramBoolean) {
/* 52 */     FileDescriptor fileDescriptor = serverSocket(paramBoolean);
/* 53 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(fileDescriptor);
/* 54 */     ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(fileDescriptor);
/*    */     
/* 56 */     long l1 = 0L;
/* 57 */     long l2 = 0L;
/*    */     try {
/* 59 */       l1 = resourceRequest.request(1L, resourceIdImpl);
/* 60 */       if (l1 < 1L) {
/* 61 */         throw new ResourceRequestDeniedException("Resource limited: too many open file descriptors");
/*    */       }
/* 63 */       l2 = 1L;
/*    */     } finally {
/* 65 */       resourceRequest.request(-(l1 - l2), resourceIdImpl);
/*    */     } 
/*    */     
/* 68 */     return fileDescriptor;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\NetRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */