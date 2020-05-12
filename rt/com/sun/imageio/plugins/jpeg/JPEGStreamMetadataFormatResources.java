/*    */ package com.sun.imageio.plugins.jpeg;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JPEGStreamMetadataFormatResources
/*    */   extends JPEGMetadataFormatResources
/*    */ {
/*    */   protected Object[][] getContents() {
/* 39 */     Object[][] arrayOfObject = new Object[commonContents.length][2];
/* 40 */     for (byte b = 0; b < commonContents.length; b++) {
/* 41 */       arrayOfObject[b][0] = commonContents[b][0];
/* 42 */       arrayOfObject[b][1] = commonContents[b][1];
/*    */     } 
/* 44 */     return arrayOfObject;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\imageio\plugins\jpeg\JPEGStreamMetadataFormatResources.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */