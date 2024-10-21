// added by JavaCIP
public interface TranslationResponse {

    public abstract void addHeaders(boolean arg0);

    public abstract boolean getOutputStream();

    public abstract void setEndState(boolean arg0);

    public abstract void setEndState(ResponseStateRuntimeException arg0);
}
