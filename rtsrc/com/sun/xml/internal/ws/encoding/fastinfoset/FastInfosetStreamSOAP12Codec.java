/*    */ package com.sun.xml.internal.ws.encoding.fastinfoset;
/*    */ 
/*    */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*    */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*    */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*    */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*    */ import com.sun.xml.internal.ws.api.pipe.StreamSOAPCodec;
/*    */ import com.sun.xml.internal.ws.encoding.ContentTypeImpl;
/*    */ import com.sun.xml.internal.ws.message.stream.StreamHeader;
/*    */ import com.sun.xml.internal.ws.message.stream.StreamHeader12;
/*    */ import javax.xml.stream.XMLStreamReader;
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
/*    */ final class FastInfosetStreamSOAP12Codec
/*    */   extends FastInfosetStreamSOAPCodec
/*    */ {
/*    */   FastInfosetStreamSOAP12Codec(StreamSOAPCodec soapCodec, boolean retainState) {
/* 47 */     super(soapCodec, SOAPVersion.SOAP_12, retainState, retainState ? "application/vnd.sun.stateful.soap+fastinfoset" : "application/soap+fastinfoset");
/*    */   }
/*    */ 
/*    */   
/*    */   private FastInfosetStreamSOAP12Codec(FastInfosetStreamSOAPCodec that) {
/* 52 */     super(that);
/*    */   }
/*    */   
/*    */   public Codec copy() {
/* 56 */     return new FastInfosetStreamSOAP12Codec(this);
/*    */   }
/*    */   
/*    */   protected final StreamHeader createHeader(XMLStreamReader reader, XMLStreamBuffer mark) {
/* 60 */     return new StreamHeader12(reader, mark);
/*    */   }
/*    */   
/*    */   protected ContentType getContentType(String soapAction) {
/* 64 */     if (soapAction == null) {
/* 65 */       return this._defaultContentType;
/*    */     }
/* 67 */     return new ContentTypeImpl(this._defaultContentType
/* 68 */         .getContentType() + ";action=\"" + soapAction + "\"");
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\encoding\fastinfoset\FastInfosetStreamSOAP12Codec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */