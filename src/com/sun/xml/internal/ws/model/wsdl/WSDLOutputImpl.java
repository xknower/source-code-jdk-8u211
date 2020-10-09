/*    */ package com.sun.xml.internal.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtension;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLMessage;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLOperation;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLMessage;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOperation;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOutput;
/*    */ import java.util.List;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import org.xml.sax.Locator;
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
/*    */ public final class WSDLOutputImpl
/*    */   extends AbstractExtensibleImpl
/*    */   implements EditableWSDLOutput
/*    */ {
/*    */   private String name;
/*    */   private QName messageName;
/*    */   private EditableWSDLOperation operation;
/*    */   private EditableWSDLMessage message;
/*    */   private String action;
/*    */   private boolean defaultAction = true;
/*    */   
/*    */   public WSDLOutputImpl(XMLStreamReader xsr, String name, QName messageName, EditableWSDLOperation operation) {
/* 49 */     super(xsr);
/* 50 */     this.name = name;
/* 51 */     this.messageName = messageName;
/* 52 */     this.operation = operation;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 56 */     return (this.name == null) ? (this.operation.getName().getLocalPart() + "Response") : this.name;
/*    */   }
/*    */   
/*    */   public EditableWSDLMessage getMessage() {
/* 60 */     return this.message;
/*    */   }
/*    */   
/*    */   public String getAction() {
/* 64 */     return this.action;
/*    */   }
/*    */   
/*    */   public boolean isDefaultAction() {
/* 68 */     return this.defaultAction;
/*    */   }
/*    */   
/*    */   public void setDefaultAction(boolean defaultAction) {
/* 72 */     this.defaultAction = defaultAction;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public EditableWSDLOperation getOperation() {
/* 77 */     return this.operation;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public QName getQName() {
/* 82 */     return new QName(this.operation.getName().getNamespaceURI(), getName());
/*    */   }
/*    */   
/*    */   public void setAction(String action) {
/* 86 */     this.action = action;
/*    */   }
/*    */   
/*    */   public void freeze(EditableWSDLModel root) {
/* 90 */     this.message = root.getMessage(this.messageName);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\model\wsdl\WSDLOutputImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */