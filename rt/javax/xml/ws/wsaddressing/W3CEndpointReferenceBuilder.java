/*     */ package javax.xml.ws.wsaddressing;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.spi.Provider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class W3CEndpointReferenceBuilder
/*     */ {
/*     */   private String address;
/*  72 */   private List<Element> referenceParameters = new ArrayList<>();
/*  73 */   private List<Element> metadata = new ArrayList<>(); private QName interfaceName; private QName serviceName;
/*  74 */   private Map<QName, String> attributes = new HashMap<>(); private QName endpointName; private String wsdlDocumentLocation;
/*  75 */   private List<Element> elements = new ArrayList<>();
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
/*     */   public W3CEndpointReferenceBuilder address(String address) {
/*  95 */     this.address = address;
/*  96 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public W3CEndpointReferenceBuilder interfaceName(QName interfaceName) {
/* 115 */     this.interfaceName = interfaceName;
/* 116 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public W3CEndpointReferenceBuilder serviceName(QName serviceName) {
/* 139 */     this.serviceName = serviceName;
/* 140 */     return this;
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
/*     */   public W3CEndpointReferenceBuilder endpointName(QName endpointName) {
/* 171 */     if (this.serviceName == null) {
/* 172 */       throw new IllegalStateException("The W3CEndpointReferenceBuilder's serviceName must be set before setting the endpointName: " + endpointName);
/*     */     }
/*     */     
/* 175 */     this.endpointName = endpointName;
/* 176 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public W3CEndpointReferenceBuilder wsdlDocumentLocation(String wsdlDocumentLocation) {
/* 195 */     this.wsdlDocumentLocation = wsdlDocumentLocation;
/* 196 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public W3CEndpointReferenceBuilder referenceParameter(Element referenceParameter) {
/* 215 */     if (referenceParameter == null)
/* 216 */       throw new IllegalArgumentException("The referenceParameter cannot be null."); 
/* 217 */     this.referenceParameters.add(referenceParameter);
/* 218 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public W3CEndpointReferenceBuilder metadata(Element metadataElement) {
/* 237 */     if (metadataElement == null)
/* 238 */       throw new IllegalArgumentException("The metadataElement cannot be null."); 
/* 239 */     this.metadata.add(metadataElement);
/* 240 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public W3CEndpointReferenceBuilder element(Element element) {
/* 259 */     if (element == null) {
/* 260 */       throw new IllegalArgumentException("The extension element cannot be null.");
/*     */     }
/* 262 */     this.elements.add(element);
/* 263 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public W3CEndpointReferenceBuilder attribute(QName name, String value) {
/* 283 */     if (name == null || value == null) {
/* 284 */       throw new IllegalArgumentException("The extension attribute name or value cannot be null.");
/*     */     }
/* 286 */     this.attributes.put(name, value);
/* 287 */     return this;
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
/*     */   public W3CEndpointReference build() {
/* 336 */     if (this.elements.isEmpty() && this.attributes.isEmpty() && this.interfaceName == null)
/*     */     {
/* 338 */       return Provider.provider().createW3CEndpointReference(this.address, this.serviceName, this.endpointName, this.metadata, this.wsdlDocumentLocation, this.referenceParameters);
/*     */     }
/*     */ 
/*     */     
/* 342 */     return Provider.provider().createW3CEndpointReference(this.address, this.interfaceName, this.serviceName, this.endpointName, this.metadata, this.wsdlDocumentLocation, this.referenceParameters, this.elements, this.attributes);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\ws\wsaddressing\W3CEndpointReferenceBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */