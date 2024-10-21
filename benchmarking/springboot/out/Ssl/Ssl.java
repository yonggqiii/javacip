import java.util.ArrayList;
import java.util.List;

public class Ssl {

    private boolean enabled = true;

    private String bundle;

    private ClientAuth clientAuth;

    private String[] ciphers;

    private String[] enabledProtocols;

    private String keyAlias;

    private String keyPassword;

    private String keyStore;

    private String keyStorePassword;

    private String keyStoreType;

    private String keyStoreProvider;

    private String trustStore;

    private String trustStorePassword;

    private String trustStoreType;

    private String trustStoreProvider;

    private String certificate;

    private String certificatePrivateKey;

    private String trustCertificate;

    private String trustCertificatePrivateKey;

    private String protocol = "TLS";

    private List<ServerNameSslBundle> serverNameBundles = new ArrayList<>();

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getBundle() {
        return this.bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public ClientAuth getClientAuth() {
        return this.clientAuth;
    }

    public void setClientAuth(ClientAuth clientAuth) {
        this.clientAuth = clientAuth;
    }

    public String[] getCiphers() {
        return this.ciphers;
    }

    public void setCiphers(String[] ciphers) {
        this.ciphers = ciphers;
    }

    public String[] getEnabledProtocols() {
        return this.enabledProtocols;
    }

    public void setEnabledProtocols(String[] enabledProtocols) {
        this.enabledProtocols = enabledProtocols;
    }

    public String getKeyAlias() {
        return this.keyAlias;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public String getKeyPassword() {
        return this.keyPassword;
    }

    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    public String getKeyStore() {
        return this.keyStore;
    }

    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

    public String getKeyStorePassword() {
        return this.keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getKeyStoreType() {
        return this.keyStoreType;
    }

    public void setKeyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
    }

    public String getKeyStoreProvider() {
        return this.keyStoreProvider;
    }

    public void setKeyStoreProvider(String keyStoreProvider) {
        this.keyStoreProvider = keyStoreProvider;
    }

    public String getTrustStore() {
        return this.trustStore;
    }

    public void setTrustStore(String trustStore) {
        this.trustStore = trustStore;
    }

    public String getTrustStorePassword() {
        return this.trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    public String getTrustStoreType() {
        return this.trustStoreType;
    }

    public void setTrustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
    }

    public String getTrustStoreProvider() {
        return this.trustStoreProvider;
    }

    public void setTrustStoreProvider(String trustStoreProvider) {
        this.trustStoreProvider = trustStoreProvider;
    }

    public String getCertificate() {
        return this.certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getCertificatePrivateKey() {
        return this.certificatePrivateKey;
    }

    public void setCertificatePrivateKey(String certificatePrivateKey) {
        this.certificatePrivateKey = certificatePrivateKey;
    }

    public String getTrustCertificate() {
        return this.trustCertificate;
    }

    public void setTrustCertificate(String trustCertificate) {
        this.trustCertificate = trustCertificate;
    }

    public String getTrustCertificatePrivateKey() {
        return this.trustCertificatePrivateKey;
    }

    public void setTrustCertificatePrivateKey(String trustCertificatePrivateKey) {
        this.trustCertificatePrivateKey = trustCertificatePrivateKey;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public static boolean isEnabled(Ssl ssl) {
        return (ssl != null) && ssl.isEnabled();
    }

    public List<ServerNameSslBundle> getServerNameBundles() {
        return this.serverNameBundles;
    }

    public void setServerNameBundles(List<ServerNameSslBundle> serverNameBundles) {
        this.serverNameBundles = serverNameBundles;
    }

    public static Ssl forBundle(String bundle) {
        Ssl ssl = new Ssl();
        ssl.setBundle(bundle);
        return ssl;
    }
}
