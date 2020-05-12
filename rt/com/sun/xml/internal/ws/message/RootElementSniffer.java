/*    */ package com.sun.xml.internal.ws.message;
/*    */ 
/*    */ import org.xml.sax.Attributes;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.helpers.AttributesImpl;
/*    */ import org.xml.sax.helpers.DefaultHandler;
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
/*    */ public final class RootElementSniffer
/*    */   extends DefaultHandler
/*    */ {
/* 39 */   private String nsUri = "##error";
/* 40 */   private String localName = "##error";
/*    */   
/*    */   private Attributes atts;
/*    */   private final boolean parseAttributes;
/*    */   
/*    */   public RootElementSniffer(boolean parseAttributes) {
/* 46 */     this.parseAttributes = parseAttributes;
/*    */   }
/*    */   
/*    */   public RootElementSniffer() {
/* 50 */     this(true);
/*    */   }
/*    */   
/*    */   public void startElement(String uri, String localName, String qName, Attributes a) throws SAXException {
/* 54 */     this.nsUri = uri;
/* 55 */     this.localName = localName;
/*    */     
/* 57 */     if (this.parseAttributes) {
/* 58 */       if (a.getLength() == 0) {
/* 59 */         this.atts = EMPTY_ATTRIBUTES;
/*    */       } else {
/* 61 */         this.atts = new AttributesImpl(a);
/*    */       } 
/*    */     }
/*    */     
/* 65 */     throw aSAXException;
/*    */   }
/*    */   
/*    */   public String getNsUri() {
/* 69 */     return this.nsUri;
/*    */   }
/*    */   
/*    */   public String getLocalName() {
/* 73 */     return this.localName;
/*    */   }
/*    */   
/*    */   public Attributes getAttributes() {
/* 77 */     return this.atts;
/*    */   }
/*    */   
/* 80 */   private static final SAXException aSAXException = new SAXException();
/* 81 */   private static final Attributes EMPTY_ATTRIBUTES = new AttributesImpl();
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\message\RootElementSniffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */