/*     */ package com.sun.xml.internal.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.ClassFactory;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.internal.bind.v2.model.core.WildcardMode;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeElement;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeReferencePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JaxBeanInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.ChildLoader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Receiver;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.WildcardLoader;
/*     */ import com.sun.xml.internal.bind.v2.util.QNameMap;
/*     */ import java.io.IOException;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.annotation.DomHandler;
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
/*     */ class ArrayReferenceNodeProperty<BeanT, ListT, ItemT>
/*     */   extends ArrayERProperty<BeanT, ListT, ItemT>
/*     */ {
/*  61 */   private final QNameMap<JaxBeanInfo> expectedElements = new QNameMap<>();
/*     */   
/*     */   private final boolean isMixed;
/*     */   
/*     */   private final DomHandler domHandler;
/*     */   private final WildcardMode wcMode;
/*     */   
/*     */   public ArrayReferenceNodeProperty(JAXBContextImpl p, RuntimeReferencePropertyInfo prop) {
/*  69 */     super(p, prop, prop.getXmlName(), prop.isCollectionNillable());
/*     */     
/*  71 */     for (RuntimeElement e : prop.getElements()) {
/*  72 */       JaxBeanInfo bi = p.getOrCreate(e);
/*  73 */       this.expectedElements.put(e.getElementName().getNamespaceURI(), e.getElementName().getLocalPart(), bi);
/*     */     } 
/*     */     
/*  76 */     this.isMixed = prop.isMixed();
/*     */     
/*  78 */     if (prop.getWildcard() != null) {
/*  79 */       this.domHandler = ClassFactory.<DomHandler>create(prop.getDOMHandler());
/*  80 */       this.wcMode = prop.getWildcard();
/*     */     } else {
/*  82 */       this.domHandler = null;
/*  83 */       this.wcMode = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void serializeListBody(BeanT o, XMLSerializer w, ListT list) throws IOException, XMLStreamException, SAXException {
/*  88 */     ListIterator<ItemT> itr = this.lister.iterator(list, w);
/*     */     
/*  90 */     while (itr.hasNext()) {
/*     */       try {
/*  92 */         ItemT item = itr.next();
/*  93 */         if (item != null) {
/*  94 */           if (this.isMixed && item.getClass() == String.class) {
/*  95 */             w.text((String)item, (String)null); continue;
/*     */           } 
/*  97 */           JaxBeanInfo<ItemT> bi = w.grammar.getBeanInfo(item, true);
/*  98 */           if (bi.jaxbType == Object.class && this.domHandler != null) {
/*     */ 
/*     */             
/* 101 */             w.writeDom(item, this.domHandler, o, this.fieldName); continue;
/*     */           } 
/* 103 */           bi.serializeRoot(item, w);
/*     */         }
/*     */       
/* 106 */       } catch (JAXBException e) {
/* 107 */         w.reportError(this.fieldName, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void createBodyUnmarshaller(UnmarshallerChain chain, QNameMap<ChildLoader> loaders) {
/* 114 */     int offset = chain.allocateOffset();
/*     */     
/* 116 */     Receiver recv = new ArrayERProperty.ReceiverImpl(this, offset);
/*     */     
/* 118 */     for (QNameMap.Entry<JaxBeanInfo> n : this.expectedElements.entrySet()) {
/* 119 */       JaxBeanInfo beanInfo = n.getValue();
/* 120 */       loaders.put(n.nsUri, n.localName, new ChildLoader(beanInfo.getLoader(chain.context, true), recv));
/*     */     } 
/*     */     
/* 123 */     if (this.isMixed)
/*     */     {
/* 125 */       loaders.put(TEXT_HANDLER, new ChildLoader(new MixedTextLoader(recv), null));
/*     */     }
/*     */ 
/*     */     
/* 129 */     if (this.domHandler != null) {
/* 130 */       loaders.put(CATCH_ALL, new ChildLoader(new WildcardLoader(this.domHandler, this.wcMode), recv));
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class MixedTextLoader
/*     */     extends Loader
/*     */   {
/*     */     private final Receiver recv;
/*     */     
/*     */     public MixedTextLoader(Receiver recv) {
/* 140 */       super(true);
/* 141 */       this.recv = recv;
/*     */     }
/*     */     
/*     */     public void text(UnmarshallingContext.State state, CharSequence text) throws SAXException {
/* 145 */       if (text.length() != 0) {
/* 146 */         this.recv.receive(state, text.toString());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public PropertyKind getKind() {
/* 152 */     return PropertyKind.REFERENCE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Accessor getElementPropertyAccessor(String nsUri, String localName) {
/* 158 */     if (this.wrapperTagName != null) {
/* 159 */       if (this.wrapperTagName.equals(nsUri, localName)) {
/* 160 */         return this.acc;
/*     */       }
/* 162 */     } else if (this.expectedElements.containsKey(nsUri, localName)) {
/* 163 */       return this.acc;
/*     */     } 
/* 165 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtime\property\ArrayReferenceNodeProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */