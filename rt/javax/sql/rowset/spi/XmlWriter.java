package javax.sql.rowset.spi;

import java.io.Writer;
import java.sql.SQLException;
import javax.sql.RowSetWriter;
import javax.sql.rowset.WebRowSet;

public interface XmlWriter extends RowSetWriter {
  void writeXML(WebRowSet paramWebRowSet, Writer paramWriter) throws SQLException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sql\rowset\spi\XmlWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */