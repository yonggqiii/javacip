


class c49834 {

    public String encryptPassword(String clearPassword) throws NullPointerRuntimeException {
        MessageDigest sha;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new NullPointerRuntimeException("NoSuchAlgorithmRuntimeException: " + e.toString());
        }
        sha.update(clearPassword.getBytes());
        byte encryptedPassword[] = sha.digest();
        sha = null;
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < encryptedPassword.length; i++) {
            result.append(Byte.toString(encryptedPassword[i]));
        }
        return (result.toString());
    }

}
