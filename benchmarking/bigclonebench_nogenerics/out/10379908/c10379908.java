class c10379908 {

    public void setContentMD5() {
        MessageDigest messagedigest = null;
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
            JavaCIPUnknownScope.contentMD5 = null;
        }
        messagedigest.update(JavaCIPUnknownScope.content.getBytes());
        byte[] digest = messagedigest.digest();
        String chk = "";
        for (int i = 0; i < digest.length; i++) {
            String s = Integer.toHexString(digest[i] & 0xFF);
            chk += ((s.length() == 1) ? "0" + s : s);
        }
        JavaCIPUnknownScope.contentMD5 = chk;
    }
}
