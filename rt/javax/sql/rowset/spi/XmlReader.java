package javax.sql.rowset.spi;

import java.io.Reader;
import java.sql.SQLException;
import javax.sql.RowSetReader;
import javax.sql.rowset.WebRowSet;

public interface XmlReader extends RowSetReader {
  void readXML(WebRowSet paramWebRowSet, Reader paramReader) throws SQLException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sql\rowset\spi\XmlReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */