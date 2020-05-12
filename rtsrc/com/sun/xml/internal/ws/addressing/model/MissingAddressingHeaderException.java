/*    */ package com.sun.xml.internal.ws.addressing.model;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.istack.internal.Nullable;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.resources.AddressingMessages;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MissingAddressingHeaderException
/*    */   extends WebServiceException
/*    */ {
/*    */   private final QName name;
/*    */   private final transient Packet packet;
/*    */   
/*    */   public MissingAddressingHeaderException(@NotNull QName name) {
/* 50 */     this(name, null);
/*    */   }
/*    */   
/*    */   public MissingAddressingHeaderException(@NotNull QName name, @Nullable Packet p) {
/* 54 */     super(AddressingMessages.MISSING_HEADER_EXCEPTION(name));
/* 55 */     this.name = name;
/* 56 */     this.packet = p;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public QName getMissingHeaderQName() {
/* 66 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Packet getPacket() {
/* 76 */     return this.packet;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\ws\addressing\model\MissingAddressingHeaderException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */