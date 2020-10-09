/*     */ package com.sun.xml.internal.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.bind.api.TypeReference;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallerImpl;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.MarshalException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.UnmarshalException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
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
/*     */ final class BridgeAdapter<OnWire, InMemory>
/*     */   extends InternalBridge<InMemory>
/*     */ {
/*     */   private final InternalBridge<OnWire> core;
/*     */   private final Class<? extends XmlAdapter<OnWire, InMemory>> adapter;
/*     */   
/*     */   public BridgeAdapter(InternalBridge<OnWire> core, Class<? extends XmlAdapter<OnWire, InMemory>> adapter) {
/*  64 */     super(core.getContext());
/*  65 */     this.core = core;
/*  66 */     this.adapter = adapter;
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, InMemory inMemory, XMLStreamWriter output) throws JAXBException {
/*  70 */     this.core.marshal(m, adaptM(m, inMemory), output);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, InMemory inMemory, OutputStream output, NamespaceContext nsc) throws JAXBException {
/*  74 */     this.core.marshal(m, adaptM(m, inMemory), output, nsc);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, InMemory inMemory, Node output) throws JAXBException {
/*  78 */     this.core.marshal(m, adaptM(m, inMemory), output);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller context, InMemory inMemory, ContentHandler contentHandler) throws JAXBException {
/*  82 */     this.core.marshal(context, adaptM(context, inMemory), contentHandler);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller context, InMemory inMemory, Result result) throws JAXBException {
/*  86 */     this.core.marshal(context, adaptM(context, inMemory), result);
/*     */   }
/*     */   
/*     */   private OnWire adaptM(Marshaller m, InMemory v) throws JAXBException {
/*  90 */     XMLSerializer serializer = ((MarshallerImpl)m).serializer;
/*  91 */     serializer.pushCoordinator();
/*     */     try {
/*  93 */       return _adaptM(serializer, v);
/*     */     } finally {
/*  95 */       serializer.popCoordinator();
/*     */     } 
/*     */   }
/*     */   
/*     */   private OnWire _adaptM(XMLSerializer serializer, InMemory v) throws MarshalException {
/* 100 */     XmlAdapter<OnWire, InMemory> a = serializer.getAdapter((Class)this.adapter);
/*     */     try {
/* 102 */       return a.marshal(v);
/* 103 */     } catch (Exception e) {
/* 104 */       serializer.handleError(e, v, (String)null);
/* 105 */       throw new MarshalException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public InMemory unmarshal(Unmarshaller u, XMLStreamReader in) throws JAXBException {
/* 111 */     return adaptU(u, this.core.unmarshal(u, in));
/*     */   }
/*     */   @NotNull
/*     */   public InMemory unmarshal(Unmarshaller u, Source in) throws JAXBException {
/* 115 */     return adaptU(u, this.core.unmarshal(u, in));
/*     */   }
/*     */   @NotNull
/*     */   public InMemory unmarshal(Unmarshaller u, InputStream in) throws JAXBException {
/* 119 */     return adaptU(u, this.core.unmarshal(u, in));
/*     */   }
/*     */   @NotNull
/*     */   public InMemory unmarshal(Unmarshaller u, Node n) throws JAXBException {
/* 123 */     return adaptU(u, this.core.unmarshal(u, n));
/*     */   }
/*     */   
/*     */   public TypeReference getTypeReference() {
/* 127 */     return this.core.getTypeReference();
/*     */   }
/*     */   @NotNull
/*     */   private InMemory adaptU(Unmarshaller _u, OnWire v) throws JAXBException {
/* 131 */     UnmarshallerImpl u = (UnmarshallerImpl)_u;
/* 132 */     XmlAdapter<OnWire, InMemory> a = u.coordinator.getAdapter((Class)this.adapter);
/* 133 */     u.coordinator.pushCoordinator();
/*     */     try {
/* 135 */       return a.unmarshal(v);
/* 136 */     } catch (Exception e) {
/* 137 */       throw new UnmarshalException(e);
/*     */     } finally {
/* 139 */       u.coordinator.popCoordinator();
/*     */     } 
/*     */   }
/*     */   
/*     */   void marshal(InMemory o, XMLSerializer out) throws IOException, SAXException, XMLStreamException {
/*     */     try {
/* 145 */       this.core.marshal(_adaptM(XMLSerializer.getInstance(), o), out);
/* 146 */     } catch (MarshalException marshalException) {}
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtime\BridgeAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */