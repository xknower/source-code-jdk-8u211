/*    */ package jdk.management.resource.internal.inst;
/*    */ 
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
/*    */ @InstrumentationTarget("java.net.SocketOutputStream")
/*    */ @TypeMapping(from = "jdk.management.resource.internal.inst.SocketOutputStreamRMHooks$AbstractPlainSocketImpl", to = "java.net.AbstractPlainSocketImpl")
/*    */ public final class SocketOutputStreamRMHooks
/*    */ {
/*    */   static class AbstractPlainSocketImpl
/*    */   {
/*    */     protected int localport;
/*    */   }
/* 30 */   private AbstractPlainSocketImpl impl = null;
/*    */ 
/*    */   
/*    */   @InstrumentationMethod
/*    */   private void socketWrite(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 35 */     if (paramInt2 < 0) {
/* 36 */       socketWrite(paramArrayOfbyte, paramInt1, paramInt2);
/*    */       
/*    */       return;
/*    */     } 
/* 40 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(Integer.valueOf(this.impl.localport));
/* 41 */     ResourceRequest resourceRequest = ApproverGroup.SOCKET_WRITE_GROUP.getApprover(this);
/* 42 */     long l = 0L;
/*    */     try {
/* 44 */       l = resourceRequest.request(paramInt2, resourceIdImpl);
/* 45 */       if (l < paramInt2) {
/* 46 */         throw new IOException("Resource limited: insufficient bytes approved");
/*    */       }
/* 48 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 49 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*    */     } 
/*    */     
/* 52 */     int i = 0;
/*    */     try {
/* 54 */       socketWrite(paramArrayOfbyte, paramInt1, paramInt2);
/* 55 */       i = paramInt2;
/*    */     } finally {
/*    */       
/* 58 */       resourceRequest.request(-(l - i), resourceIdImpl);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\SocketOutputStreamRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */