/*    */ package sun.font;
/*    */ 
/*    */ import java.awt.font.TextAttribute;
/*    */ import java.util.AbstractMap;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ public final class AttributeMap
/*    */   extends AbstractMap<TextAttribute, Object>
/*    */ {
/*    */   private AttributeValues values;
/*    */   private Map<TextAttribute, Object> delegateMap;
/*    */   
/*    */   public AttributeMap(AttributeValues paramAttributeValues) {
/* 62 */     this.values = paramAttributeValues;
/*    */   }
/*    */   
/*    */   public Set<Map.Entry<TextAttribute, Object>> entrySet() {
/* 66 */     return delegate().entrySet();
/*    */   }
/*    */   
/*    */   public Object put(TextAttribute paramTextAttribute, Object paramObject) {
/* 70 */     return delegate().put(paramTextAttribute, paramObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public AttributeValues getValues() {
/* 75 */     return this.values;
/*    */   }
/*    */ 
/*    */   
/*    */   private Map<TextAttribute, Object> delegate() {
/* 80 */     if (this.delegateMap == null) {
/* 81 */       if (first) {
/* 82 */         first = false;
/* 83 */         Thread.dumpStack();
/*    */       } 
/* 85 */       this.delegateMap = this.values.toMap(new HashMap<>(27));
/*    */ 
/*    */ 
/*    */       
/* 89 */       this.values = null;
/*    */     } 
/*    */     
/* 92 */     return this.delegateMap;
/*    */   }
/*    */   private static boolean first = false;
/*    */   public String toString() {
/* 96 */     if (this.values != null) {
/* 97 */       return "map of " + this.values.toString();
/*    */     }
/* 99 */     return super.toString();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\font\AttributeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */