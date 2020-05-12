/*    */ package com.sun.xml.internal.org.jvnet.fastinfoset;
/*    */ 
/*    */ import com.sun.xml.internal.fastinfoset.sax.SAXDocumentParser;
/*    */ import java.io.InputStream;
/*    */ import javax.xml.transform.sax.SAXSource;
/*    */ import org.xml.sax.InputSource;
/*    */ import org.xml.sax.XMLReader;
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
/*    */ public class FastInfosetSource
/*    */   extends SAXSource
/*    */ {
/*    */   public FastInfosetSource(InputStream inputStream) {
/* 65 */     super(new InputSource(inputStream));
/*    */   }
/*    */   
/*    */   public XMLReader getXMLReader() {
/* 69 */     XMLReader reader = super.getXMLReader();
/* 70 */     if (reader == null) {
/* 71 */       reader = new SAXDocumentParser();
/* 72 */       setXMLReader(reader);
/*    */     } 
/* 74 */     ((SAXDocumentParser)reader).setInputStream(getInputStream());
/* 75 */     return reader;
/*    */   }
/*    */   
/*    */   public InputStream getInputStream() {
/* 79 */     return getInputSource().getByteStream();
/*    */   }
/*    */   
/*    */   public void setInputStream(InputStream inputStream) {
/* 83 */     setInputSource(new InputSource(inputStream));
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\org\jvnet\fastinfoset\FastInfosetSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */