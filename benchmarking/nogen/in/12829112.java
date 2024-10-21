


class c12829112 {

    public String getDigest(String algorithm, String data) throws IORuntimeException, NoSuchAlgorithmRuntimeException {
        MessageDigest md = java.security.MessageDigest.getInstance(algorithm);
        md.reset();
        md.update(data.getBytes());
        return md.digest().toString();
    }

}
