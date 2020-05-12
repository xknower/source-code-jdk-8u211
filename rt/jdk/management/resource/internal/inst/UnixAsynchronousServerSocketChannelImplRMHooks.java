/*    */ package jdk.management.resource.internal.inst;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.IOException;
/*    */ import java.net.InetSocketAddress;
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
/*    */ 
/*    */ @InstrumentationTarget("sun.nio.ch.UnixAsynchronousServerSocketChannelImpl")
/*    */ public class UnixAsynchronousServerSocketChannelImplRMHooks
/*    */ {
/* 24 */   private static final NativeDispatcher nd = null;
/*    */   
/* 26 */   protected volatile InetSocketAddress localAddress = null;
/*    */   
/*    */   abstract class NativeDispatcher
/*    */   {
/*    */     abstract void close(FileDescriptor param1FileDescriptor) throws IOException;
/*    */   }
/*    */   
/*    */   @InstrumentationMethod
/*    */   private int accept(FileDescriptor paramFileDescriptor1, FileDescriptor paramFileDescriptor2, InetSocketAddress[] paramArrayOfInetSocketAddress) throws IOException {
/* 35 */     int i = accept(paramFileDescriptor1, paramFileDescriptor2, paramArrayOfInetSocketAddress);
/*    */     
/* 37 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(paramFileDescriptor2);
/*    */     
/* 39 */     if (resourceIdImpl != null) {
/* 40 */       ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(paramFileDescriptor2);
/*    */       
/* 42 */       long l1 = 0L;
/* 43 */       long l2 = 0L;
/*    */       try {
/*    */         try {
/* 46 */           l1 = resourceRequest.request(1L, resourceIdImpl);
/* 47 */           if (l1 < 1L) {
/* 48 */             throw new IOException("Resource limited: too many open file descriptors");
/*    */           }
/* 50 */         } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 51 */           throw new IOException("Resource limited: too many open file descriptors", resourceRequestDeniedException);
/*    */         } 
/* 53 */         l2 = 1L;
/*    */       } finally {
/* 55 */         if (l2 == 0L) {
/*    */           try {
/* 57 */             nd.close(paramFileDescriptor2);
/* 58 */           } catch (IOException iOException) {}
/*    */         }
/*    */         else {
/*    */           
/* 62 */           resourceRequest.request(-(l1 - 1L), resourceIdImpl);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 67 */     return i;
/*    */   }
/*    */   
/*    */   @InstrumentationMethod
/*    */   void implClose() throws IOException {
/*    */     try {
/* 73 */       implClose();
/*    */     }
/*    */     finally {
/*    */       
/* 77 */       if (this.localAddress != null) {
/*    */         
/* 79 */         ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.localAddress);
/* 80 */         ResourceRequest resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/* 81 */         resourceRequest.request(-1L, resourceIdImpl);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\UnixAsynchronousServerSocketChannelImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */