/*    */ package jdk.management.resource.internal.inst;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.InetAddress;
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
/*    */ @InstrumentationTarget("sun.security.ssl.BaseSSLSocketImpl")
/*    */ abstract class BaseSSLSocketImplRMHooks
/*    */ {
/*    */   @InstrumentationMethod
/*    */   boolean isLayered() {
/* 25 */     return isLayered();
/*    */   }
/*    */   
/*    */   @InstrumentationMethod
/*    */   public final InetAddress getLocalAddress() {
/* 30 */     return getLocalAddress();
/*    */   }
/*    */   
/*    */   @InstrumentationMethod
/*    */   public final boolean isBound() {
/* 35 */     return isBound();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @InstrumentationMethod
/*    */   public synchronized void close() throws IOException {
/* 42 */     if (isLayered())
/*    */     {
/* 44 */       if (isBound()) {
/* 45 */         ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/* 46 */         ResourceRequest resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/* 47 */         resourceRequest.request(-1L, resourceIdImpl);
/*    */       } 
/*    */     }
/* 50 */     close();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\BaseSSLSocketImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */