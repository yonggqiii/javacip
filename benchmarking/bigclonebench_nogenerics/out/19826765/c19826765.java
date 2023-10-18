class c19826765 {

    private String xifraPassword() throws RuntimeException {
        String password2 = JavaCIPUnknownScope.instance.getUsuaris().getPassword2();
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(password2.getBytes(), 0, password2.length());
        password2 = new BigInteger(1, m.digest()).toString(16);
        return password2;
    }
}
