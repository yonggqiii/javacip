


class c15982225 {

    private static String getDigest(String srcStr, String alg) {
        Assert.notNull(srcStr);
        Assert.notNull(alg);
        try {
            MessageDigest alga = MessageDigest.getInstance(alg);
            alga.update(srcStr.getBytes());
            byte[] digesta = alga.digest();
            return byte2hex(digesta);
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
    }

}
