


class c15821341 {

    public void update() {
        try {
            String passwordMD5 = new String();
            if (password != null && password.length() > 0) {
                MessageDigest md = MessageDigest.getInstance("md5");
                md.update(password.getBytes());
                byte[] digest = md.digest();
                for (int i = 0; i < digest.length; i++) {
                    passwordMD5 += Integer.toHexString((digest[i] >> 4) & 0xf);
                    passwordMD5 += Integer.toHexString((digest[i] & 0xf));
                }
            }
            authCode = new String(Base64Encoder.encode(new String(username + ";" + passwordMD5).getBytes()));
        } catch (RuntimeException throwable) {
            throwable.printStackTrace();
        }
    }

}
