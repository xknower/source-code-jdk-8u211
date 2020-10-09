/*     */ package com.sun.xml.internal.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.AccessorException;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeElementPropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeRef;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Name;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.ListTransducedAccessorImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.TransducedAccessor;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.ChildLoader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.DefaultValueLoaderDecorator;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LeafPropertyLoader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.internal.bind.v2.util.QNameMap;
/*     */ import java.io.IOException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.SAXException;
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
/*     */ final class ListElementProperty<BeanT, ListT, ItemT>
/*     */   extends ArrayProperty<BeanT, ListT, ItemT>
/*     */ {
/*     */   private final Name tagName;
/*     */   private final String defaultValue;
/*     */   private final TransducedAccessor<BeanT> xacc;
/*     */   
/*     */   public ListElementProperty(JAXBContextImpl grammar, RuntimeElementPropertyInfo prop) {
/*  69 */     super(grammar, prop);
/*     */     
/*  71 */     assert prop.isValueList();
/*  72 */     assert prop.getTypes().size() == 1;
/*  73 */     RuntimeTypeRef ref = prop.getTypes().get(0);
/*     */     
/*  75 */     this.tagName = grammar.nameBuilder.createElementName(ref.getTagName());
/*  76 */     this.defaultValue = ref.getDefaultValue();
/*     */ 
/*     */     
/*  79 */     Transducer<ItemT> xducer = ref.getTransducer();
/*     */     
/*  81 */     this.xacc = new ListTransducedAccessorImpl<>(xducer, this.acc, this.lister);
/*     */   }
/*     */   
/*     */   public PropertyKind getKind() {
/*  85 */     return PropertyKind.ELEMENT;
/*     */   }
/*     */   
/*     */   public void buildChildElementUnmarshallers(UnmarshallerChain chain, QNameMap<ChildLoader> handlers) {
/*  89 */     Loader l = new LeafPropertyLoader(this.xacc);
/*  90 */     l = new DefaultValueLoaderDecorator(l, this.defaultValue);
/*  91 */     handlers.put(this.tagName, new ChildLoader(l, null));
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeBody(BeanT o, XMLSerializer w, Object outerPeer) throws SAXException, AccessorException, IOException, XMLStreamException {
/*  96 */     ListT list = this.acc.get(o);
/*     */     
/*  98 */     if (list != null) {
/*  99 */       if (this.xacc.useNamespace()) {
/* 100 */         w.startElement(this.tagName, null);
/* 101 */         this.xacc.declareNamespace(o, w);
/* 102 */         w.endNamespaceDecls(list);
/* 103 */         w.endAttributes();
/* 104 */         this.xacc.writeText(w, o, this.fieldName);
/* 105 */         w.endElement();
/*     */       } else {
/* 107 */         this.xacc.writeLeafElement(w, this.tagName, o, this.fieldName);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Accessor getElementPropertyAccessor(String nsUri, String localName) {
/* 114 */     if (this.tagName != null && 
/* 115 */       this.tagName.equals(nsUri, localName)) {
/* 116 */       return this.acc;
/*     */     }
/* 118 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtime\property\ListElementProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */