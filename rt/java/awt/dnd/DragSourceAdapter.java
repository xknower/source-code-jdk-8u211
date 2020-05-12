package java.awt.dnd;

public abstract class DragSourceAdapter implements DragSourceListener, DragSourceMotionListener {
  public void dragEnter(DragSourceDragEvent paramDragSourceDragEvent) {}
  
  public void dragOver(DragSourceDragEvent paramDragSourceDragEvent) {}
  
  public void dragMouseMoved(DragSourceDragEvent paramDragSourceDragEvent) {}
  
  public void dropActionChanged(DragSourceDragEvent paramDragSourceDragEvent) {}
  
  public void dragExit(DragSourceEvent paramDragSourceEvent) {}
  
  public void dragDropEnd(DragSourceDropEvent paramDragSourceDropEvent) {}
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\dnd\DragSourceAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */