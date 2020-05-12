/*     */ package com.sun.xml.internal.messaging.saaj.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl;
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import java.util.concurrent.ArrayBlockingQueue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
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
/*     */ public class ParserPool
/*     */ {
/*     */   private final BlockingQueue queue;
/*     */   private SAXParserFactory factory;
/*     */   private int capacity;
/*     */   
/*     */   public ParserPool(int capacity) {
/*  48 */     this.capacity = capacity;
/*  49 */     this.queue = new ArrayBlockingQueue(capacity);
/*     */     
/*  51 */     this.factory = new SAXParserFactoryImpl();
/*  52 */     this.factory.setNamespaceAware(true);
/*  53 */     for (int i = 0; i < capacity; i++) {
/*     */       try {
/*  55 */         this.queue.put(this.factory.newSAXParser());
/*  56 */       } catch (InterruptedException ex) {
/*  57 */         Thread.currentThread().interrupt();
/*  58 */         throw new RuntimeException(ex);
/*  59 */       } catch (ParserConfigurationException ex) {
/*  60 */         throw new RuntimeException(ex);
/*  61 */       } catch (SAXException ex) {
/*  62 */         throw new RuntimeException(ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXParser get() throws ParserConfigurationException, SAXException {
/*     */     try {
/*  71 */       return this.queue.take();
/*  72 */     } catch (InterruptedException ex) {
/*  73 */       throw new SAXException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void put(SAXParser parser) {
/*  79 */     this.queue.offer(parser);
/*     */   }
/*     */   
/*     */   public void returnParser(SAXParser saxParser) {
/*  83 */     saxParser.reset();
/*  84 */     resetSaxParser(saxParser);
/*  85 */     put(saxParser);
/*     */   }
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
/*     */   private void resetSaxParser(SAXParser parser) {
/*     */     try {
/* 100 */       SymbolTable table = new SymbolTable();
/* 101 */       parser.setProperty("http://apache.org/xml/properties/internal/symbol-table", table);
/*     */     }
/* 103 */     catch (SAXNotRecognizedException sAXNotRecognizedException) {
/*     */     
/* 105 */     } catch (SAXNotSupportedException sAXNotSupportedException) {}
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saa\\util\ParserPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */