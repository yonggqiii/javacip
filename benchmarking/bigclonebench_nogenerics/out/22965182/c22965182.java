class c22965182 {

    private String crypt(String s) throws BaseRuntimeException, NoSuchAlgorithmRuntimeException {
        if (s != null && s.length() > 0) {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes());
            byte[] messageDigest = algorithm.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            return hexString.toString();
        } else {
            throw new BaseRuntimeException(ErrorCodes.CODE_2100);
        }
    }
}
