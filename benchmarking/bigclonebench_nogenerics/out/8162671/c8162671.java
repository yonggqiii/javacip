class c8162671 {

    public static String getMD5Hash(String hashthis) throws NoSuchAlgorithmRuntimeException {
        byte[] key = "PATIENTISAUTHENTICATION".getBytes();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(hashthis.getBytes());
        return new String(HashUtility.base64Encode(md5.digest(key)));
    }
}
