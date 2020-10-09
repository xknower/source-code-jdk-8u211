/*     */ package com.sun.xml.internal.messaging.saaj.soap;
/*     */ 
/*     */ import com.sun.xml.internal.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.internal.messaging.saaj.util.JAXMStreamSource;
/*     */ import com.sun.xml.internal.messaging.saaj.util.ParserPool;
/*     */ import com.sun.xml.internal.messaging.saaj.util.RejectDoctypeSaxFilter;
/*     */ import com.sun.xml.internal.messaging.saaj.util.transform.EfficientStreamingTransformer;
/*     */ import java.io.IOException;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.XMLReader;
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
/*     */ public class EnvelopeFactory
/*     */ {
/*  53 */   protected static final Logger log = Logger.getLogger("com.sun.xml.internal.messaging.saaj.soap", "com.sun.xml.internal.messaging.saaj.soap.LocalStrings");
/*     */ 
/*     */   
/*  56 */   private static ContextClassloaderLocal<ParserPool> parserPool = new ContextClassloaderLocal<ParserPool>()
/*     */     {
/*     */       protected ParserPool initialValue() throws Exception
/*     */       {
/*  60 */         return new ParserPool(5);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Envelope createEnvelope(Source src, SOAPPartImpl soapPart) throws SOAPException {
/*  68 */     SAXParser saxParser = null;
/*  69 */     if (src instanceof javax.xml.transform.stream.StreamSource) {
/*  70 */       XMLReader rejectFilter; if (src instanceof JAXMStreamSource) {
/*     */         try {
/*  72 */           if (!SOAPPartImpl.lazyContentLength) {
/*  73 */             ((JAXMStreamSource)src).reset();
/*     */           }
/*  75 */         } catch (IOException ioe) {
/*  76 */           log.severe("SAAJ0515.source.reset.exception");
/*  77 */           throw new SOAPExceptionImpl(ioe);
/*     */         } 
/*     */       }
/*     */       try {
/*  81 */         saxParser = ((ParserPool)parserPool.get()).get();
/*  82 */       } catch (Exception e) {
/*  83 */         log.severe("SAAJ0601.util.newSAXParser.exception");
/*  84 */         throw new SOAPExceptionImpl("Couldn't get a SAX parser while constructing a envelope", e);
/*     */       } 
/*     */ 
/*     */       
/*  88 */       InputSource is = SAXSource.sourceToInputSource(src);
/*  89 */       if (is.getEncoding() == null && soapPart.getSourceCharsetEncoding() != null) {
/*  90 */         is.setEncoding(soapPart.getSourceCharsetEncoding());
/*     */       }
/*     */       
/*     */       try {
/*  94 */         rejectFilter = new RejectDoctypeSaxFilter(saxParser);
/*  95 */       } catch (Exception ex) {
/*  96 */         log.severe("SAAJ0510.soap.cannot.create.envelope");
/*  97 */         throw new SOAPExceptionImpl("Unable to create envelope from given source: ", ex);
/*     */       } 
/*     */ 
/*     */       
/* 101 */       src = new SAXSource(rejectFilter, is);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 106 */       Transformer transformer = EfficientStreamingTransformer.newTransformer();
/* 107 */       DOMResult result = new DOMResult(soapPart);
/* 108 */       transformer.transform(src, result);
/*     */       
/* 110 */       Envelope env = (Envelope)soapPart.getEnvelope();
/* 111 */       return env;
/* 112 */     } catch (Exception ex) {
/* 113 */       if (ex instanceof SOAPVersionMismatchException) {
/* 114 */         throw (SOAPVersionMismatchException)ex;
/*     */       }
/* 116 */       log.severe("SAAJ0511.soap.cannot.create.envelope");
/* 117 */       throw new SOAPExceptionImpl("Unable to create envelope from given source: ", ex);
/*     */     }
/*     */     finally {
/*     */       
/* 121 */       if (saxParser != null)
/* 122 */         ((ParserPool)parserPool.get()).returnParser(saxParser); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saaj\soap\EnvelopeFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */