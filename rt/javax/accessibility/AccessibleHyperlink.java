package javax.accessibility;

public abstract class AccessibleHyperlink implements AccessibleAction {
  public abstract boolean isValid();
  
  public abstract int getAccessibleActionCount();
  
  public abstract boolean doAccessibleAction(int paramInt);
  
  public abstract String getAccessibleActionDescription(int paramInt);
  
  public abstract Object getAccessibleActionObject(int paramInt);
  
  public abstract Object getAccessibleActionAnchor(int paramInt);
  
  public abstract int getStartIndex();
  
  public abstract int getEndIndex();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\accessibility\AccessibleHyperlink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */