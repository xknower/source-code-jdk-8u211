/*     */ package javax.xml.transform.stax;
/*     */ 
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXResult
/*     */   implements Result
/*     */ {
/*     */   public static final String FEATURE = "http://javax.xml.transform.stax.StAXResult/feature";
/*  63 */   private XMLEventWriter xmlEventWriter = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private XMLStreamWriter xmlStreamWriter = null;
/*     */ 
/*     */   
/*  72 */   private String systemId = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StAXResult(XMLEventWriter xmlEventWriter) {
/*  89 */     if (xmlEventWriter == null) {
/*  90 */       throw new IllegalArgumentException("StAXResult(XMLEventWriter) with XMLEventWriter == null");
/*     */     }
/*     */ 
/*     */     
/*  94 */     this.xmlEventWriter = xmlEventWriter;
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
/*     */   public StAXResult(XMLStreamWriter xmlStreamWriter) {
/* 112 */     if (xmlStreamWriter == null) {
/* 113 */       throw new IllegalArgumentException("StAXResult(XMLStreamWriter) with XMLStreamWriter == null");
/*     */     }
/*     */ 
/*     */     
/* 117 */     this.xmlStreamWriter = xmlStreamWriter;
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
/*     */   public XMLEventWriter getXMLEventWriter() {
/* 133 */     return this.xmlEventWriter;
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
/*     */   public XMLStreamWriter getXMLStreamWriter() {
/* 149 */     return this.xmlStreamWriter;
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
/*     */   public void setSystemId(String systemId) {
/* 169 */     throw new UnsupportedOperationException("StAXResult#setSystemId(systemId) cannot set the system identifier for a StAXResult");
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
/*     */   public String getSystemId() {
/* 181 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\transform\stax\StAXResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */