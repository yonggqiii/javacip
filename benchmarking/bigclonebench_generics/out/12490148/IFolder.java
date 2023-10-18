// added by JavaCIP
public interface IFolder {

    public abstract IPath getFullPath();

    public abstract void create(boolean arg0, boolean arg1, SubProgressMonitor arg2);

    public abstract boolean getFile(String arg0);
}
