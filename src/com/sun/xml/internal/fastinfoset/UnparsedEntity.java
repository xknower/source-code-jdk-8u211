/*    */ package com.sun.xml.internal.fastinfoset;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnparsedEntity
/*    */   extends Notation
/*    */ {
/*    */   public final String notationName;
/*    */   
/*    */   public UnparsedEntity(String _name, String _systemIdentifier, String _publicIdentifier, String _notationName) {
/* 35 */     super(_name, _systemIdentifier, _publicIdentifier);
/* 36 */     this.notationName = _notationName;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\fastinfoset\UnparsedEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */