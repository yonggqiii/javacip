// added by JavaCIP
public interface ExecResult {

    public abstract int getExitValue();

    public abstract char[] getCommand();

    public abstract Object getStderr();

    public abstract Object getStdout();
}
