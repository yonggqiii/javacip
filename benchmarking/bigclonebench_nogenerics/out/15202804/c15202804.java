class c15202804 {

    public static String getDigest(String user, String realm, String password, String method, String uri, String nonce) {
        String digest1 = user + ":" + realm + ":" + password;
        String digest2 = method + ":" + uri;
        try {
            MessageDigest digestOne = MessageDigest.getInstance("md5");
            digestOne.update(digest1.getBytes());
            String hexDigestOne = JavaCIPUnknownScope.getHexString(digestOne.digest());
            MessageDigest digestTwo = MessageDigest.getInstance("md5");
            digestTwo.update(digest2.getBytes());
            String hexDigestTwo = JavaCIPUnknownScope.getHexString(digestTwo.digest());
            String digest3 = hexDigestOne + ":" + nonce + ":" + hexDigestTwo;
            MessageDigest digestThree = MessageDigest.getInstance("md5");
            digestThree.update(digest3.getBytes());
            String hexDigestThree = JavaCIPUnknownScope.getHexString(digestThree.digest());
            return hexDigestThree;
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
