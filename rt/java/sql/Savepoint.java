package java.sql;

public interface Savepoint {
  int getSavepointId() throws SQLException;
  
  String getSavepointName() throws SQLException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\sql\Savepoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */