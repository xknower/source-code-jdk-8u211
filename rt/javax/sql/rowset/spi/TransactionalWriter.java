package javax.sql.rowset.spi;

import java.sql.SQLException;
import java.sql.Savepoint;
import javax.sql.RowSetWriter;

public interface TransactionalWriter extends RowSetWriter {
  void commit() throws SQLException;
  
  void rollback() throws SQLException;
  
  void rollback(Savepoint paramSavepoint) throws SQLException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sql\rowset\spi\TransactionalWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */