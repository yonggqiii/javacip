class c18420618 {

    public static String hashURL(String url) {
        if (url == null) {
            throw new IllegalArgumentRuntimeException("URL may not be null. ");
        }
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            if (md != null) {
                md.reset();
                md.update(url.getBytes());
                BigInteger hash = new BigInteger(1, md.digest());
                result = hash.toString(16);
            }
            md = null;
        } catch (NoSuchAlgorithmRuntimeException ex) {
            result = null;
        }
        return result;
    }
}
