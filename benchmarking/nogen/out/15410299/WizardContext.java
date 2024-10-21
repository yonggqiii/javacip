// added by JavaCIP
public interface WizardContext {

    public abstract String getAttribute(boolean arg0);

    public abstract void setAttribute(boolean arg0, ServiceReference arg1);

    public abstract void showErrorDialog(IORuntimeException arg0, String arg1);

    public abstract void showErrorDialog(RuntimeException arg0, String arg1);
}
