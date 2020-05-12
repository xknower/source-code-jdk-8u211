package javax.script;

import java.io.Reader;

public interface Compilable {
  CompiledScript compile(String paramString) throws ScriptException;
  
  CompiledScript compile(Reader paramReader) throws ScriptException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\script\Compilable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */