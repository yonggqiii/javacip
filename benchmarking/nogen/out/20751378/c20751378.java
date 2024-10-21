class c20751378 {

    private String hashPassword(String password) throws NoSuchAlgorithmRuntimeException {
        String hash = null;
        MessageDigest md = MessageDigest.getInstance("SHA");
        JavaCIPUnknownScope.log.debug("secure hash on password " + password);
        md.update(password.getBytes());
        hash = new String(Base64.encodeBase64(md.digest()));
        JavaCIPUnknownScope.log.debug("returning hash " + hash);
        return hash;
    }
}
