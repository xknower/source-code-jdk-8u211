/*    */ package com.sun.xml.internal.ws.message;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.SOAPException;
/*    */ import javax.xml.soap.SOAPHeader;
/*    */ import javax.xml.soap.SOAPHeaderElement;
/*    */ import javax.xml.soap.SOAPMessage;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamWriter;
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
/*    */ public final class RelatesToHeader
/*    */   extends StringHeader
/*    */ {
/*    */   protected String type;
/*    */   private final QName typeAttributeName;
/*    */   
/*    */   public RelatesToHeader(QName name, String messageId, String type) {
/* 48 */     super(name, messageId);
/* 49 */     this.type = type;
/* 50 */     this.typeAttributeName = new QName(name.getNamespaceURI(), "type");
/*    */   }
/*    */   
/*    */   public RelatesToHeader(QName name, String mid) {
/* 54 */     super(name, mid);
/* 55 */     this.typeAttributeName = new QName(name.getNamespaceURI(), "type");
/*    */   }
/*    */   
/*    */   public String getType() {
/* 59 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 64 */     w.writeStartElement("", this.name.getLocalPart(), this.name.getNamespaceURI());
/* 65 */     w.writeDefaultNamespace(this.name.getNamespaceURI());
/* 66 */     if (this.type != null)
/* 67 */       w.writeAttribute("type", this.type); 
/* 68 */     w.writeCharacters(this.value);
/* 69 */     w.writeEndElement();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 74 */     SOAPHeader header = saaj.getSOAPHeader();
/* 75 */     if (header == null)
/* 76 */       header = saaj.getSOAPPart().getEnvelope().addHeader(); 
/* 77 */     SOAPHeaderElement she = header.addHeaderElement(this.name);
/*    */     
/* 79 */     if (this.type != null)
/* 80 */       she.addAttribute(this.typeAttributeName, this.type); 
/* 81 */     she.addTextNode(this.value);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\message\RelatesToHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */