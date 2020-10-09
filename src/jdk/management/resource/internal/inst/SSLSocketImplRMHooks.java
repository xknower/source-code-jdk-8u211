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
/*    */ 
/*    */ @InstrumentationTarget("sun.security.ssl.SSLSocketImpl")
/*    */ public final class SSLSocketImplRMHooks
/*    */ {
/*    */   public final boolean isBound() {
/* 25 */     return isBound();
/*    */   }
/*    */   
/*    */   boolean isLayered() {
/* 29 */     return isLayered();
/*    */   }
/*    */   
/*    */   public final InetAddress getLocalAddress() {
/* 33 */     return getLocalAddress();
/*    */   }
/*    */   
/*    */   @InstrumentationMethod
/*    */   void waitForClose(boolean paramBoolean) throws IOException {
/* 38 */     InetAddress inetAddress = null;
/* 39 */     if (isLayered()) {
/* 40 */       inetAddress = getLocalAddress();
/*    */     }
/*    */     
/* 43 */     if (isBound()) {
/* 44 */       ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(inetAddress);
/* 45 */       ResourceRequest resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/* 46 */       resourceRequest.request(-1L, resourceIdImpl);
/*    */     } 
/* 48 */     waitForClose(paramBoolean);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\SSLSocketImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */