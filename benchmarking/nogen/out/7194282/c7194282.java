class c7194282 {

    public static String createNormalizedJarDescriptorDigest(String path) throws RuntimeException {
        String descriptor = JavaCIPUnknownScope.createNormalizedDescriptor(new JarFile2(path));
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(descriptor.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        }
        return "";
    }
}
