/*     */ package javax.xml.crypto.dsig.dom;
/*     */ 
/*     */ import java.security.Key;
/*     */ import javax.xml.crypto.KeySelector;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dsig.XMLValidateContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DOMValidateContext
/*     */   extends DOMCryptoContext
/*     */   implements XMLValidateContext
/*     */ {
/*     */   private Node node;
/*     */   
/*     */   public DOMValidateContext(KeySelector paramKeySelector, Node paramNode) {
/*  74 */     if (paramKeySelector == null) {
/*  75 */       throw new NullPointerException("key selector is null");
/*     */     }
/*  77 */     init(paramNode, paramKeySelector);
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
/*     */   public DOMValidateContext(Key paramKey, Node paramNode) {
/*  93 */     if (paramKey == null) {
/*  94 */       throw new NullPointerException("validatingKey is null");
/*     */     }
/*  96 */     init(paramNode, KeySelector.singletonKeySelector(paramKey));
/*     */   }
/*     */   
/*     */   private void init(Node paramNode, KeySelector paramKeySelector) {
/* 100 */     if (paramNode == null) {
/* 101 */       throw new NullPointerException("node is null");
/*     */     }
/*     */     
/* 104 */     this.node = paramNode;
/* 105 */     setKeySelector(paramKeySelector);
/* 106 */     if (System.getSecurityManager() != null) {
/* 107 */       setProperty("org.jcp.xml.dsig.secureValidation", Boolean.TRUE);
/*     */     }
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
/*     */   public void setNode(Node paramNode) {
/* 120 */     if (paramNode == null) {
/* 121 */       throw new NullPointerException();
/*     */     }
/* 123 */     this.node = paramNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getNode() {
/* 133 */     return this.node;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\crypto\dsig\dom\DOMValidateContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */