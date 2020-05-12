/*    */ package com.sun.xml.internal.ws.message;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.message.Attachment;
/*    */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
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
/*    */ 
/*    */ public final class AttachmentSetImpl
/*    */   implements AttachmentSet
/*    */ {
/* 45 */   private final ArrayList<Attachment> attList = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AttachmentSetImpl() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AttachmentSetImpl(Iterable<Attachment> base) {
/* 57 */     for (Attachment a : base)
/* 58 */       add(a); 
/*    */   }
/*    */   
/*    */   public Attachment get(String contentId) {
/* 62 */     for (int i = this.attList.size() - 1; i >= 0; i--) {
/* 63 */       Attachment a = this.attList.get(i);
/* 64 */       if (a.getContentId().equals(contentId))
/* 65 */         return a; 
/*    */     } 
/* 67 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 71 */     return this.attList.isEmpty();
/*    */   }
/*    */   
/*    */   public void add(Attachment att) {
/* 75 */     this.attList.add(att);
/*    */   }
/*    */   
/*    */   public Iterator<Attachment> iterator() {
/* 79 */     return this.attList.iterator();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\message\AttachmentSetImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */