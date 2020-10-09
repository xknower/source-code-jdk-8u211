/*    */ package com.sun.xml.internal.ws.streaming;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.istack.internal.Nullable;
/*    */ import com.sun.xml.internal.ws.util.xml.XMLStreamReaderFilter;
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ public class TidyXMLStreamReader
/*    */   extends XMLStreamReaderFilter
/*    */ {
/*    */   private final Closeable closeableSource;
/*    */   
/*    */   public TidyXMLStreamReader(@NotNull XMLStreamReader reader, @Nullable Closeable closeableSource) {
/* 48 */     super(reader);
/* 49 */     this.closeableSource = closeableSource;
/*    */   }
/*    */   
/*    */   public void close() throws XMLStreamException {
/* 53 */     super.close();
/*    */     try {
/* 55 */       if (this.closeableSource != null)
/* 56 */         this.closeableSource.close(); 
/* 57 */     } catch (IOException e) {
/* 58 */       throw new WebServiceException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\streaming\TidyXMLStreamReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */