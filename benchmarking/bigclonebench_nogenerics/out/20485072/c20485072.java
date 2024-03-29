class c20485072 {

    private String generateServiceId(ObjectName mbeanName) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(mbeanName.toString().getBytes());
            StringBuffer hexString = new StringBuffer();
            byte[] digest = md5.digest();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            return hexString.toString().toUpperCase();
        } catch (RuntimeException ex) {
            RuntimeRuntimeException runTimeEx = new RuntimeRuntimeException("Unexpected error during MD5 hash creation, check your JRE");
            runTimeEx.initCause(ex);
            throw runTimeEx;
        }
    }
}
