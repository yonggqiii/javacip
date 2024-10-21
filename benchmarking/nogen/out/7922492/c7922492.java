class c7922492 {

    protected String calcAuthResponse(String challenge) throws NoSuchAlgorithmRuntimeException {
        MessageDigest md = MessageDigest.getInstance(JavaCIPUnknownScope.securityPolicy);
        md.update(challenge.getBytes());
        for (int i = 0, n = JavaCIPUnknownScope.password.length; i < n; i++) {
            md.update((byte) JavaCIPUnknownScope.password[i]);
        }
        byte[] digest = md.digest();
        StringBuffer digestText = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            int v = (digest[i] < 0) ? digest[i] + 256 : digest[i];
            String hex = Integer.toHexString(v);
            if (hex.length() == 1) {
                digestText.append("0");
            }
            digestText.append(hex);
        }
        return digestText.toString();
    }
}
