// added by JavaCIP
public interface ExecResult {

    public abstract int getExitValue();

    public abstract boolean getCommand();

    public abstract Object getStderr();

    public abstract Object getStdout();
}
