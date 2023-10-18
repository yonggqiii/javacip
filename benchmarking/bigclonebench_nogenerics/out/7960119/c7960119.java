class c7960119 {

    private void getRandomGuid(boolean secure) {
        MessageDigest md5 = null;
        StringBuffer sbValueBeforeMD5 = new StringBuffer();
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
        long time = System.currentTimeMillis();
        long rand = 0;
        if (secure) {
            rand = JavaCIPUnknownScope.secureRandom.nextLong();
        } else {
            rand = JavaCIPUnknownScope.random.nextLong();
        }
        sbValueBeforeMD5.append(JavaCIPUnknownScope.id);
        sbValueBeforeMD5.append(":");
        sbValueBeforeMD5.append(Long.toString(time));
        sbValueBeforeMD5.append(":");
        sbValueBeforeMD5.append(Long.toString(rand));
        String valueBeforeMD5 = sbValueBeforeMD5.toString();
        md5.update(valueBeforeMD5.getBytes());
        byte[] array = md5.digest();
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < array.length; ++j) {
            int b = array[j] & 0xFF;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
        }
        JavaCIPUnknownScope.guid = sb.toString();
    }
}
