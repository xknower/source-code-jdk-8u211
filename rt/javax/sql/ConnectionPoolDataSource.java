package javax.sql;

import java.sql.SQLException;

public interface ConnectionPoolDataSource extends CommonDataSource {
  PooledConnection getPooledConnection() throws SQLException;
  
  PooledConnection getPooledConnection(String paramString1, String paramString2) throws SQLException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sql\ConnectionPoolDataSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */