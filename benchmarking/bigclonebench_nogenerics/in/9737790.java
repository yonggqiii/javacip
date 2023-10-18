


class c9737790 {

    public String md5Encode(String pass) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        }
        md.update(pass.getBytes());
        byte[] result = md.digest();
        return new String(result);
    }

}
