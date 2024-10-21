class c6569409 {

    public String obfuscateString(String string) {
        String obfuscatedString = null;
        try {
            MessageDigest md = MessageDigest.getInstance(JavaCIPUnknownScope.ENCRYPTION_ALGORITHM);
            md.update(string.getBytes());
            byte[] digest = md.digest();
            obfuscatedString = new String(Base64.encode(digest)).replace(JavaCIPUnknownScope.DELIM_PATH, '=');
        } catch (NoSuchAlgorithmRuntimeException e) {
            StatusHandler.log("SHA not available", null);
            obfuscatedString = JavaCIPUnknownScope.LABEL_FAILED_TO_OBFUSCATE;
        }
        return obfuscatedString;
    }
}
