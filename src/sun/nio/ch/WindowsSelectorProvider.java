/*    */ package sun.nio.ch;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.channels.spi.AbstractSelector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowsSelectorProvider
/*    */   extends SelectorProviderImpl
/*    */ {
/*    */   public AbstractSelector openSelector() throws IOException {
/* 44 */     return new WindowsSelectorImpl(this);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\WindowsSelectorProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */