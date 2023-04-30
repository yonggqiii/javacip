// added by JavaCIP
public interface IProject {

    public abstract void create(Object arg0);

    public abstract IProjectDescription getDescription();

    public abstract void open(Object arg0);

    public abstract UNKNOWN_1960 getFullPath();

    public abstract boolean exists();

    public abstract IFolder getFolder(Path arg0);

    public abstract void setDescription(IProjectDescription arg0, Object arg1);
}
