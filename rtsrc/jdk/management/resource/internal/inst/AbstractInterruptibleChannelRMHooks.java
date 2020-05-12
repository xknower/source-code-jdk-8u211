/*    */ package jdk.management.resource.internal.inst;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.SocketAddress;
/*    */ import java.nio.channels.DatagramChannel;
/*    */ import java.nio.channels.ServerSocketChannel;
/*    */ import java.nio.channels.SocketChannel;
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
/*    */ @InstrumentationTarget("java.nio.channels.spi.AbstractInterruptibleChannel")
/*    */ public final class AbstractInterruptibleChannelRMHooks
/*    */ {
/* 25 */   private final Object closeLock = new Object();
/*    */   private volatile boolean open = true;
/*    */   
/*    */   @InstrumentationMethod
/*    */   public final void close() throws IOException {
/* 30 */     synchronized (this.closeLock) {
/* 31 */       if (!this.open) {
/*    */         return;
/*    */       }
/*    */     } 
/*    */     
/* 36 */     ResourceIdImpl resourceIdImpl = null;
/* 37 */     SocketAddress socketAddress = null;
/* 38 */     if (DatagramChannel.class.isInstance(this)) {
/* 39 */       DatagramChannel datagramChannel = (DatagramChannel)this;
/* 40 */       socketAddress = datagramChannel.getLocalAddress();
/* 41 */       resourceIdImpl = ResourceIdImpl.of(socketAddress);
/* 42 */     } else if (SocketChannel.class.isInstance(this)) {
/* 43 */       SocketChannel socketChannel = (SocketChannel)this;
/* 44 */       socketAddress = socketChannel.getLocalAddress();
/* 45 */       resourceIdImpl = ResourceIdImpl.of(socketAddress);
/* 46 */     } else if (ServerSocketChannel.class.isInstance(this)) {
/* 47 */       ServerSocketChannel serverSocketChannel = (ServerSocketChannel)this;
/* 48 */       socketAddress = serverSocketChannel.getLocalAddress();
/* 49 */       resourceIdImpl = ResourceIdImpl.of(socketAddress);
/*    */     }  try {
/* 51 */       close();
/*    */     }
/*    */     finally {
/*    */       
/* 55 */       if (socketAddress != null)
/* 56 */         if (DatagramChannel.class.isInstance(this)) {
/* 57 */           ResourceRequest resourceRequest = ApproverGroup.DATAGRAM_OPEN_GROUP.getApprover(this);
/* 58 */           resourceRequest.request(-1L, resourceIdImpl);
/* 59 */         } else if (SocketChannel.class.isInstance(this) || ServerSocketChannel.class
/* 60 */           .isInstance(this)) {
/* 61 */           ResourceRequest resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/* 62 */           resourceRequest.request(-1L, resourceIdImpl);
/*    */         }  
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\AbstractInterruptibleChannelRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */