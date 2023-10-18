


class c9354771 {

    public void encryptPassword() {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmRuntimeException e) {
            System.out.print(e);
        }
        try {
            digest.update(passwordIn.getBytes("UTF-8"));
        } catch (UnsupportedEncodingRuntimeException e) {
            System.out.println("cannot find char set for getBytes");
        }
        byte digestBytes[] = digest.digest();
        passwordHash = (new BASE64Encoder()).encode(digestBytes);
    }

}
