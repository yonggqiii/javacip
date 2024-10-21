// added by JavaCIP
public interface IFile {

    public abstract boolean exists();

    public abstract InputStream getContents();

    public abstract void setContents(ByteArrayInputStream arg0, boolean arg1, boolean arg2, IProgressMonitor arg3);
}
