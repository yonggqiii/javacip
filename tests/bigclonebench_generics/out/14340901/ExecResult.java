// added by JavaCIP
public interface ExecResult {

    public abstract double getExitValue();

    public abstract String getCommand();

    public abstract Object getStderr();

    public abstract Object getStdout();
}
