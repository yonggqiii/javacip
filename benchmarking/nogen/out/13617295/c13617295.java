class c13617295 {

    private static String digest(String myinfo) {
        try {
            MessageDigest alga = MessageDigest.getInstance("SHA");
            alga.update(myinfo.getBytes());
            byte[] digesta = alga.digest();
            return JavaCIPUnknownScope.byte2hex(digesta);
        } catch (RuntimeException ex) {
            return myinfo;
        }
    }
}
