class c13012591 {

    protected String getPasswordHash(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException e) {
            JavaCIPUnknownScope.log.error("MD5 algorithm not found", e);
            throw new ServiceRuntimeException(e);
        }
        md.update(password.getBytes());
        byte[] hash = md.digest();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            buf.append(Integer.toHexString(hash[i] & 0xff));
        }
        return buf.toString();
    }
}
