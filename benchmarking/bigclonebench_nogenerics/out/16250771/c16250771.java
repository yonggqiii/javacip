class c16250771 {

    public static String genDigest(String info) {
        MessageDigest alga;
        byte[] digesta = null;
        try {
            alga = MessageDigest.getInstance("SHA-1");
            alga.update(info.getBytes());
            digesta = alga.digest();
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        }
        return JavaCIPUnknownScope.byte2hex(digesta);
    }
}
