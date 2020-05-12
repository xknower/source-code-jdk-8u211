package java.sql;

import java.util.Map;

public interface Ref {
  String getBaseTypeName() throws SQLException;
  
  Object getObject(Map<String, Class<?>> paramMap) throws SQLException;
  
  Object getObject() throws SQLException;
  
  void setObject(Object paramObject) throws SQLException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\sql\Ref.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */