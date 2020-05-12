/*    */ package com.sun.xml.internal.fastinfoset.stax.util;
/*    */ 
/*    */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*    */ import javax.xml.stream.StreamFilter;
/*    */ import javax.xml.stream.XMLStreamException;
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
/*    */ public class StAXFilteredParser
/*    */   extends StAXParserWrapper
/*    */ {
/*    */   private StreamFilter _filter;
/*    */   
/*    */   public StAXFilteredParser() {}
/*    */   
/*    */   public StAXFilteredParser(XMLStreamReader reader, StreamFilter filter) {
/* 43 */     super(reader);
/* 44 */     this._filter = filter;
/*    */   }
/*    */   
/*    */   public void setFilter(StreamFilter filter) {
/* 48 */     this._filter = filter;
/*    */   }
/*    */ 
/*    */   
/*    */   public int next() throws XMLStreamException {
/* 53 */     if (hasNext())
/* 54 */       return super.next(); 
/* 55 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.noMoreItems"));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() throws XMLStreamException {
/* 60 */     while (super.hasNext()) {
/* 61 */       if (this._filter.accept(getReader())) return true; 
/* 62 */       super.next();
/*    */     } 
/* 64 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\fastinfoset\sta\\util\StAXFilteredParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */