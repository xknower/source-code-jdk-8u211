/*    */ package jdk.management.resource.internal.inst;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.IOException;
/*    */ import jdk.internal.instrumentation.InstrumentationMethod;
/*    */ import jdk.internal.instrumentation.InstrumentationTarget;
/*    */ import jdk.internal.instrumentation.TypeMapping;
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
/*    */ 
/*    */ @InstrumentationTarget("java.net.SocketInputStream")
/*    */ @TypeMapping(from = "jdk.management.resource.internal.inst.SocketInputStreamRMHooks$AbstractPlainSocketImpl", to = "java.net.AbstractPlainSocketImpl")
/*    */ public final class SocketInputStreamRMHooks
/*    */ {
/*    */   static class AbstractPlainSocketImpl
/*    */   {
/*    */     protected int localport;
/*    */   }
/* 31 */   private AbstractPlainSocketImpl impl = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @InstrumentationMethod
/*    */   private int socketRead(FileDescriptor paramFileDescriptor, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) throws IOException {
/*    */     int j;
/* 40 */     if (paramInt2 < 0) {
/* 41 */       return socketRead(paramFileDescriptor, paramArrayOfbyte, paramInt1, paramInt2, paramInt3);
/*    */     }
/*    */     
/* 44 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(Integer.valueOf(this.impl.localport));
/* 45 */     ResourceRequest resourceRequest = ApproverGroup.SOCKET_READ_GROUP.getApprover(this);
/* 46 */     long l = 0L;
/*    */     try {
/* 48 */       l = Math.max(resourceRequest.request(paramInt2, resourceIdImpl), 0L);
/* 49 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 50 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*    */     } 
/*    */ 
/*    */     
/* 54 */     paramInt2 = Math.min(paramInt2, (int)l);
/*    */ 
/*    */     
/* 57 */     int i = 0;
/*    */     
/*    */     try {
/* 60 */       j = socketRead(paramFileDescriptor, paramArrayOfbyte, paramInt1, paramInt2, paramInt3);
/* 61 */       i = Math.max(j, 0);
/*    */     } finally {
/*    */       
/* 64 */       resourceRequest.request(-(l - i), resourceIdImpl);
/*    */     } 
/*    */     
/* 67 */     return j;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\SocketInputStreamRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */