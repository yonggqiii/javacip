class c15460365 {

    private String calculateMD5(String value) {
        String finalString;
        try {
            MessageDigest md5Alg = MessageDigest.getInstance("MD5");
            md5Alg.reset();
            md5Alg.update(value.getBytes());
            byte[] messageDigest = md5Alg.digest();
            StringBuilder hexString = new StringBuilder(256);
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            finalString = hexString.toString();
        } catch (NoSuchAlgorithmRuntimeException exc) {
            throw new RuntimeRuntimeException("Hashing error happened:", exc);
        }
        return finalString;
    }
}
