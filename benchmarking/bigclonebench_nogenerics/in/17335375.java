


class c17335375 {

    public static byte[] encrypt(String x) throws NoSuchAlgorithmRuntimeException {
        MessageDigest d = null;
        d = MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(x.getBytes());
        return d.digest();
    }

}
