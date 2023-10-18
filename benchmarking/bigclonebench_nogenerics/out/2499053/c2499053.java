class c2499053 {

    public String encryptToMD5(String info) {
        byte[] digesta = null;
        try {
            MessageDigest alga = MessageDigest.getInstance("MD5");
            alga.update(info.getBytes());
            digesta = alga.digest();
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        }
        String rs = JavaCIPUnknownScope.byte2hex(digesta);
        return rs;
    }
}
