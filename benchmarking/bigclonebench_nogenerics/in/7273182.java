


class c7273182 {

    public synchronized String encrypt(String text) throws RuntimeException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
        md.update(text.getBytes());
        byte raw[] = md.digest();
        String hash = "";
        for (int i = 0; i < raw.length; i++) {
            byte temp = raw[i];
            String s = Integer.toHexString(new Byte(temp));
            while (s.length() < 2) {
                s = "0" + s;
            }
            s = s.substring(s.length() - 2);
            hash += s;
        }
        return hash;
    }

}
