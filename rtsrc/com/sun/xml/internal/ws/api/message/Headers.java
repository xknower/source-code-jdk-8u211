/*     */ package com.sun.xml.internal.ws.api.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.db.glassfish.BridgeWrapper;
/*     */ import com.sun.xml.internal.ws.message.DOMHeader;
/*     */ import com.sun.xml.internal.ws.message.StringHeader;
/*     */ import com.sun.xml.internal.ws.message.jaxb.JAXBHeader;
/*     */ import com.sun.xml.internal.ws.message.saaj.SAAJHeader;
/*     */ import com.sun.xml.internal.ws.message.stream.StreamHeader11;
/*     */ import com.sun.xml.internal.ws.message.stream.StreamHeader12;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContextFactory;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPHeaderElement;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.w3c.dom.Element;
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
/*     */ public abstract class Headers
/*     */ {
/*     */   public static Header create(SOAPVersion soapVersion, Marshaller m, Object o) {
/*  83 */     return new JAXBHeader(BindingContextFactory.getBindingContext(m), o);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header create(JAXBContext context, Object o) {
/*  90 */     return new JAXBHeader(BindingContextFactory.create(context), o);
/*     */   }
/*     */   
/*     */   public static Header create(BindingContext context, Object o) {
/*  94 */     return new JAXBHeader(context, o);
/*     */   }
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
/*     */   public static Header create(SOAPVersion soapVersion, Marshaller m, QName tagName, Object o) {
/* 109 */     return create(soapVersion, m, new JAXBElement(tagName, o.getClass(), o));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header create(Bridge<?> bridge, Object jaxbObject) {
/* 117 */     return new JAXBHeader(new BridgeWrapper(null, bridge), jaxbObject);
/*     */   }
/*     */   
/*     */   public static Header create(XMLBridge bridge, Object jaxbObject) {
/* 121 */     return new JAXBHeader(bridge, jaxbObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header create(SOAPHeaderElement header) {
/* 128 */     return new SAAJHeader(header);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header create(Element node) {
/* 135 */     return new DOMHeader<>(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header create(SOAPVersion soapVersion, Element node) {
/* 143 */     return create(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header create(SOAPVersion soapVersion, XMLStreamReader reader) throws XMLStreamException {
/* 154 */     switch (soapVersion) {
/*     */       case SOAP_11:
/* 156 */         return new StreamHeader11(reader);
/*     */       case SOAP_12:
/* 158 */         return new StreamHeader12(reader);
/*     */     } 
/* 160 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header create(QName name, String value) {
/* 172 */     return new StringHeader(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header createMustUnderstand(@NotNull SOAPVersion soapVersion, @NotNull QName name, @NotNull String value) {
/* 183 */     return new StringHeader(name, value, soapVersion, true);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\message\Headers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */