package java.lang.reflect;

public interface Member {
  public static final int PUBLIC = 0;
  
  public static final int DECLARED = 1;
  
  Class<?> getDeclaringClass();
  
  String getName();
  
  int getModifiers();
  
  boolean isSynthetic();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\reflect\Member.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */