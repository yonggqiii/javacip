// added by JavaCIP
public interface IProjectDescription {

    public abstract String[] getNatureIds();

    public abstract void setBuildSpec(ICommand[] arg0);

    public abstract ICommand newCommand();

    public abstract ICommand[] getBuildSpec();

    public abstract void setNatureIds(String[] arg0);
}
