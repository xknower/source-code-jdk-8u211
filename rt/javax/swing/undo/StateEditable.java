package javax.swing.undo;

import java.util.Hashtable;

public interface StateEditable {
  public static final String RCSID = "$Id: StateEditable.java,v 1.2 1997/09/08 19:39:08 marklin Exp $";
  
  void storeState(Hashtable<Object, Object> paramHashtable);
  
  void restoreState(Hashtable<?, ?> paramHashtable);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swin\\undo\StateEditable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */