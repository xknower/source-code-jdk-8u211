package org.omg.CORBA;

public abstract class ContextList {
  public abstract int count();
  
  public abstract void add(String paramString);
  
  public abstract String item(int paramInt) throws Bounds;
  
  public abstract void remove(int paramInt) throws Bounds;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CORBA\ContextList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */