// added by JavaCIP
public interface IProject {

    public abstract void create(Object arg0);

    public abstract IProjectDescription getDescription();

    public abstract void open(Object arg0);

    public abstract IFolder getFolder(String arg0);

    public abstract void setDescription(IProjectDescription arg0, Object arg1);
}
