// added by JavaCIP
public interface MessageFactory {

    public static MessageFactory newInstance(boolean arg0) {
        return null;
    }

    public abstract SOAPMessage createMessage(Object arg0, boolean arg1);
}
