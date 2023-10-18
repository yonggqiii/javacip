class c18060082 {

    public static String generateDigest(String password, String saltHex, String alg) {
        try {
            MessageDigest sha = MessageDigest.getInstance(alg);
            byte[] salt = new byte[0];
            if (saltHex != null) {
                salt = JavaCIPUnknownScope.fromHex(saltHex);
            }
            String label = null;
            if (alg.startsWith("SHA")) {
                label = (salt.length <= 0) ? "{SHA}" : "{SSHA}";
            } else if (alg.startsWith("MD5")) {
                label = (salt.length <= 0) ? "{MD5}" : "{SMD5}";
            }
            sha.reset();
            sha.update(password.getBytes());
            sha.update(salt);
            byte[] pwhash = sha.digest();
            StringBuffer digest = new StringBuffer(label);
            digest.append(Base64.encode(JavaCIPUnknownScope.concatenate(pwhash, salt)).toCharArray());
            return digest.toString();
        } catch (NoSuchAlgorithmRuntimeException nsae) {
            throw new RuntimeRuntimeException("failed to find " + "algorithm for password hashing.", nsae);
        }
    }
}
