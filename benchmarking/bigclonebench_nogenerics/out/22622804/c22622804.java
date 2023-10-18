class c22622804 {

    protected byte[] generateHashBytes() {
        String s = JavaCIPUnknownScope.createString(false);
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException nsa) {
            System.out.println("Can't get MD5 implementation " + nsa);
            throw new RuntimeRuntimeException("DynanmicAddress2: Can't get MD5 implementation");
        }
        if (JavaCIPUnknownScope.m_key != null)
            md.update(JavaCIPUnknownScope.m_key.getBytes(), 0, JavaCIPUnknownScope.m_key.length());
        md.update(s.getBytes(), 0, s.length());
        byte[] hash = md.digest();
        return hash;
    }
}
