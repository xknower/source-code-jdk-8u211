/*     */ package com.sun.xml.internal.ws.message;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.encoding.MimeMultipartParser;
/*     */ import com.sun.xml.internal.ws.resources.EncodingMessages;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public final class MimeAttachmentSet
/*     */   implements AttachmentSet
/*     */ {
/*     */   private final MimeMultipartParser mpp;
/*  47 */   private Map<String, Attachment> atts = new HashMap<>();
/*     */ 
/*     */   
/*     */   public MimeAttachmentSet(MimeMultipartParser mpp) {
/*  51 */     this.mpp = mpp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Attachment get(String contentId) {
/*  61 */     Attachment att = this.atts.get(contentId);
/*  62 */     if (att != null) {
/*  63 */       return att;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  69 */       att = this.mpp.getAttachmentPart(contentId);
/*  70 */       if (att != null) {
/*  71 */         this.atts.put(contentId, att);
/*     */       }
/*  73 */     } catch (IOException e) {
/*  74 */       throw new WebServiceException(EncodingMessages.NO_SUCH_CONTENT_ID(contentId), e);
/*     */     } 
/*  76 */     return att;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  84 */     return (this.atts.size() <= 0 && this.mpp.getAttachmentParts().isEmpty());
/*     */   }
/*     */   
/*     */   public void add(Attachment att) {
/*  88 */     this.atts.put(att.getContentId(), att);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Attachment> iterator() {
/*  99 */     Map<String, Attachment> attachments = this.mpp.getAttachmentParts();
/* 100 */     for (Map.Entry<String, Attachment> att : attachments.entrySet()) {
/* 101 */       if (this.atts.get(att.getKey()) == null) {
/* 102 */         this.atts.put(att.getKey(), att.getValue());
/*     */       }
/*     */     } 
/*     */     
/* 106 */     return this.atts.values().iterator();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\message\MimeAttachmentSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */