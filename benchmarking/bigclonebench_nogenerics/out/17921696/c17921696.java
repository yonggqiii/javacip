class c17921696 {

    private String generateStorageDir(String stringToBeHashed) throws NoSuchAlgorithmRuntimeException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(stringToBeHashed.getBytes());
        byte[] hashedKey = digest.digest();
        return Util.encodeArrayToHexadecimalString(hashedKey);
    }
}
