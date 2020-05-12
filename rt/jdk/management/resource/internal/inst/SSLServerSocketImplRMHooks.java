/*    */ package jdk.management.resource.internal.inst;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.Socket;
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
/*    */ 
/*    */ @InstrumentationTarget("sun.security.ssl.SSLServerSocketImpl")
/*    */ final class SSLServerSocketImplRMHooks
/*    */ {
/*    */   @InstrumentationMethod
/*    */   public Socket accept() throws IOException {
/* 26 */     long l1 = 0L;
/* 27 */     long l2 = 0L;
/* 28 */     Socket socket = null;
/* 29 */     ResourceIdImpl resourceIdImpl = null;
/* 30 */     ResourceRequest resourceRequest = null;
/*    */     try {
/* 32 */       socket = accept();
/* 33 */       l2 = 1L;
/* 34 */       resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(socket);
/* 35 */       resourceIdImpl = ResourceIdImpl.of(socket.getLocalAddress());
/*    */       try {
/* 37 */         l1 = resourceRequest.request(1L, resourceIdImpl);
/* 38 */         if (l1 < 1L) {
/*    */           try {
/* 40 */             socket.close();
/* 41 */           } catch (IOException iOException) {}
/*    */ 
/*    */           
/* 44 */           throw new IOException("Resource limited: too many open sockets");
/*    */         } 
/* 46 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*    */         try {
/* 48 */           socket.close();
/* 49 */         } catch (IOException iOException) {}
/*    */ 
/*    */         
/* 52 */         throw new IOException("Resource limited: too many open sockets", resourceRequestDeniedException);
/*    */       } 
/* 54 */       l2 = 1L;
/*    */     } finally {
/* 56 */       if (resourceRequest != null)
/*    */       {
/* 58 */         resourceRequest.request(-(l1 - l2), resourceIdImpl);
/*    */       }
/*    */     } 
/*    */     
/* 62 */     return socket;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\SSLServerSocketImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */