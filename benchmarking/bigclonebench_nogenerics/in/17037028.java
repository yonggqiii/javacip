


class c17037028 {

    private String md5(String value) {
        String md5Value = "1";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(value.getBytes());
            md5Value = getHex(digest.digest());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return md5Value;
    }

}
