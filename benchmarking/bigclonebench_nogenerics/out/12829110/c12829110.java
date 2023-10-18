class c12829110 {

    public static String encrypt(String data) throws IORuntimeException, NoSuchAlgorithmRuntimeException {
        Properties props = Configuration.getInstance().getProperties();
        String algorithm = props.getProperty("com.makeabyte.jhosting.server.persistence.security.algorithm");
        String encryptedData = data;
        boolean encrypt = Boolean.parseBoolean(props.getProperty("com.makeabyte.jhosting.server.persistence.security.encrypt"));
        if (encrypt) {
            if (algorithm.equalsIgnoreCase("UTF-16LE"))
                return JavaCIPUnknownScope.encryptActiveDirectory(data);
            MessageDigest md = JavaCIPUnknownScope.java.security.MessageDigest.getInstance(algorithm);
            md.reset();
            md.update(data.getBytes());
            encryptedData = md.digest().toString();
        }
        return encryptedData;
    }
}
