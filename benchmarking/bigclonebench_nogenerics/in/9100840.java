


class c9100840 {

    public static String encodeMD5(String value) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BASE64Encoder encoder = new BASE64Encoder();
            md.update(value.getBytes());
            byte[] raw = md.digest();
            result = encoder.encode(raw);
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }

}
