/*    */ package com.sun.xml.internal.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtension;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLMessage;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLOperation;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLInput;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLMessage;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOperation;
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
/*    */ public final class WSDLInputImpl
/*    */   extends AbstractExtensibleImpl
/*    */   implements EditableWSDLInput
/*    */ {
/*    */   private String name;
/*    */   private QName messageName;
/*    */   private EditableWSDLOperation operation;
/*    */   private EditableWSDLMessage message;
/*    */   private String action;
/*    */   private boolean defaultAction = true;
/*    */   
/*    */   public WSDLInputImpl(XMLStreamReader xsr, String name, QName messageName, EditableWSDLOperation operation) {
/* 49 */     super(xsr);
/* 50 */     this.name = name;
/* 51 */     this.messageName = messageName;
/* 52 */     this.operation = operation;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 56 */     if (this.name != null) {
/* 57 */       return this.name;
/*    */     }
/* 59 */     return this.operation.isOneWay() ? this.operation.getName().getLocalPart() : (this.operation.getName().getLocalPart() + "Request");
/*    */   }
/*    */   
/*    */   public EditableWSDLMessage getMessage() {
/* 63 */     return this.message;
/*    */   }
/*    */   
/*    */   public String getAction() {
/* 67 */     return this.action;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public EditableWSDLOperation getOperation() {
/* 72 */     return this.operation;
/*    */   }
/*    */   
/*    */   public QName getQName() {
/* 76 */     return new QName(this.operation.getName().getNamespaceURI(), getName());
/*    */   }
/*    */   
/*    */   public void setAction(String action) {
/* 80 */     this.action = action;
/*    */   }
/*    */   
/*    */   public boolean isDefaultAction() {
/* 84 */     return this.defaultAction;
/*    */   }
/*    */   
/*    */   public void setDefaultAction(boolean defaultAction) {
/* 88 */     this.defaultAction = defaultAction;
/*    */   }
/*    */   
/*    */   public void freeze(EditableWSDLModel parent) {
/* 92 */     this.message = parent.getMessage(this.messageName);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\model\wsdl\WSDLInputImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */