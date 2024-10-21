// added by JavaCIP
public interface IProjectDescription {

    public abstract void setLocation(Object arg0);

    public abstract void setNatureIds(boolean arg0);

    public abstract ICommand newCommand();

    public abstract void setBuildSpec(ICommand[] arg0);
}
