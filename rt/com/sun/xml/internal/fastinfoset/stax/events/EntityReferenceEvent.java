/*    */ package com.sun.xml.internal.fastinfoset.stax.events;
/*    */ 
/*    */ import javax.xml.stream.events.EntityDeclaration;
/*    */ import javax.xml.stream.events.EntityReference;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityReferenceEvent
/*    */   extends EventBase
/*    */   implements EntityReference
/*    */ {
/*    */   private EntityDeclaration _entityDeclaration;
/*    */   private String _entityName;
/*    */   
/*    */   public EntityReferenceEvent() {
/* 39 */     init();
/*    */   }
/*    */   
/*    */   public EntityReferenceEvent(String entityName, EntityDeclaration entityDeclaration) {
/* 43 */     init();
/* 44 */     this._entityName = entityName;
/* 45 */     this._entityDeclaration = entityDeclaration;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 53 */     return this._entityName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityDeclaration getDeclaration() {
/* 60 */     return this._entityDeclaration;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 64 */     this._entityName = name;
/*    */   }
/*    */   
/*    */   public void setDeclaration(EntityDeclaration declaration) {
/* 68 */     this._entityDeclaration = declaration;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 72 */     String text = this._entityDeclaration.getReplacementText();
/* 73 */     if (text == null)
/* 74 */       text = ""; 
/* 75 */     return "&" + getName() + ";='" + text + "'";
/*    */   }
/*    */   
/*    */   protected void init() {
/* 79 */     setEventType(9);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\fastinfoset\stax\events\EntityReferenceEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */