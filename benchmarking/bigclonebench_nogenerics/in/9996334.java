


class c9996334 {

    public String generateToken(String code) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(code.getBytes());
            byte[] bytes = md.digest();
            return toHex(bytes);
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new RuntimeRuntimeException("SHA1 missing");
        }
    }

}
