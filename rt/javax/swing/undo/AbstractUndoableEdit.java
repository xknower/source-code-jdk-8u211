/*     */ package javax.swing.undo;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AbstractUndoableEdit
/*     */   implements UndoableEdit, Serializable
/*     */ {
/*     */   protected static final String UndoName = "Undo";
/*     */   protected static final String RedoName = "Redo";
/*     */   boolean hasBeenDone = true;
/*     */   boolean alive = true;
/*     */   
/*     */   public void die() {
/*  94 */     this.alive = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void undo() throws CannotUndoException {
/* 109 */     if (!canUndo()) {
/* 110 */       throw new CannotUndoException();
/*     */     }
/* 112 */     this.hasBeenDone = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUndo() {
/* 127 */     return (this.alive && this.hasBeenDone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void redo() throws CannotRedoException {
/* 141 */     if (!canRedo()) {
/* 142 */       throw new CannotRedoException();
/*     */     }
/* 144 */     this.hasBeenDone = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRedo() {
/* 158 */     return (this.alive && !this.hasBeenDone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addEdit(UndoableEdit paramUndoableEdit) {
/* 170 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replaceEdit(UndoableEdit paramUndoableEdit) {
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSignificant() {
/* 192 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPresentationName() {
/* 209 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUndoPresentationName() {
/* 228 */     String str = getPresentationName();
/* 229 */     if (!"".equals(str)) {
/* 230 */       str = UIManager.getString("AbstractUndoableEdit.undoText") + " " + str;
/*     */     } else {
/*     */       
/* 233 */       str = UIManager.getString("AbstractUndoableEdit.undoText");
/*     */     } 
/*     */     
/* 236 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRedoPresentationName() {
/* 255 */     String str = getPresentationName();
/* 256 */     if (!"".equals(str)) {
/* 257 */       str = UIManager.getString("AbstractUndoableEdit.redoText") + " " + str;
/*     */     } else {
/*     */       
/* 260 */       str = UIManager.getString("AbstractUndoableEdit.redoText");
/*     */     } 
/*     */     
/* 263 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 274 */     return super.toString() + " hasBeenDone: " + this.hasBeenDone + " alive: " + this.alive;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swin\\undo\AbstractUndoableEdit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */