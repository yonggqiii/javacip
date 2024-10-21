// added by JavaCIP
public interface TACMessage {

    public abstract String getMessageString();

    public abstract boolean getType();

    public abstract void setReceivedMessage(String arg0);

    public abstract void deliverMessage();
}
