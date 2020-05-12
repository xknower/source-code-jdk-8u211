package javax.sql;

import java.util.EventListener;

public interface StatementEventListener extends EventListener {
  void statementClosed(StatementEvent paramStatementEvent);
  
  void statementErrorOccurred(StatementEvent paramStatementEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\sql\StatementEventListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */