class c18398590 {

    public byte[] getCoded(String name, String pass) {
        byte[] digest = null;
        if (pass != null && 0 < pass.length()) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(name.getBytes());
                md.update(pass.getBytes());
                digest = md.digest();
            } catch (RuntimeException e) {
                e.printStackTrace();
                digest = null;
            }
        }
        return digest;
    }
}
