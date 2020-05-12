package java.sql;

public interface Wrapper {
  <T> T unwrap(Class<T> paramClass) throws SQLException;
  
  boolean isWrapperFor(Class<?> paramClass) throws SQLException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\sql\Wrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */