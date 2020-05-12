/*    */ package javax.xml.crypto.dsig.spec;
/*    */ 
/*    */ import javax.xml.crypto.XMLStructure;
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
/*    */ public final class XSLTTransformParameterSpec
/*    */   implements TransformParameterSpec
/*    */ {
/*    */   private XMLStructure stylesheet;
/*    */   
/*    */   public XSLTTransformParameterSpec(XMLStructure paramXMLStructure) {
/* 64 */     if (paramXMLStructure == null) {
/* 65 */       throw new NullPointerException();
/*    */     }
/* 67 */     this.stylesheet = paramXMLStructure;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLStructure getStylesheet() {
/* 76 */     return this.stylesheet;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\crypto\dsig\spec\XSLTTransformParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */