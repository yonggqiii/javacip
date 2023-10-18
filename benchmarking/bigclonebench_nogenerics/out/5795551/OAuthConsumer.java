// added by JavaCIP
public interface OAuthConsumer {

    public abstract void setTokenWithSecret(String arg0, String arg1);

    public abstract void setAdditionalParameters(HttpParameters arg0);

    public abstract void sign(HttpURLConnection arg0);
}
