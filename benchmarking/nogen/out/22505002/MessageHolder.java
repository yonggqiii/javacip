// added by JavaCIP
public interface MessageHolder {

    public static MessageHolder getInstance() {
        return null;
    }

    public abstract boolean checkerMessageExists();

    public abstract void printPersisterReport(java.io.PrintStream arg0);

    public abstract void printCheckerReport(java.io.PrintStream arg0);
}
