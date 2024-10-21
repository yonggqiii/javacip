// added by JavaCIP
public interface AbstractHttp11JsseProtocol<A> {

    public abstract String getDefaultSSLHostConfigName();

    public abstract void setSSLEnabled(boolean arg0);

    public abstract void addSslHostConfig(SSLHostConfig arg0, boolean arg1);
}
