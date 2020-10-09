/*    */ package com.sun.xml.internal.ws.message.saaj;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*    */ import com.sun.xml.internal.ws.message.DOMHeader;
/*    */ import javax.xml.soap.SOAPHeaderElement;
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
/*    */ public final class SAAJHeader
/*    */   extends DOMHeader<SOAPHeaderElement>
/*    */ {
/*    */   public SAAJHeader(SOAPHeaderElement header) {
/* 44 */     super(header);
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public String getRole(@NotNull SOAPVersion soapVersion) {
/* 50 */     String v = getAttribute(soapVersion.nsUri, soapVersion.roleAttributeName);
/* 51 */     if (v == null || v.equals(""))
/* 52 */       v = soapVersion.implicitRole; 
/* 53 */     return v;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\message\saaj\SAAJHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */