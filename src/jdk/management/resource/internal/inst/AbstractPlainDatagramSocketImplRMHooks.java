/*    */ package jdk.management.resource.internal.inst;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.net.SocketException;
/*    */ import jdk.internal.instrumentation.InstrumentationMethod;
/*    */ import jdk.internal.instrumentation.InstrumentationTarget;
/*    */ import jdk.management.resource.ResourceRequest;
/*    */ import jdk.management.resource.ResourceRequestDeniedException;
/*    */ import jdk.management.resource.internal.ApproverGroup;
/*    */ import jdk.management.resource.internal.ResourceIdImpl;
/*    */ import sun.misc.JavaIOFileDescriptorAccess;
/*    */ import sun.misc.SharedSecrets;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @InstrumentationTarget("java.net.AbstractPlainDatagramSocketImpl")
/*    */ public class AbstractPlainDatagramSocketImplRMHooks
/*    */ {
/*    */   protected FileDescriptor fd;
/*    */   
/*    */   @InstrumentationMethod
/*    */   protected synchronized void create() throws SocketException {
/*    */     long l1;
/* 28 */     create();
/*    */ 
/*    */     
/* 31 */     JavaIOFileDescriptorAccess javaIOFileDescriptorAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*    */     
/*    */     try {
/* 34 */       l1 = javaIOFileDescriptorAccess.getHandle(this.fd);
/* 35 */       if (l1 == -1L) {
/* 36 */         l1 = javaIOFileDescriptorAccess.get(this.fd);
/*    */       }
/* 38 */     } catch (UnsupportedOperationException unsupportedOperationException) {
/* 39 */       l1 = javaIOFileDescriptorAccess.get(this.fd);
/*    */     } 
/* 41 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(Long.valueOf(l1));
/* 42 */     ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(this.fd);
/* 43 */     long l2 = 0L;
/*    */     try {
/* 45 */       l2 = resourceRequest.request(1L, resourceIdImpl);
/* 46 */       if (l2 < 1L) {
/* 47 */         throw new SocketException("Resource limited: too many open file descriptors");
/*    */       }
/* 49 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 50 */       resourceRequest.request(-l2, resourceIdImpl);
/* 51 */       SocketException socketException = new SocketException("Resource limited: too many open file descriptors");
/*    */       
/* 53 */       socketException.initCause(resourceRequestDeniedException);
/* 54 */       throw socketException;
/*    */     } 
/*    */     
/* 57 */     resourceRequest.request(-(l2 - 1L), resourceIdImpl);
/*    */   }
/*    */   
/*    */   @InstrumentationMethod
/*    */   protected void close() {
/* 62 */     if (this.fd != null) {
/*    */       long l;
/* 64 */       JavaIOFileDescriptorAccess javaIOFileDescriptorAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*    */       
/*    */       try {
/* 67 */         l = javaIOFileDescriptorAccess.getHandle(this.fd);
/* 68 */         if (l == -1L) {
/* 69 */           l = javaIOFileDescriptorAccess.get(this.fd);
/*    */         }
/* 71 */       } catch (UnsupportedOperationException unsupportedOperationException) {
/* 72 */         l = javaIOFileDescriptorAccess.get(this.fd);
/*    */       } 
/* 74 */       if (l != -1L) {
/* 75 */         ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(Long.valueOf(l));
/* 76 */         ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(this.fd);
/* 77 */         resourceRequest.request(-1L, resourceIdImpl);
/*    */       } 
/*    */     } 
/*    */     
/* 81 */     close();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\AbstractPlainDatagramSocketImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */