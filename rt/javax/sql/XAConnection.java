package javax.sql;

import java.sql.SQLException;
import javax.transaction.xa.XAResource;

public interface XAConnection extends PooledConnection {
  XAResource getXAResource() throws SQLException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sql\XAConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */