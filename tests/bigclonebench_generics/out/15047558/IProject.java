// added by JavaCIP
public interface IProject {

    public abstract void create(NullProgressMonitor arg0);

    public abstract IProjectDescription getDescription();

    public abstract void open(NullProgressMonitor arg0);

    public abstract boolean exists();

    public abstract IFolder getFolder(String arg0);

    public abstract void setDescription(IProjectDescription arg0, NullProgressMonitor arg1);
}
