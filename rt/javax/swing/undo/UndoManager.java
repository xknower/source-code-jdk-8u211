/*     */ package javax.swing.undo;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.UndoableEditEvent;
/*     */ import javax.swing.event.UndoableEditListener;
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
/*     */ public class UndoManager
/*     */   extends CompoundEdit
/*     */   implements UndoableEditListener
/*     */ {
/*     */   int indexOfNextAdd;
/*     */   int limit;
/*     */   
/*     */   public UndoManager() {
/* 144 */     this.indexOfNextAdd = 0;
/* 145 */     this.limit = 100;
/* 146 */     this.edits.ensureCapacity(this.limit);
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
/*     */   public synchronized int getLimit() {
/* 159 */     return this.limit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void discardAllEdits() {
/* 169 */     for (UndoableEdit undoableEdit : this.edits) {
/* 170 */       undoableEdit.die();
/*     */     }
/* 172 */     this.edits = new Vector<>();
/* 173 */     this.indexOfNextAdd = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void trimForLimit() {
/* 184 */     if (this.limit >= 0) {
/* 185 */       int i = this.edits.size();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 191 */       if (i > this.limit) {
/* 192 */         int j = this.limit / 2;
/* 193 */         int k = this.indexOfNextAdd - 1 - j;
/* 194 */         int m = this.indexOfNextAdd - 1 + j;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 201 */         if (m - k + 1 > this.limit) {
/* 202 */           k++;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 209 */         if (k < 0) {
/* 210 */           m -= k;
/* 211 */           k = 0;
/*     */         } 
/* 213 */         if (m >= i) {
/* 214 */           int n = i - m - 1;
/* 215 */           m += n;
/* 216 */           k += n;
/*     */         } 
/*     */ 
/*     */         
/* 220 */         trimEdits(m + 1, i - 1);
/* 221 */         trimEdits(0, k - 1);
/*     */       } 
/*     */     } 
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
/*     */   protected void trimEdits(int paramInt1, int paramInt2) {
/* 237 */     if (paramInt1 <= paramInt2) {
/*     */ 
/*     */       
/* 240 */       for (int i = paramInt2; paramInt1 <= i; i--) {
/* 241 */         UndoableEdit undoableEdit = this.edits.elementAt(i);
/*     */ 
/*     */         
/* 244 */         undoableEdit.die();
/*     */ 
/*     */         
/* 247 */         this.edits.removeElementAt(i);
/*     */       } 
/*     */       
/* 250 */       if (this.indexOfNextAdd > paramInt2) {
/*     */         
/* 252 */         this.indexOfNextAdd -= paramInt2 - paramInt1 + 1;
/* 253 */       } else if (this.indexOfNextAdd >= paramInt1) {
/*     */         
/* 255 */         this.indexOfNextAdd = paramInt1;
/*     */       } 
/*     */     } 
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
/*     */ 
/*     */   
/*     */   public synchronized void setLimit(int paramInt) {
/* 278 */     if (!this.inProgress) throw new RuntimeException("Attempt to call UndoManager.setLimit() after UndoManager.end() has been called"); 
/* 279 */     this.limit = paramInt;
/* 280 */     trimForLimit();
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
/*     */   protected UndoableEdit editToBeUndone() {
/* 292 */     int i = this.indexOfNextAdd;
/* 293 */     while (i > 0) {
/* 294 */       UndoableEdit undoableEdit = this.edits.elementAt(--i);
/* 295 */       if (undoableEdit.isSignificant()) {
/* 296 */         return undoableEdit;
/*     */       }
/*     */     } 
/*     */     
/* 300 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UndoableEdit editToBeRedone() {
/* 311 */     int i = this.edits.size();
/* 312 */     int j = this.indexOfNextAdd;
/*     */     
/* 314 */     while (j < i) {
/* 315 */       UndoableEdit undoableEdit = this.edits.elementAt(j++);
/* 316 */       if (undoableEdit.isSignificant()) {
/* 317 */         return undoableEdit;
/*     */       }
/*     */     } 
/*     */     
/* 321 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void undoTo(UndoableEdit paramUndoableEdit) throws CannotUndoException {
/* 332 */     boolean bool = false;
/* 333 */     while (!bool) {
/* 334 */       UndoableEdit undoableEdit = this.edits.elementAt(--this.indexOfNextAdd);
/* 335 */       undoableEdit.undo();
/* 336 */       bool = (undoableEdit == paramUndoableEdit) ? true : false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void redoTo(UndoableEdit paramUndoableEdit) throws CannotRedoException {
/* 348 */     boolean bool = false;
/* 349 */     while (!bool) {
/* 350 */       UndoableEdit undoableEdit = this.edits.elementAt(this.indexOfNextAdd++);
/* 351 */       undoableEdit.redo();
/* 352 */       bool = (undoableEdit == paramUndoableEdit) ? true : false;
/*     */     } 
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
/*     */   public synchronized void undoOrRedo() throws CannotRedoException, CannotUndoException {
/* 371 */     if (this.indexOfNextAdd == this.edits.size()) {
/* 372 */       undo();
/*     */     } else {
/* 374 */       redo();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean canUndoOrRedo() {
/* 386 */     if (this.indexOfNextAdd == this.edits.size()) {
/* 387 */       return canUndo();
/*     */     }
/* 389 */     return canRedo();
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
/*     */   public synchronized void undo() throws CannotUndoException {
/* 408 */     if (this.inProgress) {
/* 409 */       UndoableEdit undoableEdit = editToBeUndone();
/* 410 */       if (undoableEdit == null) {
/* 411 */         throw new CannotUndoException();
/*     */       }
/* 413 */       undoTo(undoableEdit);
/*     */     } else {
/* 415 */       super.undo();
/*     */     } 
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
/*     */   public synchronized boolean canUndo() {
/* 430 */     if (this.inProgress) {
/* 431 */       UndoableEdit undoableEdit = editToBeUndone();
/* 432 */       return (undoableEdit != null && undoableEdit.canUndo());
/*     */     } 
/* 434 */     return super.canUndo();
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
/*     */   public synchronized void redo() throws CannotRedoException {
/* 453 */     if (this.inProgress) {
/* 454 */       UndoableEdit undoableEdit = editToBeRedone();
/* 455 */       if (undoableEdit == null) {
/* 456 */         throw new CannotRedoException();
/*     */       }
/* 458 */       redoTo(undoableEdit);
/*     */     } else {
/* 460 */       super.redo();
/*     */     } 
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
/*     */   public synchronized boolean canRedo() {
/* 475 */     if (this.inProgress) {
/* 476 */       UndoableEdit undoableEdit = editToBeRedone();
/* 477 */       return (undoableEdit != null && undoableEdit.canRedo());
/*     */     } 
/* 479 */     return super.canRedo();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean addEdit(UndoableEdit paramUndoableEdit) {
/* 502 */     trimEdits(this.indexOfNextAdd, this.edits.size() - 1);
/*     */     
/* 504 */     boolean bool = super.addEdit(paramUndoableEdit);
/* 505 */     if (this.inProgress) {
/* 506 */       bool = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 513 */     this.indexOfNextAdd = this.edits.size();
/*     */ 
/*     */     
/* 516 */     trimForLimit();
/*     */     
/* 518 */     return bool;
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
/*     */   public synchronized void end() {
/* 530 */     super.end();
/* 531 */     trimEdits(this.indexOfNextAdd, this.edits.size() - 1);
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
/*     */   public synchronized String getUndoOrRedoPresentationName() {
/* 545 */     if (this.indexOfNextAdd == this.edits.size()) {
/* 546 */       return getUndoPresentationName();
/*     */     }
/* 548 */     return getRedoPresentationName();
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
/*     */   public synchronized String getUndoPresentationName() {
/* 566 */     if (this.inProgress) {
/* 567 */       if (canUndo()) {
/* 568 */         return editToBeUndone().getUndoPresentationName();
/*     */       }
/* 570 */       return UIManager.getString("AbstractUndoableEdit.undoText");
/*     */     } 
/*     */     
/* 573 */     return super.getUndoPresentationName();
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
/*     */   public synchronized String getRedoPresentationName() {
/* 591 */     if (this.inProgress) {
/* 592 */       if (canRedo()) {
/* 593 */         return editToBeRedone().getRedoPresentationName();
/*     */       }
/* 595 */       return UIManager.getString("AbstractUndoableEdit.redoText");
/*     */     } 
/*     */     
/* 598 */     return super.getRedoPresentationName();
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
/*     */   public void undoableEditHappened(UndoableEditEvent paramUndoableEditEvent) {
/* 611 */     addEdit(paramUndoableEditEvent.getEdit());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 621 */     return super.toString() + " limit: " + this.limit + " indexOfNextAdd: " + this.indexOfNextAdd;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swin\\undo\UndoManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */