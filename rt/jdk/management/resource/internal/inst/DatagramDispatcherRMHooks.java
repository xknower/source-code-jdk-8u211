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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Exported(false)
/*    */ @InstrumentationTarget("sun.nio.ch.DatagramDispatcher")
/*    */ public class DatagramDispatcherRMHooks
/*    */ {
/*    */   @InstrumentationMethod
/*    */   void close(FileDescriptor paramFileDescriptor) throws IOException {
/* 27 */     long l = 0L;
/* 28 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(paramFileDescriptor);
/*    */     try {
/* 30 */       close(paramFileDescriptor);
/* 31 */       l = 1L;
/*    */     } finally {
/* 33 */       ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(paramFileDescriptor);
/* 34 */       resourceRequest.request(-l, resourceIdImpl);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\DatagramDispatcherRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */