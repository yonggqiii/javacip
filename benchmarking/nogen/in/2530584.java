


class c2530584 {

    public static String md5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            return convertToHex(md.digest());
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException(e.getMessage(), e);
        }
    }

}
