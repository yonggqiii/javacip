


class c19869683 {

    public static String MD5(byte[] data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String text = convertToHex(data);
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }

}
