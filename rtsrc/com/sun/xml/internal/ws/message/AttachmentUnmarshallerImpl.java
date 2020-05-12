/*    */ package com.sun.xml.internal.ws.message;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.message.Attachment;
/*    */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*    */ import com.sun.xml.internal.ws.resources.EncodingMessages;
/*    */ import javax.activation.DataHandler;
/*    */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ 
/*    */ public final class AttachmentUnmarshallerImpl
/*    */   extends AttachmentUnmarshaller
/*    */ {
/*    */   private final AttachmentSet attachments;
/*    */   
/*    */   public AttachmentUnmarshallerImpl(AttachmentSet attachments) {
/* 49 */     this.attachments = attachments;
/*    */   }
/*    */ 
/*    */   
/*    */   public DataHandler getAttachmentAsDataHandler(String cid) {
/* 54 */     Attachment a = this.attachments.get(stripScheme(cid));
/* 55 */     if (a == null)
/* 56 */       throw new WebServiceException(EncodingMessages.NO_SUCH_CONTENT_ID(cid)); 
/* 57 */     return a.asDataHandler();
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getAttachmentAsByteArray(String cid) {
/* 62 */     Attachment a = this.attachments.get(stripScheme(cid));
/* 63 */     if (a == null)
/* 64 */       throw new WebServiceException(EncodingMessages.NO_SUCH_CONTENT_ID(cid)); 
/* 65 */     return a.asByteArray();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String stripScheme(String cid) {
/* 72 */     if (cid.startsWith("cid:"))
/* 73 */       cid = cid.substring(4); 
/* 74 */     return cid;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\message\AttachmentUnmarshallerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */