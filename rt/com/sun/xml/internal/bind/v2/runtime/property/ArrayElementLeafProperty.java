/*    */ package com.sun.xml.internal.bind.v2.runtime.property;
/*    */ 
/*    */ import com.sun.xml.internal.bind.api.AccessorException;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeElementPropertyInfo;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeRef;
/*    */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*    */ import com.sun.xml.internal.bind.v2.runtime.JaxBeanInfo;
/*    */ import com.sun.xml.internal.bind.v2.runtime.Transducer;
/*    */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*    */ import java.io.IOException;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import org.xml.sax.SAXException;
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
/*    */ final class ArrayElementLeafProperty<BeanT, ListT, ItemT>
/*    */   extends ArrayElementProperty<BeanT, ListT, ItemT>
/*    */ {
/*    */   private final Transducer<ItemT> xducer;
/*    */   
/*    */   public ArrayElementLeafProperty(JAXBContextImpl p, RuntimeElementPropertyInfo prop) {
/* 55 */     super(p, prop);
/*    */ 
/*    */     
/* 58 */     assert prop.getTypes().size() == 1;
/*    */ 
/*    */     
/* 61 */     this.xducer = ((RuntimeTypeRef)prop.getTypes().get(0)).getTransducer();
/* 62 */     assert this.xducer != null;
/*    */   }
/*    */   
/*    */   public void serializeItem(JaxBeanInfo bi, ItemT item, XMLSerializer w) throws SAXException, AccessorException, IOException, XMLStreamException {
/* 66 */     this.xducer.declareNamespace(item, w);
/* 67 */     w.endNamespaceDecls(item);
/* 68 */     w.endAttributes();
/*    */ 
/*    */     
/* 71 */     this.xducer.writeText(w, item, this.fieldName);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtime\property\ArrayElementLeafProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */