// added by JavaCIP
public interface WebService {

    public abstract boolean getMethodName();

    public abstract boolean getServiceURI();

    public abstract boolean getReturnType();

    public abstract Iterator parametersIterator();
}
