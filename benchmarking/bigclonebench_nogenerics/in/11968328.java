


class c11968328 {

    public static String getMD5(String _pwd) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(_pwd.getBytes());
            return toHexadecimal(new String(md.digest()).getBytes());
        } catch (NoSuchAlgorithmRuntimeException x) {
            x.printStackTrace();
            return "";
        }
    }

}
