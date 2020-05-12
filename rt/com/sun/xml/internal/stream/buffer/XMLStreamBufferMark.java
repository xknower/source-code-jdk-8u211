/*    */ package com.sun.xml.internal.stream.buffer;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLStreamBufferMark
/*    */   extends XMLStreamBuffer
/*    */ {
/*    */   public XMLStreamBufferMark(Map<String, String> inscopeNamespaces, AbstractCreatorProcessor src) {
/* 62 */     if (inscopeNamespaces != null) {
/* 63 */       this._inscopeNamespaces = inscopeNamespaces;
/*    */     }
/*    */     
/* 66 */     this._structure = src._currentStructureFragment;
/* 67 */     this._structurePtr = src._structurePtr;
/*    */     
/* 69 */     this._structureStrings = src._currentStructureStringFragment;
/* 70 */     this._structureStringsPtr = src._structureStringsPtr;
/*    */     
/* 72 */     this._contentCharactersBuffer = src._currentContentCharactersBufferFragment;
/* 73 */     this._contentCharactersBufferPtr = src._contentCharactersBufferPtr;
/*    */     
/* 75 */     this._contentObjects = src._currentContentObjectFragment;
/* 76 */     this._contentObjectsPtr = src._contentObjectsPtr;
/* 77 */     this.treeCount = 1;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\stream\buffer\XMLStreamBufferMark.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */