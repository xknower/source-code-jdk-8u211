/*     */ package com.sun.corba.se.impl.protocol.giopmsgheaders;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.CDRInputStream;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.IORFactories;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.servicecontext.ServiceContexts;
/*     */ import java.io.IOException;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ReplyMessage_1_1
/*     */   extends Message_1_1
/*     */   implements ReplyMessage
/*     */ {
/*  57 */   private ORB orb = null;
/*  58 */   private ORBUtilSystemException wrapper = null;
/*  59 */   private ServiceContexts service_contexts = null;
/*  60 */   private int request_id = 0;
/*  61 */   private int reply_status = 0;
/*  62 */   private IOR ior = null;
/*  63 */   private String exClassName = null;
/*  64 */   private int minorCode = 0;
/*  65 */   private CompletionStatus completionStatus = null;
/*     */ 
/*     */ 
/*     */   
/*     */   ReplyMessage_1_1(ORB paramORB) {
/*  70 */     this.orb = paramORB;
/*  71 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   ReplyMessage_1_1(ORB paramORB, ServiceContexts paramServiceContexts, int paramInt1, int paramInt2, IOR paramIOR) {
/*  77 */     super(1195986768, GIOPVersion.V1_1, (byte)0, (byte)1, 0);
/*     */     
/*  79 */     this.orb = paramORB;
/*  80 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*     */     
/*  82 */     this.service_contexts = paramServiceContexts;
/*  83 */     this.request_id = paramInt1;
/*  84 */     this.reply_status = paramInt2;
/*  85 */     this.ior = paramIOR;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequestId() {
/*  91 */     return this.request_id;
/*     */   }
/*     */   
/*     */   public int getReplyStatus() {
/*  95 */     return this.reply_status;
/*     */   }
/*     */   
/*     */   public short getAddrDisposition() {
/*  99 */     return 0;
/*     */   }
/*     */   
/*     */   public ServiceContexts getServiceContexts() {
/* 103 */     return this.service_contexts;
/*     */   }
/*     */   
/*     */   public void setServiceContexts(ServiceContexts paramServiceContexts) {
/* 107 */     this.service_contexts = paramServiceContexts;
/*     */   }
/*     */   
/*     */   public SystemException getSystemException(String paramString) {
/* 111 */     return MessageBase.getSystemException(this.exClassName, this.minorCode, this.completionStatus, paramString, this.wrapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public IOR getIOR() {
/* 116 */     return this.ior;
/*     */   }
/*     */   
/*     */   public void setIOR(IOR paramIOR) {
/* 120 */     this.ior = paramIOR;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(InputStream paramInputStream) {
/* 126 */     super.read(paramInputStream);
/* 127 */     this.service_contexts = new ServiceContexts((InputStream)paramInputStream);
/*     */     
/* 129 */     this.request_id = paramInputStream.read_ulong();
/* 130 */     this.reply_status = paramInputStream.read_long();
/* 131 */     isValidReplyStatus(this.reply_status);
/*     */ 
/*     */ 
/*     */     
/* 135 */     if (this.reply_status == 2) {
/*     */       
/* 137 */       String str = paramInputStream.read_string();
/* 138 */       this.exClassName = ORBUtility.classNameOf(str);
/* 139 */       this.minorCode = paramInputStream.read_long();
/* 140 */       int i = paramInputStream.read_long();
/*     */       
/* 142 */       switch (i) {
/*     */         case 0:
/* 144 */           this.completionStatus = CompletionStatus.COMPLETED_YES;
/*     */           return;
/*     */         case 1:
/* 147 */           this.completionStatus = CompletionStatus.COMPLETED_NO;
/*     */           return;
/*     */         case 2:
/* 150 */           this.completionStatus = CompletionStatus.COMPLETED_MAYBE;
/*     */           return;
/*     */       } 
/* 153 */       throw this.wrapper.badCompletionStatusInReply(CompletionStatus.COMPLETED_MAYBE, new Integer(i));
/*     */     } 
/*     */     
/* 156 */     if (this.reply_status != 1)
/*     */     {
/* 158 */       if (this.reply_status == 3) {
/* 159 */         CDRInputStream cDRInputStream = (CDRInputStream)paramInputStream;
/* 160 */         this.ior = IORFactories.makeIOR(cDRInputStream);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(OutputStream paramOutputStream) {
/* 167 */     super.write(paramOutputStream);
/* 168 */     if (this.service_contexts != null) {
/* 169 */       this.service_contexts.write((OutputStream)paramOutputStream, GIOPVersion.V1_1);
/*     */     }
/*     */     else {
/*     */       
/* 173 */       ServiceContexts.writeNullServiceContext((OutputStream)paramOutputStream);
/*     */     } 
/*     */     
/* 176 */     paramOutputStream.write_ulong(this.request_id);
/* 177 */     paramOutputStream.write_long(this.reply_status);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void isValidReplyStatus(int paramInt) {
/* 183 */     switch (paramInt) {
/*     */       case 0:
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */         return;
/*     */     } 
/* 190 */     ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get("rpc.protocol");
/*     */     
/* 192 */     throw oRBUtilSystemException.illegalReplyStatus(CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void callback(MessageHandler paramMessageHandler) throws IOException {
/* 199 */     paramMessageHandler.handleInput(this);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\protocol\giopmsgheaders\ReplyMessage_1_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */