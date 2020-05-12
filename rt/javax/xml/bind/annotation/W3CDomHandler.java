/*     */ package javax.xml.bind.annotation;
/*     */ 
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class W3CDomHandler
/*     */   implements DomHandler<Element, DOMResult>
/*     */ {
/*     */   private DocumentBuilder builder;
/*     */   
/*     */   public W3CDomHandler() {
/*  56 */     this.builder = null;
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
/*     */   public W3CDomHandler(DocumentBuilder builder) {
/*  68 */     if (builder == null)
/*  69 */       throw new IllegalArgumentException(); 
/*  70 */     this.builder = builder;
/*     */   }
/*     */   
/*     */   public DocumentBuilder getBuilder() {
/*  74 */     return this.builder;
/*     */   }
/*     */   
/*     */   public void setBuilder(DocumentBuilder builder) {
/*  78 */     this.builder = builder;
/*     */   }
/*     */   
/*     */   public DOMResult createUnmarshaller(ValidationEventHandler errorHandler) {
/*  82 */     if (this.builder == null) {
/*  83 */       return new DOMResult();
/*     */     }
/*  85 */     return new DOMResult(this.builder.newDocument());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getElement(DOMResult r) {
/*  91 */     Node n = r.getNode();
/*  92 */     if (n instanceof Document) {
/*  93 */       return ((Document)n).getDocumentElement();
/*     */     }
/*  95 */     if (n instanceof Element)
/*  96 */       return (Element)n; 
/*  97 */     if (n instanceof org.w3c.dom.DocumentFragment) {
/*  98 */       return (Element)n.getChildNodes().item(0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 103 */     throw new IllegalStateException(n.toString());
/*     */   }
/*     */   
/*     */   public Source marshal(Element element, ValidationEventHandler errorHandler) {
/* 107 */     return new DOMSource(element);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\bind\annotation\W3CDomHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */