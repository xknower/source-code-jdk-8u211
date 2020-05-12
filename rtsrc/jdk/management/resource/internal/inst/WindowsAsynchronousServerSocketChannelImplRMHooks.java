/*    */ package jdk.management.resource.internal.inst;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.IOException;
/*    */ import java.net.InetSocketAddress;
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
/*    */ @InstrumentationTarget("sun.nio.ch.WindowsAsynchronousServerSocketChannelImpl")
/*    */ public class WindowsAsynchronousServerSocketChannelImplRMHooks
/*    */ {
/* 23 */   protected final FileDescriptor fd = null;
/* 24 */   protected volatile InetSocketAddress localAddress = null;
/*    */   
/*    */   @InstrumentationMethod
/*    */   void implClose() throws IOException {
/*    */     try {
/* 29 */       implClose();
/*    */     } finally {
/* 31 */       ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.fd);
/* 32 */       if (resourceIdImpl != null) {
/* 33 */         ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(this.fd);
/* 34 */         resourceRequest.request(-1L, resourceIdImpl);
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 39 */       if (this.localAddress != null) {
/*    */         
/* 41 */         resourceIdImpl = ResourceIdImpl.of(this.localAddress);
/* 42 */         ResourceRequest resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/* 43 */         resourceRequest.request(-1L, resourceIdImpl);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\WindowsAsynchronousServerSocketChannelImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */