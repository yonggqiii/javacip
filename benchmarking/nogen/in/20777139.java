


class c20777139 {

    protected static String encodePassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(password.getBytes());
            return HexString.bufferToHex(md.digest());
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
    }

}
