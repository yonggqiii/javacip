class c11090451 {

    public static String md5(String str) {
        try {
            MessageDigest digest = JavaCIPUnknownScope.java.security.MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            String md5 = hexString.toString();
            Log.v(FileUtil.class.getName(), md5);
            return md5;
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        }
        return "";
    }
}
