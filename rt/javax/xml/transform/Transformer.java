/*    */ package javax.xml.transform;
/*    */ 
/*    */ import java.util.Properties;
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
/*    */ public abstract class Transformer
/*    */ {
/*    */   public void reset() {
/* 79 */     throw new UnsupportedOperationException("This Transformer, \"" + 
/* 80 */         getClass().getName() + "\", does not support the reset functionality.  Specification \"" + 
/* 81 */         getClass().getPackage().getSpecificationTitle() + "\" version \"" + 
/* 82 */         getClass().getPackage().getSpecificationVersion() + "\"");
/*    */   }
/*    */   
/*    */   public abstract void transform(Source paramSource, Result paramResult) throws TransformerException;
/*    */   
/*    */   public abstract void setParameter(String paramString, Object paramObject);
/*    */   
/*    */   public abstract Object getParameter(String paramString);
/*    */   
/*    */   public abstract void clearParameters();
/*    */   
/*    */   public abstract void setURIResolver(URIResolver paramURIResolver);
/*    */   
/*    */   public abstract URIResolver getURIResolver();
/*    */   
/*    */   public abstract void setOutputProperties(Properties paramProperties);
/*    */   
/*    */   public abstract Properties getOutputProperties();
/*    */   
/*    */   public abstract void setOutputProperty(String paramString1, String paramString2) throws IllegalArgumentException;
/*    */   
/*    */   public abstract String getOutputProperty(String paramString) throws IllegalArgumentException;
/*    */   
/*    */   public abstract void setErrorListener(ErrorListener paramErrorListener) throws IllegalArgumentException;
/*    */   
/*    */   public abstract ErrorListener getErrorListener();
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\transform\Transformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */