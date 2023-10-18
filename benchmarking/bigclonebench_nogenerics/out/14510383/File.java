// added by JavaCIP
public interface File {

    public abstract boolean canExecute();

    public abstract boolean getPath();

    public abstract void setExecutable(boolean arg0, boolean arg1);

    public abstract boolean exists();

    public abstract boolean delete();
}
