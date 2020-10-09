/*    */ package jdk.management.resource.internal.inst;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.SocketAddress;
/*    */ import java.nio.channels.AsynchronousSocketChannel;
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
/*    */ @InstrumentationTarget("sun.nio.ch.AsynchronousSocketChannelImpl")
/*    */ public class AsynchronousSocketChannelImplRMHooks
/*    */ {
/*    */   @InstrumentationMethod
/*    */   public final SocketAddress getLocalAddress() throws IOException {
/* 26 */     return getLocalAddress();
/*    */   }
/*    */ 
/*    */   
/*    */   @InstrumentationMethod
/*    */   public final AsynchronousSocketChannel bind(SocketAddress paramSocketAddress) throws IOException {
/* 32 */     ResourceIdImpl resourceIdImpl = null;
/* 33 */     ResourceRequest resourceRequest = null;
/* 34 */     long l = 0L;
/*    */ 
/*    */     
/* 37 */     if (getLocalAddress() == null) {
/* 38 */       resourceIdImpl = ResourceIdImpl.of(paramSocketAddress);
/* 39 */       resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/*    */       
/*    */       try {
/* 42 */         l = resourceRequest.request(1L, resourceIdImpl);
/* 43 */         if (l < 1L) {
/* 44 */           throw new IOException("Resource limited: too many open socket channels");
/*    */         }
/* 46 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 47 */         throw new IOException("Resource limited: too many open socket channels", resourceRequestDeniedException);
/*    */       } 
/*    */     } 
/*    */     
/* 51 */     boolean bool = false;
/* 52 */     AsynchronousSocketChannel asynchronousSocketChannel = null;
/*    */     try {
/* 54 */       asynchronousSocketChannel = bind(paramSocketAddress);
/* 55 */       bool = true;
/*    */     } finally {
/* 57 */       if (resourceRequest != null)
/*    */       {
/* 59 */         resourceRequest.request(-(l - bool), resourceIdImpl);
/*    */       }
/*    */     } 
/*    */     
/* 63 */     return asynchronousSocketChannel;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\AsynchronousSocketChannelImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */