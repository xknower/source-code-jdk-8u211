/*    */ package com.sun.org.apache.xerces.internal.xinclude;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.util.XML11Char;
/*    */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*    */ import java.io.IOException;
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
/*    */ public class XInclude11TextReader
/*    */   extends XIncludeTextReader
/*    */ {
/*    */   public XInclude11TextReader(XMLInputSource source, XIncludeHandler handler, int bufferSize) throws IOException {
/* 50 */     super(source, handler, bufferSize);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isValid(int ch) {
/* 60 */     return XML11Char.isXML11Valid(ch);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\xinclude\XInclude11TextReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */