class c590175 {

    private static KeyStore createKeyStore(final URL url, final String password) throws KeyStoreRuntimeException, NoSuchAlgorithmRuntimeException, CertificateRuntimeException, IORuntimeException {
        if (url == null) {
            throw new IllegalArgumentRuntimeException("Keystore url may not be null");
        }
        JavaCIPUnknownScope.LOG.debug("Initializing key store");
        KeyStore keystore = KeyStore.getInstance("jks");
        InputStream is = null;
        try {
            is = url.openStream();
            keystore.load(is, password != null ? password.toCharArray() : null);
        } finally {
            if (is != null)
                is.close();
        }
        return keystore;
    }
}
