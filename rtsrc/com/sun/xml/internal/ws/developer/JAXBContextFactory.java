/*    */ package com.sun.xml.internal.ws.developer;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.bind.api.JAXBRIContext;
/*    */ import com.sun.xml.internal.bind.api.TypeReference;
/*    */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.JAXBException;
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
/*    */ public interface JAXBContextFactory
/*    */ {
/* 95 */   public static final JAXBContextFactory DEFAULT = new JAXBContextFactory() {
/*    */       @NotNull
/*    */       public JAXBRIContext createJAXBContext(@NotNull SEIModel sei, @NotNull List<Class<?>> classesToBind, @NotNull List<TypeReference> typeReferences) throws JAXBException {
/* 98 */         return JAXBRIContext.newInstance((Class[])classesToBind.<Class<?>[]>toArray((Class<?>[][])new Class[classesToBind.size()]), typeReferences, null, sei
/* 99 */             .getTargetNamespace(), false, null);
/*    */       }
/*    */     };
/*    */   
/*    */   @NotNull
/*    */   JAXBRIContext createJAXBContext(@NotNull SEIModel paramSEIModel, @NotNull List<Class> paramList, @NotNull List<TypeReference> paramList1) throws JAXBException;
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\developer\JAXBContextFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */