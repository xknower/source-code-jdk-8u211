/*    */ package com.sun.xml.internal.ws.message;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.message.Message;
/*    */ import org.xml.sax.ContentHandler;
/*    */ import org.xml.sax.InputSource;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.helpers.DefaultHandler;
/*    */ import org.xml.sax.helpers.XMLFilterImpl;
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
/*    */ final class XMLReaderImpl
/*    */   extends XMLFilterImpl
/*    */ {
/*    */   private final Message msg;
/*    */   
/*    */   XMLReaderImpl(Message msg) {
/* 45 */     this.msg = msg;
/*    */   }
/*    */   
/*    */   public void parse(String systemId) {
/* 49 */     reportError();
/*    */   }
/*    */ 
/*    */   
/*    */   private void reportError() {
/* 54 */     throw new IllegalStateException("This is a special XMLReader implementation that only works with the InputSource given in SAXSource.");
/*    */   }
/*    */ 
/*    */   
/*    */   public void parse(InputSource input) throws SAXException {
/* 59 */     if (input != THE_SOURCE)
/* 60 */       reportError(); 
/* 61 */     this.msg.writeTo(this, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public ContentHandler getContentHandler() {
/* 66 */     if (super.getContentHandler() == DUMMY) return null; 
/* 67 */     return super.getContentHandler();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setContentHandler(ContentHandler contentHandler) {
/* 72 */     if (contentHandler == null) contentHandler = DUMMY; 
/* 73 */     super.setContentHandler(contentHandler);
/*    */   }
/*    */   
/* 76 */   private static final ContentHandler DUMMY = new DefaultHandler();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 81 */   protected static final InputSource THE_SOURCE = new InputSource();
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\message\XMLReaderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */