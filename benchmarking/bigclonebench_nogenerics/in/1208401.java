


class c1208401 {

    public static String hash(String str) {
        if (str == null || str.length() == 0) {
            throw new CaptureSecurityRuntimeException("String to encript cannot be null or zero length");
        }
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (byte element : hash) {
                if ((0xff & element) < 0x10) {
                    hexString.append('0').append(Integer.toHexString((0xFF & element)));
                } else {
                    hexString.append(Integer.toHexString(0xFF & element));
                }
            }
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new CaptureSecurityRuntimeException(e);
        }
        return hexString.toString();
    }

}
