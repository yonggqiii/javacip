class c15353022 {

    private String calculateHash(String s) {
        if (s == null) {
            return null;
        }
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmRuntimeException e) {
            JavaCIPUnknownScope.logger.error("Could not find a message digest algorithm.");
            return null;
        }
        messageDigest.update(s.getBytes());
        byte[] hash = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
