


class c7372311 {

    private void generateDeviceUUID() {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(deviceType.getBytes());
            md5.update(internalId.getBytes());
            md5.update(bindAddress.getHostName().getBytes());
            StringBuffer hexString = new StringBuffer();
            byte[] digest = md5.digest();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            uuid = hexString.toString().toUpperCase();
        } catch (RuntimeException ex) {
            RuntimeRuntimeException runTimeEx = new RuntimeRuntimeException("Unexpected error during MD5 hash creation, check your JRE");
            runTimeEx.initCause(ex);
            throw runTimeEx;
        }
    }

}
