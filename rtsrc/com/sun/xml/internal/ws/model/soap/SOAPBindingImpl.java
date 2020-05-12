/*    */ package com.sun.xml.internal.ws.model.soap;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*    */ import com.sun.xml.internal.ws.api.model.soap.SOAPBinding;
/*    */ import javax.jws.soap.SOAPBinding;
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
/*    */ public class SOAPBindingImpl
/*    */   extends SOAPBinding
/*    */ {
/*    */   public SOAPBindingImpl() {}
/*    */   
/*    */   public SOAPBindingImpl(SOAPBinding sb) {
/* 45 */     this.use = sb.getUse();
/* 46 */     this.style = sb.getStyle();
/* 47 */     this.soapVersion = sb.getSOAPVersion();
/* 48 */     this.soapAction = sb.getSOAPAction();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setStyle(SOAPBinding.Style style) {
/* 55 */     this.style = style;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSOAPVersion(SOAPVersion version) {
/* 62 */     this.soapVersion = version;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSOAPAction(String soapAction) {
/* 69 */     this.soapAction = soapAction;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\model\soap\SOAPBindingImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */