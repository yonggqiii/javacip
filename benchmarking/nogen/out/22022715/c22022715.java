class c22022715 {

    protected static final byte[] digest(String s) {
        byte[] ret = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            ret = md.digest();
        } catch (NoSuchAlgorithmRuntimeException e) {
            System.err.println("no message digest algorithm available!");
            System.exit(1);
        }
        return ret;
    }
}
