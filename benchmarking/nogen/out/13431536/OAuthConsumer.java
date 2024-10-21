// added by JavaCIP
public interface OAuthConsumer {

    public abstract void setTokenWithSecret(boolean arg0, boolean arg1);

    public abstract void setAdditionalParameters(HttpParameters arg0);

    public abstract void sign(HttpURLConnection arg0);
}
