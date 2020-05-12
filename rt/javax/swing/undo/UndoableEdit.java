package javax.swing.undo;

public interface UndoableEdit {
  void undo() throws CannotUndoException;
  
  boolean canUndo();
  
  void redo() throws CannotRedoException;
  
  boolean canRedo();
  
  void die();
  
  boolean addEdit(UndoableEdit paramUndoableEdit);
  
  boolean replaceEdit(UndoableEdit paramUndoableEdit);
  
  boolean isSignificant();
  
  String getPresentationName();
  
  String getUndoPresentationName();
  
  String getRedoPresentationName();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swin\\undo\UndoableEdit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */