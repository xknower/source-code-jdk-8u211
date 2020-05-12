package javax.accessibility;

public interface AccessibleHypertext extends AccessibleText {
  int getLinkCount();
  
  AccessibleHyperlink getLink(int paramInt);
  
  int getLinkIndex(int paramInt);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\accessibility\AccessibleHypertext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */