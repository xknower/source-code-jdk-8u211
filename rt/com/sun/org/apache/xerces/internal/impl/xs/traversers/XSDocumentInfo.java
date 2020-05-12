/*     */ package com.sun.org.apache.xerces.internal.impl.xs.traversers;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.validation.ValidationState;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaException;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.util.XInt;
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import java.util.Stack;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
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
/*     */ class XSDocumentInfo
/*     */ {
/*     */   protected SchemaNamespaceSupport fNamespaceSupport;
/*     */   protected SchemaNamespaceSupport fNamespaceSupportRoot;
/*  53 */   protected Stack SchemaNamespaceSupportStack = new Stack();
/*     */ 
/*     */   
/*     */   protected boolean fAreLocalAttributesQualified;
/*     */ 
/*     */   
/*     */   protected boolean fAreLocalElementsQualified;
/*     */ 
/*     */   
/*     */   protected short fBlockDefault;
/*     */ 
/*     */   
/*     */   protected short fFinalDefault;
/*     */ 
/*     */   
/*     */   String fTargetNamespace;
/*     */ 
/*     */   
/*     */   protected boolean fIsChameleonSchema;
/*     */   
/*     */   protected Element fSchemaElement;
/*     */   
/*  75 */   Vector fImportedNS = new Vector();
/*     */   
/*  77 */   protected ValidationState fValidationContext = new ValidationState();
/*     */   
/*  79 */   SymbolTable fSymbolTable = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected XSAttributeChecker fAttrChecker;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object[] fSchemaAttrs;
/*     */ 
/*     */ 
/*     */   
/*  91 */   protected XSAnnotationInfo fAnnotations = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector fReportedTNS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initNamespaceSupport(Element schemaRoot) {
/* 150 */     this.fNamespaceSupport = new SchemaNamespaceSupport();
/* 151 */     this.fNamespaceSupport.reset();
/*     */     
/* 153 */     Node parent = schemaRoot.getParentNode();
/* 154 */     while (parent != null && parent.getNodeType() == 1 && 
/* 155 */       !parent.getNodeName().equals("DOCUMENT_NODE")) {
/*     */       
/* 157 */       Element eparent = (Element)parent;
/* 158 */       NamedNodeMap map = eparent.getAttributes();
/* 159 */       int length = (map != null) ? map.getLength() : 0;
/* 160 */       for (int i = 0; i < length; i++) {
/* 161 */         Attr attr = (Attr)map.item(i);
/* 162 */         String uri = attr.getNamespaceURI();
/*     */ 
/*     */         
/* 165 */         if (uri != null && uri.equals("http://www.w3.org/2000/xmlns/")) {
/* 166 */           String prefix = attr.getLocalName().intern();
/* 167 */           if (prefix == "xmlns") prefix = "";
/*     */           
/* 169 */           if (this.fNamespaceSupport.getURI(prefix) == null) {
/* 170 */             this.fNamespaceSupport.declarePrefix(prefix, attr
/* 171 */                 .getValue().intern());
/*     */           }
/*     */         } 
/*     */       } 
/* 175 */       parent = parent.getParentNode();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void backupNSSupport(SchemaNamespaceSupport nsSupport) {
/* 182 */     this.SchemaNamespaceSupportStack.push(this.fNamespaceSupport);
/* 183 */     if (nsSupport == null)
/* 184 */       nsSupport = this.fNamespaceSupportRoot; 
/* 185 */     this.fNamespaceSupport = new SchemaNamespaceSupport(nsSupport);
/*     */     
/* 187 */     this.fValidationContext.setNamespaceSupport(this.fNamespaceSupport);
/*     */   }
/*     */   
/*     */   void restoreNSSupport() {
/* 191 */     this.fNamespaceSupport = this.SchemaNamespaceSupportStack.pop();
/* 192 */     this.fValidationContext.setNamespaceSupport(this.fNamespaceSupport);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 197 */     return (this.fTargetNamespace == null) ? "no targetNamspace" : ("targetNamespace is " + this.fTargetNamespace);
/*     */   }
/*     */   
/*     */   public void addAllowedNS(String namespace) {
/* 201 */     this.fImportedNS.addElement((namespace == null) ? "" : namespace);
/*     */   }
/*     */   
/*     */   public boolean isAllowedNS(String namespace) {
/* 205 */     return this.fImportedNS.contains((namespace == null) ? "" : namespace);
/*     */   }
/*     */ 
/*     */   
/*     */   XSDocumentInfo(Element schemaRoot, XSAttributeChecker attrChecker, SymbolTable symbolTable) throws XMLSchemaException {
/* 210 */     this.fReportedTNS = null; this.fSchemaElement = schemaRoot; initNamespaceSupport(schemaRoot); this.fIsChameleonSchema = false; this.fSymbolTable = symbolTable; this.fAttrChecker = attrChecker; if (schemaRoot != null) {
/*     */       Element root = schemaRoot; this.fSchemaAttrs = attrChecker.checkAttributes(root, true, this); if (this.fSchemaAttrs == null)
/*     */         throw new XMLSchemaException(null, null);  this.fAreLocalAttributesQualified = (((XInt)this.fSchemaAttrs[XSAttributeChecker.ATTIDX_AFORMDEFAULT]).intValue() == 1); this.fAreLocalElementsQualified = (((XInt)this.fSchemaAttrs[XSAttributeChecker.ATTIDX_EFORMDEFAULT]).intValue() == 1); this.fBlockDefault = ((XInt)this.fSchemaAttrs[XSAttributeChecker.ATTIDX_BLOCKDEFAULT]).shortValue(); this.fFinalDefault = ((XInt)this.fSchemaAttrs[XSAttributeChecker.ATTIDX_FINALDEFAULT]).shortValue(); this.fTargetNamespace = (String)this.fSchemaAttrs[XSAttributeChecker.ATTIDX_TARGETNAMESPACE]; if (this.fTargetNamespace != null)
/*     */         this.fTargetNamespace = symbolTable.addSymbol(this.fTargetNamespace);  this.fNamespaceSupportRoot = new SchemaNamespaceSupport(this.fNamespaceSupport); this.fValidationContext.setNamespaceSupport(this.fNamespaceSupport);
/*     */       this.fValidationContext.setSymbolTable(symbolTable);
/* 215 */     }  } final boolean needReportTNSError(String uri) { if (this.fReportedTNS == null) {
/* 216 */       this.fReportedTNS = new Vector();
/* 217 */     } else if (this.fReportedTNS.contains(uri)) {
/* 218 */       return false;
/* 219 */     }  this.fReportedTNS.addElement(uri);
/* 220 */     return true; }
/*     */ 
/*     */ 
/*     */   
/*     */   Object[] getSchemaAttrs() {
/* 225 */     return this.fSchemaAttrs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void returnSchemaAttrs() {
/* 231 */     this.fAttrChecker.returnAttrArray(this.fSchemaAttrs, null);
/* 232 */     this.fSchemaAttrs = null;
/*     */   }
/*     */ 
/*     */   
/*     */   void addAnnotation(XSAnnotationInfo info) {
/* 237 */     info.next = this.fAnnotations;
/* 238 */     this.fAnnotations = info;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   XSAnnotationInfo getAnnotations() {
/* 244 */     return this.fAnnotations;
/*     */   }
/*     */ 
/*     */   
/*     */   void removeAnnotations() {
/* 249 */     this.fAnnotations = null;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\impl\xs\traversers\XSDocumentInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */