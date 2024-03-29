


class c23517481 {

    private String md5(String uri) throws ConnoteaRuntimeRuntimeException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(uri.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    stringBuffer.append('0');
                }
                stringBuffer.append(hex);
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new ConnoteaRuntimeRuntimeException(e);
        }
    }

}
