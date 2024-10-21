


class c7157998 {

    public static synchronized String encrypt(String x) throws RuntimeException {
        MessageDigest d = MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(String.valueOf(x).getBytes());
        return byteArrayToHexString(d.digest());
    }

}
