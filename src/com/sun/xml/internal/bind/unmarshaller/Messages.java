/*    */ package com.sun.xml.internal.bind.unmarshaller;
/*    */ 
/*    */ import java.text.MessageFormat;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Messages
/*    */ {
/*    */   public static final String UNEXPECTED_ENTER_ELEMENT = "ContentHandlerEx.UnexpectedEnterElement";
/*    */   public static final String UNEXPECTED_LEAVE_ELEMENT = "ContentHandlerEx.UnexpectedLeaveElement";
/*    */   public static final String UNEXPECTED_ENTER_ATTRIBUTE = "ContentHandlerEx.UnexpectedEnterAttribute";
/*    */   public static final String UNEXPECTED_LEAVE_ATTRIBUTE = "ContentHandlerEx.UnexpectedLeaveAttribute";
/*    */   public static final String UNEXPECTED_TEXT = "ContentHandlerEx.UnexpectedText";
/*    */   public static final String UNEXPECTED_LEAVE_CHILD = "ContentHandlerEx.UnexpectedLeaveChild";
/*    */   public static final String UNEXPECTED_ROOT_ELEMENT = "SAXUnmarshallerHandlerImpl.UnexpectedRootElement";
/*    */   public static final String UNDEFINED_PREFIX = "Util.UndefinedPrefix";
/*    */   public static final String NULL_READER = "Unmarshaller.NullReader";
/*    */   public static final String ILLEGAL_READER_STATE = "Unmarshaller.IllegalReaderState";
/*    */   
/*    */   public static String format(String property) {
/* 39 */     return format(property, (Object[])null);
/*    */   }
/*    */   
/*    */   public static String format(String property, Object arg1) {
/* 43 */     return format(property, new Object[] { arg1 });
/*    */   }
/*    */   
/*    */   public static String format(String property, Object arg1, Object arg2) {
/* 47 */     return format(property, new Object[] { arg1, arg2 });
/*    */   }
/*    */   
/*    */   public static String format(String property, Object arg1, Object arg2, Object arg3) {
/* 51 */     return format(property, new Object[] { arg1, arg2, arg3 });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String format(String property, Object[] args) {
/* 58 */     String text = ResourceBundle.getBundle(Messages.class.getName()).getString(property);
/* 59 */     return MessageFormat.format(text, args);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bin\\unmarshaller\Messages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */