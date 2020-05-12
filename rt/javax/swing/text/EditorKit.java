/*    */ package javax.swing.text;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.Reader;
/*    */ import java.io.Serializable;
/*    */ import java.io.Writer;
/*    */ import javax.swing.Action;
/*    */ import javax.swing.JEditorPane;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EditorKit
/*    */   implements Cloneable, Serializable
/*    */ {
/*    */   public Object clone() {
/*    */     Object object;
/*    */     try {
/* 66 */       object = super.clone();
/* 67 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 68 */       object = null;
/*    */     } 
/* 70 */     return object;
/*    */   }
/*    */   
/*    */   public void install(JEditorPane paramJEditorPane) {}
/*    */   
/*    */   public void deinstall(JEditorPane paramJEditorPane) {}
/*    */   
/*    */   public abstract String getContentType();
/*    */   
/*    */   public abstract ViewFactory getViewFactory();
/*    */   
/*    */   public abstract Action[] getActions();
/*    */   
/*    */   public abstract Caret createCaret();
/*    */   
/*    */   public abstract Document createDefaultDocument();
/*    */   
/*    */   public abstract void read(InputStream paramInputStream, Document paramDocument, int paramInt) throws IOException, BadLocationException;
/*    */   
/*    */   public abstract void write(OutputStream paramOutputStream, Document paramDocument, int paramInt1, int paramInt2) throws IOException, BadLocationException;
/*    */   
/*    */   public abstract void read(Reader paramReader, Document paramDocument, int paramInt) throws IOException, BadLocationException;
/*    */   
/*    */   public abstract void write(Writer paramWriter, Document paramDocument, int paramInt1, int paramInt2) throws IOException, BadLocationException;
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\text\EditorKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */