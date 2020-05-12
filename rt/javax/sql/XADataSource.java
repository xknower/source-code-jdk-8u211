package javax.sql;

import java.sql.SQLException;

public interface XADataSource extends CommonDataSource {
  XAConnection getXAConnection() throws SQLException;
  
  XAConnection getXAConnection(String paramString1, String paramString2) throws SQLException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sql\XADataSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */