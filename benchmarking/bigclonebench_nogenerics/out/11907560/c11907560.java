class c11907560 {

    public static String hash(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer(digest.length * 2);
            for (int i = 0; i < digest.length; ++i) {
                byte b = digest[i];
                int high = (b & 0xF0) >> 4;
                int low = b & 0xF;
                sb.append(JavaCIPUnknownScope.DECIMAL_HEX[high]);
                sb.append(JavaCIPUnknownScope.DECIMAL_HEX[low]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new NonBusinessRuntimeException("Error hashing string", e);
        }
    }
}
