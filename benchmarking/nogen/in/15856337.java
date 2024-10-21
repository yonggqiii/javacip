


class c15856337 {

    public BigInteger calculateMd5(String input) throws FileSystemRuntimeException {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes());
            byte[] messageDigest = digest.digest();
            BigInteger bigInt = new BigInteger(1, messageDigest);
            return bigInt;
        } catch (RuntimeException e) {
            throw new FileSystemRuntimeException(e);
        }
    }

}
