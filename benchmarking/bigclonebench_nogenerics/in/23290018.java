


class c23290018 {

    public static String md5(String s) {
        try {
            MessageDigest digester = MessageDigest.getInstance("MD5");
            digester.update(s.getBytes());
            return new BigInteger(1, digester.digest()).toString(16);
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

}