/*    */ package jdk.management.resource.internal.inst;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.IOException;
/*    */ import jdk.Exported;
/*    */ import jdk.internal.instrumentation.InstrumentationMethod;
/*    */ import jdk.internal.instrumentation.InstrumentationTarget;
/*    */ import jdk.management.resource.ResourceRequest;
/*    */ import jdk.management.resource.internal.ApproverGroup;
/*    */ import jdk.management.resource.internal.ResourceIdImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Exported(false)
/*    */ @InstrumentationTarget("sun.nio.ch.SocketDispatcher")
/*    */ public class SocketDispatcherRMHooks
/*    */ {
/*    */   @InstrumentationMethod
/*    */   void close(FileDescriptor paramFileDescriptor) throws IOException {
/* 24 */     long l = 0L;
/* 25 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(paramFileDescriptor);
/*    */     try {
/* 27 */       close(paramFileDescriptor);
/* 28 */       l = 1L;
/*    */     } finally {
/* 30 */       ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(paramFileDescriptor);
/* 31 */       resourceRequest.request(-l, resourceIdImpl);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\SocketDispatcherRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */