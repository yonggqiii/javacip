


class c16030263 {

    public static String md5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            return ForumUtil.bufferToHex(md.digest());
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

}
