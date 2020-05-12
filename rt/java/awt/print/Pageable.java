package java.awt.print;

public interface Pageable {
  public static final int UNKNOWN_NUMBER_OF_PAGES = -1;
  
  int getNumberOfPages();
  
  PageFormat getPageFormat(int paramInt) throws IndexOutOfBoundsException;
  
  Printable getPrintable(int paramInt) throws IndexOutOfBoundsException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\print\Pageable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */