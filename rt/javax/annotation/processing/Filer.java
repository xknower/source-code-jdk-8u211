package javax.annotation.processing;

import java.io.IOException;
import javax.lang.model.element.Element;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

public interface Filer {
  JavaFileObject createSourceFile(CharSequence paramCharSequence, Element... paramVarArgs) throws IOException;
  
  JavaFileObject createClassFile(CharSequence paramCharSequence, Element... paramVarArgs) throws IOException;
  
  FileObject createResource(JavaFileManager.Location paramLocation, CharSequence paramCharSequence1, CharSequence paramCharSequence2, Element... paramVarArgs) throws IOException;
  
  FileObject getResource(JavaFileManager.Location paramLocation, CharSequence paramCharSequence1, CharSequence paramCharSequence2) throws IOException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\annotation\processing\Filer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */