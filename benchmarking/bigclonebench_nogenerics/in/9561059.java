


class c9561059 {

    private static byte[] createHash(EHashType hashType, String string) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(hashType.getJavaHashType());
            md.reset();
            md.update(string.getBytes());
            byte[] byteResult = md.digest();
            return byteResult;
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

}
