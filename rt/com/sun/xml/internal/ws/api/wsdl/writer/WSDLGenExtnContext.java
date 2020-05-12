/*    */ package com.sun.xml.internal.ws.api.wsdl.writer;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.istack.internal.Nullable;
/*    */ import com.sun.xml.internal.txw2.TypedXmlWriter;
/*    */ import com.sun.xml.internal.ws.api.WSBinding;
/*    */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*    */ import com.sun.xml.internal.ws.api.server.Container;
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
/*    */ public class WSDLGenExtnContext
/*    */ {
/*    */   private final TypedXmlWriter root;
/*    */   private final SEIModel model;
/*    */   private final WSBinding binding;
/*    */   private final Container container;
/*    */   private final Class endpointClass;
/*    */   
/*    */   public WSDLGenExtnContext(@NotNull TypedXmlWriter root, @NotNull SEIModel model, @NotNull WSBinding binding, @Nullable Container container, @NotNull Class endpointClass) {
/* 63 */     this.root = root;
/* 64 */     this.model = model;
/* 65 */     this.binding = binding;
/* 66 */     this.container = container;
/* 67 */     this.endpointClass = endpointClass;
/*    */   }
/*    */   
/*    */   public TypedXmlWriter getRoot() {
/* 71 */     return this.root;
/*    */   }
/*    */   
/*    */   public SEIModel getModel() {
/* 75 */     return this.model;
/*    */   }
/*    */   
/*    */   public WSBinding getBinding() {
/* 79 */     return this.binding;
/*    */   }
/*    */   
/*    */   public Container getContainer() {
/* 83 */     return this.container;
/*    */   }
/*    */   
/*    */   public Class getEndpointClass() {
/* 87 */     return this.endpointClass;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\api\wsdl\writer\WSDLGenExtnContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */