package java.util.regex;

public interface MatchResult {
  int start();
  
  int start(int paramInt);
  
  int end();
  
  int end(int paramInt);
  
  String group();
  
  String group(int paramInt);
  
  int groupCount();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\regex\MatchResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */