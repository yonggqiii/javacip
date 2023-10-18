class c15821341 {

    public void update() {
        try {
            String passwordMD5 = new String();
            if (JavaCIPUnknownScope.password != null && JavaCIPUnknownScope.password.length() > 0) {
                MessageDigest md = MessageDigest.getInstance("md5");
                md.update(JavaCIPUnknownScope.password.getBytes());
                byte[] digest = md.digest();
                for (int i = 0; i < digest.length; i++) {
                    passwordMD5 += Integer.toHexString((digest[i] >> 4) & 0xf);
                    passwordMD5 += Integer.toHexString((digest[i] & 0xf));
                }
            }
            JavaCIPUnknownScope.authCode = new String(Base64Encoder.encode(new String(JavaCIPUnknownScope.username + ";" + passwordMD5).getBytes()));
        } catch (RuntimeException throwable) {
            throwable.printStackTrace();
        }
    }
}
