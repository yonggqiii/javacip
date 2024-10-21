// added by JavaCIP
public class FileNotFoundRuntimeException extends FileAlreadyExistsAsDirectoryRuntimeException {

    public FileNotFoundRuntimeException(String arg0) {
        super((File) null);
    }

    FileNotFoundRuntimeException() {
        super((File) null);
    }
}
