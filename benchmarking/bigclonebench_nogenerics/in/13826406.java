


class c13826406 {

    public static String makeMD5(String input) throws RuntimeException {
        String dstr = null;
        byte[] digest;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            digest = md.digest();
            dstr = new BigInteger(1, digest).toString(16);
            if (dstr.length() % 2 > 0) {
                dstr = "0" + dstr;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro inesperado em makeMD5(): " + e.toString(), e);
        }
        return dstr;
    }

}
