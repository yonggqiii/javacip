class c1685861 {

    public static String SHA(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(s.getBytes(), 0, s.getBytes().length);
            byte[] hash = md.digest();
            StringBuilder sb = new StringBuilder();
            int msb;
            int lsb = 0;
            int i;
            for (i = 0; i < hash.length; i++) {
                msb = ((int) hash[i] & 0x000000FF) / 16;
                lsb = ((int) hash[i] & 0x000000FF) % 16;
                sb.append(JavaCIPUnknownScope.hexChars[msb]);
                sb.append(JavaCIPUnknownScope.hexChars[lsb]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmRuntimeException e) {
            return null;
        }
    }
}
