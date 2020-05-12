package java.awt.dnd.peer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.InvalidDnDOperationException;

public interface DropTargetContextPeer {
  void setTargetActions(int paramInt);
  
  int getTargetActions();
  
  DropTarget getDropTarget();
  
  DataFlavor[] getTransferDataFlavors();
  
  Transferable getTransferable() throws InvalidDnDOperationException;
  
  boolean isTransferableJVMLocal();
  
  void acceptDrag(int paramInt);
  
  void rejectDrag();
  
  void acceptDrop(int paramInt);
  
  void rejectDrop();
  
  void dropComplete(boolean paramBoolean);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\dnd\peer\DropTargetContextPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */