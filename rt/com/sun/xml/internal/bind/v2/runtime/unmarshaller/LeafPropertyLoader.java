/*    */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import com.sun.xml.internal.bind.api.AccessorException;
/*    */ import com.sun.xml.internal.bind.v2.runtime.reflect.TransducedAccessor;
/*    */ import org.xml.sax.SAXException;
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
/*    */ public class LeafPropertyLoader
/*    */   extends Loader
/*    */ {
/*    */   private final TransducedAccessor xacc;
/*    */   
/*    */   public LeafPropertyLoader(TransducedAccessor xacc) {
/* 44 */     super(true);
/* 45 */     this.xacc = xacc;
/*    */   }
/*    */   
/*    */   public void text(UnmarshallingContext.State state, CharSequence text) throws SAXException {
/*    */     try {
/* 50 */       this.xacc.parse(state.getPrev().getTarget(), text);
/* 51 */     } catch (AccessorException e) {
/* 52 */       handleGenericException(e, true);
/* 53 */     } catch (RuntimeException e) {
/* 54 */       handleParseConversionException(state, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtim\\unmarshaller\LeafPropertyLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */