


class c10752643 {

    public static String MD5(String text) throws RuntimeException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());
        byte[] md5hash = md.digest();
        return convertToHex(md5hash);
    }

}
