package javax.sql.rowset;

import java.sql.SQLException;

public interface Joinable {
  void setMatchColumn(int paramInt) throws SQLException;
  
  void setMatchColumn(int[] paramArrayOfint) throws SQLException;
  
  void setMatchColumn(String paramString) throws SQLException;
  
  void setMatchColumn(String[] paramArrayOfString) throws SQLException;
  
  int[] getMatchColumnIndexes() throws SQLException;
  
  String[] getMatchColumnNames() throws SQLException;
  
  void unsetMatchColumn(int paramInt) throws SQLException;
  
  void unsetMatchColumn(int[] paramArrayOfint) throws SQLException;
  
  void unsetMatchColumn(String paramString) throws SQLException;
  
  void unsetMatchColumn(String[] paramArrayOfString) throws SQLException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sql\rowset\Joinable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */