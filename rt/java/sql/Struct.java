package java.sql;

import java.util.Map;

public interface Struct {
  String getSQLTypeName() throws SQLException;
  
  Object[] getAttributes() throws SQLException;
  
  Object[] getAttributes(Map<String, Class<?>> paramMap) throws SQLException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\sql\Struct.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */